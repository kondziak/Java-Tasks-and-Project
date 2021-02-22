package ShardedData;
//KLASA ZAJMUJE SIÊ WYSY£ANIEM I ODBIERANIEM KOMUNIKATÓW
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ActionController {
	private Socket clientSocket = null;
	private DataInputStream dataReader = null;
	private DataOutputStream dataSender = null;
	
	public ActionController(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		this.dataReader = new DataInputStream(this.clientSocket.getInputStream());
		this.dataSender = new DataOutputStream(this.clientSocket.getOutputStream());
	}
	
	public BookingInformation messageController() throws IOException {
		String receivedMessage = dataReader.readUTF();
		if(receivedMessage.length() > 0) {
			String[] splittedText = receivedMessage.split(" ");
			BookingInformation info = new BookingInformation();
			info.setMessageType(splittedText[0]);
			info.setFullName(String.format("%s %s", splittedText[1],splittedText[2]));
			if(splittedText.length > 3) {
				info.setTime(splittedText[3]);
			}
			info.setSuccess(true);
			return info;
		}
		return null;
	}
	
	public void orderVisit(String clientInfo,String Time) throws IOException {
		this.dataSender.writeUTF(String.format("BOOK %s %s",clientInfo,Time));
	}
	public void cancelVisit(String clientInfo,String Time) throws IOException {
		if(Time != null) {
			this.dataSender.writeUTF(String.format("CANCEL %s %s", clientInfo,Time));
		}
		else {
			this.dataSender.writeUTF(String.format("CANCEL %s", clientInfo));
		}
	}
	
	public void addUser(String clientInfo) throws IOException {
		this.dataSender.writeUTF(String.format("ADD %s", clientInfo));
	}
	
	public void closeConnection() throws IOException {
		dataReader.close();
		dataSender.close();
	}
}

