/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Spabill;
import entity.Spapackagelist;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Tharana
 */
public class SpaPackagelistDao {

    public static List<Spapackagelist> getAllBySpaBillId(Spabill spaBill) {
        HashMap hmap = new HashMap();
        hmap.put("spabillId", spaBill);

        return CommonDao.select("Spapackagelist.findBySpabillId", hmap);
    }

    public static void delete(Spapackagelist spapackagelist) {
        CommonDao.delete(spapackagelist);
    }

    public static void deleteWithInnerObject(Spapackagelist spapackagelist) {
        CommonDao.deleteWithInnerObject(spapackagelist);
    }

    public static void deleteSameSession(Spapackagelist spapackagelist) {
        CommonDao.deleteSameSession(spapackagelist);
    }

    public static void insert(Spapackagelist tmpRFIL) {
        CommonDao.insert(tmpRFIL);
    }

    public static void insertWhenTwoOrMoreObjectsWithRemoveInstance(Spapackagelist tmpRFIL) {
        CommonDao.insertWhenTwoOrMoreObjectsWithRemoveInstance(tmpRFIL);
    }

    public static void insertSameSession(Spapackagelist tmpRFIL) {
        CommonDao.insertSameSession(tmpRFIL);
    }

}
