package gui;

import be.Film;
import be.User;
import bll.FilmParser;
import bll.RatingsParser;
import bll.UserParser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class UserInterfaceController {

    public UserInterfaceController(){
    this.filmParser = new FilmParser(this);
    this.userParser = new UserParser(this);
    this.ratingsParser = new RatingsParser(this);
    }

    private FilmParser filmParser;
    private UserParser userParser;
    private RatingsParser ratingsParser;

    public Label filmLabel;
    public Label dateLabel;
    public Label ratingLabel;
    public AnchorPane sidePanel;
    private ObservableList<Film> films;

    @FXML
    public TextField searchField;
    @FXML
    public TableColumn<Film, Integer> dateColumn;
    @FXML
    public TableColumn<Film,String> filmColumn;
    @FXML
    public TableColumn<Film,Integer> movieIdColumn;
    @FXML
    public TableView filmTable;
    private Film selectedFilm;

    @FXML
    public void initialize(){
        this.loadFilms();
        this.filmTable.setItems(this.films);

        this.filmTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.selectedFilm = (Film) newValue;
            if(selectedFilm!=null) {
                changeLabels(selectedFilm);
            }
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
        ratingLabel.setText(String.valueOf(ratingsParser.getRatingsForFilm(film)));
    }
}
