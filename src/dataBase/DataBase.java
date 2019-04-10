package dataBase;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;

import objetos.*;

public class DataBase {

	private ArrayList<Veiculo> estacionamento = new ArrayList<Veiculo>();
	
	public void Entrar(String placa) {
		estacionamento.add(new Veiculo(placa));
	}
	
	public void Entrar(String placa, int dia, int mes, int ano,  int hora, int minuto) {
		estacionamento.add(new Veiculo(placa, dia, mes, ano,  hora, minuto));
	}
	
	public boolean Sair(String placa) {
		Veiculo saindo = BuscarEstacionado(placa);
		if(saindo != null && saindo.isPago()) {
			estacionamento.remove(saindo);
			return true;
		}
		else
			return false;
	}
	
	public boolean pagar(String placa) {
		Veiculo pagando = BuscarEstacionado(placa);
		if(pagando.isPago())
			return true;
		else {
			float valor = CalcularValor(pagando.getEntrada());
			System.out.println("Placa: " +placa +" Valor:" +valor);
			pagando.setPago(true);
			return true;
		}
	}
	
	private Veiculo BuscarEstacionado(String placa) {
		for(Veiculo veic: estacionamento) {
			if(veic.getPlaca().equals(placa)) {
				return veic;
			}
		}
		return null;
	}

	private float CalcularValor(LocalDateTime localDateTime) {
		float valor;
		LocalDateTime pagando = LocalDateTime.now();
        /*// Calcula a diferença de dias entre as duas datas
		long difEmMes = ChronoUnit.MONTHS.between(localDateTime, pagando); 
        //Calcula a diferença de dias entre as duas datas
        long difEmDias = ChronoUnit.DAYS.between(localDateTime, pagando);
        // Calcula a diferença de meses entre as duas datas
        long difEmHoras = ChronoUnit.HOURS.between(localDateTime, pagando);*/
        // Calcula a diferença de meses entre as duas datas
        long difEmMinutos = ChronoUnit.MINUTES.between(localDateTime, pagando);
        
        valor = 0;
        
        if(difEmMinutos < 15) 	//TOLERANCIA
        	return 0;
        if(difEmMinutos <= 30)	//30 Min
        	return 3;
        if(difEmMinutos <= 60) 	//1 HORA
        	return 4;
        if(difEmMinutos < 600) 	//1 HORA ATÉ 10 HORAS -> 5 reais a hora
        	return ((difEmMinutos%60)*5);
        if(difEmMinutos <= 1440) //10 horas até 1dia
        	return 75;
        else
        	return 200;
        
	}


	





}
