package View;

import java.util.List;
import Command.Command;
import Model.Model;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SalesUpdateClientsView implements Command{
	private VBox vbox;
	
	public SalesUpdateClientsView(Stage stage) {
		vbox=new VBox();
		Text title=new Text("Names of clients who are \ninterested in updates :");
		title.setFont(Font.font("Verdana", 18));
		vbox.getChildren().add(title);
		VBox.setMargin(title, new Insets(0, 0, 20, 0));

		Scene scene = new Scene(vbox, 300, 300);
		stage.setScene(scene);
		stage.show();
	}

	public void printClient(String client) {
		Text text=new Text(client);
		vbox.getChildren().add(text);
	}
	
	@Override
	public void performAction(Model theModel, int option) {
		new Thread(() -> {  
			List<String> arr = theModel.getSalesUpdateClients();
			for (int i = 0; i < arr.size(); i++) {
				String name = arr.get(i);
				Platform.runLater(() -> {
					printClient(name);
				});
				try {
					Thread.sleep(2000);
				} catch (Exception e1) {
				}
			}
		}).start();
	}

	

}
