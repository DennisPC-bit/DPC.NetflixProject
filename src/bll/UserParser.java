package bll;

import be.User;
import dal.UserDAO;
import gui.UserInterfaceController;
import java.util.ArrayList;

public class UserParser {
    private UserDAO userDAO=new UserDAO(this);
    private UserInterfaceController userInterfaceController;

    public UserParser(UserInterfaceController userInterfaceController){
        this.userInterfaceController=userInterfaceController;
    }

    public User parseUser(String input){
        String[] user=input.split(",");
        return new User(user[1], Integer.parseInt(user[0]));
    }

    public ArrayList<User> getAllUsers(){
        return userDAO.getAllUsers();
    }
}
