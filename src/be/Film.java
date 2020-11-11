package be;


import javafx.beans.property.*;

public class Film {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty date;
    private StringProperty title;
    private double avgRating;
    private int ratingCumulated;
    private int ratings;

    public Film(String title, int date, int id){
        this.ratingCumulated=0;
        this.ratings=0;
        this.title = new SimpleStringProperty(title);
        this.date = new SimpleIntegerProperty(date);
        this.id = new SimpleIntegerProperty(id);
    }

    public ObjectProperty<Integer> getDate() {
        return this.date.asObject();
    }

    public ObjectProperty<Integer> getId() {
        return this.id.asObject();
    }

    public int getIntId(){return this.id.intValue();}

    public StringProperty getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setDate(int date) {
        this.date.set(date);
    }

    public void addRating(int rating){
        this.ratings++;
        this.ratingCumulated+=rating;
        this.avgRating = (float)this.ratingCumulated/this.ratings;
    }
    public double getAvgRating(){
        return avgRating;
    }
}
