/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.FoodItemDao;
import dao.RestaurantBillDao;
import dao.RestaurantfooditemlistDao;
import entity.Fooditem;
import entity.Restaurantbill;
import entity.Restaurantfooditemlist;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
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
import static ui.RestaurantBillUIControllers.restauranatFoodItemListUIStage;
import static ui.RestaurantBillUIControllers.restaurantbill;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class RestauranatFoodItemListUIController implements Initializable {

    Restaurantbill foodItems;
    Restaurantbill oldFoodItems;

    @FXML
    private ListView<Fooditem> lstFoodItems;
    @FXML
    private ScrollPane scpSelectedFoodItems;
    @FXML
    private Label lblTotalPrice;
    @FXML
    private JFXTextField txtSearchFoodItems;
    @FXML
    private JFXButton btnAddFoodItems;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        foodItems = restaurantbill;
        oldFoodItems = null;

        initialFilterItemListViwe();

//        lstFoodItems.setItems(FoodItemDao.getAll());
        toFillList(foodItems.getRestaurantfooditemlistList());
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
            lablePrice.setText(String.valueOf(innertableList.get(i).getFooditemId().getUnitprice().multiply(new BigDecimal(innertableList.get(i).getQty()))));
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

                    lstFoodItems.getItems().addAll(FXCollections.observableArrayList(innertableList.get(x).getFooditemId()));

                    innertableList.remove(x);

                    toFillList(innertableList);
                }
            });
        }

        BigDecimal totalCost = BigDecimal.ZERO;

        for (int i = 0; i < innertableList.size(); i++) {
            totalCost = totalCost.add(innertableList.get(i).getFooditemId().getUnitprice().multiply(new BigDecimal(innertableList.get(i).getQty())));
        }

        lblTotalPrice.setText(totalCost.toString());
    }

    @FXML
    private void lstItemsMC(MouseEvent event) {
        if (2 == event.getClickCount()) {
            if (null != lstFoodItems.getSelectionModel().getSelectedItem()) {
                Restaurantfooditemlist newInner = new Restaurantfooditemlist();

                newInner.setFooditemId(lstFoodItems.getSelectionModel().getSelectedItem());
                newInner.setQty(1);

                foodItems.getRestaurantfooditemlistList().add(newInner);

                toFillList(foodItems.getRestaurantfooditemlistList());

                lstFoodItems.getItems().removeAll(lstFoodItems.getSelectionModel().getSelectedItems());
            }
        }
    }

    @FXML
    private void txtSearchFoodItemsKR(KeyEvent event) {
        String jobId = txtSearchFoodItems.getText().trim();
        boolean sjobId = !jobId.isEmpty();

        ObservableList<Fooditem> allItems = FXCollections.observableArrayList();
        ObservableList<Fooditem> setItems = FXCollections.observableArrayList();
        ObservableList<Restaurantfooditemlist> addedItems = FXCollections.observableArrayList();
//        ObservableList<Restaurantfooditemlist> addedItems = (ObservableList<Restaurantfooditemlist>) foodItems.getRestaurantfooditemlistList();

        for (int i = 0; i < foodItems.getRestaurantfooditemlistList().size(); i++) {
            addedItems.add(foodItems.getRestaurantfooditemlistList().get(i));
        }

        if (sjobId) {
            allItems = FoodItemDao.getAllByName(jobId);
            setItems = FoodItemDao.getAllByName(jobId);
        } else {
            allItems = FoodItemDao.getAll();
            setItems = FoodItemDao.getAll();
        }

        for (int i = 0; i < allItems.size(); i++) {
            for (int j = 0; j < addedItems.size(); j++) {
                if (addedItems.get(j).getFooditemId().getId() == allItems.get(i).getId()) {
                    setItems.remove(allItems.get(i));
                }
            }
        }

        if (0 != addedItems.size()) {
            lstFoodItems.getItems().clear();
            lstFoodItems.getItems().addAll(setItems);
        } else {
            lstFoodItems.getItems().clear();
            lstFoodItems.getItems().addAll(allItems);
        }
    }

    @FXML
    private void btnAddFoodItemsAP(ActionEvent event) {
        // get all
        List<Restaurantfooditemlist> allList = RestaurantfooditemlistDao.getAllByRestaurantbillId(restaurantbill);

        //delete all
        for (int i = 0; i < allList.size(); i++) {
            RestaurantfooditemlistDao.delete(allList.get(i));
        }

        // add all
        for (int i = 0; i < foodItems.getRestaurantfooditemlistList().size(); i++) {
            Restaurantfooditemlist tmpRFIL = new Restaurantfooditemlist();

            tmpRFIL.setFooditemId(foodItems.getRestaurantfooditemlistList().get(i).getFooditemId());
            tmpRFIL.setQty(foodItems.getRestaurantfooditemlistList().get(i).getQty());
            tmpRFIL.setAmount(foodItems.getRestaurantfooditemlistList().get(i).getFooditemId().getUnitprice().multiply(new BigDecimal(foodItems.getRestaurantfooditemlistList().get(i).getQty())));
            tmpRFIL.setRestaurantbillId(restaurantbill);

            RestaurantfooditemlistDao.add(tmpRFIL);
        }
        
        // update total price
        Restaurantbill res = RestaurantBillDao.getById(restaurantbill.getId());
        res.setTotalprice(new BigDecimal(lblTotalPrice.getText()));
        RestaurantBillDao.update(res);

        restauranatFoodItemListUIStage.close();
    }

    private void initialFilterItemListViwe() {
//        ObservableList<Restaurantfooditemlist> addedItems = (ObservableList<Restaurantfooditemlist>) foodItems.getRestaurantfooditemlistList();
        ObservableList<Restaurantfooditemlist> addedItems = FXCollections.observableArrayList();
        ObservableList<Fooditem> allItems = FoodItemDao.getAll();
        ObservableList<Fooditem> setItems = FoodItemDao.getAll();

        for (int i = 0; i < foodItems.getRestaurantfooditemlistList().size(); i++) {
            addedItems.add(foodItems.getRestaurantfooditemlistList().get(i));
        }

        for (int i = 0; i < allItems.size(); i++) {
            for (int j = 0; j < addedItems.size(); j++) {
                if (addedItems.get(j).getFooditemId().getId() == allItems.get(i).getId()) {
                    setItems.remove(allItems.get(i));
                }
            }
        }

        if (0 != addedItems.size()) {
            lstFoodItems.getItems().clear();
            lstFoodItems.getItems().addAll(setItems);
        } else {
            lstFoodItems.getItems().clear();
            lstFoodItems.getItems().addAll(allItems);
        }
    }
}
