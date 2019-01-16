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
import dao.CustomerPackageDao;
import dao.ExtrabedDao;
import dao.PaymentTypeDao;
import dao.ReservationDao;
import dao.ReservationStatusDao;
import dao.ReservationTypeDao;
import dao.RoomDao;
import dao.RoomTypeDao;
import entity.Customer;
import entity.Customerpackage;
import entity.Extrabed;
import entity.Paymenttype;
import entity.Reservation;
import entity.Reservationstatus;
import entity.Reservationtype;
import entity.Room;
import entity.Roomtype;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import static ui.CheckinMainUIController.todayCheckInReservation;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class CheckinUIControllers implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="Form-Data">
    Reservation reservation = todayCheckInReservation;
    Reservation oldReservation;

    String initial;
    String valid;
    String invalid;
    String updated;

    int page;
    int row;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    Date today = new Date();

    DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    LocalDateTime now = LocalDateTime.now();

    Stage roomAvailabilityStage;

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="FXML-Data">
    private JFXDatePicker dtpSearchCheckedInDate;
    @FXML
    private JFXTextField txtID;
    @FXML
    private Label lblCustomerID;
    @FXML
    private JFXTextField txtCustomerName;
    @FXML
    private JFXTextField txtCustomerMobile;
    @FXML
    private JFXTextField txtCustomerEmail;
    @FXML
    private JFXComboBox<Customerpackage> cmbCustomerPackage;
    @FXML
    private JFXComboBox<Roomtype> cmbRoomType;
    @FXML
    private JFXDatePicker dtpArrival;
    @FXML
    private Label lblEmployeeID1;
    @FXML
    private JFXDatePicker dtpReservationDate;
    @FXML
    private Label lblReservationID;
    @FXML
    private JFXDatePicker dtpDeparture;
    @FXML
    private JFXComboBox<Extrabed> cmbExtBed;
    @FXML
    private JFXComboBox<Room> cmbRoomNo;
    @FXML
    private TextArea txtComments;
    @FXML
    private JFXTextField txtNights;
    @FXML
    private JFXTextField txtChild;
    @FXML
    private JFXTextField txtAdults;
    @FXML
    private JFXTextField txtResVatPres;
    @FXML
    private JFXTextField txtResSerChaPres;
    @FXML
    private JFXTextField txtResSubTotal;
    @FXML
    private JFXTextField txtResGrandTotal;
    @FXML
    private JFXTextField txtResDisPres;
    @FXML
    private Button btnCheckIn;
    @FXML
    private JFXComboBox<Reservationtype> cmbReservationType;
    @FXML
    private JFXComboBox<Paymenttype> cmbPaymentType;
    @FXML
    private AnchorPane apnCheckIn;

    //<editor-fold defaultstate="collapsed" desc="Initializing-Methods">
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initial = Style.initial;
        valid = Style.valid;
        invalid = Style.invalid;
        updated = Style.updated;

        loadForm();

    }

    private void loadForm() {

        reservation = todayCheckInReservation;
        oldReservation = null;

        txtResDisPres.setText(reservation.getDispresentage().toString());
        txtResGrandTotal.setText(reservation.getReservationgrandtotal().toString());
        txtResSerChaPres.setText(reservation.getServicechargepresentage().toString());
        txtResSubTotal.setText(reservation.getReservationsubtotal().toString());
        txtResVatPres.setText(reservation.getVatpresentage().toString());
        cmbPaymentType.getSelectionModel().select((Paymenttype)reservation.getPaymenttypeId());
        
        cmbExtBed.getSelectionModel().select((Extrabed) reservation.getExtrabedId());
        cmbExtBed.setDisable(true);

        cmbCustomerPackage.getSelectionModel().select((Customerpackage) reservation.getCustomerpackageId());
        cmbCustomerPackage.setDisable(true);

        cmbRoomType.getSelectionModel().select((Roomtype) reservation.getRoomId().getRoomtypeId());
        cmbRoomType.setDisable(true);

        cmbRoomNo.getSelectionModel().select((Room) reservation.getRoomId());
        cmbRoomNo.setDisable(true);

        cmbPaymentType.getSelectionModel().select((Paymenttype) reservation.getPaymenttypeId());
        cmbPaymentType.setDisable(true);

        cmbReservationType.getSelectionModel().select((Reservationtype) reservation.getReservationtypeId());
        cmbReservationType.setDisable(true);

        txtID.setText(reservation.getCustomerId().getIdno());
        txtCustomerName.setText(reservation.getCustomerId().getName());
        txtResDisPres.setText(reservation.getDispresentage().toString());
        txtResSerChaPres.setText(reservation.getServicechargepresentage().toString());
        txtResVatPres.setText(reservation.getVatpresentage().toString());
        txtNights.setText(reservation.getNights().toString());
        txtAdults.setText(reservation.getAdultscount().toString());
        txtChild.setText(reservation.getChildcount().toString());
        txtCustomerEmail.setText(reservation.getCustomerId().getEmail());
        txtCustomerMobile.setText(reservation.getCustomerId().getMobile());
        txtComments.setText(reservation.getComments());

        txtID.setEditable(false);
        txtCustomerName.setEditable(false);
        txtCustomerEmail.setEditable(false);
        txtCustomerMobile.setEditable(false);
        txtResDisPres.setDisable(true);
        txtResSerChaPres.setDisable(true);
        txtResVatPres.setDisable(true);
        txtNights.setDisable(true);
        txtAdults.setDisable(true);
        txtChild.setDisable(true);

        txtComments.setDisable(true);

        dtpArrival.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(reservation.getArrival())));
        dtpArrival.getEditor().setText(new SimpleDateFormat("yyyy-MM-dd").format(reservation.getArrival()));
        dtpArrival.setDisable(true);
        dtpDeparture.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(reservation.getDeparture())));
        dtpDeparture.getEditor().setText(new SimpleDateFormat("yyyy-MM-dd").format(reservation.getDeparture()));
        dtpDeparture.setDisable(true);

        dtpReservationDate.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(reservation.getReservationdate())));
        dtpReservationDate.getEditor().setText(new SimpleDateFormat("yyyy-MM-dd").format(reservation.getReservationdate()));
        dtpReservationDate.setDisable(true);

        lblReservationID.setText(String.format("%06d", reservation.getId()));
        lblCustomerID.setText(String.format("%06d", reservation.getCustomerId().getId()));

    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Binding-Methods">
    @FXML
    private void txtIDKR(KeyEvent event) {
    }

    @FXML
    private void cmbCustomerPackageAP(ActionEvent event) {
    }

    @FXML
    private void cmbRoomTypeAP(ActionEvent event) {
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
    private void cmbRoomNoAP(ActionEvent event) {
    }

    @FXML
    private void txtCommentsKR(KeyEvent event) {
    }

    @FXML
    private void txtNightsKR(KeyEvent event) {
    }

    @FXML
    private void txtChildKR(KeyEvent event) {
    }

    @FXML
    private void txtAdultsKR(KeyEvent event) {
    }

    @FXML
    private void txtResVatPresKR(KeyEvent event) {
    }

    @FXML
    private void txtResSerChaPresKR(KeyEvent event) {
    }

    @FXML
    private void txtResDisPresKR(KeyEvent event) {
    }

    @FXML
    private void cmbReservationTypeAP(ActionEvent event) {
    }


    @FXML
    private void cmbPaymentTypeAP(ActionEvent event) {
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Operation-Methods">
    @FXML
    private void btnCheckInAP(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Checkin Module");
        alert.setHeaderText("Checkin Module");
        alert.setContentText("Are you sure  need to checkin this reservation");

        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialogForConfirmation");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            reservation.setReservationstatusId(ReservationStatusDao.getCheckinTypeOnly());
            //  Notification.Notifier.INSTANCE.notifySuccess("Update", employee.getName() + " is updated!");
            reservation.setCheckindate(today);
            
            ReservationDao.update(reservation);

            try {
                AnchorPane root = FXMLLoader.load(Main.class.getResource("CheckinMainUI.fxml"));
                apnCheckIn.getChildren().clear();
                apnCheckIn.getChildren().add(root);
            } catch (IOException ex) {
                Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
//</editor-fold>

}
