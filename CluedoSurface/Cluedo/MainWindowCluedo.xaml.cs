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
    /// Logique d'interaction pour MainWindowCluedo.xaml
    /// </summary>
    public partial class MainWindowCluedo : SurfaceWindow
    {
        public static SurfaceWindow instance;

        static List<String> cases = new List<String> { "0.4", "0.5", "1.3", "1.4", "1.5", "1.6", "2.0", "2.1", "2.2", "2.3", "2.4", "2.5", "2.6", "2.7", "2.8", "3.1", "3.2", "3.3", "3.4", "3.5", "3.6", "3.7", "3.8", "3.9", "4.1", "4.2", "4.3", "4.4", "4.5", "4.6", "4.7", "4.8", "5.1", "5.2", "5.5", "5.7", "6.0", "6.1", "6.2", "6.3", "6.6", "6.7", "7.1", "7.2", "7.5", "7.7", "7.8", "8.1", "8.2", "8.3", "8.4", "8.5", "8.6", "8.7", "9.1", "9.2", "9.3", "9.4", "9.5", "9.6", "9.7", "10.2", "10.3", "10.4", "10.5", "10.6", "10.7", "10.8", "11.2", "11.3", "11.4", "11.5", "11.6", "11.7", "11.8", "11.9", "12.0", "12.1", "12.2", "12.3", "12.4", "13.1", "13.2", "13.3", "13.4", "13.5", "14.3" };
        static String[] GARAGE = { "2.0" };
        static String[] SALLEDEJEU = { "6.0" };
        static String[] CHAMBRE = { "12.0","14.0" };                      //???? 14.0
        static String[] SALLEDEBAIN = { "14.3","14.0" };
        static String[] BUREAU = { "13.5" };
        static String[] CUSINE = { "11.9" };
        static String[] SALLEAMANGER = { "7.8" };
        static String[] SALON = { "3.9" };
        static String[] ENTREE = { "0.4", "0.5" };
        static String[] HALL = { "5.5", "6.6", "7.5", "6.3" };
        public MainWindowCluedo()
        {
            InitializeComponent();

            instance = this;
        }

        public static SurfaceWindow getInstance()
        {
            if (instance == null)
                instance = new SurfaceWindow();
            return instance;
        }

        private void verifyPlace(object sender, TagVisualizerEventArgs e)
        {
            //string idCase = ((TagVisualizer)e.OriginalSource).DataContext.ToString();
            //Rectangle zoneCase = getZoneCaseFromName(Convert.ToInt32(idCase));
            //SocketIO.PionPose(idCase);
            //1. get last location from server by sending joueur id, if in a room, get all doors
            //String[] list = { "6.3","6.6","5.5","7.5" };
            String[] list = getDoors("HALL");

            //2. get "dé" number
            int de = 3;

            List<String> tab = seDeplacer(list, de);

            string idCase = ((TagVisualizer)e.OriginalSource).DataContext.ToString();
            Console.WriteLine(((TagVisualizer)e.OriginalSource).DataContext.ToString());
            Console.WriteLine(int.Parse((float.Parse(idCase)*10).ToString()) );

            Rectangle zoneCase = getZoneCaseFromName(int.Parse((float.Parse(idCase) * 10).ToString()));

            Boolean positionCorrect = false;
            foreach (string caseMvt in tab) {
                if (idCase.Equals(caseMvt)) {
                    zoneCase.Fill = Brushes.Green;
                    positionCorrect = true;
                    //4. if position correct send current position   
                }
            }
            if (!positionCorrect) {
                zoneCase.Fill = Brushes.Red;
            }


        }

        private void entrerDansPiece(object sender, TagVisualizerEventArgs e)
        {
            string idCase = ((TagVisualizer)e.OriginalSource).DataContext.ToString();
            Console.WriteLine("entrer dans " + idCase);
        }

        private String[] getDoors(String room) {
            switch (room)
            {
                case "GARAGE":
                    return GARAGE;
                case "SALLEDEJEU":
                    return SALLEDEJEU;
                case "CHAMBRE":
                    return CHAMBRE;
                case "SALLEDEBAIN":
                    return SALLEDEBAIN;
                case "BUREAU":
                    return BUREAU;
                case "CUSINE":
                    return CUSINE;
                case "SALLEAMANGE":
                    return SALLEAMANGER;
                case "SALON":
                    return SALON;
                case "ENTREE":
                    return ENTREE;
                case "HALL":
                    return HALL;
                default:
                    return null;
            }

        }


        private void cleanColor(object sender, TagVisualizerEventArgs e){
            string idCase = ((TagVisualizer)e.OriginalSource).DataContext.ToString();
            Rectangle zoneCase = getZoneCaseFromName(int.Parse((float.Parse(idCase) * 10).ToString()));
            zoneCase.Fill = Brushes.Beige;
        }

        public Rectangle getZoneCaseFromName(int idCase)
        {
            switch (idCase)
            {
                case 13:
                    return this.N13;
                case 14:
                    return this.N14;
                case 15:
                    return this.N15;
                case 16:
                    return this.N16;
                case 21:
                    return this.N21;
                case 22:
                    return this.N22;
                case 23:
                    return this.N23;
                case 24:
                    return this.N24;
                case 25:
                    return this.N25;
                case 26:
                    return this.N26;
                case 27:
                    return this.N27;
                case 28:
                    return this.N28;
                case 31:
                    return this.N31;
                case 32:
                    return this.N32;
                case 33:
                    return this.N33;
                case 34:
                    return this.N34;
                case 35:
                    return this.N35;
                case 36:
                    return this.N36;
                case 37:
                    return this.N37;
                case 38:
                    return this.N38;
                case 41:
                    return this.N41;
                case 42:
                    return this.N42;
                case 43:
                    return this.N43;
                case 44:
                    return this.N44;
                case 45:
                    return this.N45;
                case 46:
                    return this.N46;
                case 47:
                    return this.N47;
                case 48:
                    return this.N48;
                case 51:
                    return this.N51;
                case 52:
                    return this.N52;
                case 57:
                    return this.N57;
                case 61:
                    return this.N61;
                case 62:
                    return this.N62;
                case 67:
                    return this.N67;
                case 71:
                    return this.N71;
                case 72:
                    return this.N72;
                case 77:
                    return this.N77;
                case 81:
                    return this.N81;
                case 82:
                    return this.N82;
                case 83:
                    return this.N83;
                case 84:
                    return this.N84;
                case 85:
                    return this.N85;
                case 86:
                    return this.N86;
                case 87:
                    return this.N87;
                case 91:
                    return this.N91;
                case 92:
                    return this.N92;
                case 93:
                    return this.N93;
                case 94:
                    return this.N94;
                case 95:
                    return this.N95;
                case 96:
                    return this.N96;
                case 97:
                    return this.N97;
                case 102:
                    return this.N102;
                case 103:
                    return this.N103;
                case 104:
                    return this.N104;
                case 105:
                    return this.N105;
                case 106:
                    return this.N106;
                case 107:
                    return this.N107;
                case 108:
                    return this.N108;
                case 112:
                    return this.N112;
                case 113:
                    return this.N113;
                case 114:
                    return this.N114;
                case 115:
                    return this.N115;
                case 116:
                    return this.N116;
                case 117:
                    return this.N117;
                case 118:
                    return this.N118;
                case 121:
                    return this.N121;
                case 122:
                    return this.N122;
                case 123:
                    return this.N123;
                case 124:
                    return this.N124;
                case 131:
                    return this.N131;
                case 132:
                    return this.N132;
                case 133:
                    return this.N133;
                case 134:
                    return this.N134;

            }

            return null;
        }

        static List<String> seDeplacer(string[] positions, int de)
        {
            List<String> tab = new List<string>();
            foreach (string pos in positions)
            {
                tab.AddRange(choixDeplacement(pos, de, new List<String>()));
            }
            // on enlève les entrées de la pièce d'origine
            foreach (string pos in positions)
            {
                tab.Remove(pos);
            }
            // on enlève les doublons
            tab = tab.Distinct().ToList<String>();
            return tab;
        }

        static Boolean isRoom(string pos)
        {
            if (pos == "0.4" || pos == "0.5" || pos == "2.0" || pos == "3.9" || pos == "6.0" || pos == "6.3" || pos == "5.5" || pos == "7.5" || pos == "6.6" || pos == "7.8" || pos == "11.9" || pos == "12.0" || pos == "13.5" || pos == "14.3")
            {
                //Console.WriteLine("salle "+pos);
                return true;
            }
            return false;
        }

        static List<String> choixDeplacement(string pos, int de, List<String> cheminParcouru)
        {
            List<String> tab = new List<String>();
            // sinon si l'on peut encore se déplacer
            if (de > 0)
            {
                string[] position = pos.Split('.');
                int x = Int32.Parse(position[0]);
                int y = Int32.Parse(position[1]);
                string temp = "";
                // tout droit
                temp = (x + 1) + "." + y;
                // si pièce, retourner la case
                if (isRoom(temp))
                {
                    tab.Add(temp);
                    cheminParcouru.Add(temp);
                }
                else if (cases.Contains(temp) && !cheminParcouru.Contains(temp))
                {
                    cheminParcouru.Add(temp);
                    tab.AddRange(choixDeplacement(temp, de - 1, cheminParcouru));
                }
                // à droite
                temp = x + "." + (y + 1);
                if (isRoom(temp))
                {
                    tab.Add(temp);
                    cheminParcouru.Add(temp);
                }
                else if (cases.Contains(temp) && !cheminParcouru.Contains(temp))
                {
                    cheminParcouru.Add(temp);
                    tab.AddRange(choixDeplacement(temp, de - 1, cheminParcouru));
                }
                // à gauche
                temp = x + "." + (y - 1);
                if (isRoom(temp))
                {
                    tab.Add(temp);
                    cheminParcouru.Add(temp);
                }
                else if (cases.Contains(temp) && !cheminParcouru.Contains(temp))
                {
                    cheminParcouru.Add(temp);
                    tab.AddRange(choixDeplacement(temp, de - 1, cheminParcouru));
                }
            }
            // sinon, de=0, retourne la case actuelle
            else
            {
                tab.Add(pos);
                cheminParcouru.Add(pos);
            }

            return tab;
        }
    }
}
