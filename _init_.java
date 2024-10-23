
// ------------------------- MAIN ENGINE --------------------------
public class _init_{
    public static void main(String[] args) {
        
        MenuPage menu = new MenuPage();
        menu.showMenu();

    }
};

class MenuPage {
    
    // class attribute
    boolean engineStatus;
    int userOption;

    // public contructor
    public MenuPage(){
        this.engineStatus = true;
        this.userOption = 0;
    }

    // methods list
    public void showMenu(){
        while (this.engineStatus){

            // printing the menu
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
                    Guest currentGuest = new Guest(name, age, password);
                    System.out.println("ok");
                } catch (Exception e){
                    System.out.println("Account registration failed, please restart the system");
                    break;
                }
            }
            
        }
    }
}


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
