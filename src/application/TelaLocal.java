package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
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
import classes.ComentarioUtil;
import classes.Denuncia;
import classes.Endereco;
import classes.EnderecoComDescricao;
import classes.Parceiro;
import classes.TableViewUtil;
import classes.Telefone;
import classes.TelefoneNumero;
import classes.Util;
import classes.UtilDashboard;
import classesTableView.ComentarioTable;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
	//private Stage primaryStage;
	private List<String> caracteresProibidosComentario=new ArrayList<String>();
	
	public void setIdBuscarInfos(String id) {
		this.idBuscarInfos = id;
	}
	
	private Event evento;
	
	public void setEvento(Event evento)
	{
		this.evento = evento;
	}
	
	@FXML
	private TextField txtNumeroResidencia;

	@FXML
    private TableColumn<TelefoneNumero, String> tvcTelefone;
	
	@FXML
    private TableView<TelefoneNumero> tvTelefone;
    @FXML
    private TableColumn<TelefoneNumero, String> tvcTipo;
	
	@FXML
    private Tab tabInfosEndereco;
	
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
    private TextField txtNomeUsuario;

    @FXML
    private TextField txtNomeCompleto;

    @FXML
    private ScrollPane scrlComments;
    
    @FXML
    private TextField txtEmail;

    @FXML
    private BorderPane pnlBorderImagem;
    
    @FXML
    private BorderPane pnlImagemDono;

    @FXML
    private ImageView imgProprietario;
    
	@FXML
	private TableColumn<ComentarioTable, String> tvcUsuario;

	@FXML
    private Label lblTelefoneSelecionado;
	
	@FXML
	private TableColumn<ComentarioTable, String> tvcComentario;

	 @FXML
	 private TabPane tabPaneInfos;
	 
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
	private VBox vboxComentarios;
	 
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
    private ScrollBar scrlComentarios;
    
    @FXML
    private ImageView imgAbrirMaps;

    @FXML
    private Label lbAbrirMaps;
    
    @FXML
    private Label lbWhatsApp;
    
    @FXML
    private FlowPane flpComentarios;
	
    private int idDono;
    
	@FXML
    void RealizarComentario(ActionEvent event) throws IOException {
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
			String query="insert into comentarios values(default,"+id_telefone+","+Util.getContaLogada().getId()+","+
					"'"+LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"','"+comentario+"');";
			Banco.InserirQuery(query);
			AtualizarComentarioUtil(Util.getContaLogada().getUsuario(), LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), comentario);
			atualizarComentarios();
			txtComentario.clear();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	private void AtualizarComentarioUtil(String usuario, String data, String comentario)
	{
		ComentarioUtil.setComentario(comentario);
		ComentarioUtil.setData(data);
		ComentarioUtil.setUsuario(usuario);
	}
	
	@FXML
	public void ChamarNumeroWhatsApp() throws IOException, URISyntaxException
	{
		Util.ChamarNumeroWhatsApp(Util.FormatarSetTelefone(Util.getTelefoneAtual().getNumero(), Util.getTelefoneAtual().getTipo()));
	}
	
	private void atualizarComentarios() throws IOException {
		
		List<ComentarioTable> comentarios=Util.RecuperarComentariosEndereco(this.id_telefone);
		ObservableList<ComentarioTable> observableComentario =
				FXCollections.observableArrayList(comentarios);
		if(comentarios!=null) {
			this.vboxComentarios.getChildren().clear();
			for(ComentarioTable t:comentarios) {
				
				ComentarioUtil.setComentario(t.getComentario());
				ComentarioUtil.setData(t.getDataComentario());
				ComentarioUtil.setUsuario(t.getUsuario());
				
				TelaComentario comentarioTela = new TelaComentario();
    			comentarioTela.setPane(this.vboxComentarios);
    			comentarioTela.loadFxml();
			}
		}
	}
	
	public void getEvent(Event evento) {
		this.evento = evento;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("telaLugar.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Image icon = new Image("Recursos/logo.png");
			primaryStage.initModality(Modality.WINDOW_MODAL);
			primaryStage.initOwner(((Node)evento.getSource()).getScene().getWindow());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Lista Pública - Informações detalhadas do local");
			primaryStage.getIcons().add(icon);
			primaryStage.setResizable(false);
			primaryStage.show();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void buscarInfosDono(int idDono,boolean comImagem) {
		// esse método obtem os campos nome,usuario e email do dono referente ao numero do telefone e atribui os valores nos respectivos campos
		Parceiro ret=Util.recuperarValoresUsuarioTelaLocal(idDono);
		if(ret!=null) {
			if(comImagem)
				buscarTodasImagens();
			txtNomeUsuario.setText(ret.getUsuario());
			txtEmail.setText(ret.getEmail());
			txtNomeCompleto.setText(ret.getNome());
		
		}
	}
	
	@FXML
	public void AbrirLinkMaps() throws IOException, URISyntaxException
	{
		Util.AbrirLinkGoogleMaps(txtRua.getText(), txtNumeroResidencia.getText(), txtBairro.getText(), txtCidade.getText(), txtEstado.getText());
	}

	void buscarTodasImagens() {
		// esse método vai buscar as imagens de usuário e do endereço e atribuir as ImageView
		
		try {
			obterImagemParceiro();
			obterImagemLocal();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void obterImagemParceiro() throws SQLException, Exception {
		//verifica se a imagem ja existe no cache(nos diretórios)
		String caminho="C:\\lista\\usuarios\\profile"+idDono+".jpg";
//		File f = new File(caminho);
//		
//		byte[] bytesArq=Files.readAllBytes(Path.of(caminho));
//		byte[] bytesArqBase64=Base64.getEncoder().encode(bytesArq);
//		
		String query="select imagem from parceiro where id="+idDono;
		Banco.InserirQueryReader(query);
		
		if(Banco.getReader().next()) {
			String imagem=Banco.getReader().getString("imagem");
			if(imagem != null) {
				if(imagem.length()>0) {
					Util.verificaExistenciaImagem("profile"+idDono+".jpg", imagem.getBytes(), false);
					
					Image img=new Image(new File(caminho).toURI().toString(), 400, 400, false, false);
					imgProprietario.setImage(img);
				}	
			}
		}
		
		
	}
	
	private void obterImagemLocal() throws SQLException, Exception {
		
		String caminho="C:\\lista\\locais\\addr"+id_endereco+".jpg";
		File f = new File(caminho);
		
			String query="select imagem from endereco where id="+id_endereco;
			
			Banco.InserirQueryReader(query);
			
			if(Banco.getReader().next()) {
				String imagem=Banco.getReader().getString("imagem");
				if(imagem != null) {
					if(imagem.length()>0) {
						Util.verificaExistenciaImagem("addr"+id_endereco+".jpg", imagem.getBytes(), true);
						Image img=new Image(new File(caminho).toURI().toString(), 1920, 1080, false, false);
						imgLocal.setImage(img);
						
					}	
				}
			}
		
	}
	
	
	@FXML
	void abrirDescricaoDetalhada(MouseEvent event) {
		//		DOUBLE CLICK NA LINHA
		if (event.getButton().equals(MouseButton.PRIMARY)){
	            if(tvTelefone.getSelectionModel().getSelectedItem() != null) {
	            	tabInfosEndereco.setDisable(false);
	            	TelefoneNumero numero=tvTelefone.getSelectionModel().getSelectedItem();
	            	
	            	try {
	            		//String telefone=numero.getNumero();
	            		idDono=Util.recuperarIdDonoAtravesTelefone(numero.getNumero());
	            		Util.setTelefoneAtual(new TableViewUtil(numero.getNumero(), numero.getTipo()));
	            		lbWhatsApp.setText(String.format("Chamar %s no WhatsApp", Util.FormatarGetTelefone(numero.getNumero(), numero.getTipo())));
	            		
	            		String numeroStr=numero.getNumero().replace('\s', '+');
	                	String url="http://localhost:5000/ListaPublica/getUserAddress/" + numeroStr;
	                	JSONArray array=null;
	                	JSONObject objeto=null;
	                	try {
	                		id_telefone=Util.RecuperarIdTelefonePorTelefone(numeroStr);
		            		
	                		if(id_telefone==-1) {
	                			Util.MessageBoxShow("Telefone inexistente", "O telefone selecionado não existe. O telefone pode ter sido removido pelo moderador.");
	                			tvTelefone.getItems().remove(numero);
	                			
	                			return;
		            		}
		            		
	                		// verifica se o telefone possui descrição
		            		verificaDescricaoTelefone(id_telefone);
	                		
	                		array=requisicaoGenerica(url); // passo o telefone e a api vai obter o id do local e fazer uma requisicao nesse id
		            		objeto=array.getJSONObject(0);
		            		id_endereco=objeto.getInt("id");
		            		
		            		
		            		imgAbrirMaps.setVisible(true);
		            		lbAbrirMaps.setVisible(true);
	                	}catch(Exception e) {
	                		telefoneSemEndereco();
	                	}
	                	
	                	if(objeto.getString("imagem").length()>0) {
	                		conteudoImagem = objeto.getString("imagem");
	                		possuiImagem = true;
	                		fileName = numero.getNumero() + ".jpg";
	                		Util.verificaExistenciaImagem(fileName, conteudoImagem.getBytes(), true);
	                		imgLocal.setImage(new Image("file:///C:/lista/locais/"+fileName));
	                	}
	                	insereCampos(objeto.get("estado").toString(),objeto.get("bairro").toString(),objeto.get("rua").toString(),
	                			objeto.get("cidade").toString(),objeto.get("numero").toString(),objeto.getString("nome"));
	                	List<ComentarioTable> comentarios=Util.RecuperarComentariosEndereco(id_telefone); // Esse utilDashboard será usado apenas ao entrar no form
	            		if(comentarios != null) {
	            			ObservableList<ComentarioTable> observableComentario =
		            				FXCollections.observableArrayList(comentarios);	
	            			this.vboxComentarios.getChildren().clear();
	            			for(ComentarioTable t:observableComentario) {
	            				ComentarioUtil.setComentario(t.getComentario());
	            				ComentarioUtil.setData(t.getDataComentario());
	            				ComentarioUtil.setUsuario(t.getUsuario());
	            				
	            				TelaComentario comentarioTela = new TelaComentario();
		            			comentarioTela.setPane(this.vboxComentarios);
		            			comentarioTela.loadFxml();
	            			}
	 
	            		}
	            		
	            		buscarInfosDono(idDono,false);
	                	
	            	}catch(JSONException | NullPointerException e) {
	            		// não faça nada, pois se entrar aqui é pq o array não tem posições, então ignore.
	            		e.printStackTrace();
	            	} catch (Exception e) {
	            		
						e.printStackTrace();
					}
	            	
	            	
	            }
            }
	}
	
	private void verificaDescricaoTelefone(int id_telefone) {
		try {
			Banco.InserirQueryReader("select descricao from telefone where id="+id_telefone+";");
			if(Banco.getReader().next()) {
				txtDescricao.setText(Banco.getReader().getString("descricao"));
			}else {
				txtDescricao.setText("N/D");
			}
		} catch (SQLException e) {
			txtDescricao.setText("N/D");
			e.printStackTrace();
		}
		
		
	}

	private void telefoneSemEndereco() {
		imgAbrirMaps.setVisible(false);
		lbAbrirMaps.setVisible(false);
		txtEstado.setText("N/D");
		txtCidade.setText("N/D");
		txtBairro.setText("N/D");
		txtRua.setText("N/D");
		txtNumeroResidencia.setText("N/D");
		txtNome.setText("N/D");
		txtComentario.setText("N/D");
		txtNomeCompleto.setText("N/D");
		txtNomeUsuario.setText("N/D");
		txtEmail.setText("N/D");
		tabInfosEndereco.setDisable(true);
		
		
		tvComentarios.getItems().clear();
		
	}

	public void initialize() throws Exception {
		lbDenunciarLocal.setDisable(Util.isConvidado());
		
		id_telefone=UtilDashboard.getIdTelefone();
		
		tvcTelefone.setStyle("-fx-alignment: CENTER;");
		
		verificaDescricaoTelefone(id_telefone);
		this.idDono= Integer.parseInt(UtilDashboard.getIdDono());
		this.id_endereco=Integer.parseInt(UtilDashboard.getIdLugar());
		
		
		
		buscarInfosDono(idDono,true);
		
		caracteresProibidosComentario=Arrays.asList("'");
		
		tvcTelefone.setCellValueFactory(new PropertyValueFactory("numero"));
		tvcTipo.setCellValueFactory(new PropertyValueFactory("tipo"));

		
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
		for(ComentarioTable t:comentarios) {
			TelaComentario comentarioTela = new TelaComentario();
			ComentarioUtil.setComentario(t.getComentario());
			ComentarioUtil.setUsuario(t.getUsuario());
			ComentarioUtil.setData(t.getDataComentario());
			comentarioTela.setPane(this.vboxComentarios);
			comentarioTela.loadFxml();
			

		}
		
		// inicia a api
		iniciaApi();
		lbWhatsApp.setText(String.format("Chamar %s no WhatsApp", 
	    Util.FormatarGetTelefone(Util.FormatarSetTelefone(Util.getTelefoneAtual().getNumero(), Util.getTelefoneAtual().getTipo()), Util.getTelefoneAtual().getTipo())));
	
		int contador=0;
		for(TelefoneNumero n:tvTelefone.getItems()) {
			if(n.getNumero().toString().equals(UtilDashboard.getNumeroTelefone().toString())) {
				tvTelefone.getSelectionModel().select(contador);
				break;
			}
			contador++;
		}
		
	
	}

	@FXML
	public void MostrarTelaDenuncia() {
		if (Util.getContaLogada().getUsuario().equals(txtNomeUsuario.getText()))
		{
			Util.MessageBoxShow("Opção inválida", "Não é possível denunciar seu próprio telefone.", AlertType.INFORMATION);
			return;
		}
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
		ObjectMapper mapper = new ObjectMapper();
		String link = url;
		HttpGet get = new HttpGet(url);
		JSONArray obj=null;
		try {
			HttpClient httpClient = HttpClients.createDefault();
			HttpResponse response;
			response = httpClient.execute(get);
			
			String result = EntityUtils.toString(response.getEntity());
			if(result.length()<=0) {
				tabInfosEndereco.setDisable(true);
				tabPaneInfos.getSelectionModel().select(1);
			}
			
			obj = new JSONArray(result);
			return obj;
		} catch (JSONException | IOException e) {
			e.printStackTrace();
			// não faça nada.
		}
		return obj;
	}

	void iniciaApi() {
		
		ObjectMapper mapper = new ObjectMapper();
		String telefone="";
		
		// jogar a função abaixo
		telefone=Util.FormatarSetTelefone(UtilDashboard.getNumeroTelefone(), Util.getTelefoneAtual().getTipo());
		
		
		String numeroStr=telefone.replace('\s', '+');
		String url = "http://localhost:5000/ListaPublica/getUserAddress/" + numeroStr;
		HttpGet get = new HttpGet(url);

		try {
			String result="";
			HttpClient httpClient = HttpClients.createDefault();
			HttpResponse response;
			response = httpClient.execute(get);
			
			try {
				result = EntityUtils.toString(response.getEntity());	
			}catch(IllegalArgumentException e) {
			}
			
			// se o result for vazio, desative a aba de infos do endereço
			
			if(result.length()<=0) {
				tabPaneInfos.getSelectionModel().select(1);
				tabInfosEndereco.setDisable(true);
			}
			
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
					enderecos.get(0).getCidade(), Integer.toString(enderecos.get(0).getNumero()), enderecos.get(0).getNome());

		} catch (JSONException e) {
			// não faça nada (Entra aqui caso o JSONARray=0)
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	void insereCampos(String estado, String bairro, String rua, String cidade, String numero, String nome) {
		this.txtBairro.setText(bairro);
		this.txtRua.setText(rua);
		this.txtCidade.setText(cidade);
		this.txtEstado.setText(estado);
		this.txtNumeroResidencia.setText(numero);
		this.txtNome.setText(nome);
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
