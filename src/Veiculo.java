import java.io.Serializable;
import java.rmi.Remote;
import java.time.LocalDateTime;

public class Veiculo implements Remote, Serializable{

	private static final long serialVersionUID = 8213271719324815076L;
	private String placa;
	private LocalDateTime entrada;
	private boolean pago;
	
	
	public Veiculo(String placa) {
		this.placa = placa;
		this.entrada = LocalDateTime.now();
		this.pago = false;
		//format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}
	
	public Veiculo(String placa, int dia, int mes, int ano,  int hora, int minuto) {
		this.placa = placa;
		this.entrada = LocalDateTime.of(ano, mes, dia, hora, minuto);
		this.pago = false;
		//format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}

	public String getPlaca() { return placa; }

	public LocalDateTime getEntrada() { return entrada; }
	
	public boolean isPago() { return pago; }

	public void setPago(boolean pago) { this.pago = pago; }

	@Override
	public String toString() {
		return "Veiculo [placa=" + placa + ", entrada=" + entrada + ", pago=" + pago + "]";
	}	

}
