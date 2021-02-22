package ShardedData;

//INFORMACJE DOTYCZ¥CE KLIENTA I TYPU JEGO WIADOMOŒCI

public class BookingInformation{
	private String fullName = null;
	private String time = null;
	private String messageType = null;
	private boolean isSuccess = false;
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public void setSuccess(boolean value) {
		this.isSuccess = value;
	}
	
	public String getFullName() {return fullName;};
	public String getTime() {return time;};
	public String getMessageType() {return messageType;};
	public boolean getSuccess(){return isSuccess;}
}