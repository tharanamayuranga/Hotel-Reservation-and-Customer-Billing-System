/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class MainWindowController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="FXML-Data">
    @FXML
    private AnchorPane apnMainwindows;
    @FXML
    private SplitPane sptMain;
    @FXML
    private AnchorPane apnLeft;
    @FXML
    private ImageView imgUser;
    @FXML
    private Label lblUser;
    @FXML
    private ToggleButton btnHome;
    @FXML
    private ToggleButton mtmUser;
    @FXML
    private ToggleGroup tglAdminTasks;
    @FXML
    private ToggleButton mtmPrivilage;
    @FXML
    private ToggleButton mtmEmployee;
    @FXML
    private ToggleButton mtmServices;
    @FXML
    private ToggleButton mtmMenuItem;
    @FXML
    private ToggleButton mtmFunctionHall;
    @FXML
    private ToggleButton mtmKitchenItem;
    @FXML
    private ToggleButton mtmKitchenItem1;
    @FXML
    private ToggleGroup tglAdminTasks1;
    @FXML
    private ToggleButton mtmKitchenItem11;
    @FXML
    private ToggleGroup tglAdminTasks11;
    @FXML
    private ToggleButton mtmGuestManagement;
    @FXML
    private ToggleGroup tglBusinessTasks;
    @FXML
    private ToggleButton mtmFunctionBooking;
    @FXML
    private ToggleButton mtmMenuManagement;
    @FXML
    private ToggleButton mtmPayemet;
    @FXML
    private ToggleButton mtmKitchenItemRequest;
    @FXML
    private ToggleButton mtmKitchenItemRequest1;
    @FXML
    private ToggleGroup tglBusinessTasks2;
    @FXML
    private ToggleButton mtmKitchenItemRequest11;
    @FXML
    private ToggleGroup tglBusinessTasks21;
    @FXML
    private ToggleButton mtmKitchenItemRequest111;
    @FXML
    private ToggleGroup tglBusinessTasks211;
    @FXML
    private ToggleButton mtmKitchenItemRequest112;
    @FXML
    private ToggleGroup tglBusinessTasks212;
    @FXML
    private ToggleButton btnReportManagement;
    @FXML
    private ToggleGroup tglBusinessTasks1;
    @FXML
    private AnchorPane apnRight;
    @FXML
    private Pane imgDashBoard;
    @FXML
    private ToggleButton tglGuestMgt;
    @FXML
    private ToggleButton tglMenuCustom;
    @FXML
    private ToggleButton tglFunctionBooking;
    @FXML
    private ToggleButton tglKitchenRequest;
    @FXML
    private ToggleButton tglPayement;
    @FXML
    private ToggleButton tglReporting;
    @FXML
    private JFXButton btnSignout;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Initializing-Methods">
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="UI-Loading-Methods">
    @FXML
    private void btnHomeAP(ActionEvent event) {
        apnRight.getChildren().clear();
        apnRight.getChildren().add(imgDashBoard);
    }
    
    @FXML
    private void mtmUserAP(ActionEvent event) {
        
    }
    
    @FXML
    private void mtmPrivilageAP(ActionEvent event) {
    }
    
    @FXML
    private void mtmEmployeeAP(ActionEvent event) {
		 try {
            AnchorPane root = FXMLLoader.load(Main.class.getResource("EmployeeUI.fxml"));
            apnRight.getChildren().clear();
            apnRight.getChildren().add(root);
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    @FXML
    private void mtmServicesAP(ActionEvent event) {
    }
    
    @FXML
    private void mtmMenuItemAP(ActionEvent event) {
    }
    
    @FXML
    private void mtmFunctionHallAP(ActionEvent event) {
    }
    
    @FXML
    private void mtmKitchenItemAP(ActionEvent event) {
        
    }
    
    @FXML
    private void mtmGuestManagementAP(ActionEvent event) {
    }
    
    @FXML
    private void mtmFunctionBookingAP(ActionEvent event) {
    }
    
    @FXML
    private void mtmMenuManagementAP(ActionEvent event) {
    }
    
    @FXML
    private void mtmPayemetAP(ActionEvent event) {
    }
    
    @FXML
    private void mtmKitchenItemRequestAP(ActionEvent event) {
    }
    
    @FXML
    private void btnReportManagementAP(ActionEvent event) {
    }
    
    @FXML
    private void tglGuestMgtAP(ActionEvent event) {
    }
    
    @FXML
    private void tglMenuCustomAP(ActionEvent event) {
    }
    
    @FXML
    private void tglFunctionBookingAP(ActionEvent event) {
    }
    
    @FXML
    private void tglKitchenRequestAP(ActionEvent event) {
    }
    
    @FXML
    private void tglPayementAP(ActionEvent event) {
    }
    
    @FXML
    private void tglReportingAP(ActionEvent event) {
    }
    
    @FXML
    private void btnSignoutAP(ActionEvent event) {
    }
//</editor-fold>
    
}
