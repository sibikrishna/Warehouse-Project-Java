/**@course CSCI530 Project1 Warehouse
   @Due 2/17/2014
   @authors Zhao Xie, Fatimata Sacko
   @file Order.java**/
import java.util.Date;
public class OrderC extends Order{
  private static final long serialVersionUID = 1L;
  private String clientID;
  private static final String ORDER_STRING = "OC";
  public OrderC(){
    super();
	date = new Date();
	clientID = "";
  }
  public  OrderC (String clientID) {
    super();
	this.clientID = clientID;
    date = new Date();
    id = ORDER_STRING + (OrderCIdServer.instance()).getId();
  }
  public String getCientID() {
    return clientID;
  }
  public void setClientID(String newClientID) {
    clientID = newClientID;
  }
  public String toString() {
    String string = super.toString() +"\nClient ID: " + clientID ;
    return string;
  }
}
