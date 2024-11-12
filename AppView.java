// ------------------------- JAVAFX LIBRARY ---------------------------
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
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
        //  titleLabel.setTextFill(Color.WHITE);
 
         // label
         Label username = new Label("Username: ");
        //  username.setTranslateY(10);
 
         TextField regisAcc = new TextField();
         regisAcc.setPromptText("Enter new name");
         regisAcc.setPrefWidth(200);;
 
         Label acceptName = new Label("✔   ");
         acceptName.setTextFill(Color.FORESTGREEN);
        
         Label age = new Label("Age:          ");
        //  age.setTranslateY(35);
 
         TextField regisAge = new TextField();
         regisAge.setPromptText("Enter your age");
         regisAge.setPrefWidth(200);
 
         Label acceptAge = new Label("✔   ");
         acceptAge.setTextFill(Color.FORESTGREEN);
 
         Label pass = new Label("Password: ");
        //  pass.setTranslateY(50);
 
         TextField regisPass = new TextField();
         regisPass.setPromptText("Enter your password");
         regisPass.setPrefWidth(200);
 
         Label acceptPass = new Label("✔   ");
         acceptPass.setTextFill(Color.FORESTGREEN);
 
        Label accountType = new Label("Account type:   ");

        ToggleGroup toggleAccountCreateGroup = new ToggleGroup();
        RadioButton sellerBtn = new RadioButton("Seller");
        sellerBtn.setToggleGroup(toggleAccountCreateGroup);

        RadioButton guestBtn = new RadioButton("Guest");
        guestBtn.setToggleGroup(toggleAccountCreateGroup);


         Button createBtn = new Button("Sign up");
         createBtn.setStyle("-fx-background-color: #7AB2D3");
         createBtn.setTextFill(Color.WHITE);
         createBtn.setOnMouseEntered(e -> {
             createBtn.setStyle("-fx-background-color: white"); 
             createBtn.setTextFill(Color.BLACK);
         });
         createBtn.setOnMouseExited(e -> {
             createBtn.setStyle("-fx-background-color: #7AB2D3"); 
             createBtn.setTextFill(Color.WHITE);
         });
 
         // fill in first V box

        int paddingLeft = 17;
 
         HBox horiRootButton = new HBox();
         horiRootButton.getChildren().addAll(createBtn);
         horiRootButton.setAlignment(Pos.CENTER);
        //  horiRootButton.setSpacing(40);
        horiRootButton.setTranslateX(90);
        //  horiRootButton.setTranslateY(90);

        HBox horiRootAccount = new HBox();
        horiRootAccount.getChildren().addAll(accountType, guestBtn, sellerBtn);
        horiRootAccount.setAlignment(Pos.CENTER_LEFT);
        horiRootAccount.setSpacing(paddingLeft+25);
 
         HBox horiRootage = new HBox(paddingLeft);
         horiRootage.getChildren().addAll(age, regisAge, acceptAge);
         horiRootage.setAlignment(Pos.CENTER);
        //  horiRootage.setTranslateX(60);
        //  horiRootage.setTranslateY(40);
 
         HBox horiRootPass = new HBox(paddingLeft);
         horiRootPass.getChildren().addAll(pass, regisPass, acceptPass);
         horiRootPass.setAlignment(Pos.CENTER);
        //  horiRootPass.setTranslateX(60);
        //  horiRootPass.setTranslateY(55);
 
         HBox horiRoot = new HBox(paddingLeft);
         horiRoot.getChildren().addAll(username, regisAcc, acceptName);
         horiRoot.setAlignment(Pos.CENTER);
        //  horiRoot.setTranslateX(60);
        //  horiRoot.setTranslateY(25);
 
         VBox fillBox = new VBox();
         fillBox.getChildren().addAll(horiRoot, horiRootage, horiRootPass, horiRootAccount, horiRootButton);
         fillBox.setAlignment(Pos.TOP_CENTER);
         fillBox.setPrefHeight(260);
         fillBox.setMaxWidth(320);
         fillBox.setSpacing(20);
        //  fillBox.setStyle("-fx-background-color: #B9E5E8");
 
        int paddingTop = 30;
         VBox root = new VBox(paddingTop);
         root.getChildren().addAll(titleLabel, fillBox);
         root.setAlignment(Pos.TOP_CENTER);
        //  root.setStyle("-fx-background-color: #7AB2D3");

         Scene regis = new Scene(root, ScreenWidth, ScreenHeight);

         return regis;
 
    }
}
