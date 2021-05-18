using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ListaPublica.Classes
{
    public class Parceiro
    {
        public Parceiro()
        {
            telefones = new List<Telefone>();
            enderecos = new List<Endereco>();
        }

        public int id { get; set; }
        public string nome { get; set; }
        public string cpf { get; set; }
        public string cnpj { get; set; }
        public bool tipo { get; set; }
        public string email { get; set; }
        public string usuario { get; set; }
        public IList<Telefone> telefones { get; set; }
        public IList<Endereco> enderecos { get; set; }

    }
}
