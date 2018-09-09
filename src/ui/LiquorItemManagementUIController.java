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
//<editor-fold defaultstate="collapsed" desc="Operation Methods">
    @FXML
    private void btnAddAP(ActionEvent event) {
        String errors = getErrors();

        if (errors.isEmpty()) {

            String details
                    = "\nItem Code          \t\t: " + liquorItem.getCode()
                    + "\nItem Name        \t\t: " + liquorItem.getName()
                    + "\nItem Categoery  \t\t: " + liquorItem.getLiquoritemcategoryId().getName()
                    + "\nUnit Price       \t\t: " + liquorItem.getUnitprice()
                    + "\nDescription   \t\t: " + liquorItem.getDescription();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add Module");
            alert.setHeaderText("Are you sure you need to add the following Food Item???");
            alert.setContentText(details);

            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForConfirmation");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {

                // employee.setDisable(0);
                LiquorItemDao.add(liquorItem);

                loadForm();

                loadTable();

            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You need to fill the following Employee");
            alert.setContentText(errors);

            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForError");

            alert.showAndWait();

        }
    }

    @FXML
    private void btnClearAP(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialogForConfirmation");

        alert.setTitle(" Liquor Item Management");
        alert.setHeaderText("Clear Form");
        alert.setContentText("Are you sure you need to clear form?? ");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            loadForm();

        }
    }

    @FXML
    private void btnUpdateAP(ActionEvent event) {
        String errors = getErrors();

        if (errors.isEmpty()) {

//            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//
//            try {
//
//                employee.setDob(java.sql.Date.valueOf(format.format(format.parse(actualDob))));
//
//            } catch (ParseException ex) {
//
//                System.out.println(ex);
//
//            }
            String updates = getUpdates();

            if (!updates.isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                alert.setTitle("Update Module");
                alert.setHeaderText("Are you sure you need to update the following Module");
                alert.setContentText(updates);

                DialogPane dialogPane = alert.getDialogPane();

                dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialogForConfirmation");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                    //  Notification.Notifier.INSTANCE.notifySuccess("Update", employee.getName() + " is updated!");
                    LiquorItemDao.update(liquorItem);

                    loadForm();
                    loadTable();

                }

            } else {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Update Module");
                alert.setHeaderText("There is nothing to Update!!!");
                alert.setContentText("Nothing to Update!!!");

                DialogPane dialogPane = alert.getDialogPane();

                dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialogForInformation");

                alert.showAndWait();

            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error - Employee Update");
            alert.setHeaderText("Form Data Error");
            alert.setContentText(errors);

            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForError");

            alert.showAndWait();

        }
    }

    @FXML
    private void btnDeleteAP(ActionEvent event) {

        String details
                = "\nItem Code          \t\t: " + liquorItem.getCode()
                + "\nItem Name        \t\t: " + liquorItem.getName()
                + "\nItem Categoery  \t\t: " + liquorItem.getLiquoritemcategoryId().getName()
                + "\nUnit Price       \t\t: " + liquorItem.getUnitprice()
                + "\nDescription   \t\t: " + liquorItem.getDescription();

        Alert alertForDelete = new Alert(Alert.AlertType.CONFIRMATION);

        alertForDelete.setTitle("Delete Module");
        alertForDelete.setHeaderText("Are you sure you need to delete the following Module?");
        alertForDelete.setContentText(details);

        DialogPane dialogPane = alertForDelete.getDialogPane();

        dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialogForConfirmation");

        Optional<ButtonType> result = alertForDelete.showAndWait();

        if (result.get() == ButtonType.OK) {

//            EmployeeDao.delete(oldEmployee);
//oldFoodItem.setDisable(1);
            LiquorItemDao.delete(liquorItem);

//            fillTable(EmployeeDao.getAll());
            loadTable();

            loadForm();

            pagination.setCurrentPageIndex(page);
            tblLiquorItem.getSelectionModel().select(row);

        }
    }

    @FXML
    private void tblLiquorItemMC(MouseEvent event) {
        fillForm();
    }

    @FXML
    private void tblLiquorItemKR(KeyEvent event) {
        fillForm();
    }

    private String getErrors() {

        String errors = "";

        if (liquorItem.getName() == null) {
            errors = errors + "Name \t\tis Invalid\n";
        }

        if (liquorItem.getLiquoritemcategoryId() == null) {
            errors = errors + "FoodItem Category \t\tis Not Selected\n";
        }

        if (liquorItem.getCode() == null) {
            errors = errors + "Item Code \tis Not Selected\n";
        }

        if (liquorItem.getDescription() == null) {
            errors = errors + "Description \t\tis Invalid\n";
        }

        if (liquorItem.getUnitprice() == null) {
            errors = errors + "Unit Price  \t\tis Invalid \n";
        }

        return errors;

    }

    private String getUpdates() {

        String updates = "";

        if (oldLiquorItem != null) {

            if (liquorItem.getName() != null && !liquorItem.getName().equals(oldLiquorItem.getName())) {
                updates = updates + oldLiquorItem.getName() + " chnaged to " + liquorItem.getName() + "\n";
            }

            if (liquorItem.getCode() != null && !liquorItem.getCode().equals(oldLiquorItem.getCode())) {
                updates = updates + oldLiquorItem.getCode() + " chnaged to " + liquorItem.getCode() + "\n";
            }

            if (liquorItem.getUnitprice() != null && !liquorItem.getUnitprice().equals(oldLiquorItem.getUnitprice())) {
                updates = updates + oldLiquorItem.getUnitprice() + " chnaged to " + liquorItem.getUnitprice() + "\n";
            }

            if (liquorItem.getDescription() != null && !liquorItem.getDescription().equals(oldLiquorItem.getDescription())) {
                updates = updates + oldLiquorItem.getDescription() + " chnaged to " + liquorItem.getDescription() + "\n";
            }

        }

        return updates;

    }

    private void fillForm() {

        if (tblLiquorItem.getSelectionModel().getSelectedItem() != null) {

            dissableButtons(false, true, false, false);
            setStyle(valid);

            oldLiquorItem = LiquorItemDao.getById(tblLiquorItem.getSelectionModel().getSelectedItem().getId());
            liquorItem = LiquorItemDao.getById(tblLiquorItem.getSelectionModel().getSelectedItem().getId());

//            cmbSearchDesignation.setStyle(initial);
//            cmbSearchStatus.setStyle(initial);
//            txtSearchName.setStyle(initial);
            txtCode.setText(oldLiquorItem.getCode());
            txtItemName.setText(liquorItem.getName());
            cmbItemCategory.getSelectionModel().select((Liquoritemcategory) liquorItem.getLiquoritemcategoryId());
            txtDescription.setText(liquorItem.getDescription());
            txtUnitPrice.setText(liquorItem.getUnitprice().toString());

            page = pagination.getCurrentPageIndex();
            row = tblLiquorItem.getSelectionModel().getSelectedIndex();

//            oldNicWithOldFormateWithV = oldEmployee.getNic().substring(2, 7) + oldEmployee.getNic().substring(8) + "v";
//            oldNicWithOldFormateWithX = oldEmployee.getNic().substring(2, 7) + oldEmployee.getNic().substring(8) + "x";
//            lblDob.setText(oldEmployee.getDob().toString());
//            lblGender.setText(oldEmployee.getGenderId().getName());
        }

    }
//</editor-fold>

}
