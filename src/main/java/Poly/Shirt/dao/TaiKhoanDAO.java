/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Poly.Shirt.dao;

import Poly.Shirt.Enity.TaiKhoan;
import java.util.List;

/**
 *
 * @author trinh
 */
public interface TaiKhoanDAO extends CrudDAO<TaiKhoan, Integer> {

    TaiKhoan findByUsername(String tenDangNhap);

    List<TaiKhoan> findAll();

    List<TaiKhoan> findByKeyword(String keyword);

    List<TaiKhoan> findByCriteria(String tenDangNhap, String tenNV, Boolean trangThai, Integer vaiTro);

}
