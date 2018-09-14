/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXTextField;
import dao.SpaPackageCategoryDao;
import entity.Spapackage;
import entity.Spapackagecategory;
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
public class SpaPackageCategoryUIController implements Initializable {

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
    private ListView<Spapackagecategory> lstCategoryType;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Form-Data">
    Spapackagecategory spaCategory;
    Spapackagecategory oldSpaCategory;

    String initial;
    String valid;
    String invalid;
    String updated;

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Initializing-Mthods">
    
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

        spaCategory = new Spapackagecategory();
        oldSpaCategory = null;

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

        lstCategoryType.setItems(SpaPackageCategoryDao.getAll());
        lstCategoryType.refresh();

    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Binding-Methods">
    @FXML
    private void txtCustomerIDTypeKR(KeyEvent event) {
        List dename = SpaPackageCategoryDao.getAllByName(txtCategoryType.getText().trim());

        if (spaCategory.setName(txtCategoryType.getText().trim())) {

            if (dename.isEmpty()) {

                if (oldSpaCategory != null && !spaCategory.getName().equals(oldSpaCategory.getName())) {

                    txtCategoryType.setStyle(updated);

                } else {

                    txtCategoryType.setStyle(valid);

                }

            } else {

                if (oldSpaCategory != null && oldSpaCategory.getName().equals(spaCategory.getName())) {

                    txtCategoryType.setStyle(valid);

                } else {

                    txtCategoryType.setStyle(invalid);
                    spaCategory.setName(null);

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

            String details = "\nName :         \t\t" + spaCategory.getName();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add Designation");
            alert.setHeaderText("Are you sure you need to add the following Spa Package Category??????");
            alert.setContentText(details);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {

                SpaPackageCategoryDao.add(spaCategory);

                loadTable();
                loadForm();

            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You need to fill the following Spa Package Category");
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

        if (errors.isEmpty()) {

            String updates = getUpdates();

            if (!updates.isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                alert.setTitle("Update Spa Package category");
                alert.setHeaderText("Are you sure you need to update the following Spa Package Category List??????");
                alert.setContentText(updates);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                    SpaPackageCategoryDao.update(spaCategory);

                    loadForm();
                    loadTable();

                }

            } else {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Update Spa Package Category");
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

    @FXML
    private void btnDeleteAP(ActionEvent event) {
         String details = "\nName  \t: \t: " + oldSpaCategory.getName();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Designation");
        alert.setHeaderText("Are you sure you need to delete the following Spa Package Category?");
        alert.setContentText(details);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            SpaPackageCategoryDao.delete(spaCategory);

            loadTable();
            loadForm();

        }
    }
    private String getErrors() {

        String errors = "";

        if (spaCategory.getName() == null) {
            errors = errors + "Name \t\tis Invalid or already in\n";
        }

        return errors;

    }

    private String getUpdates() {

        String updates = "";

        if (oldSpaCategory != null) {

            if (spaCategory.getName() != null && !spaCategory.getName().equals(oldSpaCategory.getName())) {
                updates = updates + oldSpaCategory.getName() + " chnaged to " + spaCategory.getName() + "\n";
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

            oldSpaCategory = SpaPackageCategoryDao.getById(lstCategoryType.getSelectionModel().getSelectedItem().getId());
            spaCategory = SpaPackageCategoryDao.getById(lstCategoryType.getSelectionModel().getSelectedItem().getId());

            txtCategoryType.setText(oldSpaCategory.getName());

        }

    }
    
}
