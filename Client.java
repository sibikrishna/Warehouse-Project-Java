/**@course CSCI530 Project1 Warehouse
   @Due 2/8/2014
   @authors Zhao Xie, Fatimata Sacko
   @file UserInterface.java**/
import java.util.*;
import java.io.*;
public class Client implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private String address;
  private String phone;
  private String id;
  private double balance;
  private static final String CLIENT_STRING = "C";
  private List transactions = new LinkedList();
  public Client(){
  }
  public  Client (String name, String address, String phone) {
    this.name = name;
    this.address = address;
    this.phone = phone;
	balance = 0.0;
    id = CLIENT_STRING + (MemberIdServer.instance()).getId();
  }
  public double getBalance(){
    return balance;
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
  public boolean updateTransaction(Order order){
    transactions.add(order);
	return true;
  }
  public double updateBalance(double value){
    balance += value;
	return balance;
  }
  public void listTransactions(){
    for(Iterator iter = transactions.iterator();iter.hasNext();){
	  Order order = (Order)iter.next();
	  order.showOrder();
	}
  }
  public boolean equals(String id) {
    return this.id.equals(id);
  }
  public String toString() {
    String string = "Client name " + name + " address " + address + " id " + id + " phone " + phone;
    return string;
  }
}