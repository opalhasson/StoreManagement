package observer;

/**
 * 
 * 
 **/

//Observer
public interface Receiver {
	String receiveMSG(Sender s, Message msg);
	String getClientName();
}
