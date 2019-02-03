/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Liquorbill;
import entity.Liquoritemlist;
import java.util.HashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class LiquorBillDao {

    public static Integer getLastLiquorBillId() {
        ObservableList<Liquorbill> list = CommonDao.select("Liquorbill.getLiquorBillId");

        if (list.isEmpty()) {

            return null;

        } else {

            return list.get(0).getId();

        }
    }

    public static ObservableList<Liquorbill> getAllLiquorBill() {
        return CommonDao.selectAllLiquorBill("Liquorbill.findAll");
    }

    public static void delete(Liquorbill oldLiquorbill) {
        CommonDao.delete(oldLiquorbill);
    }

    public static ObservableList<Liquorbill> getAll() {
        return CommonDao.select("Liquorbill.findAll");
    }

    public static void add(Liquorbill liquorbill) {
        CommonDao.insert(liquorbill);
    }
    

    public static Liquorbill getByIdWithHmap(Integer id) {
        HashMap hmap = new HashMap();
        hmap.put("id", id);

        ObservableList<Liquorbill> list = CommonDao.selectAllLiquorBillwithHmap("Liquorbill.findById", hmap);

        if (list == null) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public static void updateWhenTwoOrMoreObject(Liquorbill liquorbill) {
        CommonDao.updateWhenTwoOrMoreObject(liquorbill);
    }

    public static void updateSameSession(Liquorbill liquorbill) {
        CommonDao.updateSameSession(liquorbill);
    }

    public static Liquorbill getById(Integer id) {
        HashMap hmap = new HashMap();
        hmap.put("id", id);

        ObservableList<Liquorbill> list = CommonDao.select("Liquorbill.findById", hmap);

        if (list == null) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public static void update(Liquorbill liq) {
        CommonDao.update(liq);
    }

}
