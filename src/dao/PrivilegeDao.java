/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Module;
import entity.Privilege;
import entity.Role;
import java.util.HashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class PrivilegeDao {
    
    public static ObservableList<Privilege> getAll() {     
        
        return CommonDao.select("Privilege.findAll");
        
    }
    
    public static ObservableList<Privilege> getAllPrivilege() {     
        
        return CommonDao.selectAllPrivilege("Privilege.findAll");
        
    }


}
