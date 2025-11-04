/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poly.Shirt.dao;

import Poly.Shirt.Enity.HoaDon;
import Poly.Shirt.Enity.KhuyenMai;
import java.util.Date;
import java.util.List;

/**
 *
 * @author trinh
 */
public interface HoaDonDAO  extends CrudDAO<HoaDon, Integer>{
   List<HoaDon> findByNhanVienID(int nhanVienID);
    List<HoaDon> findByKhachHangID(int khachHangID);
    List<HoaDon> findByTrangThai(int trangThai);
    List<HoaDon> findByDateRange(Date fromDate, Date toDate);
    List<HoaDon> findByKeyword(String keyword);
    List<HoaDon> findByCriteria(Integer nhanVienID, Integer khachHangID, Integer trangThai, Date fromDate, Date toDate);
    double getTotalRevenue();
    double getTotalRevenueByDateRange(Date fromDate, Date toDate);
    

    public List<HoaDon> findAllWithCustomerAndStaffInfo();
}
