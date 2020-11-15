package gui;

import be.Film;
import be.FilmRating;
import be.User;
import bll.FilmManager;
import bll.SearchTool;
import bll.UserManager;
import dal.FilmDAO;
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
        SearchTool searchTool = new SearchTool();
        searchTool.binarySearch(0,100,69,10);
        launch(args);
    }
}
