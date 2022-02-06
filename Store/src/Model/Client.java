package Model;

import java.io.IOException;
import java.io.RandomAccessFile;

import observer.Message;
import observer.Receiver;
import observer.Sender;

public class Client implements Receiver{
	
	private String name;
	private String telephoneNumber;
	private boolean isWantedToReceiveMsg;
	
	public Client(String name, String telephoneNumber, boolean isWantedToReceiveMsg) {
		setName(name);
		setTelephoneNumber(telephoneNumber);
		setWantedToReceiveMsg(isWantedToReceiveMsg);
	}
	
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	
	public boolean isWantedToReceiveMsg() {
		return isWantedToReceiveMsg;
	}
	
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setWantedToReceiveMsg(boolean isWantedToReceiveMsg) {
		this.isWantedToReceiveMsg = isWantedToReceiveMsg;
	}
	
	public void writeClientToFile(RandomAccessFile output) throws IOException {
		output.writeUTF(name);
		output.writeUTF(telephoneNumber);
		output.writeBoolean(isWantedToReceiveMsg);	
	}

	@Override
	public String toString() {
		return "\n Client: name:" + name + ", telephone number:" + telephoneNumber + ", isWantedToReceiveMsg:"
				+ isWantedToReceiveMsg;
	}
	
	@Override
	public String getClientName() {
		return name;
	}
	
	@Override
	public String receiveMSG(Sender s, Message msg) {
		String message="\n--------" + getClientName() + "-------\nNew Sales For Your Next Shopping!\n-------------------------"+
		//System.out.println("From:" + s.getSimNumber());
		"\nDate:" + msg.getTime()+"\nMessage:" + msg.getMsg()+"\n-------------------------\n";	
		return message;
	}
}
