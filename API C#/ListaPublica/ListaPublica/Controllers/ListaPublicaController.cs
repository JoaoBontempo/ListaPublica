using ListaPublica.Classes;
using ListaPublicaMeu.Classes;
using Microsoft.AspNetCore.Mvc;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ListaPublica.Controllers
{
    [Route("[controller]")]
    [ApiController]
    public class ListaPublicaController : Controller
    {
        [HttpGet("getLast/{qntd}")]
        public IList<Telefone> getLastPhones(int qntd)
        {
            return BuscarInfosBanco("SELECT telefone.*," +
               "parceiro.id as idP, parceiro.nome as nomeP, parceiro.tipo, parceiro.usuario as usuarioP, parceiro.email, parceiro.cpf, parceiro.cnpj, " +
               "endereco.id as idE, endereco.rua, endereco.bairro, endereco.cidade, endereco.estado, endereco.nome as nomeE, endereco.numero as numeroE " +
               "FROM telefone " +
               "INNER JOIN parceiro ON telefone.dono = parceiro.id " +
               "LEFT JOIN endereco ON telefone.lugar = endereco.id ORDER BY telefone.id DESC LIMIT " + qntd);
        }

        // Obtem o endereco do id X
        // id=id do local
        [HttpGet("getUserAddress/{id}")]
        public IList<EnderecoComDescricao> getUserAddress(int id)
        {
            IList<EnderecoComDescricao> enderecos = new List<EnderecoComDescricao>();
            string query = "select distinct e.rua,e.numero,e.bairro,e.estado,e.cidade,e.nome,t.descricao,t.dono from endereco as e , telefone as t INNER JOIN endereco ON(t.dono=endereco.usuario) WHERE e.id = "+id+";";
            Banco.AbreConexao();
            Banco.InserirQueryReader(query);

            while (Banco.reader.Read())
            {
                EnderecoComDescricao endereco = null;
                try
                {
                    try
                    {
                        endereco = new EnderecoComDescricao();
                        endereco.bairro = Banco.reader.GetString("bairro");
                        endereco.cidade = Banco.reader.GetString("cidade");
                        endereco.estado = Banco.reader.GetString("estado");
                        endereco.numero = Banco.reader.GetInt32("numero");
                        endereco.rua = Banco.reader.GetString("rua");
                        endereco.nome = Banco.reader.GetString("nome");
                        endereco.descricao = Banco.reader.GetString("descricao");
                    }
                    catch (Exception e) {
                        endereco.descricao = "";
                    }
                    enderecos.Add(endereco);
                }
                catch (Exception e)
                {
                    endereco = null;
                }
            }
            return enderecos;
        }


        private string retornarQuerySQL(string numero, string nome, string email, string cidade, string estado, string descricao)
        {
            string query = "SELECT telefone.*," +
               "parceiro.id as idP, parceiro.nome as nomeP, parceiro.tipo, parceiro.usuario as usuarioP, parceiro.email, parceiro.cpf, parceiro.cnpj, " +
               "endereco.id as idE, endereco.rua, endereco.bairro, endereco.cidade, endereco.estado, endereco.nome as nomeE, endereco.numero as numeroE " +
               "FROM telefone " +
               "INNER JOIN parceiro ON telefone.dono = parceiro.id " +
               "LEFT JOIN endereco ON telefone.lugar = endereco.id " +
               "WHERE telefone.id > 0 ";
            numero = numero.Equals("*") ? "" : String.Format("AND telefone.numero LIKE '%{0}%' ", numero);
            nome = nome.Equals("*") ? "" : String.Format("AND parceiro.nome LIKE '%{0}%'", nome);
            email = email.Equals("*") ? "" : String.Format("AND parceiro.email LIKE  '%{0}%'", email);
            cidade = cidade.Equals("*") ? "" : String.Format("AND endereco.cidade = '{0}'", cidade);
            estado = estado.Equals("*") ? "" : String.Format("AND endereco.estado = '{0}'", estado);
            descricao = descricao.Equals("*") ? "" : String.Format("AND telefone.descricao LIKE '%{0}%' ", descricao);

            query += String.Format("{0} {1} {2} {3} {4} {5} ORDER BY telefone.id DESC", nome, email, estado, cidade, numero, descricao);
            return query;
        }

        [HttpPost("getFiltro")]
        public IList<Telefone> getTelefoneFiltro(TableViewUtil dados)
        {
            return BuscarInfosBanco(retornarQuerySQL(dados.numero, dados.nome, dados.email, dados.cidade, dados.estado, dados.descricao));
        }

        private IList<Telefone> BuscarInfosBanco(string query)
        {
            IList<Telefone> telefones = new List<Telefone>();
            Banco.AbreConexao();
            Banco.InserirQueryReader(query);
            while (Banco.reader.Read())
            {
                Endereco endereco = null;
                try
                {
                    if (Banco.reader.GetInt32("idE") != -1)
                    {
                        endereco = new Endereco();
                        endereco.bairro = Banco.reader.GetString("bairro");
                        endereco.cidade = Banco.reader.GetString("cidade");
                        endereco.estado = Banco.reader.GetString("estado");
                        endereco.id = Banco.reader.GetInt32("idE");
                        endereco.numero = Banco.reader.GetInt32("numeroE");
                        endereco.rua = Banco.reader.GetString("rua");
                        endereco.nome = Banco.reader.GetString("nomeE");
                    }
                }
                catch
                {
                    endereco = null;
                }

                Parceiro parceiro = new Parceiro();
                parceiro.id = Banco.reader.GetInt32("idP");
                parceiro.tipo = Convert.ToBoolean(Banco.reader.GetInt32("tipo"));
                if (parceiro.tipo)
                    parceiro.cnpj = Banco.reader.GetString("cnpj");
                else
                    parceiro.cpf = Banco.reader.GetString("cpf");
                parceiro.email = Banco.reader.GetString("email");
                parceiro.nome = Banco.reader.GetString("nomeP");
                parceiro.usuario = Banco.reader.GetString("usuarioP");
                //parceiro.enderecos.Add(endereco);

                Telefone telefone = new Telefone();
                telefone.endereco = endereco;
                telefone.descricao = Banco.reader.GetString("descricao");
                telefone.id = Banco.reader.GetInt32("id");
                telefone.numero = Banco.reader.GetString("numero");
                //parceiro.telefones.Add(telefone);

                telefone.parceiro = parceiro;

                telefones.Add(telefone);
            }
            Banco.FecharBanco();
            return telefones;
        }
    }
}
