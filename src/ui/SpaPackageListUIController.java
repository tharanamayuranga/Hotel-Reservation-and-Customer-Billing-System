/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.LiquorBillDao;
import dao.SpaBillDao;
import dao.SpaPackageDao;
import dao.SpaPackagelistDao;
import entity.Liquorbill;
import entity.Spabill;
import entity.Spapackage;
import entity.Spapackagelist;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import static ui.LiquorBillUIControllers.liquorbill;
import static ui.SpaBillUIControllers.spaBill;
import static ui.SpaBillUIControllers.spaPackageListUIStage;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class SpaPackageListUIController implements Initializable {

    Spabill spaPackages;
    Spabill oldSpaPackages;

    @FXML
    private ListView<Spapackage> lstSpaPackages;
    @FXML
    private ScrollPane scpSelectedSpaPackages;
    @FXML
    private Label lblTotalPrice;
    @FXML
    private JFXTextField txtSearchSpapackages;
    @FXML
    private JFXButton btnAddSpaPackages;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        spaPackages = spaBill;
        oldSpaPackages = null;

        initialFilterItemListViwe();

//        lstFoodItems.setItems(FoodItemDao.getAll());
        toFillList(spaPackages.getSpapackagelistList());
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

            UnaryOperator unaryOperatorFortxtQuantity = new UnaryOperator<TextFormatter.Change>() {
                @Override
                public TextFormatter.Change apply(TextFormatter.Change t) {
                    for (int i = 0; i < t.getText().length(); i++) {
                        if (t.getText().substring(0, i + 1).matches("\\d")) {
                            return t;
                        } else {
                            return null;
                        }
                    }

                    return t;
                }
            };

            TextFormatter textFormatterFortxtQuantity = new TextFormatter<String>(unaryOperatorFortxtQuantity);

            textField.setTextFormatter(textFormatterFortxtQuantity);

            Label lablePrice = new Label("");
            lablePrice.setText(String.valueOf(innertableList.get(i).getSpapackageId().getPackageprice().multiply(new BigDecimal(innertableList.get(i).getQty()))));
            lablePrice.setId(String.valueOf(i));

            Button delete = new Button("Delete");
            delete.setId(String.valueOf(i));

            gridPane.add(lable, 0, i);
            gridPane.add(lableUnitPize, 4, i);
            gridPane.add(textField, 8, i);
            gridPane.add(lablePrice, 12, i);
            gridPane.add(delete, 16, i);

            textField.setOnKeyReleased((event) -> {
                int y = Integer.valueOf(textField.getId());

                if (!textField.getText().trim().equals("") && textField.getText().trim().matches("[^0]{1}\\d*")) {
                    innertableList.get(y).setQty(new Integer(textField.getText().trim()));
                } else {
                    innertableList.get(y).setQty(1);
                }

                toFillList(innertableList);
            });

            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int x = Integer.valueOf(delete.getId());

                    lstSpaPackages.getItems().addAll(FXCollections.observableArrayList(innertableList.get(x).getSpapackageId()));

                    innertableList.remove(x);

                    toFillList(innertableList);
                }
            });
        }

        BigDecimal totalCost = BigDecimal.ZERO;

        for (int i = 0; i < innertableList.size(); i++) {
            totalCost = totalCost.add(innertableList.get(i).getSpapackageId().getPackageprice().multiply(new BigDecimal(innertableList.get(i).getQty())));
        }

        lblTotalPrice.setText(totalCost.toString());
    }

    @FXML
    private void lstISpaPackagesMC(MouseEvent event) {
        if (2 == event.getClickCount()) {
            if (null != lstSpaPackages.getSelectionModel().getSelectedItem()) {

                Spapackagelist newInner = new Spapackagelist();

                newInner.setSpapackageId(lstSpaPackages.getSelectionModel().getSelectedItem());
                newInner.setQty(1);

                spaPackages.getSpapackagelistList().add(newInner);

                toFillList(spaPackages.getSpapackagelistList());

                lstSpaPackages.getItems().removeAll(lstSpaPackages.getSelectionModel().getSelectedItems());
            }
        }
    }

    @FXML
    private void txtSearchSpapackagesKR(KeyEvent event) {
        String jobId = txtSearchSpapackages.getText().trim();
        boolean sjobId = !jobId.isEmpty();
        ObservableList<Spapackage> allItems = FXCollections.observableArrayList();
        ObservableList<Spapackage> setItems = FXCollections.observableArrayList();
        ObservableList<Spapackagelist> addedItems = FXCollections.observableArrayList();
//        ObservableList<Restaurantfooditemlist> addedItems = (ObservableList<Restaurantfooditemlist>) foodItems.getRestaurantfooditemlistList();
        for (int i = 0; i < spaPackages.getSpapackagelistList().size(); i++) {
            addedItems.add(spaPackages.getSpapackagelistList().get(i));
        }
        if (sjobId) {
            allItems = SpaPackageDao.getAllByName(jobId);
            setItems = SpaPackageDao.getAllByName(jobId);
        } else {
            allItems = SpaPackageDao.getAll();
            setItems = SpaPackageDao.getAll();
        }
        for (int i = 0; i < allItems.size(); i++) {
            for (int j = 0; j < addedItems.size(); j++) {
                if (addedItems.get(j).getSpapackageId().getId() == allItems.get(i).getId()) {
                    setItems.remove(allItems.get(i));
                }
            }
        }
        if (0 != addedItems.size()) {
            lstSpaPackages.getItems().clear();
            lstSpaPackages.getItems().addAll(setItems);
        } else {
            lstSpaPackages.getItems().clear();
            lstSpaPackages.getItems().addAll(allItems);
        }
    }

    @FXML
    private void btnAddSpaPackagesAP(ActionEvent event) {
        // get all
        List<Spapackagelist> allList = SpaPackagelistDao.getAllBySpaBillId(spaBill);

        //delete all
        for (int i = 0; i < allList.size(); i++) {
            SpaPackagelistDao.deleteSameSession(allList.get(i));
        }

        // add all
        for (int i = 0; i < spaPackages.getSpapackagelistList().size(); i++) {
            Spapackagelist tmpRFIL = new Spapackagelist();

            tmpRFIL.setSpapackageId(spaPackages.getSpapackagelistList().get(i).getSpapackageId());
            tmpRFIL.setQty(spaPackages.getSpapackagelistList().get(i).getQty());
            tmpRFIL.setAmount(spaPackages.getSpapackagelistList().get(i).getSpapackageId().getPackageprice().multiply(new BigDecimal(spaPackages.getSpapackagelistList().get(i).getQty())));
            tmpRFIL.setSpabillId(spaBill);

            SpaPackagelistDao.insertSameSession(tmpRFIL);
        }

        // update total price
        Spabill spa = SpaBillDao.getById(spaBill.getId());
        spa.setTotalprice(new BigDecimal(lblTotalPrice.getText()));
        SpaBillDao.update(spa);
        
        spaPackageListUIStage.close();
    }

    private void initialFilterItemListViwe() {
//        ObservableList<Restaurantfooditemlist> addedItems = (ObservableList<Restaurantfooditemlist>) foodItems.getRestaurantfooditemlistList();
        ObservableList<Spapackagelist> addedItems = FXCollections.observableArrayList();
        ObservableList<Spapackage> allItems = SpaPackageDao.getAll();
        ObservableList<Spapackage> setItems = SpaPackageDao.getAll();

        for (int i = 0; i < spaPackages.getSpapackagelistList().size(); i++) {
            addedItems.add(spaPackages.getSpapackagelistList().get(i));
        }

        for (int i = 0; i < allItems.size(); i++) {
            for (int j = 0; j < addedItems.size(); j++) {
                if (addedItems.get(j).getSpapackageId().getId() == allItems.get(i).getId()) {
                    setItems.remove(allItems.get(i));
                }
            }
        }

        if (0 != addedItems.size()) {
            lstSpaPackages.getItems().clear();
            lstSpaPackages.getItems().addAll(setItems);
        } else {
            lstSpaPackages.getItems().clear();
            lstSpaPackages.getItems().addAll(allItems);
        }
    }

}
