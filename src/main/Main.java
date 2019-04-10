package main;

import dataBase.DataBase;

public class Main {

	public static void main(String[] args) {
		DataBase database =new DataBase();
		database.Entrar("00"); //agora
		database.Entrar("20 min",10,4,2019,18,34); //20min
		database.Entrar("1 hora",10,4,2019,17,54); //1h
		database.Entrar("5 horas",10,4,2019,13,54); //5horas
		database.Entrar("1 dia",9,4,2019,18,54); //1 dia
		database.Entrar("3 dias",7,4,2019,18,54); //3 dias
			
		database.pagar("00");
		database.pagar("20 min");
		database.pagar("1 hora");
		database.pagar("5 horas");
		database.pagar("1 dia");
		database.pagar("3 dias");

		database.Sair("00");
		database.Sair("20 min");
		database.Sair("1 hora");
		database.Sair("5 horas");
		database.Sair("1 dia");
		database.Sair("3 dias");
	}

}
