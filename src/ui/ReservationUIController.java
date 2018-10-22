/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dao.CustomerDao;
import dao.ExtBedDao;
import dao.PackageDao;
import dao.ReservationDao;
import dao.ReservationStatusDao;
import dao.RoomDao;
import dao.RoomTypeDao;
import entity.Customer;
import entity.Extrabed;
import entity.Reservation;
import entity.Reservationstatus;
import entity.Room;
import entity.Roomtype;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import static ui.LoginUIController.privilege;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class ReservationUIController implements Initializable {
    
    //<editor-fold defaultstate="collapsed" desc="Form-Data">
    Reservation reservation;
    Reservation oldReservation;

    String initial;
    String valid;
    String invalid;
    String updated;

    int page;
    int row;

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="FXML-Data">
    @FXML
    private JFXDatePicker dtpSearchArrival;
    @FXML
    private JFXDatePicker dtpSearchDeparture;
    @FXML
    private JFXTextField txtID;
    @FXML
    private Label lblCustomerID;
    @FXML
    private Button btnDelete;
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
    private TableView<Reservation> tblReservation;
    @FXML
    private TableColumn<Reservation, String> colGuestID;
    @FXML
    private TableColumn<Reservation, String> colReservationID;
    @FXML
    private TableColumn<Reservation, Room> colRoomNo;
    @FXML
    private TableColumn<Reservation, String> colArrival;
    @FXML
    private TableColumn<Reservation, String> colDeparture;
    @FXML
    private JFXTextField txtSearchName;
    @FXML
    private JFXComboBox<Package> cmbPackage;
    @FXML
    private JFXComboBox<Roomtype> cmbRoomType;
    @FXML
    private JFXDatePicker dtpArrival;
    @FXML
    private Label lblEmployeeID1;
    @FXML
    private JFXDatePicker dtpReservationDate;
    @FXML
    private JFXDatePicker dtpDeparture;
    @FXML
    private JFXComboBox<Extrabed> cmbExtBed;
    @FXML
    private JFXComboBox<Reservationstatus> cmbReservationStatus;
    @FXML
    private Spinner<?> spnAdullt;
    @FXML
    private Spinner<?> spnChild;
    @FXML
    private JFXButton btnNewCustomer;
    @FXML
    private JFXButton btnRoomAvailability;
    @FXML
    private Label lblSubTotal;
    @FXML
    private Label lblDisAmount;
    @FXML
    private JFXTextField txtVatPresentage;
    @FXML
    private Label lblVatAmount;
    @FXML
    private JFXTextField txtServiceChargePresentage;
    @FXML
    private Label lblServiceChargeAmount;
    @FXML
    private Label lblGrandTotal;
    @FXML
    private JFXTextField txtDisPresentage;
    @FXML
    private TextArea txtComments;
    @FXML
    private JFXComboBox<Room>cmbSearchRoomNo;
    @FXML
    private Label lblReservationID;
    @FXML
    private JFXComboBox<Room> cmbRoomNo;
    @FXML
    private JFXTextField txtCustomerName;
    @FXML
    private JFXTextField txtCustomerMobile;
    @FXML
    private JFXTextField txtCustomerEmail;

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

    

}
