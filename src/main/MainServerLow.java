package main;

import connection.Server;
import connection.Server.ServerType;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import view.scenes.servidor.ServerController;
import view.scenes.util.FXMLClass;

public class MainServerLow extends FXMLClass {

	private static final int PORT_LOW = 3058; //NÃO USAR 3006
	private static Server lowServer;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	protected void initializeElementaryVariables() {
		ICON_IMAGE = null;
		FXML_LOCATION_SHORTCUT = ServerController.class.getResource("\\FXMLServidor.fxml");
		CSS_LOCATION_SHORTCUT = null;
		
		lowServer = new Server(PORT_LOW, ServerType.LOW);
		CONTROLLER = new ServerController(PORT_LOW, lowServer);
		
		TITLE = "Low Server";
	}

	@Override
	public void init() throws Exception {}
	
	@Override
	protected void afterInitialize() {
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				try {
					lowServer.destroy();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		((ServerController)CONTROLLER).updateUI();
		
		initializeInfoThreadServer();
	}
	
	private void initializeInfoThreadServer() {
		Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                    	((ServerController)CONTROLLER).updateUI();
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {}

                    Platform.runLater(updater);
                }
            }

        });
		
        thread.setDaemon(true);
        thread.start();
	}
	
}
