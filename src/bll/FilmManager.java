package bll;

import be.Film;
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

    //lav binær metode i stedet for lineær
    public ObservableList<Film> deleteFilm(Film film) {
        ObservableList<Film> films = userInterfaceController.getAllFilms();
        for(Film filmCheck: films){
            if(filmCheck.getIntId()== film.getIntId()){
                films.remove(filmCheck);
                films.sort(Comparator.comparingInt(Film::getIntId));
                saveFilmChanges(userInterfaceController.getAutoSave());
                return  films;
            }
        }
        return films;
    }

    //lav binær metode i stedet for lineær
    public void editFilm(Film film) {
        ObservableList<Film> films = userInterfaceController.getAllFilms();
        for (Film filmCheck : films) {
            if (filmCheck.getIntId() == film.getIntId()) {
                films.remove(filmCheck);
                films.add(film);
                films.sort(Comparator.comparingInt(Film::getIntId));
                saveFilmChanges(userInterfaceController.getAutoSave());
                break;
            }
        }
    }

    public void addFilm(Film film){
        ObservableList<Film> films = userInterfaceController.getAllFilms();
        films.add(film);
        films.sort(Comparator.comparingInt(Film::getIntId));
        saveFilmChanges(userInterfaceController.getAutoSave());
    }
}
