package gui.Dialogs;

import be.Film;
import be.InputAlert;
import gui.UserInterfaceController;
import javafx.scene.control.TextField;

public class AddFilmDialogController {
    private UserInterfaceController userInterfaceController;
    public TextField titlefield;
    public TextField yearField;
    private final InputAlert inputAlert = new InputAlert();

    public void setUserInterfaceController(UserInterfaceController userInterfaceController) {
        this.userInterfaceController = userInterfaceController;
    }

    public void confirmButton() {
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

    public void cancelButton() {
    userInterfaceController.closeAddFilmDialogStage();
    }
}
