// ------------------------- JAVA LIBRARY --------------------------
import java.util.*;
//-------------------- INTERFACE SETTINGS -------------------------
interface PageTrack{

    // continue to deeper page
    void nextOptionforCart(Guest g, String option);
    void nextOptionForCatalogue();
    void nextOptionforBalance(Guest g);
    void nextOptionforSettings(User u);

}

// ------------------------- MAIN SCREEN --------------------------

public class AppModel implements PageTrack{
    
    // class attribute
    boolean engineStatus;
    int userOption;
    User currentGuest;
    Catalogue catalogue;

    // public contructor
    public AppModel(){
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

    // methods list show template only

    public void showRegistration(){
        while (this.engineStatus){

            AppView.showTitleTemplate();
            
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

            AppView.showTitleTemplate();

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
            System.out.println("[5] Search for specific item");
            System.out.println("[E] Close the system");
            System.out.print("Answer: ");
            int option = In.nextInt();

            this.checkUserInputMainScreen(option);

        } 
    }

    // security check
    public boolean checkPassword(User u){
        System.out.println("Security identification!");
        System.out.print("Password: ");
        int password = In.nextInt();
        if (u.getPassword() == password){
            return true;
        } else {
            System.out.println("Password mismatch....");
            System.out.print("Cancelling protocol...");
            In.nextLine();
            return false;
        }
    }

    // check user input
    public void checkUserInputMainScreen(int option){

        AppView.showTitleTemplate();

        // universal choice
        if (option == 1){
            this.catalogue.diplayAllItems();
            this.nextOptionForCatalogue();
        } else if (option == 4){
            this.nextOptionforSettings(this.currentGuest);
        } else if (option == 5){
            this.catalogue.lookforItem();
        }

        // type restricted choice
        if (this.currentGuest instanceof Guest){
            Guest g = (Guest) this.currentGuest;
            if (option == 2){
                g.displayMyCart();
                System.out.println("Would you like to finalize(F) or remove (R) the list? [Press enter to leave]");
                System.out.print("Answer: ");
                String cOption = In.nextLine();
                this.nextOptionforCart(g, cOption);
            } else if (option == 3){
                this.nextOptionforBalance(g);
            }
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
            AppView.showTitleTemplate();
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
            AppView.showTitleTemplate();
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
            AppView.showTitleTemplate();
            if (this.checkPassword(g)){
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
            this.checkUserInputMainScreen(2);

        } else if (option.equalsIgnoreCase("R")){
            AppView.showTitleTemplate();
            g.displayMyCart();
            System.out.print("Please type in the product name or the product's order: ");
            String product = In.nextLine();
            if (product.isBlank()){
                System.out.print("...Going back");
                In.nextLine();
            } else {
                try{
                    int index = Integer.parseInt(product);
                    g.removeItemMyCart(index);
                } catch (NumberFormatException e){
                    g.removeItemMyCart(product);
                }

                };
                this.checkUserInputMainScreen(2);
            };
        } 


    // page settings for balance
    @Override
    public void nextOptionforBalance(Guest g){
        System.out.println();

        System.out.println(g.name + "'s balance details");
        System.out.println("----------------------------");
        System.out.println();
        System.out.println("Current balance: A$" + g.currentBalance);
        System.out.print("Cart's worth: A$" + g.cartPrice);
        System.out.println();
        
        System.out.println("Select one of these options: [Balance can only be deposited, any other update may only change once the cart is finalize]");
        System.out.println("[1] deposit more balance");
        System.out.println("[2] return back");
        System.out.print("Answer: ");
        int option = In.nextInt();

        if (option == 1){
            AppView.showTitleTemplate();
            if (this.checkPassword(g)){
                System.out.print("Enter deposit value: A$");
                double add = In.nextDouble();
                g.depositBalance(add);
                this.checkUserInputMainScreen(3);
            }
        }
    }

    // page settings for settings
    @Override 
    public void nextOptionforSettings(User g){
        AppView.showTitleTemplate();
        System.out.println();

        if (g instanceof Guest){
            System.out.println((Guest) g);
        } else if (g instanceof Seller){
            System.out.println((Seller) g);
        }

        System.out.println();
        System.out.println("What do you want to do?");
        System.out.println("[1] Update name");
        System.out.println("[2] Update password");
        System.out.println("[3] Return back");
        System.out.print("Answer: ");
        int option = In.nextInt();
        AppView.showTitleTemplate();
        if (option < 3 && option > 0){
            if (this.checkPassword(g)){
                if (option == 1){
                    AppView.showTitleTemplate();
                    System.err.print("Enter a new name: ");
                    String newName = In.nextLine();
                    if (!newName.isBlank()){
                        g.name = newName;
                    } else {
                        System.out.print("Denied..");
                        In.nextLine();
                    }
                    this.checkUserInputMainScreen(4);
                } else if (option == 2){
                    AppView.showTitleTemplate();
                    System.out.print("Enter new password: ");
                    int newPassword = In.nextInt();
                    g.setNewPassword(newPassword);
                    this.checkUserInputMainScreen(4);
                };
            } else {
                System.out.print("Access denied....");
                In.nextLine();
            }
        }
    }
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
        this.cartPrice += p.getPrice()*quantity;
        System.out.print(p.name + " is added to the cart");
        In.nextLine();
    }

    void removeItemMyCart(String name){
        for (Product a : this.myCart.keySet()){
            if (a.name.equalsIgnoreCase(name)){
                this.cartPrice -= a.getPrice()*this.myCart.get(a);
                this.myCart.remove(a);
                System.out.print("Order removed");
                In.nextLine();
            }
        }
    }

    void removeItemMyCart(int index){
        ArrayList<Product> tempList = new ArrayList<>();
        for (Product a : this.myCart.keySet()){
            tempList.add(a);
        }
        this.cartPrice -= tempList.get(index-1).getPrice()*this.myCart.get(tempList.get(index-1));
        this.myCart.remove(tempList.get(index-1));
        System.out.print("Order removed");
        In.nextLine();
    }

    public void displayMyCart(){
        System.out.println(this.name + "'s cart list: ");
        for (Product a : this.myCart.keySet()){
            System.out.println(" (->) " + a.name + " | quantity: " + this.myCart.get(a) + " | price: A$" + this.myCart.get(a)*a.getPrice());
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

    void lookforItem(){
        System.out.print("Please type the product name: ");
        String product = In.nextLine();
        boolean found = false;
        if (!product.isBlank()){
            for (Product p : this.itemList){
                if (p.getName().equalsIgnoreCase(product)){
                    System.out.println("Item Data is found:");
                    System.out.println("(->) " + p);
                    System.out.print("Press enter to go back");
                    found = true;
                    In.nextLine();
                }
            };
            if (found == false){
                System.out.println("Item is not found");
                In.nextLine();
            }
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

