package ServerApp;
//ZMIANY W WYGL¥DZIE GUI I AKTUALIZOWANIE UCZESTNIKÓW NA PODSTAWIE KOMUNIKATÓW
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import ShardedData.ActionController;
import ShardedData.BookingInformation;
import javafx.application.Platform;

public class ServerConnection extends Thread{

	private Socket client = null;
	private String clientInfo = null;
	private ActionController manager = null;
	private CollectClientData data = null;
	private AppController controller = null;
	
	public ServerConnection(Socket socket,AppController controller,CollectClientData data) throws IOException {
		this.client = socket;
		this.controller = controller;
		this.data = data;
		manager = new ActionController(this.client);
	}
	@Override
	public void run() {
		while(true) {
			try {
				BookingInformation infos = this.manager.messageController();
				if(infos != null) {
					if(infos.getMessageType().equals("ADD")) {
						if(infos.getSuccess()) {
							registerNewClient(infos.getFullName(), this.client);
						}
					}
					else if(infos.getMessageType().equals("BOOK")) {
						if(infos.getSuccess()) {
							setBook(infos.getFullName(), infos.getTime());
							this.data.addAppointment(String.format("%s", infos.getFullName()), infos.getTime());
						}
					}
					else if(infos.getMessageType().equals("CANCEL")){
						if(infos.getSuccess()) {
							cancelBook(infos.getFullName(), infos.getTime());
							this.data.cancelAppointment(String.format("%s", infos.getFullName()));
						}
					}
				}
			} catch (IOException e) {
				if(this.client != null) {
					try {
						removeClient(this.clientInfo);
						cancelBook(clientInfo, null);
						this.data.cancelAppointment(clientInfo);
						this.data.removeClient(clientInfo);					
						manager.closeConnection();
						client.close();
						return;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
	
	public void registerNewClient(String clientInfo, Socket socket) throws IOException {
		this.clientInfo = clientInfo;
		this.data.addClient(clientInfo, this.manager);
		HashMap<String,String> allBooks = this.data.getAppointments();
		
		for(String clientKey: allBooks.keySet()) {
			this.manager.orderVisit(clientKey, allBooks.get(clientKey));
		}
		
	}
	
	public void removeClient(String clientInfo) throws IOException {
		this.data.removeClient(clientInfo);
		for(ActionController control : this.data.getActions()) {
			control.cancelVisit(clientInfo, null);
		}
	}
	
	
	public void setBook(String clientInfo,String time) throws IOException {
		Platform.runLater(()->{
			this.controller.addClientToList(clientInfo, time);
		});
		for(ActionController action : this.data.getActions()) {
			action.orderVisit(clientInfo, time);
		}
	}
	
	public void cancelBook(String clientInfo,String time) throws IOException {
		Platform.runLater(()->{
			this.controller.deleteClientFromList(clientInfo, time);
		});
		for(ActionController action : this.data.getActions()) {
			action.cancelVisit(clientInfo, time);
		}
	}
	
	
	
}
