/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Roomtype;
import java.util.HashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class RoomTypeDao {
    public static ObservableList<Roomtype> getAll() {
   
        return  CommonDao.select("Roomtype.findAll");
    }



    
}
