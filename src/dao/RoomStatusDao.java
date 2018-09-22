/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Roomstatus;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class RoomStatusDao {
    
     public static ObservableList<Roomstatus> getAll() {
   
        return  CommonDao.select("Roomstatus.findAll");
    }
}
