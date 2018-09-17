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
    

    //<editor-fold defaultstate="collapsed" desc="Initializing-Methods">
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        attempt = 2;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Operation-Methods">
    @FXML
    private void btnCancelAP(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Oder and Payment Management");
        alert.setHeaderText("Cancel");
        alert.setContentText("Do you want to cancel?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            System.exit(3);

        }
    }

    @FXML
    private void btnLoginAP(ActionEvent event) throws IOException, SQLException {
        signIn();
    }

    private void signIn() throws IOException, SQLException {
        if (txtUsername.getText().equals("admin") && pswPassword.getText().equals("123")) {

            privilege = new HashMap<String, Boolean>();

            user = new User();

            Employee emp = new Employee();
            emp.setName("Admin");
            emp.setId(1); // Because there is no id for Admin..
//            emp.getEmployeestatusId().setId(3);

            user.setEmployeeId(emp);
            user.setUsername("Admin");

            ObservableList<Module> x = ModuleDao.getAll();

            for (Module module : x) {
                privilege.put(module.getName() + "_select", true);
                privilege.put(module.getName() + "_insert", true);
                privilege.put(module.getName() + "_update", true);
                privilege.put(module.getName() + "_delete", true);
            }

        } else {

            if (attempt == 0) {
                System.exit(1);
            } else {

                Connection connection = null;

                String location = "jdbc:mysql://localhost/hrh";
                String username = "root";
                String password = "1234";
                try {
                    connection = DriverManager.getConnection(location, username, password);
                } catch (SQLException ex) {
                    lblMessage.setText("Could not connect with the Database");
                }

                String query = "SELECT * FROM user WHERE username =? AND password = ?";

                try {

                    PreparedStatement statement = connection.prepareStatement(query);
                    System.out.println(statement);
                    statement.setString(1, txtUsername.getText());
                    statement.setString(2, Security.encrypt(pswPassword.getText()));
                    ResultSet results = statement.executeQuery();
                    
                   System.out.println(results);
//                    System.out.println(results.next());
                   System.out.println(statement);
                    
                    if (results.next()) {

                        user = UserDao.getById(results.getInt("id"));//id column name(auto increment id)
                        
                        //System.out.println(results.getString(2));
                        
                        privilege = new HashMap<String, Boolean>();

                        ObservableList<Module> x = ModuleDao.getAll();

                        for (Module module : x) {

                            privilege.put(module.getName() + "_select", false);
                            privilege.put(module.getName() + "_insert", false);
                            privilege.put(module.getName() + "_update", false);
                            privilege.put(module.getName() + "_delete", false);

                        }

                        ObservableList<Privilege> privileges = PrivilegeDao.getAllByUser(user);//new query for login
                        
//                        System.out.println(privileges);
                        
                        for (Privilege privi : privileges) {

                            String moduleName = privi.getModuleId().getName();

                            if (privi.getSel() == 1) {
                                if (!privilege.get(moduleName + "_select")) {
                                    privilege.put(moduleName + "_select", true);
                                }
                            }

                            if (privi.getIns() == 1) {
                                if (!privilege.get(moduleName + "_insert")) {
                                    privilege.put(moduleName + "_insert", true);
                                }
                            }

                            if (privi.getUpd() == 1) {
                                if (!privilege.get(moduleName + "_update")) {
                                    privilege.put(moduleName + "_update", true);
                                }
                            }

                            if (privi.getDel() == 1) {
                                if (!privilege.get(moduleName + "_delete")) {
                                    privilege.put(moduleName + "_delete", true);
                                }
                            }

                        }

                        statement.close();

                    } else {
                        lblAttempt.setText("Login Faild. You have " + (attempt--) + " more attemts.");
                        pswPassword.setText("");
                    }
                } catch (SQLException ex) {
                    lblMessage.setText("Could not get the User data from the Database");
                }

            }
        }

        if (privilege != null) {

            try {

                loginStage = (Stage) btnLogin.getScene().getWindow();
                loginStage.close();

                AnchorPane root = FXMLLoader.load(getClass().getResource("MainWindowUI.fxml"));
                Scene scene = new Scene(root);

                stageBasic = new Stage();

                stageBasic.setScene(scene);
                stageBasic.setResizable(false);
                stageBasic.show();
            } catch (IOException ex) {
                lblAttempt.setText("Could not load the MainWindow");
            }
        }
    }
    
     @FXML
    private void lblForgetPasswordMC(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ForgetPasswordUI.fxml"));
        
        Scene scene = new Scene(root);
        
        forgetPassword = new Stage();
        forgetPassword.setScene(scene);
        
        forgetPassword.initModality(Modality.APPLICATION_MODAL);
//        forgetPassword.initOwner(loginStage);
        forgetPassword.setResizable(false);
        Image ico = new Image("image/confirm.jpg");
        forgetPassword.getIcons().add(ico);
        forgetPassword.setTitle("Oder and Payment Management System");
        forgetPassword.show();
    }
//</editor-fold>

   
}
