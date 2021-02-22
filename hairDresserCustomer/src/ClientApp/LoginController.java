package ClientApp;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private AnchorPane mainWindow;
	@FXML
	private TextField name;
	@FXML
	private TextField surname;
	@FXML
	private Button connectButton;
	
	
	public void initialize() {
		connectButton.setOnMouseClicked(e->{
			try {
				tryToLoginToServer();
			} catch (IOException e1) {
				Alert alert = new Alert(AlertType.ERROR,"Seems like hairdresser App is unavailable");
				alert.show();
				return;
			}
		});
	}
	
	public void tryToLoginToServer() throws IOException {
		String userName = name.getText();
		String userSurname = surname.getText();
		
		if(userName.length() == 0) {
			Alert alarm = new Alert(AlertType.ERROR,"Enter name");
			alarm.show();
			return;
		}
		if(userSurname.length() == 0) {
			Alert alarm = new Alert(AlertType.ERROR,"Enter your surname");
			alarm.show();
			return;
		}
		FXMLLoader newLoader = new FXMLLoader(getClass().getResource("AppWindow.fxml"));
		Parent root = (Parent) newLoader.load();
		Stage newStage = new Stage();
		newStage.setTitle("Client App - Main Window");
		newStage.setScene(new Scene(root,600,400));
		newStage.setOnCloseRequest(e -> System.exit(0));
		String clientInfo = String.format("%s %s", userName,userSurname);
		AppController controller =(AppController) newLoader.getController();
		ClientConnectionController clientController = new ClientConnectionController(controller);
		clientController.addClient(clientInfo);
		newStage.show();
		clientController.start();
		controller.initialize(clientInfo,clientController);
		this.connectButton.getScene().getWindow().hide();

	}
}
