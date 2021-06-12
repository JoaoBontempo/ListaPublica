package classes;

import java.util.Timer;
import java.util.TimerTask;

import application.Dashboard;
import javafx.scene.control.Alert.AlertType;

public class TemporizadorCodigo implements Runnable{
	int segundos = 0;
	
	private static Dashboard dashboard;
	
	public static Timer temporizador = new Timer();
	TimerTask doWork = new TimerTask() {
		public void run() {
			segundos++;
			dashboard.setLabelTemporizador(segundos);
			if (segundos == 120)
			{
				temporizador.cancel();
				dashboard.MudarInterfaceCodigo(false);
				dashboard.setCodigo(null);
				Util.MessageBoxShow("O tempo expirou", "O tempo para inserir o código de troca de senha expirou"
						+ "\nPor favor, gere um novo código para alterar sua senha.", AlertType.INFORMATION);
			}
		}
	};
	
	public void start() {
		temporizador.scheduleAtFixedRate(doWork, 1000, 1000);
	}
	
	public TemporizadorCodigo(Dashboard dash)
	{
		dashboard = dash;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.start();
	}
}
