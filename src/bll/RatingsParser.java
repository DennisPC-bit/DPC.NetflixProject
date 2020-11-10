package bll;

import be.Film;
import be.FilmRating;
import be.User;
import dal.FilmDAO;
import dal.RatingDAO;
import dal.UserDAO;

public class RatingsParser {
    private UserDAO userDAO = new UserDAO();
    private FilmDAO filmDAO = new FilmDAO();
    private RatingDAO ratingDAO = new RatingDAO();

    public FilmRating parseRating(String input){
        String[] rating=input.split(",");
        for(User user : userDAO.getAllUsers()) {
            for(Film film : filmDAO.getAllFilms())
            if (user.getId() == Integer.parseInt(rating[0])&&film.getId().getValue()==Integer.parseInt(rating[2])){
                user.addFilmRating(Integer.parseInt(rating[2]), film);
                film.addRating(Integer.parseInt(rating[2]));
                return new FilmRating(Integer.parseInt(rating[2]), film);
            }
        }
        return null;
    }

    public double getAVGRating(Film film){
        return ratingDAO.ratingAVG(film);
    }
}
