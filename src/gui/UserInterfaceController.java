package gui;

import be.Film;
import be.FilmRating;
import be.User;
import bll.FilmManager;
import bll.RatingsManager;
import bll.UserManager;
import gui.Dialogs.AddFilmDialogController;
import gui.Dialogs.EditFilmDialogController;
import gui.Dialogs.LogInScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
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
    private Stage addFilmDialogStage;
    @FXML
    private Stage editFilmDialogStage;
    @FXML
    private Stage changeUserStage;
    private ObservableList<Film> films;
    private ObservableList<User> users;
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

    public ArrayList<FilmRating> loadRatings(){return ratingsManager.getAllRatings();}
    public void loadUsers(){this.users=FXCollections.observableArrayList(userManager.getAllUsers()); }
    public void loadFilms(){
        this.films = FXCollections.observableArrayList(filmManager.loadFilms());
    }
    public int getUniqueFilmId(){return filmManager.getUniqueFilmId();}
    public Film getSelectedFilm(){return selectedFilm;}
    public Stage getAddFilmDialogStage() {return addFilmDialogStage;}
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

    public void openAddFilmDialog() {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Dialogs/AddFilmDialog.fxml"));
        try {
            AnchorPane addFilmLayout=loader.load();
            Scene scene=new Scene(addFilmLayout);
            addFilmDialogStage = new Stage();
            addFilmDialogStage.setScene(scene);
            AddFilmDialogController controller = loader.getController();
            controller.setUserInterfaceController(this);
            addFilmDialogStage.initModality(Modality.APPLICATION_MODAL);
            addFilmDialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeAddFilmDialogStage(){
        addFilmDialogStage.close();
    }

    public void editFilm(Film film){
        for(Film filmCheck: films)
            if(filmCheck.getIntId()==film.getIntId()){
                films.remove(filmCheck);
                films.add(film);
                films.sort(Comparator.comparingInt(Film::getIntId));
                filmManager.saveFilmChanges(autoSave);
                break;
            }
    }

    public void openEditFilmDialog() {
        if(selectedFilm!=null) {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Dialogs/EditFilmDialog.fxml"));
            try {
                AnchorPane addFilmLayout=loader.load();
                Scene scene=new Scene(addFilmLayout);
                editFilmDialogStage = new Stage();
                editFilmDialogStage.setScene(scene);
                EditFilmDialogController controller = loader.getController();
                controller.setUserInterfaceController(this);
                editFilmDialogStage.initModality(Modality.APPLICATION_MODAL);
                editFilmDialogStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeEditFilmDialogStage(){
        editFilmDialogStage.close();
    }

    public void changeUser() {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Dialogs/LogInScreen.fxml"));
        try {
            AnchorPane changeUser = loader.load();
            changeUserStage=new Stage();
            changeUserStage.setScene(new Scene(changeUser));
            changeUserStage.initModality(Modality.APPLICATION_MODAL);
            changeUserStage.show();
            LogInScreenController controller = loader.getController();
            controller.setUserInterfaceController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CloseChangeUserStage(){
        changeUserStage.close();
    }

    public void removeFilm() {
        for(Film filmCheck: films){
            if(filmCheck.getIntId()== selectedFilm.getIntId()){
                films.remove(filmCheck);
                films.sort(Comparator.comparingInt(Film::getIntId));
                filmManager.saveFilmChanges(autoSave);
                break;
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
        ratingsManager.addFilmRating(new FilmRating(selectedFilm.getIntId(),user.getId(),-5));
    }

    public void rateTwo() {
        if(user!=null)
        ratingsManager.addFilmRating(new FilmRating(selectedFilm.getIntId(),user.getId(),-3));
    }

    public void rateThree() {
        if(user!=null)
        ratingsManager.addFilmRating(new FilmRating(selectedFilm.getIntId(),user.getId(),1));
    }

    public void rateFour() {
        if(user!=null)
        ratingsManager.addFilmRating(new FilmRating(selectedFilm.getIntId(),user.getId(),3));
    }

    public void rateFive() {
        if(user!=null)
        ratingsManager.addFilmRating(new FilmRating(selectedFilm.getIntId(),user.getId(),5));
    }
}

