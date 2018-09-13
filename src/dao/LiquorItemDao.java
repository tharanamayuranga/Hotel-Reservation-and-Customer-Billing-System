/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Liquoritem;
import entity.Liquoritemcategory;
import java.util.HashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class LiquorItemDao {
    
    public static ObservableList<Liquoritem> getAll() {
   
        return  CommonDao.select("Liquoritem.findAll");
    }

    public static void add(Liquoritem liquorItem) {
            CommonDao.insert(liquorItem); 

    }

    public static void delete(Liquoritem liquorItem) {
        CommonDao.delete(liquorItem);
    }

    public static void update(Liquoritem liquorItem) {
        CommonDao.update(liquorItem);
    }

    public static Liquoritem getById(Integer id) {
        HashMap hmap = new HashMap();
        hmap.put("id", id);

        return (Liquoritem) CommonDao.select("Liquoritem.findById", hmap).get(0);
    }

 
}
