package gui;

import be.Film;
import gui.Dialogs.FilmDialogController;
import gui.Dialogs.LogInScreenController;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;

public class WindowOpener {
    private UserInterfaceController userInterfaceController;
    private String path;
    private Stage windowStage;

    public WindowOpener (UserInterfaceController userInterfaceController){
        this.userInterfaceController=userInterfaceController;
    }

    public Stage getWindowStage() {
        return windowStage;
    }

    public void loadWindow(String path){
        this.path=path;
    FXMLLoader loader = new FXMLLoader(Main.class.getResource(path));
        try {
        AnchorPane changeUser = loader.load();
        LogInScreenController controller = loader.getController();
        controller.setUserInterfaceController(userInterfaceController);
        windowStage=new Stage();
        windowStage.setScene(new Scene(changeUser));
        windowStage.initModality(Modality.APPLICATION_MODAL);
        windowStage.alwaysOnTopProperty();
        windowStage.show();
    } catch (
    IOException e) {
        e.printStackTrace();
    }
    }

    public void loadWindow(String path, boolean isAddFilm){
    FXMLLoader loader = new FXMLLoader(Main.class.getResource(path));
        try {
        AnchorPane addFilmLayout=loader.load();
        Scene scene=new Scene(addFilmLayout);
        windowStage=new Stage();
        windowStage.setScene(scene);
        FilmDialogController controller = loader.getController();
        controller.isAddFilm(isAddFilm);
        controller.setUserInterfaceController(userInterfaceController);
        controller.setFilmDialogTitle(isAddFilm?"Add Film":"Edit Film");
        windowStage.initModality(Modality.APPLICATION_MODAL);
        windowStage.alwaysOnTopProperty();
        windowStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    public boolean deleteFilmWindow(String deleteConfirmationMessage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(deleteConfirmationMessage);
        alert.setTitle(deleteConfirmationMessage);
        alert.setHeaderText(deleteConfirmationMessage);
        alert.showAndWait();
        if(alert.getResult().getText().equals("OK"))
            return true;
        else return false;
    }
}
