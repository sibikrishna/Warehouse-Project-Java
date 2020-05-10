import java.util.*;
import java.io.*;
public class OrderCList implements Serializable {
  private static final long serialVersionUID = 1L;
  private List orderCs = new LinkedList();
  private static OrderCList orderCList;
  private OrderCList() {
  }
  public static OrderCList instance() {
    if (orderCList == null) {
      return (orderCList = new OrderCList());
    } else {
      return orderCList;
    }
  }

  public boolean insertOrderC(OrderC order) {
    orderCs.add(order);
    return true;
  }

  public Iterator getOrderCs(){
     return orderCs.iterator();
  }
  
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(orderCList);
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (orderCList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (orderCList == null) {
          orderCList = (OrderCList) input.readObject();
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
    return orderCs.toString();
  }
}