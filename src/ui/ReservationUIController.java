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

    private void loadForm() {

        reservation = new Reservation();
        oldReservation = null;

        cmbExtBed.setItems(ExtBedDao.getAll());//get all cmb details
        cmbExtBed.getSelectionModel().clearSelection();//clear the selected item

        cmbPackage.setItems(PackageDao.getAll());
        cmbPackage.getSelectionModel().clearSelection();

        cmbReservationStatus.setItems(ReservationStatusDao.getAll());
        cmbReservationStatus.getSelectionModel().clearSelection();

        cmbRoomType.setItems(RoomTypeDao.getAll());
        cmbRoomType.getSelectionModel().clearSelection();

        cmbRoomNo.setItems(RoomDao.getAll());
        cmbRoomType.getSelectionModel().clearSelection();

//        cmbStatus.setItems(EmployeestatusDao.getAll());
//        cmbStatus.getSelectionModel().select(0);//select the first index value
//        cmbStatus.setDisable(true);//disable the combo box
//        employee.setEmployeestatusId(cmbStatus.getSelectionModel().getSelectedItem());//add status details to employee object because it is disable combo box
//        
        txtID.setText("");
        txtDisPresentage.setText("");
        txtServiceChargePresentage.setText("");
        txtVatPresentage.setText("");

        dtpArrival.setValue(null);
        dtpDeparture.setValue(null);

        dtpReservationDate.setDisable(true);
        dtpReservationDate.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        Date assign = java.sql.Date.valueOf(dtpReservationDate.getValue());
        reservation.setReservationdate(assign);

        if (ReservationDao.getLastReservationId() != null) {

            lblReservationID.setText(String.format("%06d", ReservationDao.getLastReservationId() + 1));

        } else {

            lblReservationID.setText(String.format("%06d", 1));

        }

        dissableButtons(false, false, true, true);

        setStyle(initial);
    }

    private void setStyle(String style) {

        cmbExtBed.setStyle(style);
        cmbPackage.setStyle(style);
        cmbReservationStatus.setStyle(style);
        cmbRoomType.setStyle(style);

        txtID.setStyle(style);
        txtDisPresentage.setStyle(style);
        txtServiceChargePresentage.setStyle(style);
        txtVatPresentage.setStyle(style);

        if (!txtComments.getChildrenUnmodifiable().isEmpty()) {

            ((ScrollPane) txtComments.getChildrenUnmodifiable().get(0)).getContent().setStyle(style);

        }

        dtpReservationDate.getEditor().setStyle(style);

        cmbSearchRoomNo.setStyle(style);
        dtpSearchArrival.setStyle(style);
        dtpSearchDeparture.setStyle(style);
        txtSearchName.setStyle(style);

    }

    private void dissableButtons(boolean select, boolean insert, boolean update, boolean delete) {

        btnAdd.setDisable(insert);
        btnUpdate.setDisable(update);
        btnDelete.setDisable(delete);

    }

    private void loadTable() {

        cmbSearchRoomNo.setItems(RoomDao.getAll());
        cmbSearchRoomNo.getSelectionModel().clearSelection();
        
        txtSearchName.setText("");
        dtpSearchArrival.setValue(null);
        dtpSearchDeparture.setValue(null);

        colReservationID.setCellValueFactory(new PropertyValueFactory("id"));
        colGuestID.setCellValueFactory(new PropertyValueFactory("customer_id"));
        colRoomNo.setCellValueFactory(new PropertyValueFactory("mobile"));
        colArrival.setCellValueFactory(new PropertyValueFactory("arrival"));
        colDeparture.setCellValueFactory(new PropertyValueFactory("departure"));

        row = 0;
        page = 0;

        fillTable(ReservationDao.getAll());

        pagination.setCurrentPageIndex(0);

    }

    private void fillTable(ObservableList<Reservation> reservation) {

        if (reservation != null && !reservation.isEmpty()) {

            int rowsCount = 5;
            int pageCount = ((reservation.size() - 1) / rowsCount) + 1;
            pagination.setPageCount(pageCount);

            pagination.setPageFactory((Integer pageIndex) -> {
                int start = pageIndex * rowsCount;
                int end = pageIndex == pageCount - 1 ? reservation.size() : pageIndex * rowsCount + rowsCount;
                tblReservation.getItems().clear();
                tblReservation.setItems(FXCollections.observableArrayList(reservation.subList(start, end)));
                return tblReservation;
            });

        } else {

            pagination.setPageCount(1);
            tblReservation.getItems().clear();

        }

        pagination.setCurrentPageIndex(page);
        tblReservation.getSelectionModel().select(row);

    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Binding-Methods">
    @FXML
    private void txtIDKR(KeyEvent event) {
         if ( !(txtID.getText().trim().isEmpty())) {
            //txtID.getText().trim().matches("\\d*") &&
            Customer customer = CustomerDao.getAllByCustomerID(txtID.getText().trim());
            
            if (customer != null) {
                
                reservation.setCustomerId(customer);
                
                txtID.setStyle(valid);
                
                toFillCustomerDetails(customer);
                
            } else {
                
                reservation.setCustomerId(null);
                
                txtID.setStyle(invalid);
                
                txtCustomerName.setText("");
                txtCustomerMobile.setText("");
                txtCustomerEmail.setText("");
                
                
            }
            
        } else {
            
            reservation.setCustomerId(null);
            
            txtID.setStyle(invalid);
                
            txtCustomerName.setText("");
            txtCustomerMobile.setText("");
            txtCustomerEmail.setText("");
            
            
        }
    }
    
    private void toFillCustomerDetails(Customer customer) {
        

        if (customer.getName() != null) {

            txtCustomerName.setText(customer.getName());

        } else {

            txtCustomerName.setText("");

        }

        if (customer.getEmail() !=null) {

            txtCustomerEmail.setText(customer.getEmail());

        } else {

            txtCustomerEmail.setText("");

        }
        if (customer.getMobile() !=null) {

            txtCustomerMobile.setText(customer.getMobile());

        } else {

            txtCustomerEmail.setText("");

        }
         
    }
    @FXML
    private void tblEmployeeMC(MouseEvent event) {
    }
    
    @FXML
    private void tblEmployeeKR(KeyEvent event) {
    }
    
    
    
    @FXML
    private void cmbPackageAP(ActionEvent event) {
    }
    
    @FXML
    private void cmbRoomTypeAP(ActionEvent event) {
         if (cmbRoomType.getSelectionModel().getSelectedItem() != null) {
            cmbRoomNo.setItems(
                    RoomDao.getAllByRoomType(
                            cmbRoomType.getSelectionModel().getSelectedItem())
            );
        }
    }
    
    @FXML
    private void dtpArrivalAP(ActionEvent event) {
    }
    
    @FXML
    private void dtpReservationDateAP(ActionEvent event) {
    }
    
    @FXML
    private void dtpDepartureAP(ActionEvent event) {
    }
    
    @FXML
    private void cmbExtBedAP(ActionEvent event) {
    }
    
    @FXML
    private void cmbReservationStatusAP(ActionEvent event) {
    }
    
    @FXML
    private void spnAdulltKR(KeyEvent event) {
    }
    
    @FXML
    private void btnNewCustomerAP(ActionEvent event) {
    }
    
    @FXML
    private void btnRoomAvailabilityAP(ActionEvent event) {
    }
    @FXML
    private void cmbRoomNoAP(ActionEvent event) {
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Operation-Methods">
    @FXML
    private void btnDeleteAP(ActionEvent event) {
    }
    
    @FXML
    private void btnUpdateAP(ActionEvent event) {
    }
    
    @FXML
    private void btnClearAP(ActionEvent event) {
    }
    
    @FXML
    private void btnAddAP(ActionEvent event) {
    }
//</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="Search-Methods">
    @FXML
    private void cmbSearchRoomNoAP(ActionEvent event) {
    }
    @FXML
    private void txtSearchNameKR(KeyEvent event) {
    }
    @FXML
    private void btnSearchClearAP(ActionEvent event) {
    }
    @FXML
    private void dtpSearchArrivalAP(ActionEvent event) {
    }
    
    @FXML
    private void dtpSearchDepartureAP(ActionEvent event) {
    }
//</editor-fold>
    

}
