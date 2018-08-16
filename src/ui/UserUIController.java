/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dao.EmployeeDao;
import dao.RoleDao;
import dao.UserDao;
import entity.Employee;
import entity.Role;
import entity.User;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.Security;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class UserUIController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="Form-Data">
    User userForForm;
    User oldUserForForm;
    
    private Stage stageUser;
    
    String initial;
    String valid;
    String invalid;
    String updated;
    
    int page;
    int row;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="FXML-Data">
    @FXML
    private Pagination pagination;
    @FXML
    private JFXTextField txtSearchUsername;
    @FXML
    private JFXTextField txtSearchEmpName;
    @FXML
    private JFXComboBox<Role> cmbSearchRole;
    @FXML
    private JFXComboBox<Employee> cmbEmployee;
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField pswPassword;
    @FXML
    private JFXPasswordField pswRetypePassword;
    @FXML
    private JFXTextField txtHint;
    @FXML
    private ListView<Role> lstLeft;
    @FXML
    private ListView<Role> lstRight;
    @FXML
    private Button btnRightAll;
    @FXML
    private Button btnRight;
    @FXML
    private Button btnLeft;
    @FXML
    private Button btnLeftAll;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableView<User> tblUser;
    @FXML
    private TableColumn<User, String> colUsername;
    @FXML
    private TableColumn<User, Employee> colEmployee;
    @FXML
    private Button btnSearchClear;
    @FXML
    private Button btnDelete;
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

        userForForm = new User();
        oldUserForForm = null;
        
        cmbEmployee.setItems(EmployeeDao.getAllExceptUsers());//get all cmb details
        cmbEmployee.getSelectionModel().clearSelection();//clear the selected item
        
        txtUsername.setText("");
        pswPassword.setText("");
        pswRetypePassword.setText("");
        txtHint.setText("");
        
        lstLeft.setItems(RoleDao.getAll());
        lstRight.getItems().clear();

        dissableButtons(false, false, true, true);
        
        setStyle(initial);
        
//        validateList();
    }
    
    private void setStyle(String style){
        
        cmbEmployee.setStyle(style);
        
        pswPassword.setStyle(style);
        pswRetypePassword.setStyle(style);
        
        lstLeft.setStyle(style);
        lstRight.setStyle(style);
        
        txtUsername.setStyle(style);
        txtHint.setStyle(style);
        
        cmbSearchRole.setStyle(style);
        txtSearchEmpName.setStyle(style);
        txtSearchUsername.setStyle(style);
        
    }
        private void dissableButtons( boolean select , boolean insert , boolean update , boolean delete ){
        
        btnAdd.setDisable(insert);
        btnUpdate.setDisable(update);
        btnDelete.setDisable(delete);
        
    }
    
    private void loadTable(){
        
//        tblUser.setRowFactory(new Callback<TableView<User>, TableRow<User>>() {
//            
//            @Override
//            public TableRow<User> call(TableView<User> dateTableView) {
//                
//                return new TableRow<User>() {
//                    
//                    @Override
//                    protected void updateItem(User userForForm , boolean b ) {
//                        super.updateItem( userForForm , b );
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
        
        cmbSearchRole.setItems(RoleDao.getAll());
        cmbSearchRole.getSelectionModel().clearSelection();
        txtSearchEmpName.setText("");
        txtSearchUsername.setText("");
        
        colEmployee.setCellValueFactory(new PropertyValueFactory("employeeId"));
        colUsername.setCellValueFactory(new PropertyValueFactory("username"));
        
        fillTable(UserDao.getAll());
        
//        UserDao.getAll();
        
    }
    
    private void fillTable(ObservableList<User> userForForms){
        
        if ( userForForm != null && !userForForms.isEmpty()) {
            
            int rowsCount = 4;
            int pageCount = ((userForForms.size()-1)/rowsCount)+1;
            pagination.setPageCount(pageCount);
            
            pagination.setPageFactory((Integer pageIndex) -> {
                int start = pageIndex * rowsCount;
                int end = pageIndex == pageCount - 1 ? userForForms.size() : pageIndex * rowsCount + rowsCount;
                tblUser.getItems().clear();
                tblUser.setItems(FXCollections.observableArrayList(userForForms.subList(start, end)));
                return tblUser;
            });
            
        } else {
            
            pagination.setPageCount(1);
            tblUser.getItems().clear();
            
        }
        
        pagination.setCurrentPageIndex(page);
        tblUser.getSelectionModel().select(row);
        
    }
    
//</editor-fold>
    
    
    
}
