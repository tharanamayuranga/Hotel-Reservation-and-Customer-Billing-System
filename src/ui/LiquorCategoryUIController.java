/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class LiquorCategoryUIController implements Initializable {

    @FXML
    private JFXTextField txtCustomerIDType;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private ListView<?> lstCustomerIDType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void txtCustomerIDTypeKR(KeyEvent event) {
    }

    @FXML
    private void btnAddAP(ActionEvent event) {
    }

    @FXML
    private void btnClearAP(ActionEvent event) {
    }

    @FXML
    private void btnUpdateAP(ActionEvent event) {
    }

    @FXML
    private void btnDeleteAP(ActionEvent event) {
    }

    @FXML
    private void lstCustomerIDTypeMC(MouseEvent event) {
    }
    
}
