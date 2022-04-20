package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Client;
import repo.Repository;

import java.io.IOException;

public class LoginUserController {

    public TextField username;
    public TextField password;

    private Stage root;
    private Repository repo;

    public void setLoginController(Repository repo, Stage root){
        this.repo = repo;
        this.root = root;
    }

    public void handleClientLogin(ActionEvent actionEvent) {
        if(username.getText().equals("")){
            //loginErrorLabel.setText("Username cannot be empty!");
        }
        else {
            String email = username.getText();
            String passwd = password.getText();
            Client client = repo.getClient(email, passwd);
            if(client == null) {
                //loginErrorLabel.setText("We couldn't find that username!");
                username.setText(null);
                password.setText(null);
                return;
            }
            else {
                setClient(client, actionEvent);
            }
        }
        username.setText(null);
        password.setText(null);
    }

    public void setClient(Client client, ActionEvent actionEvent)
    {
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

    public void handleAdminLogin(ActionEvent actionEvent) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("admin-login.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());

            LoginAdminController loginAdminController = fxmlLoader.getController();
            loginAdminController.setLoginController(repo, stage);
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
