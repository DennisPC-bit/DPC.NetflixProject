package bll;

import be.User;

public class UserParser {
    public User parseUser(String input){
        String[] user=input.split(",");
        return new User(user[1], Integer.parseInt(user[0]));
    }
}
