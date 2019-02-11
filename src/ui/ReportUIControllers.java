/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import report.ReportView;
import static ui.LiquorBillUIControllers.liquorbill;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class ReportUIControllers implements Initializable {

    @FXML
    private JFXButton btnGuestReport;
    @FXML
    private JFXButton btnEmployeeReport;
    @FXML
    private JFXButton btnReservationReport;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnGuestReportAP(ActionEvent event) {
        try {

            new ReportView("/reports/customer/AllCustomer.jasper");

        } catch (Exception e) {

            System.out.println("JobDetailsGetByJobId Report error.....!!");

        }
    }

    @FXML
    private void btnEmployeeReportAP(ActionEvent event) {
         try {

            new ReportView("/reports/employee/Allemployees.jasper");

        } catch (Exception e) {

            System.out.println("JobDetailsGetByJobId Report error.....!!");

        }
    }

    @FXML
    private void btnReservationReportAp(ActionEvent event) {
           try {

            new ReportView("/reports/employee/Allemployees.jasper");

        } catch (Exception e) {

            System.out.println("JobDetailsGetByJobId Report error.....!!");

        }
    }

}
