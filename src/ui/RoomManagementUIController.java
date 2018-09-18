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



    

}
