package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
import classes.Comentario;
import classes.Denuncia;
import classes.Endereco;
import classes.EnderecoComDescricao;
import classes.Telefone;
import classes.TelefoneNumero;
import classes.Util;
import classes.UtilDashboard;
import classesTableView.ComentarioTable;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class TelaLocal extends Application {

	// essa variável recebe da dashboard, quando ocorre um double click em um
	// registro do datagrid, para então fazer o processo dos dados
	private String idBuscarInfos = null;
	private boolean possuiImagem = false;
	private int id_endereco;
	private int id_telefone;
	private String conteudoImagem = "";
	private String fileName="";
	private Stage primaryStage;
	private List<String> caracteresProibidosComentario=new ArrayList<String>();
	
	public void setIdBuscarInfos(String id) {
		this.idBuscarInfos = id;
	}

	@FXML
	private TextField txtNumeroResidencia;

	@FXML
    private TableColumn<TelefoneNumero, String> tvcTelefone;
	
	@FXML
    private TableView<TelefoneNumero> tvTelefone;
	
	@FXML
	private Pane pnlTelefones;

	@FXML
	private Pane pnlImagem;

	@FXML
    private Pane pnlComentarios;
	
	@FXML
    private TextField txtComentario;

    @FXML
    private Button btnEnviarComentario;
	
	@FXML
    private ListView<String> lvComentarios;
	
	@FXML
	private TableColumn<ComentarioTable, String> tvcUsuario;

	@FXML
	private TableColumn<ComentarioTable, String> tvcComentario;

	@FXML
	private TableColumn<ComentarioTable, String> tvcData;
	
	@FXML
	private ImageView imgLocal;

	@FXML
	private TextField txtBairro;

	@FXML
	private TextField txtRua;

	@FXML
	private TextField txtCidade;

	 @FXML
	private Pane pnlEnviarComentario;
	
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
    private TableView<ComentarioTable> tvComentarios;
	
    @FXML
    private ImageView imgWhatsApp;

    @FXML
    private Label lbWhatsApp;
	
	@FXML
    void RealizarComentario(ActionEvent event) {
		String comentario=txtComentario.getText();
		if(comentario.length()<=0) {
			Util.MessageBoxShow("Comentário inválido", "Digite um comentário antes de confirmar");
			return;
		}
		for(char ch:comentario.toCharArray()) {
			if(caracteresProibidosComentario.contains(Character.toString(ch))) {
				Util.MessageBoxShow("Comentário com caracteres proibidos", "O comentário possui o seguinte caractere inválido: "+ch+". Remova e tente novamente.");
				return;	
			}
		}
		try {
			String query="insert into comentarios values(default,"+UtilDashboard.getIdTelefone()+","+Util.getContaLogada().getId()+","+
					"'"+LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"','"+comentario+"');";
			System.out.println(query);
			Banco.InserirQuery(query);
			atualizarComentarios();
			txtComentario.clear();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@FXML
	public void ChamarNumeroWhatsApp() throws IOException, URISyntaxException
	{
		Util.ChamarNumeroWhatsApp(Util.FormatarSetTelefone(Util.getTelefoneAtual()));
	}
	
	private void atualizarComentarios() {
		
		List<ComentarioTable> comentarios=Util.RecuperarComentariosEndereco(this.id_telefone);
		ObservableList<ComentarioTable> observableComentario =
				FXCollections.observableArrayList(comentarios);
		if(comentarios!=null) {
			tvComentarios.getItems().clear();
			tvComentarios.setItems(observableComentario);
		}
		
	}

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

	@FXML
	void abrirDescricaoDetalhada(MouseEvent event) {
		//		DOUBLE CLICK NA LINHA
		if (event.getButton().equals(MouseButton.PRIMARY)){
	            if(tvTelefone.getSelectionModel().getSelectedItem() != null) {
	            	TelefoneNumero numero=tvTelefone.getSelectionModel().getSelectedItem();

	            	try {
	            		String telefone=numero.getNumero();
	            		Util.setTelefoneAtual(telefone);
	            		lbWhatsApp.setText(String.format("Chamar %s no WhatsApp", Util.FormatarGetTelefone(Util.getTelefoneAtual())));
	                	String url="http://localhost:5000/ListaPublica/getUserAddress/"+telefone;
	                	JSONArray array=null;
	                	JSONObject objeto=null;
	                	try {
	                		array=requisicaoGenerica(url); // passo o telefone e a api vai obter o id do local e fazer uma requisicao nesse id
		            		objeto=array.getJSONObject(0);
		            		id_endereco=objeto.getInt("id");
		            		id_telefone=Util.RecuperarIdTelefonePorTelefone(telefone);
	                	}catch(Exception e) {
	                		Util.MessageBoxShow("Telefone sem endereço", "O telefone solicitado não possui nenhum endereço atribuido");
	                	}
	                	
	            		
	            		System.out.println(objeto.toString());
	                	if(objeto.getString("imagem").length()>0) {
	                		conteudoImagem=objeto.getString("imagem");
	                		possuiImagem=true;
	                		fileName=telefone+=".jpg";
	                		Util.verificaExistenciaImagem(fileName, conteudoImagem.getBytes(), true);
	                		imgLocal.setImage(new Image("file:///C:/lista/locais/"+fileName));
	                	}
	                	//UtilDashboard.setIdDono(objeto.getString("id"));
	                	insereCampos(objeto.get("estado").toString(),objeto.get("bairro").toString(),objeto.get("rua").toString(),
	                			objeto.get("cidade").toString(),objeto.get("numero").toString(),objeto.get("descricao").toString(),
	                			objeto.getString("nome"));
	                	List<ComentarioTable> comentarios=Util.RecuperarComentariosEndereco(id_telefone); // Esse utilDashboard será usado apenas ao entrar no form
	            		if(comentarios != null) {
	            			ObservableList<ComentarioTable> observableComentario =
		            				FXCollections.observableArrayList(comentarios);	
	            			tvComentarios.setItems(observableComentario);
	            		}
	                	
	            	}catch(JSONException | NullPointerException e) {
	            		// não faça nada, pois se entrar aqui é pq o array não tem posições, então ignore.
	            	} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	
	            	
	            }
            }
	}
	
	public void initialize() throws Exception {
		lbDenunciarLocal.setDisable(Util.isConvidado());

		id_telefone=UtilDashboard.getIdTelefone();
		
		caracteresProibidosComentario=Arrays.asList("'");
		tvcTelefone.setCellValueFactory(new PropertyValueFactory("numero"));
		
		// verifica se está logado ou não (caso esteja logado apareça o pane de textbox e button)
		if(Util.getContaLogada() != null) {
			pnlEnviarComentario.setVisible(true);
		}else {
			pnlEnviarComentario.setVisible(false);
		}
		
		// obtenho os valores do arraylist da tela anterior e jogo no ListView
		ObservableList<TelefoneNumero> observableTelefones =
				FXCollections.observableArrayList(UtilDashboard.getTelefones());
		tvTelefone.setItems(observableTelefones);
		

		// verifica se o endereço possui comentário associado
		List<ComentarioTable> comentarios=Util.RecuperarComentariosEndereco(id_telefone); // Esse utilDashboard será usado apenas ao entrar no form
		ObservableList<ComentarioTable> observableComentario =
				FXCollections.observableArrayList(comentarios);
		tvcUsuario.setCellValueFactory(new PropertyValueFactory("usuario"));
		tvcComentario.setCellValueFactory(new PropertyValueFactory("comentario"));
		tvcData.setCellValueFactory(new PropertyValueFactory("dataComentario"));
		
		
		tvComentarios.setItems(observableComentario);
		
		// inicia a api
		iniciaApi();
		lbWhatsApp.setText(String.format("Chamar %s no WhatsApp", Util.FormatarGetTelefone(Util.FormatarSetTelefone(Util.getTelefoneAtual()))));
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
		String telefone="";
		
		// jogar a função abaixo
		
		//telefone=UtilDashboard.getNumeroTelefone().replace(" ","&");
		
		
		String url = "http://localhost:5000/ListaPublica/getUserAddress/" + telefone;
		HttpGet get = new HttpGet(url);

		try {
			HttpClient httpClient = HttpClients.createDefault();
			HttpResponse response;
			response = httpClient.execute(get);

			result = EntityUtils.toString(response.getEntity());
			JSONArray obj = new JSONArray(result);
			EnderecoComDescricao endereco;
			ArrayList<EnderecoComDescricao> enderecos = new ArrayList<>();

			for (int i = 0; i < obj.length(); i++) {
				endereco = mapper.readValue(obj.getJSONObject(i).toString(), EnderecoComDescricao.class);
				if(endereco.getImagem().length()>0) {
					possuiImagem=true;
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
