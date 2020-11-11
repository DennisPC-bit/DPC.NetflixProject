package be;

import java.util.ArrayList;

public class User {
    private String name;
    private int id;
    private ArrayList<FilmRating> filmsRated;

    public User(String name, int id){
        this.name=name;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public ArrayList<FilmRating> getFilmsRated() {
        return filmsRated;
    }

    public void addFilmRating(int rating, Film film){
        filmsRated.add(new FilmRating(rating,film, film.getId().getValue()));
    }
}
