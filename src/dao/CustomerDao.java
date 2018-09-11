/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Customer;
import entity.Customertype;
import java.util.HashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class CustomerDao {

    public static Integer getLastJobId() {
        
         ObservableList<Customer> list = CommonDao.select("Customer.getCustomerId");
        
        if (list.isEmpty()) {
            
            return null;
            
        } else {
            
            return list.get(0).getId();
            
        }

    }
    public static ObservableList<Customer> getAll() {
   
        return  CommonDao.select("Customer.findAll");

    }

    public static void update(Customer customer) {
       
        CommonDao.update(customer);
        
    }

    public static void add(Customer customer) {
        
        CommonDao.insert(customer);
        
    }




}
