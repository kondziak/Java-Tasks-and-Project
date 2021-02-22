package ClientApp;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class App extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			primaryStage.setTitle("HairDresser Client Logging App");
			primaryStage.setScene(new Scene(root,500,300));
			primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
