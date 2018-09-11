/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Idtype;
import java.util.HashMap;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class IDTypeDao {
    public static ObservableList<Idtype> getAll() {
        
         return  CommonDao.select("Idtype.findAll");
         
    }
        public static void add(Idtype idType) {
         CommonDao.insert(idType);
    }

    public static void update(Idtype idType) {
         CommonDao.update(idType);
    }

    public static void delete(Idtype oldIdType) {
         CommonDao.delete(oldIdType);
    }
    public static List getAllByName(String name) {
         HashMap hmap = new HashMap();
        hmap.put("name", name );

        return CommonDao.select("Idtype.findAllByName", hmap);    
    }



    public static Idtype getById(Integer id) {
        HashMap hmap = new HashMap();
        hmap.put("id", id);

        ObservableList<Idtype> list = CommonDao.select("Idtype.findById", hmap);
        
        if (list == null) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
