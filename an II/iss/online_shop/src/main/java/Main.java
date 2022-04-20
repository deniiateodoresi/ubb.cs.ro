import controllers.LoginUserController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repo.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main extends Application {
    public static void main(String[] args) { launch(args);}

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Hello World");
        Properties props=new Properties();
        InputStream is = Main.class.getResourceAsStream("bd.properties");
        if(is != null) {
            try {
                props.load(is);
            } catch (IOException e) {
                System.out.println("Cannot find bd.properties "+e);
            }
        }
        String url=props.getProperty("jdbc.url");
        System.out.println(url);
        Repository repo = new Repository(props);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        LoginUserController loginController = fxmlLoader.getController();
        loginController.setLoginController(repo, primaryStage);

        System.out.println("Hello World");
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Online-Shop");
    }
}
