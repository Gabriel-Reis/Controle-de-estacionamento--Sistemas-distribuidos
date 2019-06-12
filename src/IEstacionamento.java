import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IEstacionamento extends Remote {
	
	public void Entrar(Veiculo v) throws RemoteException;
	public boolean Sair(Veiculo v)  throws RemoteException;
	public boolean pagar(Veiculo v)  throws RemoteException;
	public Veiculo BuscarEstacionado(Veiculo v)  throws RemoteException;
	public float CalcularValor(Veiculo v) throws RemoteException;
	public String getResume() throws RemoteException;
	public String getRelatorio() throws RemoteException;
	public void Limpar() throws RemoteException;
	public ArrayList<Veiculo> CloneEstacionamento() throws RemoteException;
	public ArrayList<Historico> CloneRelatorio() throws RemoteException;
}