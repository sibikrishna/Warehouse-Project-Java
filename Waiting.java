
import java.util.*;
import java.io.*;
import java.lang.*;
public class Waiting implements Serializable {
  private static final long serialVersionUID = 1L;
  private String id;
  private Date date;
  private List products = new LinkedList();
  private double totalCost;
  private static final String WAITING_STRING = "W";
  public Waiting(){}
  public Waiting(OrderC order){
    date = new Date();
	totalCost = 0.0;
    id = WAITING_STRING + order.getId();
  }
  public Iterator getProducts(){
     return products.iterator();
  }
  public Date getDate() {
    return date;
  }
  public int getSizeProducts(){
    return products.size();
  }
  public String getId() {
    return id;
  }
  public double getTotalCost() {
    totalCost = 0.0;
	for(Iterator iterator1 = products.iterator();iterator1.hasNext();){
	   Product product = (Product)iterator1.next();
	   double value1 = product.getValue();
	   totalCost += value1;
	}
	return totalCost;
  }
  public boolean equals(String id) {
    return this.id.equals(id);
  }
  public String toString() {
    String string = "Waiting ID: " + this.id + "\tWaiting Date: " + this.date + "\nTotalCost: "+this.getTotalCost();
    return string;
  }
  public void insertProduct(Product product){
    Product p1 = new Product(product);
    products.add(p1);
  }
  /*public void removeProduct(String productID){
    for(Iterator iterator1 = products.iterator();iterator1.hasNext();){
	  Product p = (Product)iterator1.next();
	  if(p.equals(productID))
	  iterator1.remove();
	  break;
	}
  }*/
  public void removeProduct(Product product){
    products.remove(product);
  }
 public void showWaiting(){
   System.out.println(this);
   System.out.println("The product list of the Order are:");
   for(int i=0;i<products.size();i++)
     System.out.println(this.products.get(i));
   System.out.println("");
 } 
}
