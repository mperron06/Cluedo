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
using System.Windows.Media.Animation;
namespace Cluedo
{
    /// <summary>
    /// Logique d'interaction pour Page1.xaml
    /// </summary>
    public partial class Page1 : SurfaceWindow
    {
        private int numPlayer;
        private String ipAddress;

        public static Page1 instance;

        System.Collections.ObjectModel.ObservableCollection<String> items;/*************************added****************************/
      
        public Page1()
        {
            InitializeComponent();
            ipAddress = LocalIPAddress();
            ShowQrcode(ipAddress, this.qrCodeViolet, "Violet");
            ShowQrcode(ipAddress, this.qrCodeBlanc, "Leblanc");
            ShowQrcode(ipAddress, this.qrCodeRose, "Rose");
            ShowQrcode(ipAddress, this.qrCodeOlive, "Olive");
            ShowQrcode(ipAddress, this.qrCodeMoutarde, "Moutarde");
            ShowQrcode(ipAddress, this.qrCodePervenche, "Pervenche");

            // Initialise le singleton
            instance = this;

            //Populate LibraryBar
            LoadLibraryBarContent();/*******************************added********************************************/

            startServer();
            // Lance socketIO
            SocketIO.Execute();
            //IsConnectToServeur = true;*/

            
        }

        private void BeginAnimation()
        {

            DoubleAnimation doubleAnimation = new DoubleAnimation();
            doubleAnimation.From = -toto1.ActualWidth;
            doubleAnimation.To = canvas1.ActualWidth;
            doubleAnimation.RepeatBehavior = RepeatBehavior.Forever;
            doubleAnimation.Duration = new Duration(TimeSpan.FromSeconds(15));
            toto1.BeginAnimation(Canvas.RightProperty, doubleAnimation);
            toto2.BeginAnimation(Canvas.RightProperty, doubleAnimation);
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            BeginAnimation();
        }
        
       public static Page1 getInstance()
        {
            if (instance == null)
                instance = new Page1();
            return instance;
        }

        private void startServer()
        {
            Process myProcess = new Process();
            myProcess.StartInfo.WindowStyle = ProcessWindowStyle.Hidden;
            myProcess.StartInfo.CreateNoWindow = false;
            myProcess.StartInfo.UseShellExecute = false;
            myProcess.StartInfo.FileName = "cmd.exe";
            myProcess.StartInfo.Arguments = "/c cd ../../../Serveur & cd & node server.js";
            myProcess.EnableRaisingEvents = true;
            myProcess.Start();
        }
        
        /******************************************added*************************************************************/
        private void LoadLibraryBarContent()
        {
            try
            {
                string imagesFolderPath = "../../Resources/rules";
                /*
                string publicFoldersPath = Environment.GetEnvironmentVariable("public");
                // These are default OS folders. 
                string publicImagesPath = publicFoldersPath + @"\Pictures\Sample Pictures\rules";
                */
                String[] files = System.IO.Directory.GetFiles(imagesFolderPath, "*.jpg");

                items = new System.Collections.ObjectModel.ObservableCollection<String>();

                foreach (String file in files)
                {
                    items.Add(file);
                }

                library.ItemsSource = items;

            }
            catch (System.IO.DirectoryNotFoundException)
            {

            }
        }

        private void ScatterViewDrop(object sender, SurfaceDragDropEventArgs e)
        {
            //Retrieve the index of the dragged object
            int position = items.IndexOf(e.Cursor.Data as String);

            //Set the background of the ScatterView
            ImageSource i = new BitmapImage(new Uri(e.Cursor.Data as String, UriKind.Relative));
            scatter_Rules.Background = new ImageBrush(i);

            //Remove the item from the Library source and add it again (to be able to use it again)
            items.Remove(e.Cursor.Data as String);
            items.Insert(position, e.Cursor.Data as String);

        }
        /*************************************************end***************************************************************/

        private void goToMainPage(object sender, RoutedEventArgs e)
        {
            Sounds.Play(EnumSound.START);
            MainWindowCluedo mainWindow = new MainWindowCluedo();
            mainWindow.Show();
            this.Close();

            SocketIO.start();
        }


        private void ShowQrcode(string ip, QrCodeImgControl qrCode, string msg)
        {
            //control update text then encode and recreate Image.
            BitMatrix qrMatrix = qrCode.GetQrMatrix(); //Qr bit matrix for input string "QrCode.Net".

            qrCode.Lock();  //Lock class.
            qrCode.ErrorCorrectLevel = ErrorCorrectionLevel.M;  //It won't encode and recreate image.
            qrCode.Text = ip+"_"+msg;
            qrMatrix = qrCode.GetQrMatrix(); //Qr bit matrix for input string "QrCode.Net".
            qrCode.QuietZoneModule = QuietZoneModules.Zero;  //Control will recreate image, but Bitmatrix is still for "QrCode.Net" input string. 
            qrCode.Unlock(); //Unlock class, re-encode and repaint. 
        }

        public void hideQrcode(String qr) {
            UIElement ele = personGrid.FindName(qr) as UIElement;
            Image img = ele as Image;
            img.Visibility = System.Windows.Visibility.Visible;
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
    }
}
