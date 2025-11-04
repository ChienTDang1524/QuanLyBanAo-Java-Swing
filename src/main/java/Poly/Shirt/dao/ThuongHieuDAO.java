/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Poly.Shirt.dao;

/**
 *
 * @author trinh
 */


import Poly.Shirt.Enity.ThuongHieu;
import java.util.List;

public interface ThuongHieuDAO extends CrudDAO<ThuongHieu, Integer> {
    String getTenThuongHieuByID(int id);
    int getIdByName(String tenThuongHieu);
}