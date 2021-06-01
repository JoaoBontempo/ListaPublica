package application;
import java.io.IOException;
import java.util.List;

import classes.ComentarioUtil;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TelaComentario extends Application{
	//private String data, usuario, comentario;
	
	private VBox secPane;
	
	@FXML
    private Label lbUser;

    @FXML
    private TextArea txtComentario;

    @FXML
    private Label lbData;
    
   
    public void initialize()
    {
    	this.lbUser.setText(ComentarioUtil.getUsuario());
    	this.txtComentario.setText(ComentarioUtil.getComentario());
    	this.lbData.setText(ComentarioUtil.getData());
    }
	
	@FXML 
	public void loadFxml () throws IOException {
		AnchorPane newLoadedPane = FXMLLoader.load(getClass().getResource("TelaComentarioFXML.fxml"));
		//secPane.getChildren().add(newLoadedPane); 
		secPane.getChildren().add(newLoadedPane);
	}  

	
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void setPane(VBox pane)
	{
		this.secPane = pane;
	}
}
