package bll;

import be.Film;
import be.FilmRating;
import be.User;
import dal.FilmDAO;
import dal.RatingDAO;
import dal.UserDAO;
import gui.UserInterfaceController;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class RatingsManager {
    private UserInterfaceController userInterfaceController;
    RatingDAO ratingDAO = new RatingDAO(this);

    public RatingsManager(UserInterfaceController userInterfaceController){
        this.userInterfaceController=userInterfaceController;
    }

    public FilmRating parseRating(String input){
        String[] rating=input.split(",");
        return new FilmRating(Integer.parseInt(rating[0]), Integer.parseInt(rating[1]), Integer.parseInt(rating[2]));
    }

    public String inverseParseRating(FilmRating filmRating){
        return filmRating.getFilmId() + "," + filmRating.getUserId() + "," + filmRating.getRating();
    }

    public ArrayList<FilmRating> getAllRatings(){return ratingDAO.getAllRatings();}

    public int getUsersRatingsForFilm(User user,Film film){
        return ratingDAO.getUsersRatings(user, film);
    }

    public void addFilmRating(FilmRating filmRating){
        ratingDAO.addFilmRating(filmRating);
    }
}