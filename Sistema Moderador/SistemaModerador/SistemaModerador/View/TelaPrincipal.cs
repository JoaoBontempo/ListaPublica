using SistemaModerador.Classes;
using SistemaModerador.View;
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
        private string tipo = String.Empty, descricao = String.Empty;
        private int id = -1, status = -1, local = -1;
        const string queryGeral = "SELECT denuncia.id, denuncia.descricao, denuncia.tipo, denuncia.status_, denuncia.denunciado, denuncia.denunciador, " +
                                  "Pdenunciado.nome as nomeD1, " +
                                  "Pdenunciador.nome as nomeD2 " +
                                  "FROM denuncia " +
                                  "INNER JOIN parceiro as Pdenunciado ON denuncia.denunciado = Pdenunciado.id " +
                                  "INNER JOIN parceiro as Pdenunciador ON denuncia.denunciador = Pdenunciador.id " +
                                  "WHERE denuncia.id > 0 ";
        public TelaPrincipal()
        {
            InitializeComponent();
            dgvDenuncias.Columns[3].DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleCenter;
            dgvDenuncias.Columns[4].DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleCenter;
            dgvDenuncias.Columns[5].DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleCenter;
            AtualizarGridDenuncias(queryGeral + "ORDER BY denuncia.id DESC");
        }

        private void AtualizarGridDenuncias(string query)
        {
            ArrayList denuncias = new ArrayList();
            dgvDenuncias.Rows.Clear();
            Banco.InserirQueryReader(query);
            if (!Banco.reader.HasRows)
            {
                MessageBox.Show("Nenhuma informação foi encontrada");
                return;
            }
            while(Banco.reader.Read())
            {
                Denuncia denuncia = new Denuncia();
                denuncia.id = Banco.reader.GetInt32("id");
                denuncia.descricao = Banco.reader.GetString("descricao");
                denuncia.tipo = Banco.reader.GetString("tipo");
                denuncia.status = Convert.ToBoolean(Banco.reader.GetInt32("status_"));
                denuncias.Add(denuncia);

                dgvDenuncias.Rows.Add(new string[] { denuncia.id.ToString(),
                    denuncia.tipo, denuncia.descricao, denuncia.status ==  true ? "Fechada" : "Aberta", 
                    Banco.reader.GetString("nomeD2"), Banco.reader.GetString("nomeD1")});
            }
        }

        private void btnAtualizar_Click(object sender, EventArgs e)
        {
            AtualizarGridDenuncias(queryGeral + "ORDER BY denuncia.id DESC");
        }

        private void TelaPrincipal_FormClosing(object sender, FormClosingEventArgs e)
        {
            Banco.FecharBanco();
        }

        private void txtID_TextChanged(object sender, EventArgs e)
        {
            try
            {
                if (String.IsNullOrEmpty(txtID.Text))
                {
                    id = -1;
                    return;
                }
                id = Convert.ToInt32(txtID.Text);
            }
            catch
            {
                txtID.Text = String.Empty;
                id = -1;
            }
        }

        private void txtTipo_TextChanged(object sender, EventArgs e)
        {
            tipo = txtTipo.Text;
        }

        private void txtDescricao_TextChanged(object sender, EventArgs e)
        {
            descricao = txtDescricao.Text;
        }

        private void rbQualquerStatus_CheckedChanged(object sender, EventArgs e)
        {
            if (rbQualquerStatus.Checked)
                status = -1;
        }

        private void rbAberto_CheckedChanged(object sender, EventArgs e)
        {
            if (rbAberto.Checked)
                status = 0;
        }

        private void rbFechado_CheckedChanged(object sender, EventArgs e)
        {
            if (rbFechado.Checked)
                status = 1;
        }

        private void dgvDenuncias_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            frmDenunciaIndividual fdi = new frmDenunciaIndividual(Convert.ToInt32(dgvDenuncias.Rows[e.RowIndex].Cells[0].Value.ToString()));
            fdi.ShowDialog();
            AtualizarGridDenuncias(queryGeral + "ORDER BY denuncia.id DESC");
        }
        
        private string MontarQueryFiltro(int id, int local, int status, string tipo, string descricao)
        {
            string query = queryGeral;
            string idQ = id == -1 ? String.Empty : String.Format("AND denuncia.id = {0}", id);
            if (id != -1)
            {
                rbQualquerStatus.Checked = true;
                txtDescricao.Text = "";
                txtTipo.Text = "";
                return query += idQ + " ORDER BY denuncia.id DESC";
            }

            string statusQ = status < 0 ? String.Empty : String.Format("AND denuncia.status_ = {0}", status.ToString());
            tipo = String.IsNullOrEmpty(tipo) ? tipo : String.Format("AND denuncia.tipo LIKE '%{0}%'", tipo);
            descricao = String.IsNullOrEmpty(descricao) ? descricao : String.Format("AND denuncia.descricao LIKE '%{0}%'", descricao);

            Clipboard.SetText(query + String.Format("{0} {1} {2} ORDER BY denuncia.id DESC", statusQ, tipo, descricao));
            return query + String.Format("{0} {1} {2} ORDER BY denuncia.id DESC", statusQ, tipo, descricao);
        }

        private void txtPesquisar_TextChanged(object sender, EventArgs e)
        {
            foreach (DataGridViewRow linha in dgvDenuncias.Rows)
            {
                if (String.IsNullOrEmpty(txtPesquisar.Text))
                {
                    linha.Visible = true;
                    continue;
                }
                else
                {
                    foreach (DataGridViewCell celula in linha.Cells)
                    {
                        if (celula.Value.ToString().ToLower().Contains(txtPesquisar.Text.ToLower()))
                        {
                            linha.Visible = true;
                            break;
                        }
                        else
                            linha.Visible = false;
                    }
                }
            }
        }

        private void btnFiltrar_Click(object sender, EventArgs e)
        {
            AtualizarGridDenuncias(MontarQueryFiltro(id, local, status, tipo, descricao));
        }
    }
}
