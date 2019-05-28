import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Estacionamento extends UnicastRemoteObject implements IEstacionamento {

	private ArrayList<Veiculo> estacionamento = new ArrayList<Veiculo>();
	
	public Estacionamento() throws RemoteException{
	}

	public void Entrar(Veiculo v) throws RemoteException {
		estacionamento.add(v);
	}

	public boolean Sair(Veiculo v) throws RemoteException  {
		Veiculo saindo = BuscarEstacionado(v);
		if(saindo != null && saindo.isPago()) {
			estacionamento.remove(saindo);
			return true;
		}
		else
			return false;
	}
	
	public boolean pagar(Veiculo v) throws RemoteException  {
		Veiculo pagando = BuscarEstacionado(v);
		if(pagando.isPago()) {
			return true;
		}
		else {
			//float valor = CalcularValor(v);
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

	@Override
	public void Limpar() throws RemoteException {
		estacionamento.clear();
	}
}
