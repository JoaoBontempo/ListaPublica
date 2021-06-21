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
    public partial class frmConfirmacao : Form
    {
        private static Random random = new Random();
        int tempo = 120, resposta = -1;
        string codigo;
        public frmConfirmacao()
        {
            InitializeComponent();
            bwEmail.RunWorkerAsync();
        }

        public int getResposta()
        {
            return resposta;
        }

        public static string GerarCodigo()
        {
            string codigo = DateTime.Now.Millisecond.ToString() + "-LP-";
            for (int i = 0; i < 6; i++)
            {
                codigo += random.Next(0, 9).ToString();
            }
            return codigo;
        }

        private void txtConfirmarCodigo_Click(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(txtCodigo.Text))
            {
                MessageBox.Show("Nenhum código foi informado");
                txtCodigo.Focus();
            }
            if (txtCodigo.Text.Equals(codigo))
            {
                resposta = 1;
                this.Close();
            }
            else
            {
                resposta = 0;
                string senha = GerarCodigo();
                Banco.InserirQuery(String.Format("UPDATE moderador SET senha = '{0}' WHERE id = {1}", BCrypt.Net.BCrypt.HashPassword(senha), Util.moderador.getId()));
                Email.EnviarEmail(senha);
                Banco.FecharBanco();
                this.Close();
                Application.Exit();
            }
        }

        private void bwEmail_DoWork(object sender, DoWorkEventArgs e)
        {
            Util.isCodigo = true;
            codigo = GerarCodigo();
            string[] dest = { Util.moderador.getEmail() };
            Email.EnviarEmail("Código de confirmação", String.Format("Este é seu código de confirmação para realizar sua operação no Sistema Moderador:" +
                "\n\n{0}\n\n" +
                "Este código irá expirar em 2 minutos. Por favor, não compartilhe este código.", codigo), dest);
            timer.Enabled = true;
        }

        private void timer_Tick(object sender, EventArgs e)
        {
            if (tempo == 0)
                this.Close();
            tempo--;
            lbTempo.Text = "Tempo restante: " + tempo + "s";
        }
    }
}
