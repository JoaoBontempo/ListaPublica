package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;

import classes.Banco;

public class TesteAqruivo {

	public static void main(String[] args) {
		try {
			Banco.Conectar();//inserirImagem
			InputStream is = new FileInputStream(new File("C:\\Users\\Igor\\Desktop\\exemplo.jpg"));
			Banco.inserirImagem(is);
			
		} catch (ClassNotFoundException | SQLException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
