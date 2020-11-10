package be;

public class FilmRating {
    private int rating;
    private Film film;

    public FilmRating(int rating, Film film)
    {
        this.rating=rating;
        this.film=film;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Film getFilm() {
        return film;
    }
}
