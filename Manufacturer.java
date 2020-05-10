/**@course CSCI530 Project1 Warehouse
   @Due 2/8/2014
   @authors Zhao Xie, Fatimata Sacko
   @file UserInterface.java**/
import java.util.*;
import java.io.*;
public class Manufacturer implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private String id;
  private String address;
  private String phone;
  private List products = new LinkedList();
  private List transactions = new LinkedList();
  private static final String MANUFACTURER_STRING = "M";
  public Manufacturer(){}
  public  Manufacturer (String name,String address,String phone) {
    this.name = name;
    this.address = address;
    this.phone = phone;
	id = MANUFACTURER_STRING + (ManufacturerIdServer.instance()).getId();
  }
  public void addProductToManufacturer(Product product,double price){
    Product product1 = new Product(product);
	product1.setPrice(price);
	products.add(product1);
  }
  public Iterator getProducts(){
    return products.iterator();
  }
  public void insertOrderS(OrderS orderS){
    transactions.add(orderS);
  }
  public String getName() {
    return name;
  }
  public String getPhone() {
    return phone;
  }
  public String getAddress() {
    return address;
  }
  public String getId() {
    return id;
  }
  public void setName(String newName) {
    name = newName;
  }
  public void setAddress(String newAddress) {
    address = newAddress;
  }
  public void setPhone(String newPhone) {
    phone = newPhone;
  }
  public boolean equals(String id) {
    return this.id.equals(id);
  }
  public String toString() {
    String string = "Manufacturer name " + name + " id " + id+ " address " + address  + " phone " + phone;
    return string;
  }
}
