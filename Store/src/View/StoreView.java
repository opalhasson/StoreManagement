package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Map;
import java.util.Map.Entry;

import Command.Command;
import Model.Model;
import Model.Product;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class StoreView implements Command{
	private Button addProduct;
	private Button searchProduct;
	private Button showProducts;
	private Button sendMessages;
	private Button profits;
	private Button SaleClients;
	private Button deleteAllProducts;
	private Stage stage;
	private ToggleGroup tg;
	private RadioButton rb1;
	private RadioButton rb2;
	private RadioButton rb3;
	private Button store;

	public StoreView(Stage stage) {

		this.stage = stage;
		Text title = new Text("Sort Option");
		title.setFont(Font.font("Verdana", 18));
		tg = new ToggleGroup();

		rb1 = new RadioButton("Sorted By Barcode Ascending");
		rb1.setSelected(true);
		rb1.setToggleGroup(tg);
		rb2 = new RadioButton("Sorted By Barcode Descending");
		rb2.setToggleGroup(tg);
		rb3 = new RadioButton("Sorted Barcode By Order");
		rb3.setToggleGroup(tg);
		
		store=new Button("Let's Go To The Shop");
		addProduct = new Button("Add Product");
		searchProduct = new Button("Search Product");
		showProducts=new Button("Show The Inventory");
		sendMessages=new Button("Send Sales Message");
		profits=new Button("Profits"); 
		SaleClients= new Button("Client Who Like To Get Updates");
		deleteAllProducts = new Button("Delete All Products");
		
		VBox vb = new VBox();
		vb.getChildren().addAll(title, rb1, rb2, rb3,store);

		Scene scene = new Scene(vb, 400, 300);
		stage.setScene(scene);
		stage.show();

	}
		
	public String getChoice(){
		if(rb1.isSelected())
			return "ASC";
		if(rb2.isSelected())
			return "DESC";
		return "ORDER";	
	}
	
	public void Store() {    
		Text title = new Text("Shop Inventory");
		title.setFont(Font.font("Verdana", 26));

		Text t1 = new Text("Choose Option");
		t1.setFont(Font.font("Verdana", 20));

		HBox hb = new HBox();
		hb.getChildren().addAll(title);

		VBox.setMargin(t1, new Insets(0, 0, 40, 0));
		VBox.setMargin(addProduct, new Insets(0, 0, 30, 30));
		VBox.setMargin(searchProduct, new Insets(0, 0, 30, 30));
		VBox.setMargin(showProducts, new Insets(0, 0, 30, 30));
		VBox.setMargin(sendMessages, new Insets(0, 0, 30, 30));
		VBox.setMargin(profits, new Insets(0, 0, 30, 30));
		VBox.setMargin(SaleClients, new Insets(0, 0, 30, 30));
		VBox.setMargin(deleteAllProducts, new Insets(0, 0, 30, 30));

		hb.setAlignment(Pos.CENTER);

		VBox.setMargin(hb, new Insets(0, 0, 40, 0));
		VBox vb = new VBox();
		vb.getChildren().addAll(hb, t1, addProduct, searchProduct,showProducts,sendMessages,profits,SaleClients,deleteAllProducts);
		VBox.setMargin(vb, new Insets(0, 0, 40, 0));
		Scene scene = new Scene(vb, 500, 550);
		stage.setScene(scene);
		stage.show();
	}

	public Stage getStage() {
		return stage;
	}
	
	public boolean getStoreButton() {
		return store.isPressed();
	}

	public void addChangeListenerStore(ChangeListener<Boolean> listener) {
		store.pressedProperty().addListener(listener);
	}

	public void addProductButton(EventHandler<ActionEvent> event) {
		addProduct.setOnAction(event);
	}
	
	public void searchProductButton(EventHandler<ActionEvent> event) {
		searchProduct.setOnAction(event);
	}
	
	public void sendMessageButton(EventHandler<ActionEvent> event) {
		sendMessages.setOnAction(event);
	}
	
	public void showInventoryButton(EventHandler<ActionEvent> event) {
		showProducts.setOnAction(event);
	}
	
	public void showProfitsButton(EventHandler<ActionEvent> event) {
		profits.setOnAction(event);
	}
	
	public void SaleClientsButton(EventHandler<ActionEvent> event) {
		SaleClients.setOnAction(event);
	}
	
	public void deleteAllProductsButton(EventHandler<ActionEvent> event) {
		deleteAllProducts.setOnAction(event);
	}

	@Override
	public void performAction(Model theModel, int option) {
		if(option == 0) {
		Map<String,Product> allProducts=theModel.getMap();
		Stage stage=new Stage();
		VBox vbox=new VBox();
		Text title=new Text("Store Inventory");
		title.setFont(Font.font("Verdana", 20));
		vbox.getChildren().add(title);
		VBox.setMargin(title, new Insets(0, 0, 20, 0));
		Scene scene = new Scene(vbox, 460, 300);
		stage.setScene(scene);
		stage.show();

		for (Entry<String, Product> object : allProducts.entrySet()) {
			Text text=new Text(object.getValue().toString());
			vbox.getChildren().add(text);
		}
		}
		else {
			int totalProf=0;
			Map<String,Product> allProducts=theModel.getMap();
			Stage stage=new Stage();
			VBox vbox=new VBox();
			Text title=new Text("Store Profit");
			title.setFont(Font.font("Verdana", 26));
			Text eachProduct=new Text("Products Profit");
			eachProduct.setFont(Font.font("Verdana", 20));
			vbox.getChildren().addAll(title,eachProduct);
			VBox.setMargin(title, new Insets(0, 0, 20, 0));
			VBox.setMargin(eachProduct, new Insets(10, 0, 20, 0));
			Scene scene = new Scene(vbox, 300, 300);
			stage.setScene(scene);
			stage.show();

			for (Entry<String, Product> object : allProducts.entrySet()) {
				int prof=theModel.getProductProfit(object.getValue());
				totalProf=totalProf+prof;
				Text text=new Text(object.getValue().getNameOfProduct()+" : "+prof);
				vbox.getChildren().add(text);
			}
			
			Text totProf=new Text("Total Profit");
			Text Prof=new Text("The total profit of the store is: "+totalProf);
			totProf.setFont(Font.font("Verdana", 20));
			vbox.getChildren().addAll(totProf,Prof);
			VBox.setMargin(totProf, new Insets(30, 0, 20, 0));
		}
		
	}
	
}
