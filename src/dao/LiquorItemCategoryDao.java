/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Liquoritemcategory;
import java.util.HashMap;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class LiquorItemCategoryDao {
    
         public static ObservableList<Liquoritemcategory> getAll() {
   
        return  CommonDao.select("Liquoritemcategory.findAll");

    }
        public static List getAllByName(String name) {
        
        HashMap hmap = new HashMap();
        hmap.put("name", name );

        return CommonDao.select("Liquoritemcategory.findAllByName", hmap);    
    }

    public static void add(Liquoritemcategory liquorCategory) {
            CommonDao.insert(liquorCategory);
    }

    public static void update(Liquoritemcategory liquorCategory) {
        CommonDao.update(liquorCategory);
    }

    public static void delete(Liquoritemcategory oldLiquorCategory) {
        CommonDao.delete(oldLiquorCategory);
    }
     public static Liquoritemcategory getById(Integer id) {
        
        HashMap hmap = new HashMap();
        hmap.put("id", id);

        ObservableList<Liquoritemcategory> list = CommonDao.select("Liquoritemcategory.findById", hmap);
        
        if (list == null) {
            return null;
        } else {
            return list.get(0);
        }
    }

}
