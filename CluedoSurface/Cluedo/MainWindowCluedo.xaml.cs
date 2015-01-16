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

        private void putPion(object sender, TagVisualizerEventArgs e)
        {

            string idCase = ((TagVisualizer)e.OriginalSource).DataContext.ToString();
            Rectangle zoneCase = getZoneCaseFromName(Convert.ToInt32(idCase));

            SocketIO.PionPose(idCase);
        }

        public Rectangle getZoneCaseFromName(int idCase)
        {
            switch (idCase)
            {
                
                case 13:
                    return this.N13;
            }

            return null;
        }
    }
}
