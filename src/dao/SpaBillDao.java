/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Liquorbill;
import entity.Spabill;
import java.util.HashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class SpaBillDao {

    public static Integer getLastSpaBillId() {
        ObservableList<Spabill> list = CommonDao.select("Spabill.getSpaBillId");

        if (list.isEmpty()) {

            return null;

        } else {

            return list.get(0).getId();

        }
    }

    public static ObservableList<Spabill> getAllSpaBill() {
        return CommonDao.selectAllSpaBill("Spabill.findAll");
    }

    public static void add(Spabill spaBill) {
        CommonDao.insert(spaBill);
    }

    public static ObservableList<Spabill> getAll() {
        return CommonDao.select("Spabill.findAll");
    }

    public static void delete(Spabill oldSpaBill) {
        CommonDao.delete(oldSpaBill);
    }

    public static Spabill getByIdWithHmap(Integer id) {
        HashMap hmap = new HashMap();
        hmap.put("id", id);

        ObservableList<Spabill> list = CommonDao.selectAllSpaBillwithHmap("Spabill.findById", hmap);

        if (list == null) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public static Spabill getById(Integer id) {
        HashMap hmap = new HashMap();
        hmap.put("id", id);

        ObservableList<Spabill> list = CommonDao.select("Spabill.findById", hmap);

        if (list == null) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public static void update(Spabill spa) {
        CommonDao.update(spa);
    }

}
