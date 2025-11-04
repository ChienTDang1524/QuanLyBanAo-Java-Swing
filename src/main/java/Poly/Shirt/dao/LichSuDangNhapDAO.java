package Poly.Shirt.dao;

import Poly.Shirt.Enity.LichSuDangNhap;
import java.util.List;

public interface LichSuDangNhapDAO {
    void logLogin(int taiKhoanID, String ipAddress, boolean trangThai);
    List<LichSuDangNhap> findByTaiKhoanID(int taiKhoanID);
    List<LichSuDangNhap> findAll();
    
    // Thêm các phương thức mới
    List<LichSuDangNhap> findByFilter(String username, String fromDate, String toDate, int page, int pageSize);
    int countByFilter(String username, String fromDate, String toDate);
}