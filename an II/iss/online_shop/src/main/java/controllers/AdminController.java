package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Administrator;
import model.Product;
import repo.Repository;

public class AdminController {

    public TableColumn name;
    public TableColumn category;
    public TableColumn quantity;
    public TableColumn price;
    public TableColumn size;
    public TableView productTable;
    private Administrator admin;
    private Repository repo;
    private Stage root;
    private final ObservableList<Product> model = FXCollections.observableArrayList();


    public void loadAdmin(Administrator admin, Repository repo, Stage root){
        this.admin = admin;
        this.repo = repo;
        this.root = root;
        fillProductList();
    }

    public void fillProductList(){
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
}
