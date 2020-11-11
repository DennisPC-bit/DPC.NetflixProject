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
    private FilmParser filmParser;

    public FilmDAO(FilmParser filmParser){
        this.filmParser=filmParser;
    }

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
        ArrayList<Film> filmsSearch = new ArrayList<>();
        File file = new File(MOVIE_SOURCE);
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

    public void removeFilm(int id){
    }

    public ObservableList<Film> addFilm(Film film){
        films.add(film);
        File file = new File(MOVIE_SOURCE);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(filmParser.inverseParseFilm(film));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(films);
    }
}
