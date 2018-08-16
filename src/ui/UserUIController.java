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
    
    //<editor-fold defaultstate="collapsed" desc="Binding-Methods">
    
    
    @FXML
    private void cmbEmployeeAP(ActionEvent event) {
        
         boolean validity = userForForm.setEmployeeId(cmbEmployee.getSelectionModel().getSelectedItem());

        if (validity) {

            if (oldUserForForm != null && !userForForm.getEmployeeId().equals(oldUserForForm.getEmployeeId())) {

                cmbEmployee.setStyle(updated);

            } else {

                cmbEmployee.setStyle(valid);

            }

        }
        
    }
    
    
    @FXML
    private void txtUsernameKR(KeyEvent event) {
              
        if(txtUsername.getText().trim().matches("[A-Za-z0-9]{3}[A-Za-z0-9]+")){
        
            if ( UserDao.getUnassignedUsername(txtUsername.getText().trim()).isEmpty() ) {
                
                userForForm.setUsername(txtUsername.getText().trim());

//                if (oldUserForForm != null && !userForForm.getUsername().equals(oldUserForForm.getUsername())) {
//                    
//                    if (UserDao.getUnassignedUsername(txtUsername.getText().trim()).isEmpty()) {
//
//                        txtUsername.setStyle(updated);
//                        
//                    } else {
//                        
//                       userForForm.setUsername(null);
//                        
//                    }
//                    
//                } else {
//                    
//                    txtUsername.setStyle(valid);
//                    
//                }
                
                if (oldUserForForm != null && !userForForm.getUsername().equals(oldUserForForm.getUsername())) {

                    txtUsername.setStyle(updated);

                } else {

                    txtUsername.setStyle(valid);

                }

            }else{
            
                txtUsername.setStyle(invalid);
                userForForm.setUsername(null);
                           
            }
        
        }else{
        
            txtUsername.setStyle(invalid);
            userForForm.setUsername(null);
            
        }
    }
    
    @FXML
    private void pswPasswordKR(KeyEvent event) {
        
        if( userForForm.setPassword(pswPassword.getText().trim()) ){
        
            if (oldUserForForm != null && !userForForm.getPassword().equals(oldUserForForm.getPassword())) {

                pswPassword.setStyle(updated);

            } else {

                pswPassword.setStyle(valid);

            }
            
            getConfirmPasswordValidation();
        
        }else{
        
            pswPassword.setStyle(invalid);
            
        }
    }
   
    
    @FXML
    private void pswRetypePasswordKR(KeyEvent event) {
        
        if (pswPassword.getText().trim().equals(pswRetypePassword.getText().trim())) {                

            pswRetypePassword.setStyle(valid);

        } else {

            pswRetypePassword.setStyle(invalid);

        }
    }
    private void getConfirmPasswordValidation() {
       
        if (pswPassword.getText().trim().equals(pswRetypePassword.getText().trim())) {                

            pswRetypePassword.setStyle(valid);

        } else {

            pswRetypePassword.setStyle(invalid);

        }
        
    }
    
    @FXML
    private void txtHintKR(KeyEvent event) {
       //       if (txtHint.getText().trim() != null ) {
            
            if (userForForm.setHint(txtHint.getText().trim())) {
                
                if (oldUserForForm != null && oldUserForForm.getHint() != null && userForForm.getHint() != null && oldUserForForm.getHint().equals(userForForm.getHint())) {
                    
                    txtHint.setStyle(valid);
                    
                } else if (oldUserForForm != null && oldUserForForm.getHint() != userForForm.getHint()) {
                    
                    txtHint.setStyle(updated);
                    
                } else {
                    
                    txtHint.setStyle(valid);
                    
                }
                
            } else {
                
                txtHint.setStyle(invalid);
                
            }
            
    //  }
    }
    
    @FXML
    private void btnRightAllAP(ActionEvent event) {
        lstRight.setItems(RoleDao.getAll());
        lstLeft.getItems().clear();
        validateList(); 
    }
    
    @FXML
    private void btnRightAP(ActionEvent event) {
        lstRight.getItems().addAll(lstLeft.getSelectionModel().getSelectedItems());
        lstLeft.getItems().removeAll(lstLeft.getSelectionModel().getSelectedItems()); 
        validateList();
    }
    
    @FXML
    private void btnLeftAP(ActionEvent event) {
        
        lstLeft.getItems().addAll(lstRight.getSelectionModel().getSelectedItems());
        lstRight.getItems().removeAll(lstRight.getSelectionModel().getSelectedItems()); 
        validateList();
    }
    
    @FXML
    private void btnLeftAllAP(ActionEvent event) {
        lstLeft.setItems(RoleDao.getAll());
        lstRight.getItems().clear();
        validateList();
    }
        private void validateList(){
        
        if (userForForm.setRoleList(lstRight.getItems())) {
            lstRight.setStyle(valid);
        } else {
            lstRight.setStyle(invalid);
        }

        if (lstLeft.getItems().isEmpty()) {
            
            btnRight.setDisable(true);
            btnRightAll.setDisable(true);
            btnLeft.setDisable(false);
            btnLeftAll.setDisable(false);
            
        } else if (lstRight.getItems().isEmpty()) {
            
            btnRight.setDisable(false);
            btnRightAll.setDisable(false);
            btnLeft.setDisable(true);
            btnLeftAll.setDisable(true);
            
        } else {
            
            btnRight.setDisable(false);
            btnRightAll.setDisable(false);
            btnLeft.setDisable(false);
            btnLeftAll.setDisable(false);

        }
        
//        userForForm.setRoleList();
        
    }
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Operation-Methods">
       @FXML
    private void btnAddAP(ActionEvent event) {
        
        String errors = getErrors();
                 
//        for (int i = 0; i < arr.length; i++) {
//            Object arr = arr[i];
//            
//        }
        
        if ( errors.isEmpty() ) {
            
            String details = "Ara you sure you need to add this Employee with following details\n "
                    + "\nEmployee       \t:" + userForForm.getEmployeeId().getName()
                    + "\nUsername\t:" + userForForm.getUsername()
                    + "\nRoles          \t:" + userForForm.getRoleList().toString();
                
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add User");
            alert.setHeaderText("Are you sure you need to add the following User??");
            alert.setContentText(details);
            
            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForConfirmation");
            
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK) {
                
                userForForm.setDisable(0);
                
                UserDao.add(userForForm);

                loadTable();
                loadForm();
            
            }
            
            
        }else{
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You need to fill the following User");
            alert.setContentText(errors);
            
            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForError");
            
//            Optional<ButtonType> result = alert.showAndWait();
//            
//            if (result.get() == ButtonType.OK) {    }
            
            alert.showAndWait();
            
        }
        
    }
    
    private String getErrors(){
        
        String errors = "";
        
        if (userForForm.getUsername() == null) {
            errors = errors + "Username \t\tis Invalid or already in.\n";
        }
        if (userForForm.getEmployeeId() == null) {
            errors = errors + "Employee \t\tis Not Selected.\n";
        }
        if (userForForm.getRoleList() == null) {
            errors = errors + "Roles \t\t\tare Not Selected.\n";
        }
        if (userForForm.getPassword() == null) {
            errors = errors + "Password \t\tis Invalid.\n";
        }
        
        if (!pswPassword.getText().equals(pswRetypePassword.getText())) {
            errors = errors + "Confirm Password \t\tis Mismatch.\n";
        }
        
        if (txtHint.getText() != null && !userForForm.setHint(txtHint.getText().trim())) {
            errors = errors + "Hint \t\tshoud have at least two letter.\n";
        }
        
        return errors;
        
    }
    private String getUpdates(){
        
        String updates = "";
        
        if(oldUserForForm != null ){
            
            if(userForForm.getEmployeeId() != null && !userForForm.getEmployeeId().equals(oldUserForForm.getEmployeeId())){
            
                updates = updates + oldUserForForm.getEmployeeId().getName() + " changed to " + userForForm.getEmployeeId().getName() + "\n";
            
            }
            
            if(userForForm.getUsername() != null && !userForForm.getUsername().equals(oldUserForForm.getUsername())){
            
                updates = updates + oldUserForForm.getUsername() + " changed to " + userForForm.getUsername() + "\n";
            
            }
            
//            if(userForForm.getPassword() != null && !userForForm.getPassword().equals(oldUserForForm.getPassword())){
//            
//                updates = updates + oldUserForForm.getPassword() + " changed to " + userForForm.getPassword() + "\n";
//            
//            }

            if(userForForm.getPassword() != null && !userForForm.getPassword().equals(oldUserForForm.getPassword())){
            
                updates = updates + "Password is going to change\n";
            
            }
            
//            if(userForForm.getRoleList() != null && !userForForm.getRoleList().equals(oldUserForForm.getRoleList())){
//            
//                updates = updates + oldUserForForm.getRoleList().toString() + " changed to " + userForForm.getRoleList().toString() + "\n";
//            
//            }
            
             if( !(userForForm.getRoleList().containsAll(oldUserForForm.getRoleList()) && oldUserForForm.getRoleList().containsAll(userForForm.getRoleList()))){
            
                updates = updates + oldUserForForm.getRoleList().toString() + " changed to " + userForForm.getRoleList().toString() + "\n";
            
            }
             
            if (!(oldUserForForm.getHint() != null && userForForm.getHint() != null && oldUserForForm.getHint().equals(userForForm.getHint()))) {

                if (oldUserForForm.getHint() != userForForm.getHint()) {

                    updates = updates
                            + (oldUserForForm.getHint() == null ? "Nothing" : oldUserForForm.getHint())
                            
                            + " chnaged to "
                            
                            + (userForForm.getHint() == null ? "Nothing" : userForForm.getHint())
                            
                            + "\n";

                }
                
            }

        }

        return updates;
        
        
    }
    
    @FXML
    private void btnClearAP(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(" User Management");
        alert.setHeaderText("Clear Form");
        alert.setContentText("Are you sure you need to clear form???");

        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialogForConfirmation");
        
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            
           // Notification.Notifier.INSTANCE.notifySuccess("Clear Form", "The Form is cleared!" );

            loadForm();     

        }
    }
    
    @FXML
    private void btnUpdateAP(ActionEvent event) {
    }
    @FXML
    private void btnDeleteAP(ActionEvent event) {
         String details =
                  "\nEmployee                   \t: " + oldUserForForm.getEmployeeId().getName()
                + "\nUsername Type          \t: " + oldUserForForm.getUsername()
                + "\nRole List Type         \t: " + oldUserForForm.getRoleList().toString();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete User");
        alert.setHeaderText("Are you sure you need to delete the following User?");
        alert.setContentText(details);
        
        DialogPane dialogPane = alert.getDialogPane();
        
        dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialogForConfirmation");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            
//            userForForm.setRoleList(null);

//            UserDao.delete(oldUserForForm);

            oldUserForForm.setDisable(1);
            
          //  Notification.Notifier.INSTANCE.notifySuccess("Delete",  oldUserForForm.getEmployeeId().getName() + " is deleted!" );

            UserDao.update(oldUserForForm);

//            loadTable();

            fillTable(UserDao.getAll()); 
            loadForm();

            pagination.setCurrentPageIndex(page);
            tblUser.getSelectionModel().select(row);
            
        }
    }
        @FXML
    private void tblUserMC(MouseEvent event) {
        fillForm();
    }

    @FXML
    private void tblUserKR(KeyEvent event) {
        fillForm();
    }
    private void fillForm(){
    
        if (tblUser.getSelectionModel().getSelectedItem() != null ) {
            
            oldUserForForm = UserDao.getById(tblUser.getSelectionModel().getSelectedItem().getId());
            userForForm = UserDao.getById(tblUser.getSelectionModel().getSelectedItem().getId());
            
            txtUsername.setText(String.valueOf(oldUserForForm.getUsername()));
            pswPassword.setText(Security.decrypt(oldUserForForm.getPassword()));
                
            if (oldUserForForm.getHint() != null) {
                
                txtHint.setText(oldUserForForm.getHint());
                
            }else{
                
                txtHint.setText("");
        
            }

            cmbEmployee.getItems().clear();
            cmbEmployee.getItems().add((Employee) oldUserForForm.getEmployeeId());
            cmbEmployee.getSelectionModel().select(oldUserForForm.getEmployeeId());
            
            pswRetypePassword.setText(Security.decrypt(oldUserForForm.getPassword()));

            lstRight.setItems(FXCollections.observableArrayList(oldUserForForm.getRoleList()));  //problem
            System.out.println("fuck");
            lstLeft.setItems(RoleDao.getAll());
            lstLeft.getItems().removeAll(FXCollections.observableArrayList(oldUserForForm.getRoleList()));
            
            setStyle(valid);
            
            dissableButtons(true, true, false, false);
            
            validateList();
            
            page = pagination.getCurrentPageIndex();                   
            row = tblUser.getSelectionModel().getSelectedIndex();
            
            txtSearchEmpName.setStyle(initial);
            txtSearchUsername.setStyle(initial);
            cmbSearchRole.setStyle(initial);
            
           // Notification.Notifier.INSTANCE.notifyInfo("Selected", oldUserForForm.getEmployeeId().getName() + " is selected!");
            
        }
    
    }
//</editor-fold>

    

    
}
