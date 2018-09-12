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

    public static ObservableList<User> getAllByName(String employeename) {
        HashMap hmap = new HashMap();
        hmap.put("employeename", "%" + employeename + "%");

        return CommonDao.select("User.findAllByEmployeeName", hmap);
    }

    public static ObservableList<User> getAllByUser(String username) {
        HashMap hmap = new HashMap();
        hmap.put("username", "%" + username + "%");

        return CommonDao.select("User.findAllByUsername", hmap);
    
    }

    public static ObservableList<User> getAllByRole(Role role) {
        HashMap hmap = new HashMap();
        
        hmap.put("role", role.getId() ); 
        
        return CommonDao.select("User.findAllByRole", hmap);    
    }

    public static ObservableList<User> getAllByNameRole(String employeename, Role role) {
        HashMap hmap = new HashMap();
        
        hmap.put("role", role.getId() );
        hmap.put("employeename", employeename + "%");
        
        
        return CommonDao.select("User.findAllByNameRole", hmap);
    }

    public static ObservableList<User> getAllByNameUsername(String employeename, String username) {
        HashMap hmap = new HashMap();
        
        hmap.put("username",  "%" + username + "%");
        hmap.put("employeename", employeename + "%");

        return CommonDao.select("User.findAllByNameUsername", hmap);
    }

    public static ObservableList<User> getAllByRoleUsername(Role role, String username) {
        HashMap hmap = new HashMap();
        hmap.put("username",  "%" + username + "%");
        hmap.put("role", role.getId() );
        
        return CommonDao.select("User.findAllByRoleUsername", hmap);
    }

    public static ObservableList<User> getAllByNameRoleUsername(String employeename, Role role, String username) {
        HashMap hmap = new HashMap();
        hmap.put("username",  "%" + username + "%");
        hmap.put("employeename", employeename + "%");
        hmap.put("role", role.getId() );
        
        return CommonDao.select("User.findAllByNameRoleUsername", hmap);
        
    }
}
