/**@course CSCI530 Project1 Warehouse
   @Due 2/17/2014
   @authors Zhao Xie, Fatimata Sacko
   @file Order.java**/
import java.util.*;
import java.io.*;
import java.lang.*;
public class Order implements Serializable {
  private static final long serialVersionUID = 1L;
   String id;
   Date date;
   boolean processed;
   List products = new LinkedList();
   double totalCost;
  public Order(){
    totalCost = 0.0;
	processed = false;
  }
  public void setProcessed(){
    processed = true;
  }
  public boolean getProcessed(){
    return processed;
  }
  public Date getDate() {
    return date;
  }
  public String getId() {
    return id;
  }
  public Iterator getProducts(){
    return products.iterator();
  }
  public double getTotalCost() {
	totalCost = 0.0;
	for(Iterator iterator1 = products.iterator();iterator1.hasNext();){
	   Product product = (Product)iterator1.next();
	   totalCost += product.getValue();
	}
	return totalCost;
  }
  public boolean equals(String id) {
    return this.id.equals(id);
  }
  public void insertProduct(Product product){
    Product p1 = new Product();
	p1 = product;
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
  public String toString() {
    String string1 = "Order ID: " + id + ";\tOrder Date: " + date + ";\t Processed: "+(processed?"YES":"NO")
	                +"\nTotalCost: "+getTotalCost();
    return string1;
  }
 public void showOrder(){
   System.out.println(this);
   System.out.println("The product list of the Order are:");
   for(int i=0;i<products.size();i++)
     System.out.println(products.get(i));
   System.out.println("");
 } 
}