using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ListaPublica.Classes
{
    public class Banco
    {
        private static MySqlConnection conexao;
        public static MySqlDataReader reader { get; private set; }

        private static MySqlCommand cmd;


        private static string parametrosBancoAWS = "server=8.tcp.ngrok.io; Port=17548; uid=root;pwd=root; database=db_lista_publica; convert zero datetime=True";
        //private static string parametrosBancoAWS = "server=0.tcp.ngrok.io; Port=15770; uid=root;pwd=root; database=db_lista_publica; convert zero datetime=True";
        //private static string parametrosBancoAWS = "server=127.0.0.1; Port=3306; uid=root;pwd=P@ssw0rd; database=db_lista_publica; convert zero datetime=True";
        //private static string parametrosBancoAWS = "server=127.0.0.1; Port=3306; uid=root;pwd=root; database=db_lista_publica; convert zero datetime=True";

        public static int linhasAfetadas { get; private set; }
        public static void AbreConexao()
        {
            try
            {
                conexao = new MySqlConnection(parametrosBancoAWS);
                conexao.Open();
            }
            catch (Exception erro)
            {
                throw;
            }
        }
        public static void InserirQuery(string query) // SEM READER
        {

            if (reader != null)
                reader.Close(); // sempre fechar o reader, assim não criando vários processos da query (sobrecarregando o banco)


            cmd = new MySqlCommand(query, conexao);
            linhasAfetadas = cmd.ExecuteNonQuery();

        }

        public static int InserirQueryReader(string query) // COM READER
        {
            // ao chamar o metodo informa a query e um datareader , assim retornando-o

            /*    
            reader.Close(); // sempre fechar o reader, assim não criando vários processos da query (sobrecarregando o banco)
            */

            try
            {
                if (reader != null)
                    reader.Close();

                cmd = new MySqlCommand(query, conexao);
                reader = cmd.ExecuteReader();
                //linhasAfetadas = reader.RecordsAffected;

                return 0; //sucesso
            }
            catch (Exception erro)
            {
                return 1; //erro
            }


        }

        public static void FecharBanco()
        {
            try
            {
                conexao.Close();
            }
            catch
            {
                return;
            }

        }
    }
}
