package gui;

import be.Film;
import be.FilmRating;
import be.User;
import bll.FilmManager;
import bll.RatingsManager;
import bll.UserManager;
import gui.Dialogs.FilmDialogController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;

public class UserInterfaceController {

    public UserInterfaceController(){
    this.filmManager = new FilmManager(this);
    this.userManager = new UserManager(this);
    this.ratingsManager = new RatingsManager(this);
    this.windowOpener=new WindowOpener(this);
    this.autoSave=true;
    }

    private FilmManager filmManager;
    private UserManager userManager;
    private RatingsManager ratingsManager;
    private WindowOpener windowOpener;
    @FXML
    public GridPane rateButtons;
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
    @FXML
    private AnchorPane sidePanel;
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
        hideSidePanel(true);
        rateButtonDisable(true);
        this.filmTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.selectedFilm = (Film) newValue;
            if(selectedFilm!=null) {
                changeFilmLabels(selectedFilm);
            hideSidePanel(false);}
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
    public String getSelectedFilmTitle(){return getSelectedFilm().getTitle().getValue();}
    public String getSelectedFilmYear(){return getSelectedFilm().getDate().getValue().toString();}
    public int getUsersFilmRating(User user, Film film){
        return ratingsManager.getUsersRatingsForFilm(user,film);
    }
    public void setUser(User user) {this.user = user;
        rateButtons.setVisible(true);}

    public void hideSideMenu() {
        sidePanel.setMaxWidth(0);
        sidePanel.setMinWidth(0);
        this.user=null;
    }

    private void hideSidePanel(boolean hide) {
        if(hide){
        this.sidePanel.setMaxWidth(0);
        this.sidePanel.setMinWidth(0);
        }
        else{
            this.sidePanel.setMaxWidth(250);
            this.sidePanel.setMinWidth(250);
        }
    }

    public void changeFilmLabels(Film film){
        filmLabel.setText(film.getTitle().getValue());
        dateLabel.setText(film.getDate().getValue().toString());
        if(this.user!=null)
            ratingLabel.setText(ratingsManager.putStars(getUsersFilmRating(user,film)));
        else
            ratingLabel.setText("Log in to Rate!");
    }

    private void rateButtonDisable(boolean disable) {
        rateButtons.setVisible(!disable);
    }

    public void searchWithKey() {search(); }
    public void clearBtn() {searchField.setText("");
    search();}

    private void search() {
        try {this.filmTable.setItems(filmManager.searchForFilm(searchField.getText()));}
        catch(IllegalArgumentException e){e.printStackTrace();}
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
        windowOpener.loadWindow("Dialogs/FilmDialog.fxml",true);;
    }

    public void openEditFilmDialog() {
        if(selectedFilm!=null) {
            windowOpener.loadWindow("Dialogs/FilmDialog.fxml",false);
        }
    }

    public void openChangeUser() {
        windowOpener.loadWindow("Dialogs/LogInScreen.fxml");
    }

    public void closeWindow(){
        windowOpener.getWindowStage().close();
    }


    public void deleteFilm() {
        if(windowOpener.deleteFilmWindow("Confirm deleting" + " (" + selectedFilm.getIntId() + ") " + selectedFilm.getTitle().getValue()))
        {
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
        ratingsManager.saveRatings();
    }

    public void toggleAutoSave() {
            autoSave=!autoSave;
    }

    public void rateOne() {
        if(user!=null)
        ratingsManager.setFilmRating(new FilmRating(selectedFilm.getIntId(),user.getId(),-5),autoSave);
    }

    public void rateTwo() {
        if(user!=null)
        ratingsManager.setFilmRating(new FilmRating(selectedFilm.getIntId(),user.getId(),-3),autoSave);
    }

    public void rateThree() {
        if(user!=null)
        ratingsManager.setFilmRating(new FilmRating(selectedFilm.getIntId(),user.getId(),1),autoSave);
    }

    public void rateFour() {
        if(user!=null)
        ratingsManager.setFilmRating(new FilmRating(selectedFilm.getIntId(),user.getId(),3),autoSave);
    }

    public void rateFive() {
        if(user!=null)
        ratingsManager.setFilmRating(new FilmRating(selectedFilm.getIntId(),user.getId(),5),autoSave);
    }

    public ObservableList<User> searchForUsers(String searchString){
        return userManager.searchForUser(searchString);
    }
}