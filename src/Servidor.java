

import java.rmi.registry.*;

import javax.swing.JOptionPane;

public class Servidor {
	public static void main (String[] args) {
		try {
			Registry r = LocateRegistry.createRegistry(3006);
			r.rebind("Estacionamento", new Estacionamento());
			JOptionPane.showMessageDialog(null, "Server on");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Server off" +e);
		}
	}
}
