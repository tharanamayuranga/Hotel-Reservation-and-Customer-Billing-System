/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.FoodItemCategoryDao;
import dao.FoodItemDao;
import entity.Fooditem;
import entity.Fooditemcategory;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class FoodItemManagementUIController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="FXML-Data">
    @FXML
    private JFXTextField txtUnitPrice;
    @FXML
    private JFXTextField txtCode;
    @FXML
    private JFXTextField txtItemName;
    @FXML
    private JFXComboBox<Fooditemcategory> cmbItemCategory;
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
    private TableView<Fooditem> tblFoodItem;
    @FXML
    private TableColumn<Fooditem, String> colCode;
    @FXML
    private TableColumn<Fooditem, String> colItem;
    @FXML
    private TableColumn<Fooditem, Fooditemcategory> colCategory;
    @FXML
    private JFXComboBox<Fooditemcategory> cmbSearchByCategory;
    @FXML
    private JFXButton btnSearchClear;
    @FXML
    private JFXTextField txtSearchByCode;
    @FXML
    private JFXButton btnNewFoodItemCategory;
    @FXML
    private TextArea txtDescription;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Form-Data">
    Fooditem foodItem;
    Fooditem oldFoodItem;

    Stage foodItemCategoryStage;

    String initial;
    String valid;
    String invalid;
    String updated;

    int page;
    int row;

    boolean photoSelected;
    BigDecimal unitCost;

    //public static File lastDirectory;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Initializing-Methods">
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

        foodItem = new Fooditem();
        oldFoodItem = null;

        cmbItemCategory.setItems(FoodItemCategoryDao.getAll());//get all cmb details
        cmbItemCategory.getSelectionModel().clearSelection();//clear the selected item

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

        cmbSearchByCategory.setItems(FoodItemCategoryDao.getAll());
        cmbSearchByCategory.getSelectionModel().clearSelection();

        txtSearchByCode.setText("");
        txtSearchByItem.setText("");

        colCode.setCellValueFactory(new PropertyValueFactory("code"));
        colItem.setCellValueFactory(new PropertyValueFactory("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory("fooditemcategoryId"));

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

        fillTable(FoodItemDao.getAll());

        pagination.setCurrentPageIndex(0);

    }

    private void fillTable(ObservableList<Fooditem> employees) {

        if (employees != null && !employees.isEmpty()) {

            int rowsCount = 5;
            int pageCount = ((employees.size() - 1) / rowsCount) + 1;
            pagination.setPageCount(pageCount);

            pagination.setPageFactory((Integer pageIndex) -> {
                int start = pageIndex * rowsCount;
                int end = pageIndex == pageCount - 1 ? employees.size() : pageIndex * rowsCount + rowsCount;
                tblFoodItem.getItems().clear();
                tblFoodItem.setItems(FXCollections.observableArrayList(employees.subList(start, end)));
                return tblFoodItem;
            });

        } else {

            pagination.setPageCount(1);
            tblFoodItem.getItems().clear();

        }

        pagination.setCurrentPageIndex(page);
        tblFoodItem.getSelectionModel().select(row);

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Binding-Methods">
 @FXML
    private void txtDescriptionKR(KeyEvent event) {

        if (foodItem.setDescription(txtDescription.getText().trim())) {
            
            if (oldFoodItem != null && !foodItem.getDescription().equals(oldFoodItem.getDescription())) {
                
                ((ScrollPane) txtDescription.getChildrenUnmodifiable().get(0)).getContent().setStyle(updated);
                
            } else {
                
                ((ScrollPane) txtDescription.getChildrenUnmodifiable().get(0)).getContent().setStyle(valid);
                
            }
            
        } else {
            
            ((ScrollPane) txtDescription.getChildrenUnmodifiable().get(0)).getContent().setStyle(invalid);
            
        }

    }
	@FXML
    private void txtUnitPriceKR(KeyEvent event) {

   
        if (txtUnitPrice.getText().trim().matches("[0-9]{1}[0-9]*.[0-9]{1}[0-9]") || txtUnitPrice.getText().trim().matches("[0-9]{1}[0-9]*") || txtUnitPrice.getText().trim().matches("[0-9]{1}[0-9]*.[0-9]{1}") || txtUnitPrice.getText().trim().matches("[0-9]{1}[0-9]*.") ) {
            
            unitCost = new BigDecimal( txtUnitPrice.getText().trim() );

            foodItem.setUnitprice(toGetTextColor(txtUnitPrice.getText().trim()));
                
            if (oldFoodItem != null && !foodItem.getUnitprice().equals(oldFoodItem.getUnitprice())) {

                txtUnitPrice.setStyle(updated);

            } else {

                txtUnitPrice.setStyle(valid);

            }  

        } else {
            
            txtUnitPrice.setStyle(invalid);
            
            foodItem.setUnitprice(null);
            
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
        
        return  new BigDecimal("0");
        
    }
	 @FXML
    private void txtCodeKR(KeyEvent event) {

        if (foodItem.setCode(txtCode.getText().trim())) {

            if (oldFoodItem != null && !foodItem.getCode().equals(oldFoodItem.getCode())) {

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
        if (foodItem.setName(txtItemName.getText().trim())) {

            if (oldFoodItem != null && !foodItem.getName().equals(oldFoodItem.getName())) {

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
        foodItem.setFooditemcategoryId(cmbItemCategory.getSelectionModel().getSelectedItem());

        if (oldFoodItem != null && !foodItem.getFooditemcategoryId().equals(oldFoodItem.getFooditemcategoryId())) {

            cmbItemCategory.setStyle(updated);

        } else {

            cmbItemCategory.setStyle(valid);

        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Operation-Methods">
 private void btnAddAP(ActionEvent event) {
        //        employee.setName(txtName.getText());
//        employee.setNic(txtNIC.getText());
//        employee.setMobile(txtMobileNumber.getText());
//        employee.setAddress(txtAddress.getText());
//        employee.setEmail(txtEmail.getText());
//        employee.setLand(txtLandNumber.getText());
//        employee.setGenderId(cmbGender.getSelectionModel().getSelectedItem());
//        employee.setCivilstatusId(cmbCivilstatus.getSelectionModel().getSelectedItem());
//        employee.setDesignationId(cmbDesignation.getSelectionModel().getSelectedItem());
//

        String errors = getErrors();

        if (errors.isEmpty()) {

            String details
                    = "\nItem Code          \t\t: " + foodItem.getCode()
                    + "\nItem Name        \t\t: " + foodItem.getName()
                    + "\nItem Categoery  \t\t: " + foodItem.getFooditemcategoryId().getName()
                    + "\nUnit Price       \t\t: " + foodItem.getUnitprice()
                    + "\nDescription   \t\t: " + foodItem.getDescription();

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
                FoodItemDao.add(foodItem);

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

//        EmployeeDao.add(employee);
//
//        loadTable();
//
//        loadForm();
    }
	 @FXML
    private void btnClearAP(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialogForConfirmation");

        alert.setTitle(" Food Item Management");
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
                    FoodItemDao.update(foodItem);

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
                = "\nItem Code          \t\t: " + foodItem.getCode()
                + "\nItem Name        \t\t: " + foodItem.getName()
                + "\nItem Categoery  \t\t: " + foodItem.getFooditemcategoryId().getName()
                + "\nUnit Price       \t\t: " + foodItem.getUnitprice()
                + "\nDescription   \t\t: " + foodItem.getDescription();

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
            FoodItemDao.delete(foodItem);

//            fillTable(EmployeeDao.getAll());
            loadTable();

            loadForm();

            pagination.setCurrentPageIndex(page);
            tblFoodItem.getSelectionModel().select(row);

        }
    }

    private String getErrors() {

        String errors = "";

        if (foodItem.getName() == null) {
            errors = errors + "Name \t\tis Invalid\n";
        }

        if (foodItem.getFooditemcategoryId() == null) {
            errors = errors + "FoodItem Category \t\tis Not Selected\n";
        }

        if (foodItem.getCode() == null) {
            errors = errors + "Item Code \tis Not Selected\n";
        }

        if (foodItem.getDescription() == null) {
            errors = errors + "Description \t\tis Invalid\n";
        }

        if (foodItem.getUnitprice() == null) {
            errors = errors + "Unit Price  \t\tis Invalid \n";
        }

        return errors;

    }

    private String getUpdates() {

        String updates = "";

        if (oldFoodItem != null) {

            if (foodItem.getName() != null && !foodItem.getName().equals(oldFoodItem.getName())) {
                updates = updates + oldFoodItem.getName() + " chnaged to " + foodItem.getName() + "\n";
            }

            if (foodItem.getCode() != null && !foodItem.getCode().equals(oldFoodItem.getCode())) {
                updates = updates + oldFoodItem.getCode() + " chnaged to " + foodItem.getCode() + "\n";
            }

            if (foodItem.getUnitprice() != null && !foodItem.getUnitprice().equals(oldFoodItem.getUnitprice())) {
                updates = updates + oldFoodItem.getUnitprice() + " chnaged to " + foodItem.getUnitprice() + "\n";
            }

//            if (!(oldEmployee.getLand() != null && employee.getLand() != null && oldEmployee.getLand().equals(employee.getLand()))) {
//                if (oldEmployee.getLand() != employee.getLand()) {
//                    updates = updates + oldEmployee.getLand() + " chnaged to " + employee.getLand() + "\n";
//                }
//            }
            if (foodItem.getDescription() != null && !foodItem.getDescription().equals(oldFoodItem.getDescription())) {
                updates = updates + oldFoodItem.getDescription() + " chnaged to " + foodItem.getDescription() + "\n";
            }

        }

        return updates;

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Searching-Methods">

//</editor-fold>

}
