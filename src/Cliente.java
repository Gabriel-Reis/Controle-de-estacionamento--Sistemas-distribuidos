import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
//import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;

public class Cliente {
	
	private static int portMenor = 3054;
	private static int portMaior = portMenor+1;
	//private static boolean balance = true;
	
	public static void main(String[] args) {
		boolean Sa = false, Sb = false;
		IEstacionamento ServerA = null;
		IEstacionamento ServerB = null;
		try {
			ServerA = (IEstacionamento)Naming.lookup("//localhost:" +portMenor +"/Estacionamento");
			Sa = true;
		} catch (Exception e) {
			//JOptionPane.showMessageDialog(null,e);
		}
		try {
			ServerB = (IEstacionamento)Naming.lookup("//localhost:" +portMaior +"/Estacionamento");
			Sb = true;
		} catch (Exception e) {
			//JOptionPane.showMessageDialog(null,e);
		}
		
		if(Sa && Sb)
			Teste(ServerA, ServerB);
		else if (Sa)
			Teste(ServerA, ServerB);
		else if (Sb)
			Teste(ServerA, ServerB);
		
		
	}
	
	private static void Teste(IEstacionamento ... server) {
		
		Veiculo Veic1 = new Veiculo("ABC-0001",12,06,2019,02,30);
		Veiculo Veic2 = new Veiculo("ABC-0002",12,06,2019,02,30); 
		Veiculo Veic3 = new Veiculo("ABC-0003",12,06,2019,02,30); 
		Veiculo Veic4 = new Veiculo("ABC-0004",12,06,2019,01,00); 
		Veiculo Veic5 = new Veiculo("ABC-0005",12,06,2019,02,10); 
		
		try {
			for(IEstacionamento e : server) {
				if(e != null) {
					e.Limpar();
				}
			}
			
			
			if( entrar(Veic1,server) )
				abrirCancela();
			if( entrar(Veic2,server) )
				abrirCancela();
			if( entrar(Veic3,server) )
				abrirCancela();
			if( entrar(Veic4,server) )
				abrirCancela();
			if( entrar(Veic5,server) )
				abrirCancela();
			JOptionPane.showMessageDialog(null, server[0].getResume(),"ServerA Resumo", JOptionPane.INFORMATION_MESSAGE);
			
			pagar(Veic1,server);
			pagar(Veic2,server);
			pagar(Veic3,server);
			pagar(Veic3,server);
			
			JOptionPane.showMessageDialog(null, server[0].getResume(),"ServerA Resumo", JOptionPane.INFORMATION_MESSAGE);
			
			if( sair(Veic1,server) )
				abrirCancela();
			if( sair(Veic2,server) )
				abrirCancela();
			
			/*try {
				server[1] = (IEstacionamento)Naming.lookup("//localhost:" +portMaior +"/Estacionamento");
			} catch (MalformedURLException e1) {
			} catch (NotBoundException e1) {
			}*/
			
			JOptionPane.showMessageDialog(null, server[0].getResume(),"ServerA Resumo", JOptionPane.INFORMATION_MESSAGE);
			
			entrar(Veic1,server);
			pagar(Veic1,server);
			sair(Veic1,server);
			
			JOptionPane.showMessageDialog(null, server[0].getRelatorio(),"ServerA Relatorio", JOptionPane.INFORMATION_MESSAGE);
			JOptionPane.showMessageDialog(null, server[0].getRelatorio(),"ServerB Relatorio", JOptionPane.INFORMATION_MESSAGE);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private static void abrirCancela() {
		//
	}
	
	private static boolean entrar(Veiculo Veic, IEstacionamento ... server) {
		
		boolean add = false;
		for(IEstacionamento e : server) {
			if(e != null){
				try {
					e.Entrar(Veic);
					add = true;
				}catch (RemoteException re) {
					JOptionPane.showMessageDialog(null, "One server off");
					e=null;
				}
			}
		}
		return add;
		
	}
	
	private static boolean pagar(Veiculo Veic, IEstacionamento ... server) {
		boolean add = false;
		for(IEstacionamento e : server) {
			if(e != null){
				try {
					e.pagar(Veic);
					add = true;
				}catch (RemoteException re) {
					JOptionPane.showMessageDialog(null, "One server off");
					e=null;
				}
			}
		}
		return add;
	}
	
	private static boolean sair(Veiculo Veic, IEstacionamento ... server) {
		boolean add = false;
		for(IEstacionamento e : server) {
			if(e != null){
				try {
					e.Sair(Veic);
					add = true;
				}catch (RemoteException re) {
					JOptionPane.showMessageDialog(null, "One server off");
					e=null;
				}
			}
		}
		return add;
	}
	
	/*private synchronized static IEstacionamento Balanceamento(IEstacionamento serverA, IEstacionamento serverB) {
		if(balance) {
			balance = false;
			System.out.println("a");
			return serverA;
		}
		else {
			balance = true;
			System.out.println("b");
			return serverB;
		}
	}*/
	
}
