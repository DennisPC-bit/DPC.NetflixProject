package gui;

import be.Film;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class AddFilmDialogController {
    private UserInterfaceController userInterfaceController;
    public TextField titlefield;
    public TextField yearField;

    public void setUserInterfaceController(UserInterfaceController userInterfaceController) {
        this.userInterfaceController = userInterfaceController;
    }

    public void confirmButton(ActionEvent actionEvent) {
        if(titlefield!=null&&yearField!=null){
            if(!titlefield.getText().isEmpty()&&!yearField.getText().isEmpty()) {
                userInterfaceController.addFilm(new Film(titlefield.getText(), Integer.parseInt(yearField.getText()), userInterfaceController.getUniqueFilmId()));
                userInterfaceController.closeAddFilmDialogStage();
            }
        }
        userInterfaceController.closeAddFilmDialogStage();
    }

    public void cancelButton(ActionEvent actionEvent) {
    userInterfaceController.closeAddFilmDialogStage();
    }
}
