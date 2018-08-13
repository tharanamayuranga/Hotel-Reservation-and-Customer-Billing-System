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

       //fillTable(EmployeeDao.getAll());

        pagination.setCurrentPageIndex(0);

    }

    
    
//</editor-fold>
    
   

   
    
    
}
