import java.rmi.Naming;
import java.rmi.registry.*;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Servidor{
	private static int port = 3041;	 //NÃO USAR 3006
	
	public static void main (String[] args) {
	
		try {
			Registry r = LocateRegistry.createRegistry(port);
			//Registry r2 = LocateRegistry.createRegistry(port+1);
			Estacionamento estaciona = new Estacionamento();
			r.rebind("Estacionamento", estaciona);
			//r2.rebind("Estacionamento", estaciona);
			
			UIManager.put("OptionPane.okButtonText", "Encerrar servidor");
			int opcao = JOptionPane.showConfirmDialog(null, "Server on", "Server on", JOptionPane.DEFAULT_OPTION);
			
			if(opcao == 0){
				try {  
					//int port2 = port+1;
			    	Naming.unbind("//localhost:" +port +"/Estacionamento");
			    	//Naming.unbind("//localhost:" +port2 +"/Estacionamento");
			    	System.exit(0);
			    } catch (Exception e) {
			    	e.printStackTrace();
			    }  
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Server off" +e);
		}
	}
	
}
