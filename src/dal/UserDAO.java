package dal;
import be.User;
import bll.UserManager;
import java.io.*;
import java.util.ArrayList;

public class UserDAO {
    private static final String USER_DATA_SOURCE="data/users.txt";
    private ArrayList<User> userArrayList= new ArrayList<User>();
    private UserManager userParser;
    public UserDAO(UserManager userParser){
        this.userParser = userParser;
    }

    public ArrayList<User> getAllUsers() {
        File file = new File(USER_DATA_SOURCE);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                String line = br.readLine();
                while (line != null) {
                    if (!line.isEmpty())
                        userArrayList.add(userParser.parseUser(line));
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return userArrayList;
    }
}
