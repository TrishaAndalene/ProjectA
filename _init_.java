// ------------------------- JAVA LIBRARY --------------------------
import java.util.*;

// ------------------------- MAIN ENGINE --------------------------
public class _init_{
    public static void main(String[] args) {
        
        MenuPage menu = new MenuPage();
        menu.showRegistration();

    }
};

// ------------------------- MAIN SCREEN --------------------------
class MenuPage {
    
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
                    System.out.println("Session closed due to errors, please restart the system");
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
            System.out.println("[5] Close the system");
            System.out.print("Answer: ");
            int option = In.nextInt();

            if (option == 5){
                this.engineStatus = false;
            }

            this.checkUserInputMainScreen(option);

        } 
    }

    public void checkUserInputMainScreen(int option){
        if (option == 1){
            if (this.currentGuest instanceof Guest){
                this.catalogue.diplayAllItems();
                this.nextOptionForCatalogue();
            } else {
                // pass
            }
        }
    }

    public void nextOptionForCatalogue(){
        System.out.println();
        System.out.println("Do you need help with ?");
        System.out.println();
        System.out.println("[F] filter the list by category");
        System.out.println("[S] filter by name order");
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
            System.out.print("Asnwer: ");
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

        } else if (option.equalsIgnoreCase("S")){
            this.catalogue.diplayAscendingOrder();
        }
    }
}


// ------------------------- ALL OBJECTS --------------------------
// parent User
class User{ 

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
    
    Guest(String name, int age, int password){
        super(name, age, password);
    }

    // independent method
    void depositBalance(double deposit){
        this.currentBalance += deposit;
    }

    void reduceBalance(double payment){
        this.currentBalance -= payment;
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

        for (Product a : this.itemList){
            System.out.println(" (->) " + a);
            System.out.println();
        }

        System.out.print("The end of the list, please press [ENTER] to go back");
        In.nextLine();
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
