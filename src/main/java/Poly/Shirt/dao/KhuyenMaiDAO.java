package Poly.Shirt.dao;

import Poly.Shirt.Enity.KhuyenMai;
import java.util.Date;
import java.util.List;

public interface KhuyenMaiDAO extends CrudDAO<KhuyenMai, Integer> {
    List<KhuyenMai> findByCriteria(String tenKM, Boolean trangThai);
    List<KhuyenMai> findActivePromotions(Date currentDate);
    
}