package be;

public class FilmRating {
    private int rating;
    private int filmId;
    private int userId;

    public FilmRating(int filmId, int userId,int rating) {
        this.filmId=filmId;
        this.userId=userId;
        this.rating=rating;
    }

    public int getUserId() {
        return userId;
    }

    public int getRating() {
        return rating;
    }

    public int getFilmId() {return filmId;}

    public void setRating(int rating) {
        this.rating = rating;
    }
}
