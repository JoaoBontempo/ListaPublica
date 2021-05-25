package classes;
 
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
 
import javafx.scene.control.Alert.AlertType;
 
public final class Email {
 
	static final String destinatario = "lista.publica.adm@gmail.com";
	static final String[] remetente = {"lista.publica.client@gmail.com", "lista-public-client14492021"};
 
	static SimpleEmail email = new SimpleEmail();
 
	// o static vai executar toda vez que o software iniciar, ai n�o precisa chamar configurarEmail() nas fun��es
	static {
		try
		{
			email.setHostName("smtp.gmail.com");
			//email.setSmtpPort(465);
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator(remetente[0], remetente[1]));
			//email.setAuthenticator(new DefaultAuthenticator("testandoessaporra099@outlook.com.br", "Str0ngP@ssw0rd"));
			email.setSSLOnConnect(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
 
	/**
	 * Essa fun��o de sobreescrita vai funcionar com o e-mail destinat�rio PADR�O (lista.publica.adm@gmail.com)
	 * @param descricao
	 * @param titulo
	 * @return
	 */
	public static boolean enviarEmail(String descricao,String titulo)
	{
		//configurarEmail();
		try
		{
			email.setFrom(remetente[0]);
			email.setSubject(titulo); // Jogar "Nova den�ncia de parceiro: " + motivo como argumento dessa fun��o
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
 
	/**
	 * Essa fun��o de sobreescrita vai funcionar com o e-mail destinat�rio passado pelo ARGUMENTO
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
			email.setFrom(remetente[0]);
			email.setSubject(titulo); // Jogar "Nova den�ncia de parceiro: " + motivo como argumento dessa fun��o
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