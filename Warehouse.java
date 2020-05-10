import java.util.*;
import java.io.*;
public class Warehouse implements Serializable {
  private static final long serialVersionUID = 1L;
  private Inventory inventory;
  private ClientList clientList;
  private ManufacturerList manufacturerList;
  private OrderCList orderCList;
  private OrderSList orderSList;
  private WaitingList waitingList;
  private InvoiceList invoiceList;
  private static Warehouse warehouse;
  private Warehouse() {
    inventory = Inventory.instance();
    clientList = ClientList.instance();
	manufacturerList = ManufacturerList.instance();
	orderCList = OrderCList.instance();
	orderSList = OrderSList.instance();
	waitingList = WaitingList.instance();
	invoiceList = InvoiceList.instance();
  }
  public static Warehouse instance() {
    if (warehouse == null) {
      MemberIdServer.instance(); // instantiate all singletons
	  ProductIdServer.instance();
	  ManufacturerIdServer.instance();
	  OrderCIdServer.instance();
      return (warehouse = new Warehouse());
    } else {
      return warehouse;
    }
  }
  //Add product to inventory
  public Product addProduct(String name,double price,double quantity) {
    Product product = new Product(name,price,quantity);
    if (inventory.insertProduct(product)) {
      return (product);
    }
    return null;
  }
  //Add client to clientlist
  public Client addClient(String name, String address, String phone) {
    Client client = new Client(name, address, phone);
    if (clientList.insertClient(client)) {
      return (client);
    }
    return null;
  }
  //Add manufacturer to manufacturerlist and relative product
  public Manufacturer addManufacturer(String name, String address, String phone) {
    Manufacturer manufacturer = new Manufacturer(name,address, phone);
    if (manufacturerList.insertManufacturer(manufacturer)) {
       return (manufacturer);
    }
    return null;
  }
  public void addManufacturerToProduct(String productID,Manufacturer manu,double price){
    Product product = searchProduct(productID);
	product.addManufacturerToProduct(manu,price);
	manu.addProductToManufacturer(product,price);
  }
  //Record order with supplier
  public OrderS recordOrderS(String orderID,String manufacturerName,String productID,double quantity){
    Product product= searchProduct(productID);
	Manufacturer manufacturer = searchManufacturer(manufacturerName);
	OrderS orderS = new OrderS(orderID,manufacturerName,productID,quantity);
	for(Iterator iter = manufacturer.getProducts();iter.hasNext();){
	  Product product1 = (Product)iter.next();
	  if(product1.equals(productID)){
	     Product product2 = new Product(product1);
         product2.setQuantity(quantity);
		 orderS.insertProduct(product2);
	  }
	}
	orderSList.insertOrderS(orderS);
	product.insertOrderS(orderS);
	manufacturer.insertOrderS(orderS);
	return orderS;
  }
  //Accept shipment from supplier
  public Iterator getProductsFromWaiting(Waiting waiting){
    Iterator iter = waiting.getProducts();
	return iter;
  }
  public Invoice dealWaiting(Waiting waiting, String productID){
     Invoice invoice = new Invoice(waiting);
	 Product product=null;
	 for(Iterator iter2 = waiting.getProducts();iter2.hasNext();){
         product = (Product)iter2.next();
		 if(product.equals(productID))
		   break;
	}
	 invoice.insertProduct(product);
	 invoiceList.insertInvoice(invoice);
	 waiting.removeProduct(product);
	 return invoice;
  }
  public void updateQuantity(String productID,double value){
    Product product = searchProduct(productID);
	product.updateQuantity(value);
  }
  public void updateOrderS(String orderID){
     OrderS orderS = searchOrderS(orderID);
	 orderS.setProcessed();
  }
  /*public boolean updateShipment(String shipmentID,String shipmentCompany,Invoice invoice){
    invoice.setShipmentID(shipmentID);
	invoice.setShipmentCompany(shipmentCompany);
	invoice.setShipmentDate();
	return true;
  }*/
  //Get Iterators
  public Iterator getProducts() {
      return inventory.getProducts();
  }
  public Iterator getClients() {
      return clientList.getClients();
  }
  public Iterator getManufacturers() {
      return manufacturerList.getManufacturers();
  }
  public Iterator getOrderCs() {
      return orderCList.getOrderCs();
  }
  public Iterator getOrderSs() {
      return orderSList.getOrderSs();
  }
  public Iterator getInvoices() {
      return invoiceList.getInvoices();
  }
  public Iterator getWaitings() {
      return waitingList.getWaitings();
  }
  //Search Client, Manufacturer,and Product by ID
  public Client searchClient(String clientID){
	Client client = null;
	for(Iterator iter = getClients();iter.hasNext();){
	  client = (Client)iter.next();
	  if(client.equals(clientID))
	     break;
	}
	return client;
  }
  public Manufacturer searchManufacturer(String manufacturerName){
    Manufacturer manufacturer = null;
	for(Iterator iter = manufacturerList.getManufacturers();iter.hasNext();){
	  manufacturer = (Manufacturer)iter.next();
	  if(manufacturer.getName().equals(manufacturerName)){
	     break;
	  }
	}
	return manufacturer;
  }
  public Product searchProduct(String productID){
    Product product = null;
	for(Iterator iter = inventory.getProducts();iter.hasNext();){
	  product = (Product)iter.next();
	  if(product.equals(productID)){
	     break;
	  }
	}
	return product;
  }
  public OrderC searchOrderC(String orderID){
    OrderC order = null;
	for(Iterator iter = orderCList.getOrderCs();iter.hasNext();){
	  order = (OrderC)iter.next();
	  if(order.equals(orderID)){
	     break;
	  }
	}
	return order;
  }
  public OrderS searchOrderS(String orderID){
    OrderS order = null;
	for(Iterator iter = orderSList.getOrderSs();iter.hasNext();){
	  order = (OrderS)iter.next();
	  if(order.equals(orderID)){
	     break;
	  }
	}
	return order;
  }
  //Accept order
  public OrderC makeOrder(String clientID){
    OrderC orderC = new OrderC(clientID);
	return orderC;
  }
  public OrderC insertProductToOrder(String productID,double quantity,OrderC orderC){
    Product product = new Product(searchProduct(productID));
	product.setQuantity(quantity);
	orderC.insertProduct(product);
	return orderC;
  }
  public boolean insertOrderC(OrderC order){
    return orderCList.insertOrderC(order);
  }
  public boolean updateTransaction(Client client,OrderC order){
    client.updateBalance(order.getTotalCost());
	return client.updateTransaction(order);
  }
  //Process order
  public Invoice processOrder(OrderC order){
	Invoice invoice = new Invoice(order);
	Waiting waiting = new Waiting(order);
	for(Iterator iter = order.getProducts();iter.hasNext();){
	   Product product = (Product)iter.next();
	   for(Iterator iter1 = inventory.getProducts();iter1.hasNext();){
	     Product product1 = (Product)iter1.next();
		 if(product1.getName().equals(product.getName())){
		    if(product.getQuantity() <= product1.getQuantity()){
			    invoice.insertProduct(product);
				product1.updateQuantity(0.0-product.getQuantity());
			}
			else{
			    if(product1.getQuantity()>0)
			       invoice.insertProduct(product1);
                product1.setQuantity(product.getQuantity()-product1.getQuantity());
				waiting.insertProduct(product1);
                product1.setQuantity(0);				
		    }
		 }
	   }
	}
	if(invoice.getSizeProducts()!=0)
	   invoiceList.insertInvoice(invoice);
	if(waiting.getSizeProducts()!=0)
	   waitingList.insertWaiting(waiting);
	return invoice;
  }
  /*public void 	updateShipment(Invoice invoice,String shipmentID,String shipmentCompany){
     invoice.setShipmentID(shipmentID);
	 invoice.setShipmentCompany(shipmentCompany);
	 invoice.setShipmentDate();
  }*/

  public double updateBalance(Client client,double amount){
    return client.updateBalance(0.0-amount);
  }
  //Add a Supplier to a product
  public Product addSupplier(String productID,String manufacturerID){
    Product product = searchProduct(productID);
	product.addSupplierToProduct(manufacturerID);
	return product;
  }
  //Delete a Supplier from a product
  public Product deleteSupplier(String productID,String manufacturerID){
    Product product = searchProduct(productID);
    product.deleteSupplierFromProduct(manufacturerID);
	return product;
  }
  //List transactions for a client
  public void listTransactions(Client client){
    client.listTransactions();
  }
  //List clients with outstanding balance
  public void listClientsUnbalanced(){
    for(Iterator iter = clientList.getClients();iter.hasNext();){
	  Client client = (Client)iter.next();
	  if(client.getBalance()>0){
	     System.out.println(client);
	     System.out.println("Balance: "+client.getBalance());
	  }
	}
  }
  //List outstanding orders
  public void listOutstandingOrders(String productID){
    Product product = searchProduct(productID);
	product.listOutstandingOrders();
  }
  //List manufacture & price for a product
  public void listManufacturerPrices(String productID){
    Product product = searchProduct(productID);
	product.listManufacturerPrices();
  }
  //List suppliers for a product
  public void listSuppliers(String productID){
    Product product = searchProduct(productID);
	product.listSuppliers();
  }
  public static Warehouse retrieve() {
    try {
      FileInputStream file = new FileInputStream("WarehouseData");
      ObjectInputStream input = new ObjectInputStream(file);
      input.readObject();
      MemberIdServer.retrieve(input);
	  ProductIdServer.retrieve(input);
	  ManufacturerIdServer.retrieve(input);
	  OrderCIdServer.retrieve(input);
	  return warehouse;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return null;
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
      return null;
    }
  }
  public static  boolean save() {
    try {
      FileOutputStream file = new FileOutputStream("WarehouseData");
      ObjectOutputStream output = new ObjectOutputStream(file);
      output.writeObject(warehouse);
      output.writeObject(MemberIdServer.instance());
	  output.writeObject(ProductIdServer.instance());
	  output.writeObject(ManufacturerIdServer.instance());
	  output.writeObject(OrderCIdServer.instance());
      return true;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return false;
    }
  }
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(warehouse);
    } catch(IOException ioe) {
      System.out.println(ioe);
    }
  }
  private void readObject(java.io.ObjectInputStream input) {
    try {
      input.defaultReadObject();
      if (warehouse == null) {
        warehouse = (Warehouse) input.readObject();
      } else {
        input.readObject();
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  public String toString() {
    return inventory + "\n" + clientList;
  }
}
