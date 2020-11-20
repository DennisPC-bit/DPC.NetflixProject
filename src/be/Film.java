package be;
import javafx.beans.property.*;

/*
 *
 *@author DennisPC-bit
 */

public class Film {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty date;
    private StringProperty title;

    public Film(int id, int date,String title){
        this.title = new SimpleStringProperty(title);
        this.date = new SimpleIntegerProperty(date);
        this.id = new SimpleIntegerProperty(id);
    }

    public ObjectProperty<Integer> getId() {
        return this.id.asObject();
    }
    public int getIntId(){return this.id.intValue();}
    public ObjectProperty<Integer> getDate() {
        return this.date.asObject();
    }
    public StringProperty getTitle() {
        return this.title;
    }
    public void setDate(int date) {
        this.date.set(date);
    }
    public void setTitle(String title) {
        this.title.set(title);
    }
}
