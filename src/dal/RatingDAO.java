package dal;

import be.Film;
import be.FilmRating;
import bll.RatingsParser;
import bll.UserParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class RatingDAO {
    private static final String RATINGS_DATA_SOURCE="data/ratings.txt";
    private ArrayList<FilmRating> ratingsArrayList= new ArrayList<>();
    private RatingsParser ratingsParser;
    public RatingDAO(RatingsParser ratingsParser){
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
        ratingsArrayList.sort(Comparator.comparingInt(FilmRating::getId));
    for(FilmRating filmRating: getAllRatings()){
        if(film!=null && film.getId()==filmRating.getFilm().getId())
            film.addRating(filmRating.getRating());
    }
    return (int)film.getAvgRating();
    }
}
