package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
import classes.Endereco;
import classes.Parceiro;
import classes.TableViewUtil;
import classes.Telefone;
import classes.Util;
import classes.Validacao;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


public class Dashboard extends Application{

	private ArrayList<Integer> idsEstado = new ArrayList<Integer>();

	private ArrayList<String> cidadesUtil = new ArrayList<String>(); // ArrayList para a classe Utils.

	private String nome = "*", estado = "*", cidade = "*", numero = "*", email = "*";

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

	private List<TableViewUtil> telefones = new ArrayList();
	private ObservableList<TableViewUtil> observableTelefones;

	@FXML
	private void recuperarCidades ()
	{
		cboxCidades.getItems().clear();
		if (cboxEstados.getSelectionModel().getSelectedIndex() == 0)
		{
			cboxCidades.getItems().add("Todas as cidades");
			cboxCidades.getSelectionModel().selectFirst();
			return;
		}
		cboxCidades.getItems().add("Todas as cidades");
		ArrayList<Municipio> municipios = API.doGetCidades(idsEstado.get(cboxEstados.getSelectionModel().getSelectedIndex()-1));
		for (Municipio municipio : municipios)
		{
			cboxCidades.getItems().add(municipio.getNome());
		}
		cboxCidades.getSelectionModel().selectFirst();
	}

	//Método 'onLoad'
	public void initialize()
	{

		tbMeusEnderecos.setDisable(Util.isConvidado());
		tbMeusTelefones.setDisable(Util.isConvidado());
		tbMinhaConta.setDisable(Util.isConvidado());
		
		cboxEstados.getItems().add("Todos os Estados");
		ArrayList<UF> estados = API.doGetEstados();
		for (UF estado : estados)
		{
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

	private void setQueryParameters()
	{
		numero = Validacao.isNullOrEmpty(txtTelefone.getText()) ? "*" : txtTelefone.getText();
		nome = Validacao.isNullOrEmpty(txtNome.getText()) ? "*" : txtNome.getText();
		email = Validacao.isNullOrEmpty(txtEmail.getText()) ? "*" : txtEmail.getText();		
		cidade = cboxCidades.getSelectionModel().getSelectedIndex() == 0 ? "*" : cboxCidades.getSelectionModel().getSelectedItem();
		estado = cboxEstados.getSelectionModel().getSelectedIndex() == 0 ? "*" : cboxEstados.getSelectionModel().getSelectedItem();
	}
	
	@FXML
	private void RecuperarUltimos()
	{
		if (Validacao.verificarTextField(txtLimite_de_procura))
			if (Validacao.verificarNumerosTextField(txtLimite_de_procura))
				AtualizarGridTelefones(API.doGetTelefones(Integer.parseInt(txtLimite_de_procura.getText())));
	}

	
	@FXML 
	private void AplicarFiltroDeDados()
	{
		setQueryParameters();
		AtualizarGridTelefones(API.doPostTelefone(new TableViewUtil(nome, numero, cidade, estado, email)));
	}

	private void AtualizarGridTelefones(ArrayList<Telefone> dados)
	{
		if (dados.size() == 0)
		{
			Util.MessageBoxShow("Nenhum dado foi encontrado", "Não foi possível encontrar nenhum dado.\n"
					+ "Tente mudar as informaçoes do filtro", AlertType.WARNING);
			return;
		}
		telefones.clear();
		for (Telefone telefone : dados)
		{
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

				if (telefone.getNome().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true;
				} else if (telefone.getCidade().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}
				else if (telefone.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}
				else if (telefone.getEstado().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}
				else if (telefone.getNumero().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}
				else if (telefone.getDescricao().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}
				else  
					return false; 
			});
		});

		SortedList<TableViewUtil> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tvTelefones.comparatorProperty());
		tvTelefones.setItems(sortedData);
	}
	
	@FXML
	private void LimparFiltro()
	{
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
			TabPane root = (TabPane)FXMLLoader.load(getClass().getResource("telaDashboard.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Image icon = new Image("Recursos/logo.png");
			primaryStage.setScene(scene);
			primaryStage.setTitle("Lista Pública - Menu principal");
			primaryStage.getIcons().add(icon);
			primaryStage.setMaximized(true);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Stage getStage()
	{
		Stage stage = (Stage) txtPesquisar.getScene().getWindow();
		return stage;
	}

	@FXML
	public void showNovoTelefone()
	{
		CadastroTelefone ct = new CadastroTelefone();
		ct.start(new Stage());
	}
}
