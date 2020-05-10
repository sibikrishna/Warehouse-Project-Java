
//record the order from Suppliers
import java.util.*;
import java.io.*;
import java.lang.*;
public class OrderS extends Order{
  private static final long serialVersionUID = 1L;
  private String manufacturerName;
  private String productID;
  private double quantity;
  public OrderS(){
  }
  public  OrderS (String newId, String manufacturerName,String productID,double quantity) {
	this.manufacturerName = manufacturerName;
	this.productID = productID;
	this.quantity = quantity;
	processed = false;
    date = new Date();
    id = newId;
  }
  public String getManufacturerName() {
    return manufacturerName;
  }
  public void setManufacturerName(String newManufacturerName) {
    manufacturerName = newManufacturerName;
  }
  public String toString() {
    String string = super.toString() +"\nManufacturer Name: " + manufacturerName
	                +" Product ID: "+productID+" Quantity: "+quantity;
    return string;
  }
}
