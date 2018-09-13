/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXTextField;
import dao.IDTypeDao;
import entity.Idtype;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class CustomerIDTypeUIController implements Initializable {
    
    //<editor-fold defaultstate="collapsed" desc="Form-Data">
    Idtype idType;
    Idtype oldIdType;
    
    
    String initial;
    String valid;
    String invalid;
    String updated;
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="FXML_Data">
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
    private ListView<Idtype> lstCustomerIDType;
    
//</editor-fold>
    


    
    
}
