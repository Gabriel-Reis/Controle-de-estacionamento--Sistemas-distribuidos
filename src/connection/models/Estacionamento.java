package connection.models;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import connection.util.IEstacionamento;

@SuppressWarnings("serial")
public class Estacionamento extends UnicastRemoteObject implements IEstacionamento {

	private ArrayList<Veiculo> estacionamento = new ArrayList<Veiculo>();
	private ArrayList<Historico> relatorio = new ArrayList<Historico>();
	private int port = 0;
	
	public Estacionamento() throws RemoteException{
	}
	
	public void Entrar(Veiculo v) throws RemoteException {
		estacionamento.add(v);
	}

	public boolean Sair(Veiculo v) throws RemoteException  {
		Veiculo saindo = BuscarEstacionado(v);
		if(saindo != null && saindo.isPago()) {
			AddRelatorio(saindo);
			estacionamento.remove(saindo);
			return true;
		}
		else
			return false;
	}
	
	public boolean AddRelatorio(Veiculo v) throws RemoteException {
		String placa = v.getPlaca();
		long tempo = ChronoUnit.MINUTES.between(v.getEntrada(), LocalDateTime.now());
		float valor = CalcularValor(v);
		
		for(Historico Hist: relatorio) {
			if(Hist.getPlaca().equals(placa)) {
				Hist.addSaida(tempo, valor);
				return true;
			}
		}
		relatorio.add(new Historico(placa,tempo,valor));
		return true;
	}
	
	public boolean pagar(Veiculo v) throws RemoteException  {
		Veiculo pagando = BuscarEstacionado(v);
		if(pagando == null)
			return false;
		if(pagando.isPago()) {
			return true;
		}
		else {
			pagando.setPago(true);
			return true;
		}
	}
	
	public Veiculo BuscarEstacionado(Veiculo v) throws RemoteException  {
		for(Veiculo veic: estacionamento) {
			if(veic.getPlaca().equals(v.getPlaca())) {
				return veic;
			}
		}
		return null;
	}

	public float CalcularValor(Veiculo v) throws RemoteException  {
		LocalDateTime pagando = LocalDateTime.now();
		LocalDateTime localDateTime = v.getEntrada();

        long difEmMinutos = ChronoUnit.MINUTES.between(localDateTime, pagando);
        
        if(difEmMinutos < 15) 	//TOLERANCIA
        	return 0;
        if(difEmMinutos <= 30)	//30 Min
        	return 3;
        if(difEmMinutos <= 60) 	//1 HORA
        	return 4;
        if(difEmMinutos < 600) 	//1 HORA ATÉ 10 HORAS -> 5 reais a hora
        	return ((difEmMinutos%60)*5);
        if(difEmMinutos <= 1440) //10 horas até 1dia
        	return 75;
        else
        	return 200;
	}
	
	public String getResume() throws RemoteException {
		String ret = "";
		for(Veiculo veic: estacionamento) {
			ret += veic.getPlaca();
			if (veic.isPago())
				ret += " ( P )";
			ret += "\n";
		}
		return ret;
	}
	
	public String getRelatorio() throws RemoteException {
		String ret = "";
		for(Historico Historico: relatorio) {
			ret += Historico.toString();
			ret += "\n";
		}
		return ret;
	}
	
	public int getNumVeiculosPagos() {
		int num = 0;
		
		for(Veiculo veiculo : estacionamento)
			if(veiculo.isPago())
				num++;
		
		return num;
	}
	
	public int getNumVeiculosEstacionados() {
		return estacionamento.size();
	}
	
	public ArrayList<Veiculo> getAllVeiculos() {
		return estacionamento;
	}

	@Override
	public void Limpar() throws RemoteException {
		estacionamento.clear();
		relatorio.clear();
	}

	public synchronized ArrayList<Veiculo> CloneEstacionamento() throws RemoteException{
		return estacionamento;
	}
	
	
	public void setEstacionamento(ArrayList<Veiculo> estacionamento) {
		this.estacionamento = estacionamento;
	}

	public void setRelatorio(ArrayList<Historico> relatorio) {
		this.relatorio = relatorio;
	}

	public synchronized ArrayList<Historico> CloneRelatorio() throws RemoteException {
		return relatorio;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
