package Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.Map.Entry;
import java.util.TreeMap;
import observer.Message;
import observer.Receiver;
import observer.Sender;

public class Model implements Serializable,Sender {

	private static final long serialVersionUID = 1L;
	private static Model theModel = new Model(); 
	public static final String FILE_NAME = "Products.txt"; 
	private Map<String, Product> allProducts;
	private Memento last_state;
	private RandomAccessFile raf;
	private String sortType;
	private long currentPosition;
	private String barcodeToRemove;
	
	public Model() {
		try {
			raf = new RandomAccessFile(FILE_NAME, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Model getInstance() { 
		return theModel;
	}
	
	public void initMap(String choice) throws FileNotFoundException {
		this.sortType=choice;
		switch (choice) {
		case "ASC":
			allProducts = new TreeMap<String, Product>(new SortByBarcodeASC());
			break;
		case "DESC":
			allProducts = new TreeMap<String, Product>(new SortByBarcodeDESC());
			break;
		case "ORDER":
			allProducts = new LinkedHashMap<String, Product>();
			break;
		}
	}

	public void addProductToMap(Product product) throws FileNotFoundException, IOException {
		if(allProducts.get(product.getBarcode()) != null) 
			allProducts.remove(product.getBarcode());
		allProducts.put(product.getBarcode(), product);
		try {
			save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save() throws IOException {
		raf.setLength(0);
		raf.writeUTF(sortType);
		currentPosition = raf.getFilePointer();
		for (Entry<String,Product> object : allProducts.entrySet()) {
			raf.writeUTF(object.getKey());
			object.getValue().writeProductToFile(raf);
		} 
	}
	
	public int getMapFromFile() throws IOException {
		raf.seek(0);
		if(raf.length() == 0)
			return 1;
		else {
			String type = raf.readUTF();
			currentPosition = raf.getFilePointer();
			initMap(type);
			Iterator<Entry<String, Product>> iter = iterator();
			while(iter.hasNext()) {
				Entry<String, Product> en = iter.next();
				allProducts.put(en.getKey(), en.getValue());
			}	
			return 2;
		}	
	}
	
	public Iterator<Entry<String, Product>> iterator() throws IOException {
		Iterator<Entry<String, Product>> Iterator = new Iterator<Entry<String, Product>>() {
			private long prevPointer;
			private long nextPointer;
			private long currentPointer;

			@Override
			public boolean hasNext() {
				try {
					if(raf.getFilePointer() < raf.length()) 
						return true;
				} catch (IOException e) {
					e.printStackTrace();					
				}
				return false;
			}

			@Override
			public Entry<String, Product> next() {
				try {
					prevPointer = raf.getFilePointer();
					Entry<String, Product> entry = readProductFromFile();
					nextPointer = raf.getFilePointer();
					return entry;
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			public void remove(){
				try {
					boolean found =false;
					raf.seek(currentPosition);
					while(hasNext() && !found) {
						Entry<String, Product> en = this.next();
						if(en.getKey().equals(barcodeToRemove)) 
							found = true;				
					}
					this.currentPointer = prevPointer;
					while(hasNext()) {
						Entry<String, Product> en = this.next();
						raf.seek(currentPointer);
						raf.writeUTF(en.getKey());
						en.getValue().writeProductToFile(raf);
						this.currentPointer = raf.getFilePointer();
						raf.seek(nextPointer);
					}
					raf.setLength(currentPointer);
					getMapFromFile();	
				} catch (IOException e) {
					e.printStackTrace();
				}  	
			}
		};
		return Iterator;
	}
	
	public void removeProduct(String barcode) throws IOException {
		Iterator<Entry<String, Product>> iter = iterator();
		this.barcodeToRemove=barcode;
		iter.remove();
	}

	public void removeAll() throws IOException {
		Iterator<Entry<String, Product>> iter = iterator();
		for (Entry<String, Product> object : allProducts.entrySet()) {
			barcodeToRemove=object.getKey();
			iter.remove();		
		}
	}
	
	public Entry<String, Product> readProductFromFile() throws IOException {
		String code = raf.readUTF();
		String prodName = raf.readUTF();
		int priceToStore = raf.readInt();
		int priceToCustomer = raf.readInt();
		String custName = raf.readUTF();
		String number = raf.readUTF();
		boolean isSales = raf.readBoolean();
		Client c = new Client(custName, number, isSales);
		Product p = new Product(code, priceToStore, priceToCustomer, c ,prodName);
		return new AbstractMap.SimpleEntry<String, Product>(code,p);
	}

	public Map<String, Product> getMap(){
		return allProducts;
	}

	
	// -----------------------MOMENTO - CLASS-------------------------//

	public class Memento {

		private SortedMap<String, Product> products;

		public Memento(SortedMap<String, Product> products) {

			this.products = products;
		}
	}

	public Memento createMemento() {
		return new Memento(new TreeMap<String, Product>(allProducts));
	}

	public Memento getLast_state() {
		return last_state;
	}

	public void setLast_state(Memento last_state) {
		this.last_state = last_state;
	}
	
	public void setProducts(Memento mem) {
		this.allProducts = mem.products;
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Product findProduct(String barcode) {
		for (Entry<String, Product> object : allProducts.entrySet()) {
			if(object.getKey().equals(barcode))
				return object.getValue();
		}
		return null;
	}

	public int getProductProfit(Product p) {
		return p.getCustomerPrice()-p.getStorePrice();
	}
	
	public List<String> getSalesUpdateClients(){
		List<String> lst= new ArrayList<>();
		for (Entry<String, Product> object : allProducts.entrySet()) {
			if(getWantToReceive(object.getValue().getClient())) {
				lst.add(object.getValue().getClient().getClientName()+"\n");
			}
		}
		return lst;
	}
	
	@Override
	public String sendMSG(Receiver r, Message msg) {
		return r.receiveMSG(this, msg);
	}

	@Override
	public boolean getWantToReceive(Client c) {
		return c.isWantedToReceiveMsg();
	}

}
