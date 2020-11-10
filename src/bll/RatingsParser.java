package bll;

import be.Film;
import be.User;
import dal.FilmDAO;
import dal.UserDAO;

public class RatingsParser {
    private UserDAO userDAO = new UserDAO();
    private FilmDAO filmDAO = new FilmDAO();

    public void parseRating(String input){
        String[] rating=input.split(",");
        for(User user : userDAO.getAllUsers()) {
            for(Film film : filmDAO.getAllFilms())
            if (user.getId() == Integer.parseInt(rating[0])&&film.getId().getValue()==Integer.parseInt(rating[2])){
                user.addFilmRating(Integer.parseInt(rating[2]), film);
                film.addRating(Integer.parseInt(rating[2]));
            }
        }
    }
}
