/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Liquorbill;
import entity.Liquoritemlist;
import entity.Restaurantbill;
import java.util.HashMap;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class LiquoritemlistDao {

    public static List<Liquoritemlist> getAllByLiquorbillId(Liquorbill liquorbill) {
        HashMap hmap = new HashMap();
        hmap.put("liquorbillId", liquorbill);

        return CommonDao.select("Liquoritemlist.findByLiquorbillId", hmap);
    }

    public static void delete(Liquoritemlist liquoritemlist) {
        CommonDao.delete(liquoritemlist);
    }

    public static void deleteWithInnerObject(Liquoritemlist liquoritemlist) {
        CommonDao.deleteWithInnerObject(liquoritemlist);
    }

    public static void deleteSameSession(Liquoritemlist liquoritemlist) {
        CommonDao.deleteSameSession(liquoritemlist);
    }

    public static void insert(Liquoritemlist tmpRFIL) {
        CommonDao.insert(tmpRFIL);
    }

    public static void insertWhenTwoOrMoreObjectsWithRemoveInstance(Liquoritemlist tmpRFIL) {
        CommonDao.insertWhenTwoOrMoreObjectsWithRemoveInstance(tmpRFIL);
    }

    public static void updateWhenTwoOrMoreObject(Liquoritemlist tmpRFIL) {
        CommonDao.updateWhenTwoOrMoreObject(tmpRFIL);
    }

    public static void insertSameSession(Liquoritemlist tmpRFIL) {
        CommonDao.insertSameSession(tmpRFIL);
    }

    public static Liquoritemlist getById(Integer id) {
        HashMap hmap = new HashMap();
        hmap.put("id", id);

        ObservableList<Liquoritemlist> list = CommonDao.select("Liquoritemlist.findById", hmap);

        if (list == null) {
            return null;
        } else {
            return list.get(0);
        }
    }

}
