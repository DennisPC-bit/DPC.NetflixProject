package gui.Dialogs;

import be.Film;
import be.InputAlert;
import gui.UserInterfaceController;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/*
 *
 *@author DennisPC-bit
 */

public class FilmDialogController {
    public TextField titleField;
    public TextField yearField;
    public Label filmDialogTitle;
    private UserInterfaceController userInterfaceController;
    private final InputAlert inputAlert = new InputAlert();
    private boolean isAddFilm;
    private final String EMPTY_ALERT = "Titlefield or Yearfield is Empty!";
    private final String YEAR_ALERT = "Invalid Year";

    public void isAddFilm(boolean isAddFilm){
        this.isAddFilm=isAddFilm;
    }

    public void setFilmDialogTitle(String filmDialogTitle) {
        this.filmDialogTitle.setText(filmDialogTitle);
    }

    public void setUserInterfaceController(UserInterfaceController userInterfaceController) {
        this.userInterfaceController = userInterfaceController;
        if(userInterfaceController.getSelectedFilm()!=null&&!isAddFilm){
        titleField.setText(userInterfaceController.getSelectedFilmTitle());
        yearField.setText(userInterfaceController.getSelectedFilmYear());
        }
    }

    public void confirmButton(ActionEvent actionEvent) {
        try{
        if(fieldsAreValid()){
            if(!isAddFilm)
                tryToEditFilm(userInterfaceController.getSelectedFilm());
            else
                tryToAddFilm(new Film(userInterfaceController.getUniqueFilmId(), Integer.parseInt(yearField.getText()), titleField.getText()));}
        else
            inputAlert.showAlert(EMPTY_ALERT);
        } catch (NumberFormatException e)
        {
            inputAlert.showAlert(YEAR_ALERT);
        }
    }

    private boolean fieldsAreValid() {
        return titleField != null && yearField != null
          && (!titleField.getText().isEmpty() && !yearField.getText().isEmpty());
    }

    private void tryToEditFilm(Film editedFilm) throws NumberFormatException{

            editedFilm.setTitle(titleField.getText());
            editedFilm.setDate(Integer.parseInt(yearField.getText()));
            userInterfaceController.editFilm(editedFilm);
            userInterfaceController.closeWindow();
    }

    private void tryToAddFilm(Film film) throws NumberFormatException {
            userInterfaceController.addNewFilm(film);
            userInterfaceController.closeWindow();
    }

    public void cancelButton() {
        userInterfaceController.closeWindow();
    }
}
