package ServerApp;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class AppController {
	@FXML
	private ListView<Label> calendarList;
	private AcceptConnectionRequests request = null;
	
	public AppController() {
		request = new AcceptConnectionRequests(this);
	}
	
	public void initialize() {
		this.request.start();
		for(int i = 10; i <= 18; i++) {
			addTime(i);
		}
	}

    public void addTime(int time) {
        Label logLabel = new Label(String.format("[%d:00]", time));
        this.calendarList.getItems().add(logLabel);
    }
    
    public void addClientToList(String client,String time) {
    	ObservableList<Label> currentList = this.calendarList.getItems();
    	for(Label l : currentList) {
    		if(l.getText().contains(time))
    		l.setText(String.format("%s: %s", l.getText(),client));
    	}	
    }
    
    public void deleteClientFromList(String client,String time) {
    	ObservableList<Label> currentList = this.calendarList.getItems();
    	for(Label l : currentList) {
    		if(time != null) {
    			if(l.getText().contains(String.format("[%s]: %s", time,client))) {
    				l.setText(String.format("[%s]", time));
    			}
    		}
    		else {
    			if(l.getText().contains(client)) {
    				String format = String.format(": %s", client);
    				l.setText(l.getText().replace(format, ""));
    			}
    		}
    	}
    }
    
}
