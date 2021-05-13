package application;
	
import classes.Util;
import classes.Validacao;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


public class Login extends Application {
		
	@FXML
    private TextField txtUsuario;

    @FXML
    private Label lbEsqueciSenha;

    @FXML
    private Button btnLogar;

    @FXML
    private Label lbCadastrar;

    @FXML
    private PasswordField txtSenha;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("telaLogin.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Image icon = new Image("Recursos/logo.png");
			primaryStage.setScene(scene);
			primaryStage.setTitle("Lista Pública - Login");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
		
	private boolean verificarCampos()
	{
		if (!Validacao.verificarTextField(txtUsuario))
			return false;
		if (!Validacao.verificarTextField(txtSenha))
			return false;
		return true;
	}
	
	@FXML
	public void RealizarLogin()
	{
		if (verificarCampos())
		{
			Dashboard dash = new Dashboard();
			dash.start(new Stage());
			Stage stageAtual = (Stage) btnLogar.getScene().getWindow();
			stageAtual.close();
		}
	}
}
