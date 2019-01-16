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
import dao.ReservationDao;
import dao.RoomDao;
import entity.Customer;
import entity.Customerpackage;
import entity.Reservation;
import entity.Reservationstatus;
import entity.Room;
import entity.Roomtype;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
//import static ui.MainWindowController.apnRight;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class CheckinMainUIController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="Form-Data">
    String initial;
    String valid;
    String invalid;
    String updated;

    int page;
    int row;

    public static Reservation todayCheckInReservation;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FXML-Data">
    @FXML
    private JFXDatePicker dtpSearchArrival;
    @FXML
    private JFXDatePicker dtpSearchDeparture;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<Reservation> tblReservation;
    @FXML
    private TableColumn<Reservation, Customer> colGuestID;
    @FXML
    private TableColumn<Reservation, Customer> colGuestName;
    @FXML
    private TableColumn<Reservation, String> colArrival;
    @FXML
    private TableColumn<Reservation, String> colDeparture;
    @FXML
    private TableColumn<Reservation, Room> colRoomNo;
    @FXML
    private TableColumn<Reservation, String> colReservationID;
    @FXML
    private TableColumn<Reservation, Customerpackage> colCustomerPackage;
    @FXML
    private TableColumn<Reservation, Roomtype> colRoomType;
    @FXML
    private TableColumn<Reservation, Reservationstatus> colReservationStatus;
    @FXML
    private JFXButton btnSearchThisWeek;
    @FXML
    private JFXButton btnSearchThisMonth;
    @FXML
    private AnchorPane apnTodayCheckIn;
    @FXML
    private JFXButton btnClear;

//</editor-fold>
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initial = Style.initial;
        valid = Style.valid;
        invalid = Style.invalid;
        updated = Style.updated;

        loadTable();

    }

    private void loadTable() {
        tblReservation.setRowFactory(tv -> {
            TableRow<Reservation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    todayCheckInReservation = row.getItem();

                    try {
                        AnchorPane root = FXMLLoader.load(Main.class.getResource("CheckinUI.fxml"));
                        apnTodayCheckIn.getChildren().clear();
                        apnTodayCheckIn.getChildren().add(root);
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            return row;
        });

        dtpSearchArrival.setValue(null);
        dtpSearchDeparture.setValue(null);

        colReservationID.setCellValueFactory(new PropertyValueFactory("id"));
       // colGuestName.setCellValueFactory(new PropertyValueFactory("customerId"));
        
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
        
        colGuestID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Reservation, Customer>, ObservableValue<Customer>>() {
            @Override
            public ObservableValue<Customer> call(TableColumn.CellDataFeatures<Reservation, Customer> param) {
                return new SimpleObjectProperty(param.getValue().getCustomerId().getId());

            }
        });
        
        

        colArrival.setCellValueFactory(new PropertyValueFactory("arrival"));
        colDeparture.setCellValueFactory(new PropertyValueFactory("departure"));
        //colGuestID.setCellValueFactory(new PropertyValueFactory("customerId"));
        colCustomerPackage.setCellValueFactory(new PropertyValueFactory("customerpackageId"));

        colRoomType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Reservation, Roomtype>, ObservableValue<Roomtype>>() {
            @Override
            public ObservableValue<Roomtype> call(TableColumn.CellDataFeatures<Reservation, Roomtype> param) {
                return new SimpleObjectProperty(param.getValue().getRoomId().getRoomtypeId().getName());
            }
        });

        colReservationStatus.setCellValueFactory(new PropertyValueFactory("reservationstatusId"));

        row = 0;
        page = 0;

        fillTable(ReservationDao.getAllTodayCheckIn());

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

    @FXML
    private void dtpSearchArrivalAP(ActionEvent event) {
        if (null != dtpSearchArrival.getValue() && null != dtpSearchDeparture.getValue()) {
            setDataToTheTable();
        }
    }

    @FXML
    private void dtpSearchDepartureAP(ActionEvent event) {
        if (null != dtpSearchArrival.getValue() && null != dtpSearchDeparture.getValue()) {
            setDataToTheTable();
        }
    }


    @FXML
    private void btnSearchThisWeekAP(ActionEvent event) {
        LocalDate today = LocalDate.now();

        // Go backward to get Monday
        LocalDate monday = today;
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }

        // Go forward to get Sunday
        LocalDate sunday = today;
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
            sunday = sunday.plusDays(1);
        }

        Date thisWeekFirstDate = java.sql.Date.valueOf(monday);
        Date thisWeekLastDate = java.sql.Date.valueOf(sunday);

        fillTable(ReservationDao.getThisWeekCheckIn(thisWeekFirstDate, thisWeekLastDate));
    }

    @FXML
    private void btnSearchThisMonthAP(ActionEvent event) {
        LocalDate today = LocalDate.now();

        Date thisMontFirstDate = java.sql.Date.valueOf(today.withDayOfMonth(1));
        Date thisMontLastDate = java.sql.Date.valueOf(today.withDayOfMonth(today.lengthOfMonth()));

        fillTable(ReservationDao.getAllByTwoDates(thisMontFirstDate, thisMontLastDate));
    }

    @FXML
    private void tblReservationMC(MouseEvent event) {

    }

    private void setDataToTheTable() {
        if (null != dtpSearchArrival.getValue() && null != dtpSearchDeparture.getValue()) {
            Date toDate = java.sql.Date.valueOf(dtpSearchArrival.getValue());
            Date fromDate = java.sql.Date.valueOf(dtpSearchDeparture.getValue());

            fillTable(ReservationDao.getThisWeekCheckIn(toDate, fromDate));
        }
    }

    @FXML
    private void btnClearAP(ActionEvent event) {
    }

}
