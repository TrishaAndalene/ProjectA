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
    Guest currentGuest;

    // public contructor
    public MenuPage(){
        this.engineStatus = true;
        this.userOption = 0;
    }

    // methods list

    public void showTitleTemplate(){
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
                    System.out.println("Account registration failed, please restart the system");
                    break;
                }
            }
            
        }
    }

    public void showMainScreen(){

        while (this.engineStatus){
            //to make it look like cmd clear
            for (int i = 0; i < 30; i++){
                System.out.println();
            }

            this.showTitleTemplate();
            System.out.println();
            System.out.println();

            System.out.println("Welcome dear user, " + this.currentGuest.name);
            System.out.println("How may I help you for today?");
            System.out.println();
            System.out.println("[1] Browse the catalogue");
            System.out.println("[2] Check your cart");
            System.out.println("[3] Update your balance");
            System.out.println("[4] Personal settings");
            System.out.print("Answer: ");
            int option = In.nextInt();
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
    FOOD, BEVERAGE, HOMEWARE, ELECTRONIC, TOYS, FASHION, OFFICE, EVENT;
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
}

class Catalogue{

    ArrayList<Product> itemList, displayedItems;

    Catalogue(ArrayList<Product> itemList){
        this.itemList = itemList;
        this.displayedItems = new ArrayList<>();
    }

    void seperateDisplayByCategory(Category category){
        for (Product a : this.itemList){
            if (a.CATEGORY.equals(category)){
                displayedItems.add(a);
            }
        }
    };

    void diplaySpecific(){
        for (Product a : this.displayedItems){
            System.out.println("ok");
        }
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
