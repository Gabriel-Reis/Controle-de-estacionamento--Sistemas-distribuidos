

import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

public class Cliente {
	public static void main(String[] args) {
		try {
			IEstacionamento EstaRMI = (IEstacionamento)Naming.lookup("//localhost:3007/Estacionamento");	
			Teste(EstaRMI);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e);
		}
	}
	
	private static void Teste(IEstacionamento EstaRMI) {
		
		Veiculo Veic1 = new Veiculo("00");
		Veiculo Veic2 = new Veiculo("01"); 
		Veiculo Veic3 = new Veiculo("02"); 
		Veiculo Veic4 = new Veiculo("03"); 
		Veiculo Veic5 = new Veiculo("04"); 
		
		try {
			
			EstaRMI.Limpar();
			
			EstaRMI.Entrar(Veic1);
			EstaRMI.Entrar(Veic2);
			EstaRMI.Entrar(Veic3);
			EstaRMI.Entrar(Veic4);
			EstaRMI.Entrar(Veic5);
			JOptionPane.showMessageDialog(null, "-- " +EstaRMI.getResume());
			
			EstaRMI.pagar(Veic1);
			EstaRMI.pagar(Veic2);
			EstaRMI.pagar(Veic3);
			
			JOptionPane.showMessageDialog(null, "-- " +EstaRMI.getResume());
			
			EstaRMI.Sair(Veic1);
			EstaRMI.Sair(Veic2);
			
			JOptionPane.showMessageDialog(null, "-- " +EstaRMI.getResume());
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		
	}
}
