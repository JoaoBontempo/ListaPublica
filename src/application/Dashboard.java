package application;

import java.io.IOException;
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
import API_IBGE.UF;
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
	
	final int GEONAMEIDBRASIL = 3469034;

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

	private ToponymSearchResult getChildren(int id) 
	{
		try
		{
			WebService.setDefaultStyle(Style.FULL);
			WebService.setUserName("JoaoBontempo"); // add your username here
			ToponymSearchResult children = WebService.children(id, STYLESHEET_CASPIAN, Style.SHORT);
			return children;
		}
		catch (Exception erro)
		{
			//Util.MessageBoxShow("Ocorreu um erro ao carregar os Estados", "Um erro ocorreu ao conectar-se a API Geonames.\n"
			//+ " Verifique sua conex�o com a internet.", AlertType.ERROR);
		}
		return null;
	}
	
	
	public ArrayList<Distrito> doGetCidades()
	{
		String strResposta = "";

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ArrayList<Distrito> alunos = new ArrayList<Distrito>();
		HttpClient httpClient = HttpClientBuilder.create().build();
		System.out.println(String.format("https://servicodados.ibge.gov.br/api/v1/localidades/estados/%s/distritos?OrderBy=nome",
				idsEstado.get(cboxEstados.getSelectionModel().getSelectedIndex())));
		HttpGet httpGet = new HttpGet(String.format("https://servicodados.ibge.gov.br/api/v1/localidades/estados/%s/distritos?OrderBy=nome",
				idsEstado.get(cboxEstados.getSelectionModel().getSelectedIndex())));

		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			HttpEntity resEnt = response.getEntity();
			strResposta = EntityUtils.toString(resEnt);
			JSONArray obj = new JSONArray(strResposta);

			Distrito aln;

			for(int i =0; i < obj.length(); i++)
			{
				aln = mapper.readValue(obj.getJSONObject(i).toString(), Distrito.class);
				alunos.add(aln);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return alunos;
	}
	
	public ArrayList<UF> doGetEstados()
	{
		String strResposta = "";

		ObjectMapper mapper = new ObjectMapper();
		ArrayList<UF> alunos = new ArrayList<UF>();
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet("https://servicodados.ibge.gov.br/api/v1/localidades/estados?OrderBy=nome");

		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			HttpEntity resEnt = response.getEntity();
			strResposta = EntityUtils.toString(resEnt);
			JSONArray obj = new JSONArray(strResposta);

			UF aln;

			for(int i =0; i < obj.length(); i++)
			{
				aln = mapper.readValue(obj.getJSONObject(i).toString(), UF.class);
				alunos.add(aln);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return alunos;
	}
	
	@FXML
	private void recuperarCidades ()
	{
		/*
		int cont = 0;
		//geoids.clear();
		cboxCidades.getItems().clear();
		for (Toponym cidades : getChildren(idsEstado.get(cboxEstados.getSelectionModel().getSelectedIndex())).getToponyms())
		{
			System.out.println(cidades.getName());
			cboxCidades.getItems().addAll(cidades.getName());
			idsEstado.add(cidades.getGeoNameId());
			cont++;
		}
		System.out.println("RETORNOU: " + cont);
		*/
		cboxCidades.getItems().clear();
		ArrayList<Distrito> analise = doGetCidades();
		for (Distrito uf : analise)
		{
			cboxCidades.getItems().add(uf.getNome());
		}
	}

	//M�todo 'onLoad'
	public void initialize()
	{

		tbMeusEnderecos.setDisable(Util.isConvidado());
		tbMeusTelefones.setDisable(Util.isConvidado());
		tbMinhaConta.setDisable(Util.isConvidado());
		/*
		//Forma de pegar todos os Estados e suas respectivas cidades
		for (Toponym estados : getChildren(GEONAMEIDBRASIL).getToponyms())
		{
			cboxEstados.getItems().addAll(estados.getName());
			geoids.add(estados.getGeoNameId());
		}
		*/
		
		ArrayList<UF> analise = doGetEstados();
		for (UF uf : analise)
		{
			idsEstado.add(uf.getId());
			cboxEstados.getItems().add(uf.getNome());
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
			primaryStage.setTitle("Lista P�blica - Menu principal");
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
