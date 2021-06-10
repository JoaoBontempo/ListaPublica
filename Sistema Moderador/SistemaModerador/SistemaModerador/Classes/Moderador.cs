using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SistemaModerador.Classes
{
    public class Moderador
    {
        private int id;
        private string usuario;
        private string email;

        public void setId(int value)
        {
            this.id = value;
        }

        public void setUsuario(string value)
        {
            this.usuario = value;
        }

        public void setEmail(string value)
        {
            this.email = value;
        }

        public int getId()
        {
            return this.id;
        }

        public string getUsuario()
        {
            return this.usuario;
        }

        public string getEmail()
        {
            return this.email;
        }
    }
}
