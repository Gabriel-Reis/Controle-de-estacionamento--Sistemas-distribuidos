import java.rmi.Naming;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;

public class Cliente {
	
	private static int port = 3041;
	private static boolean balance = true;
	
	public static void main(String[] args) {
		try {
			IEstacionamento ServerA = (IEstacionamento)Naming.lookup("//localhost:" +port +"/Estacionamento");
			//int port2 = port+1;
			//IEstacionamento ServerB = (IEstacionamento)Naming.lookup("//localhost:" +port2 +"/Estacionamento");
			Teste(ServerA);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e);
		}
	}
	
	private static void Teste(IEstacionamento ServerA) {
		
		Veiculo Veic1 = new Veiculo("ABC-0001");
		Veiculo Veic2 = new Veiculo("ABC-0002"); 
		Veiculo Veic3 = new Veiculo("ABC-0003"); 
		Veiculo Veic4 = new Veiculo("ABC-0004"); 
		Veiculo Veic5 = new Veiculo("ABC-0005"); 
		
		try {
			
			ServerA.Limpar();
			//ServerB.Limpar();
			
			entrar(Veic1, ServerA, ServerA);
			entrar(Veic2, ServerA, ServerA);
			entrar(Veic3, ServerA, ServerA);
			entrar(Veic4, ServerA, ServerA);
			entrar(Veic5, ServerA, ServerA);
			JOptionPane.showMessageDialog(null, "-- " +ServerA.getResume());
			
			pagar(Veic1, ServerA, ServerA);
			pagar(Veic2, ServerA, ServerA);
			pagar(Veic3, ServerA, ServerA);
			pagar(Veic3, ServerA, ServerA);
			
			JOptionPane.showMessageDialog(null, "-- " +ServerA.getResume());
			
			sair(Veic1, ServerA, ServerA);
			sair(Veic2, ServerA, ServerA);
			
			JOptionPane.showMessageDialog(null, "-- " +ServerA.getResume());
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private static void entrar(Veiculo Veic,IEstacionamento ServerA,IEstacionamento ServerB) {
		try {
			if(balance) {
				ServerA.Entrar(Veic);
			}else {
				ServerB.Entrar(Veic);
			}
			if(balance) balance = false; else balance = true;
		}catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private static void pagar(Veiculo Veic,IEstacionamento ServerA,IEstacionamento ServerB) {
		try {
			if(balance) {
				ServerA.pagar(Veic);
			}else {
				ServerB.pagar(Veic);
			}
			if(balance) balance = false; else balance = true;
		}catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private static void sair(Veiculo Veic,IEstacionamento ServerA,IEstacionamento ServerB) {
		try {
			if(balance) {
				ServerA.Sair(Veic);
			}else {
				ServerB.Sair(Veic);
			}
			if(balance) balance = false; else balance = true;
		}catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
