package bll;

import be.Film;
import com.sun.javafx.tk.Toolkit;
import dal.FilmDAO;
import gui.UserInterfaceController;
import javafx.collections.ObservableList;

public class FilmParser {
    private String filmData;
    private String[] film;
    private FilmDAO filmDAO = new FilmDAO(this);
    private UserInterfaceController userInterfaceController;

    public FilmParser(UserInterfaceController userInterfaceController){
        this.userInterfaceController=userInterfaceController;
    }
    public ObservableList<Film> getAllFilms(){
        return filmDAO.getAllFilms();
    }
    public ObservableList<Film> getFilms(){
        return userInterfaceController.getAllFilms();
    }
    public int getUniqueFilmId(){
        return filmDAO.getUniqueFilmId();
    }

    public Film parseFilm(String filmData) {
        this.filmData = filmData;
        if (filmData != null) {
            film = filmData.split(",");
            if (film.length >= 3)
                for (int i = 3; i < film.length; i++)
                    film[2] += " " + film[i];
        }
        return new Film(film[2],Integer.parseInt(film[1]),Integer.parseInt(film[0]));
    }

    public String inverseParseFilm(Film film){
        return film.getId().getValue() + "," + film.getDate().getValue() + "," + film.getTitle().getValue();
    }

    public ObservableList<Film> searchForFilm(String searchString){
        return filmDAO.searchForFilm(searchString);
    }

    public void editFilm(Film film){
        filmDAO.editFilm(film);
    }

    public void removeFilm(Film film){
        filmDAO.removeFilm(film);
    }

    public void addNewFilm(Film film){
        filmDAO.addNewFilm(film);
    }

    public void toggleAutoSave(){
        filmDAO.toggleAutoSave();
    }

    public void saveFilmChanges(){
        filmDAO.saveFilmChanges(true);
    }
}
