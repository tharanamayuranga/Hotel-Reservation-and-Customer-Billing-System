/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dao.CustomerDao;
import dao.LiquorBillDao;
import dao.ReservationDao;
import dao.RestaurantBillDao;
import dao.SpaBillDao;
import entity.Customer;
import entity.Liquorbill;
import entity.Liquoritemlist;
import entity.Reservation;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import report.ReportView;
import static ui.Main.stageBasic;
import static ui.RestaurantBillUIControllers.restauranatFoodItemListUIStage;
import static ui.RestaurantBillUIControllers.restaurantbill;
import static ui.SpaBillUIControllers.spaPackageListUIStage;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class LiquorBillUIControllers implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="Form-Data">
    public static Liquorbill liquorbill;
    Liquorbill oldLiquorbill;

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

    public static Stage liquorItemListUIStage;
    
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
    private TableView<Liquorbill> tblLiquorBill;
    @FXML
    private TableColumn<Liquorbill, String> colReservationID;
    @FXML
    private TableColumn<Liquorbill, Customer> colGuestName;
    @FXML
    private TableColumn<Liquorbill, String> colLiquorBillID;
    @FXML
    private TableColumn<Liquorbill, String> colTotalaPrice;
    @FXML
    private TableColumn<Liquorbill, String> colReservedDate;
    @FXML
    private JFXTextField txtSearchName;
    @FXML
    private Label lblEmployeeID1;
    @FXML
    private JFXDatePicker dtpReservedDate;
    @FXML
    private Label lblLiquortBillID;
    @FXML
    private ScrollPane scpSelectedLiquorItems;
    @FXML
    private Label lblTotalPrice;
    @FXML
    private Button btnLiquorBill;
    @FXML
    private JFXTextField txtSearchLiquorBillID;
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

        liquorbill = new Liquorbill();
        oldLiquorbill = null;

        txtCustomerName.setEditable(false);
        txtCustomerEmail.setEditable(false);
        txtCustomerMobile.setEditable(false);

        txtID.setText("");

        txtCustomerName.setText("");
        txtCustomerEmail.setText("");
        txtCustomerMobile.setText("");

        dtpReservedDate.setDisable(true);
        dtpReservedDate.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        Date assign = java.sql.Date.valueOf(dtpReservedDate.getValue());
        liquorbill.setReserveddate(assign);

        if (LiquorBillDao.getLastLiquorBillId() != null) {

            lblLiquortBillID.setText(String.format("%06d", LiquorBillDao.getLastLiquorBillId() + 1));

        } else {

            lblLiquortBillID.setText(String.format("%06d", 1));

        }

        dissableButtons(false, false, true, true);

        toFillList(FXCollections.observableArrayList());

        setStyle(initial);
    }

    private void toFillList(List<Liquoritemlist> innertableList) {
        GridPane gridPane = new GridPane();

        gridPane.setPadding(new Insets(5, 5, 5, 5));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        scpSelectedLiquorItems.setContent(gridPane);
        scpSelectedLiquorItems.setPannable(true);

        DropShadow dsForText = new DropShadow(20, Color.GREEN);

        for (int i = 0; i < innertableList.size(); i++) {
            Label lable = new Label("");

            lable.setText(innertableList.get(i).getLiquoritemId().getName());
            lable.setId(String.valueOf(i));

            Label lableUnitPize = new Label(innertableList.get(i).getLiquoritemId().getUnitprice().toString());
            lableUnitPize.setId(String.valueOf(i));

            TextField textField = new TextField(innertableList.get(i).getQty().toString());

            textField.setId(String.valueOf(i));
            textField.setMaxSize(50, 10);
            textField.setEditable(false);

            Label lablePrice = new Label("");
            lablePrice.setText(String.valueOf(innertableList.get(i).getLiquoritemId().getUnitprice().multiply(new BigDecimal(innertableList.get(i).getQty()))));
            lablePrice.setId(String.valueOf(i));

            gridPane.add(lable, 0, i);
            gridPane.add(lableUnitPize, 5, i);
            gridPane.add(textField, 10, i);
            gridPane.add(lablePrice, 15, i);
        }

        BigDecimal totalCost = BigDecimal.ZERO;

        for (int i = 0; i < innertableList.size(); i++) {
            totalCost = totalCost.add(innertableList.get(i).getLiquoritemId().getUnitprice().multiply(new BigDecimal(innertableList.get(i).getQty())));
        }

        lblTotalPrice.setText(totalCost.toString());
    }

    private void setStyle(String style) {

        txtID.setStyle(style);
        dtpReservedDate.getEditor().setStyle(style);
        txtSearchName.setStyle(style);
        txtSearchLiquorBillID.setStyle(style);

    }

    private void dissableButtons(boolean select, boolean insert, boolean update, boolean delete) {

        btnAdd.setDisable(insert);
        btnDelete.setDisable(delete);

    }

    private void loadTable() {

        tblLiquorBill.setRowFactory(tv -> {
            TableRow<Liquorbill> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        // todayCheckInReservation = row.getItem();
                        Parent root = FXMLLoader.load(getClass().getResource("LiquorItemListUI.fxml"));

                        Scene scene = new Scene(root);

                        liquorItemListUIStage = new Stage();
                        liquorItemListUIStage.setScene(scene);

                        liquorItemListUIStage.setOnCloseRequest(e -> {

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                            alert.setTitle(" WINDOW CLOSE");
                            alert.setHeaderText("Close Form");
                            alert.setContentText("Are you sure you need to close this form?? ");

                            DialogPane dialogPane = alert.getDialogPane();

                            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
                            dialogPane.getStyleClass().add("myDialogForConfirmation");

                            Optional<ButtonType> result = alert.showAndWait();

                            if (result.get() == ButtonType.OK) {

                                liquorItemListUIStage.close();

                            }

                            e.consume();

                        });

                        liquorItemListUIStage.setOnHiding(e -> {

                            tblLiquorBill.getSelectionModel().clearSelection();

                            fillTable(LiquorBillDao.getAllLiquorBill());

                            toFillList(FXCollections.observableArrayList());

                        });

                        liquorItemListUIStage.initModality(Modality.WINDOW_MODAL);
                        liquorItemListUIStage.initOwner(stageBasic);
                        liquorItemListUIStage.setResizable(false);
                        liquorItemListUIStage.setTitle("Reservation & Customer Billing System");
                        liquorItemListUIStage.show();

                    } catch (IOException ex) {
                        Logger.getLogger(RestaurantBillUIControllers.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            return row;
        });

        txtSearchName.setText("");
        txtSearchLiquorBillID.setText("");

        colLiquorBillID.setCellValueFactory(new PropertyValueFactory("id"));
        colReservationID.setCellValueFactory(new PropertyValueFactory("reservationId"));
        colReservedDate.setCellValueFactory(new PropertyValueFactory("reserveddate"));
        colTotalaPrice.setCellValueFactory(new PropertyValueFactory("totalprice"));

        colGuestName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Liquorbill, Customer>, ObservableValue<Customer>>() {
            @Override
            public ObservableValue<Customer> call(TableColumn.CellDataFeatures<Liquorbill, Customer> param) {
                return new SimpleObjectProperty(param.getValue().getReservationId().getCustomerId().getName());
            }
        });

        row = 0;
        page = 0;

        fillTable(LiquorBillDao.getAllLiquorBill());

        pagination.setCurrentPageIndex(0);

    }

    private void fillTable(ObservableList<Liquorbill> reservation) {

        if (reservation != null && !reservation.isEmpty()) {

            int rowsCount = 5;
            int pageCount = ((reservation.size() - 1) / rowsCount) + 1;
            pagination.setPageCount(pageCount);

            pagination.setPageFactory((Integer pageIndex) -> {
                int start = pageIndex * rowsCount;
                int end = pageIndex == pageCount - 1 ? reservation.size() : pageIndex * rowsCount + rowsCount;
                tblLiquorBill.getItems().clear();
                tblLiquorBill.setItems(FXCollections.observableArrayList(reservation.subList(start, end)));
                return tblLiquorBill;
            });

        } else {

            pagination.setPageCount(1);
            tblLiquorBill.getItems().clear();

        }

        pagination.setCurrentPageIndex(page);
        tblLiquorBill.getSelectionModel().select(row);

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Binding-Methods">
    @FXML
    private void txtIDKR(KeyEvent event) {
        if (!(txtID.getText().trim().isEmpty()) && txtID.getText().trim().matches("\\d{9}[V|v|x|X]")) {

            Reservation reservarion = ReservationDao.getAllByCustomerID(txtID.getText().trim());

            if (reservarion != null) {

                liquorbill.setReservationId(reservarion);

                txtID.setStyle(valid);

                toFillCustomerDetails(reservarion);

            } else {

                liquorbill.setReservationId(null);

                txtID.setStyle(invalid);

                txtCustomerName.setText("");
                txtCustomerMobile.setText("");
                txtCustomerEmail.setText("");

            }

        } else {

            liquorbill.setReservationId(null);

            txtID.setStyle(invalid);

            txtCustomerName.setText("");
            txtCustomerMobile.setText("");
            txtCustomerEmail.setText("");

        }
    }

   

    @FXML
    private void dtpReservedDateAP(ActionEvent event) {
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Operation-methods">
    @FXML
    private void btnDeleteAP(ActionEvent event) {
        String details
                = "\nName       \t: " + liquorbill.getReservationId().getCustomerId().getName()
                + "\nReserved date   \t: " + liquorbill.getReserveddate();

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
            LiquorBillDao.delete(oldLiquorbill);

            fillTable(LiquorBillDao.getAll());
            loadForm();

            pagination.setCurrentPageIndex(page);
            tblLiquorBill.getSelectionModel().select(row);

        }
    }

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
                    = "\nName       \t: " + liquorbill.getReservationId().getCustomerId().getName()
                    + "\nReserved date   \t: " + liquorbill.getReserveddate();

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
                LiquorBillDao.add(liquorbill);

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

        if (liquorbill.getReservationId().getCustomerId() == null) {
            errors = errors + "Name \t\tis Invalid\n";
        }

        return errors;

    }

    @FXML
    private void btnLiquorBillAP(ActionEvent event) {
        if (null != liquorbill.getId()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Liquor Bill");
            alert.setHeaderText("Liquor Bill");
            alert.setContentText("Are you sure you need to generate the Liquor bill?");

            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialogForConfirmation");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                if (null == liquorbill.getPaiddate()) {
                    liquorbill.setPaiddate(today);

                    LiquorBillDao.update(liquorbill);
                }

                reservarion.setLiquorbill(liquorbill.getTotalprice());
                ReservationDao.update(reservarion);
                
                HashMap hmap = new HashMap();

                try {
                    hmap.put("liquorbillid", liquorbill.getId());

                    new ReportView("/bills/liquorbill/report1.jasper", hmap);

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

    @FXML
    private void tblLiquorBillMC(MouseEvent event) {
        fillForm();
    }

    @FXML
    private void tblLiquorBillKR(KeyEvent event) {
        fillForm();
    }

    private void fillForm() {

        if (tblLiquorBill.getSelectionModel().getSelectedItem() != null) {

            dissableButtons(false, true, false, false);

            setStyle(valid);

            // cmbStatus.setDisable(false);
            txtSearchLiquorBillID.setStyle(initial);
            txtSearchName.setStyle(initial);

            oldLiquorbill = LiquorBillDao.getByIdWithHmap(tblLiquorBill.getSelectionModel().getSelectedItem().getId());
            liquorbill = LiquorBillDao.getByIdWithHmap(tblLiquorBill.getSelectionModel().getSelectedItem().getId());

            reservarion = oldLiquorbill.getReservationId();

            toFillList(liquorbill.getLiquoritemlistList());

            //  toFillList(restaurantbill.getRestaurantfooditemlistList());
            txtID.setText(oldLiquorbill.getReservationId().getCustomerId().getIdno());
            txtID.setEditable(false);
            txtCustomerName.setText(oldLiquorbill.getReservationId().getCustomerId().getName());
            txtCustomerEmail.setText(oldLiquorbill.getReservationId().getCustomerId().getEmail());
            txtCustomerMobile.setText(oldLiquorbill.getReservationId().getCustomerId().getMobile());

            dtpReservedDate.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(oldLiquorbill.getReserveddate())));
            dtpReservedDate.getEditor().setText(new SimpleDateFormat("yyyy-MM-dd").format(oldLiquorbill.getReserveddate()));

            lblLiquortBillID.setText(String.format("%06d", oldLiquorbill.getId()));
            lblCustomerID.setText(String.format("%06d", oldLiquorbill.getReservationId().getCustomerId().getId()));

            page = pagination.getCurrentPageIndex();
            row = tblLiquorBill.getSelectionModel().getSelectedIndex();

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
