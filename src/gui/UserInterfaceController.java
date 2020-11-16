package gui;

import be.Film;
import be.FilmRating;
import be.User;
import bll.FilmManager;
import bll.RatingsManager;
import bll.UserManager;
import gui.Dialogs.FilmDialogController;
import gui.Dialogs.LogInScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;

public class UserInterfaceController {

    public UserInterfaceController(){
    this.filmManager = new FilmManager(this);
    this.userManager = new UserManager(this);
    this.ratingsManager = new RatingsManager(this);
    this.autoSave=true;
    }

    private FilmManager filmManager;
    private UserManager userManager;
    private RatingsManager ratingsManager;
    @FXML
    private Label ratingLabel;
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
    @FXML
    private Film selectedFilm;
    @FXML
    private Stage filmDialogStage;
    @FXML
    private Stage changeUserStage;
    private ObservableList<Film> films;
    private ObservableList<User> users;
    private ObservableList<FilmRating> ratings;
    private boolean autoSave;
    private User user;

    @FXML
    private void initialize(){
        this.loadFilms();
        this.loadRatings();
        this.loadUsers();
        this.filmTable.setItems(this.films);

        this.filmTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.selectedFilm = (Film) newValue;
            if(selectedFilm!=null) {changeLabels(selectedFilm);}
        });
        this.filmColumn.setCellValueFactory(cellData -> cellData.getValue().getTitle());
        this.dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDate());
        this.movieIdColumn.setCellValueFactory(cellData -> cellData.getValue().getId());
    }

    public void loadRatings(){this.ratings=FXCollections.observableArrayList(ratingsManager.loadRatings());}
    public void loadUsers(){this.users=FXCollections.observableArrayList(userManager.loadUsers()); }
    public void loadFilms(){
        this.films = FXCollections.observableArrayList(filmManager.loadFilms());
    }
    public int getUniqueFilmId(){return filmManager.getUniqueFilmId();}
    public Film getSelectedFilm(){return selectedFilm;}
    public ObservableList<User> getUsers() {return users;}
    public User getUser() {return this.user;}

    public ObservableList<Film> getAllFilms() {return films;}

    public void setUser(User user) {this.user = user;}

    public void searchWithKey() {search(); }
    public void clearBtn() {searchField.setText("");
    search();}

    private void search() {
        try {this.filmTable.setItems(filmManager.searchForFilm(searchField.getText()));}
        catch(IllegalArgumentException e){e.printStackTrace();}
    }

    public void changeLabels(Film film){
        filmLabel.setText(film.getTitle().getValue());
        dateLabel.setText(film.getDate().getValue().toString());
        if(this.user!=null)
        ratingLabel.setText(putStars(getUsersFilmRating(user,film)));
        else
            ratingLabel.setText("Log in to Rate!");
    }

    public String putStars(int rating){
        switch(rating){
            case -5 -> {return "★☆☆☆☆";}
            case -3 -> {return "★★☆☆☆";}
            case 1 -> {return "★★★☆☆";}
            case 3 -> {return "★★★★☆";}
            case 5 -> {return "★★★★★";}
            default -> {return "not rated yet.";}
        }
    }

    public int getUsersFilmRating(User user, Film film){
        return ratingsManager.getUsersRatingsForFilm(user,film);
    }

    public void addNewFilm(Film film){
        films.add(film);
        films.sort(Comparator.comparingInt(Film::getIntId));
        filmManager.saveFilmChanges(autoSave);
    }

    public void editFilm(Film film){
        for(Film filmCheck: films){
            if(filmCheck.getIntId()==film.getIntId()){
                films.remove(filmCheck);
                films.add(film);
                films.sort(Comparator.comparingInt(Film::getIntId));
                filmManager.saveFilmChanges(autoSave);
                break;
            }
        }
    }

    public void openAddFilmDialog() {
        openFilmDialog(true);
    }

    public void openEditFilmDialog() {
        if(selectedFilm!=null) {
            openFilmDialog(false);
        }
    }

    private void openFilmDialog(boolean isAddFilm) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Dialogs/FilmDialog.fxml"));
        try {
            AnchorPane addFilmLayout=loader.load();
            Scene scene=new Scene(addFilmLayout);
            filmDialogStage = new Stage();
            filmDialogStage.setScene(scene);
            FilmDialogController controller = loader.getController();
            if(!isAddFilm)
                controller.setUserInterfaceController(this);
            controller.isAddFilm(isAddFilm);
            controller.setFilmDialogTitle(isAddFilm?"Add Film":"Edit Film");
            filmDialogStage.initModality(Modality.APPLICATION_MODAL);
            filmDialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFilmDialogStage(){
        filmDialogStage.close();
    }

    public void changeUser() {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Dialogs/LogInScreen.fxml"));
        try {
            AnchorPane changeUser = loader.load();
            LogInScreenController controller = loader.getController();
            controller.setUserInterfaceController(this);
            changeUserStage=new Stage();
            changeUserStage.setScene(new Scene(changeUser));
            changeUserStage.initModality(Modality.APPLICATION_MODAL);
            changeUserStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CloseChangeUserStage(){
        changeUserStage.close();
    }

    public void deleteFilm() {
        String deleteConfirmationText = "Confirm deleting" + " " + selectedFilm.getIntId() + " " + selectedFilm.getTitle().getValue();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(deleteConfirmationText);
        alert.setTitle(deleteConfirmationText);
        alert.setHeaderText(deleteConfirmationText);
        alert.showAndWait();
        if(alert.getResult().getText().equals("OK")){
        for(Film filmCheck: films){
            if(filmCheck.getIntId()== selectedFilm.getIntId()){
                films.remove(filmCheck);
                films.sort(Comparator.comparingInt(Film::getIntId));
                filmManager.saveFilmChanges(autoSave);
                break;
            }
        }
        }
    }

    public void useSaveButton() {
        filmManager.saveFilmChanges(true);
    }

    public void toggleAutoSave() {
            autoSave=!autoSave;
    }

    public void rateOne() {
        if(user!=null)
        ratingsManager.setFilmRating(new FilmRating(selectedFilm.getIntId(),user.getId(),-5));
    }

    public void rateTwo() {
        if(user!=null)
        ratingsManager.setFilmRating(new FilmRating(selectedFilm.getIntId(),user.getId(),-3));
    }

    public void rateThree() {
        if(user!=null)
        ratingsManager.setFilmRating(new FilmRating(selectedFilm.getIntId(),user.getId(),1));
    }

    public void rateFour() {
        if(user!=null)
        ratingsManager.setFilmRating(new FilmRating(selectedFilm.getIntId(),user.getId(),3));
    }

    public void rateFive() {
        if(user!=null)
        ratingsManager.setFilmRating(new FilmRating(selectedFilm.getIntId(),user.getId(),5));
    }

    public ObservableList<User> searchForUsers(String searchString){
        return userManager.searchForUser(searchString);
    }
}