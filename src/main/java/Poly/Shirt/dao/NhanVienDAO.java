/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Poly.Shirt.dao;

import Poly.Shirt.Enity.NhanVien;
import java.util.List;

/**
 *
 * @author trinh
 */
public interface NhanVienDAO extends CrudDAO<NhanVien,Integer >{
     NhanVien findById(Integer id);
     List<NhanVien> findByCriteria(String tenNV, String soDienThoai, Boolean trangThai);
    boolean hasTaiKhoan(int nhanVienID);
}
