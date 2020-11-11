package dal;

import be.Film;
import bll.FilmParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class FilmDAO {

    private static final String FILM_SOURCE = "data/movie_titles.txt";
    ArrayList<Film> films = new ArrayList<>();
    private FilmParser filmParser;

    public FilmDAO(FilmParser filmParser){
        this.filmParser=filmParser;
    }

    public int getUniqueFilmId(){
        int index=1;
        films.sort(Comparator.comparingInt(Film::getIntId));
        for(Film film:films) {
            if (film.getIntId() == index)
                index++;
        }
        return index;
    }

    public ObservableList<Film> getAllFilms() {
        File file = new File(FILM_SOURCE);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                String line = br.readLine();
                while (line != null) {
                    if (!line.isEmpty() && line.split(",").length>=3)
                        films.add(filmParser.parseFilm(line));
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(films);
    }

    public ObservableList<Film> searchForFilm(String searchString) {
        ArrayList<Film> filmsSearch = new ArrayList<>();
        File file = new File(FILM_SOURCE);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                String line = br.readLine();
                while (line != null) {
                    if (!line.isEmpty() && line.toLowerCase().contains(searchString.toLowerCase()))
                        filmsSearch.add(filmParser.parseFilm(line));
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(filmsSearch);
    }

    public void editFilm(Film film){
        for(Film filmsCheck: films){
            if(filmsCheck.getId()==film.getId())
                filmsCheck=film;
        }
        saveFilmChanges();
    }

    public void removeFilm(Film film){
        films.remove(film);
        saveFilmChanges();
    }

    public void addNewFilm(Film film){
        films.add(film);
        saveFilmChanges();
    }

    public void saveFilmChanges(){
        films.sort(Comparator.comparingInt(Film::getIntId));
        File file = new File(FILM_SOURCE);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for(Film filmsInCurrentArray: films){
                bw.write(filmParser.inverseParseFilm(filmsInCurrentArray));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
