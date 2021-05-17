package application;

import java.util.ArrayList;

import org.geonames.Toponym;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CadastrarLocal extends Application {
	final int GEONAMEIDBRASIL = 3469034;

	private ArrayList<Integer> geoids = new ArrayList<Integer>();

	@FXML
	private ComboBox<?> cmbTipo;

	@FXML
	private TextField txtNomeLocal;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtNumeroResidencia;

	@FXML
	private TextField txtBairro;

	@FXML
	private ComboBox<?> cmbEstados;

	@FXML
	private TextArea txtDescricao;

	@FXML
	private ImageView imgFotoLocal;

	@FXML
	private Button btnAnexarFoto;

	@FXML
	private TextField txtTelefoneUm;

	@FXML
	private ComboBox<?> cmbCidades;

	public static void main(String[] args) {
		

	}

	public void initialize() throws Exception {
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		TabPane root = (TabPane)FXMLLoader.load(getClass().getResource("telaCadastroLugar.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Image icon = new Image("Recursos/logo.png");
		primaryStage.setScene(scene);
		primaryStage.setTitle("Lista Pública - Cadastro de local");
		primaryStage.getIcons().add(icon);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

}
