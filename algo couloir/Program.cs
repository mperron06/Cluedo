using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MaPremiereApplication
{
    class Program
    {
        static List<String> cases = new List<String> { "0.4", "0.5", "1.3", "1.4", "1.5", "1.6", "2.0", "2.1", "2.2", "2.3", "2.4", "2.5", "2.6", "2.7", "2.8", "3.1", "3.2", "3.3", "3.4", "3.5", "3.6", "3.7", "3.8", "3.9", "4.1", "4.2", "4.3", "4.4", "4.5", "4.6", "4.7", "4.8", "5.1", "5.2", "5.5", "5.7", "6.0", "6.1", "6.2", "6.3", "6.6", "6.7", "7.1", "7.2", "7.5", "7.7", "7.8", "8.1", "8.2", "8.3", "8.4", "8.5", "8.6", "8.7", "9.1", "9.2", "9.3", "9.4", "9.5", "9.6", "9.7", "10.2", "10.3", "10.4", "10.5", "10.6", "10.7", "10.8", "11.2", "11.3", "11.4", "11.5", "11.6", "11.7", "11.8", "11.9", "12.0", "12.1", "12.2", "12.3", "12.4", "13.1", "13.2", "13.3", "13.4", "13.5", "14.3" };
        static void Main(string[] args)
        {
             Random rnd = new Random();
            int de = rnd.Next(2, 13); // creates a number between 2 and 12
            Console.WriteLine(de);
            // deux sorties de la pièce
            //string[] list = {"0.4", "0.5"};
            string [] list = { "10.3" };
            List<String> tab = seDeplacer(list, de);

            foreach (string caseMvt in tab)
            {
                Console.WriteLine(caseMvt);
            }
            Console.WriteLine(validCases("2.3", tab));
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
                // en arrière
                temp = (x - 1) + "." + y;
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
            }
            // sinon,  retourne la case actuelle
            else
            {
                tab.Add(pos);
                cheminParcouru.Add(pos);
            }

            return tab;
        }
        static Boolean validCases(string pos, List<String> cheminPossible)
        {
            if (cheminPossible.Contains(pos))
            {
                return true;
            }
            return false;
        }
    }
}
