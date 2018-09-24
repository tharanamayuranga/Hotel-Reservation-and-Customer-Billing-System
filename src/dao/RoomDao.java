/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Room;
import entity.Roomstatus;
import entity.Roomtype;
import java.util.HashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class RoomDao {
    
    public static ObservableList<Room> getAll() {
   
        return  CommonDao.select("Room.findAll");
    }



   

   
}
