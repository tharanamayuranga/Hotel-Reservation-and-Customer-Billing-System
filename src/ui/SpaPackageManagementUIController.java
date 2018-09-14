/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class SpaPackageManagementUIController implements Initializable {

    @FXML
    private JFXTextField txtSearchByCode;
    @FXML
    private JFXTextField txtPackagePrice;
    @FXML
    private JFXButton btnNewFoodItemCategory;
    @FXML
    private TextArea txtDescription;
    @FXML
    private JFXTextField txtCode;
    @FXML
    private JFXTextField txtPackageName;
    @FXML
    private JFXComboBox<?> cmbPackageCategory;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnClear;
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXTextField txtSearchByPackage;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<?> tblPackage;
    @FXML
    private TableColumn<?, ?> colCode;
    @FXML
    private TableColumn<?, ?> colItem;
    @FXML
    private TableColumn<?, ?> colCategory;
    @FXML
    private JFXComboBox<?> cmbSearchByCategory;
    @FXML
    private JFXButton btnSearchClear;

       //<editor-fold defaultstate="collapsed" desc="Form-Data">
    Spapackage spaPackage;
    Spapackage oldSpaPackage;

    Stage spaPackageCategoryStage;

    String initial;
    String valid;
    String invalid;
    String updated;

    int page;
    int row;

    //boolean photoSelected;
    BigDecimal packageCost;

    //public static File lastDirectory;
//</editor-fold>
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void txtSearchByCodeKR(KeyEvent event) {
    }

    @FXML
    private void txtUnitPriceKR(KeyEvent event) {
    }

    @FXML
    private void btnNewFoodItemCategoryAP(ActionEvent event) {
    }

    @FXML
    private void txtDescriptionKR(KeyEvent event) {
    }

    @FXML
    private void txtCodeKR(KeyEvent event) {
    }

    @FXML
    private void txtItemNameKR(KeyEvent event) {
    }

    @FXML
    private void cmbItemCategoryAP(ActionEvent event) {
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
    private void txtSearchByItemKR(KeyEvent event) {
    }

    @FXML
    private void tblFoodItemMC(MouseEvent event) {
    }

    @FXML
    private void tblFoodItemKR(KeyEvent event) {
    }

    @FXML
    private void cmbSearchByCategoryAP(ActionEvent event) {
    }

    @FXML
    private void btnSearchClearAP(ActionEvent event) {
    }
    
}
