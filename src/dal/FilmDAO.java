package dal;

import be.Film;
import bll.FilmManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

/*
 *
 *@author DennisPC-bit
 */

public class FilmDAO {
    private final FilmManager filmManager;
    private static final String FILM_SOURCE = "data/movie_titles.txt";
    ArrayList<Film> films = new ArrayList<>();

    public FilmDAO(FilmManager filmManager){
        this.filmManager = filmManager;
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

    public ArrayList<Film> loadFilms() {
        File file = new File(FILM_SOURCE);
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
                String line = br.readLine();
                while (line != null) {
                    if (!line.isEmpty() && line.split(",").length>=3)
                        films.add(filmManager.parseFilm(line));
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
        }
        return films;
    }

    public ObservableList<Film> searchForFilm(String searchString) {
        ArrayList<Film> filmsSearch = new ArrayList<>();
        File file = new File(FILM_SOURCE);
            try (BufferedReader br = new BufferedReader(new FileReader(file))){
                String line = br.readLine();
                while (line != null) {
                    if (!line.isEmpty() && line.toLowerCase().contains(searchString.toLowerCase()))
                        filmsSearch.add(filmManager.parseFilm(line));
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        return FXCollections.observableArrayList(filmsSearch);
    }

    public void saveFilmChanges(boolean save){
        if(save){
        films.sort(Comparator.comparingInt(Film::getIntId));
        File file = new File(FILM_SOURCE);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            for(Film filmsInCurrentArray: filmManager.getAllFilms()){
                bw.write(filmManager.inverseParseFilm(filmsInCurrentArray));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
}
