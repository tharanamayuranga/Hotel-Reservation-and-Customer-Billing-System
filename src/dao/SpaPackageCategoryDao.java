/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Spapackagecategory;
import java.util.HashMap;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class SpaPackageCategoryDao {
    
    public static ObservableList<Spapackagecategory> getAll() {
   
        return  CommonDao.select("Spapackagecategory.findAll");
        
    }

    public static List getAllByName(String name) {
        
        HashMap hmap = new HashMap();
        hmap.put("name", name );
        
        return CommonDao.select("Spapackagecategory.findAllByName", hmap); 
    }

    public static void add(Spapackagecategory spaCategory) {
        CommonDao.insert(spaCategory);
    }

    public static void update(Spapackagecategory spaCategory) {
        CommonDao.update(spaCategory);

    }

    public static void delete(Spapackagecategory spaCategory) {
        CommonDao.delete(spaCategory);
    }

    public static Spapackagecategory getById(Integer id) {
         HashMap hmap = new HashMap();
        hmap.put("id", id);

        ObservableList<Spapackagecategory> list = CommonDao.select("Spapackagecategory.findById", hmap);
        
        if (list == null) {
            return null;
        } else {
            return list.get(0);
        }
    }

   
}
