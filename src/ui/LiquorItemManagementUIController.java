/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.LiquorItemCategoryDao;
import dao.LiquorItemDao;
import entity.Liquoritem;
import entity.Liquoritemcategory;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static ui.Main.stageBasic;

public class LiquorItemManagementUIController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="FXML-Data">
    @FXML
    private JFXTextField txtSearchByCode;
    @FXML
    private JFXTextField txtUnitPrice;
    @FXML
    private JFXButton btnNewLiquorItemCategory;
    @FXML
    private TextArea txtDescription;
    @FXML
    private JFXTextField txtCode;
    @FXML
    private JFXTextField txtItemName;
    @FXML
    private JFXComboBox<Liquoritemcategory> cmbItemCategory;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnClear;
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXTextField txtSearchByItem;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<Liquoritem> tblLiquorItem;
    @FXML
    private TableColumn<Liquoritem, String> colCode;
    @FXML
    private TableColumn<Liquoritem, String> colItem;
    @FXML
    private TableColumn<Liquoritem, Liquoritemcategory> colCategory;
    @FXML
    private JFXComboBox<Liquoritemcategory> cmbSearchByCategory;
    @FXML
    private JFXButton btnSearchClear;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Form Data">
    Liquoritem liquorItem;
    Liquoritem oldLiquorItem;

    Stage liquorItemCategoryStage;

    String initial;
    String valid;
    String invalid;
    String updated;

    int page;
    int row;

    //boolean photoSelected;
    BigDecimal unitCost;
    Stage liquorStage;
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

    public void loadForm() {

        liquorItem = new Liquoritem();
        oldLiquorItem = null;

        cmbItemCategory.setItems(LiquorItemCategoryDao.getAll());
        cmbItemCategory.getSelectionModel().clearSelection();

        txtCode.setText("");
        txtItemName.setText("");
        txtUnitPrice.setText("");
        txtDescription.setText("");

        dissableButtons(false, false, true, true);

        setStyle(initial);

    }

}
