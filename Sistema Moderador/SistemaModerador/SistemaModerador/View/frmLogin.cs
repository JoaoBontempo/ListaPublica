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
    public partial class frmLogin : Form
    {
        public frmLogin()
        {
            InitializeComponent();
            Banco.AbreConexao();
        }

    }
}
