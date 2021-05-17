package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.geonames.Toponym;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CadastrarLocal extends Application implements Initializable {
	final int GEONAMEIDBRASIL = 3469034;

	private ArrayList<Integer> geoids = new ArrayList<Integer>();

	private boolean campoCpfCnpj = false; // false=Cpf

	@FXML
	private ComboBox<String> cmbTipo;

	@FXML
	private TextField txtNomeLocal;

	@FXML
	private TextField txtEmail;

	@FXML
	private Label lblTipo;

	@FXML
	private TextField txtTipo;

	@FXML
	private TextField txtRua;

	@FXML
	private TextField txtNumeroResidencia;

	@FXML
	private TextField txtBairro;

	@FXML
	private ComboBox<String> cmbEstados;

	@FXML
	private TextArea txtDescricao;

	@FXML
	private ImageView imgFotoLocal;

	@FXML
	private Button btnAnexarFoto;

	@FXML
	private TextField txtTelefoneUm;

	@FXML
	private ComboBox<String> cmbCidades;

	public static void main(String[] args) {
		launch(args);

	}

	@FXML
	void VerificaDigito(KeyEvent key) {
		String builder="";
		if(key.getCharacter().matches("[a-zA-Z]")) {
			for(Character c:txtTipo.getText().toCharArray()) {
				if(!Character.toString(c).matches("[a-zA-Z]")) {
					builder+=c;	
				}
				
			}
			txtTipo.setText(builder);
			txtTipo.positionCaret(txtTipo.getLength());
		}
		
	}
	
	@FXML
	void TratamentoTextoCpfCnpj(KeyEvent key) {
//		Pattern PATTERN=Pattern.compile("[a-zA-Z]+");
//		Matcher matcher=PATTERN.matcher(txtTipo.getText());
//	    if(matcher.find()) {
//	    	txtTipo.setText(txtTipo.getText(0, txtTipo.getLength()-1));
//	    	txtTipo.positionCaret(txtTipo.getLength());
//	    	return;
//	    }
//		
		
		// DEFINE AS MÁSCARAS
		if (key.getCode() == KeyCode.DELETE || key.getCode() == KeyCode.BACK_SPACE) {
			return;
		}
		if (!campoCpfCnpj) { // cpf
			// FAZ AS VERIFICAÇÕES PRIMEIRAMENTE
			if (txtTipo.getText().length() == 0)
				return;
			
			
			
			

			String calcular = txtTipo.getText().replaceAll("\\.", "");// OBTENHO O TEXTO SEM OS PONTOS
			System.out.println(calcular);
			if (calcular.length() == 11) {
				txtTipo.deletePreviousChar();
				txtTipo.positionCaret(14);
				return;
			}

			if ((calcular.length() % 3) == 0) {
				// ANTES DE COLOCAR O PONTO, VERIFICA-SE SE O JA CONTÉM O PONTO (Essa situação ocorre quando usa o BACKSPACE)
				if(!(txtTipo.getText().charAt(txtTipo.getText().length()-1) == '.')) {
					txtTipo.setText(txtTipo.getText() + ".");
					txtTipo.positionCaret(txtTipo.getText().length() + 1);	
				}
				
			}
		} else { // cnpj

		}
	}

	@FXML
	void ModificarTipo(ActionEvent event) {
		if (cmbTipo.getSelectionModel().getSelectedIndex() == 0) {
			lblTipo.setText("CPF");
			campoCpfCnpj = false;
			txtTipo.clear();
		} else {
			lblTipo.setText("CNPJ");
			campoCpfCnpj = true;
			txtTipo.clear();
		}
	}

	// API

	@FXML
	private void recuperarCidades() {
		cmbCidades.getItems().clear();
		for (Toponym estados : getChildren(geoids.get(cmbEstados.getSelectionModel().getSelectedIndex()))
				.getToponyms()) {
			String cidade = estados.getName();
			cmbCidades.getItems().addAll(cidade);
			geoids.add(estados.getGeoNameId());
		}
	}

	private ToponymSearchResult getChildren(int id) {
		try {
			WebService.setUserName("JoaoBontempo"); // add your username here

			ToponymSearchResult children = WebService.children(id, STYLESHEET_CASPIAN, null);
			return children;
		} catch (Exception erro) {
			erro.printStackTrace();
			// Util.MessageBoxShow("Ocorreu um erro ao carregar os Estados", "Um erro
			// ocorreu ao conectar-se a API Geonames.\n"
			// + " Verifique sua conexão com a internet.", AlertType.ERROR);
		}
		return null;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("telaCadastroLugar.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Image icon = new Image("Recursos/logo.png");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
		primaryStage.setMaximized(false);
		primaryStage.setTitle("Lista Pública - Cadastro de local");
		primaryStage.getIcons().add(icon);
		primaryStage.show();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cmbTipo.getItems().add("CPF");
		cmbTipo.getItems().add("CNPJ");
		cmbTipo.getSelectionModel().selectFirst();

		for (Toponym estados : getChildren(GEONAMEIDBRASIL).getToponyms()) {
			System.out.println("->" + estados.getName());
			cmbEstados.getItems().addAll(estados.getName());
			geoids.add(estados.getGeoNameId());
		}

	}

}
