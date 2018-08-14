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
    
  

    }
//</editor-fold>
   
    
    
}
