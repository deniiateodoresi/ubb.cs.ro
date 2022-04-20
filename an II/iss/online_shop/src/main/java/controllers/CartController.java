package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Client;
import model.Product;
import model.ShoppingCart;
import repo.Repository;

import java.io.IOException;

public class CartController {
    public TableView cartProducts;
    public TableColumn name;
    public TableColumn size;
    public TableColumn category;
    public TableColumn price;
    public Label nrProducts;
    public Label totalPrice;

    private Repository repo;
    private Stage root;
    private ShoppingCart cart;
    private Client client;
    private final ObservableList<Product> model = FXCollections.observableArrayList();

    public void setCartController(Client client, Repository repo, Stage root, ShoppingCart cart) {
        this.client = client;
        this.repo = repo;
        this.root = root;
        this.cart = cart;
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
}
