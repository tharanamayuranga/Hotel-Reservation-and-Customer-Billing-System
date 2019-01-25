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
