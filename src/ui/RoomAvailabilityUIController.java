/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import dao.RoomDao;
import udobject.MyObject;
import entity.Room;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class RoomAvailabilityUIController implements Initializable {

    @FXML
    private TableView<MyObject> tblEmployee;
    @FXML
    private TableColumn<MyObject, String> col1;
    @FXML
    private TableColumn<MyObject, String> col2;
    @FXML
    private TableColumn<MyObject, String> col3;
    @FXML
    private TableColumn<MyObject, String> col4;
    @FXML
    private TableColumn<MyObject, String> col5;
    @FXML
    private TableColumn<MyObject, String> col6;
    @FXML
    private TableColumn<MyObject, String> col7;
    @FXML
    private TableColumn<MyObject, String> col8;
    @FXML
    private TableColumn<MyObject, String> col9;
    @FXML
    private TableColumn<MyObject, String> col10;
    @FXML
    private TableColumn<MyObject, String> col11;
    @FXML
    private TableColumn<MyObject, String> col12;
    @FXML
    private TableColumn<MyObject, String> col13;
    @FXML
    private TableColumn<MyObject, String> col14;
    @FXML
    private TableColumn<MyObject, String> col15;
    @FXML
    private TableColumn<MyObject, String> col16;
    @FXML
    private TableColumn<MyObject, String> col17;
    @FXML
    private TableColumn<MyObject, String> col18;
    @FXML
    private TableColumn<MyObject, String> col19;
    @FXML
    private TableColumn<MyObject, String> col20;
    @FXML
    private TableColumn<MyObject, String> col21;
    @FXML
    private TableColumn<MyObject, String> col22;
    @FXML
    private TableColumn<MyObject, String> col23;
    @FXML
    private TableColumn<MyObject, String> col24;
    @FXML
    private TableColumn<MyObject, String> col25;
    @FXML
    private TableColumn<MyObject, String> col26;
    @FXML
    private TableColumn<MyObject, String> col27;
    @FXML
    private TableColumn<MyObject, String> col28;
    @FXML
    private TableColumn<MyObject, String> col29;
    @FXML
    private TableColumn<MyObject, String> col30;
    @FXML
    private TableColumn<MyObject, String> col31;
    @FXML
    private TableColumn<MyObject, Room> colRoom;
    @FXML
    private JFXDatePicker dtpSearchFrom;
    @FXML
    private JFXDatePicker dtpSearchTo;
    @FXML
    private JFXButton btnSearch;
    @FXML
    private JFXButton btnClear;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colRoom.setCellValueFactory(new PropertyValueFactory("room"));
        col1.setCellValueFactory(new PropertyValueFactory("one"));
        col2.setCellValueFactory(new PropertyValueFactory("two"));
        col3.setCellValueFactory(new PropertyValueFactory("three"));
        col4.setCellValueFactory(new PropertyValueFactory("four"));
        col5.setCellValueFactory(new PropertyValueFactory("five"));
        col6.setCellValueFactory(new PropertyValueFactory("six"));
        col7.setCellValueFactory(new PropertyValueFactory("seven"));
        col8.setCellValueFactory(new PropertyValueFactory("eight"));
        col9.setCellValueFactory(new PropertyValueFactory("nine"));
        col10.setCellValueFactory(new PropertyValueFactory("ten"));
        col11.setCellValueFactory(new PropertyValueFactory("eleven"));
        col12.setCellValueFactory(new PropertyValueFactory("twelve"));
        col13.setCellValueFactory(new PropertyValueFactory("thirteen"));
        col14.setCellValueFactory(new PropertyValueFactory("fourteen"));
        col15.setCellValueFactory(new PropertyValueFactory("fifteen"));
        col16.setCellValueFactory(new PropertyValueFactory("sixteen"));
        col17.setCellValueFactory(new PropertyValueFactory("seventeen"));
        col18.setCellValueFactory(new PropertyValueFactory("eighteen"));
        col19.setCellValueFactory(new PropertyValueFactory("nineteen"));
        col20.setCellValueFactory(new PropertyValueFactory("twenty"));
        col21.setCellValueFactory(new PropertyValueFactory("twentyone"));
        col22.setCellValueFactory(new PropertyValueFactory("twentytwo"));
        col23.setCellValueFactory(new PropertyValueFactory("twentythree"));
        col24.setCellValueFactory(new PropertyValueFactory("twentyfour"));
        col25.setCellValueFactory(new PropertyValueFactory("twentyfive"));
        col26.setCellValueFactory(new PropertyValueFactory("twentysix"));
        col27.setCellValueFactory(new PropertyValueFactory("twentyseven"));
        col28.setCellValueFactory(new PropertyValueFactory("twentyeight"));
        col29.setCellValueFactory(new PropertyValueFactory("twentynine"));
        col30.setCellValueFactory(new PropertyValueFactory("thirty"));
        col31.setCellValueFactory(new PropertyValueFactory("thirtyone"));
		
		
		 col1.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
		    col2.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
		 col3.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col4.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col5.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col6.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
		 col7.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col8.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col9.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col10.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col11.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
		  col12.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col13.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col14.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col15.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col16.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col17.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col18.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col19.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col20.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col21.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col22.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col23.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col24.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col25.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col26.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
		 col27.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col28.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col29.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col30.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
        col31.setCellFactory(new Callback<TableColumn<MyObject, String>, TableCell<MyObject, String>>() {
            @Override
            public TableCell<MyObject, String> call(TableColumn<MyObject, String> param) {

                return new TableCell<MyObject, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (null != item) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle(null);
                        }
                    }
                };
            }
        });
		
    private ObservableList<String> getListOfDays(String[] fromArray, String[] toArray) {
        ObservableList<String> outPut = FXCollections.observableArrayList();

        for (int i = 0; i < fromArray.length; i++) {
            int Low = Integer.parseInt(fromArray[i].trim());
            int High = Integer.parseInt(toArray[i].trim());
            int diff = High - Low;

            if (1 != diff) {
                for (int j = 0; j < diff - 1; j++) {
                    outPut.add(String.valueOf(Low + j + 1));
                }
            }

            outPut.add(fromArray[i]);
            outPut.add(toArray[i]);
        }

        return outPut;
    }

	    private void loadTable(String startData, String endDate) {
        ObservableList<MyObject> characters = FXCollections.observableArrayList();

        Connection connection = null; // To set hibernate connection before

        String location = "jdbc:mysql://localhost/hrh";
        String username = "root";
        String password = "1234";

        try {
            connection = DriverManager.getConnection(location, username, password);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        String query = "SELECT "
                + "group_concat(TRIM(LEADING \'0\' FROM if(DATE_FORMAT(r.arrival, \'%d\')>DATE_FORMAT('" + startData + "', \'%d\'),"
                + "DATE_FORMAT(r.arrival, \'%d\'),DATE_FORMAT('" + startData + "', \'%d\'))) separator \', \') as fromList, "
                + "group_concat(TRIM(LEADING \'0\' FROM if(DATE_FORMAT(r.departure, \'%d\')<DATE_FORMAT('" + endDate + "', \'%d\'),"
                + "DATE_FORMAT(r.departure, \'%d\'),DATE_FORMAT('" + endDate + "', \'%d\'))) separator \', \') as toList, "
                + "r.room_id as room "
                + "FROM reservation r "
                + "where r.reservationstatus_id = 1 and r.arrival between '" + startData + " 00:00:00' and '" + endDate + " 23:59:59' "
                + "group by r.room_id";
        

//        String query = "SELECT "
//                + "group_concat(TRIM(LEADING \'0\' FROM if(DATE_FORMAT(r.arrival, \'%d\')>DATE_FORMAT('" + "2018-11-08" + "', \'%d\'),DATE_FORMAT(r.arrival, \'%d\'),DATE_FORMAT('" + "2018-11-08" + "', \'%d\'))) separator \', \') as fromList, "
//                + "group_concat(TRIM(LEADING \'0\' FROM if(DATE_FORMAT(r.departure, \'%d\')<DATE_FORMAT('" + "2018-11-25" + "', \'%d\'),DATE_FORMAT(r.departure, \'%d\'),DATE_FORMAT('" + "2018-11-25" + "', \'%d\'))) separator \', \') as toList, "
//                + "r.room_id as room "
//                + "FROM reservation r "
//                + "where r.arrival between '" + "2018-11-08" + " 00:00:00' and '" + "2018-11-25" + " 23:59:59' "
//                + "group by r.room_id";
        try {
            PreparedStatement statement = connection.prepareStatement(query); //To defend mysql injection

            ResultSet results = statement.executeQuery();

            ObservableList<Room> romms = RoomDao.getAll();
            int rommListSize = romms.size();
            for (int i = 0; i < rommListSize; i++) {
                try {
                    if (results.next()) {
                        for (int y = 0; y < rommListSize; y++) {
                            if (results.getString("room").toString().equals(romms.get(y).getId().toString())) {
                                MyObject x = new MyObject();

                                x.setRoom((romms.get(y).getNo().toString()));

                                String[] fromArray = results.getString("fromList").split("\\,", -1);
                                String[] toArray = results.getString("toList").split("\\,", -1);

                                characters.add(setValuesForTheObject(x, getListOfDays(fromArray, toArray)));
                            }
                        }
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        tblEmployee.getItems().clear();
        tblEmployee.getItems().clear();
        tblEmployee.setItems(FXCollections.observableArrayList(characters));
    }

	}
   

   
}
