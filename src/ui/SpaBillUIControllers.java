/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dao.ReservationDao;
import dao.RestaurantBillDao;
import dao.SpaBillDao;
import entity.Customer;
import entity.Reservation;
import entity.Spabill;
import entity.Spapackagelist;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
import static ui.Main.stageBasic;
import static ui.RestaurantBillUIControllers.restauranatFoodItemListUIStage;
import static ui.RestaurantBillUIControllers.restaurantbill;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class SpaBillUIControllers implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="Form-Data">
    public static Spabill spaBill;
    Spabill oldSpaBill;

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

    public static Stage spaPackageListUIStage;

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
    private TableView<Spabill> tblSpaBill;
    @FXML
    private TableColumn<Spabill, String> colReservationID;
    @FXML
    private TableColumn<Spabill, Customer> colGuestName;
    @FXML
    private TableColumn<Spabill, String> colSpaBillID;
    @FXML
    private TableColumn<Spabill, String> colTotalaPrice;
    @FXML
    private TableColumn<Spabill, String> colReservedDate;
    @FXML
    private JFXTextField txtSearchName;
    @FXML
    private Label lblEmployeeID1;
    @FXML
    private JFXDatePicker dtpReservedDate;
    @FXML
    private ScrollPane scpSelectedSpaPackages;
    @FXML
    private Label lblTotalPrice;
    @FXML
    private Button btnSpaBill;
    @FXML
    private Label lblSpaBillID;
    @FXML
    private JFXTextField txtSearchSpaBillID;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Initializing-Mehods">
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

        spaBill = new Spabill();
        oldSpaBill = null;

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
        spaBill.setReserveddate(assign);

        if (SpaBillDao.getLastSpaBillId() != null) {

            lblSpaBillID.setText(String.format("%06d", SpaBillDao.getLastSpaBillId() + 1));

        } else {

            lblSpaBillID.setText(String.format("%06d", 1));

        }

        dissableButtons(false, false, true, true);

//        toFillList(FXCollections.observableArrayList());
        setStyle(initial);
    }

    private void toFillList(List<Spapackagelist> innertableList) {
        GridPane gridPane = new GridPane();

        gridPane.setPadding(new Insets(5, 5, 5, 5));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        scpSelectedSpaPackages.setContent(gridPane);
        scpSelectedSpaPackages.setPannable(true);

        DropShadow dsForText = new DropShadow(20, Color.GREEN);

        for (int i = 0; i < innertableList.size(); i++) {
            Label lable = new Label("");

            lable.setText(innertableList.get(i).getSpapackageId().getName());
            lable.setId(String.valueOf(i));

            Label lableUnitPize = new Label(innertableList.get(i).getSpapackageId().getPackageprice().toString());
            lableUnitPize.setId(String.valueOf(i));

            TextField textField = new TextField(innertableList.get(i).getQty().toString());

            textField.setId(String.valueOf(i));
            textField.setMaxSize(50, 10);
            textField.setEditable(false);

            Label lablePrice = new Label("");
            lablePrice.setText(String.valueOf(innertableList.get(i).getSpapackageId().getPackageprice().multiply(new BigDecimal(innertableList.get(i).getQty()))));
            lablePrice.setId(String.valueOf(i));

            gridPane.add(lable, 0, i);
            gridPane.add(lableUnitPize, 5, i);
            gridPane.add(textField, 10, i);
            gridPane.add(lablePrice, 15, i);
        }

        BigDecimal totalCost = BigDecimal.ZERO;

        for (int i = 0; i < innertableList.size(); i++) {
            totalCost = totalCost.add(innertableList.get(i).getSpapackageId().getPackageprice().multiply(new BigDecimal(innertableList.get(i).getQty())));
        }

        lblTotalPrice.setText(totalCost.toString());
    }

    private void setStyle(String style) {

        txtID.setStyle(style);
        dtpReservedDate.getEditor().setStyle(style);
        txtSearchName.setStyle(style);
        txtSearchSpaBillID.setStyle(style);

    }

    private void dissableButtons(boolean select, boolean insert, boolean update, boolean delete) {

        btnAdd.setDisable(insert);
        btnDelete.setDisable(delete);

    }

    private void loadTable() {

        tblSpaBill.setRowFactory(tv -> {
            TableRow<Spabill> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        // todayCheckInReservation = row.getItem();
                        Parent root = FXMLLoader.load(getClass().getResource("SpaPackageListUI.fxml"));

                        Scene scene = new Scene(root);

                        spaPackageListUIStage = new Stage();
                        spaPackageListUIStage.setScene(scene);

                        spaPackageListUIStage.setOnCloseRequest(e -> {

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                            alert.setTitle(" WINDOW CLOSE");
                            alert.setHeaderText("Close Form");
                            alert.setContentText("Are you sure you need to close this form?? ");

                            DialogPane dialogPane = alert.getDialogPane();

                            dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
                            dialogPane.getStyleClass().add("myDialogForConfirmation");

                            Optional<ButtonType> result = alert.showAndWait();

                            if (result.get() == ButtonType.OK) {

                                spaPackageListUIStage.close();

                            }

                            e.consume();

                        });

                        spaPackageListUIStage.setOnHiding(e -> {

                            tblSpaBill.getSelectionModel().clearSelection();

                            fillTable(SpaBillDao.getAllSpaBill());

                            toFillList(FXCollections.observableArrayList());

                        });

                        spaPackageListUIStage.initModality(Modality.WINDOW_MODAL);
                        spaPackageListUIStage.initOwner(stageBasic);
                        spaPackageListUIStage.setResizable(false);
                        spaPackageListUIStage.setTitle("Oder and Payment Management System");
                        spaPackageListUIStage.show();

                    } catch (IOException ex) {
                        Logger.getLogger(SpaBillUIControllers.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            return row;
        });

        txtSearchName.setText("");
        txtSearchSpaBillID.setText("");

        colSpaBillID.setCellValueFactory(new PropertyValueFactory("id"));
        colReservationID.setCellValueFactory(new PropertyValueFactory("reservationId"));
        colReservedDate.setCellValueFactory(new PropertyValueFactory("reserveddate"));
        colTotalaPrice.setCellValueFactory(new PropertyValueFactory("totalprice"));

        colGuestName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Spabill, Customer>, ObservableValue<Customer>>() {
            @Override
            public ObservableValue<Customer> call(TableColumn.CellDataFeatures<Spabill, Customer> param) {
                return new SimpleObjectProperty(param.getValue().getReservationId().getCustomerId().getName());
            }
        });

        row = 0;
        page = 0;

        fillTable(SpaBillDao.getAllSpaBill());

        pagination.setCurrentPageIndex(0);

    }

    private void fillTable(ObservableList<Spabill> reservation) {

        if (reservation != null && !reservation.isEmpty()) {

            int rowsCount = 5;
            int pageCount = ((reservation.size() - 1) / rowsCount) + 1;
            pagination.setPageCount(pageCount);

            pagination.setPageFactory((Integer pageIndex) -> {
                int start = pageIndex * rowsCount;
                int end = pageIndex == pageCount - 1 ? reservation.size() : pageIndex * rowsCount + rowsCount;
                tblSpaBill.getItems().clear();
                tblSpaBill.setItems(FXCollections.observableArrayList(reservation.subList(start, end)));
                return tblSpaBill;
            });

        } else {

            pagination.setPageCount(1);
            tblSpaBill.getItems().clear();

        }

        pagination.setCurrentPageIndex(page);
        tblSpaBill.getSelectionModel().select(row);

    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Binding-Methods">
    @FXML
    private void dtpReservedDateAP(ActionEvent event) {
    }

    @FXML
    private void txtIDKR(KeyEvent event) {
        if (!(txtID.getText().trim().isEmpty()) && txtID.getText().trim().matches("\\d{9}[V|v|x|X]")) {

            Reservation reservarion = ReservationDao.getAllByCustomerID(txtID.getText().trim());

            if (reservarion != null) {

                spaBill.setReservationId(reservarion);

                txtID.setStyle(valid);

                toFillCustomerDetails(reservarion);

            } else {

                spaBill.setReservationId(null);

                txtID.setStyle(invalid);

                txtCustomerName.setText("");
                txtCustomerMobile.setText("");
                txtCustomerEmail.setText("");

            }

        } else {

            spaBill.setReservationId(null);

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
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Operation-Methods">
    @FXML
    private void btnDeleteAP(ActionEvent event) {
        String details
                = "\nName       \t: " + spaBill.getReservationId().getCustomerId().getName()
                + "\nReserved date   \t: " + spaBill.getReserveddate();

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
            SpaBillDao.delete(oldSpaBill);

            fillTable(SpaBillDao.getAll());
            loadForm();

            pagination.setCurrentPageIndex(page);
            tblSpaBill.getSelectionModel().select(row);

        }
    }

    @FXML
    private void btnClearAP(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialogForConfirmation");

        alert.setTitle(" Spa Bill");
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
                    = "\nName       \t: " + spaBill.getReservationId().getCustomerId().getName()
                    + "\nReserved date   \t: " + spaBill.getReserveddate();

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
                SpaBillDao.add(spaBill);

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

        if (spaBill.getReservationId().getCustomerId() == null) {
            errors = errors + "Name \t\tis Invalid\n";
        }

        return errors;

    }

    @FXML
    private void tblSpaBillMC(MouseEvent event) {
        fillForm();
    }

    @FXML
    private void tblSpaBillKR(KeyEvent event) {
        fillForm();
    }

    private void fillForm() {

        if (tblSpaBill.getSelectionModel().getSelectedItem() != null) {

            dissableButtons(false, true, false, false);

            setStyle(valid);

            // cmbStatus.setDisable(false);
            txtSearchSpaBillID.setStyle(initial);
            txtSearchName.setStyle(initial);

            oldSpaBill = SpaBillDao.getByIdWithHmap(tblSpaBill.getSelectionModel().getSelectedItem().getId());
            spaBill = SpaBillDao.getByIdWithHmap(tblSpaBill.getSelectionModel().getSelectedItem().getId());

            toFillList(spaBill.getSpapackagelistList());

            txtID.setText(oldSpaBill.getReservationId().getCustomerId().getIdno());
            txtID.setEditable(false);
            txtCustomerName.setText(oldSpaBill.getReservationId().getCustomerId().getName());
            txtCustomerEmail.setText(oldSpaBill.getReservationId().getCustomerId().getEmail());
            txtCustomerMobile.setText(oldSpaBill.getReservationId().getCustomerId().getMobile());

            dtpReservedDate.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(oldSpaBill.getReserveddate())));
            dtpReservedDate.getEditor().setText(new SimpleDateFormat("yyyy-MM-dd").format(oldSpaBill.getReserveddate()));

            lblSpaBillID.setText(String.format("%06d", oldSpaBill.getId()));
            lblCustomerID.setText(String.format("%06d", oldSpaBill.getReservationId().getCustomerId().getId()));

            page = pagination.getCurrentPageIndex();
            row = tblSpaBill.getSelectionModel().getSelectedIndex();

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

    @FXML
    private void btnSpaBillAP(ActionEvent event) {
        
    }

}
