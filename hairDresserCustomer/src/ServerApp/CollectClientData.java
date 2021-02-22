package ServerApp;
//ZBIERANIE INFORMACJI O KLIENTACH I ICH AKCJACH
import java.util.Collection;
import java.util.HashMap;

import ShardedData.ActionController;

public class CollectClientData {
	private HashMap<String, ActionController> sockets = null;
	private String actualTime = null;
	private HashMap<String, String> appointments = null;
	
	public CollectClientData() {
		sockets = new HashMap<String, ActionController>();
		appointments = new HashMap<String,String>();
	}
	
	
	public void addClient(String client, ActionController controller) {
		sockets.put(client, controller);
	}
	public void removeClient(String client) {
		sockets.remove(client);
	}
	
	public void addAppointment(String client,String time) {
		appointments.put(client, time);
	}
	public void cancelAppointment(String client) {
		appointments.remove(client);
	}
	
	public HashMap<String,String> getAppointments(){
		return appointments;
	}
	
	public Collection<ActionController> getActions(){
		return sockets.values();
	}
	
	public String getAcutualTime() {
		return actualTime;
	}
	
	
	
	
}
