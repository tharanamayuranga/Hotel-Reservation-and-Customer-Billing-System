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


   
}
