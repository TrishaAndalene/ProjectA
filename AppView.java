// ------------------------- JAVAFX LIBRARY ---------------------------
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.HBox;

public class AppView {
    
    final static int ScreenWidth = 500;
    final static int ScreenHeight = 450;

    public AppView(){

    }

    public static String showTitleTemplate(){
        String s = "";

        // format writing
        s += "\n __   __   __        ___  __  ___        ";
        s += "\n|__) |__) /  \\    | |__  /  `  |      /\\ ";
        s += "\n|    |  \\ \\__/ \\__/ |___ \\__,  |     /~~\\";
        s += "\n \n \n";

        return s;
    }

    public Scene getRegisScene(){
         // object
         String title = AppView.showTitleTemplate();
         Label titleLabel = new Label(title);
         titleLabel.setFont(Font.font("MonoSpace", FontWeight.EXTRA_BOLD,16));
         titleLabel.setTextFill(Color.WHITE);
 
         // label
         Label username = new Label("Username: ");
         username.setTranslateY(10);
 
         TextField regisAcc = new TextField();
         regisAcc.setPromptText("Enter new name");
         regisAcc.setPrefWidth(200);;
 
         Label acceptName = new Label("   ✔   ");
         acceptName.setTextFill(Color.FORESTGREEN);
 
         Label age = new Label("Age: ");
         age.setTranslateY(35);
 
         TextField regisAge = new TextField();
         regisAge.setPromptText("Enter your age");
         regisAge.setPrefWidth(200);
 
         Label acceptAge = new Label("   ✔   ");
         acceptAge.setTextFill(Color.FORESTGREEN);
 
         Label pass = new Label("Password: ");
         pass.setTranslateY(50);
 
         TextField regisPass = new TextField();
         regisPass.setPromptText("Enter your password");
         regisPass.setPrefWidth(200);
 
         Label acceptPass = new Label("   ✔   ");
         acceptPass.setTextFill(Color.FORESTGREEN);
 
         Button createGuest = new Button("Guest Sign up");
         createGuest.setStyle("-fx-background-color: #7AB2D3");
         createGuest.setTextFill(Color.WHITE);
         createGuest.setOnMouseEntered(e -> {
             createGuest.setStyle("-fx-background-color: white"); 
             createGuest.setTextFill(Color.BLACK);
         });
         createGuest.setOnMouseExited(e -> {
             createGuest.setStyle("-fx-background-color: #7AB2D3"); 
             createGuest.setTextFill(Color.WHITE);
         });
 
         Button createSeller = new Button("Seller Sign up");
         createSeller.setStyle("-fx-background-color: #7AB2D3");
         createSeller.setTextFill(Color.WHITE);
         createSeller.setOnMouseEntered(e -> {
             createSeller.setStyle("-fx-background-color: white"); 
             createSeller.setTextFill(Color.BLACK);
         });
         createSeller.setOnMouseExited(e -> {
             createSeller.setStyle("-fx-background-color: #7AB2D3"); 
             createSeller.setTextFill(Color.WHITE);
         });
 
         // fill in first V box
 
         HBox horiRootButton = new HBox();
         horiRootButton.getChildren().addAll(createGuest, createSeller);
         horiRootButton.setAlignment(Pos.CENTER_LEFT);
         horiRootButton.setSpacing(40);
         horiRootButton.setTranslateX(50);
         horiRootButton.setTranslateY(90);
 
         HBox horiRootage = new HBox();
         horiRootage.getChildren().addAll(regisAge, acceptAge);
         horiRootage.setAlignment(Pos.CENTER_LEFT);
         horiRootage.setTranslateX(60);
         horiRootage.setTranslateY(40);
 
         HBox horiRootPass = new HBox();
         horiRootPass.getChildren().addAll(regisPass, acceptPass);
         horiRootPass.setAlignment(Pos.CENTER_LEFT);
         horiRootPass.setTranslateX(60);
         horiRootPass.setTranslateY(55);
 
         HBox horiRoot = new HBox();
         horiRoot.getChildren().addAll(regisAcc, acceptName);
         horiRoot.setAlignment(Pos.CENTER_LEFT);
         horiRoot.setTranslateX(60);
         horiRoot.setTranslateY(25);
 
         VBox fillBox = new VBox();
         fillBox.getChildren().addAll(username, horiRoot, age, horiRootage, pass, horiRootPass, horiRootButton);
         fillBox.setAlignment(Pos.TOP_CENTER);
         fillBox.setPrefHeight(260);
         fillBox.setMaxWidth(320);
         fillBox.setStyle("-fx-background-color: #B9E5E8");
 
         VBox root = new VBox();
         root.getChildren().addAll(titleLabel, fillBox);
         root.setAlignment(Pos.TOP_CENTER);
         root.setStyle("-fx-background-color: #7AB2D3");

         Scene regis = new Scene(root, ScreenWidth, ScreenHeight);

         return regis;
 
    }
}
