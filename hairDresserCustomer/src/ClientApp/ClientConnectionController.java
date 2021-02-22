package ClientApp;
//£¥CZENIE SIÊ Z SERWEREM I WYSY£ANIE KOMUNIKATÓW
import java.io.IOException;
import java.net.Socket;

import ShardedData.ActionController;
import ShardedData.BookingInformation;
import ShardedData.ServerSettings;
import javafx.application.Platform;

public class ClientConnectionController extends Thread {
	private Socket clientSocket = null;
	private ActionController controller = null;
	private AppController appController = null;
	
	public ClientConnectionController(AppController controller) throws IOException {
		this.appController = controller;
		this.clientSocket = new Socket(ServerSettings.HOST,ServerSettings.PORT);
		this.controller = new ActionController(this.clientSocket);
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				BookingInformation bookInfo = this.controller.messageController();
				if(bookInfo != null) {
					if(bookInfo.getMessageType().equals("BOOK")) {
						if(bookInfo.getSuccess()) {
							this.setAppointment(bookInfo.getFullName(), bookInfo.getTime());
						}
					}
					else if(bookInfo.getMessageType().equals("CANCEL")) {
						if(bookInfo.getSuccess()) {
							this.deleteAppointment(bookInfo.getFullName(), bookInfo.getTime());
						}
					}
				}
			} catch (IOException e) {
					try {
						if(clientSocket != null) 
							clientSocket.close();
						return;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				e.printStackTrace();
			}
		}
	}
	
	public void makeAnAppointment(String clientInfo,String time) throws IOException {
		this.controller.orderVisit(clientInfo, time);
	}
	
	public void cancelAppointment(String clientInfo,String time) throws IOException {
		this.controller.cancelVisit(clientInfo, time);
	}
	
	public void addClient(String clientInfo) throws IOException {
		this.controller.addUser(clientInfo);
	}

	public void setAppointment(String clientInfo, String time) {
		Platform.runLater(()->{
			this.appController.addUser(clientInfo, time);
		});
	}
	
	public void deleteAppointment(String clientInfo,String time) {
		Platform.runLater(()->{
			this.appController.deleteUser(clientInfo, time);
		});
	}
	
	
}
