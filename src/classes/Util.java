package classes;

import java.io.IOException;
import java.net.http.HttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public final class Util {
	
	private static Parceiro contaLogada;
	
	public static Parceiro getContaLogada() {
		return contaLogada;
	}
	
	public static void setContaLogada(Parceiro contaLogada) {
		Util.contaLogada = contaLogada;
	}

	public static void MessageBoxShow(String titulo, String conteudo, AlertType tipo)
	{
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(conteudo);
		Stage stage = new Stage();
		stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("Recursos/logo.png"));
		alert.showAndWait();
	}
	
	public static JSONObject obtemInfosApiCep(String cep) {
		cep=cep.replace('.', '\0').replace('/', '\0');
		String webSite="https://viacep.com.br/ws/"+cep+"/json/";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(webSite);
		JSONObject obj=null;
		try {
			HttpResponse resp=client.execute(get);
			String retString=EntityUtils.toString(resp.getEntity());
			obj=new JSONObject(retString);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
		
		
	}
	
	public static String criptografarSenha(String senha)
	{
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}
	
	public static boolean verificarSenha(String senha, String hash)
	{
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(senha, hash);
	}
}
