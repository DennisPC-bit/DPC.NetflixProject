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

    public ArrayList<FilmRating> loadRatings() {
        File file = new File(RATINGS_DATA_SOURCE);
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
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
        return ratingsArrayList;
    }

    public void addFilmRating(FilmRating filmRating){
        ratingsArrayList.sort(Comparator.comparingInt(FilmRating::getUserId));
        ratingsArrayList.removeIf(filmRating1 -> filmRating1.getUserId()==filmRating.getUserId()&&filmRating1.getFilmId()==filmRating.getFilmId());
        ratingsArrayList.add(filmRating);
        saveRatings();
    }

    public int getUsersRatings(User user, Film film) {
        ratingsArrayList.sort(Comparator.comparingInt(FilmRating::getFilmId));
        for (FilmRating filmRating : ratingsArrayList) {
            if (filmRating.getUserId() == user.getId() && filmRating.getFilmId() == film.getIntId()){
                return filmRating.getRating();}
        }
        return 0;
    }

    public void saveRatings(){
        ratingsArrayList.sort(Comparator.comparingInt(FilmRating::getFilmId));
        File file = new File(RATINGS_DATA_SOURCE);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            for(FilmRating filmRating:ratingsArrayList){
                bw.write(ratingsManager.inverseParseRating(filmRating));
                bw.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void makeFile(){
        try (RandomAccessFile raf = new RandomAccessFile(new File("data/ratings.dat"),"rw")){
            for(FilmRating filmRating : ratingsArrayList){
                raf.writeInt(filmRating.getFilmId());
                raf.writeInt(filmRating.getUserId());
                raf.writeInt(filmRating.getRating());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int findRatingInFile(int filmId, int userId) // virker
    {
        try (RandomAccessFile raf = new RandomAccessFile(new File("data/ratings.dat"),"rw")){
            while(raf.getFilePointer()<=raf.length()){
                if(!(raf.readInt()==filmId&&raf.readInt()==userId))
                    raf.skipBytes(4);
                else
                {return raf.readInt();}
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return -1;
    }

    public void editRatingInFile(int filmId, int userId, int newRating) // manger revidering
    {
        try (RandomAccessFile raf = new RandomAccessFile(new File("Ratings.dat"),"rw")){
            while(raf.getFilePointer()<=raf.length()){
                int film=raf.readInt();
                int user=raf.readInt();
                int rating=raf.readInt();
                boolean overWritten=false;
                if(!(film==filmId&&user==userId)){
                    raf.skipBytes(4);
                }
                else {
                    overWritten=true;
                    raf.writeInt(rating);
                }
                if(!overWritten){
                    raf.writeInt(film);
                    raf.writeInt(user);
                    raf.writeInt(rating);
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
