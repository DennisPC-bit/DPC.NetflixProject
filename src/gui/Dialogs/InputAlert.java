package gui.Dialogs;

public class InputAlert {
    public void showAlert(String message){
        javafx.scene.control.Alert alert= new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(message);
        alert.setHeaderText(message);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
