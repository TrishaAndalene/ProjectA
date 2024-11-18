// ------------------------- JAVA LIBRARY --------------------------
import java.util.*;
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

        // default item for base catalogue
        ArrayList<Product> default_items = new ArrayList<>();
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
                System.out.println("login success");
                return true;
            }
        }
        System.out.println("login failed");
        return false;
    }

    // methods list show template only

    public void showRegistration(){
        while (this.engineStatus){
            
            // create the user (I think we can add an final admin account)
            System.out.println("ACCOUNT REGISTRATION");
            System.out.println();
            System.out.print("Username: ");
            String name = In.nextLine();
            System.out.print("Current age: ");
            int age = In.nextInt();

            if (age < 18){
                System.out.println("Please come back when you already 18");
                break;
            }

            System.out.print("Are you seller? (Y/N) ");
            String option = In.nextLine();
            if (option.equalsIgnoreCase("N")){
                try{
                    System.out.print("Input your password : ");
                    String password = In.nextLine();
                    this.currentBuyer = new Buyer(name, password);
                    System.out.println("ok");
                    this.showMainScreen();

                } catch (Exception e){
                    System.out.println("Session closed, please restart the system");
                    break;
                }
            }
            
        }
    }

    public void showMainScreen(){

        while (this.engineStatus){

            System.out.println("How may I help you for today?");
            System.out.println();
            System.out.println("[1] Browse the catalogue");
            if (this.currentBuyer instanceof Buyer){
                System.out.println("[2] Check your cart");
                System.out.println("[3] Update your balance");
            } else {
                System.out.println("[2] Check your item stock");
                System.out.println("[3] Onhold Deposit");
            }
            System.out.println("[4] Personal settings");
            System.out.println("[5] Search for specific item");
            System.out.println("[E] Close the system");
            System.out.print("Answer: ");
            int option = In.nextInt();
        } 
    }
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
        this.balance += cash;
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
    ArrayList<Purchase> cart;
    ArrayList<Purchase> previousPurchases;

    //constructor for customer
    Buyer(String userName, String passWord){
        super(userName, passWord);
        this.cart = new ArrayList<>();
        this.previousPurchases = new ArrayList<>();
    }

    //check Cart of customer
    void checkCart(){
        if(this.cart.size() == 0){
            System.out.println("Cart is Empty.");
        } else {
            System.out.println(this.userName + "'s Cart:");
            for (Purchase p : cart){
                System.out.println(p);
            }
        }
    }

    void addToCart(Purchase purchase){
        this.cart.add(purchase);
    }

    void removeFromCart(Purchase purchase){
        this.cart.remove(purchase);
        checkCart();
    }

    @Override
    void checkBalance(){
        System.out.println("Current Balance: A$ " + this.balance);
    }

    void addBalance(){
        checkBalance();
        // double cash = ModIn.getInteger("How much cash do you want to add to you account: ");
        // super.addBalance(cash);
        checkBalance();
    }

    void addPreviousPurchase(Purchase purchase){
        this.previousPurchases.add(purchase);
    }

    ArrayList<Purchase> getCart(){
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
    private double price;
    String name;
    Seller seller;
    private int stock;
    private Category category;
    //Product IDs
    private static int productId = 0;
    int id;

    Product(String name, double price, Category category, int stock, Seller seller){
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.seller = seller;
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
        // int choice = ModIn.getInteger("Enter your Product's Category (1 - 9)", 0, 9);
        // if (choice == 1){
        //     this.updateCategory(Category.FOOD);
        // } else if (choice == 2){
        //     this.updateCategory(Category.BEVERAGE);
        // } else if (choice == 3){
        //     this.updateCategory(Category.HOMEWARE);
        // } else if (choice == 4){
        //     this.updateCategory(Category.ELECTRONIC);
        // } else if (choice == 5){
        //     this.updateCategory(Category.TOYS);
        // } else if (choice == 6){
        //     this.updateCategory(Category.FASHION);
        // } else if (choice == 7){
        //     this.updateCategory(Category.OFFICE);
        // } else if (choice == 8){
        //     this.updateCategory(Category.EVENT);
        // } else if (choice == 9){
        //     this.updateCategory(Category.BATHROOOM);
        // }
    }

    //menu for editing products
    public void editProductDetails(){
        System.out.println("Edit Details");
        System.out.println("[1] Name");
        System.out.println("[2] Price");
        System.out.println("[3] Category");
        System.out.println("[4] Stock");
        // int choice = ModIn.getInteger("Enter Choice: ", 0, 4);
        // if (choice == 1){
        //     this.updateName(ModIn.getString("Enter Product Name: "));
        // } else if (choice == 2){
        //     this.updatePrice(ModIn.getDouble("Enter Product Price: "));
        // } else if (choice == 3){
        //     this.updateCategory();
        // } else if (choice == 4){
        //     this.updateStock(ModIn.getInteger("Enter Product's Quantity"));
        // }
    }

    //reduce stock
    public void subtractStock(int stock){
        this.stock -= stock;
    }

    //update values
    public void updateName(String newName){
        this.name = newName;
    }

    public void updatePrice(double newPrice){
        this.price = newPrice;
    }

    public void updateStock(int stock){
        this.stock = stock;
    }

    public void reduceStock(int minStock){
        this.stock -= minStock;
    }

    public void updateCategory(Category category){
        this.category = category;
    }

    // getter
    public String getName(){
        return this.name;
    }

    public double getPrice(){
        return this.price;
    }

    public int getStock(){
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

    // string
    public String toString(){
        if (this.stock > 0){
            return "Product UID [" + this.getProductID() + "] | Name: " + this.getName() + " | Price: A$ " + this.getPrice() + " | Stock: " + this.getStock() + " | Seller Name: " + this.getSellerName();
        } else{
            return "====SOLD OUT====| Product UID [" + this.getProductID() + "] | Name: " + this.getName() + " | Price: A$ " + this.getPrice() + " | Stock: " + this.getStock() + " | Seller Name: " + this.getSellerName();
        }
        
    }
}
//Purchase Class (I don't like Hashmaps)-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//handles purchases in the user acct
class Purchase {
    Product product;
    int quantity;

    //basic constructor
    Purchase(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
    }

    //pay for item
    double calculatePurchase(){
        double cost = this.product.getPrice()*quantity;
        return cost;
    }

    //setters
    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    //getters
    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
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
