using SistemaModerador.Classes;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace SistemaModerador
{
    public partial class TelaPrincipal : Form
    {
        public TelaPrincipal()
        {
            InitializeComponent();
        }

        private void AtualizarGridDenuncias(string query)
        {
            Banco.InserirQueryReader(query);
            while(Banco.reader.Read())
            {
                Denuncia denuncia = new Denuncia();
            }
        }
    }
}
