package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Administrator;
import model.Category;
import model.Product;
import model.Size;
import repo.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminController {

    public TableColumn<Product, String> name;
    public TableColumn<Product, Category> category;
    public TableColumn<Product, Integer> quantity;
    public TableColumn<Product, Float> price;
    public TableColumn<Product, Size> size;
    public TableView<Product> productTable;
    public Button deleteButton;
    public Button updateButton;
    public Button addButton;
    public ComboBox<Category> categories;
    public ComboBox<Size> sizes;
    public TextField productQuantity;
    public TextField productPrice;
    public TextField productName;
    private Administrator admin;
    private Repository repo;
    private Stage root;
    private Product selectedProduct;
    private final ObservableList<Product> model = FXCollections.observableArrayList();


    public void loadAdmin(Administrator admin, Repository repo, Stage root){
        this.admin = admin;
        this.repo = repo;
        this.root = root;
        fillProductList();
        setComboBoxes();
    }    

    private void fillProductList(){
        model.setAll(repo.findAll());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setStyle("-fx-alignment: CENTER; ");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        price.setStyle("-fx-alignment: CENTER; ");
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        size.setStyle("-fx-alignment: CENTER; ");
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantity.setStyle("-fx-alignment: CENTER; ");
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        category.setStyle("-fx-alignment: CENTER; ");
        productTable.setItems(model);
    }
    
    private void setComboBoxes(){
        List<Size> sizeList = new ArrayList<>();
        sizeList.add(Size.XS);
        sizeList.add(Size.S);
        sizeList.add(Size.M);
        sizeList.add(Size.L);
        sizeList.add(Size.XL);
        sizeList.add(Size.XXL);
        ObservableList<Size> sizeObservableList = FXCollections.observableArrayList();
        sizeObservableList.setAll(sizeList);
        sizes.setItems(sizeObservableList);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(Category.KIDS);
        categoryList.add(Category.WOMEN);
        categoryList.add(Category.MEN);
        ObservableList<Category> categoryObservableList = FXCollections.observableArrayList();
        categoryObservableList.setAll(categoryList);
        categories.setItems(categoryObservableList);
    }

    public void handleUpdateProduct(ActionEvent actionEvent) {
        String updateName = productName.getText();
        Category updatedCategory = categories.getValue();
        Size updatedSize = sizes.getValue();
        Float updatedPrice = Float.valueOf(productPrice.getText());
        Integer updatedQuantity = Integer.valueOf(productQuantity.getText());
        Product updatedProduct = new Product(updateName, updatedCategory, updatedSize, updatedPrice, updatedQuantity);
        repo.updateProduct(selectedProduct, updatedProduct);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Online Shop");
        alert.setHeaderText("Produsul selectat a fost actualizat.");
        alert.showAndWait();

        refreshList();
    }

    public void handleAddProduct(ActionEvent actionEvent) {
        String name = productName.getText();
        Category category = categories.getValue();
        Size size = sizes.getValue();
        Float price = Float.valueOf(productPrice.getText());
        Integer quantity = Integer.valueOf(productQuantity.getText());
        Product product = new Product(name, category, size, price, quantity);
        repo.addProduct(product);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Online Shop");
        alert.setHeaderText("Produsul a fost adaugat in magazinul online.");
        alert.showAndWait();

        refreshList();
    }

    private void refreshList(){
        fillProductList();
    }

    public void handleDeleteProduct(ActionEvent actionEvent) {
        repo.deleteProduct(selectedProduct);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Online Shop");
        alert.setHeaderText("Produsul a fost sters din magazinul online.");
        alert.showAndWait();

        refreshList();
    }

    public void handleSelectedProduct(MouseEvent mouseEvent) {
        selectedProduct = productTable.getSelectionModel().getSelectedItem();
        deleteButton.setDisable(false);
        updateButton.setDisable(false);
        productName.setText(selectedProduct.getName());
        productPrice.setText(String.valueOf(selectedProduct.getPrice()));
        productQuantity.setText(String.valueOf(selectedProduct.getQuantity()));
        categories.setValue(selectedProduct.getCategory());
        sizes.setValue(selectedProduct.getSize());
    }
}
