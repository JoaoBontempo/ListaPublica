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

namespace SistemaModerador.View
{
    public partial class EnviarEmail : Form
    {
        string[] destinatarios;
        string d1, d2;
        public EnviarEmail(string[] destinatarios)
        {
            this.destinatarios = destinatarios;
            d1 = destinatarios[0];
            d2 = destinatarios[1];
            InitializeComponent();
        }

        private bool VerificarCampos()
        {
            if (String.IsNullOrEmpty(txtDescricao.Text))
            {
                MessageBox.Show("O campo 'Descrição' está vazio");
                txtDescricao.Focus();
                return false;
            }
            if (String.IsNullOrEmpty(txtAssunto.Text))
            {
                MessageBox.Show("O campo 'Assunto' está vazio");
                txtAssunto.Focus();
                return false;
            }
            return true;
        }

        private void OrganizarDestinatarios()
        {
            if (rbAmbos.Checked)
            {
                destinatarios[0] = d1;
                destinatarios[1] = d2;
            }
            else if (rbDenunciado.Checked)
            {
                destinatarios[0] = d1;
                destinatarios[1] = "ignore";
            }
            else
            {
                destinatarios[1] = d2;
                destinatarios[0] = "ignore";
            }
        }

        private void btnEnviarEmail_Click(object sender, EventArgs e)
        {
            if (VerificarCampos())
            {
                OrganizarDestinatarios();
                Email.EnviarEmail(txtAssunto.Text, txtDescricao.Text, destinatarios);
            }
        }
    }
}
