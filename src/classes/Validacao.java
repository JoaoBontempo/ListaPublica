package classes;

import javafx.scene.control.TextField;

import java.util.InputMismatchException;

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

	public static boolean validarCNPJ(TextField CNPJ)
	{
		// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
		if (CNPJ.getText().equals("00000000000000") || CNPJ.getText().equals("11111111111111") ||
				CNPJ.getText().equals("22222222222222") || CNPJ.getText().equals("33333333333333") ||
				CNPJ.getText().equals("44444444444444") || CNPJ.getText().equals("55555555555555") ||
				CNPJ.getText().equals("66666666666666") || CNPJ.getText().equals("77777777777777") ||
				CNPJ.getText().equals("88888888888888") || CNPJ.getText().equals("99999999999999") ||
				(CNPJ.getText().length() != 14))
		{
			Util.MessageBoxShow("Campo inválido", "O CPNJ inserido é inválido", AlertType.ERROR);
			return(false);
		}

		char dig13, dig14;
		int sm, i, r, num, peso;

		// "try" - protege o código para eventuais erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i=11; i>=0; i--) {
				// converte o i-ésimo caractere do CNPJ em um número:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posição de '0' na tabela ASCII)
				num = (int)(CNPJ.getText().charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig13 = '0';
			else dig13 = (char)((11-r) + 48);

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i=12; i>=0; i--) {
				num = (int)(CNPJ.getText().charAt(i)- 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig14 = '0';
			else dig14 = (char)((11-r) + 48);

			// Verifica se os dígitos calculados conferem com os dígitos informados.
			if ((dig13 == CNPJ.getText().charAt(12)) && (dig14 == CNPJ.getText().charAt(13)))
				return(true);
			else 
			{
				Util.MessageBoxShow("Campo inválido", "O CPNJ inserido é inválido", AlertType.ERROR);
				return(false); 
			}
		} catch (InputMismatchException erro) {
			Util.MessageBoxShow("Campo inválido", "O CPNJ inserido é inválido", AlertType.ERROR);
			return(false);
		}
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
