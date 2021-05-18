using ListaPublica.Classes;
using Microsoft.AspNetCore.Mvc;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ListaPublica.Controllers
{
    [Route("listaPublica/[controller]")]
    [ApiController]
    public class ListaPublicaController : Controller
    {
        [HttpGet("getLast/{qntd}")]
        public IList<Telefone> ListarAlunos(int qntd)
        {
            Banco.AbreConexao();
            IList<Telefone> telefones = new List<Telefone>();

            Banco.InserirQueryReader("SELECT * FROM telefone " +
                "INNER JOIN parceiro ON telefone.dono = parceiro.id " +
                "LEFT JOIN endereco ON telefone.lugar = endereco.id ORDER BY telefone.id DESC LIMIT " + qntd);
            while (Banco.reader.Read())
            {
                Endereco endereco = null;
                try
                {
                    if (Banco.reader.GetInt32("endereco.id") != -1)
                    {
                        endereco = new Endereco();
                        endereco.bairro = Banco.reader.GetString("bairro");
                        endereco.cidade = Banco.reader.GetString("cidade");
                        endereco.estado = Banco.reader.GetString("estado");
                        endereco.id = Banco.reader.GetInt32("endereco.id");
                        endereco.numero = Banco.reader.GetInt32("endereco.numero");
                        endereco.rua = Banco.reader.GetString("rua");
                        endereco.nome = Banco.reader.GetString("endereco.nome");
                    }
                }
                catch
                {

                }

                Parceiro parceiro = new Parceiro();
                parceiro.id = Banco.reader.GetInt32("parceiro.id");
                parceiro.tipo = Convert.ToBoolean(Banco.reader.GetInt32("tipo"));
                if (parceiro.tipo)
                    parceiro.cnpj = Banco.reader.GetString("cnpj");
                else
                    parceiro.cpf = Banco.reader.GetString("cpf");
                parceiro.email = Banco.reader.GetString("email");
                parceiro.nome = Banco.reader.GetString("parceiro.nome");
                parceiro.usuario = Banco.reader.GetString("usuario");
                parceiro.enderecos.Add(endereco);

                Telefone telefone = new Telefone();
                telefone.endereco = endereco;
                telefone.descricao = Banco.reader.GetString("descricao");
                telefone.id = Banco.reader.GetInt32("telefone.id");
                telefone.numero = Banco.reader.GetString("telefone.numero");

                parceiro.telefones.Add(telefone);

                telefone.parceiro = parceiro;

                telefones.Add(telefone);
            }

            return telefones;
        }
    }
}
