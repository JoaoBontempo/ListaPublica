package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import classes.API;
import classes.Banco;
import classes.Denuncia;
import classes.Endereco;
import classes.EnderecoComDescricao;
import classes.Telefone;
import classes.Util;
import classes.UtilDashboard;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TelaLocal extends Application {

	// essa variável recebe da dashboard, quando ocorre um double click em um
	// registro do datagrid, para então fazer o processo dos dados
	private String idBuscarInfos = null;
	private boolean possuiImagem = false;
	private int id_endereco;
	private String conteudoImagem = "";
	private String fileName="";
	private Stage primaryStage;
	
	public void setIdBuscarInfos(String id) {
		this.idBuscarInfos = id;
	}

	@FXML
	private TextField txtNumeroResidencia;

	@FXML
	private Pane pnlTelefones;

	@FXML
	private Pane pnlImagem;

	@FXML
	private ImageView imgLocal;

	@FXML
	private TextField txtBairro;

	@FXML
	private TextField txtRua;

	@FXML
	private TextField txtCidade;

	@FXML
	private TextField txtEstado;

	@FXML
	private Label lblCnpj1;

	@FXML
	private TextField txtNome;

	@FXML
	private Label lbDenunciarLocal;

	@FXML
	private TextArea txtDescricao;

	@FXML
	private ListView<String> lvTelefones;

	@FXML
    void AbrirImagemBaixada(MouseEvent event) {
		File diretorio= new File("C:\\lista\\locais\\"+fileName);
		if(diretorio.exists()) {
			try {
				Process process = Runtime.getRuntime().exec("cmd /c start "+diretorio.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
    }
	
	@FXML
    void obterTelefoneClicado(MouseEvent event) throws Exception {
    	// api para obter o endereco a partir do telefone e a descricao
    	// primeiro pego o id do local
    	
    	try {
    		String telefone=lvTelefones.getSelectionModel().getSelectedItem();
    		Util.setTelefoneAtual(telefone);
        	//String query="select lugar,dono from telefone where numero='"+telefone+"';";
        	String url="http://localhost:5000/ListaPublica/getUserAddress/"+telefone;
        	JSONArray array=requisicaoGenerica(url); // passo o telefone e a api vai obter o id do local e fazer uma requisicao nesse id
    		JSONObject objeto=array.getJSONObject(0);
    		System.out.println(objeto.toString());
        	if(objeto.getString("imagem").length()>0) {
        		conteudoImagem=objeto.getString("imagem");
        		possuiImagem=true;
        		fileName=telefone+=".jpg";
        		Util.verificaExistenciaImagem(fileName, conteudoImagem.getBytes(), true);
        		trocarPosicaoPane();
        		imgLocal.setImage(new Image("file:///C:/lista/locais/"+fileName));
        	}
        	UtilDashboard.setIdDono(objeto.getString("id"));
        	insereCampos(objeto.get("estado").toString(),objeto.get("bairro").toString(),objeto.get("rua").toString(),
        			objeto.get("cidade").toString(),objeto.get("numero").toString(),objeto.get("descricao").toString(),
        			objeto.getString("nome"));
    	}catch(JSONException | NullPointerException e) {
    		// não faça nada, pois se entrar aqui é pq o array não tem posições, então ignore.
    	}
    	

	}

	void trocarPosicaoPane() {
		if (possuiImagem) {
			pnlTelefones.relocate(487, 287);
			pnlImagem.setVisible(true);
			imgLocal.setImage(new Image("Recursos/logo.png", 400, 204, true, true));
		} else {
			pnlImagem.setVisible(false);
			pnlTelefones.relocate(487, 74);
		}
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("telaLugar.fxml"));

			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Image icon = new Image("Recursos/logo.png");

			primaryStage.setScene(scene);
			primaryStage.setTitle("Lista Pública - Informações detalhadas do local");

			primaryStage.getIcons().add(icon);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initialize() throws Exception {
		// obtenho os valores do arraylist da tela anterior e jogo no ListView
		lvTelefones.getItems().addAll(UtilDashboard.getTelefones());

		
		// inicia a api
		iniciaApi();

		// verifica se o endereço possui imagem, se possuir jogue o pane de telefones
		// para baixo
		// padrao: 487,74
		// caso endereco possua imagem: 487,287
		trocarPosicaoPane();

	}

	@FXML
	public void MostrarTelaDenuncia() {
		Denuncia denuncia = new Denuncia();
		// denuncia.setDenunciado();
		denuncia.setDenunciador(Util.getContaLogada());
		denuncia.setLocal("Telefone");
		denuncia.setStatus(false);
		Util.setDenunciAtual(denuncia);
		TelaDenuncia tela = new TelaDenuncia();
		tela.start(new Stage());
	}

	JSONArray requisicaoGenerica(String url) {
		System.out.println("URL: "+url);
		ObjectMapper mapper = new ObjectMapper();
		String link = url;
		HttpGet get = new HttpGet(url);
		JSONArray obj=null;
		try {
			HttpClient httpClient = HttpClients.createDefault();
			HttpResponse response;
			response = httpClient.execute(get);

			String result = EntityUtils.toString(response.getEntity());
			obj = new JSONArray(result);
			System.out.println(obj.getJSONObject(0).toString());
			return obj;
		} catch (JSONException | IOException e) {
			e.printStackTrace();
			// não faça nada.
		}
		return obj;
	}

	void iniciaApi() {
		String result;
		
			
		ObjectMapper mapper = new ObjectMapper();
		String url = "http://localhost:5000/ListaPublica/getUserAddress/" + UtilDashboard.getNumeroTelefone();
		HttpGet get = new HttpGet(url);

		try {
			HttpClient httpClient = HttpClients.createDefault();
			HttpResponse response;
			response = httpClient.execute(get);

			result = EntityUtils.toString(response.getEntity());
			JSONArray obj = new JSONArray(result);
			System.out.println(obj.getJSONObject(0).toString());
			EnderecoComDescricao endereco;
			ArrayList<EnderecoComDescricao> enderecos = new ArrayList<>();

			for (int i = 0; i < obj.length(); i++) {
				endereco = mapper.readValue(obj.getJSONObject(i).toString(), EnderecoComDescricao.class);
				if(endereco.getImagem().length()>0) {
					possuiImagem=true;
					trocarPosicaoPane();
				}
				enderecos.add(endereco);
			}
			
			// JOGA OS VALORES NOS CAMPOS
			insereCampos(enderecos.get(0).getEstado(), enderecos.get(0).getBairro(), enderecos.get(0).getRua(),
					enderecos.get(0).getCidade(), Integer.toString(enderecos.get(0).getNumero()),
					enderecos.get(0).getDescricao(), enderecos.get(0).getNome());

		} catch (JSONException e) {
			// não faça nada (Entra aqui caso o JSONARray=0)
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	void insereCampos(String estado, String bairro, String rua, String cidade, String numero, String descricao,
			String nome) {
		this.txtBairro.setText(bairro);
		this.txtRua.setText(rua);
		this.txtCidade.setText(cidade);
		this.txtEstado.setText(estado);
		this.txtNumeroResidencia.setText(numero);
		this.txtDescricao.setText(descricao);
		this.txtNome.setText(nome);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
