/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Reservationstatus;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class ReservationStatusDao {
    
          public static ObservableList<Reservationstatus> getAll() {
        
         return  CommonDao.select("Reservationstatus.findAll");
         
    }
}
