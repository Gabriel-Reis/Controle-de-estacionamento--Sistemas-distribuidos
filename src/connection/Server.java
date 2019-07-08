package connection;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;

import connection.models.Estacionamento;
import connection.util.IEstacionamento;

public class Server {
	
	public enum ServerType {
		HIGH,
		LOW
	}
	
	private int port;
	private Registry registry;
	private static Estacionamento estacionamento;
	private IEstacionamento server;
	
	public Server(final int port, ServerType serverType) {
		this.port = port;
		
		if(serverType == ServerType.HIGH)
			this.port--;
		else
			this.port++;
		
		try {
			initialize(serverType);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void initialize(ServerType serverType) throws Exception {
		registry = LocateRegistry.createRegistry(port);
		
		estacionamento = new Estacionamento();
		estacionamento.setPort(port);
		registry.rebind("Estacionamento", estacionamento);
		
		int portOther = this.port;
		if(serverType == ServerType.HIGH)
			portOther++;
		else
			portOther--;
		
		
		try {
			LocateRegistry.getRegistry(portOther);
			server = (IEstacionamento)Naming.lookup("//localhost:" + (portOther) + "/Estacionamento");
			estacionamento.setEstacionamento(server.CloneEstacionamento());
			estacionamento.setRelatorio(server.CloneRelatorio());
		} catch(Exception ex) {
			System.out.println("Não há informação para ser copiada na porta: "+String.valueOf(portOther));
		}
		
	}
	
	public void destroy() throws Exception {
		Naming.unbind("//localhost:" + port +"/Estacionamento");
    	System.exit(0);
	}
	
	public static void gerarRelatorio() {
		Client.printLog("!! GERAR RELATÓRIO");
		
		try {
			if(estacionamento.getRelatorio().isEmpty())
				JOptionPane.showMessageDialog(null, "Relatório vazio.");
			else
				JOptionPane.showMessageDialog(null, estacionamento.getRelatorio());
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Estacionamento getEstacionamento() {
		return estacionamento;
	}

}
