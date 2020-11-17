package bll;

import be.Film;
import be.FilmRating;
import be.User;
import dal.RatingDAO;
import gui.UserInterfaceController;

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


    public ArrayList<FilmRating> loadRatings(){return ratingDAO.loadRatings();}

    public int getUsersRatingsForFilm(User user,Film film){
        return ratingDAO.getUsersRatings(user, film);
    }

    public void setFilmRating(FilmRating filmRating, boolean save){
        ratingDAO.addFilmRating(filmRating, save);
    }

    public void saveRatings(){
        ratingDAO.saveRatings(true);
    }

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


    /*
    public void addFilmRating(FilmRating filmRating){
        ratingDAO.editRatingInFile(filmRating.getFilmId(), filmRating.getUserId(), filmRating.getRating());
    }

    public int getUsersRatingsForFilm(User user,Film film){
        return ratingDAO.findRatingInFile(film.getIntId(), user.getId());
    }

    public void makeRatingsFile(){ratingDAO.makeFile();}
     */
}