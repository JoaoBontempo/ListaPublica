package application;
	
import java.util.ArrayList;

import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;

import classes.Util;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


public class Dashboard extends Application {
	
	final int GEONAMEIDBRASIL = 3469034;
	
	private ArrayList<Integer> geoids = new ArrayList<Integer>();
	
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
	
	
	private ToponymSearchResult getChildren(int id) 
	{
		try
		{
			WebService.setUserName("JoaoBontempo"); // add your username here

			ToponymSearchResult children = WebService.children(id, STYLESHEET_CASPIAN, null);
			return children;
		}
		catch (Exception erro)
		{
				Util.MessageBoxShow("Ocorreu um erro ao carregar os Estados", "Um erro ocorreu ao conectar-se a API Geonames.\n"
						+ " Verifique sua conexão com a internet.", AlertType.ERROR);
		}
		return null;
	}
	
	@FXML
	private void recuperarCidades ()
	{
		cboxCidades.getItems().clear();
		for (Toponym estados : getChildren(geoids.get(cboxEstados.getSelectionModel().getSelectedIndex())).getToponyms())
		{
			cboxCidades.getItems().addAll(estados.getName());
			geoids.add(estados.getGeoNameId());
		}
	}
	
	//Método 'onLoad'
	public void initialize() throws Exception
	{
		//Forma de pegar todos os Estados e suas respectivas cidades
		for (Toponym estados : getChildren(GEONAMEIDBRASIL).getToponyms())
		{
			cboxEstados.getItems().addAll(estados.getName());
			geoids.add(estados.getGeoNameId());
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
}
