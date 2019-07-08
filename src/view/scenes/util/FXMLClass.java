package view.scenes.util;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public abstract class FXMLClass extends Application {
	
	protected static Image ICON_IMAGE = null;
	protected static URL FXML_LOCATION_SHORTCUT = null;
	protected static URL CSS_LOCATION_SHORTCUT = null;
	protected static Object CONTROLLER = null;
	
	protected static String TITLE = null;
	
	protected static Stage primaryStage;
	
	public FXMLClass() {
		initializeElementaryVariables();
	}
	
	protected abstract void initializeElementaryVariables();
	
	@Override
	public abstract void init() throws Exception;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		if(FXML_LOCATION_SHORTCUT == null) {
			System.err.println("É necessário informar o 'FXML_LOCATION_SHORTCUT.'");
			System.exit(0);
		}
		
		primaryStage.setTitle(TITLE);
		
		try {
			initializePrimaryStage(primaryStage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@SuppressWarnings("static-access")
	private void initializePrimaryStage(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		
		FXMLLoader loader = new FXMLLoader(FXML_LOCATION_SHORTCUT);
		
		if(CONTROLLER != null)
			loader.setController(CONTROLLER);
			
		
		Parent parent = (Parent) loader.load();
		Scene scene = this.primaryStage.getScene();
		
		if(scene == null) {
			scene = new Scene(parent);
			if(CSS_LOCATION_SHORTCUT != null)
				parent.getStylesheets().add(CSS_LOCATION_SHORTCUT.toExternalForm());
			this.primaryStage.setScene(scene);
		} else {
			if(CSS_LOCATION_SHORTCUT != null)
				parent.getStylesheets().add(CSS_LOCATION_SHORTCUT.toExternalForm());
			this.primaryStage.getScene().setRoot(parent);
		}
		
		if(ICON_IMAGE != null)
			this.primaryStage.getIcons().add(ICON_IMAGE);
		
		this.primaryStage.sizeToScene();
		
		this.primaryStage.show();
		
		afterInitialize();
	}
	
	protected void afterInitialize() {}
	
}
