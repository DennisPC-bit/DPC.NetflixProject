package bll;
public class SearchTool {
    public void binarySearch(int startPoint,int endPoint, int target, int tries){
        while (tries>0){
            int midPoint=(endPoint-startPoint)/2;
            if(midPoint<target){
                midPoint=midPoint+(midPoint-startPoint)/2;
                System.out.println(midPoint);
                tries--;
            }
            if(midPoint>target){
                startPoint=midPoint+(midPoint-startPoint)/2;
                System.out.println(midPoint);
                tries--;
            }
        }
    }
}