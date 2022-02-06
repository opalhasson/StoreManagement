package View;

public class Product {
	private String nameOfProduct;
	private String barcode;
	private int storePrice;
	private int customerPrice;
	private Client client;
	
	
	
	public Product(String barcode, int storePrice, int customerPrice, Client client,String name) {
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
	
	
}
