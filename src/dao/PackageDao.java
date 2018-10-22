/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javafx.collections.ObservableList;
//import entity.Package;


/**
 *
 * @author Tharana
 */
public class PackageDao {
    
        public static ObservableList<Package> getAll() {
        
         return  CommonDao.select("Package.findAll");
         
    }
}
