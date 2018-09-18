/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Floor;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class FloorDao {
    
     public static ObservableList<Floor> getAll() {
   
        return  CommonDao.select("Floor.findAll");
    }
}
