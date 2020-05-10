
import java.util.*;
import java.lang.*;
import java.io.*;
public class Product implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private String id;
  private double price;
  private double quantity;
  private static final String PRODUCT_STRING = "P";
  private List manufacturers = new LinkedList();
  private List suppliers = new LinkedList();
  private List orderSs = new LinkedList();
  private double value;
  public Product(){}
  public Product(String name, double price,
                  double quantity) {
    this.name = name;
    this.price = price;
    this.quantity = quantity;
	id = PRODUCT_STRING + (ProductIdServer.instance()).getId();
  }
  public Product(Product p) {
    name = p.name;
    price = p.price;
    quantity = p.quantity;
	id = p.id;
  }
  public void listOutstandingOrders(){
    for(Iterator iter = getOrderSs();iter.hasNext();){
	  OrderS order = (OrderS)iter.next();
	  if(!order.getProcessed())
     	  order.showOrder();
	}
  }
  public void listManufacturerPrices(){
    for(Iterator iter = manufacturers.iterator();iter.hasNext();){
	  Supplier supplier = (Supplier)iter.next();
	  System.out.println("Manufacturer ID: "+supplier.getManufacturer().getId()
	                     +" Manufacturer Name: "+supplier.getManufacturer().getName()
						 +" Price: "+supplier.getPrice());
    }
  }
  public void listSuppliers(){
    for(Iterator iter = suppliers.iterator();iter.hasNext();){
	  Supplier supplier = (Supplier)iter.next();
	  System.out.println("Manufacturer ID: "+supplier.getManufacturer().getId()
	                     +" Manufacturer Name: "+supplier.getManufacturer().getName()
						 +" Price: "+supplier.getPrice());
    }
  }
  public void addManufacturerToProduct(Manufacturer manufacturer, double price) {
    Supplier supplier = new Supplier(manufacturer,price);
	manufacturers.add(supplier);
  }
  public void addSupplierToProduct(String manufacturerID) {
    Supplier supplier = getSupplierFromManufacturers(manufacturerID);
	suppliers.add(supplier);
  }
  public void deleteSupplierFromProduct(String manufacturerID){
    Supplier supplier = getSupplierFromSuppliers(manufacturerID);
	suppliers.remove(supplier);
  }
  public Supplier getSupplierFromSuppliers(String manufacturerID){
    Supplier supplier=null;
	for(Iterator iter=suppliers.iterator();iter.hasNext();){
	  supplier = (Supplier)iter.next();
	  if(supplier.getManufacturer().equals(manufacturerID))
	    break;
	}
	return supplier;
  }
  public Supplier getSupplierFromManufacturers(String manufacturerID){
    Supplier supplier=null;
	for(Iterator iter=manufacturers.iterator();iter.hasNext();){
	  supplier = (Supplier)iter.next();
	  if(supplier.getManufacturer().equals(manufacturerID))
	    break;
	}
	return supplier;
  }
  public void insertOrderS(OrderS orderS){
    orderSs.add(orderS);
  }
  public Iterator getSuppliers(){
    Iterator iter = suppliers.iterator();
	return iter;
  }
  public Iterator getOrderSs(){
    Iterator iter = orderSs.iterator();
	return iter;
  }
  public Iterator getManufacturers(){
    Iterator iter = manufacturers.iterator();
	return iter;
  }
  public String getName() {
    return name;
  }
  public String getId() {
    return id;
  }
  public double getPrice() {
    return price;
  }
  public double getQuantity(){
    return quantity;
  }
  public void setQuantity(double number){
    quantity = number;
  }
  public void setPrice(double price){
    this.price = price;
  }
  public void updateQuantity(double value){
    quantity += value;
  }
  public double getValue(){
	value = price * quantity;
	return value;
  }
  public boolean equals(String id) {//System.out.println(this.id + " -> " + id);
    return this.id.equals(id);
  }
  public String toString() {
      return "Product name " + name + " productID " + id + " price " + price +" quantity " +quantity;
  }
   class Supplier implements Serializable {
  private static final long serialVersionUID = 1L;
    private Manufacturer manufacturer;
	private double price;
	public Supplier(){}
	public Supplier(Manufacturer manufacturer, double price){
	   this.manufacturer = manufacturer;
	   this.price = price;
	}
	public Manufacturer getManufacturer(){
	  return manufacturer;
	}
	public double getPrice(){
	  return price;
	}
	public void setManufacture(Manufacturer m){
	  manufacturer = m;
	}
	public void setPrice(double p){
	  price = p;
	}
  }
}