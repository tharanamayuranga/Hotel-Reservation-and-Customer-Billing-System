/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Designation;
import entity.Employee;
import entity.Employeestatus;
import java.util.HashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class EmployeeDao {
    
    public static ObservableList<Employee> getAll() {
   
        return  CommonDao.select("Employee.findAll");

    }
    
    public static void add(Employee employee) {
        
        CommonDao.insert(employee);
    }
    
    public static Integer getLastJobId() {
       
        ObservableList<Employee> list = CommonDao.select("Employee.getEmployeeId");
        
        if (list.isEmpty()) {
            
            return null;
            
        } else {
            
            return list.get(0).getId();
            
        }
        
    }
    
    public static Employee getById(Integer id) {
        
        HashMap hmap = new HashMap();
        hmap.put("id", id);

        ObservableList<Employee> list = CommonDao.select("Employee.findById", hmap);
        
        if (list == null) {
            return null;
        } else {
            return list.get(0);
        }
        
    }
    
    public static void update(Employee employee) {
        
        CommonDao.update(employee);
        
    }


}
