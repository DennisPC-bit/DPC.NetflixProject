package gui;

import be.Film;
import bll.UserManager;
import dal.UserDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("UserInterface.fxml"));
        primaryStage.setTitle("Netflix");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
        UserInterfaceController userInterfaceController = new UserInterfaceController();
        System.out.println(userInterfaceController.getAllUsers().get(0).getName());
    }
}
