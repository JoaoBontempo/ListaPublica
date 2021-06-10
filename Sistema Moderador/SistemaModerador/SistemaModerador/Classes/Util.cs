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
		public static Moderador moderador = new Moderador();
        public static bool isCodigo = false;

        public static bool ValidarEmail(string email)
        {
            if (String.IsNullOrEmpty(email))
                return false;

            if (!email.Contains("@"))
                return false;

            string[] partesEmail = email.Split('@');

            if (!partesEmail[1].Contains("."))
                return false;

            string[] dominio = partesEmail[1].Split('.');

            if (partesEmail[0].Contains("{") || partesEmail[0].Contains("}") || partesEmail[0].Contains("[") || partesEmail[0].Contains("]") || partesEmail[0].Contains(",")
                        || partesEmail[0].Contains("'") || partesEmail[0].Contains(@"""") || partesEmail[0].Contains(@"\") || partesEmail[0].Contains("/") ||
                        partesEmail[0].Contains("|") || partesEmail[0].Contains("!") || partesEmail[0].Contains("=") || partesEmail[0].Contains("+")
                        || partesEmail[0].Contains("$") || partesEmail[0].Contains("%") || partesEmail[0].Contains("(") || partesEmail[0].Contains(")") || partesEmail[0].Contains("#"))
                return false;

            if (String.IsNullOrEmpty(dominio[1]) || String.IsNullOrEmpty(dominio[0]))
                return false;

            return true;
        }

        public static Image ConverterImagem(string blob)
        {
            byte[] bytes = Convert.FromBase64String(blob);
            MemoryStream ms = new MemoryStream(bytes);
            ms.Position = 0;
            Image returnImage = Image.FromStream(ms);
            return returnImage;
        }

		private static String ColocarTracoTelefone(String telefone, int index)
		{
			String telFormatado = "";
			for (int i = 0; i < telefone.Length; i++)
			{
				telFormatado += telefone[i];
				if (index - 1 == i)
					telFormatado += "-";
			}
			return telFormatado;
		}

		public static String FormatarGetTelefone(String telefone, String tipo)
		{
			if (tipo.Equals("outro"))
				return telefone;
			if (telefone.Length <= 1)
				return telefone;
			String ddd = telefone.Substring(0, 2);
			telefone = telefone.Substring(2);

			if (tipo.Equals("fixo"))
				telefone = ColocarTracoTelefone(telefone, 4);
			else
				telefone = ColocarTracoTelefone(telefone, 5);

			return String.Format("({0}) {1}", ddd, telefone);
		}
	}
}
