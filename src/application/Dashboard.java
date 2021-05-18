package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

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
import classes.Banco;
import classes.Util;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


public class Dashboard extends Application{

	private ArrayList<Integer> idsEstado = new ArrayList<Integer>();

	private ArrayList<String> cidadesUtil = new ArrayList<String>(); // ArrayList para a classe Utils.

	@FXML
	private TextField txtPesquisar;

	@FXML
	private TableView<?> tvLugares;

	@FXML
	private TableColumn<?, ?> tvcNumero;

	@FXML
	private TableColumn<?, ?> tvcNome;

	@FXML
	private TableColumn<?, ?> tvcCidade;

	@FXML
	private TableColumn<?, ?> tvcEstado;
	
	@FXML
	private TableColumn<?, ?> tvcDescricao;

	@FXML
	private TableColumn<?, ?> tvcEmail;

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

	public ArrayList<Municipio> doGetCidades()
	{
		String strResposta = "";

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ArrayList<Municipio> municipios = new ArrayList<Municipio>();
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(String.format("https://servicodados.ibge.gov.br/api/v1/localidades/estados/%s/municipios",
				idsEstado.get(cboxEstados.getSelectionModel().getSelectedIndex())));

		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			HttpEntity resEnt = response.getEntity();
			strResposta = EntityUtils.toString(resEnt);
			JSONArray obj = new JSONArray(strResposta);

			Municipio municipio;

			for(int i =0; i < obj.length(); i++)
			{
				municipio = mapper.readValue(obj.getJSONObject(i).toString(), Municipio.class);
				municipios.add(municipio);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return municipios;
	}

	public ArrayList<UF> doGetEstados()
	{
		String strResposta = "";

		ObjectMapper mapper = new ObjectMapper();
		ArrayList<UF> estados = new ArrayList<UF>();
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet("https://servicodados.ibge.gov.br/api/v1/localidades/estados?OrderBy=nome");

		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			HttpEntity resEnt = response.getEntity();
			strResposta = EntityUtils.toString(resEnt);
			JSONArray obj = new JSONArray(strResposta);

			UF estado;

			for(int i =0; i < obj.length(); i++)
			{
				estado = mapper.readValue(obj.getJSONObject(i).toString(), UF.class);
				estados.add(estado);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return estados;
	}

	@FXML
	private void recuperarCidades ()
	{
		cboxCidades.getItems().clear();
		ArrayList<Municipio> municipios = doGetCidades();
		for (Municipio municipio : municipios)
		{
			cboxCidades.getItems().add(municipio.getNome());
		}
	}

	//Método 'onLoad'
	public void initialize()
	{

		tbMeusEnderecos.setDisable(Util.isConvidado());
		tbMeusTelefones.setDisable(Util.isConvidado());
		tbMinhaConta.setDisable(Util.isConvidado());
		
		ArrayList<UF> estados = doGetEstados();
		for (UF estado : estados)
		{
			idsEstado.add(estado.getId());
			cboxEstados.getItems().add(estado.getNome());
		}
		
		
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
