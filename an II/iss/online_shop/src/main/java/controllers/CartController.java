package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import repo.Repository;
import repo.Validator;

import java.io.IOException;
import java.time.LocalDate;

public class CartController {
    public TableView cartProducts;
    public TableColumn name;
    public TableColumn size;
    public TableColumn category;
    public TableColumn price;
    public Label nrProducts;
    public Label totalPrice;
    public TextField judet;
    public TextField localitate;
    public TextField strada;
    public TextField numarStrada;
    public TextField numarCard;
    public DatePicker dataExpirare;
    public TextField codValidare;

    private Repository repo;
    private Stage root;
    private ShoppingCart cart;
    private Client client;
    private Validator validator;
    private BankCard card = null;
    private Address address = null;
    private final ObservableList<Product> model = FXCollections.observableArrayList();

    public void setCartController(Client client, Repository repo, Stage root, ShoppingCart cart) {
        this.client = client;
        this.repo = repo;
        this.root = root;
        this.cart = cart;
        this.validator = new Validator();
        nrProducts.setVisible(true);
        nrProducts.setText(cart.getProductNumber().toString());
        totalPrice.setVisible(true);
        totalPrice.setText(cart.getTotalPrice() + " lei");
        fillCartProducts();
    }

    public void fillCartProducts(){
        model.setAll(cart.getProducts());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setStyle("-fx-alignment: CENTER; ");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        price.setStyle("-fx-alignment: CENTER; ");
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        size.setStyle("-fx-alignment: CENTER; ");
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        category.setStyle("-fx-alignment: CENTER; ");
        cartProducts.setItems(model);
    }

    public void handleBackToCatalogClick(ActionEvent actionEvent) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("client-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());

            ClientController clientController = fxmlLoader.getController();
            clientController.loadClient(client, repo, stage);

            root.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        stage.setTitle("Online shop");
        stage.sizeToScene();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void handlePlaceOrder(ActionEvent actionEvent) {
        if(address == null || card == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Online Shop");
            alert.setHeaderText("Datele de livrare si de plata nu sunt completate/sunt completate incorect.");
            alert.showAndWait();
        }
        else {
            Order order = new Order(address, card, cart);
            repo.saveOrder(order);
            repo.updateProductsQuantity(cart.getProducts());
            emptyCart();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Online Shop");
            alert.setHeaderText("Comanda a fost plasata cu succes.");
            alert.showAndWait();
        }
    }

    private void emptyCart(){
        cart.setProductNumber(-1);
        repo.updateCart(cart);
        cartProducts.getItems().clear();
        nrProducts.setText("0");
        totalPrice.setText("0 lei");
    }

    public void handleSaveAddress(ActionEvent actionEvent) {
        Boolean isAddressOk;
        try {
            String county = judet.getText();
            String city = localitate.getText();
            String street = strada.getText();
            Integer number = Integer.valueOf(numarStrada.getText());

            address = new Address(county, city, street, number);

            isAddressOk = validator.validateAddress(address);
        }
        catch(NumberFormatException e){
            isAddressOk = false;
        }

        if(isAddressOk) {
            repo.saveAddress(address);
            address.setId(repo.getMaxIDAddress());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Online Shop");
            alert.setHeaderText("Adresa a fost salvata cu succes.");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Online Shop");
            alert.setHeaderText("Adresa invalida.");
            alert.showAndWait();
        }
    }

    public void handleSaveCard(ActionEvent actionEvent) {
        String cardNumber = numarCard.getText();
        LocalDate expirationDate = dataExpirare.getValue();
        String validationCode = codValidare.getText();

        card = new BankCard(cardNumber, expirationDate, validationCode);
        Boolean isBankCardOk = validator.validateCard(card);

        if(isBankCardOk) {
            repo.saveCard(card);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Online Shop");
            alert.setHeaderText("Cardul a fost salvat cu succes.");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Online Shop");
            alert.setHeaderText("Card invalid.");
            alert.showAndWait();
        }
    }
}
