/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Restaurantbill;
import entity.Restaurantfooditemlist;
import java.util.HashMap;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class RestaurantfooditemlistDao {

    public static ObservableList<Restaurantfooditemlist> getAll() {
        return CommonDao.select("Restaurantfooditemlist.findAll");
    }

    public static void add(Restaurantfooditemlist restaurantfooditemlist) {
        CommonDao.insert(restaurantfooditemlist);
    }

    public static List<Restaurantfooditemlist> getAllByRestaurantbillId(Restaurantbill restaurantbill) {
        HashMap hmap = new HashMap();
        hmap.put("restaurantbillId", restaurantbill);

       return CommonDao.select("Restaurantfooditemlist.findByRestaurantbillId", hmap);
    }

    public static void delete(Restaurantfooditemlist restaurantfooditemlist) {
        CommonDao.delete(restaurantfooditemlist);
    }
    
}
