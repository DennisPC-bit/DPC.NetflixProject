package bll;

import be.Film;
import be.FilmRating;
import be.User;
import dal.RatingDAO;
import gui.UserInterfaceController;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;

/*
 *
 *@author DennisPC-bit
 */

public class RatingsManager {
    private final RatingDAO ratingDAO = new RatingDAO(this);
    private final UserInterfaceController userInterfaceController;

    public RatingsManager(UserInterfaceController userInterfaceController) {
        this.userInterfaceController=userInterfaceController;
    }

    public int getUsersRatingFromFile(Film film){
        return ratingDAO.getUsersRatingFromFile(film);
    }

    public void setUsersRatingInFile(Film film,User user,int rating){
        ratingDAO.setUsersRatingInFile(film, user, rating);
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

    public void saveUsersRatingToMemory(User user){
        ratingDAO.saveUsersRatingInMemory(user);
    }
    public void saveRatings(){
        ratingDAO.saveRatings(true);
    }
}