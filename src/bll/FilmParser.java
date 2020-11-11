package bll;

import be.Film;
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

    public ObservableList<Film> getAllFilms(){
        return filmDAO.getAllFilms();
    }

    public ObservableList<Film> searchForFilm(String searchString){
        return filmDAO.searchForFilm(searchString);
    }

    public void addFilm(Film film){
        filmDAO.addFilm(film);
    }

    public int getUniqueFilmId(){
        return filmDAO.getUniqueFilmId();
    }

    public void editFilm(Film film){
        filmDAO.editFilm(film);
    }
}
