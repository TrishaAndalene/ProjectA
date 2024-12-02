// ------------------------- JAVAFX LIBRARY ---------------------------
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import java.io.FilterInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
// ---------------------------- JAVA LIBRARY ----------------------------
import java.util.*;

import javax.swing.text.TableView.TableRow;

public class AppView {
    
    final static int ScreenWidth = 700;
    final static int ScreenHeight = 450;

    TabPane customerOptions = new TabPane();

    public HashMap<String, Scene> scenes;
    protected Stage primaryStage;
    protected AppModel model;
    protected AppController control;
    protected ObservableList<Purchase> buyerCart;
    protected Label itemNum, itemCost, name, password, accountType, balance;
    protected ObservableList<Product> sellerCatalogue, catalogue;
    int count;
    protected SimpleIntegerProperty itemCount;
    protected SimpleDoubleProperty totalPriceCost;
    protected Tab shoppingCart, cataloguePane;

    public AppView(AppModel model, AppController control){
        this.scenes = new HashMap<>();
        this.primaryStage = null;
        this.model = model;
        this.control = control;

        //misc attribute
        this.catalogue = FXCollections.observableArrayList(this.model.generateCatalogue()); // dummy data
        
        this.buyerCart = FXCollections.observableArrayList(this.model.checkIfBuyer().cart);

        try {
            this.sellerCatalogue = FXCollections.observableArrayList(this.model.checkIfSeller().getSellerCatalogue());
        } catch (Exception e){
            // pass
        }

        this.itemNum = new Label("");
        this.itemCost = new Label("");
        this.itemCount = new SimpleIntegerProperty(0);
        this.totalPriceCost = new SimpleDoubleProperty(0);

        this.name = new Label();
        this.password = new Label();
        this.accountType = new Label();
        this.balance = new Label();
        this.shoppingCart = new Tab("Shopping Cart");

        // trigger to for scenes
        this.createRegisScreen();
        this.createLoginScreen();
        try {
            this.createMenuScreen();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

    //cart confirmation window
    public void createPurchaseFinalizationWindow(int i){
        //initialize a stage
        Stage userVerif = new Stage();
        userVerif.setTitle("Cart Status Window");

        //Cart status
        Label title = new Label();
        this.setLabelFont(title, 16);
        
        //Message
        Label body = new Label();
        this.setLabelFont(body, 14);
        
        if (i == 1){
            title.setText("Purchase Succesful");
            body.setText("Thank you for purchasing");
        } else {
            title.setText("Purchase Failed");
            body.setText("Please add more balance");
        }

        //button
        Button confirmButton = new Button("Confirm");
        this.buttonAnimation(confirmButton);
        confirmButton.setMinWidth(150);
        confirmButton.setOnAction(e -> {
            userVerif.close();
        });

        //HBoxes and VBoxes
        VBox root = new VBox();
        root.getChildren().addAll(title, body, confirmButton);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        
        //close the menu
        userVerif.setScene(new Scene(root, 250, 200));
        userVerif.show();
    }

    public void createUserVerificationWindow(){
        Stage userVerif = new Stage();
        userVerif.setTitle("User Verification");

        Label title = new Label("User Verification Form");
        this.setLabelFont(title, 16);

        Label enterPass = new Label("Enter password:");
        this.setLabelFont(enterPass, 14);

        TextField password = new TextField();
        password.setMaxWidth(150);

        Button cont = new Button("Submit");
        cont.setMinWidth(150);
        this.buttonAnimation(cont);
        cont.setOnAction(e -> {
            if (this.model.currentBuyer.checkPassword(password.getText())){
                this.createAddBalanceWindow();
                userVerif.close();
            }
        });

        // layout
        VBox root = new VBox();
        root.getChildren().addAll(title, enterPass, password, cont);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        
        userVerif.setScene(new Scene(root, 250, 200));
        userVerif.show();
    }

    public void createAddBalanceWindow(){

        //stage
        Stage addBalance = new Stage();
        addBalance.setTitle("Balance editing");

        // labelling
        Label title = new Label("Balance Top-up");
        this.setLabelFont(title, 16);

        Label enterBalance = new Label("Enter top-up value");
        this.setLabelFont(enterBalance, 14);

        //Button for and textfield for adding balance
        TextField balanceEntry = new TextField();
        balanceEntry.setMaxWidth(150);

        String[] paymentType = {"Card", "GiftCard", "Online banking"};
        ComboBox paymentList = new ComboBox(FXCollections.observableArrayList(paymentType));
        paymentList.getSelectionModel().select(0);

        Button balanceButton = new Button("Add");
        balanceButton.setMinWidth(150);
        this.buttonAnimation(balanceButton);

        //functionality
        balanceButton.setOnAction(e -> {
            this.model.checkIfBuyer().addBalance(this.control.convertStringToDouble(balanceEntry.getText()));
            this.changeUser(this.model.currentBuyer);
            addBalance.close();
        });

        //alignment
        VBox root = new VBox();
        root.getChildren().addAll(enterBalance, balanceEntry, paymentList, balanceButton);
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);

        addBalance.setScene(new Scene(root, 250, 200));
        addBalance.show();

    }

    public void createProductWindow(Product item) throws FileNotFoundException{

        // stage
        Stage itemDetailStage = new Stage();
        itemDetailStage.setTitle("Item description");

        Label titleLabel = this.showTitleTemplate();

        // Logo
        
        FileInputStream filename = new FileInputStream("polar_brand.png");
        Image image = new Image(filename);

        ImageView imgView = new ImageView(image);
        imgView.setFitHeight(80);
        imgView.setFitWidth(80);
        imgView.setPreserveRatio(true);

        titleLabel.setGraphic(imgView);
        this.setLabelFont(titleLabel, 10);

        // product printing
        Label itemName = new Label("Name:    " + item.getName().getValue());
        this.setLabelFont(itemName, 14);

        Label price = new Label("Price:   A$ " + item.getPrice().getValue());
        this.setLabelFont(price, 14);

        Label stock = new Label("Stock:   " + item.getStock().getValue());
        this.setLabelFont(stock, 14);

        Label sellerName = new Label("Seller:  " + item.getSellerName());
        this.setLabelFont(sellerName, 14);

        // button
        this.count = this.model.checkIfBuyer().checkCount(item.getName().getValue());

        Button plus = new Button("+");

        Button minus = new Button("-");

        Label quantity = new Label(""+this.count);

        plus.setOnAction(e -> {
            if (count < item.getStock().getValue()){
                count += 1;
                quantity.setText("" + count);
            } else {
                // pass first
            }
        });

        minus.setOnAction(e -> {
            if (count != 0){
                count -= 1;
                quantity.setText("" + count);
            } else {
                // pass first
            }
        });

        Button addCart = new Button("Add to Cart");

        addCart.setOnAction(e -> {
            try{
                Purchase p = new Purchase(item, count);
                if (this.count != 0){
                    try {
                        int index = this.model.checkIfBuyer().checkPurchase(item.getName().getValue(), count);
                        System.out.println("ok");
                        this.buyerCart.set(index, p);
                    } catch (Exception w){
                        System.err.println(w);
                        this.model.checkIfBuyer().addToCart(item, count);
                        System.out.println(this.model.checkIfBuyer().cart);

                        this.buyerCart.add(p);
                    }
                }
                this.itemCount.set(this.model.checkIfBuyer().checkCart());
                this.totalPriceCost.set(this.model.checkIfBuyer().getTotalPriceCart());
                itemDetailStage.close();
            } catch (Exception e1){
                System.out.println(e1);
            }
        });

        // layouting

        VBox itemDetails = new VBox();
        itemDetails.getChildren().addAll(itemName, price, stock, sellerName);
        itemDetails.setAlignment(Pos.TOP_LEFT);
        itemDetails.setTranslateX(80);
        itemDetails.setTranslateY(15);

        HBox buttons = new HBox();
        buttons.getChildren().addAll(minus, quantity, plus, addCart);
        buttons.setSpacing(20);
        buttons.setTranslateY(40);
        buttons.setAlignment(Pos.CENTER);

        VBox root = new VBox();
        root.getChildren().addAll(titleLabel, itemDetails, buttons);

        // new window
        itemDetailStage.setScene(new Scene(root, 350, 250));

        // show the new window
        itemDetailStage.show();

    }

    public void createDeleteWindow(Purchase item){
        Stage deleteWindow = new Stage();
        deleteWindow.setTitle("Deletion Form");

        Label deleteQuestion = new Label("Do you want to delete \nthis from your cart?");
        this.setLabelFont(deleteQuestion, 14);
        
        Label warningLabel = new Label("<!> please make sure of the item");
        this.setLabelFont(warningLabel, 10);
        warningLabel.setTextFill(Color.ORANGERED);

        Button yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            this.model.checkIfBuyer().removeFromCart(item);
            this.buyerCart.remove(item);
            this.itemCount.set(this.model.checkIfBuyer().checkCart());
            this.totalPriceCost.set(this.model.checkIfBuyer().getTotalPriceCart());
            deleteWindow.close();
        });

        Button noButton = new Button("No");
        noButton.setOnAction(e -> {
            deleteWindow.close();
        });

        this.multipleButtonAnimation(yesButton, noButton);

        // box creation
        HBox btnBox = new HBox();
        btnBox.getChildren().addAll(yesButton, noButton);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(50);
        
        VBox windowBox = new VBox();
        windowBox.getChildren().addAll(deleteQuestion, warningLabel, btnBox);
        windowBox.setAlignment(Pos.CENTER);
        windowBox.setSpacing(20);

        // new window
        deleteWindow.setScene(new Scene(windowBox, 350, 150));

        deleteWindow.setOnCloseRequest(e -> {
            javafx.application.Platform.exit();
        });

        // show the new window
        deleteWindow.show();
    }

    public void createUpdateUsernameWindow(){
        Stage updateName = new Stage();
        updateName.setTitle("User Verification");

        Label title = new Label("Update Username Form");
        this.setLabelFont(title, 16);

        Label enterName = new Label("Enter new username:");
        this.setLabelFont(enterName, 14);

        TextField username = new TextField();
        username.setMaxWidth(150);

        Button cont = new Button("Submit");
        cont.setMinWidth(150);
        this.buttonAnimation(cont);
        cont.setOnAction(e -> {
            if (this.control.stringNotNull(username.getText())){
                this.model.currentBuyer.editUserName(username.getText());
                this.changeUser(this.model.currentBuyer);
                updateName.close();
            }
        });

        // layout
        VBox root = new VBox();
        root.getChildren().addAll(title, enterName, username, cont);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        
        updateName.setScene(new Scene(root, 250, 200));
        updateName.show();
    }

    public void createEditProductWindow(Product item, int index){
        Stage editWindow = new Stage();
        editWindow.setTitle("Item Customization");

        Label itemNamelabel = new Label("Product name:");
        this.setLabelFont(itemNamelabel, 16);

        TextField itemNameInput = new TextField();
        itemNameInput.setText("" + item.getName().getValue());
        itemNameInput.setMaxWidth(100);

        Label itemPrice = new Label("Price: ");
        this.setLabelFont(itemPrice, 16);

        TextField itemPriceInput = new TextField();
        itemPriceInput.setText("" + item.getPrice().getValue());
        itemPriceInput.setMaxWidth(100);

        Label itemStock = new Label("Stock: ");
        this.setLabelFont(itemStock, 16);

        TextField itemStockInput = new TextField();
        itemStockInput.setMaxWidth(100);
        itemStockInput.setText("" + item.getStock().getValue());

        // floating button
        Button save = new Button("Save");
        save.setOnAction(e -> {
            item.editProductDetails(itemNameInput.getText(), this.control.convertStringToDouble(itemPriceInput.getText()), this.control.convertStringToInt(itemStockInput.getText()));
            this.catalogue.set((item.getProductID().getValue()-1), item);
            this.sellerCatalogue.set(index, item);
            editWindow.close();
        });


        Button delete = new Button("Delete");
        delete.setOnAction(e -> {
            this.sellerCatalogue.remove(item);
            this.catalogue.remove(item);
            this.model.checkIfSeller().deleteProduct(item);
            System.out.println(this.sellerCatalogue);
            editWindow.close();
        });

        this.multipleButtonAnimation(save, delete);

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(save, delete);
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.CENTER);

        VBox root = new VBox();
        root.getChildren().addAll(itemNamelabel, itemNameInput, itemPrice, itemPriceInput, itemStock, itemStockInput, buttonBox);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);

        editWindow.setScene(new Scene(root, 300, 300));
        editWindow.show();
        
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
            } else {
                acceptName.setText("✘   ");
                acceptName.setTextFill(Color.RED);
            };
        });
        regisPass.textProperty().addListener((observable) -> {
            if (this.control.stringNotNull(regisPass.getText())){
                acceptPass.setText("✔   ");
                acceptPass.setTextFill(Color.FORESTGREEN);
            } else {
                acceptPass.setText("✘   ");
                acceptPass.setTextFill(Color.RED);
            };
        });
  
        Label accountType = new Label("Account type:   ");
 
        ToggleGroup toggleAccountCreateGroup = new ToggleGroup();
        RadioButton sellerBtn = new RadioButton("Seller");
        sellerBtn.setToggleGroup(toggleAccountCreateGroup);
 
        RadioButton buyerBtn = new RadioButton("Buyer");
        buyerBtn.setToggleGroup(toggleAccountCreateGroup);

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
                    buyerBtn.setSelected(false);
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
        horiRootAccount.getChildren().addAll(accountType, buyerBtn, sellerBtn);
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
                    this.changeUser(this.model.currentBuyer);
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
            } else {
                acceptName.setText("✘   ");
                acceptName.setTextFill(Color.RED);
            };
        });
        regisPass.textProperty().addListener((observable) -> {
            if (this.control.stringNotNull(regisPass.getText())){
                acceptPass.setText("✔   ");
                acceptPass.setTextFill(Color.FORESTGREEN);
            } else {
                acceptPass.setText("✘   ");
                acceptPass.setTextFill(Color.RED);
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

    void createMenuScreen() throws FileNotFoundException{
        // Label and Logo
        Label titleLabel = this.showTitleTemplate();
        titleLabel.setTextFill(Color.WHITE);
        
        FileInputStream filename = new FileInputStream("brand.png");
        Image image = new Image(filename);

        ImageView imgView = new ImageView(image);
        imgView.setFitHeight(100);
        imgView.setFitWidth(100);
        imgView.setPreserveRatio(true);
        titleLabel.setGraphic(imgView);

        imgView.setTranslateX(-20);
        imgView.setTranslateY(10);
        titleLabel.setTranslateY(-10);
        
        //Tabs
        this.cataloguePane = new Tab("Catalogue");
        cataloguePane.setContent(this.createCatalogueRootScene());

        Tab profile = new Tab("Profile");
        profile.setContent(this.createProfileRootScene());

        Tab logOut = new Tab("Customer Service");

        //Tab addition
        customerOptions.getTabs().addAll(this.cataloguePane, this.shoppingCart, profile, logOut);
        
        customerOptions.setTabMinWidth(ScreenWidth/4.5);
        customerOptions.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        

        // Layout structure
        HBox headerBox = new HBox();
        headerBox.getChildren().addAll(titleLabel);
        headerBox.setAlignment(Pos.TOP_CENTER);
        headerBox.setStyle("-fx-background-color: black");
        headerBox.setPrefHeight(90);

        int paddingTop = 30;
        VBox root = new VBox();
        root.getChildren().addAll(headerBox, customerOptions);
        root.setAlignment(Pos.TOP_CENTER);

        this.scenes.put("menu", new Scene(root, ScreenWidth, ScreenHeight));
    }
    
    // create all tabbing panes scene
    VBox createCatalogueRootScene(){

        // button left
        String[] priceFilter = {"Highest to Lowest", "Lowest to Highest"};
        ComboBox priceSort = new ComboBox(FXCollections.observableArrayList(priceFilter));
        priceSort.getSelectionModel().select(0);

        // button right
        String[] categoryFilter = {"ANY", "FOOD", "BEVERAGE", "HOMEWARE", "ELECTRONIC", "TOYS", "FASHION", "OFFICE", "EVENT", "BATHROOOM"};
        ComboBox categorySort = new ComboBox(FXCollections.observableArrayList(categoryFilter));
        categorySort.getSelectionModel().select(0);

        // create the platform
        TilePane buttonLeftPlatform = new TilePane(priceSort);
        TilePane buttonRightPlatform = new TilePane(categorySort);

        // create layout
        //Buttons
        HBox priceBox = new HBox();
        priceBox.getChildren().addAll(buttonLeftPlatform);
        priceBox.setTranslateX(30);
        priceBox.setAlignment(Pos.TOP_LEFT);

        HBox categoryBox = new HBox();
        categoryBox.getChildren().addAll(buttonRightPlatform);
        categoryBox.setAlignment(Pos.TOP_RIGHT);
        categoryBox.setTranslateX(90);

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(priceBox, categoryBox);
        buttonBox.setSpacing(100);
        buttonBox.setTranslateY(20);
        
        //Listing items from catalogue
        TableView<Product> catalogue = new TableView<>();

        //columns and their resizing
        TableColumn<Product, String> productNameCol = new TableColumn<>("Products");
        productNameCol.setMinWidth(ScreenWidth/3);
        
        TableColumn<Product, Double> productPriceCol = new TableColumn<>("Price (A$)");
        productPriceCol.setMinWidth(ScreenWidth/3);

        TableColumn<Product, Integer> productStockCol = new TableColumn<>("Stock");
        productStockCol.setMinWidth(ScreenWidth/3);
        productStockCol.setSortable(false);
        
        productNameCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        productPriceCol.setCellValueFactory(cellData -> cellData.getValue().getPrice().asObject());
        productStockCol.setCellValueFactory(cellData -> cellData.getValue().getStock().asObject());

        catalogue.getColumns().addAll(productNameCol, productPriceCol, productStockCol);
        catalogue.setPrefSize(ScreenWidth, 300);
        
        catalogue.setOnMouseClicked(e -> {
            Product selectedProduct = catalogue.getSelectionModel().getSelectedItem();
            catalogue.getSelectionModel().clearSelection();
            try {
                if (selectedProduct != null){
                    this.createProductWindow(selectedProduct);
                }
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
        }
        });
        
        //set the catalogue class to the items of the menu
        catalogue.setItems(this.catalogue);
        catalogue.setTranslateY(30);

        VBox root = new VBox();
        root.setPrefWidth(700);
        root.setPrefHeight(350);
        root.getChildren().addAll(buttonBox, catalogue);

        return root;

    }

    // buyer root scene
    HBox createCartRootScene(){

        //Listing items from catalogue
        TableView<Purchase> cart = new TableView<>();

        //columns and their resizing
        TableColumn<Purchase, String> productNameCol = new TableColumn<>("Products");
        productNameCol.setMinWidth(100);
        productNameCol.setSortable(false);
        
        TableColumn<Purchase, Integer> productPriceQuantityCol = new TableColumn<>("Quantity");
        productPriceQuantityCol.setMinWidth(100);
        productPriceQuantityCol.setSortable(false);

        TableColumn<Purchase, Double> totalPriceCol = new TableColumn<>("Price");
        totalPriceCol.setMinWidth(100);
        totalPriceCol.setSortable(false);
        
        productNameCol.setCellValueFactory(cellData -> cellData.getValue().product.getName());
        productPriceQuantityCol.setCellValueFactory(cellData -> cellData.getValue().getQuantity().asObject());
        totalPriceCol.setCellValueFactory(cellData -> cellData.getValue().calculatePurchase().asObject());

        cart.getColumns().addAll(productNameCol, productPriceQuantityCol, totalPriceCol);
        cart.setMaxSize(302, 300);

        // table function
        cart.setOnMouseClicked(e -> {
            Purchase selectedProduct = cart.getSelectionModel().getSelectedItem();
            cart.getSelectionModel().clearSelection();
            if (selectedProduct != null){
                this.createDeleteWindow(selectedProduct);
            }
        });

        // sample data
        System.out.print("okay");
        
        // set view table
        cart.setItems(this.buyerCart);
        cart.setMaxWidth(302);
        cart.setTranslateY(10);
        cart.setTranslateX(30);

        // labels
        Label branding = new Label("My Cart");
        branding.setAlignment(Pos.CENTER);
        branding.setTranslateX(105);
        this.setLabelFont(branding, 18);

        this.itemNum.textProperty().bind(this.itemCount.asString("Total item:  %d"));
        this.setLabelFont(this.itemNum, 16);

        this.itemCost.textProperty().bind(this.totalPriceCost.asString("Total price: %.2f"));
        this.setLabelFont(this.itemCost, 16);

        Label breakLine = new Label("_____________________________");
        breakLine.setTranslateY(-20);
        this.setLabelFont(breakLine, 16);

        // function button
        Button finalize = new Button("Finalize");
        finalize.setMinWidth(280);
        finalize.setTranslateY(-20);
        this.buttonAnimation(finalize);
        //when finalizing, send all the cash to the correct sellers
        finalize.setOnMouseClicked(e -> {
            int checkingValue = this.model.checkIfBuyer().checkoutProduct(this.model.getUsers());
            //check if 1 (balance is enough) or 0 (balance isn't)
            if (checkingValue == 1){
                //recheck the value of the cart and item total, fix balance
                this.itemCount.set(this.model.checkIfBuyer().checkCart());
                this.totalPriceCost.set(this.model.checkIfBuyer().getTotalPriceCart());
                //update account profile's balance
                changeUser(this.model.checkIfBuyer());
                //add a popup window to indicate the sale has been succesful
                createPurchaseFinalizationWindow(checkingValue);
                cart.getItems().clear();
            } else {
                //provide an error message otherwise
                createPurchaseFinalizationWindow(checkingValue);
            }
        });

        // layouting
        VBox details = new VBox();
        details.getChildren().addAll(branding, this.itemNum, this.itemCost, breakLine, finalize);
        details.setTranslateX(80);
        details.setTranslateY(50);
        details.setSpacing(20);

        HBox root = new HBox();
        root.setPrefWidth(700);
        root.setPrefHeight(350);
        root.getChildren().addAll(cart, details);
        root.setAlignment(Pos.CENTER_LEFT);

        return root;
    }

    // seller root scene
    HBox createMyStockRootScene(){

        TableView<Product> sellerInventory = new TableView<>();

        //columns and their resizing
        TableColumn<Product, Integer> productIDCol = new TableColumn<>("ID");
        productIDCol.setMinWidth(60);
        productIDCol.setSortable(false);
        productIDCol.setResizable(false);
        productIDCol.setReorderable(false);

        TableColumn<Product, String> productNameCol = new TableColumn<>("Products");
        productNameCol.setMinWidth(60);
        productNameCol.setSortable(false);
        productNameCol.setResizable(false);
        productNameCol.setReorderable(false);
        
        TableColumn<Product, Integer> productPriceQuantityCol = new TableColumn<>("Quantity");
        productPriceQuantityCol.setMinWidth(60);
        productPriceQuantityCol.setSortable(false);
        productPriceQuantityCol.setResizable(false);
        productPriceQuantityCol.setReorderable(false);

        TableColumn<Product, Double> totalPriceCol = new TableColumn<>("Price");
        totalPriceCol.setMinWidth(60);
        totalPriceCol.setSortable(false);
        totalPriceCol.setResizable(false);
        totalPriceCol.setReorderable(false);
        
        productIDCol.setCellValueFactory(cellData -> cellData.getValue().getProductID().asObject());
        productNameCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        productPriceQuantityCol.setCellValueFactory(cellData -> cellData.getValue().getStock().asObject());
        totalPriceCol.setCellValueFactory(cellData -> cellData.getValue().getPrice().asObject());

        sellerInventory.getColumns().addAll(productIDCol, productNameCol, productPriceQuantityCol, totalPriceCol);
        sellerInventory.setMaxSize(402, 300);

        // table function
        sellerInventory.setOnMouseClicked(e -> {
            Product selectedProduct = sellerInventory.getSelectionModel().getSelectedItem();

            int index = sellerInventory.getSelectionModel().getSelectedIndex();

            sellerInventory.getSelectionModel().clearSelection();
            if (selectedProduct != null){
                this.createEditProductWindow(selectedProduct, index);
            }
        });
        
        // set view table
        sellerInventory.setItems(this.sellerCatalogue);
        sellerInventory.setTranslateY(10);
        sellerInventory.setTranslateX(30);

        // labels
        Label branding = new Label("My Catalogue");
        branding.setAlignment(Pos.CENTER);
        branding.setTranslateX(80);
        this.setLabelFont(branding, 18);

        Label itemNamelabel = new Label("Product name:");
        this.setLabelFont(itemNamelabel, 16);

        TextField itemNameInput = new TextField();
        itemNameInput.setPrefWidth(280);

        Label itemPrice = new Label("Price: ");
        this.setLabelFont(itemPrice, 16);

        TextField itemPriceInput = new TextField();
        itemPriceInput.setPrefWidth(280);

        Label itemStock = new Label("Stock: ");
        this.setLabelFont(itemStock, 16);

        TextField itemStockInput = new TextField();
        itemStockInput.setPrefWidth(280);

        // category option
        Label categoryLabel = new Label("Category:");
        this.setLabelFont(categoryLabel, 16);

        Category[] categoryOption = {Category.ANY, Category.FOOD, Category.BEVERAGE, Category.HOMEWARE, Category.ELECTRONIC, Category.TOYS, Category.FASHION, Category.OFFICE, Category.EVENT, Category.BATHROOOM};
        ComboBox categoryBox = new ComboBox(FXCollections.observableArrayList(categoryOption));
        categoryBox.getSelectionModel().select(0);

        // create the platform
        TilePane categoryPane = new TilePane(categoryBox);

        // function button
        Button finalize = new Button("Add to Catalogue");
        finalize.setMinWidth(280);
        finalize.setTranslateY(20);
        this.buttonAnimation(finalize);

        finalize.setOnAction(e -> {
            if (this.control.stringNotNull(itemNameInput.getText()) && this.control.stringNotNull(itemPriceInput.getText()) && this.control.stringNotNull(itemStockInput.getText())){
                Product item = new Product(itemNameInput.getText(), this.control.convertStringToDouble(itemPriceInput.getText()), categoryOption[(categoryBox.getSelectionModel().getSelectedIndex())], this.control.convertStringToInt(itemStockInput.getText()), this.model.checkIfSeller());

                System.out.println(item.getCategory());

                this.model.checkIfSeller().addProduct(item);

                System.out.println(this.model.checkIfSeller().sellerCatalogue);

                sellerInventory.setItems(FXCollections.observableArrayList(this.model.checkIfSeller().getSellerCatalogue()));

                this.sellerCatalogue.setAll(sellerInventory.getItems());
                //update the catalogue to reflect changes
                this.catalogue.add(item);

                itemNameInput.clear();
                itemPriceInput.clear();
                itemStockInput.clear();
                categoryBox.getSelectionModel().select(0);
                // this.catalogue.setItems(this.model.generateCatalogue());
            }
        });

        // layouting
        VBox details = new VBox();
        details.getChildren().addAll(branding, itemNamelabel, itemNameInput,itemPrice, itemPriceInput, itemStock, itemStockInput, categoryLabel, categoryBox,finalize);
        details.setSpacing(5);
        details.setTranslateX(70);
        details.setTranslateY(20);

        HBox root = new HBox();
        root.setPrefWidth(700);
        root.setPrefHeight(350);
        root.getChildren().addAll(sellerInventory, details);
        root.setAlignment(Pos.CENTER_LEFT);

        return root;
    }

    // neutral root scene
    HBox createProfileRootScene() throws FileNotFoundException{
        
        // left
        FileInputStream filename = new FileInputStream("placeholderProfile.jpg");
        Image image = new Image(filename);

        ImageView imgView = new ImageView(image);
        imgView.setFitWidth(200);
        imgView.setFitHeight(250);
        imgView.setTranslateX(-20);

        //right
        Label profileLabel = new Label("Account Profile");
        this.setLabelFont(profileLabel, 20);

        this.setLabelFont(this.name, 15);

        this.setLabelFont(this.password, 15);

        this.setLabelFont(this.balance, 15);

        this.setLabelFont(this.accountType, 15);

        Button addBalance = new Button("+");
        addBalance.setTranslateY(-5);
        addBalance.setTranslateX(10);
        addBalance.setOnAction(e -> {
            this.createUserVerificationWindow();
        });

        Button edit = new Button("Edit");
        edit.setMinWidth(280);
        edit.setOnAction(e -> {
            this.createUpdateUsernameWindow();
        });

        Button logOut = new Button();
        logOut.setGraphic(logOut);

        FileInputStream filepath = new FileInputStream("logOut.png");
        Image img = new Image(filepath);

        ImageView imgView2 = new ImageView(img);
        imgView2.setFitHeight(20);
        imgView2.setFitWidth(20);
        imgView2.setPreserveRatio(true);
        logOut.setGraphic(imgView2);

        FileInputStream filepath1 = new FileInputStream("logOut_polar.png");
        Image newImage = new Image(filepath1);

        this.buttonAnimation(logOut);
        logOut.setOnMouseEntered(e -> {
            logOut.setStyle("-fx-background-color: white"); 
            logOut.setTextFill(Color.BLACK);
            imgView2.setImage(newImage);
            logOut.setGraphic(imgView2);
        });
        logOut.setOnMouseExited(e -> {
            logOut.setStyle("-fx-background-color: black"); 
            logOut.setTextFill(Color.WHITE);
            imgView2.setImage(img);
            logOut.setGraphic(imgView2);
        });

        logOut.setOnAction(e -> {
            this.primaryStage.setScene(getRegisScene());
            this.createAccountManagerScreen();
        });

        this.multipleButtonAnimation(addBalance, edit);

        // layout
        HBox balanceBtn = new HBox();
        balanceBtn.getChildren().addAll(this.balance, addBalance);

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(edit, logOut);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(20);

        VBox profileDetails = new VBox();
        profileDetails.getChildren().addAll(profileLabel, this.name, this.password, balanceBtn, this.accountType, buttonBox);
        profileDetails.setAlignment(Pos.CENTER_LEFT);
        profileDetails.setSpacing(20);
        profileDetails.setTranslateX(30);

        HBox root = new HBox();
        root.getChildren().addAll(imgView, profileDetails);
        root.setAlignment(Pos.CENTER);
        
        return root;
    }

    //customer service
    HBox createCustomerServiceScene(){
        
        //Store Details
        Label userGuide = new Label();
        userGuide.setText("USER GUIDE");
        userGuide.setAlignment(Pos.CENTER);
        this.setLabelFont(userGuide, 16);

        Label userManual = new Label("This app sucks tbh, but if you want to use it anyway, do the following");
        

        //Alignment

        return null;
    }
    
    // all animations

    void buttonAnimation(Button button){
        button.setStyle("-fx-background-color: black");
        button.setTextFill(Color.WHITE);
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: white"); 
            button.setTextFill(Color.BLACK);
        });
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: black"); 
            button.setTextFill(Color.WHITE);
        });

        // color code : #7AB2D3
    }

    void multipleButtonAnimation(Button button1, Button button2){
        this.buttonAnimation(button1);
        this.buttonAnimation(button2);
    }

    void setLabelFont(Label label, int size){
        label.setFont(Font.font("MonoSpace", FontWeight.EXTRA_BOLD, size));
    }

    void changeUser(User u){
        customerOptions.getSelectionModel().select(this.cataloguePane);
        this.name.setText("Username: " + u.getUserName());
        this.password.setText("Password: " + u.getPasswordHash());
        this.balance.setText("Balance: A$" + u.getBalance());
        this.accountType.setText("Account type: " + u.getClass().getName());
        if (this.model.isSeller()){
            this.sellerCatalogue = FXCollections.observableArrayList(this.model.checkIfSeller().getSellerCatalogue());
            this.shoppingCart.setText("Storage");
            this.shoppingCart.setContent(this.createMyStockRootScene());   
        } else {
            this.buyerCart.setAll(this.model.checkIfBuyer().getCart());
            this.shoppingCart.setText("My Cart");
            this.shoppingCart.setContent(this.createCartRootScene());
        }
    }

    // accesor for all scenes
    public Scene getRegisScene(){
        return this.scenes.get("register");
    }

    public void getSpecificScene(String key){
        this.primaryStage.setScene(this.scenes.get(key));
    }
}

