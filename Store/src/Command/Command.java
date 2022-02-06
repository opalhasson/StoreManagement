package Command;

import Model.Model;

public interface Command {
	
	public void performAction(Model theModel, int option);

}
