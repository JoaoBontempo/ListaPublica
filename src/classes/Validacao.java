package classes;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;

public final class Validacao {
	
	public static boolean isNullOrEmpty(String string)
	{
		if (string == null)
			return true;
		if (string.isEmpty())
			return true;
		return false;
	}
	
	public static boolean verificarTextField(TextField txt)
	{
		if (Validacao.isNullOrEmpty(txt.getText()))
		{
			Util.MessageBoxShow("Campo inválido", "O campo '" + txt.getId().substring(3).replace("_", " ") + "' está vazio.", AlertType.WARNING);
			txt.requestFocus();
			return false;
		}
		else
			return true;
	}
	
	public static boolean verificarTextArea(TextArea txt)
	{
		if (Validacao.isNullOrEmpty(txt.getText()))
		{
			Util.MessageBoxShow("Campo inválido", "O campo '" + txt.getId().substring(3).replace("_", " ") + "' está vazio.", AlertType.WARNING);
			txt.requestFocus();
			return false;
		}
		else
			return true;
	}
}
