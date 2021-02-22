package ServerApp;
//£¥CZENIE SIÊ Z U¯YTKOWNIKAMI
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import ShardedData.ServerSettings;

public class AcceptConnectionRequests extends Thread{

	private AppController controller = null;
	private CollectClientData collector = null;
	
	public AcceptConnectionRequests(AppController controller) {
		this.controller = controller;
		this.collector = new CollectClientData();
	}
	
	
	@Override
	public void run() {
		ServerSocket server = null;
		Socket clientSocket = null;
		try {
			server = new ServerSocket(ServerSettings.PORT);
			while(true) {
				clientSocket = server.accept();
				ServerConnection serverThread = new ServerConnection(clientSocket,controller,collector);
				serverThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
