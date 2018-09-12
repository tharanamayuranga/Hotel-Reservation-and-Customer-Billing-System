/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Module;
import entity.Role;
import java.util.HashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class ModuleDao {
    
     public static ObservableList<Module> getAll() {
        
        return  CommonDao.select("Module.findAll");
        
    }
      public static ObservableList<Module> getAllUnassignedToRole(Role roleId) {
         
        HashMap hmap = new HashMap();
        hmap.put("roleId", roleId );
        
        return  CommonDao.select("Module.findByAllUnassignedToRole" , hmap );
        
    }
}
