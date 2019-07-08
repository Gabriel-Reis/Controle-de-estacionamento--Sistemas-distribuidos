package view.scenes.servidor;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import connection.Server;
import connection.models.Veiculo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ServerController implements Initializable {

	private int port;
	private Server server;
	
	public ServerController(int port, Server server) {
		this.port = port;
		this.server = server;
	}
	
    @FXML
    private Label txt_Port;

    @FXML
    private TextArea txta_Log;

    @FXML
    private JFXButton btn_LimparLog;

    @FXML
    private Label txt_NumVeiculosPagos;

    @FXML
    private Label txt_NumVeiculosEstacionados;

    @FXML
    private JFXButton btn_GerarRelatorio;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txt_Port.setText(String.valueOf(port));
		
		updateUI();
		
		btn_LimparLog.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				updateUI();
			}
		});
		
		btn_GerarRelatorio.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Server.gerarRelatorio();
			}
		});
	}

	public void setNumVeiculosPagos(int num) {
		txt_NumVeiculosPagos.setText(String.valueOf(num));
	}
	
	public void setNumVeiculosEstacionados(int num) {
		txt_NumVeiculosEstacionados.setText(String.valueOf(num));
	}
	
	public void updateUI() {
		txt_NumVeiculosEstacionados.setText(String.valueOf(server.getEstacionamento().getNumVeiculosEstacionados()));
		txt_NumVeiculosPagos.setText(String.valueOf(server.getEstacionamento().getNumVeiculosPagos()));
		
		String text = "";
		
		if(server.getEstacionamento().getAllVeiculos().isEmpty())
			txta_Log.setText("Estacionamento vazio.");
		else {
			for(Veiculo veiculo : server.getEstacionamento().getAllVeiculos()) {
				if(veiculo.isPago())
					text += String.format("VEÍCULO: %s\t (PAGO)\n", veiculo.getPlaca());
				else
					text += String.format("VEÍCULO: %s\t (PENDENTE)\n", veiculo.getPlaca(), veiculo.isPago());
			}
			
			txta_Log.setText(text);
		}
	}
	
}
