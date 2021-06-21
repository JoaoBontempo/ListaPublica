package classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import application.Dashboard;
import application.UCEnderecoController;
import application.UCTelefoneController;
import classesTableView.ComentarioTable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public final class Util {

	/* UTILIDADES DO USER CONTROL TELEFONES */
	public static Telefone telefone;
	public static ArrayList<Endereco> todoEnderecos = new ArrayList<Endereco>();
	public static ArrayList<Endereco> enderecosAtuais = new ArrayList<Endereco>();
	public static int idEndereco;
	public static Dashboard dashboard = new Dashboard();
	public static ArrayList<Telefone> telefones = new ArrayList<Telefone>();
	public static int index;
	public static int indexEndereco;
	public static ArrayList<Node> nodos = new ArrayList<Node>();
	/**/

	/* UTILIDADES DO USER CONTROL ENDERECOS */
	public static Endereco endereco;
	public static ArrayList<Endereco> Enderecos = new ArrayList<Endereco>();
	public static ArrayList<Node> nodes = new ArrayList<Node>();
	public static ArrayList<UCEnderecoController> controladorEndereco = new ArrayList<UCEnderecoController>();
	/**/

	private static Parceiro contaLogada;
	private static Denuncia denunciAtual;
	private static TableViewUtil telefoneAtual;

	private static boolean convidado = false;

	public static Parceiro getContaLogada() {
		return contaLogada;
	}

	public static void setContaLogada(Parceiro contaLogada) {
		Util.contaLogada = contaLogada;
	}

	public static int recuperarIdDonoAtravesTelefone(String telefone) {
		String query = "select dono from telefone where numero='" + telefone + "';";
		try {
			Banco.InserirQueryReader(query);
			if (Banco.getReader().next()) {
				return Banco.getReader().getInt("dono");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;
	}

	public static Parceiro recuperarValoresUsuarioTelaLocal(int idDono) {
		if (idDono < 0)
			return null;
		Parceiro p = null;
		try {
			Banco.InserirQueryReader("select usuario,email,nome from parceiro where id=" + idDono);
			if (Banco.getReader().next()) {
				p = new Parceiro(Banco.getReader().getString("nome"), Banco.getReader().getString("email"),
						Banco.getReader().getString("usuario"));
				return p;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String converterStringParaBase64(String caminho) {
		String base64 = "";
		try {
			base64 = Base64.getEncoder().encodeToString(Files.readAllBytes(Path.of(caminho)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return base64;
	}

	public static int RecuperarIdTelefonePorTelefone(String telefone) {
		try {
			telefone = telefone.replace('+', '\s');
			Banco.InserirQueryReader("select id from telefone where numero='" + telefone + "';");
			if (Banco.getReader().next()) {
				return Banco.getReader().getInt("id");
			}
		} catch (Exception e) {
			return -1;
		}
		return -1;
	}

	public static String RecuperarNomeUsuarioPorId(int id) {
		try {
			Banco.InserirQueryReader("select usuario from parceiro where id=" + id + ";");
			if (Banco.getReader().next()) {
				return Banco.getReader().getString("usuario");
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public static List<ComentarioTable> RecuperarComentariosEndereco(int idTelefone) {
		List<ComentarioTable> comentarios = null;
		try {
			Banco.InserirQueryReader(
					"select c.idTelefone,c.idParceiro,c.dataHorario,c.comentario,p.usuario from comentarios as c,parceiro as p where idTelefone="
							+ idTelefone + " and p.id=c.idParceiro;");
			comentarios = new ArrayList<ComentarioTable>();
			while (Banco.getReader().next()) {
				SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
				String data = dt
						.format(new Date(Banco.getReader().getString("dataHorario").replace("-", "/").split("\s")[0]));

				ComentarioTable c = new ComentarioTable(Banco.getReader().getString("usuario"),
						Banco.getReader().getString("comentario"), data);
				comentarios.add(c);
			}
			return comentarios;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void RecuperarInformacoesEndereco(int id) throws SQLException {
		try {
			Endereco endereco = new Endereco();
			endereco.setId(id);
			ResultSet result = Banco.InserirQueryReader("SELECT * FROM endereco WHERE id = " + id);
			result.next();
			endereco.setRua(result.getString("rua"));
			endereco.setBairro(result.getString("bairro"));
			endereco.setNumero(result.getInt("numero"));
			endereco.setCidade(result.getString("cidade"));
			endereco.setEstado(result.getString("estado"));
			endereco.setNome(result.getString("nome"));
			denunciAtual.setEndereco(endereco);
		} catch (Exception erro) {

		}
	}

	public static String FormatarSetTelefone(String telefone, String tipo) {
		if (tipo.equals("outro"))
			return telefone;
		telefone = telefone.replace("(", "");
		telefone = telefone.replace(")", "");
		telefone = telefone.replace(" ", "");
		telefone = telefone.replace("-", "");
		return telefone;
	}

	public static void ChamarNumeroWhatsApp(String numero) throws IOException, URISyntaxException {
		java.awt.Desktop.getDesktop().browse(new java.net.URI("https://api.whatsapp.com/send?phone=55" + numero));
	}

	public static void AbrirLinkGoogleMaps(String rua, String numero, String bairro, String cidade, String estado)
			throws IOException, URISyntaxException {
		rua = URLEncoder.encode(rua, "UTF-8");
		estado = URLEncoder.encode(estado, "UTF-8");
		cidade = URLEncoder.encode(cidade, "UTF-8");
		numero = URLEncoder.encode(numero, "UTF-8");
		bairro = URLEncoder.encode(bairro, "UTF-8");
		String url = String.format("https://www.google.com.br/maps/search/%s,+%s+-+%s+%s+-+%s", rua, numero, bairro,
				cidade, estado);
		java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
	}

	private static String ColocarTracoTelefone(String telefone, int index) {
		String telFormatado = "";
		for (int i = 0; i < telefone.length(); i++) {
			telFormatado += telefone.charAt(i);
			if (index - 1 == i)
				telFormatado += "-";
		}
		return telFormatado;
	}

	public static String FormatarGetTelefone(String telefone, String tipo) {
		if (tipo.equals("outro"))
			return telefone;
		if (telefone.length() <= 1)
			return telefone;
		String ddd = telefone.substring(0, 2);
		telefone = telefone.substring(2, telefone.length());

		if (tipo.equals("fixo"))
			telefone = ColocarTracoTelefone(telefone, 4);
		else
			telefone = ColocarTracoTelefone(telefone, 5);

		return String.format("(%s) %s", ddd, telefone);
	}

	/*
	 * public static void LimparCboxEmBranco(ComboBox<String> cbox) { for (int i =
	 * 0; i < cbox.getItems().size(); i++) { if
	 * (Validacao.isNullOrEmpty(cbox.getItems().get(i).toString())) {
	 * cbox.getItems().remove(i); } } }
	 */

	public static void RecuperarInformacoesDenunciado(int id) throws SQLException {
		Parceiro parceiro = new Parceiro();
		ResultSet result = Banco.InserirQueryReader("SELECT * FROM parceiro WHERE id = " + id);
		result.next();
		parceiro.setId(id);
		parceiro.setNome(result.getString("nome"));
		parceiro.setEmail(result.getString("email"));
		parceiro.setTipo(result.getInt("tipo"));
		if (parceiro.getTipo())
			parceiro.setCpf(result.getString("cpf"));
		else
			parceiro.setCnpj(result.getString("cnpj"));
		parceiro.setUsuario(result.getString("usuario"));
		denunciAtual.setDenunciado(parceiro);
	}

	public static boolean verificaTamanhoImagem(Long tamanhoLimite, File arquivo) {
		return arquivo.length() > tamanhoLimite;
	}

	public static void RecuperarInformacoesTelefoneAtual() throws SQLException {
		Telefone telefone = new Telefone();
		ResultSet result = Banco.InserirQueryReader("SELECT * FROM telefone WHERE numero = '"
				+ Util.FormatarSetTelefone(telefoneAtual.getNumero(), telefoneAtual.getTipo()) + "'");
		result.next();
		telefone.setId(result.getInt("id"));
		telefone.setDescricao(result.getString("descricao"));
		telefone.setNumero(telefoneAtual.getNumero());
		denunciAtual.setTelefone(telefone);
	}

	public static boolean verificaExistenciaImagem(String nomeArquivo, byte[] conteudo, boolean local)
			throws Exception {
		// diret�rio padr�o= C:\\img\\locais
		// C:\\img\\usuarios
		// local=False = pasta de usuario
		// local=True = pasta de local

		String diretorioRaiz = "C:\\lista";
		String diretorioPadraoLocal = "C:\\lista\\locais";
		String diretorioPadraoUsuarios = "C:\\lista\\usuarios";
		File fileLocal = new File(diretorioPadraoLocal);
		File fileUsuario = new File(diretorioPadraoUsuarios);
		File fileDiretorioRaiz = new File(diretorioRaiz);

		fileDiretorioRaiz.mkdir();
		fileLocal.mkdir();
		fileUsuario.mkdir();

		String salvarEm = local == true ? diretorioPadraoLocal : diretorioPadraoUsuarios;
		salvarEm += "\\" + nomeArquivo;

		byte[] retorno = Base64.getDecoder().decode(conteudo);
		Files.write(Path.of(salvarEm), retorno, StandardOpenOption.CREATE);
		return true;
	}

	public static Alert MessageBoxShow(String titulo, String conteudo, AlertType tipo) {
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

	public static ButtonType MessageBoxShow(String titulo, String conteudo) {
		Alert alert = MessageBoxShow(titulo, conteudo, AlertType.CONFIRMATION);
		return alert.getResult();
		// return alert;
	}

	public static JSONObject obtemInfosApiCep(String cep) {
		cep = cep.replace('.', '\0').replace('/', '\0');
		String webSite = "https://viacep.com.br/ws/" + cep + "/json/";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(webSite);
		JSONObject obj = null;
		try {
			HttpResponse resp = client.execute(get);
			String retString = EntityUtils.toString(resp.getEntity());
			obj = new JSONObject(retString);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;

	}

	public static void ChangeRadioButtons(RadioButton[] buttons, int selected) {
		for (int i = 0; i < buttons.length; i++) {
			if (i == selected)
				buttons[i].setSelected(true);
			else
				buttons[i].setSelected(false);
		}
	}

	public static String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}

	public static boolean verificarSenha(String senha, String hash) {
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

	public static TableViewUtil getTelefoneAtual() {
		return telefoneAtual;
	}

	public static void setTelefoneAtual(TableViewUtil telefoneAtual) {
		Util.telefoneAtual = telefoneAtual;
	}
}
