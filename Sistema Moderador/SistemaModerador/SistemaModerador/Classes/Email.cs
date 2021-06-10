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
        private static SmtpClient smtp;
        private static NetworkCredential credenciais;

        private static void ConfigurarCredenciais()
        {
            credenciais = new NetworkCredential(remetente[0], remetente[1]);
            smtp = new SmtpClient(servidor, porta);
            smtp.Credentials = credenciais;
            smtp.EnableSsl = true;
        }

        public static void EnviarEmail(string senha, string usuario, string email)
        {
            ConfigurarCredenciais();
            MailMessage conteudo = new MailMessage();
            conteudo.From = new MailAddress(remetente[0]);
            conteudo.To.Add(email);
            conteudo.Priority = MailPriority.High;
            conteudo.Subject = "Bem vindo ao Sistema Moderador da Lista Pública de Telefones";
            conteudo.Body = "Parabéns! Você foi cadastrado no Sistema Moderador da Lista Pública de Telefones!" +
                "\n\n" +
                "O moderador " + Util.moderador.getUsuario() + " cadastrou as seguintes informações para você: " +
                "\n\n" +
                "Usuário: " + usuario +
                "\n" +
                "Senha: " + senha +
                "\n\n" +
                "Por favor, altere sua senha ao realizar o login no sistema." +
                "\n\n" +
                "Agrademos sua contribuição para a melhoria da Lista Pública de Telefones!"; 
            smtp.Send(conteudo);
        }

        public static void EnviarEmail(string senha)
        {
            ConfigurarCredenciais();
            MailMessage conteudo = new MailMessage();
            conteudo.From = new MailAddress(remetente[0]);
            conteudo.To.Add(Util.moderador.getEmail());
            conteudo.Priority = MailPriority.High;
            conteudo.Subject = "Sua senha do sistema moderador foi alterada";
            conteudo.Body = "Alguém acessou sua conta no sistema moderador e tentou realizar operações." +
                "\n" +
                "O código enviado anteriormente ao seu e-mail foi inserido de forma incorreta, portanto, sua senha foi alterada para maior segurança." +
                "\n\n" +
                "Sua nova senha é: " + senha +
                "\n\n" +
                "Utilize esta senha para logar e altere-a imediatamente ao entrar no sistema moderador novamente." +
                "\n" +
                "Agradecemos a colaboração." +
                "\n\n" +
                "Lista Pública de telefones - Sistema moderador";
            smtp.Send(conteudo);
        }

        public static void EnviarEmail(string titulo, string descricao, string[] destinatarios) 
        {
            ConfigurarCredenciais();
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
            if (!Util.isCodigo)
                conteudo.Body = descricao + "\n\n" +
                    "Lista Pública de Telefones" +
                    "\nEquipe moderadora - Moderador: " + Util.moderador;
            else
                conteudo.Body = descricao;
            
            smtp.Send(conteudo);
            if (!Util.isCodigo)
                MessageBox.Show("E-mail enviado com sucesso!");

            Util.isCodigo = false;
        }
    }
}
