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
import dao.CustomerPackageDao;
import dao.ExtrabedDao;
import dao.PaymentTypeDao;
import dao.ReservationDao;
import dao.ReservationStatusDao;
import dao.ReservationTypeDao;
import dao.RestaurantBillDao;
import dao.RoomDao;
import dao.RoomTypeDao;
import entity.Customer;
import entity.Customerpackage;
import entity.Reservation;
import entity.Reservationtype;
import entity.Room;
import entity.Roomtype;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
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
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import report.ReportView;
import static ui.RestaurantBillUIControllers.restaurantbill;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class CheckOutUIControllers implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="Form-Data">
    Reservation reservation;
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

    static BigDecimal ttCost = new BigDecimal(0);
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
    private JFXTextField txtCustomerName;
    @FXML
    private JFXTextField txtCustomerMobile;
    @FXML
    private JFXTextField txtCustomerEmail;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSearchClear;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<Reservation> tblReservation;
    @FXML
    private TableColumn<Reservation, String> colReservationID;
    @FXML
    private TableColumn<Reservation, Room> colRoomNo;
    @FXML
    private TableColumn<Reservation, Customer> colGuestName;
    @FXML
    private TableColumn<Reservation, String> colReservationTotal;
    @FXML
    private TableColumn<Reservation, String> colRestaurantBill;
    @FXML
    private TableColumn<Reservation, String> colLiquorBill;
    @FXML
    private TableColumn<Reservation, String> colSpaBill;
    @FXML
    private JFXTextField txtSearchName;
    @FXML
    private JFXComboBox<Room> cmbSearchRoomNo;
    @FXML
    private JFXComboBox<Customerpackage> cmbCustomerPackage;
    @FXML
    private JFXComboBox<Roomtype> cmbRoomType;
    @FXML
    private JFXDatePicker dtpArrival;
    @FXML
    private Label lblReservationID;
    @FXML
    private JFXDatePicker dtpDeparture;
    @FXML
    private JFXComboBox<Room> cmbRoomNo;
    @FXML
    private JFXTextField txtNights;
    @FXML
    private JFXTextField txtResGrandTotal;
    @FXML
    private JFXTextField txtRestauranatBill;
    @FXML
    private JFXTextField txtSpaBill;
    @FXML
    private JFXTextField txtLiquorBill;
    @FXML
    private JFXTextField txtFinalTotal;
    @FXML
    private JFXComboBox<Reservationtype> cmbReservationType;
    @FXML
    private Button btnCheckOut;

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

        txtID.setEditable(true);
        txtNights.setEditable(false);
        txtCustomerName.setEditable(false);
        txtCustomerEmail.setEditable(false);
        txtCustomerMobile.setEditable(false);
        txtResGrandTotal.setEditable(false);
        txtRestauranatBill.setEditable(false);
        txtSpaBill.setEditable(false);
        txtLiquorBill.setEditable(false);

        cmbCustomerPackage.setItems(CustomerPackageDao.getAll());
        cmbCustomerPackage.getSelectionModel().clearSelection();
        cmbCustomerPackage.setDisable(true);

        cmbRoomType.setItems(RoomTypeDao.getAll());
        cmbRoomType.getSelectionModel().clearSelection();
        cmbRoomType.setDisable(true);

        cmbRoomNo.setItems(null);
        cmbRoomNo.getSelectionModel().clearSelection();
        cmbRoomNo.setDisable(true);

        cmbReservationType.setItems(ReservationTypeDao.getAll());
        cmbReservationType.getSelectionModel().clearSelection();
        cmbReservationType.setDisable(true);

        dtpArrival.setDisable(true);
        dtpDeparture.setDisable(true);

        setTestNullForAllElement();

        txtID.setText("");

//        dtpReservationDate.setDisable(true);
//        dtpReservationDate.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
//        Date assign = java.sql.Date.valueOf(dtpReservationDate.getValue());
//        reservation.setReservationdate(assign);
//        if (ReservationDao.getLastReservationId() != null) {
//
//            lblReservationID.setText(String.format("%06d", ReservationDao.getLastReservationId() + 1));
//
//        } else {
//
//            lblReservationID.setText(String.format("%06d", 1));
//
//        }
        dissableButtons(false, false, true, true);

        setStyle(initial);
    }

    private void setTestNullForAllElement() {
        txtNights.setText("");
        txtCustomerName.setText("");
        txtCustomerEmail.setText("");
        txtCustomerMobile.setText("");
        txtResGrandTotal.setText("");
        txtRestauranatBill.setText("");
        txtSpaBill.setText("");
        txtLiquorBill.setText("");

        cmbRoomNo.getSelectionModel().clearSelection();
        cmbRoomType.getSelectionModel().clearSelection();
        cmbCustomerPackage.getSelectionModel().clearSelection();

        dtpArrival.setValue(null);
        dtpDeparture.setValue(null);
    }

    private void setStyle(String style) {

        txtID.setStyle(style);
//        cmbExtBed.setStyle(style);
//        cmbCustomerPackage.setStyle(style);
//        cmbReservationStatus.setStyle(style);
//        cmbRoomType.setStyle(style);
//        cmbRoomNo.setStyle(style);
//        cmbReservationType.setStyle(style);
//        cmbPaymentType.setStyle(style);

//        txtResDisPres.setStyle(style);
//        txtResSerChaPres.setStyle(style);
//        txtResVatPres.setStyle(style);
//        txtAdults.setStyle(style);
//        txtChild.setStyle(style);
//        txtNights.setStyle(style);
//
//        dtpArrival.setStyle(style);
//        dtpDeparture.setStyle(style);
//        if (!txtComments.getChildrenUnmodifiable().isEmpty()) {
//
//            ((ScrollPane) txtComments.getChildrenUnmodifiable().get(0)).getContent().setStyle(style);
//
//        }
//
//        dtpReservationDate.setStyle(style);
        cmbSearchRoomNo.setStyle(style);
        dtpSearchArrival.setStyle(style);
        dtpSearchDeparture.setStyle(style);
        txtSearchName.setStyle(style);

    }

    private void dissableButtons(boolean select, boolean insert, boolean update, boolean delete) {

        btnCheckOut.setDisable(insert);
        btnDelete.setDisable(delete);

    }

    private void loadTable() {

        cmbSearchRoomNo.setItems(RoomDao.getAll());
        cmbSearchRoomNo.getSelectionModel().clearSelection();

        txtSearchName.setText("");
        dtpSearchArrival.setValue(null);
        dtpSearchDeparture.setValue(null);

        colReservationID.setCellValueFactory(new PropertyValueFactory("id"));
//        colGuestName.setCellValueFactory(new PropertyValueFactory("customername"));
        //colRoomNo.setCellValueFactory(new PropertyValueFactory("roomId"));

        colRoomNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Reservation, Room>, ObservableValue<Room>>() {
            @Override
            public ObservableValue<Room> call(TableColumn.CellDataFeatures<Reservation, Room> param) {
                return new SimpleObjectProperty(param.getValue().getRoomId().getNo());
            }
        });
        colGuestName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Reservation, Customer>, ObservableValue<Customer>>() {
            @Override
            public ObservableValue<Customer> call(TableColumn.CellDataFeatures<Reservation, Customer> param) {
                return new SimpleObjectProperty(param.getValue().getCustomerId().getName());

            }
        });
        colReservationTotal.setCellValueFactory(new PropertyValueFactory("reservationgrandtotal"));
        colLiquorBill.setCellValueFactory(new PropertyValueFactory("liquorbill"));
        colSpaBill.setCellValueFactory(new PropertyValueFactory("spabill"));
        colRestaurantBill.setCellValueFactory(new PropertyValueFactory("restaurantbill"));

        row = 0;
        page = 0;

        fillTable(ReservationDao.getAllCheckout());

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
    @FXML
    private void dtpSearchArrivalAP(ActionEvent event) {
    }

    @FXML
    private void dtpSearchDepartureAP(ActionEvent event) {
    }

    @FXML
    private void txtIDKR(KeyEvent event) {
        if (!(txtID.getText().trim().isEmpty())) {
            Reservation res = ReservationDao.getAllByReservationOnlyCheckinByCusId(txtID.getText().trim());

            if (res != null) {

                reservation = res;

                txtID.setStyle(valid);

                toFillCustomerDetails(res);

            } else {
                reservation = null;

                txtID.setStyle(invalid);

                setTestNullForAllElement();
            }

        } else {
            reservation = null;

            txtID.setStyle(invalid);

            setTestNullForAllElement();

        }
    }

    private void toFillCustomerDetails(Reservation reservation) {
        BigDecimal resturentBill = new BigDecimal(BigInteger.ZERO);
        BigDecimal spaBill = new BigDecimal(BigInteger.ZERO);
        BigDecimal liqBill = new BigDecimal(BigInteger.ZERO);

        txtCustomerName.setText(reservation.getCustomerId().getName());
        txtCustomerEmail.setText(reservation.getCustomerId().getEmail());
        txtCustomerMobile.setText(reservation.getCustomerId().getMobile());

        if (null != reservation.getLiquorbill()) {
            txtLiquorBill.setText(reservation.getLiquorbill().toString());
            liqBill = reservation.getLiquorbill();
        } else {
            txtLiquorBill.setText("$");
        }

        if (null != reservation.getRestaurantbill()) {
            txtRestauranatBill.setText(reservation.getRestaurantbill().toString());
            resturentBill = reservation.getRestaurantbill();
        } else {
            txtRestauranatBill.setText("$");
        }

        if (null != reservation.getSpabill()) {
            txtSpaBill.setText(reservation.getSpabill().toString());
            spaBill = reservation.getSpabill();
        } else {
            txtSpaBill.setText("$");
        }

        txtNights.setText(reservation.getNights().toString());

        if (null != reservation.getFinaltotal()) {
            txtFinalTotal.setText(reservation.getFinaltotal().toString());
        } else {
            txtFinalTotal.setText("$");
        }

        txtResGrandTotal.setText(reservation.getReservationgrandtotal().toString());

        cmbCustomerPackage.getSelectionModel().select((Customerpackage) reservation.getCustomerpackageId());
        cmbReservationType.getSelectionModel().select((Reservationtype) reservation.getReservationtypeId());
        cmbRoomType.getSelectionModel().select((Roomtype) reservation.getRoomId().getRoomtypeId());
        cmbRoomNo.getSelectionModel().select((Room) reservation.getRoomId());

        dtpArrival.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(reservation.getArrival())));
        dtpArrival.getEditor().setText(new SimpleDateFormat("yyyy-MM-dd").format(reservation.getArrival()));
        dtpDeparture.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(reservation.getDeparture())));
        dtpDeparture.getEditor().setText(new SimpleDateFormat("yyyy-MM-dd").format(reservation.getDeparture()));

        getFinalTotalCost(
                resturentBill,
                liqBill,
                spaBill
        );

    }

    private void getFinalTotalCost(BigDecimal restu, BigDecimal liq, BigDecimal spa) {
        ttCost = restu.add(spa).add(liq);
        txtFinalTotal.setText(ttCost.toString());
    }

    @FXML
    private void btnDeleteAP(ActionEvent event) {
    }

    @FXML
    private void btnSearchClearAP(ActionEvent event) {
    }

    @FXML
    private void tblReservationMC(MouseEvent event) {
    }

    @FXML
    private void tblReservationKR(KeyEvent event) {
    }

    @FXML
    private void txtSearchNameKR(KeyEvent event) {
    }

    @FXML
    private void cmbSearchRoomNoAP(ActionEvent event) {
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
    private void dtpDepartureAP(ActionEvent event) {
    }

    @FXML
    private void cmbRoomNoAP(ActionEvent event) {
    }

    @FXML
    private void txtNightsKR(KeyEvent event) {
    }

    @FXML
    private void cmbReservationTypeAP(ActionEvent event) {
    }



}
