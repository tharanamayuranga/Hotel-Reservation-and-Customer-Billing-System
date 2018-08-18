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
    

}
