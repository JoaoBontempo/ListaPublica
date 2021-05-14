package classes;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

import javafx.scene.control.Alert.AlertType;

public final class Email {

	static final String destinatario = "lista.publica.adm@gmail.com";
	static final String[] remetente = {"lista.publica.client@gmail.com", "lista-public-client14492021"};

	static SimpleEmail email = new SimpleEmail();

	private static void configurarEmail()
	{
		try
		{
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator(remetente[0], remetente[1]));
			email.setSSLOnConnect(true);
		}
		catch (Exception e)
		{

		}
	}

	public static boolean enviarEmail(String motivo, String descricao)
	{
		configurarEmail();
		try
		{
			email.setFrom(remetente[0]);
			email.setSubject("Nova denúncia de parceiro: " + motivo);
			email.setMsg(descricao);
			email.addTo(destinatario);
			email.send();
			return true;
		}
		catch (Exception erro)
		{
			Util.MessageBoxShow("Erro ao enviar e-mail", erro.getMessage(), AlertType.ERROR);
			return false;
		}
	}

}
