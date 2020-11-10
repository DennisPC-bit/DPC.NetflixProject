package be;

import java.util.ArrayList;

public class User {
    private String name;
    private int id;
    private ArrayList<FilmRating> filmsRated;

    User(String name, int id, ArrayList<FilmRating> filmsRated){
        this.name=name;
        this.id=id;
        this.filmsRated=filmsRated;
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

    public void addFilmRating(int rating, Film movie){
        filmsRated.add(new FilmRating(rating,movie));
    }
}
