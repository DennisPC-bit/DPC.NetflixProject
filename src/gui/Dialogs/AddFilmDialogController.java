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
                    String problem="INVALID YEAR";
                    Alert alert= new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(problem);
                    alert.setHeaderText(problem);
                    alert.setContentText(problem);
                    alert.showAndWait();
                }
            }
            else{
                String problem="TITLEFIELD OR YEARFIELD EMPTY";
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle(problem);
                alert.setHeaderText(problem);
                alert.setContentText(problem);
                alert.showAndWait();
            }
        }
    }

    public void cancelButton(ActionEvent actionEvent) {
    userInterfaceController.closeAddFilmDialogStage();
    }
}
