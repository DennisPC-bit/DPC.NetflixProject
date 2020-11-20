package be;

/*
 *
 *@author DennisPC-bit
 */

import javafx.scene.control.Alert;

public class InputAlert {
    public void showAlert(String message){
        javafx.scene.control.Alert alert= new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(message);
        alert.setHeaderText(message);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public boolean deleteAlert(String deleteConfirmationMessage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(deleteConfirmationMessage);
        alert.setTitle(deleteConfirmationMessage);
        alert.setHeaderText(deleteConfirmationMessage);
        alert.showAndWait();
        if(alert.getResult().getText().equals("OK"))
            return true;
        else
            return false;
    }
}
