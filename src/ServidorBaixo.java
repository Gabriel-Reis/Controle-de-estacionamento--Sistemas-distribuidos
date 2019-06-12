import java.rmi.Naming;
import java.rmi.registry.*;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class ServidorBaixo{
	
	private static int portLow = 3054;	 //NÃO USAR 3006
	
	public static void main (String[] args) {
	
		new Thread() {
			@Override
		    public void run() {
		    	try {	
					Registry r = LocateRegistry.createRegistry(portLow);
					Estacionamento estaciona = new Estacionamento();
					r.rebind("Estacionamento", estaciona);
					
		    		try {
		    			LocateRegistry.getRegistry(portLow+1);
		    		}catch (Exception e) {
		    			IEstacionamento ServerH = (IEstacionamento)Naming.lookup("//localhost:" +portLow+1 +"/Estacionamento");
		    			ServerH.CloneEstacionamento();
		    			ServerH.CloneRelatorio();
					}
					
					UIManager.put("OptionPane.okButtonText", "Encerrar servidor");
					int opcao = JOptionPane.showConfirmDialog(null, "Server on Port: " +String.valueOf(portLow), "Server on", JOptionPane.DEFAULT_OPTION);
					
					if(opcao == 0){
						try {  
					    	Naming.unbind("//localhost:" +portLow +"/Estacionamento");
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
