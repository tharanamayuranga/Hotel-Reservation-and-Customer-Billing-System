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

        spaPackage = new Spapackage();
        oldSpaPackage = null;

        cmbPackageCategory.setItems(SpaPackageCategoryDao.getAll());//get all cmb details
        cmbPackageCategory.getSelectionModel().clearSelection();//clear the selected item

        txtCode.setText("");
        txtPackageName.setText("");
        txtPackagePrice.setText("");
        txtDescription.setText("");

        dissableButtons(false, false, true, true);

        setStyle(initial);

    }

    private void setStyle(String style) {

        cmbPackageCategory.setStyle(style);

        txtCode.setStyle(style);
        txtPackageName.setStyle(style);
        txtPackagePrice.setStyle(style);
        txtDescription.setStyle(style);

        if (!txtDescription.getChildrenUnmodifiable().isEmpty()) {

            ((ScrollPane) txtDescription.getChildrenUnmodifiable().get(0)).getContent().setStyle(style);

        }

        cmbSearchByCategory.setStyle(style);
        txtSearchByCode.setStyle(style);
        txtSearchByPackage.setStyle(style);

    }

    private void dissableButtons(boolean select, boolean insert, boolean update, boolean delete) {

        btnAdd.setDisable(insert);
        btnUpdate.setDisable(update);
        btnDelete.setDisable(delete);

    }

    private void loadTable() {

        cmbSearchByCategory.setItems(SpaPackageCategoryDao.getAll());
        cmbSearchByCategory.getSelectionModel().clearSelection();

        txtSearchByCode.setText("");
        txtSearchByPackage.setText("");

        colCode.setCellValueFactory(new PropertyValueFactory("code"));
        colItem.setCellValueFactory(new PropertyValueFactory("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory("spapackagecategoryId"));

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

        fillTable(SpaPackageDao.getAll());

        pagination.setCurrentPageIndex(0);

    }

    private void fillTable(ObservableList<Spapackage> employees) {

        if (employees != null && !employees.isEmpty()) {

            int rowsCount = 5;
            int pageCount = ((employees.size() - 1) / rowsCount) + 1;
            pagination.setPageCount(pageCount);

            pagination.setPageFactory((Integer pageIndex) -> {
                int start = pageIndex * rowsCount;
                int end = pageIndex == pageCount - 1 ? employees.size() : pageIndex * rowsCount + rowsCount;
                tblPackage.getItems().clear();
                tblPackage.setItems(FXCollections.observableArrayList(employees.subList(start, end)));
                return tblPackage;
            });

        } else {

            pagination.setPageCount(1);
            tblPackage.getItems().clear();

        }

        pagination.setCurrentPageIndex(page);
        tblPackage.getSelectionModel().select(row);

    }   
//</editor-fold>
 //<editor-fold defaultstate="collapsed" desc="Binding-Methods">
    @FXML
    private void txtPackagePriceKR(KeyEvent event) {
        
        if (txtPackagePrice.getText().trim().matches("[0-9]{1}[0-9]*.[0-9]{1}[0-9]") || txtPackagePrice.getText().trim().matches("[0-9]{1}[0-9]*") || txtPackagePrice.getText().trim().matches("[0-9]{1}[0-9]*.[0-9]{1}") || txtPackagePrice.getText().trim().matches("[0-9]{1}[0-9]*.")) {

            packageCost = new BigDecimal(txtPackagePrice.getText().trim());//check it

            spaPackage.setPackageprice(toGetTextColor(txtPackagePrice.getText().trim()));

            if (oldSpaPackage != null && !spaPackage.getPackageprice().equals(oldSpaPackage.getPackageprice())) {

                txtPackagePrice.setStyle(updated);

            } else {

                txtPackagePrice.setStyle(valid);

            }

        } else {

            txtPackagePrice.setStyle(invalid);

            spaPackage.setPackageprice(null);

        }
    }

    private BigDecimal toGetTextColor(String string) {

        if (!string.contains(".") && !"".equals(txtPackagePrice.getText().trim())) {

            return new BigDecimal(string + ".00");

        } else if (string.contains(".") && !"".equals(txtPackagePrice.getText().trim())) {

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
        if (spaPackage.setDescription(txtDescription.getText().trim())) {

            if (oldSpaPackage != null && !spaPackage.getDescription().equals(oldSpaPackage.getDescription())) {

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
                if (spaPackage.setCode(txtCode.getText().trim())) {

            if (oldSpaPackage != null && !spaPackage.getCode().equals(oldSpaPackage.getCode())) {

                txtCode.setStyle(updated);

            } else {

                txtCode.setStyle(valid);

            }

        } else {

            txtCode.setStyle(invalid);

        }
    }
    
    @FXML
    private void txtPackageNameKR(KeyEvent event) {
         if (spaPackage.setName(txtPackageName.getText().trim())) {

            if (oldSpaPackage != null && !spaPackage.getName().equals(oldSpaPackage.getName())) {

                txtPackageName.setStyle(updated);

            } else {

                txtPackageName.setStyle(valid);

            }

        } else {

            txtPackageName.setStyle(invalid);

        }
    }
    
    @FXML
    private void cmbPackageCategoryAP(ActionEvent event) {
           spaPackage.setSpapackagecategoryId(cmbPackageCategory.getSelectionModel().getSelectedItem());

        if (oldSpaPackage != null && !spaPackage.getSpapackagecategoryId().equals(oldSpaPackage.getSpapackagecategoryId())) {

            cmbPackageCategory.setStyle(updated);

        } else {

            cmbPackageCategory.setStyle(valid);

        }
    }
//</editor-fold>
 //<editor-fold defaultstate="collapsed" desc="Operation-Methods">
    @FXML
    private void btnAddAP(ActionEvent event) {
         String errors = getErrors();

        if (errors.isEmpty()) {

            String details
                    = "\nItem Code          \t\t: " + spaPackage.getCode()
                    + "\nItem Name        \t\t: " + spaPackage.getName()
                    + "\nItem Categoery  \t\t: " + spaPackage.getSpapackagecategoryId().getName()
                    + "\nUnit Price       \t\t: " + spaPackage.getPackageprice()
                    + "\nDescription   \t\t: " + spaPackage.getDescription();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add Module");
            alert.setHeaderText("Are you sure you need to add the following Spa PAckage?");
            alert.setContentText(details);

            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForConfirmation");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {

                // employee.setDisable(0);
                SpaPackageDao.add(spaPackage);

                loadForm();

                loadTable();

            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You need to fill the following Employee");
            alert.setContentText(errors);

            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
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
                    SpaPackageDao.update(spaPackage);

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
                    = "\nItem Code          \t\t: " + spaPackage.getCode()
                    + "\nItem Name        \t\t: " + spaPackage.getName()
                    + "\nItem Categoery  \t\t: " + spaPackage.getSpapackagecategoryId().getName()
                    + "\nUnit Price       \t\t: " + spaPackage.getPackageprice()
                    + "\nDescription   \t\t: " + spaPackage.getDescription();

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
            SpaPackageDao.delete(spaPackage);

//            fillTable(EmployeeDao.getAll());
            loadTable();

            loadForm();

            pagination.setCurrentPageIndex(page);
            tblPackage.getSelectionModel().select(row);

        }
    }
    
    @FXML
    private void tblFoodItemMC(MouseEvent event) {
        fillForm();
    }
    
    @FXML
    private void tblFoodItemKR(KeyEvent event) {
        fillForm();
    }
    @FXML
    private void btnNewFoodItemCategoryAP(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("SpaPackageCategoryUI.fxml"));

        Scene scene = new Scene(root);

        spaPackageCategoryStage = new Stage();
        spaPackageCategoryStage.setScene(scene);

        spaPackageCategoryStage.setOnCloseRequest(e -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle(" WINDOW CLOSE");
            alert.setHeaderText("Close Form");
            alert.setContentText("Are you sure you need to close this form?? ");

            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForConfirmation");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {

                spaPackageCategoryStage.close();

            }

            e.consume();

        });

        spaPackageCategoryStage.initModality(Modality.WINDOW_MODAL);
        spaPackageCategoryStage.initOwner(stageBasic);
        spaPackageCategoryStage.setResizable(false);
        spaPackageCategoryStage.setTitle("Liquor Category Management System");
        spaPackageCategoryStage.show();
    }
     private String getErrors() {

        String errors = "";

        if (spaPackage.getName() == null) {
            errors = errors + "Name \t\tis Invalid\n";
        }

        if (spaPackage.getSpapackagecategoryId() == null) {
            errors = errors + "FoodItem Category \t\tis Not Selected\n";
        }

        if (spaPackage.getCode() == null) {
            errors = errors + "Item Code \tis Not Selected\n";
        }

        if (spaPackage.getDescription() == null) {
            errors = errors + "Description \t\tis Invalid\n";
        }

        if (spaPackage.getPackageprice() == null) {
            errors = errors + "Unit Price  \t\tis Invalid \n";
        }

        return errors;

    }

    private String getUpdates() {

        String updates = "";

        if (oldSpaPackage != null) {

            if (spaPackage.getName() != null && !spaPackage.getName().equals(oldSpaPackage.getName())) {
                updates = updates + oldSpaPackage.getName() + " chnaged to " + spaPackage.getName() + "\n";
            }

            if (spaPackage.getCode() != null && !spaPackage.getCode().equals(oldSpaPackage.getCode())) {
                updates = updates + oldSpaPackage.getCode() + " chnaged to " + spaPackage.getCode() + "\n";
            }

            if (spaPackage.getPackageprice() != null && !spaPackage.getPackageprice().equals(oldSpaPackage.getPackageprice())) {
                updates = updates + oldSpaPackage.getPackageprice() + " chnaged to " + spaPackage.getPackageprice() + "\n";
            }

            if (spaPackage.getDescription() != null && !spaPackage.getDescription().equals(oldSpaPackage.getDescription())) {
                updates = updates + oldSpaPackage.getDescription() + " chnaged to " + spaPackage.getDescription() + "\n";
            }

        }

        return updates;

    }
    private void fillForm() {

        if (tblPackage.getSelectionModel().getSelectedItem() != null) {

            dissableButtons(false, true, false, false);
            setStyle(valid);

            oldSpaPackage = SpaPackageDao.getById(tblPackage.getSelectionModel().getSelectedItem().getId());
            spaPackage = SpaPackageDao.getById(tblPackage.getSelectionModel().getSelectedItem().getId());

//            cmbSearchDesignation.setStyle(initial);
//            cmbSearchStatus.setStyle(initial);
//            txtSearchName.setStyle(initial);
            txtCode.setText(oldSpaPackage.getCode());
            txtPackageName.setText(spaPackage.getName());
            cmbPackageCategory.getSelectionModel().select((Spapackagecategory) spaPackage.getSpapackagecategoryId());
            txtDescription.setText(spaPackage.getDescription());
            txtPackagePrice.setText(spaPackage.getPackageprice().toString());

            page = pagination.getCurrentPageIndex();
            row = tblPackage.getSelectionModel().getSelectedIndex();

//            oldNicWithOldFormateWithV = oldEmployee.getNic().substring(2, 7) + oldEmployee.getNic().substring(8) + "v";
//            oldNicWithOldFormateWithX = oldEmployee.getNic().substring(2, 7) + oldEmployee.getNic().substring(8) + "x";
//            lblDob.setText(oldEmployee.getDob().toString());
//            lblGender.setText(oldEmployee.getGenderId().getName());
        }

    }
//</editor-fold>
    
}
