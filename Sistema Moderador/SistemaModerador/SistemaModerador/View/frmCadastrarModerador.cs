﻿using SistemaModerador.Classes;
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
    public partial class frmCadastrarModerador : Form
    {
        public frmCadastrarModerador()
        {
            InitializeComponent();
        }

        private void frmCadastrarModerador_Shown(object sender, EventArgs e)
        {
            frmConfirmacao confirmacao = new frmConfirmacao();
            confirmacao.ShowDialog();

            if (confirmacao.getResposta() == -1)
                this.Close();
        }

        private void btnCadastrar_Click(object sender, EventArgs e)
        {
            string senha = frmConfirmacao.GerarCodigo();
            Banco.InserirQuery(String.Format("INSERT INTO moderador (id, usuario, email, senha) VALUES (default, '{0}', '{1}', '{2}')", 
                txtUsuario.Text, txtEmail.Text, BCrypt.Net.BCrypt.HashPassword(senha)));
            Email.EnviarEmail(senha, txtUsuario.Text, txtEmail.Text);
            MessageBox.Show("O novo moderador foi cadastrado com sucesso! " +
                "\nUm e-mail foi enviado para ele com as informações do cadastro");
            this.Close();
        }
    }
}
