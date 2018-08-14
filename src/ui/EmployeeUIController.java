/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.CivilstatusDao;
import dao.DesignationDao;
import dao.EmployeeDao;
import dao.EmployeestatusDao;
import dao.GenderDao;
import entity.Civilstatus;
import entity.Designation;
import entity.Employee;
import entity.Employeestatus;
import entity.Gender;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.ImageIcon;
import static ui.Main.stageBasic;

/**
 *
 * @author Tharana
 */
public class EmployeeUIController implements Initializable {
    
    //<editor-fold defaultstate="collapsed" desc="Form-Data">
    
    Employee employee;
    Employee oldEmployee;
    
    Stage designationStage;
    
    String initial;
    String valid;
    String invalid;
    String updated;
    
    int page;
    int row;
    
    boolean photoSelected;
    
    public static File lastDirectory;
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="FXML-Data">
    
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXComboBox<Civilstatus> cmbCivilstatus;
    @FXML
    private TextArea txtAddress;
    @FXML
    private JFXTextField txtNIC;
    @FXML
    private JFXTextField txtMobileNumber;
    @FXML
    private JFXTextField txtLandNumber;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXComboBox<Designation> cmbDesignation;
    @FXML
    private JFXComboBox<Employeestatus> cmbStatus;
    @FXML
    private DatePicker dtpAssignedDate;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private ImageView imgPhoto;
    @FXML
    private Button btnPhotoClear;
    @FXML
    private Button btnPhotoSelect;
    @FXML
    private Label lblEmployeeID;
    @FXML
    private JFXTextField txtSearchName;
    @FXML
    private JFXComboBox<Employeestatus> cmbSearchStatus;
    @FXML
    private JFXComboBox<Designation> cmbSearchDesignation;
    @FXML
    private TableView<Employee> tblEmployee;
    @FXML
    private TableColumn<Employee, String> colName;
    @FXML
    private TableColumn<Employee, Employeestatus> colStatus;
    @FXML
    private TableColumn<Employee, Designation> colDesignation;
    @FXML
    private TableColumn<Employee, String> colMobile;
    @FXML
    private TableColumn<Employee, String> colEmail;
    @FXML
    private Pagination pagination;
    @FXML
    private Button btnSearchClear;
    @FXML
    private JFXComboBox<Gender> cmbGender;
    @FXML
    private DatePicker dtpDOBDate;
    @FXML
    private JFXButton btnNewDesignation;
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Initialization-Methods">
    
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

        employee = new Employee();
        oldEmployee = null;
        
        cmbGender.setItems(GenderDao.getAll());//get all cmb details
        cmbGender.getSelectionModel().clearSelection();//clear the selected item
        
        cmbCivilstatus.setItems(CivilstatusDao.getAll());
        cmbCivilstatus.getSelectionModel().clearSelection();
        
        cmbDesignation.setItems(DesignationDao.getAll());
        cmbDesignation.getSelectionModel().clearSelection();
        
        cmbStatus.setItems(EmployeestatusDao.getAll());
        cmbStatus.getSelectionModel().select(0);//select the first index value
        cmbStatus.setDisable(true);//disable the combo box
        employee.setEmployeestatusId(cmbStatus.getSelectionModel().getSelectedItem());//add status details to employee object because it is disable combo box
        
        txtName.setText("");
        txtAddress.setText("");
        txtNIC.setText("");
        txtMobileNumber.setText("");
        txtLandNumber.setText("");
        txtEmail.setText("");
        
        dtpDOBDate.setValue(null);

        dtpAssignedDate.setDisable(true);
        dtpAssignedDate.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        Date assign = java.sql.Date.valueOf(dtpAssignedDate.getValue());
        employee.setAssigned(assign);

        imgPhoto.setImage(new Image("/image/user.png"));

        photoSelected = false;
        
        if (EmployeeDao.getLastJobId() != null) {
            
            lblEmployeeID.setText(String.format("%06d", EmployeeDao.getLastJobId() + 1));
            
        } else {
            
            lblEmployeeID.setText(String.format("%06d", 1));
            
        }
        
        dissableButtons(false, false, true, true);

        setStyle(initial);
    }
    
    private void setStyle(String style) {

        cmbCivilstatus.setStyle(style);
        cmbDesignation.setStyle(style);
        cmbStatus.setStyle(style);
        cmbGender.setStyle(style);

        txtName.setStyle(style);
        txtNIC.setStyle(style);
        txtMobileNumber.setStyle(style);
        txtLandNumber.setStyle(style);
        txtEmail.setStyle(style);

        if (!txtAddress.getChildrenUnmodifiable().isEmpty()) {

            ((ScrollPane) txtAddress.getChildrenUnmodifiable().get(0)).getContent().setStyle(style);

        }

        dtpDOBDate.getEditor().setStyle(style);
        dtpAssignedDate.getEditor().setStyle(style);

        cmbSearchDesignation.setStyle(style);
        cmbSearchStatus.setStyle(style);
        txtSearchName.setStyle(style);

    }
         
    private void dissableButtons(boolean select, boolean insert, boolean update, boolean delete) {

        btnAdd.setDisable(insert);
        btnUpdate.setDisable(update);
        btnDelete.setDisable(delete);

    }
    

     
     private void loadTable() {
        
        cmbSearchStatus.setItems(EmployeestatusDao.getAll());
        cmbSearchStatus.getSelectionModel().clearSelection();
        cmbSearchDesignation.setItems(DesignationDao.getAll());
        cmbSearchDesignation.getSelectionModel().clearSelection();

        txtSearchName.setText("");

        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colStatus.setCellValueFactory(new PropertyValueFactory("employeestatusId"));
        colMobile.setCellValueFactory(new PropertyValueFactory("mobile"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colDesignation.setCellValueFactory(new PropertyValueFactory("designationId"));

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

       fillTable(EmployeeDao.getAll());

        pagination.setCurrentPageIndex(0);

    }

    private void fillTable(ObservableList<Employee> employees) {

        if (employees != null && !employees.isEmpty()) {

            int rowsCount = 5;
            int pageCount = ((employees.size() - 1) / rowsCount) + 1;
            pagination.setPageCount(pageCount);

            pagination.setPageFactory((Integer pageIndex) -> {
                int start = pageIndex * rowsCount;
                int end = pageIndex == pageCount - 1 ? employees.size() : pageIndex * rowsCount + rowsCount;
                tblEmployee.getItems().clear();
                tblEmployee.setItems(FXCollections.observableArrayList(employees.subList(start, end)));
                return tblEmployee;
            });

        } else {

            pagination.setPageCount(1);
            tblEmployee.getItems().clear();

        }

        pagination.setCurrentPageIndex(page);
        tblEmployee.getSelectionModel().select(row);

    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Binding-Methods">
    @FXML
    private void txtNameKR(KeyEvent event) {
        
        if (employee.setName(txtName.getText().trim())) {
            
            if (oldEmployee != null && !employee.getName().equals(oldEmployee.getName())) {
                
                txtName.setStyle(updated);
                
            } else {
                
                txtName.setStyle(valid);
                
            }
            
        } else {
            
            txtName.setStyle(invalid);
            
        }
    }
    
    @FXML
    private void cmbCivilstatusAP(ActionEvent event) {
        
        employee.setCivilstatusId(cmbCivilstatus.getSelectionModel().getSelectedItem());
        
        if (oldEmployee != null && !employee.getCivilstatusId().equals(oldEmployee.getCivilstatusId())) {
            
            cmbCivilstatus.setStyle(updated);
            
        } else {
            
            cmbCivilstatus.setStyle(valid);
            
        }
    }
    
    @FXML
    private void txtAddressKR(KeyEvent event) {
        
        if (employee.setAddress(txtAddress.getText().trim())) {
            
            if (oldEmployee != null && !employee.getAddress().equals(oldEmployee.getAddress())) {
                
                ((ScrollPane) txtAddress.getChildrenUnmodifiable().get(0)).getContent().setStyle(updated);
                
            } else {
                
                ((ScrollPane) txtAddress.getChildrenUnmodifiable().get(0)).getContent().setStyle(valid);
                
            }
            
        } else {
            
            ((ScrollPane) txtAddress.getChildrenUnmodifiable().get(0)).getContent().setStyle(invalid);
            
        }
    }
    @FXML
    private void cmbGenderAP(ActionEvent event) {
          employee.setGenderId(cmbGender.getSelectionModel().getSelectedItem());
        if (oldEmployee != null && !employee.getGenderId().equals(oldEmployee.getGenderId())) {
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
                employee.setDob(dob);
                if (oldEmployee != null && !employee.getDob().equals(oldEmployee.getDob())) {
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
    private void txtNICKR(KeyEvent event) {
        
        if (employee.setNic(txtNIC.getText().trim())) {
            
            if (oldEmployee != null && !employee.getNic().equals(oldEmployee.getNic())) {
                
                txtNIC.setStyle(updated);
                
            } else {
                
                txtNIC.setStyle(valid);
                
            }
            
        } else {
            
            txtNIC.setStyle(invalid);
            
        }
        
    }
    
    @FXML
    private void txtMobileNumberKR(KeyEvent event) {
        
        if (employee.setMobile(txtMobileNumber.getText().trim())) {
            
            if (oldEmployee != null && !employee.getMobile().equals(oldEmployee.getMobile())) {
                
                txtMobileNumber.setStyle(updated);
                
            } else {
                
                txtMobileNumber.setStyle(valid);
                
            }
            
        } else {
            
            txtMobileNumber.setStyle(invalid);
            
        }
    }
    
    @FXML
    private void txtLandNumberKR(KeyEvent event) {
        if (employee.setLand(txtLandNumber.getText().trim())) {
            
            if (oldEmployee != null && oldEmployee.getLand() != null && employee.getLand() != null && oldEmployee.getLand().equals(employee.getLand())) {
                
                txtLandNumber.setStyle(valid);
                
            } else if (oldEmployee != null && oldEmployee.getLand() != employee.getLand()) {
                
                txtLandNumber.setStyle(updated);
                
            } else {
                
                txtLandNumber.setStyle(valid);
                
            }
            
        } else {
            
            txtLandNumber.setStyle(invalid);
            
        }
    }
    
    @FXML
    private void txtEmailKR(KeyEvent event) {
        
        if (employee.setEmail(txtEmail.getText().trim())) {
            
            if (oldEmployee != null && !employee.getEmail().equals(oldEmployee.getEmail())) {
                
                txtEmail.setStyle(updated);
                
            } else {
                
                txtEmail.setStyle(valid);
                
            }
            
        } else {
            
            txtEmail.setStyle(invalid);
            
        }
    }
    
    @FXML
    private void cmbDesignationAP(ActionEvent event) {
        
        employee.setDesignationId(cmbDesignation.getSelectionModel().getSelectedItem());
        
        if (oldEmployee != null && !employee.getDesignationId().equals(oldEmployee.getDesignationId())) {
            
            cmbDesignation.setStyle(updated);
            
        } else {
            
            cmbDesignation.setStyle(valid);
            
        }
    }
    
    @FXML
    private void cmbStatusAP(ActionEvent event) {
        
        employee.setEmployeestatusId(cmbStatus.getSelectionModel().getSelectedItem());
        
        if (oldEmployee != null && !employee.getEmployeestatusId().equals(oldEmployee.getEmployeestatusId())) {
            
            cmbStatus.setStyle(updated);
            
        } else {
            
            cmbStatus.setStyle(valid);
            
        }
    }
    
    @FXML
    private void dtpAssignedDateAP(ActionEvent event) {
        
        if (dtpAssignedDate.getValue() != null) {
            
            Date today = new Date();
            Date assign = java.sql.Date.valueOf(dtpAssignedDate.getValue());
            
            if (assign.before(today)) {
                
                employee.setAssigned(assign);
                
                if (oldEmployee != null && !employee.getAssigned().equals(oldEmployee.getAssigned())) {
                    
                    dtpAssignedDate.getEditor().setStyle(updated);
                    
                } else {
                    
                    dtpAssignedDate.getEditor().setStyle(valid);
                    
                }
                
            } else {
                
                dtpAssignedDate.getEditor().setStyle(invalid);
                employee.setAssigned(null);
                
            }
            
        }
    }
    
    @FXML
    private void btnPhotoSelectAP(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        if (lastDirectory != null) {
            fileChooser.setInitialDirectory(lastDirectory);
        }

        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(stageBasic);

        if (file != null) {

            lastDirectory = file.getParentFile();
            FileInputStream fis = null;

            try {
                fis = new FileInputStream(file);
                byte[] image = new byte[(int) file.length()];
                DataInputStream dataIs = new DataInputStream(new FileInputStream(file));
                dataIs.readFully(image);

                ImageIcon img = new ImageIcon(image);
                String extension = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf('.'));
                if (extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg") || extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".gif")) {

                    if (img.getIconHeight() <= 100 && img.getIconWidth() <= 100) {
                        
                        Image photo = new Image(fis);
                        imgPhoto.setImage(photo);
                        employee.setImage(image);
                        photoSelected = true;

                    } else {

                        Alert alert = new Alert(Alert.AlertType.ERROR);

                        alert.setTitle("Error - Employee Add");
                        alert.setHeaderText("Photo Selection Error");
                        alert.setContentText("The Image Size should smaller than 100*100 Pixel");
                        
                        DialogPane dialogPane = alert.getDialogPane();

                        dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
                        dialogPane.getStyleClass().add("myDialogForError");
                        
                        alert.showAndWait();

                        photoSelected = false;

                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);

                    alert.setTitle("Error - Employee Add");
                    alert.setHeaderText("Photo Selection Error");
                    alert.setContentText("The Image Size should smaller than 100*100 Pixel");
                    
                    DialogPane dialogPane = alert.getDialogPane();

                    dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
                    dialogPane.getStyleClass().add("myDialogForError");
                    
                    alert.showAndWait();

                    photoSelected = false;

                }

            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        } else {
            photoSelected = false;
        }
    }
    
    @FXML
    private void btnPhotoClearAP(ActionEvent event) {
        if (employee.getImage() != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Add Employee");
            alert.setHeaderText("Clear Photo");
            alert.setContentText("Are you sure you need to clear the selectd photo???");
            
            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForConfirmation");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {

                imgPhoto.setImage(new Image("/image/user.png"));

                if (oldEmployee != null && oldEmployee.getImage() != null) {
                    
                    photoSelected = true;
                    
                } else {
                    
                    photoSelected = false;
                    
                }

                employee.setImage(null);

            }

        }

    }
    
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="operation-Methods">
    @FXML
    private void btnClearAP(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        
        
        DialogPane dialogPane = alert.getDialogPane();
        
        dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialogForConfirmation");
        
        alert.setTitle(" Employee Management");
        alert.setHeaderText("Clear Form");
        alert.setContentText("Are you sure you need to clear form?? ");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            
            loadForm();
            
        }
        
    }
    
    @FXML
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
    
    String details =
            "\nName          \t\t: " + employee.getName()
            + "\nGender        \t\t: " + employee.getGenderId().getName()
            + "\nCivil Status  \t\t: " + employee.getCivilstatusId().getName()
            + "\nAddress       \t\t: " + employee.getAddress()
            + "\nBirth Date    \t\t: " + employee.getDob().toString()
            + "\nNIC No        \t\t: " + employee.getNic()
            + "\nMobile No     \t\t: " + employee.getMobile()
            + "\nLand          \t\t: " + (employee.getLand() != null ? employee.getLand() : "Not Entered")
            + "\nEmail         \t\t: " + employee.getEmail()
            + "\nDesignation   \t\t: " + employee.getDesignationId().getName()
            + "\nAssigned Date \t: " + employee.getAssigned().toString()
            + "\nPhoto         \t\t: " + (employee.getImage() != null ? "Selected" : "Not Selected")
            + "\nStatus        \t\t: " + employee.getEmployeestatusId().getName();
    
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Add Module");
    alert.setHeaderText("Are you sure you need to add the following Employee???");
    alert.setContentText(details);
    
    DialogPane dialogPane = alert.getDialogPane();
    
    dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
    dialogPane.getStyleClass().add("myDialogForConfirmation");
    
    Optional<ButtonType> result = alert.showAndWait();
    
    if (result.get() == ButtonType.OK) {
        
        employee.setDisable(0);
        
        EmployeeDao.add(employee);
        
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
    private void btnDeleteAP(ActionEvent event) {
        String details =
                "\nName          \t: " + oldEmployee.getName()
                + "\nGender Type  : " + oldEmployee.getGenderId().getName()
                + "\nDate of Birth \t: " + oldEmployee.getDob().toString()
                + "\nNIC           \t: " + oldEmployee.getNic()
                + "\nCivilstatus   \t: " + oldEmployee.getCivilstatusId().getName()
                + "\nAddress       \t: " + oldEmployee.getAddress()
                + "\nMobile No     \t: " + oldEmployee.getMobile()
                + "\nLand No       \t: " + oldEmployee.getLand()
                + "\nEmail         \t: " + oldEmployee.getEmail()
                + "\nPhoto         \t: will be deleted.."
                + "\nDesignation   \t: " + oldEmployee.getDesignationId().getName()
                + "\nAssing Date   \t: " + oldEmployee.getAssigned().toString()
                + "\nStatus        \t: " + oldEmployee.getEmployeestatusId().getName();
        
        Alert alertForDelete = new Alert(Alert.AlertType.CONFIRMATION);
        
        alertForDelete.setTitle("Delete Module");
        alertForDelete.setHeaderText("Are you sure you need to delete the following Module?");
        alertForDelete.setContentText(details);
        
        DialogPane dialogPane = alertForDelete.getDialogPane();
        
        dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialogForConfirmation");
        
        Optional<ButtonType> result = alertForDelete.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            
            
//            EmployeeDao.delete(oldEmployee);

oldEmployee.setDisable(1);

EmployeeDao.update(oldEmployee);

//            fillTable(EmployeeDao.getAll());

loadTable();

loadForm();

pagination.setCurrentPageIndex(page);
tblEmployee.getSelectionModel().select(row);

        }
        
    }
    @FXML
    private void tblEmployeeMC(MouseEvent event) {
        fillForm();
    }
    
    @FXML
    private void tblEmployeeKR(KeyEvent event) {
        fillForm();
    }
    private void fillForm() {
        
        if (tblEmployee.getSelectionModel().getSelectedItem() != null) {
            
            photoSelected = false;
            
            dissableButtons(false, true, false, false);
            
            setStyle(valid);
            
            cmbStatus.setDisable(false);
            
            cmbSearchDesignation.setStyle(initial);
            cmbSearchStatus.setStyle(initial);
            txtSearchName.setStyle(initial);
            
            oldEmployee = EmployeeDao.getById(tblEmployee.getSelectionModel().getSelectedItem().getId());
            employee = EmployeeDao.getById(tblEmployee.getSelectionModel().getSelectedItem().getId());
            
            cmbCivilstatus.getSelectionModel().select((Civilstatus) oldEmployee.getCivilstatusId());
            cmbDesignation.getSelectionModel().select((Designation) oldEmployee.getDesignationId());
            cmbStatus.getSelectionModel().select((Employeestatus) oldEmployee.getEmployeestatusId());
            cmbGender.getSelectionModel().select((Gender) oldEmployee.getGenderId());
            
            txtName.setText(oldEmployee.getName());
            txtAddress.setText(oldEmployee.getAddress());
            
//            oldNicWithOldFormateWithV = oldEmployee.getNic().substring(2, 7) + oldEmployee.getNic().substring(8) + "v";
//            oldNicWithOldFormateWithX = oldEmployee.getNic().substring(2, 7) + oldEmployee.getNic().substring(8) + "x";

txtNIC.setText(oldEmployee.getNic());

txtMobileNumber.setText(oldEmployee.getMobile());

if (oldEmployee.getLand() != null) {
    
    txtLandNumber.setText(oldEmployee.getLand());
    
} else {
    
    txtLandNumber.setText("");
    
}

txtEmail.setText(oldEmployee.getEmail());

dtpDOBDate.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(oldEmployee.getDob())));
dtpDOBDate.getEditor().setText(new SimpleDateFormat("yyyy-MM-dd").format(oldEmployee.getDob()));

//            lblDob.setText(oldEmployee.getDob().toString());
//            lblGender.setText(oldEmployee.getGenderId().getName());

dtpAssignedDate.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(oldEmployee.getAssigned())));
dtpAssignedDate.getEditor().setText(new SimpleDateFormat("yyyy-MM-dd").format(oldEmployee.getAssigned()));

if (oldEmployee.getImage() != null) {
    imgPhoto.setImage(new Image(new ByteArrayInputStream(oldEmployee.getImage())));
} else {
    imgPhoto.setImage(new Image("/image/user.png"));
}

lblEmployeeID.setText(String.format("%06d", oldEmployee.getId() ));

page = pagination.getCurrentPageIndex();
row = tblEmployee.getSelectionModel().getSelectedIndex();

        }
        
    }
    private String getErrors() {
        
        String errors = "";
        
        if (employee.getName() == null) {
            errors = errors + "Name \t\tis Invalid\n";
        }
        
        if (employee.getGenderId() == null) {
            errors = errors + "Gender \t\tis Not Selected\n";
        }
        
        if (employee.getCivilstatusId() == null) {
            errors = errors + "Civilstatus \tis Not Selected\n";
        }
        
        if (employee.getAddress() == null) {
            errors = errors + "Address \t\tis Invalid\n";
        }
        
        if (employee.getDob() == null) {
            
            if (dtpDOBDate.getValue() == null ) {
                
                errors = errors + "Birth Date \tis not seleted\n";
                
            } else {
                
                errors = errors + "Birth Date \tAge is less than 18\n";
                
            }
            
        }
        
        if (employee.getNic() == null) {
            errors = errors + "NIC No. \t\tis Invalid or Already in\n";
        }
        
        if (employee.getMobile() == null) {
            errors = errors + "Mobile No. \tis Invalid\n";
        }
        
        if (txtLandNumber.getText() != null && !employee.setLand(txtLandNumber.getText().trim())) {
            errors = errors + "Land No. \t\tis Invalid\n";
        }
        
        if (employee.getEmail() == null) {
            errors = errors + "Email \t\tis Invalid\n";
        }
        
        if (employee.getDesignationId() == null) {
            errors = errors + "Designation \tis Not Selected\n";
        }
        
        if (employee.getAssigned() == null) {
            errors = errors + "Assign Date \tis Invalid\n";
        }
        
        if (employee.getEmployeestatusId() == null) {
            errors = errors + "Status \t\tis Not Selected\n";
        }
        
        return errors;
        
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

                dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialogForConfirmation");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                     
                  //  Notification.Notifier.INSTANCE.notifySuccess("Update", employee.getName() + " is updated!");

                    EmployeeDao.update(employee);

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

            dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForError");

            alert.showAndWait();

        }
        
        
    }
        private String getUpdates() {

        String updates = "";

        if (oldEmployee != null) {

            if (employee.getName() != null && !employee.getName().equals(oldEmployee.getName())) {
                updates = updates + oldEmployee.getName() + " chnaged to " + employee.getName() + "\n";
            }

            if (employee.getAddress() != null && !employee.getAddress().equals(oldEmployee.getAddress())) {
                updates = updates + oldEmployee.getAddress() + " chnaged to " + employee.getAddress() + "\n";
            }

            if (employee.getNic() != null && !employee.getNic().equals(oldEmployee.getNic())) {
                updates = updates + oldEmployee.getNic() + " chnaged to " + employee.getNic() + "\n";
            }

//            if (!(oldEmployee.getLand() != null && employee.getLand() != null && oldEmployee.getLand().equals(employee.getLand()))) {
//                if (oldEmployee.getLand() != employee.getLand()) {
//                    updates = updates + oldEmployee.getLand() + " chnaged to " + employee.getLand() + "\n";
//                }
//            }

            if (!(oldEmployee.getLand() != null && employee.getLand() != null && oldEmployee.getLand().equals(employee.getLand()))) {

                if (oldEmployee.getLand() != employee.getLand()) {

                    updates = updates
                            + (oldEmployee.getLand() == null ? "Nothing" : oldEmployee.getLand())
                            + " chnaged to "
                            + (employee.getLand() == null ? "Nothing" : employee.getLand())
                            + "\n";

                }

            }

            if (employee.getEmail() != null && !employee.getEmail().equals(oldEmployee.getEmail())) {
                updates = updates + oldEmployee.getEmail() + " chnaged to " + employee.getEmail() + "\n";
            }

            if (employee.getMobile() != null && !employee.getMobile().equals(oldEmployee.getMobile())) {
                updates = updates + oldEmployee.getMobile() + " chnaged to " + employee.getMobile() + "\n";
            }

            if (employee.getGenderId() != null && !employee.getGenderId().equals(oldEmployee.getGenderId())) {
                updates = updates + oldEmployee.getGenderId() + " chnaged to " + employee.getGenderId() + "\n";
            }

            if (employee.getCivilstatusId() != null && !employee.getCivilstatusId().equals(oldEmployee.getCivilstatusId())) {
                updates = updates + oldEmployee.getCivilstatusId() + " chnaged to " + employee.getCivilstatusId() + "\n";
            }

            if (employee.getDesignationId() != null && !employee.getDesignationId().equals(oldEmployee.getDesignationId())) {
                updates = updates + oldEmployee.getDesignationId() + " chnaged to " + employee.getDesignationId() + "\n";
            }

            if (employee.getDob() != null && !employee.getDob().equals(oldEmployee.getDob())) {
                updates = updates + oldEmployee.getDob().toString() + "(dob)" + " chnaged to " + employee.getDob().toString() + "\n";
            }

            if (employee.getAssigned() != null && !employee.getAssigned().equals(oldEmployee.getAssigned())) {
                updates = updates + oldEmployee.getAssigned().toString() + " chnaged to " + employee.getAssigned().toString() + "\n";
            }

            if ( photoSelected ) {
                updates = updates + "Photo Changed\n";
            }
            
            if (employee.getEmployeestatusId() != null && !employee.getEmployeestatusId().equals(oldEmployee.getEmployeestatusId())) {
                updates = updates + oldEmployee.getEmployeestatusId() + " chnaged to " + employee.getEmployeestatusId() + "\n";
            }
        }

        return updates;

    }
//</editor-fold>
   
    //<editor-fold defaultstate="collapsed" desc="Search-Methods">
        @FXML
        private void txtSearchNameKR(KeyEvent event) {
            updateTable();
        }
        
        @FXML
        private void cmbSearchStatusAP(ActionEvent event) {
            if (cmbSearchStatus.getSelectionModel().getSelectedItem() != null) {
                
                updateTable();
                
            }
        }
        
        @FXML
        private void cmbSearchDesignationAP(ActionEvent event) {
            if (cmbSearchDesignation.getSelectionModel().getSelectedItem() != null) {
                
                updateTable();
                
            }
        }
        
        
        @FXML
        private void btnSearchClearAP(ActionEvent event) {
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            
            alert.setTitle(" Employee Management");
            alert.setHeaderText("Clear Search Form");
            alert.setContentText("Are you sure you need to clear search form ????");
            
            DialogPane dialogPane = alert.getDialogPane();
            
            dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForConfirmation");
            
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK) {
                
                loadTable();
//            if (privilege.get("Employee_select")) {
//
//                //Notification.Notifier.INSTANCE.notifySuccess("Search Form", "The Search Fields are cleared!" );
//
//
//
//            }

            }
        }
        
        
//    private void lblNewDesignationAddMC(MouseEvent event) throws IOException{
//        System.out.println("yyyyy");
//        Parent root = FXMLLoader.load(getClass().getResource("DesignationUI.fxml"));
//
//        Scene scene = new Scene(root);
//
//        designationStage = new Stage();
//        designationStage.setScene(scene);
//
//        designationStage.setOnCloseRequest(e -> {
//
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//
//            alert.setTitle(" WINDOW CLOSE");
//            alert.setHeaderText("Close Form");
//            alert.setContentText("Are you sure you need to close this form?? ");
//
//            DialogPane dialogPane = alert.getDialogPane();
//
//            dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
//            dialogPane.getStyleClass().add("myDialogForConfirmation");
//
//            Optional<ButtonType> result = alert.showAndWait();
//
//            if (result.get() == ButtonType.OK) {
//
//                designationStage.close();
//
//            }
//
//            e.consume();
//
//        });
//
//        designationStage.initModality(Modality.WINDOW_MODAL);
//        designationStage.initOwner(stageBasic);
//        designationStage.setResizable(false);
//        designationStage.setTitle("Oder and Payment Management System");
//        designationStage.show();
//    }
        
        
        private void updateTable() {
            
            String name = txtSearchName.getText().trim();
            boolean sname = !name.isEmpty();
            Employeestatus status = cmbSearchStatus.getSelectionModel().getSelectedItem();
            boolean sstatus = cmbSearchStatus.getSelectionModel().getSelectedIndex() != -1;
            Designation designation = cmbSearchDesignation.getSelectionModel().getSelectedItem();
            boolean sdesignation = cmbSearchDesignation.getSelectionModel().getSelectedIndex() != -1;
            
            if (!sname && !sstatus && !sdesignation) {
                fillTable(EmployeeDao.getAll());
            }
            if (sname && !sstatus && !sdesignation) {
                fillTable(EmployeeDao.getAllByName(name));
            }
            if (!sname && !sstatus && sdesignation) {
                fillTable(EmployeeDao.getAllByDesignation(designation));
            }
            if (!sname && sstatus && !sdesignation) {
                fillTable(EmployeeDao.getAllByStatus(status));
            }
            if (sname && sstatus && !sdesignation) {
                fillTable(EmployeeDao.getAllByNameStatus(name, status));
            }
            if (sname && !sstatus && sdesignation) {
                fillTable(EmployeeDao.getAllByNameDesignation(name, designation));
            }
            if (!sname && sstatus && sdesignation) {
                fillTable(EmployeeDao.getAllByStatusDesignation(status, designation));
            }
            if (sname && sstatus && sdesignation) {
                fillTable(EmployeeDao.getAllByNameStatusDesignation(name, status, designation));
            }
            
        }
        
        @FXML
        private void btnNewDesignationAP(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("DesignationUI.fxml"));
            
            Scene scene = new Scene(root);
            
            designationStage = new Stage();
            designationStage.setScene(scene);
            
            designationStage.setOnCloseRequest(e -> {
                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                
                alert.setTitle(" WINDOW CLOSE");
                alert.setHeaderText("Close Form");
                alert.setContentText("Are you sure you need to close this form?? ");
                
                DialogPane dialogPane = alert.getDialogPane();
                
                dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialogForConfirmation");
                
                Optional<ButtonType> result = alert.showAndWait();
                
                if (result.get() == ButtonType.OK) {
                    
                    designationStage.close();
                    
                }
                
                e.consume();
                
            });
            
            designationStage.initModality(Modality.WINDOW_MODAL);
            designationStage.initOwner(stageBasic);
            designationStage.setResizable(false);
            designationStage.setTitle("Oder and Payment Management System");
            designationStage.show();
            
        }
        
//</editor-fold>
    
}
