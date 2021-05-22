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
        public Denuncia(int id, string descricao, string tipo, string local, bool status, Parceiro denunciado, Parceiro denunciador)
        {
            this.id = id;
            this.descricao = descricao;
            this.tipo = tipo;
            this.local = local;
            this.status = status;
            this.denunciado = denunciado;
            this.denunciador = denunciador;
        }

        public Denuncia()
        {

        }

        public int id { get; set; }
        public string descricao { get; set; }
        public string tipo { get; set; }
        public string local { get; set; }
        public bool status { get; set; }
        public Parceiro denunciado { get; set; }
        public Parceiro denunciador { get; set; }
    }
}
