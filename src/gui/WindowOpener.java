package gui;

import gui.Dialogs.FilmDialogController;
import gui.Dialogs.LogInScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/*
 *
 *@author DennisPC-bit
 */

public class WindowOpener {
    private final UserInterfaceController userInterfaceController;
    private String path;
    private Stage windowStage;

    public WindowOpener (UserInterfaceController userInterfaceController){
        this.userInterfaceController=userInterfaceController;
    }

    public Stage getWindowStage() {
        return windowStage;
    }

    public void loadWindow(String path){
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(path));
        try {
            AnchorPane changeUser = loader.load();
            LogInScreenController controller = loader.getController();
            controller.setUserInterfaceController(userInterfaceController);
            initializeWindowStage(changeUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadWindow(String path, boolean isAddFilm){
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(path));
        try {
            AnchorPane addFilmLayout=loader.load();
            FilmDialogController controller = loader.getController();
            controller.isAddFilm(isAddFilm);
            controller.setUserInterfaceController(userInterfaceController);
            controller.setFilmDialogTitle(isAddFilm?"Add Film":"Edit Film");
            initializeWindowStage(addFilmLayout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeWindowStage(AnchorPane layout) {
        windowStage = new Stage();
        windowStage.setScene(new Scene(layout));
        windowStage.initModality(Modality.APPLICATION_MODAL);
        windowStage.alwaysOnTopProperty();
        windowStage.show();
    }
}
