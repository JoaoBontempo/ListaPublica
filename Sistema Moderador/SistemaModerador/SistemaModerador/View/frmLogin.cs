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
        bool logado = false;
        public frmLogin()
        {
            InitializeComponent();
            Banco.AbreConexao();
        }

        private bool ValidarHash(string senhaLocal, string senhaBanco)
        {
            return BCrypt.Net.BCrypt.Verify(senhaLocal, senhaBanco);
        }

        private bool VerificarCampos()
        {
            if (String.IsNullOrEmpty(txtUsuario.Text))
            {
                MessageBox.Show("Nenhum usuário foi informado");
                txtUsuario.Focus();
                return false;
            }
            if (String.IsNullOrEmpty(txtSenha.Text))
            {
                MessageBox.Show("Nenhuma senha foi informada");
                txtUsuario.Focus();
                return false;
            }
            return true;
        }
        private void btnLogar_Click(object sender, EventArgs e)
        {
            if (VerificarCampos())
            {
                Banco.InserirQueryReader(String.Format("SELECT * FROM moderador WHERE usuario = '{0}'", txtUsuario.Text));
                Banco.reader.Read();
                if (Banco.reader.HasRows)
                {
                    if (ValidarHash(txtSenha.Text, Banco.reader.GetString("senha")))
                    {
                        logado = true;
                        TelaPrincipal tp = new TelaPrincipal();
                        tp.ShowDialog();
                        this.Close();
                    }
                    else
                    {
                        MessageBox.Show("Senha incorreta");
                    }
                }
                else
                {
                    MessageBox.Show("Usuário não encontrado");
                }
            }
        }

        private void frmLogin_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (!logado)
                Banco.FecharBanco();
        }
    }
}
