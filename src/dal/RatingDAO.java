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
        findFilmRatingInDataFile(7);
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
            for(FilmRating filmRating: search(user)){
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

    public ArrayList<FilmRating> search(User user){
        return searchTool.binarySearchFilmRatingArray(ratingsArrayList,user);
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

    public void makeFilmRatingDataFile(){
        File file = new File("data/newFile.data");
        try(RandomAccessFile raf = new RandomAccessFile(file,"rw")){
            ratingsArrayList.sort(Comparator.comparingInt(FilmRating::getUserId));
            for(FilmRating filmRating : ratingsArrayList){
                raf.writeInt(filmRating.getFilmId());
                raf.writeInt(filmRating.getUserId());
                raf.writeInt(filmRating.getRating());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findFilmRatingInDataFile(int target){
        File file = new File("data/newFile.data");
        try(RandomAccessFile raf = new RandomAccessFile(file,"r")){
            long startPoint=0;
            long endPoint=raf.length();
            long midPoint=startPoint+(endPoint-startPoint)/24;
            int tries = 100;
            boolean hit=false;
            while(tries>0&&!hit) {
                raf.seek(midPoint*12);
                raf.skipBytes(4);
                int shot = raf.readInt();
                if (shot == target){
                System.out.println("hit!");
                hit=true;}
                if (raf.readInt() < target)
                    startPoint = midPoint;
                if (raf.readInt() > target)
                    endPoint = midPoint;
                midPoint = (startPoint + (endPoint - startPoint) / 2)/12;
                while(midPoint%12!=0)
                    midPoint++;
                tries--;
            }
            long firstHit=raf.getFilePointer()-4;
            System.out.println(firstHit);
            raf.seek(firstHit-4);
            boolean stillHit = true;
            while(stillHit){
                raf.seek(raf.getFilePointer()-16);
                if(raf.readInt()!=target||raf.getFilePointer()<=16){
                    stillHit=false;
                    System.out.println(raf.getFilePointer());
                }
            }
            raf.seek(firstHit+8);
            boolean stillHitUpper=true;
            while(stillHitUpper){
                raf.seek(raf.getFilePointer()+8);
                if(raf.readInt()!=target||raf.getFilePointer()>=raf.length()-8){
                    stillHitUpper=false;
                    System.out.println(raf.getFilePointer());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
