// ------------------------- JAVA LIBRARY --------------------------
import java.util.*;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
//-------------------- INTERFACE SETTINGS -------------------------
interface PageTrack{

    // continue to deeper page
    void nextOptionforCart(Buyer g, String option);
    void nextOptionForCatalogue();
    void nextOptionforBalance(Buyer g);
    void nextOptionforSettings(User u);

}

// ------------------------- MAIN SCREEN --------------------------

public class AppModel{
    
    // class attribute

    private ArrayList<User> users;
    private final static ArrayList<Character> ACCT_TYPE = new ArrayList<>(Arrays.asList('B', 'S'));

    boolean engineStatus;
    int userOption;
    User currentBuyer;

    // public contructor
    public AppModel(){
        this.engineStatus = true;
        this.userOption = 0;
        this.users = new ArrayList<>();
        this.currentBuyer = new Buyer("admin", "12345");
    }

    // user creation method
    public void createSeller(String name, String password, String contact){
        Seller sAcct = new Seller(name, password, contact);
        this.users.add(sAcct);
        System.out.println(sAcct);
    }

    //create a seller account
    public void createBuyer(String name, String password){
        Buyer bAcct = new Buyer(name, password);
        this.users.add(bAcct);
        System.out.println(bAcct);
    }

    // login method
    public boolean loginAcct(String name, String password){
        for(User u : this.users){
            if (name.equals(u.userName) && (password.equals(u.passWord))){
                this.currentBuyer = u;
                return true;
            }
        }
        System.out.println("login failed");
        return false;
    }

    // complete till here
    public Buyer checkIfBuyer(){
        try {
            return (Buyer) this.currentBuyer;
        } catch (Exception w){
            return null;
        }
    }

    public Seller checkIfSeller(){
        try {
            return (Seller) this.currentBuyer;
        } catch (Exception e){
            return null;
        }
    }

    // methods list show template only
    
    
    // page settings for balance
}


// ------------------------- ALL OBJECTS --------------------------
abstract class User {
    //taken from the week 6 module aptitude test to generate UIDs
    private static int nextId = 0;
    int id;
    protected String userName, passWord;
    protected double balance;

    //initialize an account, for simplicity sake, we don't need to add age as an attribute to users unless we want to make a recommendation algorithm
    User(String userName, String passWord){
        //taken from the week 6 module aptitude test to generate UIDs
        this.id = User.nextId;
        User.nextId += 1;
        this.userName = userName;
        this.passWord = passWord;
        this.balance = 0;
    }

    //getters
    public double getBalance() {
        return this.balance;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassWord() {
        return this.passWord;
    }

    public int getId(){
        return this.id;
    }

    public String getPasswordHash(){
        String hash = "";
        for (char c: this.passWord.toCharArray()){
            hash += "*";
        }
        return hash;
    }

    public boolean checkPassword(String pass){
        if (pass.equals(this.passWord)){
            return true;
        } else {
            return false;
        }
    }

    //setters
    void editUserName(String userName){
        this.userName = userName;
    }

    void editPassWord(String passWord){
        this.passWord = passWord;
    }

    //to be overrided
    void checkBalance(){
        //template class
    }

    void addBalance(double cash){
        if (cash > 0){
            this.balance += cash;
        }
    }

    void subtractBalance(double cash){
        this.balance -= cash;
    }

    @Override
    public String toString() {
        return "Name: " + this.getUserName();
    }
}

//CUSTOMER--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
class Buyer extends User {
    //Need 2 arrays for user's shopping cart and previous pruchases
    List<Purchase> cart;
    int cartTotalItem;
    double totalCostCart;
    ArrayList<Purchase> previousPurchases;

    //constructor for customer
    Buyer(String userName, String passWord){
        super(userName, passWord);
        this.cart = new ArrayList<>();
        this.previousPurchases = new ArrayList<>();
        this.cartTotalItem = 0;
        this.totalCostCart = 0;
    }

    //check Cart of customer
    int checkCart(){
        this.cartTotalItem = 0;
        if (this.cart.size() != 0){
            for (Purchase p : this.cart){
                this.cartTotalItem += p.quantity.getValue();
            }
        }
        return this.cartTotalItem;
    }

    int checkCount(String name){
        if (this.cart.size() != 0){
            for (Purchase p : this.cart){
                if(name.equals(p.product.getName().getValue())){
                    return p.quantity.getValue();
                }
            }
        }
        return 0;
    }

    int checkPurchase(String name, int count){
        if (this.cart.size() != 0){
            for (Purchase p : this.cart){
                if(name.equals(p.product.getName().getValue())){
                    p.quantity.set(count);
                    return this.cart.indexOf(p);
                }
            }
        }
        return -1;
    }

    void addToCart(Product p, int quantity){
        for (Purchase pur : this.cart){
            if (pur.product == p){
                break;
            }
        }
        this.cart.add(new Purchase(p, quantity));
    }

    void removeFromCart(Purchase purchase){
        for (int i = 0; i< this.cart.size(); i++){
            Purchase p = this.cart.get(i);
            if (p.product.getName().equals(purchase.product.getName())){
                this.cart.remove(p);
            }
            System.out.println(p);
        }
    }

    double getTotalPriceCart(){
        this.totalCostCart = 0;
        if (this.cart.size() != 0){
            for (Purchase p : this.cart){
                this.totalCostCart += (p.calculatePurchase().getValue());
            }
        }
        return this.totalCostCart;
    }

    @Override
    void checkBalance(){
        System.out.println("Current Balance: A$ " + this.balance);
    }

    void addBalance(){
        //checkBalance();
        // double cash = ModIn.getInteger("How much cash do you want to add to you account: ");
        // super.addBalance(cash);
        //checkBalance();
    }

    void addPreviousPurchase(Purchase purchase){
        this.previousPurchases.add(purchase);
    }

    List<Purchase> getCart(){
        return this.cart;
    }

    ArrayList<Integer> getCartIDs(){
        ArrayList<Integer> cartIDs = new ArrayList<>();
        for (Purchase pur : cart){
            cartIDs.add(pur.getProduct().getProductID());
        }
        return cartIDs;
    }
}

//SELLER--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
class Seller extends User {
    //2 array lists with the option to expand for refunds. Unable to do because of time constraints
    ArrayList<Integer> sellerCatalogue;
    String contactNumber;
    static final double SELL_FEE = 0.05;

    //seller constructor
    Seller(String userName, String passWord, String contactNumber){
        super(userName, passWord);
        this.contactNumber = contactNumber;
        this.sellerCatalogue = new ArrayList<>();
    }

    //check products on seller catalogue
    public int checkProducts(ArrayList<Product> products){
        int size = getSellerCatalogue().size();
        if(getSellerCatalogue().size() != 0){
            System.out.println(this.userName + "'s Inventory:");
            for (Product p : products){
                if (getSellerCatalogue().contains(p.getProductID()))
                    System.out.println(p);
            }
        }
        return size;
    }

    //add a product to the saved UIDs for a product.
    public void addProduct(int p){
        this.sellerCatalogue.add(p);
    }

    //getter
    ArrayList<Integer> getSellerCatalogue(){
        return this.sellerCatalogue;
    }

    String getContactNumber(){
        return this.contactNumber;
    }

    void editContactNumber(String contactNumber){
        this.contactNumber = contactNumber;
    }

    @Override
    void checkBalance(){
        System.out.println(this.getUserName() + "'s Earnings | Market Fee: " + SELL_FEE*100 + "% per purchase" );
        System.out.println("Income before deductions: A$ " + this.getBalance());
        System.out.println("        Total deductions: A$ " + (this.getBalance()*SELL_FEE));
        System.out.println(" Income after deductions: A$ " + (this.getBalance() - (this.getBalance()*SELL_FEE)));
    }

    @Override
    public String toString() {
        return "Name: " + this.getUserName() + " | Contact Number: " + this.contactNumber;
    }
}

//PRODUCT--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//product categories
enum Category{
    FOOD, BEVERAGE, HOMEWARE, ELECTRONIC, TOYS, FASHION, OFFICE, EVENT, BATHROOOM;
}

//products
class Product {
    //attributes
    private SimpleDoubleProperty price;
    public SimpleStringProperty itemName;
    Seller seller;
    private SimpleIntegerProperty stock;
    private Category category;
    //Product IDs
    private static int productId = 0;
    int id;

    Product(String name, double price, Category category, int stock){
        this.itemName = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.stock =  new SimpleIntegerProperty(stock);
        this.seller = null;
        this.category = category;
        //Product IDs
        this.id = Product.productId;
        Product.productId += 1;
    }

    //update Category
    public void updateCategory(){
        System.out.println("Product Categories: ");
        System.out.println("[1] Food");
        System.out.println("[2] Beverages");
        System.out.println("[3] Homeware");
        System.out.println("[4] Electronics");
        System.out.println("[5] Toys");
        System.out.println("[6] Fashion");
        System.out.println("[7] Office");
        System.out.println("[8] Event");
        System.out.println("[9] Bathroom");
    }

    //menu for editing products
    public void editProductDetails(){
        System.out.println("Edit Details");
        System.out.println("[1] Name");
        System.out.println("[2] Price");
        System.out.println("[3] Category");
        System.out.println("[4] Stock");
    }

    //reduce stock
    public void subtractStock(int stock){
        int stockNumber = this.stock.getValue(); 
        stockNumber -= new SimpleIntegerProperty(stock).getValue();
        this.stock.set(stockNumber);
    }

    //update values
    public void updateName(String newName){
        this.itemName = new SimpleStringProperty(newName);
    }

    public void updatePrice(double newPrice){
        this.price = new SimpleDoubleProperty(newPrice);
    }

    public void updateStock(int stock){
        this.stock = new SimpleIntegerProperty(stock);
    }

    public void updateCategory(Category category){
        this.category = category;
    }

    // getter
    public SimpleStringProperty getName(){
        return this.itemName;
    }

    public SimpleDoubleProperty getPrice(){
        return this.price;
    }

    public SimpleIntegerProperty getStock(){
        return this.stock;
    }

    public Seller getSeller(){
        return this.seller;
    }

    public String getSellerName(){
        return this.seller.getUserName();
    }

    public int getProductID(){
        return this.id;
    }

    public Category getCategory(){
        return this.category;
    }
}

//Purchase Class (I don't like Hashmaps)-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//handles purchases in the user acct
class Purchase {
    Product product;
    SimpleIntegerProperty quantity;

    //basic constructor
    Purchase(Product product, int quantity){
        this.product = product;
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    //pay for item
    SimpleDoubleProperty calculatePurchase(){
        SimpleDoubleProperty cost = new SimpleDoubleProperty(this.product.getPrice().getValue()*quantity.getValue());
        return cost;
    }

    //setters
    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    //getters
    public Product getProduct() {
        return product;
    }

    public SimpleIntegerProperty getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Quantity: " + this.quantity + " | " + this.product.toString();
    }
}


//Product Manager------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
interface ProductManager {
    void checkProducts();
    void addProduct();
    void editProduct();
    void removeProduct();
}
