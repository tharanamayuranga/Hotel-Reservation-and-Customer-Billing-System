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
    


    
    
}
