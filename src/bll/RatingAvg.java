package bll;

import be.Film;
import dal.RatingDAO;

public class RatingAvg {

    private RatingDAO ratingDAO = new RatingDAO();
    public double getAVGRating(Film film){
        return ratingDAO.ratingAVG(film);
    }
}
