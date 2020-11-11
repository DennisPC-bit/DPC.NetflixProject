package bll;

import be.Film;
import be.FilmRating;
import be.User;
import dal.FilmDAO;
import dal.RatingDAO;
import dal.UserDAO;
import gui.UserInterfaceController;

public class RatingsParser {
    private UserInterfaceController userInterfaceController;

    public RatingsParser(UserInterfaceController userInterfaceController){
        this.userInterfaceController=userInterfaceController;
    }
    RatingDAO ratingDAO = new RatingDAO(this);

    public FilmRating parseRating(String input){
        String[] rating=input.split(",");
            for(Film film : userInterfaceController.getAllFilms())
            if (film.getId().get()==Integer.parseInt(rating[1])){
                return new FilmRating(Integer.parseInt(rating[2]), film, Integer.parseInt(rating[1]));
            }
            return null;
        }

    public double ratingAVG(Film film) {
        if (film != null) {
            for (FilmRating filmrating : ratingDAO.getAllRatings()) {
                if (film.getId() == filmrating.getFilm().getId()) {
                    film.addRating(filmrating.getRating());
                }
            }
        }
        return 0;
    }

    public int getRatingsForFilm(Film film){
        return ratingDAO.getRatingsForFilm(film);
    }




}

