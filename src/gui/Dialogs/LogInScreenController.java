package gui.Dialogs;

import be.InputAlert;
import be.User;
import gui.UserInterfaceController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInScreenController {
    @FXML
    private TextField idField;
    @FXML
    private PasswordField passWordField;
    private int userId;
    private UserInterfaceController userInterfaceController;
    private final InputAlert inputAlert = new InputAlert();

    public void confirmButton(ActionEvent actionEvent) {
        if(idField!=null){
            try {
            userId=Integer.parseInt(idField.getText());
            boolean match=false;
            for(User user: userInterfaceController.getUsers()){
                if(userId==user.getId()){
                    match=true;
                    userInterfaceController.setUser(user);
                    userInterfaceController.CloseChangeUserStage();
                    break;
                }
            }
            if(!match)
            inputAlert.showAlert("Invalid ID");
        } catch(NumberFormatException e){inputAlert.showAlert("Invalid ID");}
        }
    }

    public int getUserId(){
        return userId;
    }

    public void setUserInterfaceController(UserInterfaceController userInterfaceController){
        this.userInterfaceController=userInterfaceController;
    }
}
