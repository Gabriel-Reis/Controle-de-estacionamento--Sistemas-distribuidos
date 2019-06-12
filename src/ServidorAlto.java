import java.rmi.Naming;
import java.rmi.registry.*;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class ServidorAlto{
	
	private static int portHigh = 3054+1;	 //NÃO USAR 3006
	
	public static void main (String[] args) {
	
		new Thread() {
			@Override
		    public void run() {
		    	try {
					Registry r = LocateRegistry.createRegistry(portHigh);
					Estacionamento estaciona = new Estacionamento();
					r.rebind("Estacionamento", estaciona);
					
		    		try {
		    			LocateRegistry.getRegistry(portHigh-1);
		    		}catch (Exception e) {
		    			IEstacionamento ServerH = (IEstacionamento)Naming.lookup("//localhost:" +(portHigh-1) +"/Estacionamento");
		    			ServerH.CloneEstacionamento();
		    			ServerH.CloneRelatorio();
					}
					
					UIManager.put("OptionPane.okButtonText", "Encerrar servidor");
					int opcao = JOptionPane.showConfirmDialog(null, "Server on Port: " +String.valueOf(portHigh), "Server on", JOptionPane.DEFAULT_OPTION);
					
					if(opcao == 0){
						try {  
					    	Naming.unbind("//localhost:" +portHigh +"/Estacionamento");
					    	System.exit(0);
					    } catch (Exception e) {
					    	e.printStackTrace();
					    }  
					}
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Server off" +e);
				}
		    	
		    }
		  }.start();
	}
}
