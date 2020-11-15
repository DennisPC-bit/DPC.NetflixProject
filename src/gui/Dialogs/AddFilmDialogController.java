package gui.Dialogs;

import be.Film;
import gui.UserInterfaceController;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AddFilmDialogController {
    private UserInterfaceController userInterfaceController;
    public TextField titlefield;
    public TextField yearField;
    private final InputAlert inputAlert = new InputAlert();

    public void setUserInterfaceController(UserInterfaceController userInterfaceController) {
        this.userInterfaceController = userInterfaceController;
    }

    public void confirmButton(ActionEvent actionEvent) {
        if(titlefield!=null&&yearField!=null){
            if(!titlefield.getText().isEmpty()&&!yearField.getText().isEmpty()) {
                try {
                    userInterfaceController.addNewFilm(new Film(userInterfaceController.getUniqueFilmId(), Integer.parseInt(yearField.getText()), titlefield.getText()));
                    userInterfaceController.closeAddFilmDialogStage();
                    }
                catch (NumberFormatException e)
                {
                    inputAlert.showAlert("INVALID YEAR");
                }
            }
            else{
                inputAlert.showAlert("TITLEFIELD OR YEARFIELD EMPTY");
            }
        }
    }

    public void cancelButton(ActionEvent actionEvent) {
    userInterfaceController.closeAddFilmDialogStage();
    }
}
