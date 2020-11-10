package dal;

import be.Film;
import be.FilmRating;
import bll.RatingsParser;
import bll.UserParser;

import java.io.*;
import java.util.ArrayList;

public class RatingDAO {
    private static final String RATINGS_DATA_SOURCE="data/ratings.txt";
    private ArrayList<FilmRating> ratingsArrayList= new ArrayList<>();
    private UserParser userParser = new UserParser();
    private RatingsParser ratingsParser = new RatingsParser();

    public ArrayList<FilmRating> getAllRatings() {
        File file = new File(RATINGS_DATA_SOURCE);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                String line = br.readLine();
                while (line != null) {
                    if (!line.isEmpty())
                        ratingsArrayList.add(ratingsParser.parseRating(line));
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

    public double ratingAVG(Film film) {
        if (film != null) {
            int cumulativeRating = 0;
            int ratings = 0;
            for (FilmRating filmrating : ratingsArrayList) {
                if (film == filmrating.getFilm()) {
                    ratings++;
                    cumulativeRating += filmrating.getRating();
                }
            }
            if (ratings > 0)
                return (double) cumulativeRating / ratings;
        }
        return 0;
    }
}
