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

    @FXML
    private void btnAddAP(ActionEvent event) {
         
        String errors = getErrors();
        
        
        if ( errors.isEmpty() ) {
            
            String details =  "\nName :         \t\t" + designation.getName();
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add Designation");
            alert.setHeaderText("Are you sure you need to add the following Designation??????");
            alert.setContentText(details);
            
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK) {
                
                DesignationDao.add(designation);
                
                loadTable();
                loadForm();
                
            }
            
            
        }else{
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You need to fill the following Designation");
            alert.setContentText(errors);
            alert.showAndWait();
            
        }

    }

    @FXML
    private void btnClearAP(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        
        alert.setTitle(" Designation Management");
        alert.setHeaderText("Clear Form");
        alert.setContentText("Are you sure you need to clear form ?????? ");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            
            loadForm();
            loadTable();
        }
    }

    @FXML
    private void btnUpdateAP(ActionEvent event) {
        String errors = getErrors();
        
        if ( errors.isEmpty() ) {
            
            String updates = getUpdates();
            
            if (!updates.isEmpty()) {
                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                
                alert.setTitle("Update Designation");
                alert.setHeaderText("Are you sure you need to update the following Designation List??????");
                alert.setContentText(updates);
                
                Optional<ButtonType> result = alert.showAndWait();
                
                if (result.get() == ButtonType.OK) {
                    
                    DesignationDao.update(designation);
                    
                    loadForm();
                    loadTable();
                    
                }
                
            } else {
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                
                alert.setTitle("Update Designation");
                alert.setHeaderText("There is nothing to Update!!!");
                alert.setContentText("Nothing to Update!!!");
                alert.showAndWait();
                
            }
            
        } else {
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            
            alert.setTitle("Error - Designation Update");
            alert.setHeaderText("Form Data Error");
            alert.setContentText(errors);
            alert.showAndWait();
            
        }
    }



    
}
