/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.FoodItemDao;
import dao.LiquorBillDao;
import dao.LiquorItemDao;
import dao.LiquoritemlistDao;
import dao.RestaurantBillDao;
import entity.Fooditem;
import entity.Liquorbill;
import entity.Liquoritem;
import entity.Liquoritemlist;
import entity.Restaurantbill;
import entity.Restaurantfooditemlist;
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
import static ui.LiquorBillUIControllers.liquorItemListUIStage;
import static ui.LiquorBillUIControllers.liquorbill;
import static ui.RestaurantBillUIControllers.restaurantbill;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class LiquorItemListUIController implements Initializable {

    @FXML
    private ListView<Liquoritem> lstLiquorItems;
    @FXML
    private ScrollPane scpSelectedLiquorItems;
    @FXML
    private Label lblTotalPrice;
    @FXML
    private JFXTextField txtSearchLiquorItems;
    @FXML
    private JFXButton btnAddLiquorItems;

    Liquorbill liquorItems;
    Liquorbill oldiquorItems;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        liquorItems = liquorbill;
        oldiquorItems = null;

        initialFilterItemListViwe();

        toFillList(liquorItems.getLiquoritemlistList());
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

            lable.setText(innertableList.get(i).getLiquoritemId().getName());//set liquor item name
            lable.setId(String.valueOf(i));//set the id

            Label lableUnitPize = new Label(innertableList.get(i).getLiquoritemId().getUnitprice().toString());
            lableUnitPize.setId(String.valueOf(i));

            TextField textField = new TextField(innertableList.get(i).getQty().toString());
            textField.setId(String.valueOf(i));
            textField.setMaxSize(50, 10);

            UnaryOperator unaryOperatorFortxtQuantity = new UnaryOperator<TextFormatter.Change>() {
                @Override
                public TextFormatter.Change apply(TextFormatter.Change t) {//check is it number?
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
            lablePrice.setText(String.valueOf(innertableList.get(i).getLiquoritemId().getUnitprice().multiply(new BigDecimal(innertableList.get(i).getQty()))));
            lablePrice.setId(String.valueOf(i));

            Button delete = new Button("Delete");
            delete.setId(String.valueOf(i));

            gridPane.add(lable, 0, i);//add to grid pane
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

                    lstLiquorItems.getItems().addAll(FXCollections.observableArrayList(innertableList.get(x).getLiquoritemId()));

                    innertableList.remove(x);

                    toFillList(innertableList);
                }
            });
        }

        BigDecimal totalCost = BigDecimal.ZERO;

        for (int i = 0; i < innertableList.size(); i++) {
            totalCost = totalCost.add(innertableList.get(i).getLiquoritemId().getUnitprice().multiply(new BigDecimal(innertableList.get(i).getQty())));
        }

        lblTotalPrice.setText(totalCost.toString());
    }

    @FXML
    private void lstItemsMC(MouseEvent event) {
        if (2 == event.getClickCount()) {
            if (null != lstLiquorItems.getSelectionModel().getSelectedItem()) {
                Liquoritemlist newInner = new Liquoritemlist();

                newInner.setLiquoritemId(lstLiquorItems.getSelectionModel().getSelectedItem());
                newInner.setQty(1);

                liquorItems.getLiquoritemlistList().add(newInner);

                toFillList(liquorItems.getLiquoritemlistList());

                lstLiquorItems.getItems().removeAll(lstLiquorItems.getSelectionModel().getSelectedItems());
            }
        }
    }

    @FXML
    private void txtSearchLiquorItemsKR(KeyEvent event) {
        
        String jobId = txtSearchLiquorItems.getText().trim();
        boolean sjobId = !jobId.isEmpty();

        ObservableList<Liquoritem> allItems = FXCollections.observableArrayList();
        ObservableList<Liquoritem> setItems = FXCollections.observableArrayList();
        ObservableList<Liquoritemlist> addedItems = FXCollections.observableArrayList();
//        ObservableList<Restaurantfooditemlist> addedItems = (ObservableList<Restaurantfooditemlist>) foodItems.getRestaurantfooditemlistList();

        for (int i = 0; i < liquorItems.getLiquoritemlistList().size(); i++) {
            addedItems.add(liquorItems.getLiquoritemlistList().get(i));
        }

        if (sjobId) {
            allItems = LiquorItemDao.getAllByName(jobId);
            setItems = LiquorItemDao.getAllByName(jobId);
        } else {
            allItems = LiquorItemDao.getAll();
            setItems = LiquorItemDao.getAll();
        }

        for (int i = 0; i < allItems.size(); i++) {
            for (int j = 0; j < addedItems.size(); j++) {
                if (addedItems.get(j).getLiquoritemId().getId() == allItems.get(i).getId()) {
                    setItems.remove(allItems.get(i));
                }
            }
        }

        if (0 != addedItems.size()) {
            lstLiquorItems.getItems().clear();
            lstLiquorItems.getItems().addAll(setItems);
        } else {
            lstLiquorItems.getItems().clear();
            lstLiquorItems.getItems().addAll(allItems);
        }
    }

    @FXML
    private void btnAddLiquorItemsAP(ActionEvent event) {
                // get all
        List<Liquoritemlist> allList = LiquoritemlistDao.getAllByLiquorbillId(liquorbill);

        //delete all
        for (int i = 0; i < allList.size(); i++) {
            LiquoritemlistDao.deleteSameSession(allList.get(i));
        }

        // add all
        for (int i = 0; i < liquorItems.getLiquoritemlistList().size(); i++) {
            Liquoritemlist tmpRFIL = new Liquoritemlist();

            tmpRFIL.setLiquoritemId(liquorItems.getLiquoritemlistList().get(i).getLiquoritemId());
            tmpRFIL.setQty(liquorItems.getLiquoritemlistList().get(i).getQty());
            tmpRFIL.setAmount(liquorItems.getLiquoritemlistList().get(i).getLiquoritemId().getUnitprice().multiply(new BigDecimal(liquorItems.getLiquoritemlistList().get(i).getQty())));
            tmpRFIL.setLiquorbillId(liquorbill);

            LiquoritemlistDao.insertSameSession(tmpRFIL);
        }
        
        // update total price
        Liquorbill liq = LiquorBillDao.getById(liquorbill.getId());
        liq.setTotalprice(new BigDecimal(lblTotalPrice.getText()));
        LiquorBillDao.update(liq);
        
        liquorItemListUIStage.close();
    }

    private void initialFilterItemListViwe() {
//        ObservableList<Restaurantfooditemlist> addedItems = (ObservableList<Restaurantfooditemlist>) foodItems.getRestaurantfooditemlistList();
        ObservableList<Liquoritemlist> addedItems = FXCollections.observableArrayList();
        ObservableList<Liquoritem> allItems = LiquorItemDao.getAllLiquoritem();
        ObservableList<Liquoritem> setItems = LiquorItemDao.getAllLiquoritem();

        for (int i = 0; i < liquorItems.getLiquoritemlistList().size(); i++) {
            addedItems.add(liquorItems.getLiquoritemlistList().get(i));
        }

        for (int i = 0; i < allItems.size(); i++) {
            for (int j = 0; j < addedItems.size(); j++) {
                if (addedItems.get(j).getLiquoritemId().getId() == allItems.get(i).getId()) {
                    setItems.remove(allItems.get(i));
                }
            }
        }

        if (0 != addedItems.size()) {
            lstLiquorItems.getItems().clear();
            lstLiquorItems.getItems().addAll(setItems);
        } else {
            lstLiquorItems.getItems().clear();
            lstLiquorItems.getItems().addAll(allItems);
        }
    }
}
