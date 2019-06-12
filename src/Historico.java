import java.io.Serializable;
import java.rmi.Remote;

public class Historico implements Remote, Serializable{

	private static final long serialVersionUID = 2485414200914022471L;
	private String placa;
	private long tempo;
	private float divida;
	private int visitas;
	
	public Historico(String placa) {
		this.placa = placa;
		this.tempo = (long) 0.0;
		this.divida = 0;
		this.visitas = 0;
	}

	public Historico(String placa, long tempo, float divida) {
		this.placa = placa;
		this.tempo = tempo;
		this.divida = divida;
		this.visitas = 1;
	}

	public synchronized void addSaida(long plustempo, float f) {
		this.divida += f;
		this.tempo += plustempo;
		this.visitas ++;
	}

	public String getPlaca() {
		return placa;
	}


	@Override
	public String toString() {
		return "[Placa = " + placa + "---  tempo total = " + tempo + " minutos, total pago = " 
														+ divida + ", visitas = " +visitas +"]";
	}
}
