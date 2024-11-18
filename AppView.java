// ------------------------- JAVAFX LIBRARY ---------------------------
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

// ---------------------------- JAVA LIBRARY ----------------------------
import java.util.*;

public class AppView {
    
    final static int ScreenWidth = 500;
    final static int ScreenHeight = 450;

    public HashMap<String, Scene> scenes;
    protected Stage primaryStage;
    protected AppModel model;
    protected AppController control;

    public AppView(AppModel model, AppController control){
        this.scenes = new HashMap<>();
        this.primaryStage = null;
        this.model = model;
        this.control = control;

        // trigger to for scenes
        this.createRegisScreen();
        this.createLoginScreen();
        this.createMenuScreen();
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
        this.setLabelFont(titleLabel, 16);
        return titleLabel;
    }

    // create new windows
    public void createAccountManagerScreen(){

        Stage accountStart = new Stage();
        accountStart.setTitle("Account Manager");

        Label questionAcc = new Label("Do you have an account?");
        this.setLabelFont(questionAcc, 16);
        
        Label warningLabel = new Label("<!> you will need 1 seller and buyer account to start");
        this.setLabelFont(warningLabel, 10);
        warningLabel.setTextFill(Color.ORANGERED);

        Button yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            accountStart.close();
            this.getSpecificScene("login");
        });

        Button noButton = new Button("No");
        noButton.setOnAction(e -> {
            accountStart.close();
            this.getSpecificScene("register");
        });

        this.multipleButtonAnimation(yesButton, noButton);

        // box creation
        HBox btnBox = new HBox();
        btnBox.getChildren().addAll(yesButton, noButton);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(50);
        
        VBox windowBox = new VBox();
        windowBox.getChildren().addAll(questionAcc, warningLabel, btnBox);
        windowBox.setAlignment(Pos.CENTER);
        windowBox.setSpacing(20);

        // new window
        accountStart.setScene(new Scene(windowBox, 350, 150));

        // to set the window to unskippable and close all windows if force-close
        accountStart.initModality(Modality.APPLICATION_MODAL);

        accountStart.setOnCloseRequest(e -> {
            javafx.application.Platform.exit();
        });

        // show the new window
        accountStart.show();
    }

    public void createGreetingWindow(){
        Stage greetings = new Stage();
        greetings.setTitle("Welcome to D.D.D shopping simulator!");

        Label greetTitle = new Label("       Welcome to \nD.D.D shopping simulator!");
        this.setLabelFont(greetTitle, 16);
        greetTitle.setWrapText(true);
        greetTitle.setTranslateX(10);
        greetTitle.setTranslateY(20);
        
        Label greetingLabel = new Label("Hey there, pleased to meet you! \nFeel free to wander around! \nHappy learning!!");
        greetingLabel.setWrapText(true);
        this.setLabelFont(greetingLabel, 11);
        greetingLabel.setTranslateX(10);
        greetingLabel.setTranslateY(20);

        Button yesBtn = new Button("Yes");
        yesBtn.setTranslateY(20);
        yesBtn.setOnAction(e -> {
            greetings.close();
        });

        this.buttonAnimation(yesBtn);

        // box creation

        VBox windowBox = new VBox();
        windowBox.getChildren().addAll(greetTitle, greetingLabel, yesBtn);
        windowBox.setAlignment(Pos.TOP_CENTER);
        windowBox.setSpacing(20);

        // new window
        greetings.setScene(new Scene(windowBox, 350, 200));

        // show the new window
        greetings.show();
    }

    // create all pages/scenes
    void createRegisScreen(){
        // object
        Label titleLabel = this.showTitleTemplate();
  
        // label
        Label username = new Label("Username: ");
  
        TextField regisAcc = new TextField();
        regisAcc.setPromptText("Enter new name");
        regisAcc.setPrefWidth(200);;
  
        Label acceptName = new Label("✘   ");
        acceptName.setTextFill(Color.RED);
  
        Label pass = new Label("Password: ");
  
        TextField regisPass = new TextField();
        regisPass.setPromptText("Enter your password");
        regisPass.setPrefWidth(200);
  
        Label acceptPass = new Label("✘   ");
        acceptPass.setTextFill(Color.RED);

        // textfield change 
        regisAcc.textProperty().addListener((observable) -> {
            if (this.control.stringNotNull(regisAcc.getText())){
                acceptName.setText("✔   ");
                acceptName.setTextFill(Color.FORESTGREEN);
            };
        });
        regisPass.textProperty().addListener((observable) -> {
            if (this.control.stringNotNull(regisPass.getText())){
                acceptPass.setText("✔   ");
                acceptPass.setTextFill(Color.FORESTGREEN);
            };
        });
  
        Label accountType = new Label("Account type:   ");
 
        ToggleGroup toggleAccountCreateGroup = new ToggleGroup();
        RadioButton sellerBtn = new RadioButton("Seller");
        sellerBtn.setToggleGroup(toggleAccountCreateGroup);
 
        RadioButton guestBtn = new RadioButton("Buyer");
        guestBtn.setToggleGroup(toggleAccountCreateGroup);

        Label warningLabel = new Label("<!> check all the requirements");
        warningLabel.setVisible(false);
        warningLabel.setTextFill(Color.ORANGERED);

        CheckBox ageVerify = new CheckBox("Yes, I agree to the term and already 18 by the time       ");
        Button createBtn = new Button("Sign up");
        createBtn.setOnAction(e -> {
            if (ageVerify.isSelected() && this.control.stringNotNull(regisAcc.getText()) && this.control.stringNotNull(regisPass.getText())){
                if (sellerBtn.isSelected()){
                    this.model.createSeller(regisAcc.getText(), regisPass.getText(), "");
                    sellerBtn.setSelected(false);
                } else {
                    this.model.createBuyer(regisAcc.getText(), regisPass.getText());
                    guestBtn.setSelected(false);
                }
                this.createAccountManagerScreen();
                warningLabel.setVisible(false);
                regisAcc.clear();
                regisPass.clear();
                ageVerify.setSelected(false);
            } else {
                warningLabel.setVisible(true);
            }
        });
        this.buttonAnimation(createBtn);
  
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
  
        HBox horiRootPass = new HBox(paddingLeft);
        horiRootPass.getChildren().addAll(pass, regisPass, acceptPass);
        horiRootPass.setAlignment(Pos.CENTER);
  
        HBox horiRoot = new HBox(paddingLeft);
        horiRoot.getChildren().addAll(username, regisAcc, acceptName);
        horiRoot.setAlignment(Pos.CENTER);
  
        VBox fillBox = new VBox();
        fillBox.getChildren().addAll(horiRoot, horiRootPass, horiRootAccount, warningLabel, ageVerify, horiRootButton);
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

    void createLoginScreen(){
        // object
        Label titleLabel = this.showTitleTemplate();
  
        // label
        Label username = new Label("Username: ");
  
        TextField regisAcc = new TextField();
        regisAcc.setPromptText("Enter your username");
        regisAcc.setPrefWidth(200);;
  
        Label acceptName = new Label("✘   ");
        acceptName.setTextFill(Color.RED);
  
        Label pass = new Label("Password: ");
  
        TextField regisPass = new TextField();
        regisPass.setPromptText("Enter your password");
        regisPass.setPrefWidth(200);
  
        Label acceptPass = new Label("✘   ");
        acceptPass.setTextFill(Color.RED);

        Label warningLabel = new Label();
        warningLabel.setVisible(false);
        warningLabel.setTextFill(Color.ORANGERED);

        Button logInBtn = new Button("Log in");
        logInBtn.setOnAction(e -> {
            if (this.control.stringNotNull(regisAcc.getText()) && this.control.stringNotNull(regisPass.getText())){
                if (this.model.loginAcct(regisAcc.getText(), regisPass.getText())){
                    this.getSpecificScene("menu");
                    this.createGreetingWindow();
                }
                else {
                    warningLabel.setTextFill(Color.ORANGERED);
                    warningLabel.setText("<!> Account is not found!");
                    warningLabel.setVisible(true);
                }
            } else {
                warningLabel.setTextFill(Color.ORANGERED);
                warningLabel.setText("<!> fill in all box");
                warningLabel.setVisible(true);
            }

            regisAcc.clear();
            regisPass.clear();
        });
        this.buttonAnimation(logInBtn);

        // textfield check
        regisAcc.textProperty().addListener((observable) -> {
            if (this.control.stringNotNull(regisAcc.getText())){
                acceptName.setText("✔   ");
                acceptName.setTextFill(Color.FORESTGREEN);
            };
        });
        regisPass.textProperty().addListener((observable) -> {
            if (this.control.stringNotNull(regisPass.getText())){
                acceptPass.setText("✔   ");
                acceptPass.setTextFill(Color.FORESTGREEN);
            };
        });
  
  
        // fill in first V box
 
        int paddingLeft = 17;
  
        HBox horiRootButton = new HBox();
        horiRootButton.getChildren().addAll(logInBtn);
        horiRootButton.setAlignment(Pos.CENTER);
        horiRootButton.setTranslateX(90);
  
        HBox horiRootPass = new HBox(paddingLeft);
        horiRootPass.getChildren().addAll(pass, regisPass, acceptPass);
        horiRootPass.setAlignment(Pos.CENTER);
  
        HBox horiRoot = new HBox(paddingLeft);
        horiRoot.getChildren().addAll(username, regisAcc, acceptName);
        horiRoot.setAlignment(Pos.CENTER);
  
        VBox fillBox = new VBox();
        fillBox.getChildren().addAll(horiRoot, horiRootPass, warningLabel, horiRootButton);
        fillBox.setAlignment(Pos.TOP_CENTER);
        fillBox.setPrefHeight(260);
        fillBox.setMaxWidth(320);
        fillBox.setSpacing(20);
  
        int paddingTop = 30;
        VBox root = new VBox(paddingTop);
        root.getChildren().addAll(titleLabel, fillBox);
        root.setAlignment(Pos.TOP_CENTER);
 
        this.scenes.put("login", new Scene(root, ScreenWidth, ScreenHeight));
    }

    void createMenuScreen(){
        // object
        Label titleLabel = this.showTitleTemplate();

        int paddingTop = 30;
        VBox root = new VBox(paddingTop);
        root.getChildren().addAll(titleLabel);
        root.setAlignment(Pos.TOP_CENTER);

        this.scenes.put("menu", new Scene(root, ScreenWidth, ScreenHeight));
    }
    
    // all animations

    void buttonAnimation(Button button){
        button.setStyle("-fx-background-color: #7AB2D3");
        button.setTextFill(Color.WHITE);
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: white"); 
            button.setTextFill(Color.BLACK);
        });
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: #7AB2D3"); 
            button.setTextFill(Color.WHITE);
        });
    }

    void multipleButtonAnimation(Button button1, Button button2){
        this.buttonAnimation(button1);
        this.buttonAnimation(button2);
    }

    void setLabelFont(Label label, int size){
        label.setFont(Font.font("MonoSpace", FontWeight.EXTRA_BOLD, size));
    }

    // accesor for all scenes
    public Scene getRegisScene(){
        return this.scenes.get("register");
    }

    public void getSpecificScene(String key){
        this.primaryStage.setScene(this.scenes.get(key));
    }
}
