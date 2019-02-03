/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Customerpackage;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class CustomerPackageDao {
    
        public static ObservableList<Customerpackage> getAll() {
   
        return  CommonDao.select("Customerpackage.findAll");

    }
}
