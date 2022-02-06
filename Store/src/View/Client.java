package View;

public class Client {
	private String name;
	private int telephoneNumber;
	private boolean isWantedToReceiveMsg;
	
	
	public Client(String name, int telephoneNumber, boolean isWantedToReceiveMsg) {
		setName(name);
		setTelephoneNumber(telephoneNumber);
		setWantedToReceiveMsg(isWantedToReceiveMsg);
	}
	public String getName() {
		return name;
	}
	
	public int getTelephoneNumber() {
		return telephoneNumber;
	}
	
	public boolean isWantedToReceiveMsg() {
		return isWantedToReceiveMsg;
	}
	
	public void setTelephoneNumber(int telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	

	public void setWantedToReceiveMsg(boolean isWantedToReceiveMsg) {
		this.isWantedToReceiveMsg = isWantedToReceiveMsg;
	}

	
}
