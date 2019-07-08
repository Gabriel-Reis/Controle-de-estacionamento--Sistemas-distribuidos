package main;

import com.sun.javafx.application.LauncherImpl;

import connection.Client;
import view.scenes.cliente.Cliente;
import view.scenes.preloader.MyPreloader;

@SuppressWarnings("restriction")
public class MainClient {
	
	public static final int valorHora = 10;
	
	public static Client currentClient;
	
	public static void main(String[] args) {
        LauncherImpl.launchApplication(Cliente.class, MyPreloader.class, args);
    }
	
}
