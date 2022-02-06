package View;

import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.io.IOException;

import Command.Command;
import Model.Client;
import Model.Model;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AddProductView implements Command{
	private Button AddProduct;
	private Button undo;
	private Button BackToStore;
	private TextField barcode;
	private TextField productName;
	private TextField storePrice;
	private TextField custPrice;
	private TextField nameClient;
	private TextField telephone;
	private RadioButton Sale;
	private Stage stage;
	private VBox vb;
	private Text message;
	
	public AddProductView() {
		AddProduct = new Button("Add Product");
		undo = new Button("Delete the last product");
		BackToStore=new Button("Back To Store");
		message=new Text();
		undo.setDisable(true);
	}

	public void ViewProduct(Stage stage) {
		this.stage = stage;

		Text title = new Text("Store");
		title.setFont(Font.font("Verdana", 26));

		// ---------------------------------------PRODUCT-------------------------------------------------//
		
		barcode = new TextField();
		productName = new TextField();
		storePrice = new TextField();
		custPrice = new TextField();

		vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		HBox hb = new HBox();
		HBox hb1 = new HBox();
		HBox hb2 = new HBox();
		HBox hb3 = new HBox();
		HBox hb4 = new HBox();

		Text t1 = new Text("Barcode");
		Text t2 = new Text("Product Name");
		Text t3 = new Text("Store's Price");
		Text t4 = new Text("Customer's Price");
		Text t5 = new Text("Product");
		t5.setFont(Font.font("Verdana", 20));

		hb.getChildren().add(t5);
		HBox.setMargin(t5, new Insets(0, 0, 20, 0));
		HBox.setMargin(t1, new Insets(0, 20, 0, 5));
		HBox.setMargin(t2, new Insets(0, 20, 0, 5));
		HBox.setMargin(t3, new Insets(0, 20, 0, 5));
		HBox.setMargin(t4, new Insets(0, 20, 0, 5));

		hb1.getChildren().addAll(t1, barcode);
		hb2.getChildren().addAll(t2, productName);
		hb3.getChildren().addAll(t3, storePrice);
		hb4.getChildren().addAll(t4, custPrice);

		VBox.setMargin(hb1, new Insets(0, 0, 20, 0));
		VBox.setMargin(hb2, new Insets(0, 0, 20, 0));
		VBox.setMargin(hb3, new Insets(0, 0, 20, 0));
		VBox.setMargin(hb4, new Insets(0, 0, 20, 0));

		// ---------------------------------------CLIENT-------------------------------------------------//

		nameClient = new TextField();
		telephone = new TextField();

		HBox hBox = new HBox();
		HBox hBox1 = new HBox();
		HBox hBox2 = new HBox();

		Text text = new Text("nameClient");
		Text text1 = new Text("telephone");
		Text text2 = new Text("Client");

		hb.getChildren().add(text2);
		text2.setFont(Font.font("Verdana", 20));

		HBox.setMargin(text2, new Insets(0, 0, 20, 0));
		HBox.setMargin(text, new Insets(0, 20, 0, 5));
		HBox.setMargin(text1, new Insets(0, 20, 0, 5));
		hBox2.getChildren().addAll(text2);
		hBox.getChildren().addAll(text, nameClient);
		hBox1.getChildren().addAll(text1, telephone);
		
		HBox buttons=new HBox();
		buttons.getChildren().addAll(AddProduct, undo,BackToStore);
		buttons.setAlignment(Pos.CENTER);
		HBox.setMargin(AddProduct, new Insets(0, 10, 0, 0));
		HBox.setMargin(undo, new Insets(0, 10, 0, 0));

		Sale = new RadioButton("Is Interest in sales");
		
		vb.getChildren().addAll(title, hb, hb1, hb2, hb3, hb4, hBox2, hBox, hBox1, Sale, buttons);
		vb.getChildren().add(message);
		VBox.setMargin(hBox, new Insets(0, 0, 20, 0));
		VBox.setMargin(hBox1, new Insets(0, 0, 20, 0));
		VBox.setMargin(hBox2, new Insets(0, 0, 20, 0));

		Scene scene = new Scene(vb, 700, 1000);
		stage.setScene(scene);
		stage.show();
	}

	public Stage getStage() {
		return stage;
	}
		
	public Product createProduct() {
		int sPrice=0,cPrice=0;
		String Barcode = barcode.getText();
		if(Barcode.isEmpty()) {
			message("Barcode is a necessary field",Color.RED);
			return null;
		}
		String name = productName.getText();
		if(!isNumeric(storePrice.getText()) || !isNumeric(custPrice.getText()) || !isNumeric(telephone.getText())) {
			message("Invalid Input!",Color.RED);
			return null;
		}
		if(!storePrice.getText().isEmpty()) {
			sPrice = Integer.parseInt(storePrice.getText());
			if(sPrice<0) {
				message("Invalid Input!",Color.RED);
				return null;
			}	
		}
		if(!custPrice.getText().isEmpty()) {
			cPrice = Integer.parseInt(custPrice.getText());
			if(cPrice<0) {
				message("Invalid Input!",Color.RED);
				return null;
			}		
		}
		String telephoneNum = telephone.getText();

		String cname = nameClient.getText();
		boolean saleChoice = Sale.isSelected();
		Client client = new Client(cname, telephoneNum, saleChoice);

		Product product = new Product(Barcode, sPrice, cPrice, client, name);
		message("Product successfully added",Color.GREEN);
		return product;
	}

	public RadioButton getSale() {
		return Sale;
	}
	
	public void clearTextField() {
		barcode.clear();;
		productName.clear();
		storePrice.clear();
		custPrice.clear();
		nameClient.clear();
		telephone.clear();
	}
	
	public void clearAddProduct() {
		clearTextField();
		message.setText("");
		undo.setDisable(true);
	}
	
	
	public Button getUndoButton() {
		return this.undo;
	}
	
	public void message(String m,Color color) {
		message.setText(m);
		message.setFill(color);
	}
	
	public static boolean isNumeric(String str) { 
		if(str.isEmpty())
			return true;
		try {  
			Integer.parseInt(str);  
			return true;
		} catch(NumberFormatException e){  
			return false;  
		}  
	}
	
	public void addEventToAddProductButton(EventHandler<ActionEvent> event) {
		AddProduct.setOnAction(event);
	}
	
	public void addEventToBackToStoreButton(EventHandler<ActionEvent> event) {
		BackToStore.setOnAction(event);
	}
	
	public void addEventToUndoButton(EventHandler<ActionEvent> event) {
		undo.setOnAction(event);
	}

	@Override
	public void performAction(Model theModel , int option) {
		if(option == 0) {
    	theModel.setLast_state(theModel.createMemento()); /////????

		Product product = createProduct();
		if(product != null) {

			try {
				theModel.addProductToMap(product);
			} catch (FileNotFoundException e) {
				e.getMessage();
			} catch (IOException e) {
				e.getMessage();
			}
		}
		clearTextField();
		Sale.setSelected(false);
		undo.setDisable(false);
		}
		else {
			theModel.setProducts(theModel.getLast_state());
			getUndoButton().setDisable(true);
			message("Last product was deleted", Color.GREEN);
		}
	}
}
