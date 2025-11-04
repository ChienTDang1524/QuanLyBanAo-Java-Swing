/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Poly.Shirt.dao;

/**
 *
 * @author trinh
 */

import Poly.Shirt.Enity.AoChiTiet;
import java.util.List;

public interface AoChiTietDAO extends CrudDAO<AoChiTiet, Integer> {
    List<AoChiTiet> findByAoID(int aoID);
    List<AoChiTiet> findBySizeID(int sizeID);
    List<AoChiTiet> findByMauSacID(int mauSacID);
    
    boolean updateSoLuong(int aoChiTietID, int soLuong);

   int countByAoID(int aoID);
    int countBySizeID(int sizeID);
    int countByMauSacID(int mauSacID);
    boolean existsByAoAndSizeAndMau(int aoID, int sizeID, int mauSacID);
     Object[] getProductDetails(int aoChiTietID);
    List<AoChiTiet> findAllWithDetails();
    boolean giamSoLuongTon(int aoChiTietID, int soLuong);
    boolean tangSoLuongTon(int aoChiTietID, int soLuong);
   
}