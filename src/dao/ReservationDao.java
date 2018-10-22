/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Reservation;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class ReservationDao {
    
       public static ObservableList<Reservation> getAll() {
   
        return  CommonDao.select("Reservation.findAll");

    }
       
    public static Integer getLastReservationId() {
       
        ObservableList<Reservation> list = CommonDao.select("Reservation.getReservationId");
        
        if (list.isEmpty()) {
            
            return null;
            
        } else {
            
            return list.get(0).getId();
            
        }
    }
    
}
