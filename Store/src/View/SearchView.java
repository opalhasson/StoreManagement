package View;

import java.io.IOException;

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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SearchView implements Command{
	private Button search;
	private Button backTostore;
	private Button deleteProduct;
	private Stage Stage;
	private TextField tx;
	
	public SearchView() {
		search = new Button("Search Product");
		backTostore = new Button("Back to Store");
		deleteProduct = new Button("Delete Product");
	}
	
	public void search(Stage stage) {
		this.Stage = stage;

		Text title = new Text("Search Product");
		title.setFont(Font.font("Verdana", 18));

		Text t1 = new Text("Write Barcode : ");
		t1.setFont(Font.font("Verdana", 14));

		
		tx = new TextField();
		HBox hb = new HBox();
		hb.getChildren().addAll(t1, tx);

		HBox hb2 = new HBox();
		hb2.getChildren().addAll(title);
		hb2.setAlignment(Pos.CENTER);
		

		VBox.setMargin(hb, new Insets(0, 0, 40, 0));
		VBox vb = new VBox();
		
		HBox hb3 = new HBox();
		hb3.getChildren().addAll(search,deleteProduct,backTostore);
		HBox.setMargin(t1, new Insets(0, 0, 40, 0));
		
		HBox.setMargin(search, new Insets(0, 30, 0, 0));
		HBox.setMargin(deleteProduct, new Insets(0, 30, 0, 0));
		HBox.setMargin(backTostore, new Insets(0, 0, 0, 0));
		
		deleteProduct.setDisable(true);
		
		VBox.setMargin(t1, new Insets(0, 0, 40, 0));
		VBox.setMargin(hb3, new Insets(0, 0, 30, 30));
		
		vb.getChildren().addAll(hb2, hb, hb3);
		VBox.setMargin(hb2, new Insets(0, 0, 40, 0));
		Scene scene = new Scene(vb, 400, 300);
		Stage.setScene(scene);
		Stage.show();
	}
	
	public Button getDeleteButton() {
		return deleteProduct;
	}
	
	public void addEventToSearch(EventHandler<ActionEvent> event) {
		search.setOnAction(event);
	}
	
	public void addEventToDeleteProduct(EventHandler<ActionEvent> event) {
		deleteProduct.setOnAction(event);
	}
	
	public void addEventTobackToStore(EventHandler<ActionEvent> event) {
		backTostore.setOnAction(event);
	}
	
	public String getBarcode() {
		return tx.getText();
	}

	@Override
	public void performAction(Model theModel, int option) {
		if (option == 0 ) {
			Product p = theModel.findProduct(getBarcode());
			Stage stage=new Stage();
			Text text=new Text();
			VBox vbox=new VBox();
			vbox.getChildren().add(text);
			vbox.setAlignment(Pos.CENTER);
			Scene scene = new Scene(vbox, 600, 200);
			stage.setScene(scene);
			stage.show();
			if(p == null){
				text.setText("Product not found");
				text.setFill(Color.RED);
				deleteProduct.setDisable(true);
			}
			else {
				text.setText(p.toString());
				deleteProduct.setDisable(false);
			}		
		}
		else{
			try {
				theModel.removeProduct(getBarcode());		
				getDeleteButton().setDisable(true);
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
	}

}
