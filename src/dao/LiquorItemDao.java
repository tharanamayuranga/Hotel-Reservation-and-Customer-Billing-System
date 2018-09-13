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

   
    public static ObservableList<Liquoritem> getAllByName(String name) {
        HashMap hmap = new HashMap();
        hmap.put("name", "%" + name + "%" );
        
//        System.out.println(hmap);

        return CommonDao.select("Liquoritem.findAllByName", hmap);
    }

    public static ObservableList<Liquoritem> getAllByCategory(Liquoritemcategory category) {
        HashMap hmap = new HashMap();
        hmap.put("category", category);

        return CommonDao.select("Liquoritem.findAllByCategory", hmap);
    }

    public static ObservableList<Liquoritem> getAllByCode(String code) {
        HashMap hmap = new HashMap();
        hmap.put("code","%"+ code + "%");

        return CommonDao.select("Liquoritem.findAllByCode", hmap);
    
    }

    public static ObservableList<Liquoritem> getAllByNameCode(String name, String code) {
        HashMap hmap = new HashMap();
        hmap.put("code", code + "%");
        hmap.put("name" , name + "%");
        return CommonDao.select("Liquoritem.findAllByNameCode", hmap);
    }

    public static ObservableList<Liquoritem> getAllByNameCategory(String name, Liquoritemcategory category) {
         HashMap hmap = new HashMap();
        hmap.put("category", category);
        hmap.put("name" , name + "%");
        return CommonDao.select("Liquoritem.findAllByNameCategory", hmap);
    }

    public static ObservableList<Liquoritem> getAllByCodeCategory(String code, Liquoritemcategory category) {
        HashMap hmap = new HashMap();
        hmap.put("category", category);
        hmap.put("code", code + "%");
        return CommonDao.select("Liquoritem.findAllByCodeCategory", hmap);
    

    }

    public static ObservableList<Liquoritem> getAllByNameCodeCategory(String name, String code, Liquoritemcategory category) {
        HashMap hmap = new HashMap();
        hmap.put("category", category);
        hmap.put("code", code + "%");
        hmap.put("name" , name + "%");
        return CommonDao.select("Liquoritem.findAllByNameCodeCategory", hmap);
        
    }
}
