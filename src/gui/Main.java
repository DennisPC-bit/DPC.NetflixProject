package gui;

import dal.FilmDAO;
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
        FilmDAO dao = new FilmDAO();
        dao.getAllFilms();
        launch(args);
    }
}
