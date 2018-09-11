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

    public static Customer getById(Integer id) {
        HashMap hmap = new HashMap();
        hmap.put("id", id);

        return (Customer) CommonDao.select("Customer.findById", hmap).get(0);
       
    }

    public static ObservableList<Customer> getAllByName(String name) {
        HashMap hmap = new HashMap();
        hmap.put("name", "%" + name + "%" );
        
//      System.out.println(hmap);

        return CommonDao.select("Customer.findAllByName", hmap);
        
    }

    public static ObservableList<Customer> getAllByCustomerType(Customertype customerType) {
        HashMap hmap = new HashMap();
        hmap.put("customertype", customerType);

        return CommonDao.select("Customer.findAllByCustomerType", hmap);
    }

    public static ObservableList<Customer> getAllById(String id) {
        HashMap hmap = new HashMap();
        hmap.put("idno", "%" + id + "%" );
        
//      System.out.println(hmap);

        return CommonDao.select("Customer.findAllById", hmap);
    }

    public static ObservableList<Customer> getAllByNameId(String name, String id) {
        HashMap hmap = new HashMap();
        hmap.put("name", "%" + name + "%" );
        hmap.put("idno", "%" + id + "%" );
//      System.out.println(hmap);

        return CommonDao.select("Customer.findAllByNameId", hmap);
    }




}
