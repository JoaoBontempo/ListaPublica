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
    public partial class frmDenunciaIndividual : Form
    {
        private int idEndereco, idDenuncia;
        private string query = "SELECT denuncia.descricao, denuncia.tipo, denuncia.status_, " +
        "Pdenunciado.id as idD1, Pdenunciado.nome as nomeD1, Pdenunciado.tipo as tipoD1, Pdenunciado.usuario as usuarioD1, Pdenunciado.email as emailD1, Pdenunciado.cpf as cpfD1, Pdenunciado.cnpj as cpnjD1," +
        "Pdenunciador.id as idD2, Pdenunciador.nome as nomeD2, Pdenunciador.tipo as tipoD2, Pdenunciador.usuario as usuarioD2, Pdenunciador.email as emailD2, Pdenunciador.cpf as cpfD2, Pdenunciador.cnpj as cnpjD2," +
        "endereco.id as idE," +
        "telefone.id as idT, telefone.descricao as descT, telefone.numero " +
        "FROM denuncia " +
        "INNER JOIN parceiro as Pdenunciado ON denuncia.denunciado = Pdenunciado.id " +
        "INNER JOIN parceiro as Pdenunciador ON denuncia.denunciador = Pdenunciador.id " +
        "LEFT JOIN telefone ON denuncia.tel = telefone.id " +
        "LEFT JOIN endereco ON denuncia.end_ = endereco.id " +
        "WHERE denuncia.id = ";

        private void btnFecharDenuncia_Click(object sender, EventArgs e)
        {
            if (DialogResult.Yes.Equals(MessageBox.Show("Tem certeza que deseja fechar esta denúncia?", "Confirmar fechamento", MessageBoxButtons.YesNo, MessageBoxIcon.Question)))
            {
                Banco.InserirQuery("UPDATE denuncia SET status_ = 1 WHERE id = " + idDenuncia.ToString());
                MessageBox.Show("Denúncia fechada com sucesso!");
                this.Close();
            }
        }

        private void btnVerEndereco_Click(object sender, EventArgs e)
        {
            frmEndereco end = new frmEndereco(idEndereco);
            end.ShowDialog();
        }

        private void btnEnviarEmail_Click(object sender, EventArgs e)
        {
            EnviarEmail ee = new EnviarEmail(new string[] {txtEmailD1.Text, txtEmailD2.Text });
            ee.ShowDialog();
        }

        private void ExcluirInformacoes(string parceiro)
        {
            if (DialogResult.Yes.Equals(MessageBox.Show("Tem certeza que deseja excluir as informações?", String.Format("Todas as informações do {0} serão excluídas, incluindo:" +
               "\n\n- Telefones" +
               "\n- Endereços" +
               "\n- Esta denúncia" +
               "\n- A conta do {0}." +
               "\n\nDeseja prosseguir?", parceiro), MessageBoxButtons.YesNo, MessageBoxIcon.Question)))
            {
                TextBox textBox;
                if (parceiro.Equals("denunciado"))
                    textBox = txtIDD1;
                else
                    textBox = txtIDD2;

                Banco.InserirQuery("DELETE FROM denuncia WHERE id = " + idDenuncia);
                Banco.InserirQuery("DELETE FROM telefone WHERE dono = " + textBox.Text);
                Banco.InserirQuery("DELETE FROM endereco WHERE usuario = " + textBox.Text);
                Banco.InserirQuery("DELETE FROM	parceiro WHERE id = " + textBox.Text);

                MessageBox.Show(String.Format("As informações do {0} foram excluídas com sucesso.", parceiro));
            }
        }

        private void btnExcluirDenunciado_Click(object sender, EventArgs e)
        {
            ExcluirInformacoes("denunciado");  
        }

        private void btnExcluirDenunciante_Click(object sender, EventArgs e)
        {
            ExcluirInformacoes("denunciante");
        }

        public frmDenunciaIndividual(int idDenuncia)
        {
            InitializeComponent();
            this.idDenuncia = idDenuncia;
            this.Text = "Lista Pública - Sistema moderador - Denúncia Nº: " + idDenuncia.ToString();
            query += idDenuncia.ToString();

            Banco.InserirQueryReader(query);
            Banco.reader.Read();

            btnFecharDenuncia.Visible = Banco.reader.GetInt32("status_") == 0 ? true : false;

            //Pegando id do endereço
            try
            {
                idEndereco = Banco.reader.GetInt32("idE");
            }
            catch
            {
                btnVerEndereco.Visible = false;
                txtDescTel.Size = new Size(339, 150);
            }

            //Preenchendo informações do denunciado
            bool tipo = Convert.ToBoolean(Banco.reader.GetInt32("tipoD1"));
            txtIDD1.Text = Banco.reader.GetInt32("idD1").ToString();
            txtNomeD1.Text = Banco.reader.GetString("nomeD1");
            txtEmailD1.Text = Banco.reader.GetString("emailD1");
            txtUsuarioD1.Text = Banco.reader.GetString("usuarioD1");
            lbCPFCNPJ.Text = tipo ? "CPNJ" : "CPF";
            txtCPFCNPJD1.Text = tipo ? Banco.reader.GetString("cpnjD1") : Banco.reader.GetString("cpfD1");

            //Informações do denunciante
            tipo = Convert.ToBoolean(Banco.reader.GetInt32("tipoD2"));
            txtIDD2.Text = Banco.reader.GetInt32("idD2").ToString();
            txtNomeD2.Text = Banco.reader.GetString("nomeD2");
            txtEmailD2.Text = Banco.reader.GetString("emailD2");
            txtUsuarioD2.Text = Banco.reader.GetString("usuarioD2");
            lbCPFCPNJ2.Text = tipo ? "CPNJ" : "CPF";
            txtCPFCNPJD2.Text = tipo ? Banco.reader.GetString("cpnjD2") : Banco.reader.GetString("cpfD2");

            //Informações da denúncia
            txtIDDenuncia.Text = idDenuncia.ToString();
            txtTipo.Text = Banco.reader.GetString("tipo");
            txtDescDenuncia.Text = Banco.reader.GetString("descricao");

            //Informações do telefone
            txtIdTel.Text = Banco.reader.GetInt32("idT").ToString();
            txtNumeroTel.Text = Banco.reader.GetString("numero");
            txtDescTel.Text = Banco.reader.GetString("descT");
        }
    }
}
