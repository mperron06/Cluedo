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
public class AssocierTag
{
    public int idJoueur;
    public string tag;
}

public class NouveauJoueur
{
    public int IdJoueur { get; set; }
    public string PseudoJoueur { get; set; }
}

public class TransactionEcole
{
    public string Couleur { get; set; }
    public int IdCase { get; set; }
}

public class CaseVendue
{
    public int IdJoueur { get; set; }
    public int IdCase { get; set; }
}

public class PasseTour
{
    public int IdJoueur { get; set; }
}

public class EnExam
{
    public int IdJoueur { get; set; }
    public bool EnExams { get; set; }
}

public class ProchainJoueur
{
    public int IdJoueur { get; set; }
    public int IdCase { get; set; }
}

public class ChoixPion
{
    public string Id { get; set; }
    public string Name { get; set; }
    public string Tag { get; set; }
    public int Argent { get; set; }
    public int NumCase { get; set; }
    public string[] Proprietes { get; set; }
    public string[] Cartes { get; set; }
    public int NbTours { get; set; }
    public bool PasseTour { get; set; }
    public bool EnExams { get; set; }
    public int NbToursExams { get; set; }
}

public class CaseChangee
{
    public int IdJoueur { get; set; }
    public int IdCase { get; set; }
}

/*fin of the definition*/

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

        public static void AssocierTag(int idJoueur, string couleur)
        {
            string json = JsonConvert.SerializeObject(new AssocierTag
            {
                idJoueur = idJoueur - 1,
                tag = couleur
            }, Formatting.Indented);

            socket.Emit("associerTag", json.ToString());

            Application surface = System.Windows.Application.Current;
            surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
            {
                Rectangle zoneJoueur = SurfaceWindow1.getInstance().getZoneJoueurFromName(idJoueur);
                SurfaceWindow1.getInstance().launchTagAnimation(zoneJoueur, SurfaceWindow1.zoneJoueurColor);
                SurfaceWindow1.getInstance().setInstructionJoueur("(en attente d'instructions)", idJoueur);
            });
        }

        public static void PionDeplace()
        {
            Application surface = System.Windows.Application.Current;
            surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
            {
                SurfaceWindow1.getInstance().setInstructionJoueur("(en attente d'instructions)", SurfaceWindow1.getInstance().IdJoueurCourant);
                SurfaceWindow1.getInstance().DeplaceTonPion = false;
            });
            socket.Emit("pionDeplace", null);
        }

        public static void PionPret()
        {
            socket.Emit("pionPret", null);
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
            socket.On("nouveauJoueur", (data) =>
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
            });

            /**
             * Demande a l'ensemble des joueurs d'associer leur pion en posant leur tag dans leur zone réservée
             *  @return data['id_joueur']: identifiant du joueur commencant la partie
             */
            socket.On("choixPions", (data) =>
            {
                Console.WriteLine("recv [socket].[choixPions] event");

                Application surface = System.Windows.Application.Current;
                surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
                {
                    SurfaceWindow1.getInstance().changerVisibilite(SurfaceWindow1.getInstance().textShowIp, false);
                    SurfaceWindow1.getInstance().changerVisibilite(SurfaceWindow1.getInstance().zoneIp, false);
                    SurfaceWindow1.getInstance().broadcastInstruction("(associe ton pion dans ta zone)");
                    SurfaceWindow1.getInstance().bt_lancePartie.Visibility = Visibility.Hidden;
                });
            });

            /**
             * Demande a l'ensemble des joueurs d'associer leur pion en posant leur tag dans leur zone réservée
             *  @return data['id_joueur']: identifiant du joueur commencant la partie
             */
            socket.On("transactionEcole", (data) =>
            {
                Console.WriteLine("recv [socket].[transactionEcole] event");

                TransactionEcole transactionEcole = data.Json.GetFirstArgAs<TransactionEcole>();
                string couleur = transactionEcole.Couleur;
                int idCase = transactionEcole.IdCase;

                Application surface = System.Windows.Application.Current;
                surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
                {
                    SurfaceWindow1.getInstance().indiquerZoneProprio(idCase, couleur);
                });
            });

            /**
             * Demande a l'ensemble des joueurs d'associer leur pion en posant leur tag dans leur zone réservée
             *  @return data['id_joueur']: identifiant du joueur commencant la partie
             */
            socket.On("caseVendue", (data) =>
            {
                Console.WriteLine("recv [socket].[caseVendue] event");

                CaseVendue caseVendue = data.Json.GetFirstArgAs<CaseVendue>();
                int idJoueur = caseVendue.IdJoueur + 1;
                int idCase = caseVendue.IdCase;

                Application surface = System.Windows.Application.Current;
                surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
                {
                    SurfaceWindow1.getInstance().retablirZoneProprio(idCase);
                });
            });

            /**
             * Indique le joueur commencant la partie (PAS SUR QUE CE SOIR NECESSAIRE, POURQUOI PAS UTILISER prochainJoueur ???
             *  @return data['id_joueur']: identifiant du joueur commencant la partie
             */
            socket.On("debutPartie", (data) =>
            {
                Console.WriteLine("recv [socket].[debutPartie] event");

                ProchainJoueur premierJoueur = data.Json.GetFirstArgAs<ProchainJoueur>();
                int idJoueur = premierJoueur.IdJoueur + 1;
                int idCase = premierJoueur.IdCase;

                Console.WriteLine(idJoueur + " commence la partie");
                
                Application surface = System.Windows.Application.Current;
                surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
                {

                    // Animation de début de partie
                    SurfaceWindow1.getInstance().cacherQrCode();
                    SurfaceWindow1.getInstance().changerVisibilite(SurfaceWindow1.getInstance().titreMonopolytech, true);
                    SurfaceWindow1.getInstance().changerVisibilite(SurfaceWindow1.getInstance().gridMerite, true);
                    SurfaceWindow1.getInstance().IdCaseCourante = 0;
                    SurfaceWindow1.getInstance().IdJoueurCourant = 5;

                    SurfaceWindow1.getInstance().launchAnimationDebutPartie();

                    // Début partie
                    SurfaceWindow1.getInstance().indiquerZoneJoueur(idJoueur);
                    SurfaceWindow1.getInstance().broadcastInstruction("(attend ton tour)");
                    SurfaceWindow1.getInstance().setInstructionJoueur("(lance le dé)", idJoueur);

                    // Met à jour le joueur courant
                    SurfaceWindow1.getInstance().IdJoueurCourant = idJoueur;
                    // La partie a commencé, phase de préparation terminée
                    SurfaceWindow1.getInstance().EnPreparation = false;

                    SurfaceWindow1.getInstance().retablirCaseCourante();
                    // Met à jour la case courante (case actuelle du joueur avant son lancé de dé)
                    SurfaceWindow1.getInstance().IdCaseCourante = idCase;
                    SurfaceWindow1.getInstance().indiquerCaseCourante();


                });
            });

            /**
            * Indique que le joueur doit déplacer sont pion sur la case indiquée
            *  @return  data['id_joueur']: identifiant du joueur
            *           data['id_case']: identifiant de la case
            */
            socket.On("caseChangee", (data) =>
            {
                Console.WriteLine("recv [socket].[caseChangee] event");

                CaseChangee caseChangee = data.Json.GetFirstArgAs<CaseChangee>();
                int idJoueur = caseChangee.IdJoueur + 1;
                int idCase = caseChangee.IdCase;

                Console.WriteLine(idJoueur + " se déplace sur la case " + idCase);

                Application surface = System.Windows.Application.Current;
                surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
                {
                    if (idCase == 12)
                        SurfaceWindow1.getInstance().animateZoneMerite();
                   
                    if (!SurfaceWindow1.getInstance().PionDejaDeplace)
                    {
                        SurfaceWindow1.getInstance().launchChangerCaseAnimation(idCase);
                        SurfaceWindow1.getInstance().PionDejaDeplace = true;
                    }
                    else
                    {
                        SurfaceWindow1.getInstance().retablirCaseCourante();
                        SurfaceWindow1.getInstance().IdCaseCourante = idCase;
                        SurfaceWindow1.getInstance().indiquerCaseCourante();
                    }
                    SurfaceWindow1.getInstance().DeplaceTonPion = true;
                    SurfaceWindow1.getInstance().setInstructionJoueur("(déplace ton pion sur la case indiquée)", idJoueur);
                });
            });
            /**
             * indiquer la nouvelle sommes de Bourse
             * 
             **/
            socket.On("nouvelleBourse", (data) =>
            {
                Console.WriteLine("recv [socket].[nouvelleBourse] event");
                int nouvellesSommes = (int)data.Json.Args[0];
                Application surface = System.Windows.Application.Current;
                surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
                {
                    SurfaceWindow1.getInstance().ChangeMeriteSommes(nouvellesSommes);
                });
            });


            /**
            * Indique le prochain joueur a jouer
            *  @return  data['id_joueur']: identifiant du prochain joueur
            */
            socket.On("prochainJoueur", (data) =>
            {
                Console.WriteLine("recv [socket].[prochainJoueur] event");

                ProchainJoueur prochainJoueur = data.Json.GetFirstArgAs<ProchainJoueur>();
                int idJoueur = prochainJoueur.IdJoueur + 1;
                int idCase = prochainJoueur.IdCase;
                Console.WriteLine(idJoueur + " est le prochain joueur a jouer");

                Application surface = System.Windows.Application.Current;
                surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
                {
                    SurfaceWindow1.getInstance().PionDejaDeplace = false;
                    SurfaceWindow1.getInstance().StopAnimateSommes();
                    SurfaceWindow1.getInstance().indiquerZoneJoueur(idJoueur);
                    SurfaceWindow1.getInstance().setInstructionJoueur("(attend ton tour)", SurfaceWindow1.getInstance().IdJoueurCourant);
                    SurfaceWindow1.getInstance().setInstructionJoueur("(lance le dé)", idJoueur);

                    // Met à jour le joueur courant
                    SurfaceWindow1.getInstance().IdJoueurCourant = idJoueur;

                    SurfaceWindow1.getInstance().retablirCaseCourante();
                    // Met à jour la case courante (case actuelle du joueur avant son lancé de dé)
                    SurfaceWindow1.getInstance().IdCaseCourante = idCase;
                    SurfaceWindow1.getInstance().indiquerCaseCourante();
                });
            });

            socket.On("passeSonTour", (data) =>
            {
                Console.WriteLine("recv [socket].[passeSonTour] event");
                int idJoueur = (int)data.Json.Args[0] + 1;
                Console.WriteLine(idJoueur + " passe son tour");

                Application surface = System.Windows.Application.Current;
                surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
                {
                    SurfaceWindow1.getInstance().setInstructionJoueur("(tu sèches les cours et tu veux jouer? faut pas pousser mémé...)", idJoueur);
                });
            });


            socket.On("enExams", (data) =>
            {
                Console.WriteLine("recv [socket].[enExams] event");
                EnExam enExams = data.Json.GetFirstArgAs<EnExam>();
                int idJoueur = enExams.IdJoueur + 1;
                Console.WriteLine(idJoueur + " passe son tour (en exam)");

                Application surface = System.Windows.Application.Current;
                surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
                {
                    SurfaceWindow1.getInstance().setInstructionJoueur("(lance le dé et fais un 6 pour sortir d'exams!)", idJoueur);
                });
            });

            socket.On("partieTerminee", (data) =>
            {
                Console.WriteLine("recv [socket].[partieTerminee] event");
                Console.WriteLine("réinitialise la table");
                Application surface = System.Windows.Application.Current;
                surface.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, (Action)delegate
                {
                    SurfaceWindow1.getInstance().initialiseTable();
                    socket.Close();
                });
            });


            //adjouté
            socket.On("test", (data) =>
            {
                Console.WriteLine("serveur connecté hello world");
            });

            // make the socket.io connection
            socket.Connect();
        }
    }
}