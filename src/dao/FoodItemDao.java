/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Fooditem;
import entity.Fooditemcategory;
import java.util.HashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class FoodItemDao {
    public static ObservableList<Fooditem> getAll() {
   
        return  CommonDao.select("Fooditem.findAll");
    }

    public static void add(Fooditem foodItem) {
        
         CommonDao.insert(foodItem);
    }

    public static void update(Fooditem foodItem) {
        CommonDao.update(foodItem);
    }

    public static void delete(Fooditem foodItem) {
        CommonDao.delete(foodItem);
    }

    public static Fooditem getById(Integer id) {
         
        HashMap hmap = new HashMap();
        hmap.put("id", id);

        return (Fooditem) CommonDao.select("Fooditem.findById", hmap).get(0);
       
    }

    public static ObservableList<Fooditem> getAllByName(String name) {
        HashMap hmap = new HashMap();
        hmap.put("name", "%" + name + "%" );
        
//        System.out.println(hmap);

        return CommonDao.select("Fooditem.findAllByName", hmap);
    }

    public static ObservableList<Fooditem> getAllByCategory(Fooditemcategory category) {
        HashMap hmap = new HashMap();
        hmap.put("category", category);

        return CommonDao.select("Fooditem.findAllByCategory", hmap);
    }

    public static ObservableList<Fooditem> getAllByCode(String code) {
        HashMap hmap = new HashMap();
        hmap.put("code","%"+ code + "%");

        return CommonDao.select("Fooditem.findAllByCode", hmap);
    
    }


}
