using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Threading;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Media.Animation;
using Microsoft.Surface;
using Microsoft.Surface.Presentation;
using Microsoft.Surface.Presentation.Controls;
using Microsoft.Surface.Presentation.Input;
using System.Net;
using Gma.QrCodeNet.Encoding.Windows.Render;
using Gma.QrCodeNet.Encoding;
using Gma.QrCodeNet.Encoding.Windows.WPF;
using System.IO;
using System.Diagnostics;

namespace Cluedo
{

    /// <summary>
    /// Interaction logic for SurfaceWindow1.xaml
    /// </summary>
    public partial class SurfaceWindow1 : SurfaceWindow
    {

        private int nbJoueurs = 0;
        public int NbJoueurs
        {
            get { return nbJoueurs; }
            set { nbJoueurs = value; }
        }

        // Identifiant du joueur entrain de jouer
        private int idJoueurCourant = -1;
        public int IdJoueurCourant
        {
            get { return idJoueurCourant; }
            set { idJoueurCourant = value; }
        }
        // En préparation de la partie
        private bool enPreparation = false;
        public bool EnPreparation
        {
            get { return enPreparation; }
            set { enPreparation = value; }
        }
        // Case vers ou déplacer le pion
        private int idCaseCourante = -1;
        public int IdCaseCourante
        {
            get { return idCaseCourante; }
            set { idCaseCourante = value; }
        }

        private bool deplaceTonPion = false;
        public bool DeplaceTonPion
        {
            get { return deplaceTonPion; }
            set { deplaceTonPion = value; }
        }

        private bool pionDejaDeplace = false;
        public bool PionDejaDeplace
        {
            get { return pionDejaDeplace; }
            set { pionDejaDeplace = value; }
        }


        private bool isConnectToServeur = false;
        public bool IsConnectToServeur
        {
            get { return isConnectToServeur; }
            set { isConnectToServeur = value; }
        }


        public static SurfaceWindow1 instance;
        private ColorAnimation indiquerCaseAnimation, indiquerJoueurAnimation, retablirJoueurAnimation,retablirMeriteAnimation;
        private ColorAnimation tagPoseAnimation, lancerPartieBtnAnimation,sommesMeriteAnimation;
        private Color[] joueursCouleurs;
        private Dictionary<string, string> tagCouleurs;
        private bool[] associated;
        private static Color textColor = Colors.White;
        public static Color zoneJoueurColor = Colors.Gray;
        public static Color polytechColor = (Color)ColorConverter.ConvertFromString("#009DE1");

        /// <summary>
        /// Adds handlers for window availability events.
        /// </summary>
        private void AddWindowAvailabilityHandlers()
        {
            // Subscribe to surface window availability events
            ApplicationServices.WindowInteractive += OnWindowInteractive;
            ApplicationServices.WindowNoninteractive += OnWindowNoninteractive;
            ApplicationServices.WindowUnavailable += OnWindowUnavailable;
        }

        /// <summary>
        /// Removes handlers for window availability events.
        /// </summary>
        private void RemoveWindowAvailabilityHandlers()
        {
            // Unsubscribe from surface window availability events
            ApplicationServices.WindowInteractive -= OnWindowInteractive;
            ApplicationServices.WindowNoninteractive -= OnWindowNoninteractive;
            ApplicationServices.WindowUnavailable -= OnWindowUnavailable;
        }

        /// <summary>
        /// Default constructor.
        /// </summary>
        public SurfaceWindow1()
        {
            InitializeComponent();

            // Add handlers for window availability events
            AddWindowAvailabilityHandlers();
            this.textShowIp.Text = LocalIPAddress();

            //Cache le titre Monopolytech 
            changerVisibilite(this.titreMonopolytech, false);

            //Cache la bourse au mérite
            changerVisibilite(this.gridMerite, false);

            zoneIp.Fill = new SolidColorBrush(polytechColor);
            sommeMeriteZone.Fill = new SolidColorBrush(polytechColor);
           
            // Couleurs des zones des joueurs
            rectangleJoueur1.Fill = new SolidColorBrush(zoneJoueurColor);
            rectangleJoueur2.Fill = new SolidColorBrush(zoneJoueurColor);
            rectangleJoueur3.Fill = new SolidColorBrush(zoneJoueurColor);
            rectangleJoueur4.Fill = new SolidColorBrush(zoneJoueurColor);

            // Couleurs du texte des joueurs
            pseudoJoueur1.Foreground = new SolidColorBrush(textColor);
            pseudoJoueur2.Foreground = new SolidColorBrush(textColor);
            pseudoJoueur3.Foreground = new SolidColorBrush(textColor);
            pseudoJoueur4.Foreground = new SolidColorBrush(textColor);
            instructionJoueur1.Foreground = new SolidColorBrush(textColor);
            instructionJoueur2.Foreground = new SolidColorBrush(textColor);
            instructionJoueur3.Foreground = new SolidColorBrush(textColor);
            instructionJoueur4.Foreground = new SolidColorBrush(textColor);

            // Initialisation des animations
            initAnimations();

            // Initialisation du tableau de couleurs des joueurs
            joueursCouleurs = new Color[5];
            // Début de partie (animation pour la case départ)
            joueursCouleurs[4] = polytechColor;

            // Initialisation du tableau de couleurs des tags
            tagCouleurs = new Dictionary<string, string>();
            tagCouleurs["0xa6"] = "#FF0000";
            tagCouleurs["0xb5"] = "#FFFF00";
            tagCouleurs["0x1"] = "#0000FF";
            tagCouleurs["0x20"] = "#008000";

            // Initialisation du tableau des booleens associations
            associated = new bool[4];
            associated[0] = false;
            associated[1] = false;
            associated[2] = false;
            associated[3] = false;

            // Affiche le QRCode
            ShowQrcode(this.textShowIp.Text);

            // Initialise le singleton
            instance = this;

            // Lance le serveur
            startServer();

            // Lance socketIO
            SocketIO.Execute();
            IsConnectToServeur = true;
       }

        /*réinitalise la table aprè la fin d'une partie*/
        public void initialiseTable()
        {
            IsConnectToServeur = false;
            this.textShowIp.Visibility = System.Windows.Visibility.Visible;
            this.zoneIp.Visibility = System.Windows.Visibility.Visible;
            //Cache le titre Monopolytech 
            changerVisibilite(this.titreMonopolytech, false);
            //Cache la bourse au mérite
            changerVisibilite(this.gridMerite, false);
            //reapparaitre le qrcode
            apparaitreQrcode();

            //remettre l'instruction sur le zone JOUEUR
            pseudoJoueur1.Text = "JOUEUR 1";
            pseudoJoueur2.Text = "JOUEUR 2";
            pseudoJoueur3.Text = "JOUEUR 3";
            pseudoJoueur4.Text = "JOUEUR 4";
            instructionJoueur1.Text = "(en attente d'un joueur)";
            instructionJoueur2.Text = "(en attente d'un joueur)";
            instructionJoueur3.Text = "(en attente d'un joueur)";
            instructionJoueur4.Text = "(en attente d'un joueur)";

            //remettre la coulor de zoneJoueur et les fait tout apparaitre
            zoneJoueur1.Fill = new SolidColorBrush(zoneJoueurColor);
            zoneJoueur2.Fill = new SolidColorBrush(zoneJoueurColor);
            zoneJoueur3.Fill = new SolidColorBrush(zoneJoueurColor);
            zoneJoueur4.Fill = new SolidColorBrush(zoneJoueurColor);

            pseudoJoueur1.Visibility = System.Windows.Visibility.Visible;
            pseudoJoueur2.Visibility = System.Windows.Visibility.Visible;
            pseudoJoueur3.Visibility = System.Windows.Visibility.Visible;
            pseudoJoueur4.Visibility = System.Windows.Visibility.Visible;
            instructionJoueur1.Visibility = System.Windows.Visibility.Visible;
            instructionJoueur2.Visibility = System.Windows.Visibility.Visible;
            instructionJoueur3.Visibility = System.Windows.Visibility.Visible;
            instructionJoueur4.Visibility = System.Windows.Visibility.Visible;
            rectangleJoueur1.Visibility = System.Windows.Visibility.Visible;
            rectangleJoueur2.Visibility = System.Windows.Visibility.Visible;
            rectangleJoueur3.Visibility = System.Windows.Visibility.Visible;
            rectangleJoueur4.Visibility = System.Windows.Visibility.Visible;

            //enleve tout les marques de propriété    
            retablirZoneProprio(1);
            retablirZoneProprio(3);
            retablirZoneProprio(5);
            retablirZoneProprio(8);
            retablirZoneProprio(9);
            retablirZoneProprio(11);
            retablirZoneProprio(14);
            retablirZoneProprio(16);
            retablirZoneProprio(17);
            retablirZoneProprio(20);
            retablirZoneProprio(21);
            retablirZoneProprio(23);

            //remettre zone merite à 0
            sommeMerite.Text = "0 €";
            //cacher le carré qui indique la couleur de joueur
            couleurJoueur1.Visibility = System.Windows.Visibility.Hidden;
            couleurJoueur2.Visibility = System.Windows.Visibility.Hidden;
            couleurJoueur3.Visibility = System.Windows.Visibility.Hidden;
            couleurJoueur4.Visibility = System.Windows.Visibility.Hidden;

            text_bt_lance.Text = "CONNEXION AU SERVEUR";
            bt_lancePartie.Visibility = System.Windows.Visibility.Visible;

            retablirCaseCourante(); 
        }



        private void startServer()
        {
            Process myProcess = new Process();
            myProcess.StartInfo.WindowStyle = ProcessWindowStyle.Hidden;
            myProcess.StartInfo.CreateNoWindow = false;
            myProcess.StartInfo.UseShellExecute = false;
            myProcess.StartInfo.FileName = "cmd.exe";
            myProcess.StartInfo.Arguments = "/c cd ../../../Serveur & node server.js";
            myProcess.EnableRaisingEvents = true;
            myProcess.Start();
        }


        public void animateZoneMerite()
        {
            SolidColorBrush colorBrush = new SolidColorBrush();
            colorBrush.BeginAnimation(SolidColorBrush.ColorProperty, sommesMeriteAnimation);
            sommeMeriteZone.Fill = colorBrush;
        }

        private void initAnimations()
        {
            indiquerCaseAnimation = new ColorAnimation();
            indiquerCaseAnimation.From = polytechColor;
            indiquerCaseAnimation.To = Colors.Transparent;
            indiquerCaseAnimation.Duration = new Duration(TimeSpan.FromSeconds(0.8));
            indiquerCaseAnimation.AutoReverse = true;
            indiquerCaseAnimation.RepeatBehavior = RepeatBehavior.Forever;

            indiquerJoueurAnimation = new ColorAnimation();
            indiquerJoueurAnimation.From = zoneJoueurColor;
            indiquerJoueurAnimation.To = polytechColor;
            indiquerJoueurAnimation.Duration = new Duration(TimeSpan.FromSeconds(1));

            retablirJoueurAnimation = new ColorAnimation();
            retablirJoueurAnimation.From = polytechColor;
            retablirJoueurAnimation.To = zoneJoueurColor;
            retablirJoueurAnimation.Duration = new Duration(TimeSpan.FromSeconds(1));

            tagPoseAnimation = new ColorAnimation();
            tagPoseAnimation.From = Colors.White;
            tagPoseAnimation.To = zoneJoueurColor;
            tagPoseAnimation.Duration = new Duration(TimeSpan.FromSeconds(0.8));


            lancerPartieBtnAnimation = new ColorAnimation();
            lancerPartieBtnAnimation.From = Colors.White;
            lancerPartieBtnAnimation.To = polytechColor;
            lancerPartieBtnAnimation.Duration = new Duration(TimeSpan.FromSeconds(0.5));
            lancerPartieBtnAnimation.AutoReverse = true;
            lancerPartieBtnAnimation.RepeatBehavior = RepeatBehavior.Forever;


            retablirMeriteAnimation = new ColorAnimation();
            retablirMeriteAnimation.From = polytechColor;
            retablirMeriteAnimation.To = polytechColor;
            retablirMeriteAnimation.Duration = new Duration(TimeSpan.FromSeconds(0.5));


            sommesMeriteAnimation = new ColorAnimation();
            sommesMeriteAnimation.From = Colors.Red;
            sommesMeriteAnimation.To = polytechColor;
            sommesMeriteAnimation.Duration = new Duration(TimeSpan.FromSeconds(0.5));
            sommesMeriteAnimation.AutoReverse = true;
            sommesMeriteAnimation.RepeatBehavior = RepeatBehavior.Forever;
        }

        public static SurfaceWindow1 getInstance()
        {
            if (instance == null)
                instance = new SurfaceWindow1();
            return instance;
        }

        public void launchTagAnimation(Rectangle zone, Color toColor)
        {
            SolidColorBrush colorBrush = new SolidColorBrush();
            tagPoseAnimation.To = toColor;
            colorBrush.BeginAnimation(SolidColorBrush.ColorProperty, tagPoseAnimation);
            zone.Fill = colorBrush;
        }

        public void launchAnimationDebutPartie()
        {
            double k = 0;
            for (int i = 0; i <= 23; i++)
            {
                SolidColorBrush colorBrush1 = new SolidColorBrush();
                Rectangle zone = getZoneCaseFromName(i);

                ColorAnimation indiquerCaseChangeAnimation = new ColorAnimation();
                indiquerCaseChangeAnimation.From = polytechColor;
                indiquerCaseChangeAnimation.To = Colors.Transparent;
                indiquerCaseChangeAnimation.BeginTime = TimeSpan.FromSeconds(0.2 * k);
                indiquerCaseChangeAnimation.Duration = new Duration(TimeSpan.FromSeconds(0.2));

                colorBrush1.BeginAnimation(SolidColorBrush.ColorProperty, indiquerCaseChangeAnimation);
                zone.Fill = colorBrush1;

                k += 1;
            }
        }

        public void launchChangerCaseAnimation(int idNextCase)
        {
           // initialiseTable();
            double k = 0;
            int i = idCaseCourante;
            while ((i%24) != idNextCase)
            {
                SolidColorBrush colorBrush1 = new SolidColorBrush();
                Rectangle zone = getZoneCaseFromName(i%24);

                ColorAnimation indiquerCaseChangeAnimation = new ColorAnimation();
                indiquerCaseChangeAnimation.From = joueursCouleurs[idJoueurCourant - 1];
                indiquerCaseChangeAnimation.To = Colors.Transparent;
                indiquerCaseChangeAnimation.BeginTime = TimeSpan.FromSeconds(0.8 * k);
                indiquerCaseChangeAnimation.Duration = new Duration(TimeSpan.FromSeconds(0.8));

                colorBrush1.BeginAnimation(SolidColorBrush.ColorProperty, indiquerCaseChangeAnimation);
                zone.Fill = colorBrush1;

                k += 0.7;
                i = i + 1;
            }

            idCaseCourante = idNextCase;

            // Récupère la zone a indiquer
            Rectangle zoneCase = getZoneCaseFromName(idCaseCourante);

            // Lance l'animation
            SolidColorBrush colorBrush3 = new SolidColorBrush();
            indiquerCaseAnimation.BeginTime = TimeSpan.FromSeconds(1 * k);
            indiquerCaseAnimation.From = joueursCouleurs[idJoueurCourant - 1];
            colorBrush3.BeginAnimation(SolidColorBrush.ColorProperty, indiquerCaseAnimation);
            zoneCase.Fill = colorBrush3;
        }

        private void cacherZoneSansJoueur()
        {
            for (int i = nbJoueurs; i < 4; i++)
            {
                switch (i + 1)
                {
                    case 1:
                        rectangleJoueur1.Visibility = Visibility.Hidden;
                        pseudoJoueur1.Visibility = Visibility.Hidden;
                        couleurJoueur1.Visibility = Visibility.Hidden;
                        instructionJoueur1.Visibility = Visibility.Hidden;
                        zoneTagJoueur1.Visibility = Visibility.Hidden;
                        break;
                    case 2:
                        rectangleJoueur2.Visibility = Visibility.Hidden;
                        pseudoJoueur2.Visibility = Visibility.Hidden;
                        couleurJoueur2.Visibility = Visibility.Hidden;
                        instructionJoueur2.Visibility = Visibility.Hidden;
                        zoneTagJoueur2.Visibility = Visibility.Hidden;
                        break;
                    case 3:
                        rectangleJoueur3.Visibility = Visibility.Hidden;
                        pseudoJoueur3.Visibility = Visibility.Hidden;
                        couleurJoueur3.Visibility = Visibility.Hidden;
                        instructionJoueur3.Visibility = Visibility.Hidden;
                        zoneTagJoueur3.Visibility = Visibility.Hidden;
                        break;
                    case 4:
                        rectangleJoueur4.Visibility = Visibility.Hidden;
                        pseudoJoueur4.Visibility = Visibility.Hidden;
                        couleurJoueur4.Visibility = Visibility.Hidden;
                        instructionJoueur4.Visibility = Visibility.Hidden;
                        zoneTagJoueur4.Visibility = Visibility.Hidden;
                        break;
                }
            }
        }

        private void ShowQrcode(string ip)
        {
            //control update text then encode and recreate Image.
            BitMatrix qrMatrix = this.qrCode.GetQrMatrix(); //Qr bit matrix for input string "QrCode.Net".

            this.qrCode.Lock();  //Lock class.
            this.qrCode.ErrorCorrectLevel = ErrorCorrectionLevel.M;  //It won't encode and recreate image.
            this.qrCode.Text = ip;
            qrMatrix = qrCode.GetQrMatrix(); //Qr bit matrix for input string "QrCode.Net".
            this.qrCode.QuietZoneModule = QuietZoneModules.Zero;  //Control will recreate image, but Bitmatrix is still for "QrCode.Net" input string. 
            this.qrCode.Unlock(); //Unlock class, re-encode and repaint. 
        }

        public void changerVisibilite(Grid textblock, bool afficher)
        {
            if (afficher)
                textblock.Visibility = System.Windows.Visibility.Visible;
            else
                textblock.Visibility = System.Windows.Visibility.Hidden;
        }

        public void changerVisibilite(TextBlock textblock, bool afficher)
        {
            if (afficher)
                textblock.Visibility = System.Windows.Visibility.Visible;
            else
                textblock.Visibility = System.Windows.Visibility.Hidden;
        }

        public void changerVisibilite(Rectangle rectangle, bool afficher)
        {
            if (afficher)
                rectangle.Visibility = System.Windows.Visibility.Visible;
            else
                rectangle.Visibility = System.Windows.Visibility.Hidden;
        }

        public void cacherQrCode()
        {
            this.qrCode.Visibility = System.Windows.Visibility.Hidden;
            this.qrCodeInstruction.Visibility = System.Windows.Visibility.Hidden;
            this.qrCodeRectangle.Visibility = System.Windows.Visibility.Hidden;
        }

        public void apparaitreQrcode()
        {
            this.qrCode.Visibility = System.Windows.Visibility.Visible;
            this.qrCodeInstruction.Visibility = System.Windows.Visibility.Visible;
            this.qrCodeRectangle.Visibility = System.Windows.Visibility.Visible;
        }


        /// <summary>
        /// Met en valeur une case du plateau.
        /// </summary>
        /// <param name="nomCase">Nom de la case à mettre en valeur</param>
        public void setPseudoJoueur(string pseudo, int idJoueur)
        {
            switch (idJoueur)
            {
                case 1:
                    this.pseudoJoueur1.Text = pseudo;
                    break;
                case 2:
                    this.pseudoJoueur2.Text = pseudo;
                    break;
                case 3:
                    this.pseudoJoueur3.Text = pseudo;
                    break;
                case 4:
                    this.pseudoJoueur4.Text = pseudo;
                    break;
            }
        }

        public void broadcastInstruction(string instruction)
        {
            setInstructionJoueur(instruction, 1);
            setInstructionJoueur(instruction, 2);
            setInstructionJoueur(instruction, 3);
            setInstructionJoueur(instruction, 4);
        }

        /// <summary>
        /// Met à jour les instructions d'un joueur
        /// </summary>
        /// <param name="nomCase">Nom de la case à mettre en valeur</param>
        public void setInstructionJoueur(string instruction, int idJoueur)
        {
            switch (idJoueur)
            {
                case 1:
                    instructionJoueur1.Text = instruction;
                    break;
                case 2:
                    instructionJoueur2.Text = instruction;
                    break;
                case 3:
                    instructionJoueur3.Text = instruction;
                    break;
                case 4:
                    instructionJoueur4.Text = instruction;
                    break;
            }
        }

        /// <summary>
        /// Met en valeur une zone de joueur.
        /// </summary>
        /// <param name="idJoueur">Identifiant du joueur</param>
        public void indiquerZoneJoueur(int idJoueur)
        {
            retablirZoneJoueur(idJoueurCourant);

            Console.WriteLine("indiquer zone joueur: " + idJoueur.ToString());
            Rectangle zoneJoueur = getZoneJoueurFromName(idJoueur);
            
            SolidColorBrush colorBrush = new SolidColorBrush();
            colorBrush.BeginAnimation(SolidColorBrush.ColorProperty, indiquerJoueurAnimation);
            zoneJoueur.Fill = colorBrush;
        }

        /// <summary>
        /// Rétablis l'apparence par défaut d'une zone de joueur.
        /// </summary>
        /// <param name="idJoueur">Identifiant du joueur</param>
        public void retablirZoneJoueur(int idJoueur)
        {
            Console.WriteLine("rétablir zone joueur: " + idJoueur.ToString());
            Rectangle zoneJoueur = getZoneJoueurFromName(idJoueur);

            if (zoneJoueur != null)
            {
                SolidColorBrush colorBrush = new SolidColorBrush();
                colorBrush.BeginAnimation(SolidColorBrush.ColorProperty, retablirJoueurAnimation);
                zoneJoueur.Fill = colorBrush;
            }
        }

        /// <summary>
        /// Met en valeur la case courante
        /// </summary>
        public void indiquerCaseCourante()
        {
            // Récupère la zone a indiquer
            Rectangle zoneCase = getZoneCaseFromName(idCaseCourante);

            // Lance l'animation
            SolidColorBrush colorBrush = new SolidColorBrush();
            indiquerCaseAnimation.From = joueursCouleurs[idJoueurCourant - 1];
            colorBrush.BeginAnimation(SolidColorBrush.ColorProperty, indiquerCaseAnimation);
            zoneCase.Fill = colorBrush;
        }

        /// <summary>
        /// Rétablis l'apparence par défaut de la case courante.
        /// </summary>
        public void retablirCaseCourante()
        {
            Console.WriteLine("retablir case courante: " + idCaseCourante);
            Rectangle zoneCase = getZoneCaseFromName(idCaseCourante);
            zoneCase.Fill = new SolidColorBrush((Color)ColorConverter.ConvertFromString("Transparent"));
        }

        /// <summary>
        /// Retourne l'élément graphique d'une case du plateau.
        /// </summary>
        /// <param name="idCase">Identifiant de la case à chercher dans le plateau</param>
        /// <returns>Retourne le rectangle associé à la case dans le xaml</returns>
        public Rectangle getZoneCaseFromName(int idCase)
        {
            switch (idCase)
            {
                case 0:
                    return this.case0;
                case 1:
                    return this.case1;
                case 2:
                    return this.case2;
                case 3:
                    return this.case3;
                case 4:
                    return this.case4;
                case 5:
                    return this.case5;
                case 6:
                    return this.case6;
                case 7:
                    return this.case7;
                case 8:
                    return this.case8;
                case 9:
                    return this.case9;
                case 10:
                    return this.case10;
                case 11:
                    return this.case11;
                case 12:
                    return this.case12;
                case 13:
                    return this.case13;
                case 14:
                    return this.case14;
                case 15:
                    return this.case15;
                case 16:
                    return this.case16;
                case 17:
                    return this.case17;
                case 18:
                    return this.case18;
                case 19:
                    return this.case19;
                case 20:
                    return this.case20;
                case 21:
                    return this.case21;
                case 22:
                    return this.case22;
                case 23:
                    return this.case23;
            }

            return null;
        }

        /// <summary>
        /// Retourne l'élément graphique d'une zone de joueur.
        /// </summary>
        /// <param name="idJoueur">Identifiant d'un joueur</param>
        /// <returns>Retourne le rectangle associé à la zone du joueur dans le xaml</returns>
        public Rectangle getZoneJoueurFromName(int idJoueur)
        {
            switch (idJoueur)
            {
                case 1:
                    return this.zoneJoueur1;
                case 2:
                    return this.zoneJoueur2;
                case 3:
                    return this.zoneJoueur3;
                case 4:
                    return this.zoneJoueur4;
            }

            return null;
        }

        /// <summary>
        /// Met en valeur une zone proprio d'une école.
        /// </summary>
        /// <param name="idJoueur">Identifiant du joueur</param>
        public void indiquerZoneProprio(int idCase, string couleur)
        {
            Console.WriteLine("indiquer zone proprio: " + idCase.ToString());
            Ellipse zoneProprio = getZoneProprioFromCaseId(idCase);
            zoneProprio.Visibility = System.Windows.Visibility.Visible;

            Color color = (Color)ColorConverter.ConvertFromString(couleur);
            SolidColorBrush colorBrush = new SolidColorBrush(color);
            zoneProprio.Fill = colorBrush;
        }

        /// <summary>
        /// Rétablie l'apparence par défaut d'une zone proprio d'une école.
        /// </summary>
        /// <param name="idCase">Identifiant de la case</param>
        public void retablirZoneProprio(int idCase)
        {
            Console.WriteLine("rétablir zone proprio: " + idCase.ToString());
            Ellipse zoneProprio = getZoneProprioFromCaseId(idCase);
            zoneProprio.Visibility = System.Windows.Visibility.Hidden;
        }

        /// <summary>
        /// Retourne l'élément graphique (Ellipse) d'une zone de propriétaire d'école.
        /// </summary>
        /// <param name="idCase">Id de la case</param>
        /// <returns>Retourne l'Ellipse associée à la zone du propriétaire de l'école dans le xaml</returns>
        public Ellipse getZoneProprioFromCaseId(int idCase)
        {
            switch (idCase)
            {
                case 1:
                    return this.proprio1;
                case 3:
                    return this.proprio3;
                case 5:
                    return this.proprio5;
                case 8:
                    return this.proprio8;
                case 9:
                    return this.proprio9;
                case 11:
                    return this.proprio11;
                case 14:
                    return this.proprio14;
                case 16:
                    return this.proprio16;
                case 17:
                    return this.proprio17;
                case 20:
                    return this.proprio20;
                case 21:
                    return this.proprio21;
                case 23:
                    return this.proprio23;
                
            }

            return null;
        }

        private string LocalIPAddress()
        {
            IPHostEntry host;
            string localIP = "";
            host = Dns.GetHostEntry(Dns.GetHostName());
            foreach (IPAddress ip in host.AddressList)
            {
                if (ip.AddressFamily == System.Net.Sockets.AddressFamily.InterNetwork)
                {
                    localIP = ip.ToString();
                    break;
                }
            }
            return localIP;
        }

        private void tagVisualizer_VisualizationAdded(object sender, TagVisualizerEventArgs e)
        {
            if (!EnPreparation)
            {
                string tag = "0x" + Convert.ToString(e.TagVisualization.VisualizedTag.Value, 16);
                int idJoueur = int.Parse(((TagVisualizer)e.OriginalSource).DataContext.ToString());
                Console.WriteLine("tag: " + tag);
                Console.WriteLine("idJoueur: " + idJoueur);

                if (!associated[idJoueur - 1])
                {
                    SocketIO.AssocierTag(idJoueur, tagCouleurs[tag]);
                    associated[idJoueur - 1] = true;
                }
                else
                {
                    Rectangle zoneJoueur = getZoneJoueurFromName(idJoueur);
                    launchTagAnimation(zoneJoueur, zoneJoueurColor);
                }

                // Si le tag est valide
                if (tagCouleurs.ContainsKey(tag))
                {
                    switch (idJoueur)
                    {
                        case 1:
                            couleurJoueur1.Fill = new SolidColorBrush((Color)ColorConverter.ConvertFromString(tagCouleurs[tag]));
                            break;
                        case 2:
                            couleurJoueur2.Fill = new SolidColorBrush((Color)ColorConverter.ConvertFromString(tagCouleurs[tag]));
                            break;
                        case 3:
                            couleurJoueur3.Fill = new SolidColorBrush((Color)ColorConverter.ConvertFromString(tagCouleurs[tag]));
                            break;
                        case 4:
                            couleurJoueur4.Fill = new SolidColorBrush((Color)ColorConverter.ConvertFromString(tagCouleurs[tag]));
                            break;
                    }

                    // Met à jour la couleur du joueur
                    joueursCouleurs[idJoueur - 1] = (Color)ColorConverter.ConvertFromString(tagCouleurs[tag]);
                }
            }
        }

        public void afficherBtnLancerPartie()
        {
            bt_lancePartie.Visibility = Visibility.Visible;
            SolidColorBrush colorBrush = new SolidColorBrush();
            colorBrush.BeginAnimation(SolidColorBrush.ColorProperty, lancerPartieBtnAnimation);
            rectangleBtnLancerPartie.Fill = colorBrush;
        }

        private void movePion(object sender, TagVisualizerEventArgs e)
        {
            string idCase = ((TagVisualizer)e.OriginalSource).DataContext.ToString();
            Rectangle zoneCase = getZoneCaseFromName(Convert.ToInt32(idCase));

            if (EnPreparation && idCase == "0")
            {
                SocketIO.PionPret();
            }
            else if (idCaseCourante.ToString() == idCase && deplaceTonPion)
            {
                launchTagAnimation(zoneCase, joueursCouleurs[idJoueurCourant - 1]);
                SocketIO.PionDeplace();
            }
        }

        /// <summary>
        /// Occurs when the window is about to close. 
        /// </summary>
        /// <param name="e"></param>
        protected override void OnClosed(EventArgs e)
        {
            base.OnClosed(e);

            // Remove handlers for window availability events
            RemoveWindowAvailabilityHandlers();
        }

        /// <summary>
        /// This is called when the user can interact with the application's window.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OnWindowInteractive(object sender, EventArgs e)
        {
            //TODO: enable audio, animations here
        }

        /// <summary>
        /// This is called when the user can see but not interact with the application's window.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OnWindowNoninteractive(object sender, EventArgs e)
        {
            //TODO: Disable audio here if it is enabled   
        }

        /// <summary>
        /// This is called when the application's window is not visible or interactive.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OnWindowUnavailable(object sender, EventArgs e)
        {
            //TODO: disable audio, animations here
        }


        private void LancePartie_click(object sender, RoutedEventArgs e)
        {
            if (IsConnectToServeur)
            {
                SocketIO.ForcerDebutPartie();
                bt_lancePartie.Visibility = System.Windows.Visibility.Hidden;
                cacherZoneSansJoueur();
            }
            else
            {
                bt_lancePartie.Visibility = System.Windows.Visibility.Hidden;
                SocketIO.Execute();
                IsConnectToServeur = true;
                text_bt_lance.Text = "LANCER LA PARTIE";
            }
        }


        public void ChangeMeriteSommes(int nouvellesSommes)
        {
            sommeMerite.Text = nouvellesSommes.ToString()+" €";
        }

        public void StopAnimateSommes()
        {
            SolidColorBrush colorBrush = new SolidColorBrush();
            colorBrush.BeginAnimation(SolidColorBrush.ColorProperty, retablirMeriteAnimation);
            this.sommeMeriteZone.Fill = colorBrush;
        }
    

   
    }
}