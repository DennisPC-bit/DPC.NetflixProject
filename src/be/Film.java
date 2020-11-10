package be;


import javafx.beans.property.*;

public class Film {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty date;
    private StringProperty title;

    public Film(String title, int date, int id){
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

    public StringProperty getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setDate(int date) {
        this.date.set(date);
    }
}
