/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXTextField;
import dao.DesignationDao;
import entity.Designation;
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
public class DesignationUIController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="Form-Data">
    
    Designation designation;
    Designation oldDesignation;
    
  
    String initial;
    String valid;
    String invalid;
    String updated;
    
  
    
//</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="FXML-Data">
    
    @FXML
    private JFXTextField txtDesignation;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private ListView<Designation> lstDesignation;
//</editor-fold>
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       initial = Style.initial;
       valid = Style.valid;
       invalid = Style.invalid;
       updated = Style.updated;
       
       loadForm();
       
       loadTable();
    } 
    private void loadForm() {
        
        designation = new Designation();
        oldDesignation = null;
        
        txtDesignation.setText("");
        
        dissableButtons(false, false, true, true);
        
        setStyle(initial);
        
    }
    
    private void setStyle(String style){
        
        txtDesignation.setStyle(style);
        
    }
    
    private void dissableButtons( boolean select , boolean insert , boolean update , boolean delete ){
        
        btnAdd.setDisable(insert);
        btnUpdate.setDisable(update);
        btnDelete.setDisable(delete);
        
    }
    
     private void loadTable(){
        
        lstDesignation.setItems(DesignationDao.getAll());
        lstDesignation.refresh();
        
    }

    
    

    @FXML
    private void txtDesignationKR(KeyEvent event) {
        
        List dename = DesignationDao.getAllByName(txtDesignation.getText().trim());
        
       if ( designation.setName(txtDesignation.getText().trim()) ) {

            if ( dename.isEmpty() ) {

                if ( oldDesignation != null && !designation.getName().equals(oldDesignation.getName()) ) {

                    txtDesignation.setStyle(updated);

                } else {

                    txtDesignation.setStyle(valid);

                }

            } else {

                if ( oldDesignation != null && oldDesignation.getName().equals(designation.getName()) ) {

                    txtDesignation.setStyle(valid);

                } else {

                  txtDesignation.setStyle(invalid);  
                  designation.setName(null);

                }

            }

        } else {

            txtDesignation.setStyle(invalid);

        }
        
    }

   

    
}
