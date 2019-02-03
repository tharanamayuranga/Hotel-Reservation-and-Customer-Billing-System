/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Paymenttype;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class PaymentTypeDao {
    
    public static ObservableList<Paymenttype> getAll() {

        return CommonDao.select("Paymenttype.findAll");

    }
}
