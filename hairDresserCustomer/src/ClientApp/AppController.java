package ClientApp;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;

public class AppController {
	@FXML
	private Button bookButton;
	@FXML
	private ListView<Label> calendarList;

	private String clientInfo = null, time=null;
	private ClientConnectionController clientThread = null;
	private boolean isReserved = false;

	public void initialize(String client, ClientConnectionController controller) {
        this.clientInfo = client;
        this.clientThread= controller;
        for(int i = 10; i <= 18; i++) {
            this.addTime(i);
        }
        this.bookButton.setOnMouseClicked(ae -> {
            try {
                shareButtonOnClickEvent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
	
	private void shareButtonOnClickEvent() throws IOException {
        if(this.time == null) {
            Label selectedLabel = this.calendarList.getSelectionModel().getSelectedItem();
            if(selectedLabel != null) {
                String selectedTime = selectedLabel.getText();
                if(selectedTime.contains("]:")) {
                    Alert alert = new Alert(AlertType.ERROR, "Hairdresser has already a client");
                    alert.show();
                }
                else {
                    selectedTime = selectedTime.replace(("["), "").replace("]", "").trim();
                    this.clientThread.makeAnAppointment(this.clientInfo, selectedTime);
                    this.bookButton.setText("Cancel");
                    this.time = selectedTime;
                }
            }
            else {
                Alert alert = new Alert(AlertType.ERROR, "Before booking, choose when!!!");
                alert.show();
            }
        } else {
            this.bookButton.setText("Book");
            this.clientThread.cancelAppointment(this.clientInfo, this.time);
            this.time = null;
        }
    }
	
	public void addUser(String client, String time) {
		ObservableList<Label> userList = this.calendarList.getItems();
		for(Label l : userList) {
			if(l.getText().contains(time)) {
				l.setText(String.format("%s: %s", l.getText(),client));
			}
		}
	}
	
	public void deleteUser(String client,String time) {
		ObservableList<Label> userList = this.calendarList.getItems();
		for(Label l : userList) {
			if(time != null) {
				if(l.getText().contains(String.format("[%s]: %s", time,client))) {
					l.setText(String.format("[%s]", time));
				}
			}
			else {
				if(l.getText().contains(client)) {
					l.setText(l.getText().replace(String.format(": %s", client), "").trim());
				}
			}
		}
	}
	
	public void addTime(int time) {
		Label l = new Label(String.format("[%d:00]", time));
		this.calendarList.getItems().add(l);
	}
	
}
