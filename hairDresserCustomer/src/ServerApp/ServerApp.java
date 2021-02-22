package ServerApp;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerApp extends javafx.application.Application {

	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ServerApp Window.fxml"));
		primaryStage.setTitle("Hairdresser server");
		primaryStage.setScene(new Scene(root,600,300));
		primaryStage.setOnCloseRequest(e -> System.exit(0));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
