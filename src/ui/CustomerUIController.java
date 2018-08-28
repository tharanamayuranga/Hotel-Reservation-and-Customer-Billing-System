/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dao.CountryDao;
import dao.CustomerDao;
import dao.CustomerTypeDao;
import dao.GenderDao;
import dao.IDTypeDao;
import entity.Country;
import entity.Customer;
import entity.Customertype;
import entity.Gender;
import entity.Idtype;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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
import javafx.scene.control.Label;
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
public class CustomerUIController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="Form-Data">
    Customer customer;
    Customer oldCustomer;

    Stage idTypeStage;

    String initial;
    String valid;
    String invalid;
    String updated;

    int page;
    int row;

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="FXML-Data">
    @FXML
    private TextArea txtAddress;
    @FXML
    private JFXComboBox<Country> cmbCountry;
    @FXML
    private JFXComboBox<Customertype> cmbSearchCustomerType;
    @FXML
    private JFXComboBox<Idtype> cmbIDType;
    @FXML
    private JFXComboBox<Gender> cmbGender;
    @FXML
    private JFXDatePicker dtpDOBDate;
    @FXML
    private JFXTextField txtID;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private JFXButton btnClear;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<Customer> tblCustomer;
    @FXML
    private TableColumn<Customer, String> colName;
    @FXML
    private TableColumn<Customer, String> colMobile;
    @FXML
    private TableColumn<Customer, String> colEmail;
    @FXML
    private JFXButton btnClearSearch;
    @FXML
    private JFXTextField txtSearchByID;
    @FXML
    private JFXTextField txtSearchByGuest;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtMobile;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXComboBox<Customertype> cmbCustomerType;
    @FXML
    private JFXDatePicker dtpAssignedDate;
    @FXML
    private Label lblCustomerID;
    @FXML
    private JFXButton btnNewIDType;
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

    private void loadForm() {

        customer = new Customer();
        oldCustomer = null;

        cmbGender.setItems(GenderDao.getAll());//get all cmb details
        cmbGender.getSelectionModel().clearSelection();//clear the selected item

        cmbCustomerType.setItems(CustomerTypeDao.getAll());
        cmbCustomerType.getSelectionModel().clearSelection();

        cmbIDType.setItems(IDTypeDao.getAll());
        cmbIDType.getSelectionModel().clearSelection();

        cmbCountry.setItems(CountryDao.getAll());
        cmbCountry.getSelectionModel().clearSelection();

        txtName.setText("");
        txtAddress.setText("");
        txtID.setText("");
        txtMobile.setText("");
        txtEmail.setText("");

        dtpDOBDate.setValue(null);

        dtpAssignedDate.setDisable(true);
        dtpAssignedDate.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        Date assign = java.sql.Date.valueOf(dtpAssignedDate.getValue());
        customer.setAssigned(assign);

        if (CustomerDao.getLastJobId() != null) {

            lblCustomerID.setText(String.format("%06d", CustomerDao.getLastJobId() + 1));

        } else {

            lblCustomerID.setText(String.format("%06d", 1));

        }

        dissableButtons(false, false, true, true);

        setStyle(initial);
    }

    private void setStyle(String style) {

        cmbCountry.setStyle(style);
        cmbCustomerType.setStyle(style);
        cmbIDType.setStyle(style);
        cmbGender.setStyle(style);

        txtName.setStyle(style);
        txtID.setStyle(style);
        txtMobile.setStyle(style);
        txtEmail.setStyle(style);

        if (!txtAddress.getChildrenUnmodifiable().isEmpty()) {

            ((ScrollPane) txtAddress.getChildrenUnmodifiable().get(0)).getContent().setStyle(style);

        }

        dtpDOBDate.getEditor().setStyle(style);
        dtpAssignedDate.getEditor().setStyle(style);

        cmbSearchCustomerType.setStyle(style);
        txtSearchByGuest.setStyle(style);
        txtSearchByID.setStyle(style);

    }

    private void dissableButtons(boolean select, boolean insert, boolean update, boolean delete) {

        btnAdd.setDisable(insert);
        btnUpdate.setDisable(update);
        btnDelete.setDisable(delete);

    }
	 private void loadTable() {

        cmbSearchCustomerType.setItems(CustomerTypeDao.getAll());
        cmbSearchCustomerType.getSelectionModel().clearSelection();

        txtSearchByGuest.setText("");
        txtSearchByID.setText("");

        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colMobile.setCellValueFactory(new PropertyValueFactory("mobile"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));

////        tblEmployee.setRowFactory(new Callback<TableView<Employee>, TableRow<Employee>>() {
////
////            @Override
////            public TableRow<Employee> call(TableView<Employee> dateTableView) {
////
////                return new TableRow<Employee>() {
////
////                    @Override
////                    protected void updateItem(Employee date, boolean b) {
////                        super.updateItem(date, b);
////
////                        setStyle("-fx-background-color: linear-gradient(#04ef57 1%, #FFFFFF 100%);");
////
////                    }
////
////                };
////
////            }
////
////        });
        row = 0;
        page = 0;

        fillTable(CustomerDao.getAll());

        pagination.setCurrentPageIndex(0);

    }
	private void fillTable(ObservableList<Customer> employees) {

        if (employees != null && !employees.isEmpty()) {

            int rowsCount = 5;
            int pageCount = ((employees.size() - 1) / rowsCount) + 1;
            pagination.setPageCount(pageCount);

            pagination.setPageFactory((Integer pageIndex) -> {
                int start = pageIndex * rowsCount;
                int end = pageIndex == pageCount - 1 ? employees.size() : pageIndex * rowsCount + rowsCount;
                tblCustomer.getItems().clear();
                tblCustomer.setItems(FXCollections.observableArrayList(employees.subList(start, end)));
                return tblCustomer;
            });

        } else {

            pagination.setPageCount(1);
            tblCustomer.getItems().clear();

        }

        pagination.setCurrentPageIndex(page);
        tblCustomer.getSelectionModel().select(row);

    }



//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Binding-Methods">
   @FXML
    private void cmbIDTypeAP(ActionEvent event) {

        customer.setIdtypeId(cmbIDType.getSelectionModel().getSelectedItem());

        if (oldCustomer != null && !customer.getIdtypeId().equals(oldCustomer.getIdtypeId())) {

            cmbIDType.setStyle(updated);

        } else {

            cmbIDType.setStyle(valid);

        }

    }

    @FXML
    private void cmbGenderAP(ActionEvent event) {
        customer.setGenderId(cmbGender.getSelectionModel().getSelectedItem());

        if (oldCustomer != null && !customer.getGenderId().equals(oldCustomer.getGenderId())) {
            cmbGender.setStyle(updated);
        } else {
            cmbGender.setStyle(valid);
        }
    }

    @FXML
    private void dtpDOBDateAP(ActionEvent event) {
        if (dtpDOBDate.getValue() != null) {
            Date today = new Date();
            today.setYear(today.getYear() - 18);
            Date dob = java.sql.Date.valueOf(dtpDOBDate.getValue());

            if (dob.before(today)) {
                customer.setDobirth(dob);
                if (oldCustomer != null && !customer.getDobirth().equals(oldCustomer.getDobirth())) {
                    dtpDOBDate.setStyle(updated);
                } else {
                    dtpDOBDate.setStyle(valid);
                }
            } else {
                dtpDOBDate.setStyle(invalid);
            }
        }
    }

    @FXML
    private void txtIDKR(KeyEvent event) {
        
        if (customer.setIdno(txtID.getText().trim())) {

            if (oldCustomer != null && !customer.getIdno().equals(oldCustomer.getIdno())) {

                txtID.setStyle(updated);

            } else {

                txtID.setStyle(valid);

            }

        } else {

            txtID.setStyle(invalid);

        }
    }

    @FXML
    private void txtNameKR(KeyEvent event) {
        if (customer.setName(txtName.getText().trim())) {

            if (oldCustomer != null && !customer.getName().equals(oldCustomer.getName())) {

                txtName.setStyle(updated);

            } else {

                txtName.setStyle(valid);

            }

        } else {

            txtName.setStyle(invalid);

        }
    }

    @FXML
    private void txtMobileKR(KeyEvent event) {
        if (customer.setMobile(txtMobile.getText().trim())) {

            if (oldCustomer != null && !customer.getMobile().equals(oldCustomer.getMobile())) {

                txtMobile.setStyle(updated);

            } else {

                txtMobile.setStyle(valid);

            }

        } else {

            txtMobile.setStyle(invalid);

        }
    }

    @FXML
    private void txtEmailKR(KeyEvent event) {
        if (customer.setEmail(txtEmail.getText().trim())) {

            if (oldCustomer != null && oldCustomer.getEmail() != null && customer.getEmail() != null && oldCustomer.getEmail().equals(customer.getEmail())) {

                txtEmail.setStyle(valid);

            } else if (oldCustomer != null && oldCustomer.getEmail() != customer.getEmail()) {

                txtEmail.setStyle(updated);

            } else {

                txtEmail.setStyle(valid);

            }

        } else {

            txtEmail.setStyle(invalid);

        }
    }

    @FXML
    private void cmbCustomerTypeAP(ActionEvent event) {

        customer.setCustomertypeId(cmbCustomerType.getSelectionModel().getSelectedItem());

        if (oldCustomer != null && !customer.getCustomertypeId().equals(oldCustomer.getCustomertypeId())) {

            cmbCustomerType.setStyle(updated);

        } else {

            cmbCustomerType.setStyle(valid);

        }

    }

    @FXML
    private void dtpAssignedDateAP(ActionEvent event) {

        if (dtpAssignedDate.getValue() != null) {

            Date today = new Date();
            Date assign = java.sql.Date.valueOf(dtpAssignedDate.getValue());

            if (assign.before(today)) {

                customer.setAssigned(assign);

                if (oldCustomer != null && !customer.getAssigned().equals(oldCustomer.getAssigned())) {

                    dtpAssignedDate.getEditor().setStyle(updated);

                } else {

                    dtpAssignedDate.getEditor().setStyle(valid);

                }

            } else {

                dtpAssignedDate.getEditor().setStyle(invalid);
                customer.setAssigned(null);

            }

        }
    }

    @FXML
    private void cmbCountryAP(ActionEvent event) {

        customer.setCountryId(cmbCountry.getSelectionModel().getSelectedItem());

        if (oldCustomer != null && !customer.getCountryId().equals(oldCustomer.getCountryId())) {

            cmbCountry.setStyle(updated);

        } else {

            cmbCountry.setStyle(valid);

        }
    }

    @FXML
    private void txtAddressKR(KeyEvent event) {
        if (customer.setAddress(txtAddress.getText().trim())) {

            if (oldCustomer != null && !customer.getAddress().equals(oldCustomer.getAddress())) {

                ((ScrollPane) txtAddress.getChildrenUnmodifiable().get(0)).getContent().setStyle(updated);

            } else {

                ((ScrollPane) txtAddress.getChildrenUnmodifiable().get(0)).getContent().setStyle(valid);

            }

        } else {

            ((ScrollPane) txtAddress.getChildrenUnmodifiable().get(0)).getContent().setStyle(invalid);

        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Operation-Methods">
    @FXML
    private void btnAddAP(ActionEvent event) {

        String errors = getErrors();

        if (errors.isEmpty()) {

            String details
                    = "\nName       \t: " + customer.getName()
                    + "\nGuest Type           \t: " + customer.getCustomertypeId()
                    + "\nID Type   \t: " + customer.getIdtypeId()
                    + "\nID No  \t: " + customer.getIdno()
                    + "\nGender   \t: " + customer.getGenderId()
                    + "\nBirth Date          \t: " + customer.getDobirth().toString()
                    + "\nMobile No     \t: " + customer.getMobile()
                    + "\nEmail           \t: " + customer.getEmail()
                    + "\nCountry          \t: " + customer.getCountryId()
                    + "\nAddress     \t: " + customer.getAddress();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add Module");
            alert.setHeaderText("Are you sure you need to add the following Employee");
            alert.setContentText(details);

            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForConfirmation");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {

                //  Notification.Notifier.INSTANCE.notifySuccess("Add", ( rdbPerson.isSelected() ? customer.getName() : customer.getCompanyname() ) + " is added!");
                customer.setDisable(0);

                CustomerDao.add(customer);

                loadTable();
                loadForm();

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

        alert.setTitle(" Guest Management");
        alert.setHeaderText("Clear Form");
        alert.setContentText("Are you sure you need to clear form ");

        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialogForConfirmation");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            loadForm();

            // Notification.Notifier.INSTANCE.notifySuccess("Clear Form", "The Form is cleared!" );
        }
    }
	    @FXML
    private void btnDeleteAP(ActionEvent event) {
        String details
                = "\nName       \t: " + oldCustomer.getName()
                + "\nGuest Type           \t: " + oldCustomer.getCustomertypeId()
                + "\nID Type   \t: " + oldCustomer.getIdtypeId()
                + "\nID No  \t: " + oldCustomer.getIdno()
                + "\nGender   \t: " + oldCustomer.getGenderId()
                + "\nBirth Date          \t: " + oldCustomer.getDobirth().toString()
                + "\nMobile No     \t: " + oldCustomer.getMobile()
                + "\nEmail           \t: " + oldCustomer.getEmail()
                + "\nCountry          \t: " + oldCustomer.getCountryId()
                + "\nAddress     \t: " + oldCustomer.getAddress();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Module");
        alert.setHeaderText("Are you sure you need to delete the following Module?");
        alert.setContentText(details);

        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialogForConfirmation");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            // Notification.Notifier.INSTANCE.notifySuccess("Delete", ( rdbPerson.isSelected() ? customer.getName() : customer.getCompanyname() ) + " is deleted!");
//            CustomerDao.delete(oldCustomer);
            oldCustomer.setDisable(1);

            CustomerDao.update(oldCustomer);

            fillTable(CustomerDao.getAll());
            loadForm();

            pagination.setCurrentPageIndex(page);
            tblCustomer.getSelectionModel().select(row);

        }
    }
	    @FXML
    private void btnUpdateAP(ActionEvent event) {
        
         String errors = getErrors();
        
        if ( errors.isEmpty() ) {

            String updates = getUpdates();

            if (!updates.isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                alert.setTitle("Update Module");
                alert.setHeaderText("Are you sure you need to update the following Module????");
                alert.setContentText(updates);
                
                DialogPane dialogPane = alert.getDialogPane();

                dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialogForConfirmation");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    
              //      Notification.Notifier.INSTANCE.notifySuccess("Delete", ( rdbPerson.isSelected() ? customer.getName() : customer.getCompanyname() ) + " is updated!");

                    CustomerDao.update(customer);

                    loadForm();
                    loadTable();

                }

            } else {
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Update Module");
                alert.setHeaderText("There is nothing to Update!!!");
                alert.setContentText("Nothing to Update!!!");
                
                DialogPane dialogPane = alert.getDialogPane();

                dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialogForInformation");
                
                alert.showAndWait();
                
            }
            
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error - Customer Update");
            alert.setHeaderText("Form Data Error");
            alert.setContentText(errors);
            
            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForError");
            
            alert.showAndWait();

        }

    }
	 private String getErrors() {

        String errors = "";

        if (customer.getName() == null) {
            errors = errors + "Name \t\tis Invalid\n";
        }

        if (customer.getCustomertypeId() == null) {
            errors = errors + "Customer Type \t\tis Not Selected\n";
        }

        if (customer.getIdtypeId() == null) {
            errors = errors + "ID Type \tis Not Selected\n";
        }

        if (customer.getGenderId() == null) {
            errors = errors + "gender \t\tis Invalid\n";
        }

        if (customer.getDobirth() == null) {

            if (dtpDOBDate.getValue() == null) {

                errors = errors + "Birth Date \tis not seleted\n";

            } else {

                errors = errors + "Birth Date \tAge is less than 18\n";

            }

        }

        if (customer.getMobile() == null) {
            errors = errors + "Mobile No. \t\tis Invalid or Already in\n";
        }

        if (customer.getEmail() == null) {
            errors = errors + "Email  \tis Invalid\n";
        }

        if (customer.getCountryId() == null) {
            errors = errors + "Country \t\tis Invalid\n";
        }

        if (customer.getAddress() == null) {
            errors = errors + "Address \tis Not Selected\n";
        }

        if (customer.getAssigned() == null) {
            errors = errors + "Assign Date \tis Invalid\n";
        }

        return errors;

    }
    private String getUpdates() {

        String updates = "";

        if (oldCustomer != null) {

            if (customer.getName() != null && !customer.getName().equals(oldCustomer.getName())) {
                updates = updates + oldCustomer.getName() + " chnaged to " + customer.getName() + "\n";
            }
             if (customer.getCustomertypeId()!= null && !customer.getCustomertypeId().equals(oldCustomer.getCustomertypeId())) {
                updates = updates + oldCustomer.getCustomertypeId()+ " chnaged to " + customer.getCustomertypeId()+ "\n";
            }
            
            if (!customer.getIdtypeId().equals(oldCustomer.getIdtypeId())) {
                updates = updates + oldCustomer.getIdtypeId() + " chnaged to " + customer.getIdtypeId() + "\n";
            }
            
            if (!customer.getIdno().equals(oldCustomer.getIdno())) {
                updates = updates + oldCustomer.getIdno() + " chnaged to " + customer.getIdno() + "\n";
            }
            
            if (!customer.getGenderId().equals(oldCustomer.getGenderId())) {
                updates = updates + oldCustomer.getGenderId() + " chnaged to " + customer.getGenderId() + "\n";
            }
            
             if (customer.getDobirth() != null && !customer.getDobirth().equals(oldCustomer.getDobirth())) {
                updates = updates + oldCustomer.getDobirth().toString() + "(dob)" + " chnaged to " + customer.getDobirth().toString() + "\n";
            }
             
            if (customer.getMobile() != null && !customer.getMobile().equals(oldCustomer.getMobile())) {
                updates = updates + oldCustomer.getMobile() + " chnaged to " + customer.getMobile() + "\n";
            }
            
            if (!(oldCustomer.getEmail() != null && customer.getEmail() != null && oldCustomer.getEmail().equals(customer.getEmail()))) {
                if (oldCustomer.getEmail() != customer.getEmail()) {
                    updates = updates + oldCustomer.getEmail() + " chnaged to " + customer.getEmail() + "\n";
                }
            }
            
            if (!customer.getCountryId().equals(oldCustomer.getCountryId())) {
                updates = updates + oldCustomer.getCountryId() + " chnaged to " + customer.getCountryId() + "\n";
            }
            
            if (!customer.getAddress().equals(oldCustomer.getAddress())) {
                updates = updates + oldCustomer.getAddress() + " chnaged to " + customer.getAddress() + "\n";
            }
            
            if (customer.getAssigned() != null && !customer.getAssigned().equals(oldCustomer.getAssigned())) {
                updates = updates + oldCustomer.getAssigned().toString() + " chnaged to " + customer.getAssigned().toString() + "\n";
            }

        }

        return updates;
    }
	    @FXML
    private void tblCustomerMC(MouseEvent event) {
        fillForm();
    }

    @FXML
    private void tblCustomerKR(KeyEvent event) {
        fillForm();
    }
	 private void fillForm() {
        
        if (tblCustomer.getSelectionModel().getSelectedItem() != null) {
            
//            photoSelected = false;
            
            dissableButtons(false, true, false, false);
            
            setStyle(valid);
            
//            cmbStatus.setDisable(false);
            
            cmbSearchCustomerType.setStyle(initial);
            txtSearchByID.setStyle(initial);
            txtSearchByGuest.setStyle(initial);
            
            oldCustomer = CustomerDao.getById(tblCustomer.getSelectionModel().getSelectedItem().getId());
            customer = CustomerDao.getById(tblCustomer.getSelectionModel().getSelectedItem().getId());
            
              cmbGender.getSelectionModel().select((Gender) oldCustomer.getGenderId());
//            cmbIDType.getSelectionModel().select((Idtype) customer.getIdtypeId());
//            cmbCountry.getSelectionModel().select((Country) customer.getCountryId());
//            cmbCustomerType.getSelectionModel().select((Customertype) customer.getCustomertypeId());

            txtName.setText(oldCustomer.getName());
            txtAddress.setText(oldCustomer.getAddress());
            txtID.setText(oldCustomer.getIdno());
            txtMobile.setText(oldCustomer.getMobile());
            txtEmail.setText(oldCustomer.getEmail());
            
            
            dtpDOBDate.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(oldCustomer.getDobirth())));
            dtpDOBDate.getEditor().setText(new SimpleDateFormat("yyyy-MM-dd").format(oldCustomer.getDobirth()));
//            oldNicWithOldFormateWithV = oldEmployee.getNic().substring(2, 7) + oldEmployee.getNic().substring(8) + "v";
//            oldNicWithOldFormateWithX = oldEmployee.getNic().substring(2, 7) + oldEmployee.getNic().substring(8) + "x";


//            lblDob.setText(oldEmployee.getDob().toString());
//            lblGender.setText(oldEmployee.getGenderId().getName());

            dtpAssignedDate.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(oldCustomer.getAssigned())));
            dtpAssignedDate.getEditor().setText(new SimpleDateFormat("yyyy-MM-dd").format(oldCustomer.getAssigned()));



            lblCustomerID.setText(String.format("%06d", oldCustomer.getId() ));

            page = pagination.getCurrentPageIndex();
            row = tblCustomer.getSelectionModel().getSelectedIndex();

        }
        
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Search-Methods">
   
//</editor-fold>

}
