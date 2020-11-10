package bll;

import be.Film;
import dal.FilmDAO;
import javafx.collections.ObservableList;

public class FilmSearcher {
    FilmDAO filmDAO = new FilmDAO();
    public ObservableList<Film> searchForFilm(String searchString){
        return filmDAO.searchForFilm(searchString);
    }
}
