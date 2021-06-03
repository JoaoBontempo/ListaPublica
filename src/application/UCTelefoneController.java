package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UCTelefoneController extends Application{

	@FXML
	private TextField txtTelefone;

	@FXML
	private TextArea txtDescrição;

	@FXML
	private ImageView btnDeletar;

	@FXML
	private ImageView btnSalvar;

	@FXML
	private ComboBox<String> cboxEndereco;

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@FXML 
	public void loadFxml () throws IOException {
		AnchorPane newLoadedPane = FXMLLoader.load(getClass().getResource("TelaComentarioFXML.fxml"));
		//secPane.getChildren().add(newLoadedPane); 
		//secPane.getChildren().add(newLoadedPane);
	}  

}
