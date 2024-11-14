// ------------------------- JAVAFX LIBRARY ---------------------------
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.HBox;

// ---------------------------- JAVA LIBRARY ----------------------------
import java.util.*;

public class AppView {
    
    final static int ScreenWidth = 500;
    final static int ScreenHeight = 450;

    public HashMap<String, Scene> scenes;

    public AppView(){
        this.scenes = new HashMap<>();

        // trigger to for scenes
        this.createRegisScreen();
    }

    // only static for now
    Label showTitleTemplate(){
        String s = "";

        // format writing
        s += "\n __   __   __        ___  __  ___        ";
        s += "\n|__) |__) /  \\    | |__  /  `  |      /\\ ";
        s += "\n|    |  \\ \\__/ \\__/ |___ \\__,  |     /~~\\";
        s += "\n \n \n";

        Label titleLabel = new Label(s);
        titleLabel.setFont(Font.font("MonoSpace", FontWeight.EXTRA_BOLD,16));
        return titleLabel;
    }

    // create new windows

    // create all pages/scenes
    void createRegisScreen(){
        // object
        Label titleLabel = this.showTitleTemplate();
  
        // label
        Label username = new Label("Username: ");
  
        TextField regisAcc = new TextField();
        regisAcc.setPromptText("Enter new name");
        regisAcc.setPrefWidth(200);;
  
        Label acceptName = new Label("✔   ");
        acceptName.setTextFill(Color.FORESTGREEN);
         
        Label age = new Label("Age:          ");
  
        TextField regisAge = new TextField();
        regisAge.setPromptText("Enter your age");
        regisAge.setPrefWidth(200);
  
        Label acceptAge = new Label("✔   ");
        acceptAge.setTextFill(Color.FORESTGREEN);
  
        Label pass = new Label("Password: ");
  
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

        CheckBox ageVerify = new CheckBox("Yes, I agree to the term and already 18 by the time       ");
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
        horiRootButton.setTranslateX(90);
 
        HBox horiRootAccount = new HBox();
        horiRootAccount.getChildren().addAll(accountType, guestBtn, sellerBtn);
        horiRootAccount.setAlignment(Pos.CENTER_LEFT);
        horiRootAccount.setSpacing(paddingLeft+25);
  
        HBox horiRootage = new HBox(paddingLeft);
        horiRootage.getChildren().addAll(age, regisAge, acceptAge);
        horiRootage.setAlignment(Pos.CENTER);
  
        HBox horiRootPass = new HBox(paddingLeft);
        horiRootPass.getChildren().addAll(pass, regisPass, acceptPass);
        horiRootPass.setAlignment(Pos.CENTER);
  
        HBox horiRoot = new HBox(paddingLeft);
        horiRoot.getChildren().addAll(username, regisAcc, acceptName);
        horiRoot.setAlignment(Pos.CENTER);
  
        VBox fillBox = new VBox();
        fillBox.getChildren().addAll(horiRoot, horiRootage, horiRootPass, horiRootAccount, ageVerify, horiRootButton);
        fillBox.setAlignment(Pos.TOP_CENTER);
        fillBox.setPrefHeight(260);
        fillBox.setMaxWidth(320);
        fillBox.setSpacing(20);
  
        int paddingTop = 30;
        VBox root = new VBox(paddingTop);
        root.getChildren().addAll(titleLabel, fillBox);
        root.setAlignment(Pos.TOP_CENTER);
 
        this.scenes.put("register", new Scene(root, ScreenWidth, ScreenHeight));
    }

    void createMenuScreen(){
        Label titleLabel = this.showTitleTemplate();

        int paddingTop = 30;
        VBox root = new VBox(paddingTop);
        root.getChildren().addAll(titleLabel);
        root.setAlignment(Pos.TOP_CENTER);

        this.scenes.put("menu", new Scene(root, ScreenWidth, ScreenHeight));
    }
    
    // accesor for all scenes
    public Scene getRegisScene(){
        return this.scenes.get("register");
    }

    public Scene getMainScreen(){
        return this.scenes.get("menu");
    }
}
