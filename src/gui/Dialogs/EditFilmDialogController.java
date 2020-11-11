package gui.Dialogs;

import be.Film;
import gui.UserInterfaceController;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class EditFilmDialogController {
    public TextField titlefield;
    public TextField yearField;
    private UserInterfaceController userInterfaceController;

    public void setUserInterfaceController(UserInterfaceController userInterfaceController) {
        this.userInterfaceController = userInterfaceController;
        titlefield.setText(userInterfaceController.getSelectedFilm().getTitle().getValue());
        yearField.setText(userInterfaceController.getSelectedFilm().getDate().getValue().toString());
    }

    public void confirmButton(ActionEvent actionEvent) {
        if(titlefield!=null&&yearField!=null){
            if(!titlefield.getText().isEmpty()&&!yearField.getText().isEmpty()) {
                Film editedFilm= userInterfaceController.getSelectedFilm();
                editedFilm.setTitle(titlefield.getText());
                editedFilm.setDate(Integer.parseInt(yearField.getText()));
                userInterfaceController.editFilm(editedFilm);
                userInterfaceController.closeEditFilmDialogStage();
            }
        }
        else
        userInterfaceController.closeAddFilmDialogStage();
    }

    public void cancelButton(ActionEvent actionEvent) {
        userInterfaceController.closeEditFilmDialogStage();
    }
}
