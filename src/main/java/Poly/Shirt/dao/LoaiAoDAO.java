/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Poly.Shirt.dao;

/**
 *
 * @author trinh
 */

import Poly.Shirt.Enity.LoaiAo;
import java.util.List;

public interface LoaiAoDAO extends CrudDAO<LoaiAo, Integer> {
    String getTenLoaiAoByID(int id);
    int getIdByName(String tenLoai);
}
