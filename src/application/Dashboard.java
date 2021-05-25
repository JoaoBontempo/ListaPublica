package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.geonames.Style;
import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;
import org.json.JSONArray;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import API_IBGE.Distrito;
import API_IBGE.Municipio;
import API_IBGE.UF;
import classes.API;
import classes.Banco;
import classes.Email;
import classes.Endereco;
import classes.Parceiro;
import classes.TableViewUtil;
import classes.Telefone;
import classes.Util;
import classes.UtilDashboard;
import classes.Validacao;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class Dashboard extends Application {

	private ArrayList<Integer> idsEstado = new ArrayList<Integer>();

	private ArrayList<String> cidadesUtil = new ArrayList<String>(); // ArrayList para a classe Utils.

	private String nome = "*", estado = "*", cidade = "*", numero = "*", email = "*", descricao = "*";

	private Stage primaryStage;
	
	@FXML
	private TabPane TabDash;

	@FXML
	private TextField txtPesquisar;

	@FXML
	private TableView<TableViewUtil> tvTelefones;

	@FXML
	private TableColumn<TableViewUtil, String> tvcNumero;

	@FXML
	private TableColumn<TableViewUtil, String> tvcNome;

	@FXML
	private TableColumn<TableViewUtil, String> tvcCidade;

	@FXML
	private TableColumn<TableViewUtil, String> tvcEstado;

	@FXML
	private TableColumn<TableViewUtil, String> tvcDescricao;

	@FXML
	private TableColumn<TableViewUtil, String> tvcEmail;

	@FXML
	private ComboBox<String> cboxEstados;

	@FXML
	private ComboBox<String> cboxCidades;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtTelefone;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtDescri��o;

	@FXML
	private Button btnNovoTelefone;

	@FXML
	private Tab tbMeusTelefones;

	@FXML
	private Tab tbMeusEnderecos;

	@FXML
	private Tab tbMinhaConta;

	@FXML
	private Button btnAplicarFiltro;

	@FXML
	private Button btnLimparFiltro;

	@FXML
	private TextField txtLimite_de_procura;

	@FXML
	private Button btnAtualizarLimite;

	@FXML
	private TextField txtMCNome;

	@FXML
	private TextField txtMCUsuario;

	@FXML
	private TextField txtMCEmail;

	@FXML
	private TextField txtMCCPFouCNPJ;

	@FXML
	private Label lbMCCPFouCNPJ;

	@FXML
	private Button btnAlterarDados;

	@FXML
	private PasswordField txtMCCodigo;

	@FXML
	private Button btnConfirmarAlteracao;

	@FXML
    private ImageView imgIconePerfil;
	
	@FXML
	private Button btnAlterarSenha;

	@FXML
    private Label lblTrocarFotoPerfil;
	
	 @FXML
	 void TrocarFotoPerfil(MouseEvent event) {
		FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Defina uma imagem do local");
	    fileChooser.getExtensionFilters().addAll(
	                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
	                new FileChooser.ExtensionFilter("PNG", "*.png"),
	                new FileChooser.ExtensionFilter("ICO", "*.ico"));
	    File imagemEscolhida=fileChooser.showOpenDialog(this.primaryStage);
	    String base=Util.converterStringParaBase64(Path.of(imagemEscolhida.getAbsolutePath()).toString());
	    try {
			Banco.InserirQuery("update parceiro set imagem='"+base+" where id ="+Util.getContaLogada().getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
	 }
	
	void verificaIconeUsuario() {
		int id=Util.getContaLogada().getId();
		boolean possuiIcone=false;
		try {
			Banco.InserirQueryReader("select imagem from parceiro where id="+id);
			if(Banco.getReader().next()) {
				String imagem=Banco.getReader().getString("imagem");
				if(imagem.length()>0) {possuiIcone=true;}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(possuiIcone) {
			imgIconePerfil.setImage(new Image("C:\\lista\\usuarios\\profile.png"));
		}
		
	}
	
	@FXML
	void AlterarDados(ActionEvent event) throws SQLException {

		if (txtMCEmail.getText().equals(Util.getContaLogada().getEmail())) {
			System.out.println("Iguais");
			return;
		}

		if (Validacao.validarEmail(txtMCEmail.getText())) {
			Banco.InserirQuery(String.format("UPDATE parceiro set email = '%s' where id = %s", txtMCEmail.getText(),
					Util.getContaLogada().getId()));
			Util.MessageBoxShow("Altera��o de Dados", "Email alterado com sucesso!");
			Util.getContaLogada().setEmail(txtMCEmail.getText());
		}

	}

	private String Codigo = "";
	@FXML
	void AlterarSenha(ActionEvent event) {
		Codigo = RecuperarSenha.gerarCodigo(0, "", new Random().nextInt(9));

		if(Email.enviarEmail("O seu c�digo de acesso � " + Codigo,
				"Troca de Senha", Util.getContaLogada().getEmail())) {
			Util.MessageBoxShow("Troca de Senhas", "Foi enviado o c�digo de altera��o ao seu E-mail! ");
			txtMCCodigo.requestFocus();
		}
	}

	@FXML
	void ConfirmarCodigoSenha(ActionEvent event) {

		if(txtMCCodigo.getText().equals(Codigo)) {

			TrocarSenha trocarSenha = new TrocarSenha();
			trocarSenha.setEmail(Util.getContaLogada().getEmail());
			trocarSenha.start(new Stage());

		}
	}

	private List<TableViewUtil> telefones = new ArrayList();
	private ObservableList<TableViewUtil> observableTelefones;

	@FXML
	private void recuperarCidades() {
		cboxCidades.getItems().clear();
		if (cboxEstados.getSelectionModel().getSelectedIndex() == 0) {
			cboxCidades.getItems().add("Todas as cidades");
			cboxCidades.getSelectionModel().selectFirst();
			return;
		}
		cboxCidades.getItems().add("Todas as cidades");
		ArrayList<Municipio> municipios = API
				.doGetCidades(idsEstado.get(cboxEstados.getSelectionModel().getSelectedIndex() - 1));
		for (Municipio municipio : municipios) {
			cboxCidades.getItems().add(municipio.getNome());
		}
		cboxCidades.getSelectionModel().selectFirst();
	}

	@FXML
	void FormCadastrarEndereco(ActionEvent event) {
		try {
			CadastrarLocal cadastro=new CadastrarLocal();
			cadastro.start(new Stage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	// esse m�todo vai obter o ID do usu�rio que pertence ao lugar clicado e abrir a
	// janela de Tela, mostrando as infos detalhadas e todos os
	// telefones associados ao mesmo.
	@FXML
	void abrirDescricaoDetalhada(MouseEvent event) {
		String id="";

		//		DOUBLE CLICK NA LINHA
		if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            if(tvTelefones.getSelectionModel().getSelectedItem() != null) {
            	// obt�m o telefone para obter o id do parceiro e id do local
            	TableViewUtil ret=tvTelefones.getSelectionModel().getSelectedItem();
            	Util.setTelefoneAtual(ret.getNumero());
            	String numero=ret.getNumero();
            	String descricao=ret.getDescricao();
            	String idDono=null;
            	String idLugar=null;
            	String query="";
//            	try {
//            		Banco.Conectar();
//            		// primeiro obtenho o id do dono , depois obtenho os telefones associados a ele
//            		query="select dono,lugar,descricao from telefone where numero LIKE '%"+numero+"%'";
//            		query+=descricao == null?";":" and descricao LIKE '%"+descricao+"%';";
//            		//System.out.println(query);
//					if(tvTelefones.getSelectionModel().getSelectedItem() != null) {
//						// obt�m o telefone para obter o id do parceiro e id do local
//						ret=tvTelefones.getSelectionModel().getSelectedItem();
//						numero=ret.getNumero();
//						descricao=ret.getDescricao();
//						idDono=null;
//						idLugar=null;
//					}
//				}catch(Exception e){e.printStackTrace();}
				
				try {
					Banco.Conectar();
					// primeiro obtenho o id do dono , depois obtenho os telefones associados a ele
					query="select dono,lugar,descricao from telefone where numero LIKE '%"+numero+"%'";
					query+=descricao == null?";":" and descricao LIKE '%"+descricao+"%';";
					System.out.println(query);

					Banco.InserirQueryReader(query);
					Banco.getReader().next();
					idDono=Banco.getReader().getString("dono");
					UtilDashboard.setIdLugar(String.valueOf(Banco.getReader().getInt("lugar")));
					UtilDashboard.setIdDono(idDono);
					UtilDashboard.setNumeroTelefone(ret.getNumero());

					// obtem os telefones
					//query="select distinct endereco.usuario,telefone.numero from endereco INNER JOIN telefone ON endereco.usuario="+idDono+" order by endereco.usuario,telefone.numero;";
					query="select numero from telefone where dono="+idDono+";";
					//System.out.println("Id dono: "+idDono+"\nIdLugar:"+UtilDashboard.getIdLugar()+"\nQuery: "+query);
					Banco.InserirQueryReader(query);

					UtilDashboard.getTelefones().clear();
					while(Banco.getReader().next()){
						try {
							UtilDashboard.getTelefones().add(Banco.getReader().getString("numero"));
						}catch(Exception exc) {
							exc.printStackTrace();
						}
					}
					TelaLocal tl = new TelaLocal();
					UtilDashboard.setTbVutil(ret);
					tl.start(new Stage());

				} catch (Exception e1) {
					e1.printStackTrace(); 					
				}
			}
		}

	}


	@FXML
	void buscarDados(Event event) {

		if (tbMinhaConta.isSelected())
		{
			txtMCNome.setText(Util.getContaLogada().getNome());
			txtMCUsuario.setText(Util.getContaLogada().getUsuario());
			txtMCEmail.setText(Util.getContaLogada().getEmail());
			if (Util.getContaLogada().getTipo()) {
				lbMCCPFouCNPJ.setText("CNPJ");
				txtMCCPFouCNPJ.setText(Util.getContaLogada().getCnpj());
			} else {
				lbMCCPFouCNPJ.setText("CPF");
				txtMCCPFouCNPJ.setText(Util.getContaLogada().getCpf());
			}
		}
	}


	// M�todo 'onLoad'
	public void initialize() {

		tbMeusEnderecos.setDisable(Util.isConvidado());
		tbMeusTelefones.setDisable(Util.isConvidado());
		tbMinhaConta.setDisable(Util.isConvidado());

		cboxEstados.getItems().add("Todos os Estados");
		ArrayList<UF> estados = API.doGetEstados();
		for (UF estado : estados) {
			idsEstado.add(estado.getId());
			cboxEstados.getItems().add(estado.getSigla());
		}
		cboxEstados.getSelectionModel().selectFirst();
		cboxCidades.getItems().add("Todas as cidades");
		cboxCidades.getSelectionModel().selectFirst();
		tvcNumero.setCellValueFactory(new PropertyValueFactory("numero"));
		tvcNumero.setStyle("-fx-alignment: CENTER;");
		tvcNome.setCellValueFactory(new PropertyValueFactory("nome"));
		tvcDescricao.setCellValueFactory(new PropertyValueFactory("descricao"));
		tvcEstado.setCellValueFactory(new PropertyValueFactory("estado"));
		tvcEstado.setStyle("-fx-alignment: CENTER;");
		tvcCidade.setCellValueFactory(new PropertyValueFactory("cidade"));
		tvcCidade.setStyle("-fx-alignment: CENTER;");
		tvcEmail.setCellValueFactory(new PropertyValueFactory("email"));

		AtualizarGridTelefones(API.doGetTelefones(100));

	}

	private void setQueryParameters() {
		numero = Validacao.isNullOrEmpty(txtTelefone.getText()) ? "*" : txtTelefone.getText();
		nome = Validacao.isNullOrEmpty(txtNome.getText()) ? "*" : txtNome.getText();
		email = Validacao.isNullOrEmpty(txtEmail.getText()) ? "*" : txtEmail.getText();
		descricao = Validacao.isNullOrEmpty(txtDescri��o.getText()) ? "*" : txtDescri��o.getText();
		cidade = cboxCidades.getSelectionModel().getSelectedIndex() == 0 ? "*"
				: cboxCidades.getSelectionModel().getSelectedItem();
		estado = cboxEstados.getSelectionModel().getSelectedIndex() == 0 ? "*"
				: cboxEstados.getSelectionModel().getSelectedItem();
	}

	@FXML
	private void RecuperarUltimos() {
		if (Validacao.verificarTextField(txtLimite_de_procura))
			if (Validacao.verificarNumerosTextField(txtLimite_de_procura))
				AtualizarGridTelefones(API.doGetTelefones(Integer.parseInt(txtLimite_de_procura.getText())));
	}

	@FXML
	private void AplicarFiltroDeDados() {
		setQueryParameters();
		AtualizarGridTelefones(API.doPostTelefone(new TableViewUtil(nome, numero, cidade, estado, email, descricao)));
	}

	private void AtualizarGridTelefones(ArrayList<Telefone> dados) {
		if (dados.size() == 0) {
			Util.MessageBoxShow("Nenhum dado foi encontrado",
					"N�o foi poss�vel encontrar nenhum dado.\n" + "Tente mudar as informa�oes do filtro",
					AlertType.WARNING);
			return;
		}
		telefones.clear();
		for (Telefone telefone : dados) {
			telefones.add(new TableViewUtil(telefone, telefone.getParceiro(), telefone.getEndereco()));
		}

		observableTelefones = FXCollections.observableArrayList(telefones);
		tvTelefones.setItems(observableTelefones);

		FilteredList<TableViewUtil> filteredData = new FilteredList<>(observableTelefones, b -> true);

		txtPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(telefone -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				if (telefone.getNome().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (telefone.getCidade().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (telefone.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (telefone.getEstado().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (telefone.getNumero().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (telefone.getDescricao().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else
					return false;
			});
		});

		SortedList<TableViewUtil> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tvTelefones.comparatorProperty());
		tvTelefones.setItems(sortedData);
	}

	@FXML
	private void LimparFiltro() {
		txtNome.setText("");
		nome = "*";

		txtEmail.setText("");
		email = "*";

		txtTelefone.setText("");
		numero = "*";

		cboxEstados.getSelectionModel().selectFirst();

		cboxCidades.getItems().clear();
		cboxCidades.getItems().add("Todas as cidades");
		cboxCidades.getSelectionModel().selectFirst();

	}

	@Override
	public void start(Stage primaryStage) {
		try {
			TabPane root = (TabPane) FXMLLoader.load(getClass().getClassLoader().getResource("application/telaDashboard.fxml"));
			this.primaryStage=primaryStage;
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Image icon = new Image("/Recursos/logo.png");
			primaryStage.setScene(scene);
			primaryStage.setTitle("Lista P�blica - Menu principal");
			primaryStage.getIcons().add(icon);
			primaryStage.setMaximized(true);
			//root.getSelectionModel().selectedItemProperty().addListener((v, oldValue,
			//	newValue) -> System.out.println(newValue));


			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Stage getStage() {
		Stage stage = (Stage) txtPesquisar.getScene().getWindow();
		return stage;
	}

	@FXML
	public void showNovoTelefone() {
		CadastroTelefone ct = new CadastroTelefone();
		ct.start(new Stage());
	}
}
