using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ListaPublica.Classes
{
    public class Telefone
    {
        public int id { get; set; }
        public string numero { get; set; }
        public string descricao { get; set; }
        public Endereco endereco { get; set; }
        public Parceiro parceiro { get; set; }
    }
}
