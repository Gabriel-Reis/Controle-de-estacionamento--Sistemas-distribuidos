package view.scenes.cliente;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import connection.Client;
import connection.models.Veiculo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.img.ImgLocation;

public class ClienteController implements Initializable {

    @FXML
    private JFXTextField txt_placa_veiculo;

    @FXML
    private JFXButton btn_buscar;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXTimePicker timePicker;

    @FXML
    private Label txt_hora_parado;

    @FXML
    private Label txt_valor;
    
    @FXML
    private Label txt_pago;

    @FXML
    private ImageView img_cancela;

    @FXML
    private JFXButton btn_sair;

    @FXML
    private JFXButton btn_entrar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		datePicker.setValue(LocalDate.now());
		timePicker.setValue(LocalTime.now());
		
		btn_buscar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Veiculo veiculo = Client.BuscarVeiculo(txt_placa_veiculo.getText().toString());
				
				if(veiculo!=null) {
					datePicker.setValue(LocalDate.of(veiculo.getEntrada().getYear(), veiculo.getEntrada().getMonth(), veiculo.getEntrada().getDayOfMonth()));
					
					timePicker.setValue(LocalTime.of(veiculo.getEntrada().getHour(), veiculo.getEntrada().getMinute()));
					
					Duration duration = Duration.between(veiculo.getEntrada(), LocalDateTime.now());
					
					long horas = TimeUnit.MILLISECONDS.toHours(duration.toMillis());;
					long minutos = TimeUnit.MILLISECONDS.toMinutes(duration.toMillis()) - (horas * 60);
					
					txt_hora_parado.setText(horas + " horas e " + minutos + " minutos parado");
					txt_valor.setText(String.format("R$ %.2f", calcularValor(veiculo)));
					
					if(veiculo.isPago())
						txt_pago.setText("Pago!");
					else
						txt_pago.setText("Não pagou.");
				} else
					JOptionPane.showMessageDialog(null, "O veículo não foi encontrado", "Falha", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		btn_entrar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(Client.BuscarVeiculo(txt_placa_veiculo.getText()) == null) {
					if(!txt_placa_veiculo.getText().isEmpty() && datePicker.validate() && timePicker.validate()) {
						Veiculo veiculo = getVeiculo();
						
						Client.printLog(">> ENTRAR VEICULO " + veiculo.getPlaca());
						
						if(Client.entrarVeiculo(veiculo))
							abrirCancela();
						
						txt_hora_parado.setText("");
						txt_pago.setText("");
						txt_valor.setText("");
						
					} else
						JOptionPane.showMessageDialog(null, "Preencha de forma válida todos os campos necessários", "Falha", JOptionPane.INFORMATION_MESSAGE);
				} else
					JOptionPane.showMessageDialog(null, "O veículo já está no estacionamento", "Falha", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		btn_sair.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(Client.BuscarVeiculo(txt_placa_veiculo.getText()) != null) {
					Veiculo veiculo = getVeiculo();
					
					Client.printLog("<< SAIR VEICULO " + veiculo.getPlaca());
					
					if(Client.sairVeiculo(veiculo))
						abrirCancela();
					else {
						int reply = JOptionPane.showConfirmDialog(
								null,
								"O valor a pagar é de R$ " + calcularValor(veiculo) + ", o valor foi pago?",
								"Pagamento",
								JOptionPane.YES_NO_OPTION);
						
						if(reply == JOptionPane.YES_OPTION) {
							Client.pagar(veiculo);
						
							reply = JOptionPane.showConfirmDialog(
									null,
									"Abrir cancela para o veículo?",
									"Mensagem de confirmação",
									JOptionPane.YES_NO_OPTION);
							
							if(reply == JOptionPane.YES_OPTION)
								Client.sairVeiculo(veiculo);
						}
					}
					
					txt_hora_parado.setText("");
					txt_pago.setText("");
					txt_valor.setText("");
					
				} else
					JOptionPane.showMessageDialog(null, "O veículo não foi encontrado", "Falha", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
	}

	private Veiculo getVeiculo() {
		return new Veiculo(txt_placa_veiculo.getText().toUpperCase(), datePicker.getValue().getDayOfMonth(), datePicker.getValue().getMonthValue(), datePicker.getValue().getYear(), timePicker.getValue().getHour(), timePicker.getValue().getMinute());
	}
	
	private void abrirCancela() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				btn_buscar.setDisable(true);
				btn_entrar.setDisable(true);
				btn_sair.setDisable(true);
				
				img_cancela.setImage(new Image(ImgLocation.class.getResourceAsStream("2.png")));
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				img_cancela.setImage(new Image(ImgLocation.class.getResourceAsStream("3.png")));
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				img_cancela.setImage(new Image(ImgLocation.class.getResourceAsStream("1.png")));
				
				btn_buscar.setDisable(false);
				btn_entrar.setDisable(false);
				btn_sair.setDisable(false);
			}
		}).start();
	}
	
	private float calcularValor(Veiculo v) {
		LocalDateTime pagando = LocalDateTime.now();
		LocalDateTime localDateTime = v.getEntrada();

        long difEmMinutos = ChronoUnit.MINUTES.between(localDateTime, pagando);
        
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
