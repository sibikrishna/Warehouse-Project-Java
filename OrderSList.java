import java.util.*;
import java.io.*;
public class OrderSList implements Serializable {
  private static final long serialVersionUID = 1L;
  private List orderSs = new LinkedList();
  private static OrderSList orderSList;
  private OrderSList() {
  }
  public static OrderSList instance() {
    if (orderSList == null) {
      return (orderSList = new OrderSList());
    } else {
      return orderSList;
    }
  }

  public boolean insertOrderS(OrderS order) {
    orderSs.add(order);
    return true;
  }

  public Iterator getOrderSs(){
     return orderSs.iterator();
  }
  
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(orderSList);
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (orderSList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (orderSList == null) {
          orderSList = (OrderSList) input.readObject();
        } else {
          input.readObject();
        }
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
    }
  }
  public String toString() {
    return orderSs.toString();
  }
}