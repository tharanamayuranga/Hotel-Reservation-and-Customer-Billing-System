/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Room;
import entity.Roomstatus;
import entity.Roomtype;
import java.util.HashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class RoomDao {
    
    public static ObservableList<Room> getAll() {
   
        return  CommonDao.select("Room.findAll");
    }

    public static void add(Room room) {
         CommonDao.insert(room);
    }

    public static Room getById(Integer id) {
        HashMap hmap = new HashMap();
        hmap.put("id", id);

        return (Room) CommonDao.select("Room.findById", hmap).get(0);
    }

    public static void update(Room room) {
         CommonDao.update(room);
    }

    public static void delete(Room room) {
         CommonDao.delete(room);
    }

    public static ObservableList<Room> getAllByNo(String no) {
         HashMap hmap = new HashMap();
        hmap.put("no", "%" + no + "%" );
        
//        System.out.println(hmap);

        return CommonDao.select("Room.findAllByNo", hmap);
    }

    public static ObservableList<Room> getAllByType(Roomtype type) {
        HashMap hmap = new HashMap();
        hmap.put("type", type);

        return CommonDao.select("Room.findAllByType", hmap);
    }

    public static ObservableList<Room> getAllByStatus(Roomstatus status) {
        HashMap hmap = new HashMap();
        hmap.put("status", status);

        return CommonDao.select("Room.findAllByStatus", hmap);
    }

    public static ObservableList<Room> getAllByNoStatus(String no, Roomstatus status) {
        HashMap hmap = new HashMap();
        hmap.put("no", "%" + no + "%" );
        hmap.put("status", status);

        return CommonDao.select("Room.findAllByNoStatus", hmap);
    }

    public static ObservableList<Room> getAllByNoType(String no, Roomtype type) {
        HashMap hmap = new HashMap();
        hmap.put("no", "%" + no + "%" );
        hmap.put("type", type);

        return CommonDao.select("Room.findAllByNoType", hmap);
    }

    public static ObservableList<Room> getAllByStatusType(Roomstatus status, Roomtype type) {
         HashMap hmap = new HashMap();
        hmap.put("status", status);
        hmap.put("type", type);

        return CommonDao.select("Room.findAllByStatusType", hmap);
    }

    public static ObservableList<Room> getAllByNoStatusType(String no, Roomstatus status, Roomtype type) {
         HashMap hmap = new HashMap();
         hmap.put("no", "%" + no + "%" );
        hmap.put("status", status);
        hmap.put("type", type);

        return CommonDao.select("Room.findAllByNoStatusType", hmap);
    }

   

   
}
