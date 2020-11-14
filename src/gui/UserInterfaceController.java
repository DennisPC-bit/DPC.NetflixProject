package gui;

import be.Film;
import be.User;
import bll.FilmManager;
import bll.RatingsManager;
import bll.UserManager;
import gui.Dialogs.AddFilmDialogController;
import gui.Dialogs.EditFilmDialogController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class UserInterfaceController {

    public UserInterfaceController(){
    this.filmParser = new FilmManager(this);
    this.userParser = new UserManager(this);
    this.ratingsParser = new RatingsManager(this);
    }

    private FilmManager filmParser;
    private UserManager userParser;
    private RatingsManager ratingsParser;

    @FXML
    private Label filmLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private TextField searchField;
    @FXML
    private TableColumn<Film, Integer> dateColumn;
    @FXML
    private TableColumn<Film,String> filmColumn;
    @FXML
    private TableColumn<Film,Integer> movieIdColumn;
    @FXML
    private TableView filmTable;
    private Film selectedFilm;
    private Stage addFilmDialogStage;
    private Stage editFilmDialogStage;
    private ObservableList<Film> films;

    @FXML
    public void initialize(){
        this.loadFilms();
        this.filmTable.setItems(this.films);

        this.filmTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.selectedFilm = (Film) newValue;
            if(selectedFilm!=null) {changeLabels(selectedFilm);}
        });
        this.filmColumn.setCellValueFactory(cellData -> cellData.getValue().getTitle());
        this.dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDate());
        this.movieIdColumn.setCellValueFactory(cellData -> cellData.getValue().getId());
    }

    public ObservableList<Film> getAllFilms() {
        return filmParser.getAllFilms();
    }
    public ArrayList<User> getAllUsers(){
        return userParser.getAllUsers();
    }
    public int getUniqueFilmId(){return filmParser.getUniqueFilmId();}
    public Film getSelectedFilm(){return selectedFilm;}
    public Stage getAddFilmDialogStage() {return addFilmDialogStage;}
    public void loadFilms(){
        this.films = filmParser.getAllFilms();
    }

    public void searchWithKey(KeyEvent actionEvent) {search(); }
    public void searchBtn(ActionEvent actionEvent) {searchField.setText("");
    search();}

    private void search() {
        try {
            if (searchField.getText() == null || searchField.getText().equals(""))
                this.filmTable.setItems(this.films);
            else
                this.filmTable.setItems(filmParser.searchForFilm(searchField.getText()));
        }
        catch(IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    public void changeLabels(Film film){
        filmLabel.setText(film.getTitle().getValue());
        dateLabel.setText(film.getDate().getValue().toString());
    }

    public void addNewFilm(Film film){filmParser.addNewFilm(film);
        films.add(film);}

    public void editFilm(Film film){
        filmParser.editFilm(film);
    }

    public void removeFilm(ActionEvent actionEvent) {
        filmParser.removeFilm(selectedFilm);
        films.remove(selectedFilm);}

    public void openAddFilmDialog(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Dialogs/AddFilmDialog.fxml"));
        try {
            AnchorPane addFilmLayout=loader.load();
            Scene scene=new Scene(addFilmLayout);
            addFilmDialogStage = new Stage();
            addFilmDialogStage.setScene(scene);
            AddFilmDialogController controller = loader.getController();
            controller.setUserInterfaceController(this);
            addFilmDialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeAddFilmDialogStage(){
        addFilmDialogStage.close();
    }

    public void openEditFilmDialog(ActionEvent actionEvent) {
        if(selectedFilm!=null) {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Dialogs/EditFilmDialog.fxml"));
            try {
                AnchorPane addFilmLayout=loader.load();
                Scene scene=new Scene(addFilmLayout);
                editFilmDialogStage = new Stage();
                editFilmDialogStage.setScene(scene);
                EditFilmDialogController controller = loader.getController();
                controller.setUserInterfaceController(this);
                editFilmDialogStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeEditFilmDialogStage(){
        editFilmDialogStage.close();
    }

    public void useSaveButton(ActionEvent actionEvent) {
        filmParser.saveFilmChanges();
    }

    public void toggleAutoSave(ActionEvent actionEvent) {
        filmParser.toggleAutoSave();
    }
}

