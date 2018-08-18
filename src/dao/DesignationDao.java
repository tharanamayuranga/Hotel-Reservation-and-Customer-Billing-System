/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Civilstatus;
import entity.Designation;
import java.util.HashMap;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class DesignationDao {
    
    public static ObservableList<Designation> getAll() {
        
         return  CommonDao.select("Designation.findAll");
         
    }

    public static List getAllByName(String name) {
        
        HashMap hmap = new HashMap();
        hmap.put("name", name );

        return CommonDao.select("Designation.findAllByName", hmap);    
    }

    public static void add(Designation designation) {
        CommonDao.insert(designation);

    }

    public static void delete(Designation oldDesignation) {
        CommonDao.delete(oldDesignation);
    }

    public static Designation getById(Integer id) {
        
        HashMap hmap = new HashMap();
        hmap.put("id", id);

        ObservableList<Designation> list = CommonDao.select("Designation.findById", hmap);
        
        if (list == null) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public static void update(Designation designation) {
        CommonDao.update(designation);
    }
}
