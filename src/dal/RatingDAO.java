package dal;

import be.Film;
import be.FilmRating;
import be.SearchTool;
import be.User;
import bll.RatingsManager;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class RatingDAO {
    private final SearchTool searchTool = new SearchTool();
    private static final String RATINGS_DATA_SOURCE="data/ratings.txt";
    private static final String CURRENT_RATING_DATA_SOURCE="data/ratings_current.data";
    private ArrayList<FilmRating> ratingsArrayList= new ArrayList<>();
    private RatingsManager ratingsManager;
    private ArrayList<FilmRating> userRatingsArrayList= new ArrayList<>();
    private ArrayList<FilmRating> userRatings= new ArrayList<>();
    private boolean printmode=false;

    public void setPrintmode(boolean printmode) {
        this.printmode = printmode;
    }

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

    /*
    public void addFilmRating(FilmRating filmRating, boolean save){
        ratingsArrayList.sort(Comparator.comparingInt(FilmRating::getUserId));
        ratingsArrayList.removeIf(filmRating1 -> filmRating1.getUserId()==filmRating.getUserId()&&filmRating1.getFilmId()==filmRating.getFilmId());
        ratingsArrayList.add(filmRating);
        saveRatings(save);
    }

    public int getUsersRatings(User user, Film film) {
        for (FilmRating filmRating : ratingsArrayList) {
            if (filmRating.getUserId() == user.getId() && filmRating.getFilmId() == film.getIntId()){
                return filmRating.getRating();}
        }
        return 0;
    }
     */

    public void saveRatings(boolean save){
        if(save){
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
    }

    public void initUsersRatingsToFile(User user){
        File file = new File(CURRENT_RATING_DATA_SOURCE);
        try(RandomAccessFile raf = new RandomAccessFile(file,"rw")){
            raf.setLength(0);
            userRatingsArrayList.clear();
            for(FilmRating filmRating:ratingsArrayList){
                if(filmRating.getUserId()==user.getId())
                    userRatingsArrayList.add(filmRating);}
            for(FilmRating filmRating: userRatingsArrayList){
                raf.writeInt(filmRating.getFilmId());
                raf.writeInt(filmRating.getUserId());
                raf.writeInt(filmRating.getRating());
            }
            raf.seek(0);
            if(printmode){
            System.out.println("Persons ratings:");
            String format = "%-10s%-10s%3s%n";
            System.out.printf(format,"FilmId", "UserId", "★");
            while(raf.getFilePointer()<raf.length()){
                System.out.printf(format,raf.readInt(), raf.readInt(), raf.readInt());
            }}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUsersRatingInFile(Film film, User user, int rating){
        File file = new File(CURRENT_RATING_DATA_SOURCE);
        try(RandomAccessFile raf = new RandomAccessFile(file,"rws")){
            boolean overWritten=false;
            while(raf.getFilePointer()<raf.length()&&!overWritten){
                if(raf.readInt()==film.getIntId()){
                    raf.skipBytes(4);
                    raf.writeInt(rating);
                    overWritten=true;
                    if(printmode){
                    System.out.println("Film overwritten");
                    System.out.printf("%-10s%-10s%3s%n",film.getId().getValue(),user.getId(),rating);}
                }
                else raf.skipBytes(8);
            }
            if(!overWritten){
                if(printmode){
                System.out.println("Added rating");
                System.out.printf("%-10s%-10s%3s%n",film.getId().getValue(),user.getId(),rating);
                }
                raf.writeInt(film.getIntId());
                raf.writeInt(user.getId());
                raf.writeInt(rating);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getUsersRatingFromFile(Film film){
        File file = new File(CURRENT_RATING_DATA_SOURCE);
        try(RandomAccessFile raf = new RandomAccessFile(file,"r")){
            while(raf.getFilePointer()<raf.length()){
                if(raf.readInt()==film.getIntId()){
                    raf.skipBytes(4);
                    return raf.readInt();
                }
                else raf.skipBytes(8);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void saveUsersRatingInMemory(User user){
        File file = new File(CURRENT_RATING_DATA_SOURCE);
        ratingsArrayList.removeIf(filmRating -> filmRating.getUserId() == user.getId());
        try(RandomAccessFile raf = new RandomAccessFile(file,"rw")) {
            while (raf.getFilePointer() < raf.length()) {
                ratingsArrayList.add(new FilmRating(raf.readInt(),raf.readInt(), raf.readInt()));
            }
            if(printmode){
                System.out.println("saved!");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
