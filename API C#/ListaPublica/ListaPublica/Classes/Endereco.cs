using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ListaPublica.Classes
{
    public class Endereco
    {
        public int id { get; set; }
        public int numero { get; set; }
        public string rua { get; set; }
        public string bairro { get; set; }
        public string cidade { get; set; }
        public string estado { get; set; }
        public string nome { get; set; }
        //public Parceiro parceiro { get; set; }
    }
}
