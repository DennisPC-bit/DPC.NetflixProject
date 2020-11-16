package gui.Dialogs;

import be.Film;
import be.InputAlert;
import gui.UserInterfaceController;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FilmDialogController {
    public TextField titlefield;
    public TextField yearField;
    public Label filmDialogTitle;
    private UserInterfaceController userInterfaceController;
    private final InputAlert inputAlert = new InputAlert();
    private boolean isAddFilm;

    public void isAddFilm(boolean isAddFilm){
        this.isAddFilm=isAddFilm;
    }

    public void setFilmDialogTitle(String filmDialogTitle) {
        this.filmDialogTitle.setText(filmDialogTitle);
    }

    public void setUserInterfaceController(UserInterfaceController userInterfaceController) {
        this.userInterfaceController = userInterfaceController;
        titlefield.setText(userInterfaceController.getSelectedFilm().getTitle().getValue());
        yearField.setText(userInterfaceController.getSelectedFilm().getDate().getValue().toString());
    }

    public void confirmButton(ActionEvent actionEvent) {
        if(titlefield!=null&&yearField!=null
        &&(!titlefield.getText().isEmpty()&&!yearField.getText().isEmpty())){
            if(!isAddFilm){
                tryToEditFilm();
            }
            else{
                tryToAddFilm();
            }
        }else{
            inputAlert.showAlert("TITLEFIELD OR YEARFIELD EMPTY");
        }
    }

    private void tryToEditFilm() {
        if(!titlefield.getText().isEmpty()&&!yearField.getText().isEmpty()) {
            Film editedFilm = userInterfaceController.getSelectedFilm();
            try{
            editedFilm.setTitle(titlefield.getText());
            editedFilm.setDate(Integer.parseInt(yearField.getText()));
            userInterfaceController.editFilm(editedFilm);
            userInterfaceController.closeFilmDialogStage();}
            catch(NumberFormatException e){
                inputAlert.showAlert("INVALID YEAR");
            }
        }
        else{
        inputAlert.showAlert("TITLEFIELD OR YEARFIELD EMPTY");
        }
    }

    private void tryToAddFilm() {
        try {
            userInterfaceController.addNewFilm(new Film(userInterfaceController.getUniqueFilmId(), Integer.parseInt(yearField.getText()), titlefield.getText()));
            userInterfaceController.closeFilmDialogStage();
        }
        catch (NumberFormatException e)
        {
            inputAlert.showAlert("INVALID YEAR");
        }
    }

    public void cancelButton(ActionEvent actionEvent) {
        userInterfaceController.closeFilmDialogStage();
    }
}
