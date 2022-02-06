package observer;

import Model.Client;

/**
 * 
 * 
 **/

//Observable
public interface Sender {
	boolean getWantToReceive(Client c);
	String sendMSG(Receiver r, Message msg);
}
