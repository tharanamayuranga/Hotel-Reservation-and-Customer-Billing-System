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

    
}
