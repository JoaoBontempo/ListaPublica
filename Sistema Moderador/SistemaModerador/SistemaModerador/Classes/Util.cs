using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SistemaModerador.Classes
{
    public class Util
    {
        public static string moderador;
        public static string email;
        public static bool isCodigo = false;

        public static Image ConverterImagem(string blob)
        {
            byte[] bytes = Convert.FromBase64String(blob);
            MemoryStream ms = new MemoryStream(bytes);
            ms.Position = 0;
            Image returnImage = Image.FromStream(ms);
            return returnImage;
        }
    }
}
