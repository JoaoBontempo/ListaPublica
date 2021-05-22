using SistemaModerador.Classes;
using System;
using System.Collections;
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
    //local 1 = telefone
    //status 1 = fechado
    public partial class TelaPrincipal : Form
    {
        const string queryGeral = "SELECT denuncia.id, denuncia.descricao, denuncia.tipo, denuncia.local_, denuncia.status_, denuncia.denunciado, denuncia.denunciador, " +
                                  "Pdenunciado.nome as nomeD1, " +
                                  "Pdenunciador.nome as nomeD2 " +
                                  "FROM denuncia " +
                                  "INNER JOIN parceiro as Pdenunciado ON denuncia.denunciado = Pdenunciado.id " +
                                  "INNER JOIN parceiro as Pdenunciador ON denuncia.denunciador = Pdenunciador.id " +
                                  "WHERE denuncia.id > 0 ORDER BY denuncia.id DESC";
        public TelaPrincipal()
        {
            InitializeComponent();
            AtualizarGridDenuncias(queryGeral);
        }

        private void AtualizarGridDenuncias(string query)
        {
            ArrayList denuncias = new ArrayList();
            dgvDenuncias.Rows.Clear();
            Banco.InserirQueryReader(query);
            while(Banco.reader.Read())
            {
                Denuncia denuncia = new Denuncia();
                denuncia.id = Banco.reader.GetInt32("id");
                denuncia.descricao = Banco.reader.GetString("descricao");
                denuncia.local = Convert.ToBoolean(Banco.reader.GetInt32("local_"));
                denuncia.tipo = Banco.reader.GetString("tipo");
                denuncia.status = Convert.ToBoolean(Banco.reader.GetInt32("status_"));
                denuncias.Add(denuncia);

                dgvDenuncias.Rows.Add(new string[] { denuncia.id.ToString(),
                    denuncia.tipo, denuncia.local == true ? "Telefone" : "Comentário",
                    denuncia.descricao, denuncia.status ==  true ? "Fechada" : "Aberta", Banco.reader.GetString("nomeD2"), Banco.reader.GetString("nomeD1")});
            }
        }

        private void btnAtualizar_Click(object sender, EventArgs e)
        {
            AtualizarGridDenuncias(queryGeral);
        }

        private void TelaPrincipal_FormClosing(object sender, FormClosingEventArgs e)
        {
            Banco.FecharBanco();
        }
    }
}
