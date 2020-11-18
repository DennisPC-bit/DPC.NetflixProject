package be;

import java.util.ArrayList;

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
}