﻿using System;
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
        }

        public static void tourChoixAccusation(String idCase)
        {
            socket.Emit("tourChoixAccusation", idCase);
        }

        public static void tourTermine(String idCase)
        {
            socket.Emit("tourTermine", idCase);
        }

        public static void tagsDansPiece(ArrayList tags) {
            for (int i = 0; i < tags.Count; i++)
            {
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
                    Console.WriteLine("Test: " + mouvement.idJoueur + " " + mouvement.idCase + " " + mouvement.value);
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
                    Uri personne = new Uri("Resources/personHead/" + supposition.perso.ToLower()+".jpg", UriKind.Relative);
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
                });
            });


            // make the socket.io connection
            socket.Connect();
        }
    }
}