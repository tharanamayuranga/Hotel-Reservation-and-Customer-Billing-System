/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Spapackage;
import entity.Spapackagecategory;
import java.util.HashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class SpaPackageDao {
    
    public static ObservableList<Spapackage> getAll() {
   
        return  CommonDao.select("Spapackage.findAll");
    }
    public static void add(Spapackage spaPackage) {
            CommonDao.insert(spaPackage); 
 
    }

    public static void delete(Spapackage spaPackage) {
         CommonDao.delete(spaPackage); 

    }

    public static void update(Spapackage spaPackage) {
        CommonDao.update(spaPackage);
    }

    public static Spapackage getById(Integer id) {
        HashMap hmap = new HashMap();
        hmap.put("id", id);

        return (Spapackage) CommonDao.select("Spapackage.findById", hmap).get(0);
    }
     public static ObservableList<Spapackage> getAllByName(String name) {
        HashMap hmap = new HashMap();
        hmap.put("name", "%" + name + "%" );
        
//        System.out.println(hmap);

        return CommonDao.select("Spapackage.findAllByName", hmap);
    }

    public static ObservableList<Spapackage> getAllByCategory(Spapackagecategory category) {
        HashMap hmap = new HashMap();
        hmap.put("category", category);

        return CommonDao.select("Spapackage.findAllByCategory", hmap);
    }

    public static ObservableList<Spapackage> getAllByCode(String code) {
        HashMap hmap = new HashMap();
        hmap.put("code","%"+ code + "%");

        return CommonDao.select("Spapackage.findAllByCode", hmap);
    
    }

    public static ObservableList<Spapackage> getAllByNameCode(String name, String code) {
        HashMap hmap = new HashMap();
        hmap.put("code", code + "%");
        hmap.put("name" , name + "%");
        return CommonDao.select("Spapackage.findAllByNameCode", hmap);
    }

    public static ObservableList<Spapackage> getAllByNameCategory(String name, Spapackagecategory category) {
         HashMap hmap = new HashMap();
        hmap.put("category", category);
        hmap.put("name" , name + "%");
        return CommonDao.select("Spapackage.findAllByNameCategory", hmap);
    }

    public static ObservableList<Spapackage> getAllByCodeCategory(String code, Spapackagecategory category) {
        HashMap hmap = new HashMap();
        hmap.put("category", category);
        hmap.put("code", code + "%");
        return CommonDao.select("Spapackage.findAllByCodeCategory", hmap);
    

    }

    public static ObservableList<Spapackage> getAllByNameCodeCategory(String name, String code, Spapackagecategory category) {
        HashMap hmap = new HashMap();
        hmap.put("category", category);
        hmap.put("code", code + "%");
        hmap.put("name" , name + "%");
        return CommonDao.select("Spapackage.findAllByNameCodeCategory", hmap);
        
    }
}
