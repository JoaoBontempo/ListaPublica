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
	
	public static boolean validarCPF(TextField textBox)
	{
		if (isNullOrEmpty(textBox.getText()))
        {
            Util.MessageBoxShow("Campo inválido", "O campo 'CPF' está em branco", AlertType.WARNING);
            textBox.requestFocus();
            return false;
        }
        if (textBox.getText().length() < 11)
        {
        	Util.MessageBoxShow("Campo inválido", "O campo 'CPF' está em incompleto"
        			+ "\n\nInforme todos os digitos do CPF", AlertType.WARNING);
        	textBox.requestFocus();
            return false;
        }
        if (textBox.getText().length() > 11)
        {
        	Util.MessageBoxShow("Campo inválido", "O campo 'CPF' está em incorreto"
        			+ "\n\nInsira apenas os digitos do CPF.", AlertType.WARNING);
        	textBox.requestFocus();
            return false;
        }

        int[] digitos = new int[10];
        short cont = 0;
        char numero;
        for (int i = 0; i < 11; i++)
        {
        	numero = textBox.getText().charAt(i);
            short num = Short.parseShort(String.valueOf(numero));
            digitos[cont++] = num;
            if (cont == 9)
                break;
        }
        
        short digito;
        int digito1V, digito2V;
        short digito1, digito2;
        digito1 = Short.parseShort(String.valueOf(textBox.getText().charAt(9)));//dg1, 12
        digito2 = Short.parseShort(String.valueOf(textBox.getText().charAt(10)));//dg2, 13
        int soma = 0;
        int index = 2;

        for (int i = 8; i >= 0 ; i-- )
        {
        	digito = Short.parseShort(String.valueOf(digitos[i]));//digito, digitos[i]
            soma += digito*index++;
        }
        if (soma % 11 < 2)
            digito1V = 0;
        else
            digito1V = 11 - (soma % 11);

        digitos[9] = digito1;
        
        soma = 0;
        index = 2;
        for (int i = 9; i >= 0; i--)
        {
        	digito = Short.parseShort(String.valueOf(digitos[i]));//digito, digitos[i]
            soma += digito * index++;
        }
        if (soma % 11 < 2)
            digito2V = 0;
        else
            digito2V = 11 - (soma % 11);


        if (digito1 != digito1V)
        {
            Util.MessageBoxShow("Campo inválido", "O CPF inserido é inválido!", AlertType.WARNING);
            return false;
        }
        else if (digito2 != digito2V)
        {
            Util.MessageBoxShow("Campo inválido", "O CPF inserido é inválido!", AlertType.WARNING);
            return false;
        }
        else
            return true;
	}
	
	public static boolean validarEmail(String email)
	{
		if (isNullOrEmpty(email))
            return false;

        if (!email.contains("@"))
            return false;
        
        String[] partesEmail = email.split("@");
        
        try
        {
        if (!partesEmail[1].contains("."))
            return false;
        }
        catch (ArrayIndexOutOfBoundsException erro)
        {
        	return false;
        }
        String[] dominio = partesEmail[1].split("\\.");

        if (partesEmail[0].contains("{") || partesEmail[0].contains("}") || partesEmail[0].contains("[") || partesEmail[0].contains("]") || partesEmail[0].contains(",")
            || partesEmail[0].contains("'") || partesEmail[0].contains("\"\"") || partesEmail[0].contains("\\") || partesEmail[0].contains("/")
            || partesEmail[0].contains("|") || partesEmail[0].contains("!") || partesEmail[0].contains("=") || partesEmail[0].contains("+")
            || partesEmail[0].contains("$") || partesEmail[0].contains("%") || partesEmail[0].contains("(") || partesEmail[0].contains(")") || partesEmail[0].contains("#"))
            return false;
        try
        {
        if (isNullOrEmpty(dominio[1]) || isNullOrEmpty(dominio[0]))
            return false;
        }
        catch(ArrayIndexOutOfBoundsException erro)
        {
        	return false;
        }
        return true;
	}
}
