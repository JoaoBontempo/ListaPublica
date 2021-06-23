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
	private int idEndereco, index;	
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



	public void initialize()
	{
		index = Util.index;
		telefone = Util.telefone;
		idEndereco = Util.idEndereco;
		txtTelefone.setText(Util.FormatarGetTelefone(telefone.getNumero(), telefone.getTipo()));
		txtDescrição.setText(telefone.getDescricao());
		boolean found = false;
		int i = 0;
		cboxEndereco.getItems().add("Sem endereço");
		for (Endereco endereco : Util.todoEnderecos)
		{
			cboxEndereco.getItems().add(endereco.getNome());
			if (idEndereco == endereco.getId())
			{
				Util.enderecosAtuais.add(endereco);
				cboxEndereco.getSelectionModel().select(i+1);
				found = true;
				continue;
			}				
			i++;
		}

		if (!found)
		{
			cboxEndereco.getSelectionModel().selectFirst();
			Endereco end = new Endereco();
			end.setNome("Sem endereço");
			Util.enderecosAtuais.add(end);
		}
	}

	private void AtualizarParametrosPesquisa()
	{
		Util.telefones.get(index).setDescricao(txtDescrição.getText());
		Util.enderecosAtuais.get(index).setNome(cboxEndereco.getSelectionModel().getSelectedItem());
	}

	@FXML
	public void AlterarTelefone() throws SQLException
	{
		if(!Validacao.verificarTextArea(txtDescrição))
			return;
		
		if (!Validacao.VerificarQuantidadeCaracteres(txtDescrição, 300))
			return;
		
		if(Util.MessageBoxShow("Alterar informações" , "Tem certeza que deseja aletrar as informações do telefone " 
				+ Util.FormatarGetTelefone(telefone.getNumero(), telefone.getTipo()) + " ?").equals(ButtonType.OK)){


			if (cboxEndereco.getSelectionModel().getSelectedIndex() != 0)
			{
				Banco.InserirQuery(String.format("UPDATE telefone SET lugar = %s, descricao = '%s' WHERE id = %s "
						, Util.todoEnderecos.get(cboxEndereco.getSelectionModel().getSelectedIndex() - 1).getId(), txtDescrição.getText(), telefone.getId()));
			}
			else
			{
				Banco.InserirQuery(String.format("UPDATE telefone SET  lugar = null, descricao = '%s' WHERE id = %s "
						, txtDescrição.getText(), telefone.getId()));
			}
			Util.MessageBoxShow("Alteração realizada!", "Seu telefone foi alterado com sucesso!", AlertType.INFORMATION);
			AtualizarParametrosPesquisa();
		}
	}

	@FXML 
	public void ExcluirTelefone() throws SQLException, IOException
	{
		if(Util.MessageBoxShow("Excluir número" , "Tem certeza que deseja excluir o telefone " 
				+ Util.FormatarGetTelefone(telefone.getNumero(), telefone.getTipo()) + " ?").equals(ButtonType.OK)){


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
		Util.nodos.add(newLoadedPane);
	}

	public Telefone getTelefone() {
		return this.telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}
}
