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
    public partial class frmEndereco : Form
    {
        private string query = "SELECT rua, numero, bairro, estado, cidade, nome, imagem FROM endereco WHERE id = ";
        public frmEndereco(int id)
        {
            InitializeComponent();
            query += id.ToString();
            Banco.InserirQueryReader(query);
            Banco.reader.Read();

            try
            {
                pbxImagem.Image = Util.ConverterImagem(Banco.reader.GetString("imagem"));
            }
            catch
            {
                pbxImagem.Visible = false;
                this.Size = new Size(466, 219);
            }

            this.Text = String.Format("Endereço Nº: {0}, '{1}'", id.ToString(), Banco.reader.GetString("nome"));
            txtRua.Text = Banco.reader.GetString("rua");
            txtBairro.Text = Banco.reader.GetString("bairro");
            txtCidade.Text = Banco.reader.GetString("cidade");
            txtEstado.Text = Banco.reader.GetString("estado");
            txtNumero.Text = Banco.reader.GetInt32("numero").ToString();
        }
    }
}
