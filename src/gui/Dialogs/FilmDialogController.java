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
    private String EMPTY_ALERT = "TITLEFIELD OR YEARFIELD EMPTY";
    private String YEAR_ALERT = "INVALID YEAR";

    public void isAddFilm(boolean isAddFilm){
        this.isAddFilm=isAddFilm;
    }

    public void setFilmDialogTitle(String filmDialogTitle) {
        this.filmDialogTitle.setText(filmDialogTitle);
    }

    public void setUserInterfaceController(UserInterfaceController userInterfaceController) {
        this.userInterfaceController = userInterfaceController;
        if(userInterfaceController.getSelectedFilm()!=null&&!isAddFilm){
        titlefield.setText(userInterfaceController.getSelectedFilmTitle());
        yearField.setText(userInterfaceController.getSelectedFilmYear());
        }
    }

    public void confirmButton(ActionEvent actionEvent) {
        try{
        if(fieldsAreValid()){
            if(!isAddFilm)
                tryToEditFilm(userInterfaceController.getSelectedFilm());
            else
                tryToAddFilm(new Film(userInterfaceController.getUniqueFilmId(), Integer.parseInt(yearField.getText()), titlefield.getText()));}
        else
            inputAlert.showAlert(EMPTY_ALERT);
        }
        catch (NumberFormatException e)
        {
            inputAlert.showAlert(YEAR_ALERT);
        }
    }

    private boolean fieldsAreValid() {
        return titlefield != null && yearField != null
          && (!titlefield.getText().isEmpty() && !yearField.getText().isEmpty());
    }

    private void tryToEditFilm(Film editedFilm) throws NumberFormatException{
        if(!titlefield.getText().isEmpty()&&!yearField.getText().isEmpty()) {
            editedFilm.setTitle(titlefield.getText());
            editedFilm.setDate(Integer.parseInt(yearField.getText()));
            userInterfaceController.editFilm(editedFilm);
            userInterfaceController.closeWindow();
        }
        else{
        inputAlert.showAlert(EMPTY_ALERT);
        }
    }

    private void tryToAddFilm(Film film) throws NumberFormatException {
            userInterfaceController.addNewFilm(film);
            userInterfaceController.closeWindow();
    }

    public void cancelButton() {
        userInterfaceController.closeWindow();
    }
}
