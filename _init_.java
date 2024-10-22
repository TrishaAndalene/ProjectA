
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
        System.out.println("Okay");
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
            
            // create the user
            System.out.println("ACCOUNT REGISTRATION");
            System.out.println();
            System.out.print("Username: ");
            String name = In.nextLine();
            System.out.print("Current age: ");
            int age = In.nextInt();
            
        }
    }
}

