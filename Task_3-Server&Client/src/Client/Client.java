package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	private final static String host ="127.0.0.1";
	private final static int port = 8888;
	private Socket socket = null;
	private BufferedReader br = null;
	private PrintWriter pr = null;
	private Scanner scan = null;
	
	public Client() throws UnknownHostException, IOException{
		socket = new Socket(host,port);
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public BufferedReader openBR(Socket s) throws IOException {
		return new BufferedReader(new InputStreamReader(s.getInputStream()));
	}
	
	public PrintWriter openPW(Socket s) throws IOException {
		return new PrintWriter(s.getOutputStream());
	}
	
	public Scanner openScanner() {
		return new Scanner(System.in);
	}
		
	public void closeScanner(Scanner scan) {
		if(scan != null) scan.close();
	}
	
	
	public static void main(String args[]) {
			
		try {
			Client newClient = new Client();
			newClient.clientHandler(newClient.getSocket());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void clientHandler(Socket s) throws IOException{
		
			br = openBR(s);
			pr = openPW(s);
			scan = openScanner();
			String line = "";
			while(true) {
				System.out.println("Enter text:");
				line = scan.nextLine();
				pr.println(line);
				pr.flush();
				if(line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("quit")) {
					break;
				}
				line = br.readLine();
				System.out.println("Server response: " + line);
				
			}
			System.out.println("Client has exited");
			closeScanner(scan);
			closeConnection(br,pr,socket);
	}
	
	public void closeConnection(BufferedReader br, PrintWriter pr, Socket socket) throws IOException {
		if(br != null) {
			br.close();
		}
		if(pr != null) {
			pr.close();
		}
		if(socket != null) {
			socket.close();
		}
	}
	
	
	
}
