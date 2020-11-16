package gui.Dialogs;

import be.InputAlert;
import be.User;
import gui.UserInterfaceController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LogInScreenController {
    public TextField searchUser;
    public TableView userTable;
    public TableColumn<User,String> userTableColumn;
    @FXML
    private TextField idField;
    @FXML
    private PasswordField passWordField;
    private int userId;
    private UserInterfaceController userInterfaceController = new UserInterfaceController();
    private final InputAlert inputAlert = new InputAlert();
    private User selectedUser;

    public void initialize(){
        this.userTable.setItems(userInterfaceController.searchForUsers(""));

        this.userTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.selectedUser = (User) newValue;
            if(selectedUser!=null) {idField.setText(String.valueOf(selectedUser.getId()));}
        });

        this.userTableColumn.setCellValueFactory(cellData -> cellData.getValue().toSimpleStringProperty());
    }

    public void setUserInterfaceController(UserInterfaceController userInterfaceController){
        this.userInterfaceController=userInterfaceController;
    }

    public int getUserId(){
        return userId;
    }

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

    public void searchForUser() {
        if(searchUser!=null&&!searchUser.getText().equals("")){
            try {
                this.userTable.setItems(userInterfaceController.searchForUsers(searchUser.getText().toString()));}
            catch(IllegalArgumentException e){e.printStackTrace();}
        }
    }
}
