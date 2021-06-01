using SistemaModerador.Classes;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace SistemaModerador.View
{
    public partial class FotoPerfil : Form
    {
        int id;
        public FotoPerfil(string nome, int id)
        {
            try
            {
                InitializeComponent();
                this.id = id;
                this.Text = "Foto de perfil: " + nome;
                Banco.InserirQueryReader("SELECT imagem FROM parceiro WHERE id = " + id.ToString());
                Banco.reader.Read();
                //byte[] blob = Convert.FromBase64String(Banco.reader.GetString("imagem"));
                this.pbxFotoPerfil.Image = Util.ConverterImagem(Banco.reader.GetString("imagem"));
            }
            catch
            {
                MessageBox.Show("Este usuário não possui foto de perfil");
                this.Close();
            }
        }

        private void btnExcluirEndereco_Click(object sender, EventArgs e)
        {
            if (MessageBox.Show("Tem certeza que deseja excluir esta imagem?", "Confirme sua operação", MessageBoxButtons.YesNo, MessageBoxIcon.Question).Equals(DialogResult.Yes))
            {
                frmConfirmacao confirmacao = new frmConfirmacao();
                confirmacao.ShowDialog();

                switch (confirmacao.getResposta())
                {
                    case -1:
                        MessageBox.Show("A operação foi cancelada.");
                        break;

                    case 0:
                        MessageBox.Show("O código de confirmação está incorreto! O sistema será fechado");
                        Banco.FecharBanco();
                        this.Close();
                        Application.Exit();
                        break;

                    case 1:
                        Banco.InserirQuery("UPDATE parceiro SET imagem = null WHERE id = " + id.ToString());
                        MessageBox.Show("Foto excluída com sucesso!");
                        this.Close();
                        break;
                }
            }
        }
    }
}
