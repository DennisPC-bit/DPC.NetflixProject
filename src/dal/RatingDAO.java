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
    private final RatingsManager ratingsManager;
    private static final String RATINGS_DATA_SOURCE="data/ratings.txt";
    private static final String DATA_RATINGS_RECENT ="data/ratings_recent.data";
    private ArrayList<FilmRating> ratingsArrayList= new ArrayList<>();
    private boolean printMode =true;

    public RatingDAO(RatingsManager ratingsManager){
        this.ratingsManager = ratingsManager;
    }

    public void setPrintMode(boolean printMode) {
        this.printMode = printMode;
    }

    public int getUsersRatingFromFile(Film film){
        File file = new File(DATA_RATINGS_RECENT);
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

    public void setUsersRatingInFile(Film film, User user, int rating){
        File file = new File(DATA_RATINGS_RECENT);
        try(RandomAccessFile raf = new RandomAccessFile(file,"rws")){
            boolean overWritten=false;
            while(raf.getFilePointer()<raf.length()&&!overWritten){
                if(raf.readInt()==film.getIntId()){
                    raf.skipBytes(4);
                    raf.writeInt(rating);
                    overWritten=true;
                    if(printMode){
                        System.out.println("Film overwritten");
                        System.out.printf("%-10s%-10s%3s%n",film.getId().getValue(),user.getId(),rating);}
                }
                else raf.skipBytes(8);
            }
            if(!overWritten){
                if(printMode){
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

    public void initUsersRatingsToFile(User user){
        File file = new File(DATA_RATINGS_RECENT);
        try(RandomAccessFile raf = new RandomAccessFile(file,"rw")){
            raf.setLength(0);
            for(FilmRating filmRating: search(user)){
                raf.writeInt(filmRating.getFilmId());
                raf.writeInt(filmRating.getUserId());
                raf.writeInt(filmRating.getRating());
            }
            raf.seek(0);
            if(printMode){
            System.out.println("Persons ratings:");
            String format = "%-10s%-10s%3s%4d%n";
            System.out.printf(format,"FilmId", "UserId", "★",42);
            int i=1;
            while(raf.getFilePointer()<raf.length()){
                System.out.printf(format,raf.readInt(), raf.readInt(), raf.readInt(),i);
                i++;
            }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<FilmRating> search(User user){
        return searchTool.binarySearchFilmRatingArray(ratingsArrayList,user.getId());
    }

    public void saveUsersRatingInMemory(User user){
        File file = new File(DATA_RATINGS_RECENT);
        ratingsArrayList.removeIf(filmRating -> filmRating.getUserId() == user.getId());
        try(RandomAccessFile raf = new RandomAccessFile(file,"rw")) {
            while (raf.getFilePointer() < raf.length()) {
                ratingsArrayList.add(new FilmRating(raf.readInt(),raf.readInt(), raf.readInt()));
            }
            if(printMode){
                System.out.println("saved!");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public ArrayList<FilmRating> findFilmRatingInDataFile(int target){
        ArrayList<FilmRating> ratingTest = new ArrayList<>();
        File file = new File("data/newFile.data");
        try(RandomAccessFile raf = new RandomAccessFile(file,"r")){
            long startPoint=0;
            long endPoint=raf.length();
            long midPoint=startPoint+(endPoint-startPoint)/2;
            int tries = 100;
            boolean hit=false;
            while(tries>0&&!hit) {
                raf.seek(midPoint);
                raf.skipBytes(4);
                int shot = raf.readInt();
                if (shot == target){
                    if(printMode)
                    System.out.println("hit!");
                    hit=true;}
                if (shot < target)
                    startPoint = midPoint;
                if (shot > target)
                    endPoint = midPoint;
                midPoint = (startPoint + (endPoint - startPoint) / 2);
                while(midPoint%12!=0)
                    midPoint++;
                tries--;
            }
            if(hit){
                long firstHit=raf.getFilePointer();
                if(printMode)
                System.out.println(firstHit);
                raf.seek(firstHit);
                long startIndex=0;
                long endIndex=0;
                while(true){
                    raf.seek(raf.getFilePointer()-16);
                    if(raf.readInt()!=target||raf.getFilePointer()<=16){
                        startIndex=raf.getFilePointer();
                        if(printMode)
                        System.out.println(startIndex);
                        break;
                    }
                }
                raf.seek(firstHit);
                while(true){
                    raf.skipBytes(8);
                    long lol = raf.readInt();
                    if(lol!=target||raf.getFilePointer()>=raf.length()-8){
                        endIndex=raf.getFilePointer()-8;
                        if(printMode)
                        System.out.println(endIndex);
                        break;
                    }
                }
                raf.seek(startIndex-8);
                while(raf.getFilePointer()<endIndex)
                    ratingTest.add(ratingsManager.parseRatingFromBinary(raf.readInt(),raf.readInt(),raf.readInt()));
                if(printMode){
                for(FilmRating filmRating : ratingTest)
                    System.out.println(filmRating.getFilmId() + " " + filmRating.getUserId() + " " + filmRating.getRating());
                }
                return ratingTest;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void makeTailFile(int c){
        File file = new File("data/newFileTail.data");
        File file2 = new File("data/newFile.data");
        try(RandomAccessFile raf = new RandomAccessFile(file,"rw");
            RandomAccessFile rafOrg = new RandomAccessFile(file,"rw")){
            rafOrg.seek(c);
            while(raf.getFilePointer()<raf.length()-c)
                raf.writeInt(rafOrg.readInt());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}