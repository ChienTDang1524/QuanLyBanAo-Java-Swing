/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Poly.Shirt.dao;

import Poly.Shirt.Enity.KhachHang;
import java.util.Date;
import java.util.List;

/**
 *
 * @author trinh
 */
public interface KhachHangDAO extends CrudDAO<KhachHang, Integer> {

    KhachHang findById(int khachHangID);

    List<KhachHang> findAll();

    List<KhachHang> findByKeyword(String keyword);

    List<KhachHang> findByCriteria(String tenKH, String soDienThoai);

}
