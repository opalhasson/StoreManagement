package Model;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Product {
	private String nameOfProduct;
	private String barcode;
	private int storePrice;
	private int customerPrice;
	private Client client;

	public Product(String barcode, int storePrice, int customerPrice, Client client, String name) {
		setBarcode(barcode);
		setCustomerPrice(customerPrice);
		setStorePrice(storePrice);
		setClient(client);
		setNameOfProduct(name);
	}

	public void setStorePrice(int storePrice) {
		this.storePrice = storePrice;
	}

	public void setCustomerPrice(int customerPrice) {
		this.customerPrice = customerPrice;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getCustomerPrice() {
		return customerPrice;
	}

	public String getBarcode() {
		return barcode;
	}

	public int getStorePrice() {
		return storePrice;
	}

	public Client getClient() {
		return client;
	}

	public String getNameOfProduct() {
		return nameOfProduct;
	}

	public void setNameOfProduct(String nameOfProduct) {
		this.nameOfProduct = nameOfProduct;
	}

	public void writeProductToFile(RandomAccessFile output) throws IOException {
		output.writeUTF(nameOfProduct);
		output.writeInt(storePrice);
		output.writeInt(customerPrice);
		client.writeClientToFile(output);
	}
	
	@Override
	public String toString() {
		return "Product: " + nameOfProduct + ", barcode: " + barcode + ", storePrice:" + storePrice
				+ ", customerPrice:" + customerPrice + client.toString()+"\n";
	}

}
