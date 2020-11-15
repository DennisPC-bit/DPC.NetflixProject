package bll;
public class SearchTool {
    public int binarySearch(int startPoint,int endPoint, int target){
        int midPoint=startPoint+(endPoint-startPoint)/2;
        while (midPoint!=target){
            if(midPoint<target){
                startPoint=midPoint;
                midPoint=startPoint+(endPoint-startPoint)/2;
            }
            if(midPoint>target){
                endPoint=midPoint;
                midPoint=startPoint+(endPoint-startPoint)/2;
            }
        }
        return midPoint;
    }
}