
   
import java.util.*;
import java.text.*;
import java.io.*;
public class UserInterface{
  private static UserInterface userInterface;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  private static final int EXIT = 0;
  private static final int ADD_CLIENT = 1;
  private static final int ADD_PRODUCTS = 2;
  private static final int ADD_MANUFACTURERS = 3;
  private static final int ADD_SUPPLIER = 4;
  private static final int DELETE_SUPPLIER = 5;
  private static final int ACCEPT_ORDERS = 6;
  private static final int PLACE_ORDERS = 7;
  private static final int ACCEPT_PAYMENTS = 8;
  private static final int PROCESS_ORDERS = 9;
  private static final int ACCEPT_SHIPMENTS = 10;
  private static final int LIST_TRANSACTIONS = 11;
  private static final int LIST_CLIENTS_BALANCE = 12;
  private static final int LIST_MANUFACTURER_ORDERS = 13;
  private static final int LIST_MANUFACTURER_PRICES = 14;
  private static final int LIST_SUPPLIERS = 15;
  private static final int SHOW_CLIENTS = 16;
  private static final int SHOW_PRODUCTS = 17;
  private static final int SHOW_MANUFACTURERS = 18;
  private static final int SHOW_ORDERS_CLIENTS = 19;
  private static final int SHOW_ORDERS_SUPPLIERS = 20;
  private static final int SHOW_INVOICES = 21;
  private static final int SHOW_WAITINGS = 22;
  private static final int SAVE = 23;
  private static final int RETRIEVE = 24;
  private static final int HELP = 25;
  private UserInterface() {
    if (yesOrNo("Look for saved data and  use it?")) {
      retrieve();
    } else {
      warehouse = Warehouse.instance();
    }
  }
  //singleton
  public static UserInterface instance() {
    if (userInterface == null) {
      return userInterface = new UserInterface();
    } else {
      return userInterface;
    }
  }
  //Good little tools
  public String getToken(String prompt) {
    do {
      try {
        System.out.println(prompt);
        String line = reader.readLine();
        StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
        if (tokenizer.hasMoreTokens()) {
          return tokenizer.nextToken();
        }
      } catch (IOException ioe) {
        System.exit(0);
      }
    } while (true);
  }
  private boolean yesOrNo(String prompt) {
    String more = getToken(prompt + " (Y|y)[es] or anything else for no");
    if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
      return false;
    }
    return true;
  }
  public int getNumber(String prompt) {
    do {
      try {
        String item = getToken(prompt);
        Integer num = Integer.valueOf(item);
        return num.intValue();
      } catch (NumberFormatException nfe) {
        System.out.println("Please input a number ");
      }
    } while (true);
  }
  public double getDouble(String prompt) {
    do {
      try {
        String item = getToken(prompt);
        Double num = Double.valueOf(item);
        return num.doubleValue();
      } catch (NumberFormatException nfe) {
        System.out.println("Please input a number ");
      }
    } while (true);
  }
  public Calendar getDate(String prompt) {
    do {
      try {
        Calendar date = new GregorianCalendar();
        String item = getToken(prompt);
        DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
        date.setTime(df.parse(item));
        return date;
      } catch (Exception fe) {
        System.out.println("Please input a date as mm/dd/yy");
      }
    } while (true);
  }
  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
        if (value >= EXIT && value <= HELP) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }
//Menu list
  public void help() {
    System.out.println("Enter a number between 0 and 25 as explained below:");
    System.out.println(EXIT + " to Exit.\n");
    System.out.println(ADD_CLIENT + " to add a client.");
    System.out.println(ADD_PRODUCTS + " to add products.");
    System.out.println(ADD_MANUFACTURERS + " to add manufacturers.");
    System.out.println(ADD_SUPPLIER + " to add a manufacturer suppling a product.");
    System.out.println(DELETE_SUPPLIER + " to delete a manufacturer suppling a product.");
    System.out.println(ACCEPT_ORDERS + " to accept orders.");
    System.out.println(PLACE_ORDERS + " to record orders from supplier.");
    System.out.println(ACCEPT_PAYMENTS + " to accept payments.");
	System.out.println(PROCESS_ORDERS +" to process orders.");
    System.out.println(ACCEPT_SHIPMENTS + " to accept shipments.");
    System.out.println(LIST_TRANSACTIONS + " to list transactions for a client.");
    System.out.println(LIST_CLIENTS_BALANCE + " to list clients with an outstanding balance.");
    System.out.println(LIST_MANUFACTURER_ORDERS + " to list all outstanding manufacturer orders for a given product.");
	System.out.println(LIST_MANUFACTURER_PRICES + " to list manufacturers and their prices for a given product.");
	System.out.println(LIST_SUPPLIERS + " to list suppliers for a given product.");
	System.out.println(SHOW_CLIENTS + " to show clients.");
	System.out.println(SHOW_PRODUCTS + " to show products.");
	System.out.println(SHOW_MANUFACTURERS + " to show manufacturers.");
	System.out.println(SHOW_ORDERS_CLIENTS + " to show orders from clients.");
	System.out.println(SHOW_ORDERS_SUPPLIERS + " to show orders with suppliers.");
	System.out.println(SHOW_INVOICES + " to show invoices.");
	System.out.println(SHOW_WAITINGS + " to show waitings.");
    System.out.println(SAVE + " to  save data.");
    System.out.println(RETRIEVE + " to  retrieve.");
    System.out.println(HELP + " for help.");
  }
  public void addClient() {
    String name = getToken("Enter client name");
    String address = getToken("Enter address");
    String phone = getToken("Enter phone");
    Client result;
    result = warehouse.addClient(name, address, phone);
    if (result == null) {
      System.out.println("Could not add client");
    }
    System.out.println(result);
  }
  public void addProducts() {
    Product result;
    do {
      String name = getToken("Enter product name");
      double price = getDouble("Enter price");
	  double quantity = getDouble("Enter quantity");
      result = warehouse.addProduct(name,price,quantity);
      if (result != null) {
        System.out.println(result);
      } else {
        System.out.println("Product could not be added");
      }
      if (!yesOrNo("Add more products?")) {
        break;
      }
    } while (true);
  }
  public void addManufacturers() {
    Manufacturer result;
    do {
      String name = getToken("Enter manufacturer name");
	  String address = getToken("Enter address");
	  String phone = getToken("Enter phone");
      result = warehouse.addManufacturer(name,address, phone);
      if (result != null) {
        System.out.println(result);
      } else {
        System.out.println("Manufacturer could not be added");
      }
	  do{
	    String productID = getToken("Enter productID the manufacturer can provide: ");
		double price = getDouble("Enter productID price: ");
		warehouse.addManufacturerToProduct(productID,result,price);//+ addProductToManufacturer
		if (!yesOrNo("Add more products?")) {
        break;
      }
	  }while(true);
      if (!yesOrNo("Add more manufacturers?")) {
        break;
      }
    } while (true);
  }
  public void addSupplier() {
     String manufacturerID = getToken("Enter manufacturerID: ");
	 String productID = getToken("Enter productID: ");
	 Product result = warehouse.addSupplier(productID,manufacturerID);
	 if(result!=null){
	   System.out.println("Successful to add Supplier.");
	 }
	 else{
	   System.out.println("Fail to add Supplier.");
	 }
  }
  public void deleteSupplier() {
     String manufacturerID = getToken("Enter manufacturerID: ");
	 String productID = getToken("Enter productID: ");
	 Product result = warehouse.deleteSupplier(productID,manufacturerID);
	 if(result!=null){
	   System.out.println("Successful to delete Supplier.");
	 }
	 else{
	   System.out.println("Fail to add Supplier.");
	 }
  }
  public void acceptOrders() {
      String clientID = getToken("Please enter the Client ID: ");
	  Client client = warehouse.searchClient(clientID);
	  OrderC result = null;
	  if(client != null){
		result = warehouse.makeOrder(clientID);
		do {
            String id = getToken("Enter product ID:");
	        //double price = getDouble("Enter  price:");//System gives the price
	        double quantity = getDouble("Enter quantity:");
            result = warehouse.insertProductToOrder(id,quantity,result);
           if (!yesOrNo("Add more product?")) {
              break;
           }
        } while (true);
		if (result != null) {
            result.showOrder();
        } else {
              System.out.println("Product could not be added");
        }
	  }
	  else{
	    System.out.println("The client ID doesnot exist!");
	  }
	  warehouse.updateTransaction(client,result);
	  warehouse.insertOrderC(result);
  }
  public void processOrderCs() {
      String orderID = getToken("Enter order ID:");
	  OrderC order = warehouse.searchOrderC(orderID); 
	  Invoice invoice = null;
	  if(order != null){
	     System.out.println("Found the order.");
	  }
	  else
	     System.out.println("Cannot find the order.");
	  if(!order.getProcessed()){
	     order.setProcessed();
	     invoice = warehouse.processOrder(order);
		 /*String shipmentID = getToken("Enter shipment ID: ");
		 String shipmentCompany = getToken("Enter shipment Company: ");
		 warehouse.updateShipment(shipmentID, shipmentCompany,invoice);*/
		}
	  else
	     System.out.println("This Order has already processed.");
	  if(invoice != null)
	     System.out.println("Invoice is created. ");
	  else
	     System.out.println("Invoice isnot created. ");
	  order.showOrder();
	  invoice.showInvoice();
  }
  public void acceptPayments() {
      String clientID = getToken("Enter client ID: ");
	  Client result = warehouse.searchClient(clientID);
	  double amount;
	  if(result != null)
	     System.out.println(result);
	  else
	     System.out.println("Cannot find the order.");
	  while(true){
	    amount = getDouble("Enter amount of payment.");
	    if (yesOrNo("s the anount of payment:" +amount+" ?")) 
           break;
	  }
      double balance = warehouse.updateBalance(result,amount);
	  System.out.println("The client's balance is: "+balance);
  }
  public void placeOrders(){
     String productID = getToken("Enter productID:");
	 String manufacturerName = getToken("Enter manufacturer Name:");
	 String orderID = getToken("Enter order ID:");
	 double quantity = getDouble("Enter quantity:");
	 OrderS orderS = warehouse.recordOrderS(orderID,manufacturerName,productID,quantity);
	 if(orderS!=null)
	    System.out.println(orderS);
	 else
	    System.out.println("Failed to record the order.");
  }
  public void acceptShipments() {
      String productID = getToken("Enter product ID:");
	  String orderID = getToken("Enter order ID:");
	  warehouse.updateOrderS(orderID);
	  boolean fill;
	  double quantitySoFar = 0.0;
	  Invoice invoice;
	   Waiting waiting=null;
	  for(Iterator iter = warehouse.getWaitings();iter.hasNext();){
	     waiting = (Waiting)iter.next();
		 for(Iterator iter1=warehouse.getProductsFromWaiting(waiting);iter1.hasNext();){
		   Product product = (Product)iter1.next();
		   if(product.equals(productID)){
		      quantitySoFar += product.getQuantity();
			  if(waiting!=null){
		      waiting.showWaiting();
			  fill = yesOrNo("Do you need to fill this waiting?");
			  if(fill){
			    invoice = warehouse.dealWaiting(waiting,productID);
			    System.out.println("New invoice is created:");
			    /*String shipmentID = getToken("Enter shipment ID: ");
	            String shipmentCompany = getToken("Enter shipment company:");*/
			    invoice.showInvoice();
	            /*if(warehouse.updateShipment(shipmentID,shipmentCompany,invoice))
	               System.out.println("Update shipment informatiin is successful.");
	            else
	               System.out.println("Error of update shipment information.");*/
			}
		  }
		 }
	  }
	 }
	 //warehouse.deleteEmptyWaiting();
	  double quantity = getDouble("Enter quantity: ");
	  if(quantity>quantitySoFar){
	     boolean accept = yesOrNo("The quantity is bigger than that from waitinglist."+
		                           "\nDo you need to receive the quantity?");
		 if(accept)
		   warehouse.updateQuantity(productID,quantity-quantitySoFar);
	  }
  }
  public void listTransactions() {
      String clientID = getToken("Enter client ID: ");
	  Client result = warehouse.searchClient(clientID);
	  warehouse.listTransactions(result);
  }
  public void listClientsBalance() {
      warehouse.listClientsUnbalanced();
  }
  public void listOutstandingOrders() {
      String productID = getToken("Enter product ID:");
	  warehouse.listOutstandingOrders(productID);
  }
  public void listManufacturerPrices() {
      String productID = getToken("Enter product ID:");
	  warehouse.listManufacturerPrices(productID);
  }
  public void listSuppliers(){
      String productID = getToken("Enter product ID:");
	  warehouse.listSuppliers(productID);
  }
  public void showProducts() {
      Iterator allProducts = warehouse.getProducts();
      while (allProducts.hasNext()){
	  Product product = (Product)(allProducts.next());
          System.out.println(product.toString());
      }
  }
  public void showClients() {
      Iterator allClients = warehouse.getClients();
      while (allClients.hasNext()){
	  Client client = (Client)(allClients.next());
          System.out.println(client.toString());
      }
  }
  public void showManufacturers() {
      Iterator allManufacturers = warehouse.getManufacturers();
      while (allManufacturers.hasNext()){
	  Manufacturer manufacturer = (Manufacturer)(allManufacturers.next());
          System.out.println(manufacturer.toString());
      }
  }
  public void showOrderCs() {
      Iterator iter = warehouse.getOrderCs();
      while (iter.hasNext()){
	  OrderC orderC = (OrderC)(iter.next());
          orderC.showOrder();
      }
  }
  public void showOrderSs() {
      Iterator iter = warehouse.getOrderSs();
      while (iter.hasNext()){
	  OrderS orderS = (OrderS)(iter.next());
          System.out.println(orderS.toString());
      }
  }
  public void showInvoices() {
      Iterator iter = warehouse.getInvoices();
      while (iter.hasNext()){
	  Invoice invoice = (Invoice)(iter.next());
          invoice.showInvoice();
      }
  }
  public void showWaitings() {
      Iterator iter = warehouse.getWaitings();
      while (iter.hasNext()){
	  Waiting waiting = (Waiting)(iter.next());
          waiting.showWaiting();
      }
  }
  private void save() {
    if (warehouse.save()) {
      System.out.println(" The warehouse has been successfully saved in the file WarehouseData \n" );
    } else {
      System.out.println(" There has been an error in saving \n" );
    }
  }
  private void retrieve() {
    try {
      Warehouse tempWarehouse = Warehouse.retrieve();
      if (tempWarehouse != null) {
        System.out.println(" The warehouse has been successfully retrieved from the file WarehouseData \n" );
        warehouse = tempWarehouse;
      } else {
        System.out.println("File doesnot exist; creating new warehouse" );
        warehouse = Warehouse.instance();
      }
    } catch(Exception cnfe) {
      cnfe.printStackTrace();
    }
  }
  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
        case ADD_CLIENT:        addClient();
                                break;
        case ADD_PRODUCTS:      addProducts();
                                break;
		case ADD_MANUFACTURERS:   addManufacturers();
								break;
        case ADD_SUPPLIER:      addSupplier();
                                break;
        case DELETE_SUPPLIER:   deleteSupplier();
                                break;
        case ACCEPT_ORDERS:     acceptOrders();
                                break;
        case PLACE_ORDERS:      placeOrders();
                                break;
        case ACCEPT_PAYMENTS:   acceptPayments();
                                break;
		case PROCESS_ORDERS:    processOrderCs();
								break;
        case ACCEPT_SHIPMENTS:  acceptShipments();
                                break;
        case LIST_TRANSACTIONS: listTransactions();
                                break;
        case LIST_CLIENTS_BALANCE: listClientsBalance();
                                break;
		case LIST_MANUFACTURER_ORDERS: listOutstandingOrders();
								break;
		case LIST_MANUFACTURER_PRICES: listManufacturerPrices();
								break;
		case LIST_SUPPLIERS:    listSuppliers();
								break;
        case SAVE:              save();
                                break;
        case RETRIEVE:          retrieve();
                                break;
        case SHOW_CLIENTS:	    showClients();
                                break; 		
        case SHOW_PRODUCTS:	    showProducts();
                                break;
		case SHOW_MANUFACTURERS: showManufacturers();
								break;
		case SHOW_ORDERS_CLIENTS: showOrderCs();
								break;
		case SHOW_ORDERS_SUPPLIERS: showOrderSs();
								break;
		case SHOW_INVOICES:     showInvoices();
		                        break;
		case SHOW_WAITINGS:     showWaitings();
								break;
        case HELP:              help();
                                break;
      }
    }
  }
  public static void main(String[] s) {
    UserInterface.instance().process();
  }
}