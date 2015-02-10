using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Media;
using Cluedo.Properties;

namespace Cluedo
{
    public enum EnumSound { START, LANCESUPPOSITION, POSERARME, POSERPERSONNAGE }
    public static class Sounds
    {
        private static SoundPlayer sp = new SoundPlayer();

        public static void Play(EnumSound es)
        {
            bool isLoop = false;
            switch (es)
            {
                case EnumSound.START: sp.Stream = Resources.start; break;
                case EnumSound.LANCESUPPOSITION: sp.Stream = Resources.lanceSupposition; break;
                case EnumSound.POSERPERSONNAGE: sp.Stream = Resources.personnagePose; break;
                case EnumSound.POSERARME: sp.Stream = Resources.armePose; break;
                default: return;
            }

            sp.Stop();

            if (isLoop)
                sp.PlayLooping();
            else
                sp.Play();
        }

        public static void Stop()
        {
            sp.Stop();
        }
    }
}
