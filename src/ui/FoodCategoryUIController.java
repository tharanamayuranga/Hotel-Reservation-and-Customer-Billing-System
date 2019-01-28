/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXTextField;
import dao.FoodItemCategoryDao;
import dao.LiquorItemCategoryDao;
import entity.Fooditemcategory;
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
public class FoodCategoryUIController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="FXML-Data">
    @FXML
    private JFXTextField txtCategoryType;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private ListView<Fooditemcategory> lstCategoryType;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Form-Data">
    Fooditemcategory foodCategory;
    Fooditemcategory oldFoodCategory;

    String initial;
    String valid;
    String invalid;
    String updated;

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Initialize Methods">
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

        foodCategory = new Fooditemcategory();
        oldFoodCategory = null;

        txtCategoryType.setText("");

        dissableButtons(false, false, true, true);

        setStyle(initial);

    }

    private void setStyle(String style) {

        txtCategoryType.setStyle(style);

    }

    private void dissableButtons(boolean select, boolean insert, boolean update, boolean delete) {

        btnAdd.setDisable(insert);
        btnUpdate.setDisable(update);
        btnDelete.setDisable(delete);

    }

    private void loadTable() {

        lstCategoryType.setItems(FoodItemCategoryDao.getAll());
        lstCategoryType.refresh();

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Binding-Methods">
    @FXML
    private void txtCategoryTypeKR(KeyEvent event) {
        
        List dename = FoodItemCategoryDao.getAllByName(txtCategoryType.getText().trim());
        
        if (foodCategory.setName(txtCategoryType.getText().trim())) {
            
            if (dename.isEmpty()) {
                
                if (oldFoodCategory != null && !foodCategory.getName().equals(oldFoodCategory.getName())) {
                    
                    txtCategoryType.setStyle(updated);
                    
                } else {
                    
                    txtCategoryType.setStyle(valid);
                    
                }
                
            } else {
                
                if (oldFoodCategory != null && oldFoodCategory.getName().equals(foodCategory.getName())) {
                    
                    txtCategoryType.setStyle(valid);
                    
                } else {
                    
                    txtCategoryType.setStyle(invalid);
                    foodCategory.setName(null);
                    
                }
                
            }
            
        } else {
            
            txtCategoryType.setStyle(invalid);
            
        }
    }
//</editor-fold>
    
    @FXML
    private void btnAddAP(ActionEvent event) {
        String errors = getErrors();

        if (errors.isEmpty()) {

            String details = "\nName :         \t\t" + foodCategory.getName();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add Food Category");
            alert.setHeaderText("Are you sure you need to add the following Food Category??????");
            alert.setContentText(details);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {

                FoodItemCategoryDao.add(foodCategory);

                loadTable();
                loadForm();

            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You need to fill the following Food Category");
            alert.setContentText(errors);
            alert.showAndWait();

        }
    }

    @FXML
    private void btnClearAP(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(" Food Category Management");
        alert.setHeaderText("Clear Form");
        alert.setContentText("Are you sure you need to clear form ?????? ");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            loadForm();
            loadTable();
        }
    }
//<editor-fold defaultstate="collapsed" desc="Operation-Methods">
    
    @FXML
    private void btnUpdateAP(ActionEvent event) {
        String errors = getErrors();
        
        if (errors.isEmpty()) {
            
            String updates = getUpdates();
            
            if (!updates.isEmpty()) {
                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                
                alert.setTitle("Update Liquor category");
                alert.setHeaderText("Are you sure you need to update the following Liquor Category List??????");
                alert.setContentText(updates);
                
                Optional<ButtonType> result = alert.showAndWait();
                
                if (result.get() == ButtonType.OK) {
                    
                    FoodItemCategoryDao.update(foodCategory);
                    
                    loadForm();
                    loadTable();
                    
                }
                
            } else {
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                
                alert.setTitle("Update Food Category");
                alert.setHeaderText("There is nothing to Update!!!");
                alert.setContentText("Nothing to Update!!!");
                alert.showAndWait();
                
            }
            
        } else {
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            
            alert.setTitle("Error - Food Category Update");
            alert.setHeaderText("Form Data Error");
            alert.setContentText(errors);
            alert.showAndWait();
            
        }
    }
    
    @FXML
    private void btnDeleteAP(ActionEvent event) {
        String details = "\nName  \t: \t: " + oldFoodCategory.getName();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Food Category");
        alert.setHeaderText("Are you sure you need to delete the following Food Category?");
        alert.setContentText(details);
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            
            FoodItemCategoryDao.delete(oldFoodCategory);
            
            loadTable();
            loadForm();
            
        }
    }
    
    private String getErrors() {
        
        String errors = "";
        
        if (foodCategory.getName() == null) {
            errors = errors + "Name \t\tis Invalid or already in\n";
        }
        
        return errors;
        
    }
    
    private String getUpdates() {
        
        String updates = "";
        
        if (oldFoodCategory != null) {
            
            if (foodCategory.getName() != null && !foodCategory.getName().equals(oldFoodCategory.getName())) {
                updates = updates + oldFoodCategory.getName() + " chanaged to " + foodCategory.getName() + "\n";
            }
            
        }
        
        return updates;
        
    }
    
    @FXML
    private void lstCategoryTypeMC(MouseEvent event) {
        fillForm();
    }
    
    @FXML
    private void lstCategoryTypeKR(KeyEvent event) {
        fillForm();
    }
    
    private void fillForm() {
        
        if (lstCategoryType.getSelectionModel().getSelectedItem() != null) {
            
            dissableButtons(false, true, false, false);
            setStyle(valid);
            
            oldFoodCategory = FoodItemCategoryDao.getById(lstCategoryType.getSelectionModel().getSelectedItem().getId());
            foodCategory = FoodItemCategoryDao.getById(lstCategoryType.getSelectionModel().getSelectedItem().getId());
            
            txtCategoryType.setText(oldFoodCategory.getName());
            
        }
        
    }
//</editor-fold>

}
