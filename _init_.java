// ------------------------- JAVAFX LIBRARY ---------------------------
import javafx.application.Application;
import javafx.stage.Stage;

// ------------------------- MAIN ENGINE --------------------------
public class _init_ extends Application{

    // attributes static
    static AppModel menu = new AppModel();
    static AppView view = new AppView();

    public static void main(String[] args) {

        launch(args);
        // menu.showRegistration();

    }

    @Override
    public void start(Stage primaryStage){

        // setting the value to be used globally in view
        view.primaryStage = primaryStage;

        primaryStage.setTitle("Project A");
        // show scene
        primaryStage.setScene(view.getRegisScene());
        primaryStage.show();

        view.createAccountManagerScreen();

    }
};
