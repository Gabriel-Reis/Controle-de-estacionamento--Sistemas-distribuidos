package connection;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import connection.models.Veiculo;
import connection.util.IEstacionamento;


public class Client {
	
	private static Client singletonClient;
	
	private final static int PORT_MENOR = 3058;
	private final static int PORT_MAIOR = PORT_MENOR + 1;
	
	private static ArrayList<IEstacionamento> servidores = new ArrayList<>();
	
	private static IEstacionamento serverA;
	private static IEstacionamento serverB;
	
	//private static boolean balance = true;
	
	private Client() {
		connect();
	}
	
	private static void connect() {
		servidores.clear();
		serverA = connectInServer(PORT_MENOR);
		servidores.add(serverA);
		
		serverB = connectInServer(PORT_MAIOR);
		servidores.add(serverB);
	}
	
	private static IEstacionamento connectInServer(final int PORT) {
		IEstacionamento server = null;
		
		try {
			 server = (IEstacionamento)Naming.lookup("//localhost:" + PORT +"/Estacionamento");
			 System.out.println("OK: Conexão com o servidor estabelecida com sucesso [PORT:" + PORT + "].");
		} catch (Exception ex) {
			System.err.println("ERROR: Não foi possível estabelecer conexão com o servidor [PORT: " + PORT + "].");
		}
		
		return server;
	}
	
	public static Client connectInServer() {
		if(singletonClient == null)
			singletonClient = new Client();
		
		return singletonClient;
	}
	
	public boolean isConnected() {
		for(IEstacionamento servidor : servidores) {
			if(servidor != null)
				return true;
		}
		return false;
	}
	
	public static boolean entrarVeiculo(Veiculo veiculo) {
		boolean add = false;
		connect();
		
		for(IEstacionamento servidor : servidores) {
			if(servidor != null){
				try {
					servidor.Entrar(veiculo);
					add = true;
				} catch (RemoteException re) {
					JOptionPane.showMessageDialog(null, "One server off");
					servidor=null;
				}
			}
		}
		
		return add;
	}
	
	public static boolean pagar(Veiculo veiculo) {
		boolean add = false;
		connect();
		
		for(IEstacionamento servidor : servidores) {
			if(servidor != null){
				try {
					servidor.pagar(veiculo);
					add = true;
				} catch (RemoteException re) {
					JOptionPane.showMessageDialog(null, "One server off");
					servidor=null;
				}
			}
		}
		
		if(add)
			JOptionPane.showMessageDialog(null, "Pagamento Efetuado");
		
		return add;
	}
	
	public static boolean sairVeiculo(Veiculo veiculo) {
		boolean add = false;
		connect();
		
		for(IEstacionamento server : servidores) {
			if(server != null){
				try {
					add = server.Sair(veiculo);
				} catch (RemoteException re) {
					JOptionPane.showMessageDialog(null, "One server off");
					server=null;
				}
			}
		}
		
		return add;
	}
	
	public static Veiculo BuscarVeiculo(String placa) {
		connect();
		placa = placa.toUpperCase();
		Veiculo veiculo = null;
		
		Client.printLog("?? BUSCAR " + placa);
		
		for(IEstacionamento server : servidores) {
			if(server != null){
				try {
					veiculo = server.BuscarEstacionado(new Veiculo(placa));
				} catch (RemoteException re) {
					JOptionPane.showMessageDialog(null, "One server off");
					server=null;
				}
			}
		}
		
		return veiculo;
	}
	
	public static void printLog(String log) {
		LocalDateTime now = LocalDateTime.now();
		
		System.out.printf("%02d/%02d/%02d [%02d:%02d:%02d]\t%s\n", now.getDayOfMonth(), now.getMonthValue(), now.getYear(), now.getHour(), now.getMinute(), now.getSecond(), log);
	}
	
}
