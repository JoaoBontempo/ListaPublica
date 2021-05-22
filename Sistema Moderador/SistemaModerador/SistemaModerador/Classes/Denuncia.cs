using ListaPublica.Classes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SistemaModerador.Classes
{
    public class Denuncia
    {
        public Denuncia(int id, string descricao, string tipo, bool local, bool status)
        {
            this.id = id;
            this.descricao = descricao;
            this.tipo = tipo;
            this.local = local;
            this.status = status;
        }

        public Denuncia()
        {

        }

        public int id { get; set; }
        public string descricao { get; set; }
        public string tipo { get; set; }
        public bool local { get; set; }
        public bool status { get; set; }
        public Parceiro denunciado { get; set; }
        public Parceiro denunciador { get; set; }
        public Telefone telefone { get; set; }
        public Endereco endereco { get; set; }
    }
}
