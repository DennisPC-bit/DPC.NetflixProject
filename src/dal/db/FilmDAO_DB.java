package dal.db;

import be.Film;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO_DB {

    private DbConnectionProvider dbConnectionProvider;

    public FilmDAO_DB() throws Exception {
        dbConnectionProvider = new DbConnectionProvider();
    }

    public List<Film> getAllFilms() throws Exception {
        ArrayList<Film> allFilms= new ArrayList<>();
        try(Connection connection = dbConnectionProvider.getConnection()){
            String sql = "SELECT * FROM Film;";

            Statement statement = connection.createStatement();

            if(statement.execute(sql)){
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()){
                    int id = resultSet.getInt("ID");
                    String title = resultSet.getString("TITLE");
                    int year = resultSet.getInt("YEAR");
                    Film film = new Film(id,year,title);
                    allFilms.add(film);
                }
            }

        }
        return allFilms;
    }
}
