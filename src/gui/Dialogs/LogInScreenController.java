package gui.Dialogs;

import be.InputAlert;
import be.User;
import gui.UserInterfaceController;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LogInScreenController {
    private UserInterfaceController userInterfaceController = new UserInterfaceController();
    private final InputAlert inputAlert = new InputAlert();
    @FXML
    private TextField searchUser;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User,String> userTableColumn;
    @FXML
    private TextField idField;
    @FXML
    private PasswordField passWordField;
    private int userId;
    private User selectedUser;
    private String INVALID_ID_ALERT = "Invalid ID";

    public void initialize(){
        this.userTable.setItems(userInterfaceController.searchForUsers(""));

        this.userTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.selectedUser = (User) newValue;
            if(selectedUser!=null) {idField.setText(String.valueOf(selectedUser.getId()));}
        });

        this.userTableColumn.setCellValueFactory(cellData -> cellData.getValue().toSimpleStringProperty());
    }

    public int getUserId(){
        return userId;
    }

    public void setUserInterfaceController(UserInterfaceController userInterfaceController){
        this.userInterfaceController=userInterfaceController;
    }

    public void confirmButton() {
        if(idField!=null){
            try {
            userId=Integer.parseInt(idField.getText());
            boolean match=false;
            for(User user: userInterfaceController.getUsers()){
                if(userId==user.getId()){
                    match=true;
                    userInterfaceController.setUser(user);
                    userInterfaceController.closeWindow();
                    break;
                }
            }
            if(!match)
            inputAlert.showAlert(INVALID_ID_ALERT);
        } catch(NumberFormatException e){inputAlert.showAlert(INVALID_ID_ALERT);}
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
