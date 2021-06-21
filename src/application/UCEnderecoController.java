package application;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import API_IBGE.Municipio;
import API_IBGE.UF;
import classes.API;
import classes.Banco;
import classes.Endereco;
import classes.Telefone;
import classes.Util;
import classes.Validacao;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class UCEnderecoController {

	@FXML
	private TextField txtNome;

	@FXML
	private ImageView btnDeletar;

	@FXML
	private ImageView btnSalvar;

	@FXML
	private TextField txtRua;

	@FXML
	private ComboBox<String> cboxEstado;

	@FXML
	private ComboBox<String> cboxCidade;

	@FXML
	private TextField txtBairro;

	@FXML
	private TextField txtNumero;

	@FXML
	private ImageView imgLocal;

	@FXML
	private Button btnRemoverImagem;

	@FXML
	private Button btnAdicionarImagem;

	@FXML
	private TextField txtCaminhoImagem;
	@FXML
	private Label lbIndice;

	private FlowPane secPane;

	private ArrayList<Integer> idsEstado = new ArrayList<Integer>();

	private Window primaryStage;

	boolean alterada = false;

	private File imagemEscolhida;
	private int index;
    public String nome;
	
	public int getIndex() {
		return index;
	}

	public String getNome() {
		return nome;
	}

	@FXML
	void AlterarEndereco(MouseEvent event) throws SQLException, IOException {

		if (ValidarCampos()) {

			if (Util.MessageBoxShow(" Alterar o Local",
					"Tem certeza que deseja alterar o Local " + endereco.getNome() + " ?").equals(ButtonType.OK)) {

				String query = "";
				String anexoImagem = txtCaminhoImagem.getText();

				if (alterada) {
					query = String.format(
							"UPDATE endereco SET nome = '%s', imagem = '%s', estado = '%s', cidade = '%s', bairro = '%s', rua = '%s', numero = %s WHERE id = %s ",
							txtNome.getText(),
							anexoImagem.length() > 0 ? Util.converterStringParaBase64(anexoImagem) : "",
							cboxEstado.getSelectionModel().getSelectedItem(),
							cboxCidade.getSelectionModel().getSelectedItem(), txtBairro.getText(), txtRua.getText(),
							txtNumero.getText(), endereco.getId());
				} else {

					query = String.format(
							"UPDATE endereco SET nome = '%s',  estado = '%s', cidade = '%s', bairro = '%s', rua = '%s', numero = %s WHERE id = %s",
							txtNome.getText(), cboxEstado.getSelectionModel().getSelectedItem(),
							cboxCidade.getSelectionModel().getSelectedItem(), txtBairro.getText(), txtRua.getText(),
							txtNumero.getText(), endereco.getId());
				}

				if (Banco.InserirQuery(query)) {

					Util.MessageBoxShow("Endereço alterado", "Seu endereço foi alterado com sucesso",
							AlertType.INFORMATION);
					alterada = false;
					AtualizarParametrosPesquisa();
				} else {

					Util.MessageBoxShow("Endereço inalterado", "Não foi possível alterar seu endereço",
							AlertType.ERROR);
				}
			}
		}

	}

	@FXML
	void removerImagem(ActionEvent event) {

		if (Util.MessageBoxShow(" Excluir a Imagem do Local", "Tem certeza que deseja Remover a Imagem ?? ")
				.equals(ButtonType.OK)) {
			txtCaminhoImagem.setText("");
			imgLocal.setImage(new Image(new File("src/Recursos/logo_contornada.png").toURI().toString()));
		}

	}

	@FXML
	void abrirImagem(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Defina uma imagem do local");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("PNG", "*.png"));
		imagemEscolhida = fileChooser.showOpenDialog(this.primaryStage);

		if (imagemEscolhida == null)
			return;

		if (imagemEscolhida.length() <= 4194304) {

			txtCaminhoImagem.setText(imagemEscolhida.getAbsolutePath());
			imgLocal.setImage(
					new Image(new File(txtCaminhoImagem.getText()).toURI().toString(), 400, 400, false, false));
			alterada = true;
		} else
			Util.MessageBoxShow("Inserção de Imagem", "A imagem solicitada escede o tamanho mínimo de 4Mb",
					AlertType.WARNING);
	}

	private void AtualizarParametrosPesquisa() {
		Util.Enderecos.get(index).setEstado(cboxEstado.getSelectionModel().getSelectedItem());
		Util.Enderecos.get(index).setCidade(cboxCidade.getSelectionModel().getSelectedItem());
		Util.Enderecos.get(index).setBairro(txtBairro.getText());
		Util.Enderecos.get(index).setRua(txtRua.getText());
		Util.Enderecos.get(index).setNumero(Integer.parseInt(txtNumero.getText()));
		Util.Enderecos.get(index).setNome(txtNome.getText());
	}

	private boolean ValidarCampos() {

		if (!Validacao.verificarTextField(txtBairro)) {
			txtBairro.setText(endereco.getBairro());
			return false;
		}

		if (!Validacao.verificarTextField(txtRua)) {
			txtRua.setText(endereco.getRua());
			return false;
		}

		if (!Validacao.verificarTextField(txtNumero)) {
			txtNumero.setText(String.valueOf(endereco.getNumero()));
			return false;
		}

		if (!Validacao.verificarTextField(txtNome)) {
			txtNome.setText(endereco.getNome());
			return false;
		}

		if (!Validacao.verificarNumerosTextField(txtNumero)) {
			txtNumero.setText(String.valueOf(endereco.getNumero()));
			return false;
		}

		return true;
	}

	@FXML
	void ExcluirEndereco(MouseEvent event) throws SQLException, IOException {
		if (Util.MessageBoxShow(" Excluir o Local",
				"Tem certeza que deseja excluir o Local " + endereco.getNome() + " ?").equals(ButtonType.OK)) {

			Banco.InserirQuery("UPDATE telefone SET lugar = null WHERE lugar = '" + endereco.getId() + "'");
			if (Banco.InserirQuery("DELETE FROM endereco WHERE id = " + endereco.getId())) {

				Util.MessageBoxShow("Endereço excluído", "Seu endereço foi excluído com sucesso",
						AlertType.INFORMATION);
				// Util.dashboard.AtualizarCbxEnderecos();
				Util.dashboard.AtualizarFlowPaneEndereco(index);
			} else
				Util.MessageBoxShow("Exclusão de Endereço", "Houve um erro ao excluir seu endereço", AlertType.ERROR);
		}
	}

	private void obterImagemLocal() throws SQLException, Exception {

		String caminho = "C:\\lista\\locais\\addr" + endereco.getId() + ".jpg";
		File f = new File(caminho);

		String query = "select imagem from endereco where id=" + endereco.getId();
		Banco.InserirQueryReader(query);

		if (Banco.getReader().next()) {
			String imagem = Banco.getReader().getString("imagem");
			if (imagem != null) {
				if (imagem.length() > 0) {
					Util.verificaExistenciaImagem("addr" + endereco.getId() + ".jpg", imagem.getBytes(), true);
					imgLocal.setImage(new Image(new File(caminho).toURI().toString(), 400, 400, false, false));

				}

			}
		}

	}

	private Endereco endereco;

	public void initialize() throws SQLException, Exception {
		index = Util.indexEndereco;
		endereco = Util.endereco;
		txtRua.setText(endereco.getRua());
		txtBairro.setText(endereco.getBairro());
		txtNumero.setText(String.valueOf(endereco.getNumero()));
		txtNome.setText(endereco.getNome());

		ArrayList<UF> estados = API.doGetEstados();

		for (UF estado : estados) {
			idsEstado.add(estado.getId());
			cboxEstado.getItems().add(estado.getSigla());
		}

		cboxEstado.getSelectionModel().select(endereco.getEstado());
		RecuperarCidades();
		cboxCidade.getSelectionModel().select(endereco.getCidade());
		obterImagemLocal();
		lbIndice.setText(String.valueOf(Util.indexEndereco));
		//System.out.println("Local " + endereco.getNome() + " index "+ index );
		//nome = endereco.getNome();
		Util.controladorEndereco.add(this);
	}

	private void RecuperarCidades() {
		cboxCidade.getItems().clear();

		ArrayList<Municipio> municipios = API
				.doGetCidades(idsEstado.get(cboxEstado.getSelectionModel().getSelectedIndex()));
		for (Municipio municipio : municipios) {
			cboxCidade.getItems().add(municipio.getNome());
		}
		cboxCidade.getSelectionModel().selectFirst();
	}

	@FXML
	private void recuperarCidades() {
		RecuperarCidades();
	}

	public void setPane(FlowPane pane) {
		this.secPane = pane;
	}
	
	public void setIndex()
	{
		this.index = Util.indexEndereco;
	}

	@FXML
	public void loadFxml() throws IOException {

		AnchorPane newLoadedPane = FXMLLoader.load(getClass().getResource("UCEndereco.fxml"));
		secPane.getChildren().add(newLoadedPane);
		Util.nodes.add(newLoadedPane);
	}

}
