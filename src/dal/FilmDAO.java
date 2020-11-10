package dal;

import be.Film;
import bll.FilmParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class FilmDAO {

    private static final String MOVIE_SOURCE = "data/movie_titles.txt";
    ArrayList<Film> films = new ArrayList<>();
    ArrayList<Film> filmsSearched = new ArrayList<>();
    FilmParser filmParser = new FilmParser();

    public ObservableList<Film> getAllFilms() {
        File file = new File(MOVIE_SOURCE);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                String line = br.readLine();
                while (line != null) {
                    if (!line.isEmpty())
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
        filmsSearched=films;
        filmsSearched.removeIf(film -> !film.getTitle().toString().toLowerCase().contains(searchString.toLowerCase()));
        return FXCollections.observableArrayList(filmsSearched);
}
}
