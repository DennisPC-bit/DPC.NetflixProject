package bll;

import be.Film;
import be.FilmRating;
import be.User;
import dal.FilmDAO;
import dal.RatingDAO;
import dal.UserDAO;
import gui.UserInterfaceController;

import java.util.ArrayList;

public class RatingsManager {
    private UserInterfaceController userInterfaceController;

    public RatingsManager(UserInterfaceController userInterfaceController){
        this.userInterfaceController=userInterfaceController;
    }
    RatingDAO ratingDAO = new RatingDAO(this);

    public FilmRating parseRating(String input){
        String[] rating=input.split(",");
        return new FilmRating(Integer.parseInt(rating[0]), Integer.parseInt(rating[1]), Integer.parseInt(rating[2]));
    }

    public String inverseParseRating(FilmRating filmRating){
        return filmRating.getFilmId() + "," + filmRating.getUserId() + "," + filmRating.getRating();
    }


    public double ratingAVG(Film film) {
        if (film != null) {
            for (FilmRating filmrating : ratingDAO.getAllRatings()) {
                if (film.getId().getValue() == filmrating.getFilmId()) {
                    film.addRating(filmrating.getRating());
                }
            }
        }
        return 0;
    }

    public ArrayList<FilmRating> getAllRatings(){return ratingDAO.getAllRatings();}

    public int getUsersRatingsForFilm(User user,Film film){
        return ratingDAO.getUsersRatings(user, film);
    }

    public void addFilmRating(FilmRating filmRating){
        ratingDAO.addFilmRating(filmRating);
    }
}

