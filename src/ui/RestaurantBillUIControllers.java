/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dao.CustomerDao;
import dao.ReservationDao;
import dao.RestaurantBillDao;
import entity.Customer;
import entity.Reservation;
import entity.Restaurantbill;
import entity.Restaurantfooditemlist;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import report.ReportView;
import static ui.LiquorBillUIControllers.liquorItemListUIStage;
import static ui.Main.stageBasic;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class RestaurantBillUIControllers implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="Form-Data">
    public static Restaurantbill restaurantbill;
    Restaurantbill oldRestaurantbill;

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

    public static Stage restauranatFoodItemListUIStage;

    Reservation reservarion;

//    public static Restaurantbill restaurantbill;
//    static BigDecimal ttCost = new BigDecimal(0);
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="FXML-Data">
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
    private Button btnClear;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnSearchClear;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<Restaurantbill> tblRestaurantBill;
    @FXML
    private TableColumn<Restaurantbill, String> colReservationID;
    @FXML
    private TableColumn<Restaurantbill, Customer> colGuestName;
    @FXML
    private TableColumn<Restaurantbill, String> colRestaurantBillID;
    @FXML
    private TableColumn<Restaurantbill, String> colReservedDate;
    @FXML
    private TableColumn<Restaurantbill, String> colTotalaPrice;
    @FXML
    private JFXTextField txtSearchName;
    @FXML
    private Label lblEmployeeID1;
    @FXML
    private JFXDatePicker dtpReservedDate;
    @FXML
    private ScrollPane scpSelectedFoodItems;
    @FXML
    private Label lblTotalPrice;
    @FXML
    private Button btnRestaurantBill;
    @FXML
    private JFXTextField txtSearchRestaurantBillID;
    @FXML
    private Label lblRestaurantBillID;

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Initialize-Methods">
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

        restaurantbill = new Restaurantbill();
        oldRestaurantbill = null;

        txtCustomerName.setEditable(false);
        txtCustomerEmail.setEditable(false);
        txtCustomerMobile.setEditable(false);

        txtID.setText("");
        txtID.setDisable(false);
        txtID.setEditable(true);

        txtCustomerName.setText("");
        txtCustomerEmail.setText("");
        txtCustomerMobile.setText("");

        dtpReservedDate.setDisable(true);
        dtpReservedDate.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        Date assign = java.sql.Date.valueOf(dtpReservedDate.getValue());
        restaurantbill.setReserveddate(assign);

        if (RestaurantBillDao.getLastResetaurantBillId() != null) {

            lblRestaurantBillID.setText(String.format("%06d", RestaurantBillDao.getLastResetaurantBillId() + 1));

        } else {

            lblRestaurantBillID.setText(String.format("%06d", 1));

        }

        dissableButtons(false, false, true, true);

        toFillList(FXCollections.observableArrayList());

        setStyle(initial);
    }

    private void toFillList(List<Restaurantfooditemlist> innertableList) {
        GridPane gridPane = new GridPane();

        gridPane.setPadding(new Insets(5, 5, 5, 5));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        scpSelectedFoodItems.setContent(gridPane);
        scpSelectedFoodItems.setPannable(true);

        DropShadow dsForText = new DropShadow(20, Color.GREEN);

        for (int i = 0; i < innertableList.size(); i++) {
            Label lable = new Label("");

            lable.setText(innertableList.get(i).getFooditemId().getName());
            lable.setId(String.valueOf(i));

            Label lableUnitPize = new Label(innertableList.get(i).getFooditemId().getUnitprice().toString());
            lableUnitPize.setId(String.valueOf(i));

            TextField textField = new TextField(innertableList.get(i).getQty().toString());

            textField.setId(String.valueOf(i));
            textField.setMaxSize(50, 10);
            textField.setEditable(false);

            Label lablePrice = new Label("");
            lablePrice.setText(String.valueOf(innertableList.get(i).getFooditemId().getUnitprice().multiply(new BigDecimal(innertableList.get(i).getQty()))));
            lablePrice.setId(String.valueOf(i));

            gridPane.add(lable, 0, i);
            gridPane.add(lableUnitPize, 5, i);
            gridPane.add(textField, 10, i);
            gridPane.add(lablePrice, 15, i);
        }

        BigDecimal totalCost = BigDecimal.ZERO;

        for (int i = 0; i < innertableList.size(); i++) {
            totalCost = totalCost.add(innertableList.get(i).getFooditemId().getUnitprice().multiply(new BigDecimal(innertableList.get(i).getQty())));
        }

        lblTotalPrice.setText(totalCost.toString());
    }

    private void setStyle(String style) {

        txtID.setStyle(style);
        dtpReservedDate.getEditor().setStyle(style);
        txtSearchName.setStyle(style);
        txtSearchRestaurantBillID.setStyle(style);

    }

    private void dissableButtons(boolean select, boolean insert, boolean update, boolean delete) {

        btnAdd.setDisable(insert);
        //   btnUpdate.setDisable(update);
        btnDelete.setDisable(delete);

    }

    private void loadTable() {

        tblRestaurantBill.setRowFactory(tv -> {
            TableRow<Restaurantbill> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        // todayCheckInReservation = row.getItem();
                        Parent root = FXMLLoader.load(getClass().getResource("RestauranatFoodItemListUI.fxml"));

                        Scene scene = new Scene(root);

                        restauranatFoodItemListUIStage = new Stage();
                        restauranatFoodItemListUIStage.setScene(scene);

                        restauranatFoodItemListUIStage.setOnCloseRequest(e -> {

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                            alert.setTitle(" WINDOW CLOSE");
                            alert.setHeaderText("Close Form");
                            alert.setContentText("Are you sure you need to close this form?? ");

                            DialogPane dialogPane = alert.getDialogPane();

                            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
                            dialogPane.getStyleClass().add("myDialogForConfirmation");

                            Optional<ButtonType> result = alert.showAndWait();

                            if (result.get() == ButtonType.OK) {

                                restauranatFoodItemListUIStage.close();

                            }

                            e.consume();

                        });

                        restauranatFoodItemListUIStage.setOnHiding(e -> {

                            tblRestaurantBill.getSelectionModel().clearSelection();

                            fillTable(RestaurantBillDao.getAllRestaurantBill());

                            toFillList(FXCollections.observableArrayList());

                        });

                        restauranatFoodItemListUIStage.initModality(Modality.WINDOW_MODAL);
                        restauranatFoodItemListUIStage.initOwner(stageBasic);
                        restauranatFoodItemListUIStage.setResizable(false);
                        restauranatFoodItemListUIStage.setTitle("Oder and Payment Management System");
                        restauranatFoodItemListUIStage.show();

                    } catch (IOException ex) {
                        Logger.getLogger(RestaurantBillUIControllers.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            return row;
        });

        txtSearchName.setText("");
        txtSearchRestaurantBillID.setText("");

        colRestaurantBillID.setCellValueFactory(new PropertyValueFactory("id"));
        colReservationID.setCellValueFactory(new PropertyValueFactory("reservationId"));
        colReservedDate.setCellValueFactory(new PropertyValueFactory("reserveddate"));
        colTotalaPrice.setCellValueFactory(new PropertyValueFactory("totalprice"));

        colGuestName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Restaurantbill, Customer>, ObservableValue<Customer>>() {
            @Override
            public ObservableValue<Customer> call(TableColumn.CellDataFeatures<Restaurantbill, Customer> param) {
                return new SimpleObjectProperty(param.getValue().getReservationId().getCustomerId().getName());
            }
        });

        row = 0;
        page = 0;

        fillTable(RestaurantBillDao.getAllRestaurantBill());

        pagination.setCurrentPageIndex(0);

    }

    private void fillTable(ObservableList<Restaurantbill> reservation) {

        if (reservation != null && !reservation.isEmpty()) {

            int rowsCount = 5;
            int pageCount = ((reservation.size() - 1) / rowsCount) + 1;
            pagination.setPageCount(pageCount);

            pagination.setPageFactory((Integer pageIndex) -> {
                int start = pageIndex * rowsCount;
                int end = pageIndex == pageCount - 1 ? reservation.size() : pageIndex * rowsCount + rowsCount;
                tblRestaurantBill.getItems().clear();
                tblRestaurantBill.setItems(FXCollections.observableArrayList(reservation.subList(start, end)));
                return tblRestaurantBill;
            });

        } else {

            pagination.setPageCount(1);
            tblRestaurantBill.getItems().clear();

        }

        pagination.setCurrentPageIndex(page);
        tblRestaurantBill.getSelectionModel().select(row);

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Binding-Methods">
    @FXML
    private void txtIDKR(KeyEvent event) {

        if (!(txtID.getText().trim().isEmpty()) && txtID.getText().trim().matches("\\d{9}[V|v|x|X]")) {

            reservarion = ReservationDao.getAllByCustomerIDOnlyCheckInTypes(txtID.getText().trim());

            if (reservarion != null) {

                restaurantbill.setReservationId(reservarion);

                txtID.setStyle(valid);

                toFillCustomerDetails(reservarion);

            } else {

                restaurantbill.setReservationId(null);

                txtID.setStyle(invalid);

                txtCustomerName.setText("");
                txtCustomerMobile.setText("");
                txtCustomerEmail.setText("");

            }

        } else {

            restaurantbill.setReservationId(null);

            txtID.setStyle(invalid);

            txtCustomerName.setText("");
            txtCustomerMobile.setText("");
            txtCustomerEmail.setText("");

        }
    }

    private void toFillCustomerDetails(Reservation reservation) {

        if (reservation.getCustomerId().getName() != null) {

            txtCustomerName.setText(reservation.getCustomerId().getName());

        } else {

            txtCustomerName.setText("");

        }

        if (reservation.getCustomerId().getEmail() != null) {

            txtCustomerEmail.setText(reservation.getCustomerId().getEmail());

        } else {

            txtCustomerEmail.setText("");

        }
        if (reservation.getCustomerId().getMobile() != null) {

            txtCustomerMobile.setText(reservation.getCustomerId().getMobile());

        } else {

            txtCustomerEmail.setText("");

        }
        lblCustomerID.setText(reservation.getId().toString());

    }

    @FXML
    private void dtpReservedDateAP(ActionEvent event) {

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Operation-Methods">
    @FXML
    private void btnDeleteAP(ActionEvent event) {
        String details
                = "\nName       \t: " + restaurantbill.getReservationId().getCustomerId().getName()
                + "\nReserved date   \t: " + restaurantbill.getReserveddate();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Module");
        alert.setHeaderText("Are you sure you need to delete the following Module?");
        alert.setContentText(details);

        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialogForConfirmation");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            // Notification.Notifier.INSTANCE.notifySuccess("Delete", ( rdbPerson.isSelected() ? customer.getName() : customer.getCompanyname() ) + " is deleted!");
//            CustomerDao.delete(oldCustomer);
//            oldCu.setDisable(1);
            RestaurantBillDao.delete(oldRestaurantbill);

            fillTable(RestaurantBillDao.getAll());
            loadForm();

            pagination.setCurrentPageIndex(page);
            tblRestaurantBill.getSelectionModel().select(row);

        }
    }

//    private void btnUpdateAP(ActionEvent event) {
//        
//         String errors = getErrors();
//        
//        if ( errors.isEmpty() ) {
//
//            String updates = getUpdates();
//
//            if (!updates.isEmpty()) {
//
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//
//                alert.setTitle("Update Module");
//                alert.setHeaderText("Are you sure you need to update the following Module????");
//                alert.setContentText(updates);
//                
//                DialogPane dialogPane = alert.getDialogPane();
//
//                dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
//                dialogPane.getStyleClass().add("myDialogForConfirmation");
//
//                Optional<ButtonType> result = alert.showAndWait();
//
//                if (result.get() == ButtonType.OK) {
//                    
//              //      Notification.Notifier.INSTANCE.notifySuccess("Delete", ( rdbPerson.isSelected() ? customer.getName() : customer.getCompanyname() ) + " is updated!");
//
//                    RestaurantBillDao.update(restaurantbill);
//
//                    loadForm();
//                    loadTable();
//
//                }
//
//            } else {
//                
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//
//                alert.setTitle("Update Module");
//                alert.setHeaderText("There is nothing to Update!!!");
//                alert.setContentText("Nothing to Update!!!");
//                
//                DialogPane dialogPane = alert.getDialogPane();
//
//                dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
//                dialogPane.getStyleClass().add("myDialogForInformation");
//                
//                alert.showAndWait();
//                
//            }
//            
//        } else {
//
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//
//            alert.setTitle("Error - Restaurant Bill Update");
//            alert.setHeaderText("Form Data Error");
//            alert.setContentText(errors);
//            
//            DialogPane dialogPane = alert.getDialogPane();
//
//            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
//            dialogPane.getStyleClass().add("myDialogForError");
//            
//            alert.showAndWait();
//
//        }
//    }
    @FXML
    private void btnClearAP(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialogForConfirmation");

        alert.setTitle(" Restaurant Bill");
        alert.setHeaderText("Clear Form");
        alert.setContentText("Are you sure you need to clear form?? ");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            loadForm();

        }
    }

    @FXML
    private void btnAddAP(ActionEvent event) {
        String errors = getErrors();

        if (errors.isEmpty()) {

            String details
                    = "\nName       \t: " + restaurantbill.getReservationId().getCustomerId().getName()
                    + "\nReserved date   \t: " + restaurantbill.getReserveddate();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add Module");
            alert.setHeaderText("Are you sure you need to add the following Guest");
            alert.setContentText(details);

            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForConfirmation");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                //  Notification.Notifier.INSTANCE.notifySuccess("Add", ( rdbPerson.isSelected() ? customer.getName() : customer.getCompanyname() ) + " is added!");
                //  restaurantbill.setDisable(0);
                RestaurantBillDao.add(restaurantbill);

                loadTable();
                loadForm();

            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You need to fill the following Guest");
            alert.setContentText(errors);

            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForError");

            alert.showAndWait();

        }
    }

    private String getErrors() {

        String errors = "";

        if (restaurantbill.getReservationId().getCustomerId() == null) {
            errors = errors + "Name \t\tis Invalid\n";
        }

        return errors;

    }

    private String getUpdates() {

        String updates = "";

        if (oldRestaurantbill != null) {

            if (restaurantbill.getReservationId().getCustomerId().getName() != null && !restaurantbill.getReservationId().getCustomerId().getName().equals(restaurantbill.getReservationId().getCustomerId().getName())) {
                updates = updates + oldRestaurantbill.getReservationId().getCustomerId().getName() + " chanaged to " + restaurantbill.getReservationId().getCustomerId().getName() + "\n";
            }

        }

        return updates;
    }

    @FXML
    private void tblRestaurantBillMC(MouseEvent event) {
        fillForm();
    }

    @FXML
    private void tblRestaurantBillKR(KeyEvent event) {
        fillForm();
    }

    @FXML
    private void btnRestaurantBillAP(ActionEvent event) {
        if (null != restaurantbill.getId()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Restaurant Bill");
            alert.setHeaderText("Restaurant Bill");
            alert.setContentText("Are you sure you need to generate the restaurant bill?");

            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForConfirmation");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                if (null == restaurantbill.getPaiddate()) {
                    restaurantbill.setPaiddate(today);

                    RestaurantBillDao.update(restaurantbill);
                }

                reservarion.setRestaurantbill(restaurantbill.getTotalprice());
                ReservationDao.update(reservarion);

                HashMap hmap = new HashMap();

                try {
                    // bills.reservationinitial    
                    hmap.put("restBillId", restaurantbill.getId());

                    //System.out.println(hmap);
                    new ReportView("/bills/restaurantbill/restaurantbill.jasper", hmap);

                } catch (Exception e) {

                    System.out.println("JobDetailsGetByJobId Report error.....!!");

                }

            }
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error - Report");
            alert.setHeaderText("Input Error");
            alert.setContentText("Restaurant Bill  ID is invalid!");

            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForError");

            alert.showAndWait();

        }
    }

    private void fillForm() {

        if (tblRestaurantBill.getSelectionModel().getSelectedItem() != null) {

            dissableButtons(false, true, false, false);

            setStyle(valid);

            // cmbStatus.setDisable(false);
            txtSearchRestaurantBillID.setStyle(initial);
            txtSearchName.setStyle(initial);

            oldRestaurantbill = RestaurantBillDao.getByIdWithHmap(tblRestaurantBill.getSelectionModel().getSelectedItem().getId());
            restaurantbill = RestaurantBillDao.getByIdWithHmap(tblRestaurantBill.getSelectionModel().getSelectedItem().getId());

            reservarion = oldRestaurantbill.getReservationId();

            toFillList(restaurantbill.getRestaurantfooditemlistList());

            txtID.setText(oldRestaurantbill.getReservationId().getCustomerId().getIdno());
            txtID.setEditable(false);
            txtCustomerName.setText(oldRestaurantbill.getReservationId().getCustomerId().getName());
            txtCustomerEmail.setText(oldRestaurantbill.getReservationId().getCustomerId().getEmail());
            txtCustomerMobile.setText(oldRestaurantbill.getReservationId().getCustomerId().getMobile());

            dtpReservedDate.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(oldRestaurantbill.getReserveddate())));
            dtpReservedDate.getEditor().setText(new SimpleDateFormat("yyyy-MM-dd").format(oldRestaurantbill.getReserveddate()));

            lblRestaurantBillID.setText(String.format("%06d", oldRestaurantbill.getId()));
            lblCustomerID.setText(String.format("%06d", oldRestaurantbill.getReservationId().getCustomerId().getId()));

            page = pagination.getCurrentPageIndex();
            row = tblRestaurantBill.getSelectionModel().getSelectedIndex();

        }

    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Search-Methods">
    @FXML
    private void btnSearchClearAP(ActionEvent event) {
    }

    @FXML
    private void txtSearchNameKR(KeyEvent event) {
    }
//</editor-fold>

}
