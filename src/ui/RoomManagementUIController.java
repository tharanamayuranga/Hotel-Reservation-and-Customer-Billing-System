/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.FloorDao;
import dao.RoomDao;
import dao.RoomStatusDao;
import dao.RoomTypeDao;
import entity.Floor;
import entity.Room;
import entity.Roomstatus;
import entity.Roomtype;
import java.net.URL;
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
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class RoomManagementUIController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="FXML-Data">
    @FXML
    private JFXComboBox<Floor> cmbFloorNo;
    @FXML
    private JFXTextField txtRoomNo;
    @FXML
    private JFXComboBox<Roomtype> cmbRoomType;
    @FXML
    private JFXComboBox<Roomstatus> cmbRoomStatus;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<Room> tblRoom;
    @FXML
    private TableColumn<Room, String> colRoomNo;
    @FXML
    private TableColumn<Room, Floor> colFloorNo;
    @FXML
    private TableColumn<Room, Roomtype> colRoomType;
    @FXML
    private TableColumn<Room, Roomstatus> colRoomStatus;
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
    private JFXComboBox<Roomtype> cmbSearchRoomType;
    @FXML
    private JFXComboBox<Roomstatus> cmbSearchRoomStatus;
    @FXML
    private JFXTextField txtSearchRoomNo;

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Form-Data">
    Room room;
    Room oldRoom;

    //Stage designationStage;
    String initial;
    String valid;
    String invalid;
    String updated;

    int page;
    int row;

    //boolean photoSelected;
    //public static File lastDirectory;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Initializing Methods">
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

        room = new Room();
        oldRoom = null;

        cmbFloorNo.setItems(FloorDao.getAll());//get all cmb details
        cmbFloorNo.getSelectionModel().clearSelection();//clear the selected item

        cmbRoomStatus.setItems(RoomStatusDao.getAll());
        cmbRoomStatus.getSelectionModel().clearSelection();

        cmbRoomType.setItems(RoomTypeDao.getAll());
        cmbRoomType.getSelectionModel().clearSelection();

        txtRoomNo.setText("");

        dissableButtons(false, false, true, true);

        setStyle(initial);
    }

    private void setStyle(String style) {

        cmbFloorNo.setStyle(style);
        cmbRoomStatus.setStyle(style);
        cmbRoomType.setStyle(style);

        txtRoomNo.setStyle(style);

   
        cmbSearchRoomStatus.setStyle(style);
        txtSearchRoomNo.setStyle(style);

    }

    private void dissableButtons(boolean select, boolean insert, boolean update, boolean delete) {

        btnAdd.setDisable(insert);
        btnUpdate.setDisable(update);
        btnDelete.setDisable(delete);

    }

    private void loadTable() {

        cmbSearchRoomStatus.setItems(RoomStatusDao.getAll());
        cmbSearchRoomStatus.getSelectionModel().clearSelection();
        cmbSearchRoomType.setItems(RoomTypeDao.getAll());
        cmbSearchRoomType.getSelectionModel().clearSelection();

        txtSearchRoomNo.setText("");

        colRoomNo.setCellValueFactory(new PropertyValueFactory("no"));
        colFloorNo.setCellValueFactory(new PropertyValueFactory("floorId"));
        colRoomStatus.setCellValueFactory(new PropertyValueFactory("roomstatusId"));
        colRoomType.setCellValueFactory(new PropertyValueFactory("roomtypeId"));

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

        fillTable(RoomDao.getAll());

        pagination.setCurrentPageIndex(0);

    }

    private void fillTable(ObservableList<Room> employees) {

        if (employees != null && !employees.isEmpty()) {

            int rowsCount = 5;
            int pageCount = ((employees.size() - 1) / rowsCount) + 1;
            pagination.setPageCount(pageCount);

            pagination.setPageFactory((Integer pageIndex) -> {
                int start = pageIndex * rowsCount;
                int end = pageIndex == pageCount - 1 ? employees.size() : pageIndex * rowsCount + rowsCount;
                tblRoom.getItems().clear();
                tblRoom.setItems(FXCollections.observableArrayList(employees.subList(start, end)));
                return tblRoom;
            });

        } else {

            pagination.setPageCount(1);
            tblRoom.getItems().clear();

        }

        pagination.setCurrentPageIndex(page);
        tblRoom.getSelectionModel().select(row);

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Binding methods">
    @FXML
    private void cmbFloorNoAP(ActionEvent event) {
        room.setFloorId(cmbFloorNo.getSelectionModel().getSelectedItem());

        if (oldRoom != null && !room.getFloorId().equals(oldRoom.getFloorId())) {

            cmbFloorNo.setStyle(updated);

        } else {

            cmbFloorNo.setStyle(valid);

        }
    }

    @FXML
    private void txtRoomNoKR(KeyEvent event) {
        if (room.setNo(txtRoomNo.getText().trim())) {

            if (oldRoom != null && !room.getNo().equals(oldRoom.getNo())) {

                txtRoomNo.setStyle(updated);

            } else {

                txtRoomNo.setStyle(valid);

            }

        } else {

            txtRoomNo.setStyle(invalid);

        }
    }

    @FXML
    private void cmbRoomTypeAP(ActionEvent event) {
        room.setRoomtypeId(cmbRoomType.getSelectionModel().getSelectedItem());

        if (oldRoom != null && !room.getRoomtypeId().equals(oldRoom.getRoomtypeId())) {

            cmbRoomType.setStyle(updated);

        } else {

            cmbRoomType.setStyle(valid);

        }

    }

    @FXML
    private void cmbRoomStatusAP(ActionEvent event) {
        room.setRoomstatusId(cmbRoomStatus.getSelectionModel().getSelectedItem());

        if (oldRoom != null && !room.getRoomstatusId().equals(oldRoom.getRoomstatusId())) {

            cmbRoomStatus.setStyle(updated);

        } else {

            cmbRoomStatus.setStyle(valid);

        }
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Operation Methods">
    @FXML
    private void btnAddAP(ActionEvent event) {
        String errors = getErrors();

        if (errors.isEmpty()) {

            String details
                    = "\nRoom No          \t\t: " + room.getNo()
                    + "\nRoom Type  \t\t: " + room.getRoomtypeId()
                    + "\nFloor No  \t\t: " + room.getFloorId()
                    + "\nRoom Status        \t\t: " + room.getRoomstatusId().getName();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add Module");
            alert.setHeaderText("Are you sure you need to add the following Food Item???");
            alert.setContentText(details);

            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForConfirmation");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {

                // employee.setDisable(0);
                RoomDao.add(room);

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
    private void btnDeleteAP(ActionEvent event) {
         
                     String details
                    = "\nRoom No          \t\t: " + room.getNo()
                    + "\nRoom Type  \t\t: " + room.getRoomtypeId()
                    + "\nFloor No  \t\t: " + room.getFloorId()
                    + "\nRoom Status        \t\t: " + room.getRoomstatusId().getName();
                     
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
            RoomDao.delete(room);

//            fillTable(EmployeeDao.getAll());
            loadTable();

            loadForm();

            pagination.setCurrentPageIndex(page);
            tblRoom.getSelectionModel().select(row);

        }
    }

    @FXML
    private void btnClearAP(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialogForConfirmation");

        alert.setTitle(" Room Management Management");
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
                    RoomDao.update(room);

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
    private void tblRoomMC(MouseEvent event) {
        fillForm();
    }

    @FXML
    private void tblRoomKR(KeyEvent event) {
        fillForm();
    }

    private String getErrors() {

        String errors = "";

        if (room.getNo() == null) {
            errors = errors + "Room No \t\tis Invalid\n";
        }

        if (room.getFloorId() == null) {
            errors = errors + "Floor No \t\tis Not Selected\n";
        }
        if (room.getRoomtypeId() == null) {
            errors = errors + "Room Type \t\tis Not Selected\n";
        }
        if (room.getRoomstatusId() == null) {
            errors = errors + "Room Status \t\tis Not Selected\n";
        }

        return errors;

    }

    private String getUpdates() {

        String updates = "";

        if (oldRoom != null) {

            if (room.getNo() != null && !room.getNo().equals(oldRoom.getNo())) {
                updates = updates + oldRoom.getNo() + " chnaged to " + room.getNo() + "\n";
            }
            if (room.getFloorId() != null && !room.getFloorId().equals(oldRoom.getFloorId())) {
                updates = updates + oldRoom.getFloorId() + " chnaged to " + room.getFloorId() + "\n";
            }
            if (room.getRoomtypeId() != null && !room.getRoomtypeId().equals(oldRoom.getRoomtypeId())) {
                updates = updates + oldRoom.getRoomtypeId() + " chnaged to " + room.getRoomtypeId() + "\n";
            }
            if (room.getRoomstatusId() != null && !room.getRoomstatusId().equals(oldRoom.getRoomstatusId())) {
                updates = updates + oldRoom.getRoomstatusId() + " chnaged to " + room.getRoomstatusId() + "\n";
            }

        }

        return updates;

    }

    private void fillForm() {

        if (tblRoom.getSelectionModel().getSelectedItem() != null) {

            dissableButtons(false, true, false, false);
            setStyle(valid);

            oldRoom = RoomDao.getById(tblRoom.getSelectionModel().getSelectedItem().getId());
            room = RoomDao.getById(tblRoom.getSelectionModel().getSelectedItem().getId());

//            cmbSearchDesignation.setStyle(initial);
//            cmbSearchStatus.setStyle(initial);
//            txtSearchName.setStyle(initial);
            txtRoomNo.setText(oldRoom.getNo());
            cmbFloorNo.getSelectionModel().select((Floor) room.getFloorId());
            cmbRoomType.getSelectionModel().select((Roomtype) room.getRoomtypeId());
            cmbRoomStatus.getSelectionModel().select((Roomstatus) room.getRoomstatusId());

            

            page = pagination.getCurrentPageIndex();
            row = tblRoom.getSelectionModel().getSelectedIndex();

//            oldNicWithOldFormateWithV = oldEmployee.getNic().substring(2, 7) + oldEmployee.getNic().substring(8) + "v";
//            oldNicWithOldFormateWithX = oldEmployee.getNic().substring(2, 7) + oldEmployee.getNic().substring(8) + "x";
//            lblDob.setText(oldEmployee.getDob().toString());
//            lblGender.setText(oldEmployee.getGenderId().getName());
        }

    }
//</editor-fold>



    

}
