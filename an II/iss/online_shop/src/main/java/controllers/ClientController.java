package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Client;
import model.Product;
import model.ShoppingCart;
import repo.Repository;

import java.io.IOException;

public class ClientController {
    public TableColumn<Product, String> name;
    public TableColumn<Product, String> category;
    public TableColumn<Product, Float> price;
    public TableColumn<Product, String> size;
    public TableColumn<Product, Integer> quantity;
    public TableView<Product> productTable;
    public Label selectionLabel;

    private ShoppingCart cart;
    private Client client;
    private Product selectedProduct;
    private Repository repo;
    private Stage root;
    private final ObservableList<Product> model = FXCollections.observableArrayList();

    public void loadClient(Client client,Repository repo,Stage stage){
        this.client = client;
        this.repo = repo;
        this.root = stage;
        fillProductList();
        this.cart = repo.initializeCart(client);
    }

    public void fillProductList(){
        model.setAll(repo.findAll());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setStyle("-fx-alignment: CENTER; ");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        price.setStyle("-fx-alignment: CENTER; ");
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        size.setStyle("-fx-alignment: CENTER; ");
        quantity.setCellValueFactory(new PropertyValueFactory<>("status"));
        quantity.setStyle("-fx-alignment: CENTER; ");
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        category.setStyle("-fx-alignment: CENTER; ");
        productTable.setItems(model);
    }

    public void handleTableClicked(MouseEvent mouseEvent) {
        selectedProduct = productTable.getSelectionModel().getSelectedItem();
        selectionLabel.setText("You've selected a product:\n" + selectedProduct.getName() + "\n" + selectedProduct.getCategory() + "\n" + selectedProduct.getSize());
    }


    public void handleAddToCartClick(ActionEvent actionEvent) {
        cart.add(selectedProduct);
        cart.setProductNumber(cart.getProductNumber() + 1);
        cart.setTotalPrice(cart.getTotalPrice() + selectedProduct.getPrice());
        repo.updateCart(cart);
        repo.addProductCartPair(selectedProduct, cart);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Online Shop");
        alert.setHeaderText("The selected product was added to cart.");
        alert.showAndWait();

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("cos-cumparaturi.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());

            CartController cartController = fxmlLoader.getController();
            cartController.setCartController(client, repo, stage, cart);

            root.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        //Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        //stage.initOwner(window);
        stage.setTitle("Online shop");
        stage.sizeToScene();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
