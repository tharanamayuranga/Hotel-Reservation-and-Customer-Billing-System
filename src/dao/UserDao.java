/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Role;
import entity.User;
import java.util.HashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class UserDao {
    
    public static ObservableList getAll() {
        
        return CommonDao.select("User.findAll");
    }
    
    public static ObservableList getUnassignedUsername( String username ) {
       
        HashMap hmap = new HashMap();
        hmap.put("username", username);

        return CommonDao.select("User.findUnassignedUsername", hmap);
        
    }
        public static void add(User user) {
       
        CommonDao.insert(user);
    }

    public static void update(User user) {
        CommonDao.update(user);
    }

    public static User getById(Integer id) {
        HashMap hmap = new HashMap();
        hmap.put("id", id);

        ObservableList<User> list = CommonDao.select("User.findById", hmap);
        
        if (list == null) {
            return null;
        } else {
            return list.get(0);
        }
    }


}
