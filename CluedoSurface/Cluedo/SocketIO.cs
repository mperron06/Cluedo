using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Shapes;
using System.Windows.Media;
using SocketIOClient;
using SocketIOClient.Messages;
using Newtonsoft.Json;
using System.Collections;
using System.Windows.Media.Imaging;

/*definition of the class used to convert to json objet, which is passed between the client and the server*/
public class Joueur
{
    public int idJoueur { get; set; }
    public string persoName { get; set; }
}

public class ChoixMovement
{
    public string idJoueur { get; set; }
    public string idCase { get; set; }
    public int value { get; set; }
}

public class Supposition 
{
    public string perso{ get; set; }
    public string arme{ get; set; }
    public string lieu{ get; set; }
}

public class QuiAEnvoyeLaCarte
{
    public string nameJoueur { get; set; }
}

namespace Cluedo
{
    class SocketIO
    {
        // Socket permettant d'échanger avec le serveur
        static Client socket;

        public static void ForcerDebutPartie()
        {
            socket.Emit("forcerDebutPartie",null);
        }


        public static void tourChoixSupposition(String idCase)
        {
            socket.Emit("tourChoixSupposition", idCase);

            Console.WriteLine("envoyer le id de la salle " + idCase);
        }

        public static void tourChoixAccusation(String idCase)
        {
            socket.Emit("tourChoixAccusation", idCase);
        }

        public static void tourTermine(String idCase)
        {
            socket.Emit("tourTermine", idCase);
        }

        public static void ValidationCase(String cmd) {
            socket.Emit("validationCaseTable", cmd);
        }

        public static void tagsDansPiece(ArrayList tags) {
            Console.WriteLine("tags dans pieces");
            for (int i = 0; i < tags.Count; i++)
            {
                Console.WriteLine("haha tags dans piece " + tags[i]+" fini");
                socket.Emit("newPionSupposition", tags[i]);
            }
        }


        public static void start() {
            Console.WriteLine("start !!!");
            socket.Emit("lancementDebutPartie", "on passe à la page suivante");
        }

        public static void lancementPionsPrets() {
            socket.Emit("lancementPionsPrets", null);
        }


        public static void Execute()
        {
            Console.WriteLine("Starting SocketIO");

            // Initialisation du socket client vers le serveur
            socket = new Client("http://localhost:8080"); // url to nodejs 

            // register for 'connect' event with io server
            socket.On("connect", (fn) =>
            {
                Console.WriteLine("Connecté au serveur...\r\n");
                socket.Emit("addTable", null);
            });


            socket.On("nouveauJoueur", (data) =>
            {
                Application surface = System.Windows.Application.Current;
                surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
                {
                    Joueur joueur = data.Json.GetFirstArgAs<Joueur>();
                    Console.WriteLine("nouveauJoueur " + joueur.persoName);
                    Page1.getInstance().hideQrcode(joueur.persoName);
                    MainWindowCluedo.joueurs.Add(joueur.persoName);
                    Console.WriteLine(MainWindowCluedo.joueurs.Count);
                });
            });

            socket.On("choixMouvement", (data) =>
            {
                Application surface = System.Windows.Application.Current;
                surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
                {
                    ChoixMovement mouvement = data.Json.GetFirstArgAs<ChoixMovement>();
                    MainWindowCluedo.getInstance().choixMouvement(mouvement.idJoueur, mouvement.idCase, mouvement.value);
                    Console.WriteLine("Test: " + mouvement.idJoueur + " " + mouvement.idCase + " " + mouvement.value);


                    //
                    MainWindowCluedo.getInstance().suppReponse("");
                });
            });

            socket.On("showSupposition", (data) =>
            {
                Application surface = System.Windows.Application.Current;
                surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
                {
                    Supposition supposition = data.Json.GetFirstArgAs<Supposition>();
                    MainWindowCluedo.lancementSupposition = false;

                    //carte
                    Uri personne = new Uri("Resources/personCard/" + supposition.perso.ToLower()+".png", UriKind.Relative);
                    Uri arme = new Uri("Resources/armCard/" + supposition.arme.ToLower()+".png", UriKind.Relative);
                    Uri lieu = new Uri("Resources/pieceCard/" + supposition.lieu.ToLower()+".png", UriKind.Relative);
                    MainWindowCluedo.getInstance().suppoPerson1.Source = new BitmapImage(personne);
                    MainWindowCluedo.getInstance().suppoArm1.Source = new BitmapImage(arme);
                    MainWindowCluedo.getInstance().suppoPiece1.Source = new BitmapImage(lieu);

                    MainWindowCluedo.getInstance().suppoPerson2.Source = new BitmapImage(personne);
                    MainWindowCluedo.getInstance().suppoArm2.Source = new BitmapImage(arme);
                    MainWindowCluedo.getInstance().suppoPiece2.Source = new BitmapImage(lieu);
                });
            });

            socket.On("retourTourChoixSupposition", (data) =>
            {
                Application surface = System.Windows.Application.Current;
                surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
                {
                    MainWindowCluedo.lancementSupposition = true;

                    MainWindowCluedo.tagListEnvoye = false;

                    Sounds.Play(EnumSound.LANCESUPPOSITION);
                });
            });

            socket.On("carteEnvoye", (data) =>
            {
                Application surface = System.Windows.Application.Current;
                surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
                {
                    QuiAEnvoyeLaCarte quiEnvoie = data.Json.GetFirstArgAs<QuiAEnvoyeLaCarte>();
                    string str = quiEnvoie.nameJoueur + " a envoyé une carte";
                    MainWindowCluedo.getInstance().suppReponse(str);
                });
            });

            socket.On("carteNonEnvoye", (data) =>
            {
                Application surface = System.Windows.Application.Current;
                surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
                {
                    string str = "Aucune carte reçu";
                    MainWindowCluedo.getInstance().suppReponse(str);
                });
            });

            socket.On("validerCaseTable", (data) =>
            {
                Application surface = System.Windows.Application.Current;
                surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
                {
                    if (MainWindowCluedo.dansUneSalle)
                    {
                        tourChoixSupposition(MainWindowCluedo.idCasePoseTemporaire);

                        Console.WriteLine("idCasePoseTemporaire envoyé: " + MainWindowCluedo.idCasePoseTemporaire);
                    }
                    else {
                        tourTermine(MainWindowCluedo.idCasePoseTemporaire);

                        Console.WriteLine("idCasePoseTemporaire envoyé: " + MainWindowCluedo.idCasePoseTemporaire);
                    }
                });
            });

            // make the socket.io connection
            socket.Connect();
        }
    }
}