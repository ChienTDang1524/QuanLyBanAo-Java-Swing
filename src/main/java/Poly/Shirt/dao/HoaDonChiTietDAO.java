package Poly.Shirt.dao;

import Poly.Shirt.Enity.HoaDonChiTiet;
import java.util.List;

public interface HoaDonChiTietDAO extends CrudDAO<HoaDonChiTiet, Integer> {
    List<HoaDonChiTiet> findByHoaDonID(int hoaDonID);
    List<HoaDonChiTiet> findByAoChiTietID(int aoChiTietID);
    boolean deleteByHoaDonID(int hoaDonID);
    List<HoaDonChiTiet> findWithProductDetails(int hoaDonID);
    double getTotalRevenueByProduct(int aoChiTietID);
    
    
}