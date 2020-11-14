package dal;

import be.Film;
import be.FilmRating;
import bll.RatingsManager;

import java.io.*;
import java.util.ArrayList;

public class RatingDAO {
    private static final String RATINGS_DATA_SOURCE="data/ratings.txt";
    private ArrayList<FilmRating> ratingsArrayList= new ArrayList<>();
    private RatingsManager ratingsParser;
    public RatingDAO(RatingsManager ratingsParser){
        this.ratingsParser=ratingsParser;
    }

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

    public int getRatingsForFilm(Film film) {
        int cumulativeRating=0;
        int ratings=0;
        File file = new File(RATINGS_DATA_SOURCE);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                String line = br.readLine();
                while (line != null) {
                    if (!line.isEmpty()&&ratingsParser.parseRating(line).getFilm().getId()!=null&&ratingsParser.parseRating(line).getFilm().getId()==film.getId())
                        cumulativeRating+=ratingsParser.parseRating(line).getRating();
                        ratings++;
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    return (int)cumulativeRating/ratings;
    }
}
