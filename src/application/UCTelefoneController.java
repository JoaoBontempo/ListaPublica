package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import classes.Banco;
import classes.Endereco;
import classes.Telefone;
import classes.Util;
import classes.Validacao;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UCTelefoneController extends Application{

	private FlowPane secPane;
	private Telefone telefone;
	private int idEndereco;
	
	@FXML
	private TextField txtTelefone;

	@FXML
	private TextArea txtDescrição;

	@FXML
	private ImageView btnDeletar;

	@FXML
	private ImageView btnSalvar;
	
	private ArrayList<Endereco> enderecos;
	
	@FXML
	private ComboBox<String> cboxEndereco;

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}
	
	public void initialize()
	{
		enderecos = Util.enderecos;
		telefone = Util.telefone;
		idEndereco = Util.idEndereco;
		txtTelefone.setText(telefone.getNumero());
		txtDescrição.setText(telefone.getDescricao());
		
		int i = 0;
		for (Endereco endereco : enderecos)
		{
			if (Validacao.isNullOrEmpty(endereco.getNome()))
				continue;
			cboxEndereco.getItems().add(endereco.getNome());
			if (idEndereco == endereco.getId())
				cboxEndereco.getSelectionModel().select(i);
			i++;
		}
	}
	
	@FXML
	public void AlterarTelefone()
	{
		
	}
	
	@FXML 
	public void ExcluirTelefone() throws SQLException, IOException
	{
		if(Util.MessageBoxShow(" Excluir número" , "Tem certeza que deseja excluir o telefone " 
				+ telefone.getNumero() + " ?").equals(ButtonType.OK)){

			
			Banco.InserirQuery("DELETE FROM comentarios WHERE idTelefone = " + telefone.getId());
			Banco.InserirQuery("DELETE FROM denuncia WHERE tel = " + telefone.getId());
			Banco.InserirQuery("DELETE FROM telefone WHERE id = " + telefone.getId());
			Util.MessageBoxShow("Telefone excluído", "Seu telefone foi excluído com sucesso", AlertType.INFORMATION);
			Util.dashboard.AtualizarCbxTelefones();
		}
	}
	
	public void setPane(FlowPane pane)
	{
		this.secPane = pane;
	}
	
	@FXML 
	public void loadFxml () throws IOException {
		AnchorPane newLoadedPane = FXMLLoader.load(getClass().getResource("UCTelefone.fxml"));
		secPane.getChildren().add(newLoadedPane);
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}  

}
