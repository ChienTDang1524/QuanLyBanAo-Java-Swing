/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Poly.Shirt.dao;

/**
 *
 * @author trinh
 */


import Poly.Shirt.Enity.SizeAo;
import java.util.List;

public interface SizeAoDAO extends CrudDAO<SizeAo, Integer> {
    String getTenSizeByID(int id);
    int getIdByName(String tenSize);
}
