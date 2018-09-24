/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXTextField;
import dao.UserDao;
import entity.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class ForgetPasswordUIController implements Initializable {

    @FXML
    private JFXTextField txtUsername;
    @FXML
    private Label lblHint;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         lblHint.setText("");
    }    

    @FXML
    private void txtUsernameKR(KeyEvent event) {
        User getHint = UserDao.getHintToForgetPassword(txtUsername.getText().trim());
        
        if ( getHint != null ) {
            
            if (getHint.getHint() != null ) {
                
                lblHint.setText("Your hint is " + getHint.getHint() + ".");
                
            } else {
                
                lblHint.setText("Your hint is not entered.");
            }
            
        }else {
            
            lblHint.setText("");
            
        }
    }
    
}
