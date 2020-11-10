package gui;

import be.Film;
import be.FilmRating;
import bll.FilmSearcher;
import dal.FilmDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class UserInterfaceController {

    public Label filmLabel;
    public Label dateLabel;
    public Label ratingLabel;
    public AnchorPane sidePanel;
    private ObservableList<Film> films;
    private FilmDAO filmDAO = new FilmDAO();
    private FilmSearcher filmSearcher = new FilmSearcher();

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

    public void loadFilms(){
        this.films = filmDAO.getAllFilms();
    }

    public void searchWithKey(KeyEvent actionEvent) {search(); }
    public void searchBtn(ActionEvent actionEvent) {search();}

    private void search() {
        if(searchField.getText()==null||searchField.getText().equals(""))
            this.filmTable.setItems(this.films);
        else
            this.filmTable.setItems(filmSearcher.searchForFilm(searchField.getText()));
    }

    public void changeLabels(Film film){
        filmLabel.setText(film.getTitle().getValue());
        dateLabel.setText(film.getDate().getValue().toString());
        ratingLabel.setText(String.valueOf(film.getRating(film)));
    }
}
