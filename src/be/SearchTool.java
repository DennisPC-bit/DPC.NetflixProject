package be;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;

/*
 *
 *@author DennisPC-bit
 */

public class SearchTool {

    public int binarySearch(int startPoint,int endPoint, int target){
        int midPoint=startPoint+(endPoint-startPoint)/2;
        while (midPoint!=target){
            System.out.println(midPoint);
            if(midPoint<target){
                startPoint=midPoint;
            }
            else endPoint=midPoint;
            midPoint=startPoint+(endPoint-startPoint)/2;
        }
        return midPoint;
    }

    public int binarySearch(int[] intArray, int target){
        int startPoint=0;
        int endPoint=intArray.length-1;
        int midPoint=startPoint+(endPoint-startPoint)/2;
        while(intArray[endPoint]<=target && intArray[0]>=target){
            if(intArray[midPoint]==target)
                return midPoint;
            else if(intArray[midPoint]<target&&intArray[midPoint+1]>target)
                break;
            else if(intArray[midPoint]<target)
                startPoint=midPoint;
            else if(intArray[midPoint]>target){
                endPoint=midPoint;
            }
            midPoint=startPoint+(endPoint-startPoint)/2;
        }
        return -1;
    }

    public int binarySearch(ArrayList<Integer> intArray, int target){
            int midPoint=intArray.size()/2;
            int startPoint=0;
            int endPoint=intArray.size()-1;
            while(target>=intArray.get(startPoint)&&target<=intArray.get(endPoint)){
                if(intArray.get(midPoint)<target&&intArray.get(midPoint+1)>target)
                    break;
                else if(intArray.get(midPoint)==target)
                    return midPoint;
                else if(intArray.get(midPoint)<target) {
                    startPoint=midPoint;
                }
                else if(intArray.get(midPoint)>target){
                    endPoint=midPoint;
                }
                midPoint=startPoint+(endPoint-startPoint)/2;
            }
            return -1;
    }

    public ArrayList<FilmRating> binarySearchFilmRatingArray(ArrayList<FilmRating> ratings, User user){
        ratings.sort(Comparator.comparingInt(FilmRating::getUserId));
        int startPoint=0;
        int endPoint=ratings.size()-1;
        int midPoint=startPoint+(endPoint-startPoint)/2;
        while(ratings.get(startPoint).getUserId()<= user.getId()&&ratings.get(endPoint).getUserId()>= user.getId()) {
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

    public ArrayList<FilmRating> binarySearchFilmRatingArray(ArrayList<FilmRating> ratings, int userId){
        ratings.sort(Comparator.comparingInt(FilmRating::getUserId));
        int startPoint=0;
        int endPoint=ratings.size()-1;
        int midPoint=startPoint+(endPoint-startPoint)/2;
        while(ratings.get(startPoint).getUserId()<=userId&&ratings.get(endPoint).getUserId()>=userId) {
            if(ratings.get(midPoint).getUserId() == userId) {
                break;
            }
            else if (ratings.get(midPoint).getUserId() < userId) {
                startPoint = midPoint;
            }
            else if (ratings.get(midPoint).getUserId() > userId) {
                endPoint = midPoint;
            }
            midPoint = startPoint + (endPoint - startPoint) / 2;
        }
        int firstHit=midPoint;
        while(firstHit>=0&&ratings.get(firstHit).getUserId() == userId)
            firstHit--;
        int startIndex=firstHit;
        while(midPoint<ratings.size()&&ratings.get(midPoint).getUserId() == userId)
            midPoint++;
        int endIndex=midPoint;

        ArrayList<FilmRating> fittingRatings = new ArrayList<>(ratings.subList(startIndex+1, endIndex));
        fittingRatings.sort(Comparator.comparingInt(FilmRating::getFilmId));
        return fittingRatings;
    }

    public Film binarySearchFilmArray(ObservableList<Film> films, int filmId){
        films.sort(Comparator.comparingInt(Film::getIntId));
        int startPoint=0;
        int endPoint=films.size()-1;
        int midPoint=startPoint+(endPoint-startPoint)/2;
        while(films.get(startPoint).getIntId()<=filmId&&films.get(endPoint).getIntId()>=filmId) {
            if(films.get(midPoint).getIntId() == filmId) {
                return films.get(midPoint);
            }else if(films.get(midPoint).getIntId() < filmId&&films.get(midPoint+1).getIntId() > filmId)
                break;
            else if (films.get(midPoint).getIntId() < filmId) {
                startPoint = midPoint;
            }
            else if (films.get(midPoint).getIntId() > filmId) {
                endPoint = midPoint;
            }
            midPoint = startPoint + (endPoint - startPoint) / 2;
        }
        return null;
    }
}