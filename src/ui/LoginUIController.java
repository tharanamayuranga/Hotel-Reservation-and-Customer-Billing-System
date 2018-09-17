/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dao.ModuleDao;
import dao.PrivilegeDao;
import dao.UserDao;
import entity.Employee;
import entity.Module;
import entity.Privilege;
import entity.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static ui.Main.stageBasic;
import util.Security;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class LoginUIController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="Form Data">
    private int attempt;
    public static User user;
    public static HashMap<String, Boolean> privilege;

    Stage loginStage;
    Stage forgetPassword;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FXML-Data">
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private Label lblAttempt;
    @FXML
    private JFXPasswordField pswPassword;
    @FXML
    private Label lblMessage;
    @FXML
    private Label lblForgetPassword;
//</editor-fold>
    


   
}
