package bll;

import be.Film;

public class FilmParser {
    private String filmData;
    private String[] film;

    public Film parseFilm(String filmData) {
        this.filmData = filmData;
        if (filmData != null) {
            film = filmData.split(",");
            if (film.length > 3)
                for (int i = 3; i < film.length; i++)
                    film[2] += " " + film[i];
        }

        return new Film(film[2],Integer.parseInt(film[1]),Integer.parseInt(film[0]));
    }
}
