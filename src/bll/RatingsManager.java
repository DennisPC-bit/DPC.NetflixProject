package bll;

import be.Film;
import be.FilmRating;
import be.User;
import dal.RatingDAO;
import gui.UserInterfaceController;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;

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
    public FilmRating parseRatingFromBinary(int input,int input2, int input3){
        return new FilmRating(input, input2, input3);
    }

    public String inverseParseRating(FilmRating filmRating){
        return filmRating.getFilmId() + "," + filmRating.getUserId() + "," + filmRating.getRating();
    }

    public void loadRatings(){ratingDAO.loadRatings();}

    /*
    public int getUsersRatingsForFilm(User user,Film film){
        return ratingDAO.getUsersRatings(user, film);
    }

    public void setFilmRating(FilmRating filmRating, boolean save){
        ratingDAO.addFilmRating(filmRating, save);
    }
     */


    public String putStars(int rating){
        switch(rating){
            case -5 -> {return "★☆☆☆☆";}
            case -3 -> {return "★★☆☆☆";}
            case 1 -> {return "★★★☆☆";}
            case 3 -> {return "★★★★☆";}
            case 5 -> {return "★★★★★";}
            default -> {return "not rated yet.";}
        }
    }

    public void initUsersRatingsToFile(User user){
        ratingDAO.initUsersRatingsToFile(user);
    }

    public int getUsersRatingFromFile(Film film){
        return ratingDAO.getUsersRatingFromFile(film);
    }

    public void setUsersRatingInFile(Film film,User user,int rating){
        ratingDAO.setUsersRatingInFile(film, user, rating);
    }

    public void saveUsersRatingToMemory(User user){
        ratingDAO.saveUsersRatingInMemory(user);
    }
    public void saveRatings(){
        ratingDAO.saveRatings(true);
    }
}