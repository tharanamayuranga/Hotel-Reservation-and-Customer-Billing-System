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
    


    

}
