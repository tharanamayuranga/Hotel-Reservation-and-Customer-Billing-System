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
    
    //<editor-fold defaultstate="collapsed" desc="Initializing Methods">
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
        
        idType = new Idtype();
        oldIdType = null;
        
        txtCustomerIDType.setText("");
        
        dissableButtons(false, false, true, true);
        
        setStyle(initial);
        
    }
    
    private void setStyle(String style){
        
        txtCustomerIDType.setStyle(style);
        
    }
    
    private void dissableButtons( boolean select , boolean insert , boolean update , boolean delete ){
        
        btnAdd.setDisable(insert);
        btnUpdate.setDisable(update);
        btnDelete.setDisable(delete);
        
    }
    
     private void loadTable(){
        
        lstCustomerIDType.setItems(IDTypeDao.getAll());
        lstCustomerIDType.refresh();
        
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Binding Methods">
    @FXML
    private void txtCustomerIDTypeKR(KeyEvent event) {
        
           List dename = IDTypeDao.getAllByName(txtCustomerIDType.getText().trim());
        
       if ( idType.setName(txtCustomerIDType.getText().trim()) ) {

            if ( dename.isEmpty() ) {

                if ( oldIdType != null && !idType.getName().equals(oldIdType.getName()) ) {

                    txtCustomerIDType.setStyle(updated);

                } else {

                    txtCustomerIDType.setStyle(valid);

                }

            } else {

                if ( oldIdType != null && oldIdType.getName().equals(idType.getName()) ) {

                    txtCustomerIDType.setStyle(valid);

                } else {

                  txtCustomerIDType.setStyle(invalid);  
                  idType.setName(null);

                }

            }

        } else {

            txtCustomerIDType.setStyle(invalid);

        }
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Operation Methods">
    @FXML
    private void btnAddAP(ActionEvent event) {
         String errors = getErrors();
        
        
        if ( errors.isEmpty() ) {
            
            String details =  "\nName :         \t\t" + idType.getName();
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add ID Type");
            alert.setHeaderText("Are you sure you need to add the following ID Type??????");
            alert.setContentText(details);
            
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK) {
                
                IDTypeDao.add(idType);
                
                loadTable();
                loadForm();
                
            }
            
            
        }else{
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You need to fill the following ID Type");
            alert.setContentText(errors);
            alert.showAndWait();
            
        }
    }
    
    @FXML
    private void btnClearAP(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        
        alert.setTitle(" ID Type Management");
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
                
                alert.setTitle("Update ID Type");
                alert.setHeaderText("Are you sure you need to update the following Designation List??????");
                alert.setContentText(updates);
                
                Optional<ButtonType> result = alert.showAndWait();
                
                if (result.get() == ButtonType.OK) {
                    
                    IDTypeDao.update(idType);
                    
                    loadForm();
                    loadTable();
                    
                }
                
            } else {
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                
                alert.setTitle("Update ID Type");
                alert.setHeaderText("There is nothing to Update!!!");
                alert.setContentText("Nothing to Update!!!");
                alert.showAndWait();
                
            }
            
        } else {
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            
            alert.setTitle("Error - ID Type Update");
            alert.setHeaderText("Form Data Error");
            alert.setContentText(errors);
            alert.showAndWait();
            
        }
    }
    
    @FXML
    private void btnDeleteAP(ActionEvent event) {
         String details = "\nName  \t: \t: " + oldIdType.getName();
        
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Designation");
        alert.setHeaderText("Are you sure you need to delete the following ID Type?");
        alert.setContentText(details);
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            
            IDTypeDao.delete(oldIdType);
            
            loadTable();
            loadForm();
            
        }
    }
    
    @FXML
    private void lstCustomerIDTypeMC(MouseEvent event) {
        fillForm();
    }
    @FXML
    private void lstCustomerIDTypeKR(KeyEvent event) {
        fillForm();
    }
    private void fillForm(){
        
        if (lstCustomerIDType.getSelectionModel().getSelectedItem() != null) {
            
            dissableButtons(false, true, false, false);
            setStyle(valid);
            
            oldIdType = IDTypeDao.getById(lstCustomerIDType.getSelectionModel().getSelectedItem().getId());
            idType = IDTypeDao.getById(lstCustomerIDType.getSelectionModel().getSelectedItem().getId());
            
            txtCustomerIDType.setText(oldIdType.getName());
            
            
        }
        
    }
    private String getErrors(){
        
        String errors = "";
        
        if (idType.getName() == null) {
            errors = errors + "Name \t\tis Invalid or already in\n";
        }
        
        return errors;
        
    }
        private String getUpdates(){
        
        String updates = "";
        
        if (oldIdType != null) {
            
            if (idType.getName() != null && !idType.getName().equals(oldIdType.getName())) {
                updates = updates + oldIdType.getName() + " chanaged to " + idType.getName() + "\n";
            }
            
        }
        
        return updates;
        
    }
//</editor-fold>

    
    
}
