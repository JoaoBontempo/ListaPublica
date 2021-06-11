package classes;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javafx.scene.control.Alert.AlertType;

public final class Email {

	static final String destinatario = "lista.publica.adm@gmail.com";
	static final String[] remetente = {"lista.publica.client@gmail.com", "lista-public-client14492021"};
	
	static Properties propriedades = new Properties();
	static Session session;
	// o static vai executar toda vez que o software iniciar, ai não precisa chamar configurarEmail() nas funções
	static {
		propriedades.put("mail.smtp.auth", "true");
		propriedades.put("mail.smtp.starttls.enable", "true");
		propriedades.put("mail.smtp.host", "smtp.gmail.com");
		propriedades.put("mail.smtp.port", "587");

		session = Session.getInstance(propriedades, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(remetente[0],remetente[1]);
			}
		});

	}

	/**
	 * Essa função de sobreescrita vai funcionar com o e-mail destinatário PADRÃO (lista.publica.adm@gmail.com)
	 * @param descricao
	 * @param titulo
	 * @return
	 * @throws MessagingException 
	 * @throws AddressException 
	 */


	public static boolean enviarEmail(String descricao,String titulo, ArrayList<String> moderadores)
	{
		try
		{
			Message mensagem = new MimeMessage(session);
			mensagem.setFrom(new InternetAddress(remetente[0]));
			for (String moderador : moderadores)
				mensagem.addRecipient(Message.RecipientType.TO, new InternetAddress(moderador));
			mensagem.setSubject(titulo);
			mensagem.setText(descricao);
			Transport.send(mensagem);
			return true;
		}
		catch (Exception erro)
		{
			Util.MessageBoxShow("Erro ao enviar e-mail", erro.getMessage(), AlertType.ERROR);
			return false;
		}
	}

	/**
	 * Essa função de sobreescrita vai funcionar com o e-mail destinatário passado pelo ARGUMENTO
	 * @param descricao
	 * @param titulo
	 * @return
	 */
	public static boolean enviarEmail(String descricao,String titulo,String destinatario)
	{

		if(!Validacao.validarEmail(destinatario)) {
			return false;
		}

		try
		{
			Message mensagem = new MimeMessage(session);
			mensagem.setFrom(new InternetAddress(remetente[0]));
			mensagem.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			mensagem.setSubject(titulo);
			mensagem.setText(descricao);
			Transport.send(mensagem);
			return true;
		}
		catch (Exception erro)
		{
			Util.MessageBoxShow("Erro ao enviar e-mail", erro.getMessage(), AlertType.ERROR);
			return false;
		}
	}




}