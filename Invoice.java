/**@course CSCI530 Project1 Warehouse
   @Due 2/17/2014
   @authors Zhao Xie, Fatimata Sacko
   @file Invoice.java**/
import java.util.*;
import java.io.*;
import java.lang.*;
public class Invoice implements Serializable {
  private static final long serialVersionUID = 1L;
  private String id;
  private Date date;
  private List products = new LinkedList();
  /*private String shipmentCompany;
  private Date shipmentDate;
  private String shipmentID;*/
  private double totalCost;
  private static final String INVOICE_STRING = "I";
  public Invoice(){date = new Date();}
  public Invoice(Order order){
    totalCost = 0.0;
	date = new Date();
    id = INVOICE_STRING + order.getId();
	//shipmentID = "";shipmentCompany = "";
  }
  public Invoice(Waiting waiting){
    totalCost = 0.0;
	date = new Date();
    id = INVOICE_STRING + waiting.getId();
	//shipmentID = "";shipmentCompany = "";
  }
  public Date getDate() {
    return date;
  }
  /*public String getShipmentID() {
    return shipmentID;
  }
  public Date getShipmentDate() {
    return shipmentDate;
  }
  public String getShipmentCompany() {
    return shipmentCompany;
  }
  public void setShipmentID(String newShipmentID){
    shipmentID = newShipmentID;
  }
  public void setShipmentCompany(String newShipmentCompany){
    shipmentCompany = newShipmentCompany;
  }
  public void setShipmentDate(){
    shipmentDate = new Date();
  }*/
  public int getSizeProducts(){
    return products.size();
  }
  public String getId() {
    return id;
  }
  public double getTotalCost() {
    totalCost = 0.0;
	for(Iterator iterator1 = products.iterator();iterator1.hasNext();){
	   Product p = (Product)iterator1.next();
	   totalCost += p.getValue();
	}
	return totalCost;
  }
  public Iterator getProducts(){
    return products.iterator();
  }
  public boolean equals(String id) {
    return this.id.equals(id);
  }
  public String toString() {
    String string = "Invoice ID: " + id + "\tInvoice Date: " + date+ "\nTotalCost: "+getTotalCost();
	               /*+ "\nShipmentID:" + shipmentID +"\tShipmentCompany: "+shipmentCompany
				   +"\tShipmentDate: "+(shipmentDate==null?"":shipmentDate) */
    return string;
  }
  public void insertProduct(Product product){
    Product p1 = new Product(product);
    products.add(p1);
  }
  public void removeProduct(String productID){
    for(Iterator iterator1 = products.iterator();iterator1.hasNext();){
	  Product p = (Product)iterator1.next();
	  if(p.equals(productID))
	  iterator1.remove();
	  break;
	}
  }
 public void showInvoice(){
   System.out.println(this);
   System.out.println("The product list of the Order are:");
   for(int i=0;i<products.size();i++)
     System.out.println(products.get(i));
   System.out.println("");
 } 
}
