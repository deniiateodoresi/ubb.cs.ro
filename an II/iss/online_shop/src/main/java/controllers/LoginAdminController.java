package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Administrator;
import model.Client;
import repo.Repository;

import java.io.IOException;

public class LoginAdminController {

    public TextField email;
    public TextField number;
    public TextField passwd;
    private Repository repo;
    private Stage root;

    public void setLoginController(Repository repo,Stage root){
        this.repo = repo;
        this.root = root;
    }

    public void handleAdminLogin(ActionEvent actionEvent) {
        if(email.getText().equals("")){
            //loginErrorLabel.setText("Username cannot be empty!");
        }
        else {
            Integer nr = Integer.valueOf(number.getText());
            String e = email.getText();
            String p = passwd.getText();
            Administrator admin = repo.getAdmin(nr, e, p);
            if(admin != null) {
                setAdmin(admin, actionEvent);
            }
        }
        email.setText(null);
        passwd.setText(null);
        number.setText(null);
    }

    private void setAdmin(Administrator admin, ActionEvent actionEvent) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("admin-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());

            AdminController adminController = fxmlLoader.getController();
            adminController.loadAdmin(admin, repo, stage);

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
