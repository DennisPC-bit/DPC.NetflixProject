package dal;
import be.User;
import bll.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

/*
 *
 *@author DennisPC-bit
 */

public class UserDAO {
    private static final String USER_DATA_SOURCE="data/users.txt";
    private ArrayList<User> userArrayList= new ArrayList<User>();
    private UserManager userParser;
    public UserDAO(UserManager userManager){
        this.userParser = userManager;
    }

    public ArrayList<User> loadUsers() {
        File file = new File(USER_DATA_SOURCE);
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line = br.readLine();
            while (line != null) {
                if (!line.isEmpty())
                    userArrayList.add(userParser.parseUser(line));
                line = br.readLine();
            }
        }
        catch (IOException e) {e.printStackTrace();}
        return userArrayList;
    }

    public int getUniqueUserId(){
        int index=1;
        userArrayList.sort(Comparator.comparingInt(User::getId));
        for(User user:userArrayList) {
            if (user.getId() == index)
                index++;
        }
        return index;
    }

    public ObservableList<User> searchForUser(String searchString) {
        ArrayList<User> userSearch = new ArrayList<>();
        File file = new File(USER_DATA_SOURCE);
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line = br.readLine();
            while (line != null) {
                if (!line.isEmpty() && line.toLowerCase().contains(searchString.toLowerCase()))
                    userSearch.add(userParser.parseUser(line));
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(userSearch);
    }
}
