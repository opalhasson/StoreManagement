package Model;

import Controller.Controller;
import View.StoreView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) throws Exception {	
		Model theModel = Model.getInstance();
		int option=theModel.getMapFromFile();
		StoreView theView = new StoreView(stage);
		if(option ==2)
			theView.Store();	
		Controller theController = new Controller(theModel, theView);
	}
}
