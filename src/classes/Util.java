package classes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

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
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public final class Util {
	
	private static Parceiro contaLogada;
	private static Denuncia denunciAtual;
	
	private static boolean convidado = false;
	
	public static Parceiro getContaLogada() {
		return contaLogada;
	}
	
	public static void setContaLogada(Parceiro contaLogada) {
		Util.contaLogada = contaLogada;
	}
	
	public static boolean verificaExistenciaImagem(String nomeArquivo,byte[] conteudo,boolean local) throws Exception {
		// diretório padrão= C:\\img\\locais
		//					 C:\\img\\usuarios
		// local=False = pasta de usuario
		// local=True  = pasta de local
		List<String> extensoesPermitidas;
		extensoesPermitidas=Arrays.asList(".jpg",".png");
		
//		Pattern extens = Pattern.compile("([\\w\\-]+)");
//		Matcher match=extens.matcher(nomeArquivo);
//		System.out.println("Matches :"+match.matches());
//		if(match.matches()) {
//			System.out.println("GROUp 1:"+match.group(1));
//			if(!extensoesPermitidas.contains(match.group(1))) {
//				throw new Exception("Extensão do arquivo inválida. Não é do tipo jpg.");
//			}
//		}
		
		
		String diretorioRaiz="C:\\lista";
		String diretorioPadraoLocal="C:\\lista\\locais";
		String diretorioPadraoUsuarios="C:\\lista\\usuarios";
		File fileLocal=new File(diretorioPadraoLocal);
		File fileUsuario=new File(diretorioPadraoUsuarios);
		File fileDiretorioRaiz=new File(diretorioRaiz);
		
		fileDiretorioRaiz.mkdir();
		fileLocal.mkdir();
		fileUsuario.mkdir();
		
		String salvarEm=local==true?diretorioPadraoLocal:diretorioPadraoUsuarios;
		salvarEm+="\\"+nomeArquivo;

		
		
		return true;
		
		
	}
	
	public static Alert MessageBoxShow(String titulo, String conteudo, AlertType tipo)
	{
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(conteudo);
		Stage stage = new Stage();
		stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("Recursos/logo.png"));
		alert.showAndWait();
		return alert;
	}
	
	public static ButtonType MessageBoxShow(String titulo, String conteudo)
	{
		Alert alert = MessageBoxShow(titulo, conteudo, AlertType.CONFIRMATION);
		return alert.getResult();
		//return alert;
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

	public static boolean isConvidado() {
		return convidado;
	}

	public static void setConvidado(boolean value) {
		convidado = value;
	}

	public static Denuncia getDenunciAtual() {
		return denunciAtual;
	}

	public static void setDenunciAtual(Denuncia denunciAtual) {
		Util.denunciAtual = denunciAtual;
	}
}
