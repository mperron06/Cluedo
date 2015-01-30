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

/*definition of the class used to convert to json objet, which is passed between the client and the server*/
public class Joueur
{
    public int idJoueur { get; set; }
    public string persoName { get; set; }
}

public class ChoixMovement
{
    public int idJoueur { get; set; }
    public string idCase { get; set; }
    public int value { get; set; }
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

       
        public static void PionPose(String idCase) {
            socket.Emit("pionPose", idCase);
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

            /**
             * Recois la connexion d'un nouveau joueur
             *  @param  data['id_joueur']: identifiant du joueur
             *          data['pseudo']: pseudo du joueur
             */
           /* socket.On("nouveauJoueur", (data) =>
            {
                Console.WriteLine("recv [socket].[nouveauJoueur] event");

                NouveauJoueur nouveauJoueur = data.Json.GetFirstArgAs<NouveauJoueur>();
                int idJoueur = nouveauJoueur.IdJoueur + 1;
                string pseudoJoueur = nouveauJoueur.PseudoJoueur;

                Application surface = System.Windows.Application.Current;
                surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
                {
                    SurfaceWindow1.getInstance().NbJoueurs += 1;
                    SurfaceWindow1.getInstance().setPseudoJoueur(pseudoJoueur, idJoueur);
                    SurfaceWindow1.getInstance().broadcastInstruction("(attend d'autre joueurs)");

                    if (SurfaceWindow1.getInstance().NbJoueurs == 2)
                        SurfaceWindow1.getInstance().afficherBtnLancerPartie();
                });
            });*/


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
                });
            });
            

            // make the socket.io connection
            socket.Connect();
        }
    }
}