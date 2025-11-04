/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Poly.Shirt.dao;

/**
 *
 * @author trinh
 */


import Poly.Shirt.Enity.MauSac;
import java.util.List;

public interface MauSacDAO extends CrudDAO<MauSac, Integer> {
    String getTenMauByID(int id);
    int getIdByName(String tenMau);
}