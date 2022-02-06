package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import Model.Model;
import Model.Product;
import View.AddProductView;
import View.SalesUpdateClientsView;
import View.SearchView;
import View.SendMessageView;
import View.StoreView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller {
	private Model theModel;
	private StoreView theView;
	private SearchView sView;
	private AddProductView apView;
	private SendMessageView smView;
	
	public Controller(Model m, StoreView v) {
		theModel = m;
		theView = v;
		apView = new AddProductView();
		sView = new SearchView();
		smView=new SendMessageView();

		//store buttons
		ChangeListener<Boolean> storeButton = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (theView.getStoreButton()) {
					try {
						theModel.initMap(theView.getChoice());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					theView.Store();
				}
			}
		};
		theView.addChangeListenerStore(storeButton);

		EventHandler<ActionEvent> addProductButton = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				apView.ViewProduct(theView.getStage());
			}
		};
		theView.addProductButton(addProductButton);

		EventHandler<ActionEvent> searchProductButton = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				sView.search(theView.getStage());
			}
		};
		theView.searchProductButton(searchProductButton);

		EventHandler<ActionEvent> showInventoryButton = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				theView.performAction(theModel, 0);
			}
		};
		theView.showInventoryButton(showInventoryButton);
		
		EventHandler<ActionEvent> sendMessageButton = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				smView.SendMessage(theView.getStage());		
			}
		};
		theView.sendMessageButton(sendMessageButton);
		
		EventHandler<ActionEvent> showProfitsButton = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				theView.performAction(theModel, 1);
			}
		};
		theView.showProfitsButton(showProfitsButton);
		
		EventHandler<ActionEvent> SaleClientsButton = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				SalesUpdateClientsView suView= new SalesUpdateClientsView(new Stage());
				suView.performAction(theModel,0);
				
			}
		};
		theView.SaleClientsButton(SaleClientsButton);
		
		EventHandler<ActionEvent> deleteAllProductsButton = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					theModel.removeAll();
					Alert msg=new Alert(AlertType.WARNING);
					
					msg.setContentText("All products has been deleted");
					msg.show();
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		};
		theView.deleteAllProductsButton(deleteAllProductsButton);
		
		
		//search buttons
		EventHandler<ActionEvent> eventTobackToStore = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				theView.Store();
			}
		};
		sView.addEventTobackToStore(eventTobackToStore);
		
		EventHandler<ActionEvent> addEventToDeleteProduct = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				sView.performAction(theModel, 1);
			}
		};
		sView.addEventToDeleteProduct(addEventToDeleteProduct);

		EventHandler<ActionEvent> searchButtonInWidowSearch = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {	
				sView.performAction(theModel,0);
			}
		};
		sView.addEventToSearch(searchButtonInWidowSearch);
		
		
		//send message buttons
		EventHandler<ActionEvent> addEventToSend = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				smView.performAction(theModel,0);
			}
		};
		smView.addEventToSend(addEventToSend);
	
		EventHandler<ActionEvent> addEventToBackToStore = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				theView.Store();
			}
		};
		smView.addEventToBackToStore(addEventToBackToStore);
		
		
		//add product buttons
		EventHandler<ActionEvent> BackToStoreButton = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				apView.clearAddProduct();
				theView.Store();
			}
		};
		apView.addEventToBackToStoreButton(BackToStoreButton);

		EventHandler<ActionEvent> addEventToAddProductButton = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				apView.performAction(theModel,0);
			}
		};
		apView.addEventToAddProductButton(addEventToAddProductButton);

		EventHandler<ActionEvent> addEventToUndoButton = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				apView.performAction(theModel, 1);
			}
		};
		apView.addEventToUndoButton(addEventToUndoButton);

	}
}