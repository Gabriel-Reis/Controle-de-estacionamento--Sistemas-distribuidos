package view.scenes.preloader;

import java.net.URL;

import javax.swing.JOptionPane;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.MainClient;

public class MyPreloader extends Preloader {

	public static final URL FXML_LOCATION_SHORTCUT = MyPreloader.class.getResource("\\FXMLPreloader.fxml");
	
    private Stage preloaderStage;
    private Scene scene;

    public MyPreloader() {}

    @Override
    public void start(Stage primaryStage) throws Exception {
    	this.preloaderStage = primaryStage;

    	Parent parent = (Parent) FXMLLoader.load(FXML_LOCATION_SHORTCUT, null, new JavaFXBuilderFactory());
		scene = new Scene(parent);
    	
        preloaderStage.setScene(scene);
        preloaderStage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        StateChangeNotification.Type type = info.getType();
        
        switch (type) {
            case BEFORE_LOAD:
                break;
            case BEFORE_INIT:
                break;
            case BEFORE_START:
                if(!MainClient.currentClient.isConnected()) {
                	JOptionPane.showConfirmDialog(null, "Não foi possível conectar com o servidor.", "Tempo limite esgotado.", JOptionPane.DEFAULT_OPTION);
                	System.exit(-1);
                } else {
                	JOptionPane.showConfirmDialog(null, "Conectado com suesso!", "Conectado", JOptionPane.DEFAULT_OPTION);
                }
                
                preloaderStage.hide();
                
                break;
        }
    }
}
