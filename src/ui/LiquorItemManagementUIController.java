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
	private void setStyle(String style) {

        cmbItemCategory.setStyle(style);

        txtCode.setStyle(style);
        txtItemName.setStyle(style);
        txtUnitPrice.setStyle(style);
        txtDescription.setStyle(style);

        if (!txtDescription.getChildrenUnmodifiable().isEmpty()) {

            ((ScrollPane) txtDescription.getChildrenUnmodifiable().get(0)).getContent().setStyle(style);

        }

        cmbSearchByCategory.setStyle(style);
        txtSearchByCode.setStyle(style);
        txtSearchByItem.setStyle(style);

    }

    private void dissableButtons(boolean select, boolean insert, boolean update, boolean delete) {

        btnAdd.setDisable(insert);
        btnUpdate.setDisable(update);
        btnDelete.setDisable(delete);

    }

    private void loadTable() {

        cmbSearchByCategory.setItems(LiquorItemCategoryDao.getAll());
        cmbSearchByCategory.getSelectionModel().clearSelection();

        txtSearchByCode.setText("");
        txtSearchByItem.setText("");

        colCode.setCellValueFactory(new PropertyValueFactory("code"));
        colItem.setCellValueFactory(new PropertyValueFactory("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory("liquoritemcategoryId"));

//        tblEmployee.setRowFactory(new Callback<TableView<Employee>, TableRow<Employee>>() {
//
//            @Override
//            public TableRow<Employee> call(TableView<Employee> dateTableView) {
//
//                return new TableRow<Employee>() {
//
//                    @Override
//                    protected void updateItem(Employee date, boolean b) {
//                        super.updateItem(date, b);
//
//                        setStyle("-fx-background-color: linear-gradient(#04ef57 1%, #FFFFFF 100%);");
//
//                    }
//
//                };
//
//            }
//
//        });
        row = 0;
        page = 0;

        fillTable(LiquorItemDao.getAll());

        pagination.setCurrentPageIndex(0);

    }
	 private void fillTable(ObservableList<Liquoritem> employees) {

        if (employees != null && !employees.isEmpty()) {

            int rowsCount = 5;
            int pageCount = ((employees.size() - 1) / rowsCount) + 1;
            pagination.setPageCount(pageCount);

            pagination.setPageFactory((Integer pageIndex) -> {
                int start = pageIndex * rowsCount;
                int end = pageIndex == pageCount - 1 ? employees.size() : pageIndex * rowsCount + rowsCount;
                tblLiquorItem.getItems().clear();
                tblLiquorItem.setItems(FXCollections.observableArrayList(employees.subList(start, end)));
                return tblLiquorItem;
            });

        } else {

            pagination.setPageCount(1);
            tblLiquorItem.getItems().clear();

        }

        pagination.setCurrentPageIndex(page);
        tblLiquorItem.getSelectionModel().select(row);

    }
//</editor-fold>

 //<editor-fold defaultstate="collapsed" desc="Binding Methods">
    @FXML
    private void txtUnitPriceKR(KeyEvent event) {

        if (txtUnitPrice.getText().trim().matches("[0-9]{1}[0-9]*.[0-9]{1}[0-9]") || txtUnitPrice.getText().trim().matches("[0-9]{1}[0-9]*") || txtUnitPrice.getText().trim().matches("[0-9]{1}[0-9]*.[0-9]{1}") || txtUnitPrice.getText().trim().matches("[0-9]{1}[0-9]*.")) {

            unitCost = new BigDecimal(txtUnitPrice.getText().trim());//check it

            liquorItem.setUnitprice(toGetTextColor(txtUnitPrice.getText().trim()));

            if (oldLiquorItem != null && !liquorItem.getUnitprice().equals(oldLiquorItem.getUnitprice())) {

                txtUnitPrice.setStyle(updated);

            } else {

                txtUnitPrice.setStyle(valid);

            }

        } else {

            txtUnitPrice.setStyle(invalid);

            liquorItem.setUnitprice(null);

        }
    }

    private BigDecimal toGetTextColor(String string) {

        if (!string.contains(".") && !"".equals(txtUnitPrice.getText().trim())) {

            return new BigDecimal(string + ".00");

        } else if (string.contains(".") && !"".equals(txtUnitPrice.getText().trim())) {

            try {

                String[] x = string.split("[.]");

                if (x[1].length() == 2) {

                    return new BigDecimal(string);

                } else if (x[1].length() == 1) {

                    return new BigDecimal(string + "0");

                }

            } catch (Exception e) {

//                System.out.println(e);
                return new BigDecimal(string + "00");

            }

        }

        return new BigDecimal("0");

    }

    @FXML
    private void txtDescriptionKR(KeyEvent event) {
        if (liquorItem.setDescription(txtDescription.getText().trim())) {

            if (oldLiquorItem != null && !liquorItem.getDescription().equals(oldLiquorItem.getDescription())) {

                ((ScrollPane) txtDescription.getChildrenUnmodifiable().get(0)).getContent().setStyle(updated);

            } else {

                ((ScrollPane) txtDescription.getChildrenUnmodifiable().get(0)).getContent().setStyle(valid);

            }

        } else {

            ((ScrollPane) txtDescription.getChildrenUnmodifiable().get(0)).getContent().setStyle(invalid);

        }
    }

    @FXML
    private void txtCodeKR(KeyEvent event) {

        if (liquorItem.setCode(txtCode.getText().trim())) {

            if (oldLiquorItem != null && !liquorItem.getCode().equals(oldLiquorItem.getCode())) {

                txtCode.setStyle(updated);

            } else {

                txtCode.setStyle(valid);

            }

        } else {

            txtCode.setStyle(invalid);

        }
    }

    @FXML
    private void txtItemNameKR(KeyEvent event) {

        if (liquorItem.setName(txtItemName.getText().trim())) {

            if (oldLiquorItem != null && !liquorItem.getName().equals(oldLiquorItem.getName())) {

                txtItemName.setStyle(updated);

            } else {

                txtItemName.setStyle(valid);

            }

        } else {

            txtItemName.setStyle(invalid);

        }
    }

    @FXML
    private void cmbItemCategoryAP(ActionEvent event) {
        liquorItem.setLiquoritemcategoryId(cmbItemCategory.getSelectionModel().getSelectedItem());

        if (oldLiquorItem != null && !liquorItem.getLiquoritemcategoryId().equals(oldLiquorItem.getLiquoritemcategoryId())) {

            cmbItemCategory.setStyle(updated);

        } else {

            cmbItemCategory.setStyle(valid);

        }
    }

//</editor-fold>

}
