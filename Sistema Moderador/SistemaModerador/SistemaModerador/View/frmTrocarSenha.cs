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
    public partial class frmTrocarSenha : Form
    {
        public frmTrocarSenha()
        {
            InitializeComponent();
        }

        private void txtConfirmarSenha_TextChanged(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(txtConfirmarSenha.Text))
            {
                lbConfirmarSenha.Hide();
                pnlConfirmarSenha.Hide();
                return;
            }
            lbConfirmarSenha.Show();
            pnlConfirmarSenha.Show();
            if (txtConfirmarSenha.Text.Equals(txtNovaSenha.Text))
            {
                lbConfirmarSenha.Text = "As senha coincidem";
                lbConfirmarSenha.ForeColor = Color.Green;
                pnlConfirmarSenha.BackColor = Color.Green;
                btnConfirmar.Enabled = true;
            }
            else
            {
                lbConfirmarSenha.Text = "As não senhas coincidem";
                lbConfirmarSenha.ForeColor = Color.Red;
                pnlConfirmarSenha.BackColor = Color.Red;
                btnConfirmar.Enabled = false;
            }
        }

        private void btnAtualizar_Click(object sender, EventArgs e)
        {
            Banco.InserirQuery(String.Format("UPDATE moderador SET senha = '{0}' WHERE id = {1}", BCrypt.Net.BCrypt.HashPassword(txtNovaSenha.Text), Util.moderador.getId()));
            MessageBox.Show("Sua senha foi alterada com sucesso!" +
                "\nPor favor, entre novamente no sistema.");
            this.Close();
            Application.Exit();
        }

        private void frmTrocarSenha_Shown(object sender, EventArgs e)
        {
            frmConfirmacao confirmacao = new frmConfirmacao();
            confirmacao.ShowDialog();

            if (confirmacao.getResposta() == -1)
                this.Close();
        }
    }
}
