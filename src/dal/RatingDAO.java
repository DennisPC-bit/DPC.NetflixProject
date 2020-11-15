package dal;

import be.Film;
import be.FilmRating;
import be.User;
import bll.RatingsManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class RatingDAO {
    private static final String RATINGS_DATA_SOURCE="data/ratings.txt";
    private ArrayList<FilmRating> ratingsArrayList= new ArrayList<>();
    private RatingsManager ratingsManager;
    public RatingDAO(RatingsManager ratingsManager){
        this.ratingsManager = ratingsManager;
    }

    public ArrayList<FilmRating> getAllRatings() {
        File file = new File(RATINGS_DATA_SOURCE);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                String line = br.readLine();
                while (line != null) {
                    if (!line.isEmpty()){
                        ratingsArrayList.add(ratingsManager.parseRating(line));
                    }
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ratingsArrayList;
    }

    public int getUsersRatings(User user, Film film) {
        for (FilmRating filmRating : ratingsArrayList) {
            ratingsArrayList.sort(Comparator.comparingInt(FilmRating::getFilmId));
            if (filmRating.getUserId() == user.getId() && filmRating.getFilmId() == film.getIntId())
                return filmRating.getRating();
        }
        return 0;
    }
}
