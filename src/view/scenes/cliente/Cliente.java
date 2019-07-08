package view.scenes.cliente;

import connection.Client;
import main.MainClient;
import view.scenes.util.FXMLClass;

public class Cliente extends FXMLClass {
	
	@Override
	protected void initializeElementaryVariables() {
		ICON_IMAGE = null;
		FXML_LOCATION_SHORTCUT = Cliente.class.getResource("\\FXMLCliente.fxml");
		CSS_LOCATION_SHORTCUT = null;
		
		TITLE = "Página Inicial";
	}
	
	@Override
    public void init() throws Exception {
		MainClient.currentClient = Client.connectInServer();
    }

}