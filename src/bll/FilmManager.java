package bll;

import be.Film;
import dal.FilmDAO;
import gui.UserInterfaceController;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class FilmManager {
    private String filmData;
    private String[] film;
    private FilmDAO filmDAO = new FilmDAO(this);
    private UserInterfaceController userInterfaceController;

    public FilmManager(UserInterfaceController userInterfaceController){
        this.userInterfaceController=userInterfaceController;
    }
    public ArrayList<Film> getAllFilms(){
        return filmDAO.getAllFilms();
    }
    public ArrayList<Film> getFilms(){
        return userInterfaceController.getAllFilms();
    }
    public int getUniqueFilmId(){
        return filmDAO.getUniqueFilmId();
    }

    public Film parseFilm(String filmData) {
        this.filmData = filmData;
        if (filmData != null) {
            film = filmData.split(",");
            if (film.length >= 2)
                for (int i = 3; i < film.length; i++)
                    film[2] += " " + film[i];
        }
        return new Film(Integer.parseInt(film[0]),Integer.parseInt(film[1]),film[2]);
    }

    public String inverseParseFilm(Film film){
        return film.getId().getValue() + "," + film.getDate().getValue() + "," + film.getTitle().getValue();
    }

    public ObservableList<Film> searchForFilm(String searchString){
        return filmDAO.searchForFilm(searchString);
    }

    public void saveFilmChanges(boolean save){
        filmDAO.saveFilmChanges(save);
    }
}
