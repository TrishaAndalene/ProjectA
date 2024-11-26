// ------------------------- JAVAFX LIBRARY ---------------------------
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

    public HashMap<String, Scene> scenes;
    protected Stage primaryStage;
    protected AppModel model;
    protected AppController control;
    protected ObservableList<Purchase> buyerCart;
    protected Label itemNum, itemCost;
    int count;
    protected SimpleIntegerProperty itemCount;
    protected SimpleDoubleProperty totalPriceCost;

    public AppView(AppModel model, AppController control){
        this.scenes = new HashMap<>();
        this.primaryStage = null;
        this.model = model;
        this.control = control;

        //misc attribute
        this.buyerCart = FXCollections.observableArrayList(this.model.checkIfBuyer().cart);
        this.itemNum = new Label("");
        this.itemCost = new Label("");
        this.itemCount = new SimpleIntegerProperty(0);
        this.totalPriceCost = new SimpleDoubleProperty(0);

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

    public void createProductWindow(Product item) throws FileNotFoundException{

        // stage
        Stage itemDetailStage = new Stage();
        itemDetailStage.setTitle("Item description");

        Label titleLabel = this.showTitleTemplate();

        // Logo
        
        FileInputStream filename = new FileInputStream("C:\\Users\\user\\OneDrive - UTS\\UTS Diploma Material\\Programming 2\\Project B\\polar_brand.png");
        Image image = new Image(filename);

        ImageView imgView = new ImageView(image);
        imgView.setFitHeight(80);
        imgView.setFitWidth(80);
        imgView.setPreserveRatio(true);

        titleLabel.setGraphic(imgView);
        this.setLabelFont(titleLabel, 10);

        // product printing
        Label itemName = new Label(item.getName().getValue());
        this.setLabelFont(itemName, 14);

        Label price = new Label("Selling price: A$" + item.getPrice().getValue());
        this.setLabelFont(price, 14);

        Label stock = new Label("Stock:               " + item.getStock().getValue());
        this.setLabelFont(stock, 14);

        Label sellerName = new Label("Seller:           NULL");
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

        Label deleteQuestion = new Label("Do you want to delete this from your cart?");
        this.setLabelFont(deleteQuestion, 16);
        
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
        
        FileInputStream filename = new FileInputStream("C:\\Users\\user\\OneDrive - UTS\\UTS Diploma Material\\Programming 2\\Project B\\brand.png");
        Image image = new Image(filename);

        ImageView imgView = new ImageView(image);
        imgView.setFitHeight(100);
        imgView.setFitWidth(100);
        imgView.setPreserveRatio(true);
        titleLabel.setGraphic(imgView);

        imgView.setTranslateX(-20);
        imgView.setTranslateY(10);
        titleLabel.setTranslateY(-10);

        //Tabs for Customer Options
        TabPane customerOptions = new TabPane();
        
        //Tabs
        Tab catalogue = new Tab("Catalogue");
        catalogue.setContent(this.createCatalogueRootScene());

        Tab shoppingCart = new Tab("Shopping Cart");
        shoppingCart.setContent(this.createCartRootScene());
        Tab profile = new Tab("Profile");
        Tab logOut = new Tab("Log Out");

        //Tab addition
        customerOptions.getTabs().addAll(catalogue, shoppingCart, profile, logOut);
        
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

        // sample data
        ObservableList<Product> sampleData = FXCollections.observableArrayList(
                new Product("Horse", 500, Category.ELECTRONIC, 4),
                new Product("Sheep", 200, Category.BATHROOOM, 2),
                new Product("Cow", 400, Category.EVENT, 7));
        
        // set view table
        catalogue.setItems(sampleData);
        catalogue.setTranslateY(30);

        VBox root = new VBox();
        root.setPrefWidth(700);
        root.setPrefHeight(350);
        root.getChildren().addAll(buttonBox, catalogue);

        return root;

    }

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

        TableColumn<Purchase, Double> totalPriceCol = new TableColumn<>("TotalPrice");
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
        Label branding = new Label("MyCart");
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
        return this.scenes.get("menu");
    }

    public void getSpecificScene(String key){
        this.primaryStage.setScene(this.scenes.get(key));
    }
}
