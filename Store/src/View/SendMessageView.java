package View;

import java.util.Map;
import java.util.Map.Entry;

import Command.Command;
import Model.Model;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import observer.Message;
import observer.Receiver;

public class SendMessageView implements Command{
	private Button send;
	private Button backTostore;
	private Stage Stage;
	private TextField tx;
	
	public SendMessageView() {
		send = new Button("Send");
		backTostore = new Button("Back to Store");
	}
	
	public void SendMessage(Stage stage) {
		this.Stage = stage;

		Text title = new Text("Send Sales Message");
		title.setFont(Font.font("Verdana", 18));

		Text t1 = new Text("Write Message :");
		t1.setFont(Font.font("Verdana", 14));

		tx = new TextField();
		HBox hb = new HBox();
		hb.getChildren().addAll(t1, tx);

		HBox hb2 = new HBox();
		hb2.getChildren().addAll(title);
		hb2.setAlignment(Pos.CENTER);
		
		HBox hb3=new HBox();
		hb3.getChildren().addAll(send,backTostore);
		HBox.setMargin(send, new Insets(0,40,0, 0));
		
		VBox.setMargin(t1, new Insets(0, 0, 40, 0));
		VBox.setMargin(hb3, new Insets(0, 0, 30, 30));

		VBox.setMargin(hb, new Insets(0, 0, 40, 0));
		VBox vb = new VBox();
		vb.getChildren().addAll(hb2, hb, hb3);
		VBox.setMargin(hb2, new Insets(0, 0, 40, 0));
		Scene scene = new Scene(vb, 400, 300);
		Stage.setScene(scene);
		Stage.show();
	}
	
	public String getMessage() {
		return tx.getText();
	}
	
	public void printMessage(Map<String,Product> allProducts, Stage stage) {
		VBox vbox=new VBox();
		Text title=new Text("All Messages");
		title.setFont(Font.font("Verdana", 20));
		vbox.getChildren().add(title);
		VBox.setMargin(title, new Insets(0, 0, 20, 0));

		Scene scene = new Scene(vbox, 400, 300);
		stage.setScene(scene);
		stage.show();
	}
		

	public Stage getStage() {
		return Stage;
	}
	
	public void addEventToSend(EventHandler<ActionEvent> event) {
		send.setOnAction(event);
	}
	
	public void addEventToBackToStore(EventHandler<ActionEvent> event) {
		backTostore.setOnAction(event);
	}

	@Override
	public void performAction(Model theModel ,int option) {
		Map<String,Product> allProducts=theModel.getMap();
		Stage stage=new Stage();
		VBox vbox=new VBox();
		Text title=new Text("All Messages");
		title.setFont(Font.font("Verdana", 20));
		vbox.getChildren().add(title);
		VBox.setMargin(title, new Insets(0, 0, 20, 0));

		Scene scene = new Scene(vbox, 400, 300);
		stage.setScene(scene);
		stage.show();

		for (Entry<String, Product> object : allProducts.entrySet()) {
			if(theModel.getWantToReceive(object.getValue().getClient())) {
				Message m=new Message(getMessage());
				Receiver r = object.getValue().getClient();
				Text text=new Text(theModel.sendMSG(r, m));
				vbox.getChildren().add(text);
			}
		}
		
	}

}
