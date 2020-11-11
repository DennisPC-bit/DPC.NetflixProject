package be;

public class FilmRating {
    private int rating;
    private Film film;
    private int id;

    public FilmRating(int rating, Film film, int id)
    {
        this.rating=rating;
        this.film=film;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public Film getFilm() {return film;}

    public void setRating(int rating) {
        this.rating = rating;
    }
}
