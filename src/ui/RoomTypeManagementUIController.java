/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.RoomTypeDao;
import entity.Room;
import entity.Roomtype;
import java.math.BigDecimal;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
public class RoomTypeManagementUIController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="FXML-Data">
    @FXML
    private JFXTextField txtRoomType;
    @FXML
    private JFXTextField txtArea;
    @FXML
    private JFXTextField txtRoomTypePrice;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<Roomtype> tblRoomType;
    @FXML
    private JFXButton btnSearchClear;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnClear;
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private JFXTextField txtSearchRoomType;
    @FXML
    private TableColumn<Roomtype, String> colRoomType;
    @FXML
    private TableColumn<Roomtype, ImageView> colFun1;
    @FXML
    private TableColumn<Roomtype, ImageView> colFun2;
    @FXML
    private TableColumn<Roomtype, ImageView> colFun3;
    @FXML
    private TableColumn<Roomtype, ImageView> colFun4;
    @FXML
    private TableColumn<Roomtype, ImageView> colFun5;
    @FXML
    private TableColumn<Roomtype, ImageView> colFun6;
    @FXML
    private TableColumn<Roomtype, ImageView> colFun7;
    @FXML
    private TableColumn<Roomtype, ImageView> colFun8;
    @FXML
    private TableColumn<Roomtype, ImageView> colFun9;
    @FXML
    private TableColumn<Roomtype, ImageView> colFun10;
    @FXML
    private JFXCheckBox cbxFun1;
    @FXML
    private JFXCheckBox cbxFun6;
    @FXML
    private JFXCheckBox cbxFun4;
    @FXML
    private JFXCheckBox cbxFun3;
    @FXML
    private JFXCheckBox cbxFun2;
    @FXML
    private JFXCheckBox cbxFun8;
    @FXML
    private JFXCheckBox cbxFun7;
    @FXML
    private JFXCheckBox cbxFun9;
    @FXML
    private JFXCheckBox cbxFun5;
    @FXML
    private JFXCheckBox cbxFun10;
    @FXML
    private TextArea txtDescription;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Form-Data">
    Roomtype roomtype;
    Roomtype oldRoomtype;
    
    String initial;
    String valid;
    String invalid;
    String updated;
    
    int page;
    int row;
    
    BigDecimal roomTypeCost;
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
        
        roomtype = new Roomtype();
        oldRoomtype = null;
        
//        cmbModule.setEditable(true);
        
//        cmbRole.setItems(RoleDao.getAllUnassignedByRole());
        txtRoomType.setText("");
        txtArea.setText("");
        txtRoomTypePrice.setText("");
        txtDescription.setText("");
        
//        cbxSelect.setSelected(true);
//        cbxSelect.setDisable(true); 
        cbxFun1.setSelected(false);
        cbxFun2.setSelected(false);
        cbxFun3.setSelected(false);
        cbxFun4.setSelected(false); 
        cbxFun5.setSelected(false);
        cbxFun6.setSelected(false);
        cbxFun7.setSelected(false);
        cbxFun8.setSelected(false);
        cbxFun9.setSelected(false);
        cbxFun10.setSelected(false);
        
        
        dissableButtons(false, false, true, true);
        
        setStyle(initial);
        
//        RoleDao.getAll();
//        ModulaDao.getAllUnassignedByRole();
        
    }
     private void setStyle(String style){
    
        
        txtRoomType.setStyle(style);
        txtArea.setStyle(style);
        txtRoomTypePrice.setStyle(style);
        

        if (!txtDescription.getChildrenUnmodifiable().isEmpty()) {

            ((ScrollPane) txtDescription.getChildrenUnmodifiable().get(0)).getContent().setStyle(style);

        }

        txtSearchRoomType.setStyle(style);
        
    
    }
    
   private void dissableButtons( boolean select , boolean insert , boolean update , boolean delete ){
    
        btnAdd.setDisable(insert);
        btnUpdate.setDisable(update );
        btnDelete.setDisable(delete);
        
    
    }
    private void loadTable() {
        

       
        txtSearchRoomType.setText("");
        
        
       
        colRoomType.setCellValueFactory(new PropertyValueFactory("name"));
        
        
//        colSelect.setCellValueFactory(new PropertyValueFactory("sel"));
//        colInsert.setCellValueFactory(new PropertyValueFactory("ins"));
//        colUpdate.setCellValueFactory(new PropertyValueFactory("upd"));
//        colDelete.setCellValueFactory(new PropertyValueFactory("del"));
        
        colFun1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Roomtype, ImageView>, ObservableValue<ImageView>>() {

              @Override
              public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Roomtype, ImageView> param) {

                  if (param.getValue().getFun1() == 1) {

                      ImageView img = new ImageView("/image/confirm.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  } else if (param.getValue().getFun1() == 0) {

                      ImageView img = new ImageView("/image/wrong2.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  }

                  return null;

              }

          });
          
          colFun2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Roomtype, ImageView>, ObservableValue<ImageView>>() {

              @Override
              public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Roomtype, ImageView> param) {

                  if (param.getValue().getFun2() == 1) {

                      ImageView img = new ImageView("/image/confirm.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  } else if (param.getValue().getFun2() == 0) {

                      ImageView img = new ImageView("/image/wrong2.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  }

                  return null;

              }

          });
          
          colFun3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Roomtype, ImageView>, ObservableValue<ImageView>>() {

              @Override
              public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Roomtype, ImageView> param) {

                  if (param.getValue().getFun3() == 1) {

                      ImageView img = new ImageView("/image/confirm.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  } else if (param.getValue().getFun3() == 0) {

                      ImageView img = new ImageView("/image/wrong2.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  }

                  return null;

              }

          });
          
          colFun4.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Roomtype, ImageView>, ObservableValue<ImageView>>() {

              @Override
              public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Roomtype, ImageView> param) {

                  if (param.getValue().getFun4() == 1) {

                      ImageView img = new ImageView("/image/confirm.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  } else if (param.getValue().getFun4() == 0) {

                      ImageView img = new ImageView("/image/wrong2.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  }

                  return null;

              }

          });
           colFun5.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Roomtype, ImageView>, ObservableValue<ImageView>>() {

              @Override
              public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Roomtype, ImageView> param) {

                  if (param.getValue().getFun5() == 1) {

                      ImageView img = new ImageView("/image/confirm.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  } else if (param.getValue().getFun5() == 0) {

                      ImageView img = new ImageView("/image/wrong2.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  }

                  return null;

              }

          });
            colFun6.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Roomtype, ImageView>, ObservableValue<ImageView>>() {

              @Override
              public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Roomtype, ImageView> param) {

                  if (param.getValue().getFun6() == 1) {

                      ImageView img = new ImageView("/image/confirm.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  } else if (param.getValue().getFun6() == 0) {

                      ImageView img = new ImageView("/image/wrong2.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  }

                  return null;

              }

          });
             colFun7.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Roomtype, ImageView>, ObservableValue<ImageView>>() {

              @Override
              public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Roomtype, ImageView> param) {

                  if (param.getValue().getFun7() == 1) {

                      ImageView img = new ImageView("/image/confirm.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  } else if (param.getValue().getFun7() == 0) {

                      ImageView img = new ImageView("/image/wrong2.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  }

                  return null;

              }

          });
              colFun8.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Roomtype, ImageView>, ObservableValue<ImageView>>() {

              @Override
              public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Roomtype, ImageView> param) {

                  if (param.getValue().getFun8() == 1) {

                      ImageView img = new ImageView("/image/confirm.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  } else if (param.getValue().getFun8() == 0) {

                      ImageView img = new ImageView("/image/wrong2.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  }

                  return null;

              }

          });
               colFun9.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Roomtype, ImageView>, ObservableValue<ImageView>>() {

              @Override
              public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Roomtype, ImageView> param) {

                  if (param.getValue().getFun9() == 1) {

                      ImageView img = new ImageView("/image/confirm.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  } else if (param.getValue().getFun9() == 0) {

                      ImageView img = new ImageView("/image/wrong2.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  }

                  return null;

              }

          });
                colFun10.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Roomtype, ImageView>, ObservableValue<ImageView>>() {

              @Override
              public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Roomtype, ImageView> param) {

                  if (param.getValue().getFun10() == 1) {

                      ImageView img = new ImageView("/image/confirm.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  } else if (param.getValue().getFun10() == 0) {

                      ImageView img = new ImageView("/image/wrong2.jpg");

                      img.setFitHeight(20);
                      img.setFitWidth(20);

                      return new SimpleObjectProperty(img);

                  }

                  return null;

              }

          });

        fillTable(RoomTypeDao.getAll());//chang to getAll to getAppprivilege
        
//        RoleDao.getAll();
//        ModulaDao.getAll();
//        PrivilegeDao.getAll();
        
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Binding-Methods">
    @FXML
    private void txtRoomTypeKR(KeyEvent event) {
        if (roomtype.setName(txtRoomType.getText().trim())) {

            if (oldRoomtype != null && !roomtype.getName().equals(oldRoomtype.getName())) {

                txtRoomType.setStyle(updated);

            } else {

                txtRoomType.setStyle(valid);

            }

        } else {

            txtRoomType.setStyle(invalid);

        }
    }
    
    @FXML
    private void txtAreaKR(KeyEvent event) {
        
         if (txtArea.getText().matches("[0-9]{1}")) {

            if (roomtype.setArea(new BigDecimal(txtArea.getText()))) {

                txtArea.setStyle(valid);

            } else {

                txtArea.setStyle(invalid);
            }

        } else {
            txtArea.setStyle(invalid);

        }
    }
     @FXML
    private void txtDescriptionKR(KeyEvent event) {
         if (roomtype.setDescription(txtDescription.getText().trim())) {

            if (oldRoomtype != null && !roomtype.getDescription().equals(oldRoomtype.getDescription())) {

                ((ScrollPane) txtDescription.getChildrenUnmodifiable().get(0)).getContent().setStyle(updated);

            } else {

                ((ScrollPane) txtDescription.getChildrenUnmodifiable().get(0)).getContent().setStyle(valid);

            }

        } else {

            ((ScrollPane) txtDescription.getChildrenUnmodifiable().get(0)).getContent().setStyle(invalid);

        }
    }
    
    @FXML
    private void txtRoomTypePriceKR(KeyEvent event) {
      if (txtRoomTypePrice.getText().trim().matches("[0-9]{1}[0-9]*.[0-9]{1}[0-9]") || txtRoomTypePrice.getText().trim().matches("[0-9]{1}[0-9]*") || txtRoomTypePrice.getText().trim().matches("[0-9]{1}[0-9]*.[0-9]{1}") || txtRoomTypePrice.getText().trim().matches("[0-9]{1}[0-9]*.")) {

            roomTypeCost = new BigDecimal(txtRoomTypePrice.getText().trim());//check it

            roomtype.setUnitprice(toGetTextColor(txtRoomTypePrice.getText().trim()));

            if (oldRoomtype != null && !roomtype.getUnitprice().equals(oldRoomtype.getUnitprice())) {

                txtRoomTypePrice.setStyle(updated);

            } else {

                txtRoomTypePrice.setStyle(valid);

            }

        } else {

            txtRoomTypePrice.setStyle(invalid);

            roomtype.setUnitprice(null);

        }
    }

    private BigDecimal toGetTextColor(String string) {

        if (!string.contains(".") && !"".equals(txtRoomTypePrice.getText().trim())) {

            return new BigDecimal(string + ".00");

        } else if (string.contains(".") && !"".equals(txtRoomTypePrice.getText().trim())) {

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
 
    
//</editor-fold>
  //<editor-fold defaultstate="collapsed" desc="Operation-Methods">
    
    @FXML
    private void btnAddAP(ActionEvent event) {
        
        roomtype.setFun1(cbxFun1.isSelected()?1:0); // validation and binding 
        roomtype.setFun2(cbxFun2.isSelected()?1:0);
        roomtype.setFun3(cbxFun3.isSelected()?1:0);
        roomtype.setFun4(cbxFun4.isSelected()?1:0);
        roomtype.setFun5(cbxFun5.isSelected()?1:0);
        roomtype.setFun6(cbxFun6.isSelected()?1:0);
        roomtype.setFun7(cbxFun7.isSelected()?1:0);
        roomtype.setFun8(cbxFun8.isSelected()?1:0);
        roomtype.setFun9(cbxFun9.isSelected()?1:0);
        roomtype.setFun10(cbxFun10.isSelected()?1:0);
        
        String errors = getErrors();       
        
        if ( errors.isEmpty() ) {
            
             String details = "Are you sure you need to add this Privileges with following details\n "
                    + "\nRoom Type           \t:" + roomtype.getName()
                     + "\nArea           \t:" + roomtype.getArea()
                     + "\nRoom Type Price           \t:" + roomtype.getUnitprice()
                     + "\nRoom Description           \t:" + roomtype.getDescription()
                    + "\nSelect         \t:" + ( roomtype.getFun1() == 0 ? "Not Selected" : "Selected") 
                    + "\nInsert         \t:" + ( roomtype.getFun2() == 0 ? "Not Selected" : "Selected")
                    + "\nUpdate         \t:" + ( roomtype.getFun3() == 0 ? "Not Selected" : "Selected")
                    + "\nDelete         \t:" + ( roomtype.getFun4() == 0 ? "Not Selected" : "Selected")
                    + "\nSelect         \t:" + ( roomtype.getFun5() == 0 ? "Not Selected" : "Selected") 
                    + "\nInsert         \t:" + ( roomtype.getFun6() == 0 ? "Not Selected" : "Selected")
                    + "\nUpdate         \t:" + ( roomtype.getFun7() == 0 ? "Not Selected" : "Selected")
                    + "\nDelete         \t:" + ( roomtype.getFun8() == 0 ? "Not Selected" : "Selected")
                    + "\nUpdate         \t:" + ( roomtype.getFun9() == 0 ? "Not Selected" : "Selected")
                    + "\nDelete         \t:" + ( roomtype.getFun10() == 0 ? "Not Selected" : "Selected");
                   
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add Privilege");
            alert.setHeaderText("Are you sure you need to add the following Room Type");
            alert.setContentText(details);
            
            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForConfirmation");
            
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK) {
                
               // Notification.Notifier.INSTANCE.notifySuccess("Save", privilegeForForm.getModuleId().getName() + " is saved!");
                
                RoomTypeDao.add(roomtype);

                loadTable();
                loadForm();
            
            }   
            
        }else{
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You need to fill the following Privilege");
            alert.setContentText(errors);
            
            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForError");
            
            alert.showAndWait();
        
        }
    }
    
    @FXML
    private void btnDeleteAP(ActionEvent event) {
        String details =
                       "\nRoom Type           \t:" + roomtype.getName()
                     + "\nArea           \t:" + roomtype.getArea()
                     + "\nRoom Type Price           \t:" + roomtype.getUnitprice()
                     + "\nRoom Description           \t:" + roomtype.getDescription()
                    + "\nSelect         \t:" + ( roomtype.getFun1() == 0 ? "Not Selected" : "Selected") 
                    + "\nInsert         \t:" + ( roomtype.getFun2() == 0 ? "Not Selected" : "Selected")
                    + "\nUpdate         \t:" + ( roomtype.getFun3() == 0 ? "Not Selected" : "Selected")
                    + "\nDelete         \t:" + ( roomtype.getFun4() == 0 ? "Not Selected" : "Selected")
                    + "\nSelect         \t:" + ( roomtype.getFun5() == 0 ? "Not Selected" : "Selected") 
                    + "\nInsert         \t:" + ( roomtype.getFun6() == 0 ? "Not Selected" : "Selected")
                    + "\nUpdate         \t:" + ( roomtype.getFun7() == 0 ? "Not Selected" : "Selected")
                    + "\nDelete         \t:" + ( roomtype.getFun8() == 0 ? "Not Selected" : "Selected")
                    + "\nUpdate         \t:" + ( roomtype.getFun9() == 0 ? "Not Selected" : "Selected")
                    + "\nDelete         \t:" + ( roomtype.getFun10() == 0 ? "Not Selected" : "Selected");

            
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

            RoomTypeDao.delete(roomtype);

//            loadTable();
            fillTable(RoomTypeDao.getAll()); 
            loadForm();
            

            pagination.setCurrentPageIndex(page);
            tblRoomType.getSelectionModel().select(row);
            
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

                dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialogForConfirmation");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    
                    //Notification.Notifier.INSTANCE.notifySuccess("Update", privilegeForForm.getModuleId().getName() + " is updated!");

                    RoomTypeDao.update(roomtype);

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
    private void tblRoomTypeMC(MouseEvent event) {
        fillForm();
    }
    
    @FXML
    private void tblRoomTypeKR(KeyEvent event) {
        fillForm();
    }
     private String getErrors(){
    
        String errors = "";
        
        if (roomtype.getName() == null) {
            errors = errors + "Name \t\tis Invalid\n";
        }
        if (roomtype.getArea() == null) {
            errors = errors + "Area \t\tis Invalid\n";
        }
        if (roomtype.getDescription() == null) {
            errors = errors + "Description \t\tis Invalid\n";
        }

        if (roomtype.getUnitprice() == null) {
            errors = errors + "Unit Price  \t\tis Invalid \n";
        }
//        if (!(cbxSelect.isSelected()|| cbxInsert.isSelected()|| cbxUpdate.isSelected()|| cbxDelete.isSelected())) {
//            errors = errors + "Privilege \t\tis Not Selected\n";
//        }
        
        return errors;
    
    }
      private String getUpdates(){
        
        String updates = "";
        
         if ( roomtype != null ) {
             
        roomtype.setFun1(cbxFun1.isSelected()?1:0); // validation and binding 
        roomtype.setFun2(cbxFun2.isSelected()?1:0);
        roomtype.setFun3(cbxFun3.isSelected()?1:0);
        roomtype.setFun4(cbxFun4.isSelected()?1:0);
        roomtype.setFun5(cbxFun5.isSelected()?1:0);
        roomtype.setFun6(cbxFun6.isSelected()?1:0);
        roomtype.setFun7(cbxFun7.isSelected()?1:0);
        roomtype.setFun8(cbxFun8.isSelected()?1:0);
        roomtype.setFun9(cbxFun9.isSelected()?1:0);
        roomtype.setFun10(cbxFun10.isSelected()?1:0);
             
            if (roomtype.getName() != null && !roomtype.getName().equals(oldRoomtype.getName())) {
                updates = updates + oldRoomtype.getName() + " chnaged to " + roomtype.getName() + "\n";
            }

            if (roomtype.getArea() != null && !roomtype.getArea().equals(oldRoomtype.getArea())) {
                updates = updates + oldRoomtype.getName() + " chnaged to " + roomtype.getArea() + "\n";
            }

            if (roomtype.getUnitprice() != null && !roomtype.getUnitprice().equals(oldRoomtype.getUnitprice())) {
                updates = updates + oldRoomtype.getUnitprice() + " chnaged to " + roomtype.getUnitprice() + "\n";
            }

            if (roomtype.getDescription() != null && !roomtype.getDescription().equals(oldRoomtype.getDescription())) {
                updates = updates + oldRoomtype.getDescription() + " chnaged to " + roomtype.getDescription() + "\n";
            }
             if (!roomtype.getFun1().equals(oldRoomtype.getFun1())) {
                 updates = updates + "Select Changed " + ( oldRoomtype.getFun1() == 1 ? "Selecteed" : "Not Selected" ) + " to " + ( roomtype.getFun1()  == 1 ? "Selecteed" : "Not Selected") + "\n";
             }
             
             if (!roomtype.getFun2().equals(oldRoomtype.getFun2())) {
                 updates = updates + "Insert Changed " + ( oldRoomtype.getFun2()  == 1 ? "Selecteed" : "Not Selected" ) + " to " + ( roomtype.getFun2()  == 1 ? "Selecteed" : "Not Selected") + "\n";
             }
             
             if (!roomtype.getFun3().equals(oldRoomtype.getFun3())) {
                 updates = updates + "Update Changed " + ( oldRoomtype.getFun3()  == 1 ? "Selecteed" : "Not Selected") + " to " + ( roomtype.getFun3()  == 1 ? "Selecteed" : "Not Selected") + "\n";
             }
             if (!roomtype.getFun4().equals(oldRoomtype.getFun4())) {
                 updates = updates + "Delete Changed " + ( oldRoomtype.getFun4()  == 1 ? "Selecteed" : "Not Selected") + " to " + ( roomtype.getFun4()  == 1 ? "Selecteed" : "Not Selected") + "\n";
             }
             if (!roomtype.getFun5().equals(oldRoomtype.getFun5())) {
                 updates = updates + "Select Changed " + ( oldRoomtype.getFun5() == 1 ? "Selecteed" : "Not Selected" ) + " to " + ( roomtype.getFun5()  == 1 ? "Selecteed" : "Not Selected") + "\n";
             }
             
             if (!roomtype.getFun6().equals(oldRoomtype.getFun6())) {
                 updates = updates + "Insert Changed " + ( oldRoomtype.getFun6()  == 1 ? "Selecteed" : "Not Selected" ) + " to " + ( roomtype.getFun6()  == 1 ? "Selecteed" : "Not Selected") + "\n";
             }
             
             if (!roomtype.getFun7().equals(oldRoomtype.getFun7())) {
                 updates = updates + "Update Changed " + ( oldRoomtype.getFun7()  == 1 ? "Selecteed" : "Not Selected") + " to " + ( roomtype.getFun7()  == 1 ? "Selecteed" : "Not Selected") + "\n";
             }
             if (!roomtype.getFun8().equals(oldRoomtype.getFun8())) {
                 updates = updates + "Delete Changed " + ( oldRoomtype.getFun8()  == 1 ? "Selecteed" : "Not Selected") + " to " + ( roomtype.getFun8()  == 1 ? "Selecteed" : "Not Selected") + "\n";
             }
             if (!roomtype.getFun9().equals(oldRoomtype.getFun9())) {
                 updates = updates + "Update Changed " + ( oldRoomtype.getFun9()  == 1 ? "Selecteed" : "Not Selected") + " to " + ( roomtype.getFun9()  == 1 ? "Selecteed" : "Not Selected") + "\n";
             }
             if (!roomtype.getFun10().equals(oldRoomtype.getFun10())) {
                 updates = updates + "Delete Changed " + ( oldRoomtype.getFun10()  == 1 ? "Selecteed" : "Not Selected") + " to " + ( roomtype.getFun10()  == 1 ? "Selecteed" : "Not Selected") + "\n";
             }
             
         }

        return updates;
     
     }
           private void fillForm() {
          
         if ( tblRoomType.getSelectionModel().getSelectedItem() != null ) {
             
//             cmbModule.setEditable(false);
             
             dissableButtons(true, true, false, false);
             
             setStyle(valid);
             
             txtSearchRoomType.setStyle(initial);
             
             
             oldRoomtype = RoomTypeDao.getById(tblRoomType.getSelectionModel().getSelectedItem().getId());
             roomtype = RoomTypeDao.getById(tblRoomType.getSelectionModel().getSelectedItem().getId());

//         System.out.println(oldPrivilegeForForm.getModuleId());
//         System.out.println(privilegeForForm.getModuleId());
            
            txtRoomType.setText(roomtype.getName());
            txtArea.setText(roomtype.getArea().toString());
            txtDescription.setText(roomtype.getDescription());
            txtRoomTypePrice.setText(roomtype.getUnitprice().toString());
             
             cbxFun1.setSelected(oldRoomtype.getFun1()== 1);
             cbxFun2.setSelected(oldRoomtype.getFun2()== 1);
             cbxFun3.setSelected(oldRoomtype.getFun3()== 1);
             cbxFun4.setSelected(oldRoomtype.getFun4() == 1);
             cbxFun5.setSelected(oldRoomtype.getFun5()== 1);
             cbxFun6.setSelected(oldRoomtype.getFun6()== 1);
             cbxFun7.setSelected(oldRoomtype.getFun7()== 1);
             cbxFun8.setSelected(oldRoomtype.getFun8() == 1);
             cbxFun9.setSelected(oldRoomtype.getFun9()== 1);
             cbxFun10.setSelected(oldRoomtype.getFun10()== 1);
             

       //     PrivilegeDao.getById(); 
       
            page = pagination.getCurrentPageIndex();                   
            row = tblRoomType.getSelectionModel().getSelectedIndex();
            
           // Notification.Notifier.INSTANCE.notifyInfo("Selected", oldPrivilegeForForm.getModuleId().getName() + " is selected!");
             
        }
         
    }
//</editor-fold>
    
  
  
   
    //<editor-fold defaultstate="collapsed" desc="Searching-methods">
    @FXML
    private void btnSearchClearAP(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(" Privilege Management");
        alert.setHeaderText("Clear Search Form");
        alert.setContentText("Are you sure you need to clear search form ????");
        
        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialogForConfirmation");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            
            //Notification.Notifier.INSTANCE.notifySuccess("Search Form", "The Search Fields are cleared!" );

            loadTable();   

        }
    }
    
    @FXML
    private void txtSearchRoomTypeKR(KeyEvent event) {
        updateTable();
    }
    private void updateTable() {

        String name = txtSearchRoomType.getText().trim();
        boolean sname = !name.isEmpty();

        if (sname) {
            fillTable(RoomTypeDao.getAllByName(name));
        }
        
        

    }
//</editor-fold>

   
}
