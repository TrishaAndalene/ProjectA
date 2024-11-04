// ------------------------- JAVA LIBRARY --------------------------
import java.util.*;

// ------------------------- MAIN ENGINE --------------------------
public class _init_{
    public static void main(String[] args) {
        
        MenuPage menu = new MenuPage();
        menu.showRegistration();

    }
};

// -------------------- INTERFACE SETTINGS -------------------------
interface PageTrack{

    // continue to deeper page
    void nextOptionforCart(Guest g, String option);
    void nextOptionForCatalogue();
    void nextOptionforBalance(Guest g);
    void nextOptionforSettings();

}

// ------------------------- MAIN SCREEN --------------------------

class MenuPage implements PageTrack{
    
    // class attribute
    boolean engineStatus;
    int userOption;
    User currentGuest;
    Catalogue catalogue;

    // public contructor
    public MenuPage(){
        this.engineStatus = true;
        this.userOption = 0;

        // default item for base catalogue
        ArrayList<Product> default_items = new ArrayList<>();
        default_items.add(new Product("Shampoo", 8.5, Category.BATHROOOM, 10));
        default_items.add(new Product("FootBall", 19.5, Category.TOYS, 2));
        default_items.add(new Product("Apple", 3.1, Category.FOOD, 200));
        default_items.add(new Product("Halloween Costume XL", 41, Category.EVENT, 15));

        this.catalogue = new Catalogue(default_items);
    }

    // methods list

    public void showTitleTemplate(){

        for (int i = 0; i < 25; i++){
            System.out.println();
        }

        System.out.println(" /$$$$$$$                                               /$$            /$$$$$$ ");
        System.out.println("| $$__  $$                                             | $$           /$$__  $$");
        System.out.println("| $$  \\ $$ /$$$$$$   /$$$$$$  /$$  /$$$$$$   /$$$$$$$ /$$$$$$        | $$  \\ $$");
        System.out.println("| $$$$$$$//$$__  $$ /$$__  $$|__/ /$$__  $$ /$$_____/|_  $$_/        | $$$$$$$$");
        System.out.println("| $$____/| $$  \\__/| $$  \\ $$ /$$| $$$$$$$$| $$        | $$          | $$__  $$");
        System.out.println("| $$     | $$      | $$  | $$| $$| $$_____/| $$        | $$ /$$      | $$  | $$");
        System.out.println("| $$     | $$      |  $$$$$$/| $$|  $$$$$$$|  $$$$$$$  |  $$$$/      | $$  | $$");
        System.out.println("|__/     |__/       \\______/ | $$ \\_______/ \\_______/   \\___/        |__/  |__/");
        System.out.println("                        /$$  | $$                                              ");
        System.out.println("                       |  $$$$$$/                                              ");
        System.out.println("                        \\______/                                               ");
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public void showRegistration(){
        while (this.engineStatus){

            this.showTitleTemplate();
            
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
                    int password = In.nextInt();
                    this.currentGuest = new Guest(name, age, password);
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
            //to make it look like cmd clear

            this.showTitleTemplate();

            System.out.println("Welcome dear user, " + this.currentGuest.name);
            System.out.println("How may I help you for today?");
            System.out.println();
            System.out.println("[1] Browse the catalogue");
            if (this.currentGuest instanceof Guest){
                System.out.println("[2] Check your cart");
                System.out.println("[3] Update your balance");
            } else {
                System.out.println("[2] Check your item stock");
                System.out.println("[3] Onhold Deposit");
            }
            System.out.println("[4] Personal settings");
            System.out.println("[E] Close the system");
            System.out.print("Answer: ");
            int option = In.nextInt();

            this.checkUserInputMainScreen(option);

        } 
    }

    public void checkUserInputMainScreen(int option){

        this.showTitleTemplate();

        if (option == 1){
            if (this.currentGuest instanceof Guest){
                this.catalogue.diplayAllItems();
                this.nextOptionForCatalogue();
            }
        } else if (option == 2){
            if (this.currentGuest instanceof Guest){
                Guest g = (Guest) this.currentGuest;
                g.displayMyCart();
                System.out.println("Would you like to finalize(F), remove(R), or update(U) the list? [Press enter to leave]");
                System.out.print("Answer: ");
                String cOption = In.nextLine();
                this.nextOptionforCart(g, cOption);
            };
        } else if (option == 3){
            if (this.currentGuest instanceof Guest){
                Guest g = (Guest) this.currentGuest;
                this.nextOptionforBalance(g);
            };
        }
    }

    // page settings for catalogue
    @Override
    public void nextOptionForCatalogue(){
        System.out.println();
        System.out.println("Do you need help with ?");
        System.out.println();
        System.out.println("[F] filter the list by category");
        System.out.println("[S] other filtering option");
        System.out.println("[A] add item to my cart");
        System.out.println("[E] return back to the previous page");
        System.out.print("Answer: ");

        String option = In.nextLine();

        // if need sorting
        if (option.equalsIgnoreCase("F")){
            System.out.println();
            this.showTitleTemplate();
            System.out.println("Category List: ");
            System.out.println("Food | Beverages | Homeware | Electronic | Toys | Fashion | Office | Event | Bathroom");
            System.out.println();
            System.out.print("Answer: ");
            String category = In.nextLine();

            if (category.equalsIgnoreCase("food")){
                this.catalogue.seperateDisplayByCategory(Category.FOOD);
            } else if (category.equalsIgnoreCase("beverages")){
                this.catalogue.seperateDisplayByCategory(Category.BEVERAGE);
            } else if (category.equalsIgnoreCase("homeware")){
                this.catalogue.seperateDisplayByCategory(Category.HOMEWARE);
            } else if (category.equalsIgnoreCase("electronic")){
                this.catalogue.seperateDisplayByCategory(Category.ELECTRONIC);
            } else if (category.equalsIgnoreCase("toys")){
                this.catalogue.seperateDisplayByCategory(Category.TOYS);
            } else if (category.equalsIgnoreCase("fashion")){
                this.catalogue.seperateDisplayByCategory(Category.FASHION);
            } else if (category.equalsIgnoreCase("office")){
                this.catalogue.seperateDisplayByCategory(Category.OFFICE);
            } else if (category.equalsIgnoreCase("event")){
                this.catalogue.seperateDisplayByCategory(Category.EVENT);
            } else if (category.equalsIgnoreCase("bathroom")){
                this.catalogue.seperateDisplayByCategory(Category.BATHROOOM);
            }

            this.checkUserInputMainScreen(1);

        } else if (option.equalsIgnoreCase("S")){
            this.filteringOption();
            this.checkUserInputMainScreen(1);
        } else if (option.equalsIgnoreCase("A")){
            this.showTitleTemplate();
            this.catalogue.diplayAllItems();
            System.out.print("Please type in the product name or the product's order: ");
            String product = In.nextLine();
            if (product.isBlank()){
                System.out.print("...Going back");
                In.nextLine();
            } else {
                if (this.currentGuest instanceof Guest){
                    Guest g = (Guest) this.currentGuest;
                    try{
                        int index = Integer.parseInt(product);
                        g.addtoMyCart(this.catalogue.addCollectionUser(index));
                    } catch (NumberFormatException e){
                        g.addtoMyCart(this.catalogue.addCollectionUser(product));
                    }
                };
            }
            this.checkUserInputMainScreen(1);
        }
    }

    public void filteringOption(){
        System.out.println();
        
        System.out.println("Filtering options: ");
        System.out.println("[1] high - low price");
        System.out.println("[2] low - high price");
        System.out.print("Answer: ");
        int option = In.nextInt();
        if (option == 1){
            this.catalogue.filterByPrice(true);
        } else if (option == 2){
            this.catalogue.filterByPrice(false);
        }
    }

    // page settings for cart
    @Override
    public void nextOptionforCart(Guest g, String option){
        if (option.equalsIgnoreCase("F")){
            System.out.println("Cart has been finalized, deducting balance");
            // check balance
            if (g.currentBalance >= g.cartPrice){
                g.currentBalance -= g.cartPrice;
                g.cartPrice = 0;
                for (int i = 0; i < this.catalogue.itemList.size(); i++){
                    for (Product b: g.myCart.keySet()){
                        if (this.catalogue.itemList.get(i).equals(b)){
                            int stock = g.myCart.get(b);
                            b.reduceStock(stock);
                        }
                    }
                }
                g.myCart.clear();
                System.out.println("Transaction completes, current balance: A$" + g.currentBalance);
                System.out.print("");
                In.nextLine();
            }   
        }
    }  


    // page settings for balance
    @Override
    public void nextOptionforBalance(Guest g){
        System.out.println();

        System.out.println(g.name + "'s balance details");
        System.out.println("----------------------------");
        System.out.println();
        System.out.println("Current balance: A$" + g.currentBalance);
        System.out.print("Cart's worth: A$" + g.myCart);
        System.out.println();
        In.nextLine();
    }

    // page settings for settings
    @Override 
    public void nextOptionforSettings(){}
}


// ------------------------- ALL OBJECTS --------------------------
// parent User
abstract class User{ 

    // attributes list
    String name;
    int age;
    double currentBalance;
    private int password;

    // constructor
    User(String name, int age, int password){
        this.name = name;
        this.age = age;
        this.password = password;
        this.currentBalance = 0;
    }

    // functions
    public String toString(){
        return "Username : " + this.name + " | age : " + this.age;
    }

    public int getPassword(){
        return this.password;
    }

    public void setNewPassword(int newPassword){
        this.password = newPassword;
    }

}

class Guest extends User{

    // attributes
    HashMap<Product, Integer> myCart;
    double currentBalance;
    double cartPrice;
    
    Guest(String name, int age, int password){
        super(name, age, password);
        this.currentBalance = 100;
        this.cartPrice = 0;
        this.myCart = new HashMap<>();
        
    }

    // independent method
    void depositBalance(double deposit){
        this.currentBalance += deposit;
    }

    void reduceBalance(double payment){
        this.currentBalance -= payment;
    }

    public void addtoMyCart(Product p){
        System.out.print("How many? ");
        int quantity = In.nextInt();
        this.myCart.put(p, quantity);
    }

    public void displayMyCart(){
        System.out.println(this.name + "'s cart list: ");
        for (Product a : this.myCart.keySet()){
            System.out.println(" (->) " + a.name + " | quantity: " + this.myCart.get(a) + " | price: A$" + a.getPrice()*this.myCart.get(a));
            this.cartPrice += a.getPrice()*this.myCart.get(a);
            System.out.println();
        }
        System.out.println("Total price: A$" + this.cartPrice);
    }

    // parent method overriding
    @Override
    public String toString(){
        return "Guest's account: " + this.name + " | age : " + this.age + " | current balance : A$" + this.currentBalance;
    }
}

class Seller extends User{

    double onHoldBalance;

    final static double TAX_RATE = 0.1;

    Seller(String name, int age, int password){
        super(name, age, password);
        this.onHoldBalance = 0;
    }

    double getTotalBalance(){
        return this.currentBalance + this.onHoldBalance;
    }

    void addOnHoldBalance(double customerPayment){
        this.onHoldBalance += customerPayment*(1- TAX_RATE);
    }

    // parent overriding
    @Override
    public String toString(){
        return "Seller's Account : " + this.name + " | age : " + this.age + " | current balance (on-hold balance) : A$ " + this.currentBalance + " ( " + this.onHoldBalance + " ) | tax rate : " + TAX_RATE;
    }
}

// sellable item

enum Category{
    FOOD, BEVERAGE, HOMEWARE, ELECTRONIC, TOYS, FASHION, OFFICE, EVENT, BATHROOOM;
}

class Product{

    // attributes
    private double price;
    String name, description;
    private int stock;
    final Category CATEGORY;

    Product(String name, double price, Category category, int stock){
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.CATEGORY = category;
        this.description = "This is a default description";
    }

    public void updateName(String newName){
        this.name = newName;
    }

    public void updatePrice(double newPrice){
        this.price = newPrice;
    }

    public void updateDesc(String newDesc){
        this.description = newDesc;
    }

    public void addStock(int addStock){
        this.stock += stock;
    }

    public void reduceStock(int minStock){
        this.stock -= minStock;
    }

    // getter
    public int getStock(){
        return this.stock;
    }

    public double getPrice(){
        return this.price;
    }

    public String getName(){
        return this.name;
    }

    // string
    public String toString(){
        return "Item name: " + this.name + " | category: " + this.CATEGORY + " | price: A$" + this.getPrice() + " | current stock: " + this.getStock();
    }
}

class Catalogue{

    ArrayList<Product> itemList, displayedItems;

    Catalogue(ArrayList<Product> itemList){
        this.itemList = itemList;
        this.displayedItems = new ArrayList<>();
    }

    // displaying only
    void diplayAllItems(){
        for (Product a : this.itemList){
            System.out.println(" (->) " + a);
            System.out.println();
        }

    }

    void seperateDisplayByCategory(Category category){
        for (Product a : this.itemList){
            if (a.CATEGORY.equals(category)){
                displayedItems.add(a);
            }
        }

        for (Product a : this.displayedItems){
            System.out.println(" (->) " + a);
            System.out.println();
        }

        this.displayedItems.clear();

        System.out.print("The end of the list, please press [ENTER] to go back");
        In.nextLine();
        
    };

    void diplayAscendingOrder(){

        Comparator<Product> comparator = Comparator.comparing(Product::getName);

        Collections.sort(this.itemList, comparator);
    }

    void filterByPrice(boolean status){
        if (status == true){
            Comparator<Product> comparator = Comparator.comparing(Product::getPrice).reversed();

            Collections.sort(this.itemList, comparator);
        } else {
            Comparator<Product> comparator = Comparator.comparing(Product::getPrice);

            Collections.sort(this.itemList, comparator);
        }
    }

    // collection altering
    Product addCollectionUser(String name){
        for (Product a : this.itemList){
            if (a.name.equalsIgnoreCase(name)){
                return a;
            }
        }
        return null;
    }

    Product addCollectionUser(int index){
        System.out.println(this.itemList.get(index-1));
        return this.itemList.get(index-1);
    }

}

class News{
    String news, title, author;
    // int date;

    News(String author, String title, String news){
        this.author = author;
        this.title = title;
        this.news = news;
    }

    public void printNews(){
        System.out.println(this.title);
        System.out.println("Written by " + this.author);
        System.out.println(this.news);
    }
}

class BroadCast{

    ArrayList<News> announcement;

    BroadCast(){
        this.announcement = new ArrayList<>();
    }

    void addBroadcast(News newAnnouncement){
        this.announcement.add(newAnnouncement);
    }

    void printAllBroadcast(){
        System.out.println("Today's Broadcast ANNOUNCEMENT!!!");
        System.out.println();
        System.out.println();
        for (int i=0; i < this.announcement.size(); i++){
            System.out.print(1+i + " ");
            this.announcement.get(i).printNews();
            System.out.println();
        }
    }

}
