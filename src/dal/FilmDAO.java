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

    public void removeFilm(int id){
    }

    public void addFilm(Film film){
        File file = new File(FILM_SOURCE);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(filmParser.inverseParseFilm(film));
            bw.newLine();
            for(Film filmThatWereAlreadyInTheArray: films){
                bw.write(filmParser.inverseParseFilm(filmThatWereAlreadyInTheArray));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void editFilm(Film film){
        File file = new File(FILM_SOURCE);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for(Film filmsCheck: films)
                if(filmsCheck.getId()==film.getId())
                    filmsCheck=film;
            for(Film filmThatWereAlreadyInTheArray: films){
                bw.write(filmParser.inverseParseFilm(filmThatWereAlreadyInTheArray));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
