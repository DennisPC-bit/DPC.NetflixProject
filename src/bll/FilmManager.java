package bll;

import be.Film;
import be.SearchTool;
import dal.FilmDAO;
import gui.UserInterfaceController;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;

public class FilmManager {
    private String filmData;
    private String[] film;
    private FilmDAO filmDAO = new FilmDAO(this);
    private UserInterfaceController userInterfaceController;
    private final SearchTool searchTool = new SearchTool();

    public FilmManager(UserInterfaceController userInterfaceController){
        this.userInterfaceController=userInterfaceController;
    }
    public ArrayList<Film> loadFilms(){
        return filmDAO.loadFilms();
    }
    public ObservableList<Film> getAllFilms(){return userInterfaceController.getAllFilms();}
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

    public void deleteFilm(Film film) {
        ObservableList<Film> films = userInterfaceController.getAllFilms();
        films.remove(searchTool.binarySearchFilmArray(films,film.getIntId()));
        films.sort(Comparator.comparingInt(Film::getIntId));
        saveFilmChanges(userInterfaceController.getAutoSave());
    }

    public void editFilm(Film film) {
        ObservableList<Film> films = userInterfaceController.getAllFilms();
        films.remove(searchTool.binarySearchFilmArray(films,film.getIntId()));
        films.add(film);
        films.sort(Comparator.comparingInt(Film::getIntId));
        saveFilmChanges(userInterfaceController.getAutoSave());
    }

    public void addFilm(Film film){
        ObservableList<Film> films = userInterfaceController.getAllFilms();
        films.add(film);
        films.sort(Comparator.comparingInt(Film::getIntId));
        saveFilmChanges(userInterfaceController.getAutoSave());
    }
}
