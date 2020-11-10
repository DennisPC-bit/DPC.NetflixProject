package be;

public class FilmRating {
    private int rating;
    private Film movie;

    public FilmRating(int rating, Film movie)
    {
        this.rating=rating;
        this.movie=movie;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Film getMovie() {
        return movie;
    }
}
