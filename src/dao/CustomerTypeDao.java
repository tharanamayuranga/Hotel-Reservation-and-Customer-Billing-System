/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Customertype;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class CustomerTypeDao {
    
    public static ObservableList<Customertype> getAll() {
        
         return  CommonDao.select("Customertype.findAll");
         
    }
}
