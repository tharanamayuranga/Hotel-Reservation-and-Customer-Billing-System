/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import dao.ModuleDao;
import dao.PrivilegeDao;
import dao.RoleDao;
import entity.Module;
import entity.Privilege;
import entity.Role;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class PrivilageController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="FXML-Data">
    @FXML
    private Label lblAddNewRole;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnSearchClear;
    @FXML
    private Pagination pagination;
    @FXML
    private TableColumn<Privilege, Role> colRole;
    @FXML
    private TableColumn<Privilege, Module> colModule;
    @FXML
    private TableColumn<Privilege, ImageView> colSelect;
    @FXML
    private TableColumn<Privilege, ImageView> colInsert;
    @FXML
    private TableColumn<Privilege, ImageView> colUpdate;
    @FXML
    private TableColumn<Privilege, ImageView> colDelete;
    @FXML
    private JFXCheckBox cbxSelect;
    @FXML
    private JFXCheckBox cbxInsert;
    @FXML
    private JFXCheckBox cbxUpdate;
    @FXML
    private JFXCheckBox cbxDelete;
    @FXML
    private JFXComboBox<Module> cmbSearchModule;
    @FXML
    private JFXComboBox<Role> cmbSearchRole;
    @FXML
    private JFXComboBox<Module> cmbModule;
    @FXML
    private JFXComboBox<Role> cmbRole;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<Privilege> tblPrivilege;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Form-Data">
    Privilege privilegeForForm;
    Privilege oldPrivilegeForForm;
    
    String initial;
    String valid;
    String invalid;
    String updated;
    
    int page;
    int row;
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
        
        privilegeForForm = new Privilege();
        oldPrivilegeForForm = null;
        
//        cmbModule.setEditable(true);
        
//        cmbRole.setItems(RoleDao.getAllUnassignedByRole());
        cmbRole.setItems(RoleDao.getAll());
        cmbRole.getSelectionModel().clearSelection();
        cmbModule.setItems( ModuleDao.getAll() );
        cmbModule.getSelectionModel().clearSelection();
        
//        cbxSelect.setSelected(true);
//        cbxSelect.setDisable(true); 
        cbxSelect.setSelected(false);
        cbxInsert.setSelected(false);
        cbxUpdate.setSelected(false);
        cbxDelete.setSelected(false);       
        
        dissableButtons(false, false, true, true);
        
        setStyle(initial);
        
//        RoleDao.getAll();
//        ModulaDao.getAllUnassignedByRole();
        
    }
     private void setStyle(String style){
    
        cmbModule.setStyle(style);
        cmbRole.setStyle(style);
        
        cmbSearchModule.setStyle(style);
        cmbSearchRole.setStyle(style);
    
    }
    
   private void dissableButtons( boolean select , boolean insert , boolean update , boolean delete ){
    
        btnAdd.setDisable(insert);
        btnUpdate.setDisable(update );
        btnDelete.setDisable(delete);
        
    
    }
       private void loadTable() {
        
//         tblPrivilege.setRowFactory(new Callback<TableView<Privilege>, TableRow<Privilege>>() {
//            
//            @Override
//            public TableRow<Privilege> call(TableView<Privilege> dateTableView) {
//                
//                return new TableRow<Privilege>() {
//                    
//                    @Override
//                    protected void updateItem(Privilege privilegeForForm, boolean b) {
//                        super.updateItem( privilegeForForm , b );
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
        cmbSearchModule.setItems(ModuleDao.getAll());
        cmbSearchModule.getSelectionModel().clearSelection();
       
        colRole.setCellValueFactory(new PropertyValueFactory("roleId"));
        colModule.setCellValueFactory(new PropertyValueFactory("moduleId"));
        
//        colSelect.setCellValueFactory(new PropertyValueFactory("sel"));
//        colInsert.setCellValueFactory(new PropertyValueFactory("ins"));
//        colUpdate.setCellValueFactory(new PropertyValueFactory("upd"));
//        colDelete.setCellValueFactory(new PropertyValueFactory("del"));
        
        colSelect.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Privilege, ImageView>, ObservableValue<ImageView>>() {

              @Override
              public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Privilege, ImageView> param) {

                  if (param.getValue().getSel() == 1) {

                      ImageView img = new ImageView("/image/confirm.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  } else if (param.getValue().getSel() == 0) {

                      ImageView img = new ImageView("/image/wrong2.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  }

                  return null;

              }

          });
          
          colUpdate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Privilege, ImageView>, ObservableValue<ImageView>>() {

              @Override
              public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Privilege, ImageView> param) {

                  if (param.getValue().getUpd() == 1) {

                      ImageView img = new ImageView("/image/confirm.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  } else if (param.getValue().getUpd() == 0) {

                      ImageView img = new ImageView("/image/wrong2.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  }

                  return null;

              }

          });
          
          colDelete.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Privilege, ImageView>, ObservableValue<ImageView>>() {

              @Override
              public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Privilege, ImageView> param) {

                  if (param.getValue().getDel() == 1) {

                      ImageView img = new ImageView("/image/confirm.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  } else if (param.getValue().getDel() == 0) {

                      ImageView img = new ImageView("/image/wrong2.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  }

                  return null;

              }

          });
          
          colInsert.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Privilege, ImageView>, ObservableValue<ImageView>>() {

              @Override
              public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Privilege, ImageView> param) {

                  if (param.getValue().getIns() == 1) {

                      ImageView img = new ImageView("/image/confirm.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  } else if (param.getValue().getIns() == 0) {

                      ImageView img = new ImageView("/image/wrong2.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  }

                  return null;

              }

          });

        fillTable(PrivilegeDao.getAllPrivilege());//chang to getAll to getAppprivilege
        
//        RoleDao.getAll();
//        ModulaDao.getAll();
//        PrivilegeDao.getAll();
        
    }
    private void fillTable(ObservableList<Privilege> privilegeForForm){
     
        if ( privilegeForForm != null && !privilegeForForm.isEmpty()) {

            int rowsCount = 6;
            int pageCount = ((privilegeForForm.size()-1)/rowsCount)+1; 
            pagination.setPageCount(pageCount);

            pagination.setPageFactory((Integer pageIndex) -> {
                int start = pageIndex * rowsCount;
                int end = pageIndex == pageCount - 1 ? privilegeForForm.size() : pageIndex * rowsCount + rowsCount;
                tblPrivilege.getItems().clear();
                tblPrivilege.setItems(FXCollections.observableArrayList(privilegeForForm.subList(start, end)));
                return tblPrivilege;
            });

        } else {

            pagination.setPageCount(1);
            tblPrivilege.getItems().clear();
            
        }
        
        pagination.setCurrentPageIndex(page);
        tblPrivilege.getSelectionModel().select(row);
         
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Binding Methods">

    @FXML
    private void cmbModuleAP(ActionEvent event) {
        
         if (cmbModule.getSelectionModel().getSelectedItem() != null) {
            
            privilegeForForm.setModuleId(cmbModule.getSelectionModel().getSelectedItem());
            
//            System.out.println(privilegeForForm.getModuleId());
//            System.out.println(oldPrivilegeForForm.getModuleId());
            
            if (oldPrivilegeForForm != null && !privilegeForForm.getModuleId().getId().equals(oldPrivilegeForForm.getModuleId().getId())) {
                
                cmbModule.setStyle(updated);
                
            } else {
                
                cmbModule.setStyle(valid);
                
            }
            
        }
        
    }

    @FXML
    private void cmbRoleAP(ActionEvent event) {
        
                if (cmbRole.getSelectionModel().getSelectedItem() != null) {
            
            privilegeForForm.setRoleId(cmbRole.getSelectionModel().getSelectedItem());
            
            if (oldPrivilegeForForm == null) {
                
                cmbModule.setItems(ModuleDao.getAllUnassignedToRole(cmbRole.getSelectionModel().getSelectedItem()));
                
            }
            
            if (oldPrivilegeForForm != null && !privilegeForForm.getRoleId().getId().equals(oldPrivilegeForForm.getRoleId().getId())) {
                
                cmbRole.setStyle(updated);
                
            } else {
                
                cmbRole.setStyle(valid);
                
            }
            
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Operation Methods">
    @FXML
    private void btnUpdateAP(ActionEvent event) {
         String errors = getErrors();
        
        if ( errors.isEmpty() ) {

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
                    
                    //Notification.Notifier.INSTANCE.notifySuccess("Update", privilegeForForm.getModuleId().getName() + " is updated!");

                    PrivilegeDao.update(privilegeForForm);

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
    private void btnClearAP(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle(" Privilege Management");
            alert.setHeaderText("Clear Form");
            alert.setContentText("Are you sure you need to clear form ???");
            
            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForConfirmation");

            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK) {
                
                //Notification.Notifier.INSTANCE.notifySuccess("Clear Form", "The Form is cleared!" );
                
                loadForm();     
                
            }
    }

    @FXML
    private void btnAddAP(ActionEvent event) {
        privilegeForForm.setSel(cbxSelect.isSelected()?1:0); // validation and binding 
        privilegeForForm.setIns(cbxInsert.isSelected()?1:0);
        privilegeForForm.setUpd(cbxUpdate.isSelected()?1:0);
        privilegeForForm.setDel(cbxDelete.isSelected()?1:0);
        
        String errors = getErrors();       
        
        if ( errors.isEmpty() ) {
            
             String details = "Are you sure you need to add this Privileges with following details\n "
                    + "\nRole           \t:" + privilegeForForm.getRoleId().getName()
                    + "\nModule         \t:" + privilegeForForm.getModuleId().getName()
                    + "\nSelect         \t:" + ( privilegeForForm.getSel() == 0 ? "Not Selected" : "Selected") 
                    + "\nInsert         \t:" + ( privilegeForForm.getIns() == 0 ? "Not Selected" : "Selected")
                    + "\nUpdate         \t:" + ( privilegeForForm.getUpd() == 0 ? "Not Selected" : "Selected")
                    + "\nDelete         \t:" + ( privilegeForForm.getDel() == 0 ? "Not Selected" : "Selected");
                   
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add Privilege");
            alert.setHeaderText("Are you sure you need to add the following Privilege");
            alert.setContentText(details);
            
            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForConfirmation");
            
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK) {
                
               // Notification.Notifier.INSTANCE.notifySuccess("Save", privilegeForForm.getModuleId().getName() + " is saved!");
                
                PrivilegeDao.add(privilegeForForm);

                loadTable();
                loadForm();
            
            }   
            
        }else{
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You need to fill the following Privilege");
            alert.setContentText(errors);
            
            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForError");
            
            alert.showAndWait();
        
        }
    }

    @FXML
    private void btnDeleteAP(ActionEvent event) {
        String details =
                  "\nRole                   \t: " + oldPrivilegeForForm.getRoleId().getName()
                + "\nModule Type    \t: " + oldPrivilegeForForm.getModuleId().getName()
                + "\nSelect                 \t: " + (oldPrivilegeForForm.getSel() == 0 ? "Not Selected" : "Selected")
                + "\nInsert                 \t: " + (oldPrivilegeForForm.getIns() == 0 ? "Not Selected" : "Selected")
                + "\nUpdate             \t: " + (oldPrivilegeForForm.getUpd() == 0 ? "Not Selected" : "Selected")
                + "\nDelete                 \t: " + (oldPrivilegeForForm.getDel() == 0 ? "Not Selected" : "Selected");

            
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Privilege");
        alert.setHeaderText("Are you sure you need to delete the following Privilege?");
        alert.setContentText(details);
        
//        DialogPane dialogPane = alert.getDialogPane();
//
//        dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
//        dialogPane.getStyleClass().add("myDialogForConfirmation");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            
           // Notification.Notifier.INSTANCE.notifySuccess("Delete",  oldPrivilegeForForm.getModuleId().getName() + " is deleted!" );

            PrivilegeDao.delete(oldPrivilegeForForm);

//            loadTable();
            fillTable(PrivilegeDao.getAllPrivilege()); 
            loadForm();
            

            pagination.setCurrentPageIndex(page);
            tblPrivilege.getSelectionModel().select(row);
            
        }      
    }

    private String getErrors(){
    
        String errors = "";
        
         if (privilegeForForm.getRoleId() == null) {
            errors = errors + "Role \t\tis Not Selected\n";
        }
        if (privilegeForForm.getModuleId() == null) {
            errors = errors + "Module \t\tis Not Selected\n";
        }
//        if (!(cbxSelect.isSelected()|| cbxInsert.isSelected()|| cbxUpdate.isSelected()|| cbxDelete.isSelected())) {
//            errors = errors + "Privilege \t\tis Not Selected\n";
//        }
        
        return errors;
    
    }
     private String getUpdates(){
        
        String updates = "";
        
         if ( oldPrivilegeForForm != null ) {
             
             privilegeForForm.setSel(cbxSelect.isSelected() ? 1 : 0);             
             privilegeForForm.setIns(cbxInsert.isSelected() ? 1 : 0);
             privilegeForForm.setUpd(cbxUpdate.isSelected() ? 1 : 0);
             privilegeForForm.setDel(cbxDelete.isSelected() ? 1 : 0);
             
             if (!privilegeForForm.getSel().equals(oldPrivilegeForForm.getSel())) {
                 updates = updates + "Select Changed " + ( oldPrivilegeForForm.getSel() == 1 ? "Selecteed" : "Not Selected" ) + " to " + ( privilegeForForm.getSel()  == 1 ? "Selecteed" : "Not Selected") + "\n";
             }
             
             if (!privilegeForForm.getIns().equals(oldPrivilegeForForm.getIns())) {
                 updates = updates + "Insert Changed " + ( oldPrivilegeForForm.getIns()  == 1 ? "Selecteed" : "Not Selected" ) + " to " + ( privilegeForForm.getIns()  == 1 ? "Selecteed" : "Not Selected") + "\n";
             }
             
             if (!privilegeForForm.getUpd().equals(oldPrivilegeForForm.getUpd())) {
                 updates = updates + "Update Changed " + ( oldPrivilegeForForm.getUpd()  == 1 ? "Selecteed" : "Not Selected") + " to " + ( privilegeForForm.getUpd()  == 1 ? "Selecteed" : "Not Selected") + "\n";
             }
             if (!privilegeForForm.getDel().equals(oldPrivilegeForForm.getDel())) {
                 updates = updates + "Delete Changed " + ( oldPrivilegeForForm.getDel()  == 1 ? "Selecteed" : "Not Selected") + " to " + ( privilegeForForm.getDel()  == 1 ? "Selecteed" : "Not Selected") + "\n";
             }
             
         }

        return updates;
     
     }
      @FXML
    private void tblPrivilegeMC(MouseEvent event) {
        fillForm();
    }

    @FXML
    private void tblPrivilegeKR(KeyEvent event) {
        fillForm();
    }
     private void fillForm() {
          
         if ( tblPrivilege.getSelectionModel().getSelectedItem() != null ) {
             
//             cmbModule.setEditable(false);
             
             dissableButtons(true, true, false, false);
             
             setStyle(valid);
             
             cmbSearchModule.setStyle(initial);
             cmbSearchRole.setStyle(initial);
             
             oldPrivilegeForForm = PrivilegeDao.getById(tblPrivilege.getSelectionModel().getSelectedItem().getId());
             privilegeForForm = PrivilegeDao.getById(tblPrivilege.getSelectionModel().getSelectedItem().getId());

//         System.out.println(oldPrivilegeForForm.getModuleId());
//         System.out.println(privilegeForForm.getModuleId());

             cmbRole.getItems().clear();
             cmbRole.setItems(FXCollections.observableArrayList((Role) oldPrivilegeForForm.getRoleId()));             
             cmbRole.getSelectionModel().select(0);
             
             cmbModule.getItems().clear();
             cmbModule.setItems(FXCollections.observableArrayList((Module) oldPrivilegeForForm.getModuleId()));             
             cmbModule.getSelectionModel().select(0);
             
             cbxSelect.setSelected(oldPrivilegeForForm.getSel() == 1);
             cbxInsert.setSelected(oldPrivilegeForForm.getIns() == 1);
             cbxUpdate.setSelected(oldPrivilegeForForm.getUpd() == 1);
             cbxDelete.setSelected(oldPrivilegeForForm.getDel() == 1);

       //     PrivilegeDao.getById(); 
       
            page = pagination.getCurrentPageIndex();                   
            row = tblPrivilege.getSelectionModel().getSelectedIndex();
            
           // Notification.Notifier.INSTANCE.notifyInfo("Selected", oldPrivilegeForForm.getModuleId().getName() + " is selected!");
             
        }
         
    }
    @FXML
    private void lblAddNewRoleMC(MouseEvent event) {
    }
//</editor-fold>
    
 
   
}
