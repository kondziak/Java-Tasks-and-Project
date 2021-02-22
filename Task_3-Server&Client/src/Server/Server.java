 package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class Server {
	private ServerSocket server = null;
	
	public static void main(String args[]) {
		try {
			Server newServer = new Server();
			newServer.startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startServer() throws IOException {
		server = new ServerSocket(8888);
		while(true) {
			Socket client = server.accept();
			ClientHandler handler = new ClientHandler(client);
			handler.start();
		}
	}
	
}


class ClientHandler extends Thread
{
	private Socket ClientSocket = null;
	private String[] outputText = {"Hello","What's up","How are you?", "How old are you?", "Do you like pears?",
			"What's in your mind?","Are you going to the cinema later?","I like chocolate"};
	private BufferedReader in = null;
	private PrintWriter out = null;
	
	public ClientHandler(Socket socket) {
		ClientSocket = socket;
	}
	
	public BufferedReader openBR(Socket s) throws IOException {
		return new BufferedReader(new InputStreamReader(s.getInputStream()));
	}
	
	public PrintWriter openPW(Socket s) throws IOException {
		return new PrintWriter(s.getOutputStream());
	}
	
	@Override
	public void run() {
			try {
				in = openBR(ClientSocket);
				out = openPW(ClientSocket);
				while(true) {
					String command = in.readLine();
					System.out.println("Client says: " + command);
					if(command.equalsIgnoreCase("exit") || command.equalsIgnoreCase("quit")) {
						break;
					}
					Random index = new Random();
					command = outputText[index.nextInt(outputText.length)];
					out.println(command);
					out.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					closeConnection(in,out,ClientSocket);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
	
	public void closeConnection(BufferedReader br, PrintWriter pr, Socket socket) throws IOException {
		if(in != null) {
			in.close();
		}
		if(out != null) {
			out.close();
		}
		if(ClientSocket != null) {
			ClientSocket.close();
		}
	}
}
