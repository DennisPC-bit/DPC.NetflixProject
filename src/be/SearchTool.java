package be;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SearchTool {
    public int binarySearch(int startPoint,int endPoint, int target){
        int midPoint=startPoint+(endPoint-startPoint)/2;
        while (midPoint!=target){
            System.out.println(midPoint);
            if(midPoint<target){
                startPoint=midPoint;
            }
            else if(midPoint>target){
                endPoint=midPoint;
            }
            midPoint=startPoint+(endPoint-startPoint)/2;
        }
        return midPoint;
    }

    public int binarySearch(int[] intArray, int target){
        try{
        int midPoint=intArray.length/2;
        int startPoint=0;
        int endPoint=intArray.length;
        boolean intNotInArray = false;
        while(intArray[midPoint]!=target && !intNotInArray){
            if(intArray[midPoint]<target) {
                startPoint=midPoint;
                if(intArray[midPoint]<target&&intArray[midPoint+1]>target)
                    intNotInArray=true;
            }
            if(intArray[midPoint]>target){
                endPoint=midPoint;

                if(intArray[midPoint]>target&&intArray[midPoint-1]<target)
                    intNotInArray=true;
            }
            midPoint=startPoint+(endPoint-startPoint)/2;
        }
        if(intNotInArray)
            return -1;
        else
        return midPoint;
        }catch(ArrayIndexOutOfBoundsException e){
            return -1;
        }
    }

    public int binarySearch(ArrayList<Integer> intArray, int target){
        try{
            int midPoint=intArray.size()/2;
            int startPoint=0;
            int endPoint=intArray.size();
            boolean intNotInArray = false;
            while(intArray.get(midPoint)!=target && !intNotInArray){
                if(intArray.get(midPoint)<target) {
                    startPoint=midPoint;
                    if(intArray.get(midPoint)<target&&intArray.get(midPoint+1)>target)
                        intNotInArray=true;
                }
                if(intArray.get(midPoint)>target){
                    endPoint=midPoint;

                    if(intArray.get(midPoint)>target&&intArray.get(midPoint-1)<target)
                        intNotInArray=true;
                }
                midPoint=startPoint+(endPoint-startPoint)/2;
            }
            if(intNotInArray)
                return -1;
            else
                return midPoint;
        }catch(ArrayIndexOutOfBoundsException e){
            return -1;
        }
    }

    public ArrayList<Integer> binarySearchArray(ArrayList<Integer> intArray, int target){
        ArrayList<Integer> indexes = new ArrayList<>();
        try{
            int midPoint=intArray.size()/2;
            int startPoint=0;
            int endPoint=intArray.size();
            boolean intNotInArray = false;
            while(!intNotInArray){
                if(intArray.get(midPoint)<target) {
                    startPoint=midPoint;
                    if(intArray.get(midPoint)<target&&intArray.get(midPoint+1)>target)
                        intNotInArray=true;
                }
                else if (intArray.get(midPoint)>target){
                    endPoint=midPoint;

                    if(intArray.get(midPoint)>target&&intArray.get(midPoint-1)<target)
                        intNotInArray=true;
                }
                midPoint=startPoint+(endPoint-startPoint)/2;
            }
            intArray.remove(midPoint);
            indexes.add(midPoint);
            return indexes;
        }catch(ArrayIndexOutOfBoundsException e){
            return indexes;
        }
    }

    public ArrayList<FilmRating> binarySearchFilmRatingArray(ArrayList<FilmRating> ratings, User user){
        ratings.sort(Comparator.comparingInt(FilmRating::getUserId));
        int startPoint=0;
        int endPoint=ratings.size()-1;
        int midPoint=startPoint+(endPoint-startPoint)/2;
        while(!ratings.isEmpty()) {
            if(ratings.get(midPoint).getUserId() == user.getId()) {
                break;
            }
            else if (ratings.get(midPoint).getUserId() < user.getId()) {
                startPoint = midPoint;
            }
            else if (ratings.get(midPoint).getUserId() > user.getId()) {
                endPoint = midPoint;
            }
            midPoint = startPoint + (endPoint - startPoint) / 2;
        }
        int firstHit=midPoint;
        while(firstHit>=0&&ratings.get(firstHit).getUserId()==user.getId())
            firstHit--;
        int startIndex=firstHit;
        while(midPoint<ratings.size()&&ratings.get(midPoint).getUserId()==user.getId())
            midPoint++;
        int endIndex=midPoint;

        ArrayList<FilmRating> fittingRatings = new ArrayList<>(ratings.subList(startIndex+1, endIndex));
        fittingRatings.sort(Comparator.comparingInt(FilmRating::getFilmId));
        return fittingRatings;
    }
}