using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Mail;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace SistemaModerador.Classes
{
    public static class Email
    {
        private static string[] remetente = { "lista.publica.client@gmail.com", "lista-public-client14492021" };
        private static ArrayList destinatarios = new ArrayList();
        private static string servidor = "smtp.gmail.com";
        private static int porta = 587;

        public static void EnviarEmail(string titulo, string descricao, string[] destinatarios) 
        {
            NetworkCredential credenciais = new NetworkCredential(remetente[0], remetente[1]);
            SmtpClient smtp = new SmtpClient(servidor, porta);
            smtp.Credentials = credenciais;
            smtp.EnableSsl = true;
            MailMessage conteudo = new MailMessage();
            conteudo.From = new MailAddress(remetente[0]);
            foreach (string destinatario in destinatarios)
            {
                if (destinatario.Equals("ignore"))
                    continue;
                conteudo.To.Add(destinatario);
            }
            conteudo.Priority = MailPriority.High;
            conteudo.Subject = titulo;
            conteudo.Body = descricao + "\n\n" +
                "Lista Pública de Telefones" +
                "\nEquipe moderadora - Moderador: " + Util.moderador;
            smtp.Send(conteudo);
            MessageBox.Show("E-mail enviado com sucesso!");
        }
    }
}
