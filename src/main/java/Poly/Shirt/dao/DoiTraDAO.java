package Poly.Shirt.dao;

import Poly.Shirt.Enity.DoiTra;
import java.util.Date;
import java.util.List;

public interface DoiTraDAO extends CrudDAO<DoiTra, Integer> {
    List<DoiTra> findByHoaDonID(int hoaDonID);
    List<DoiTra> findByTrangThai(int trangThai);
    List<DoiTra> findByNhanVienID(int nhanVienID);
    List<DoiTra> findByDateRange(Date fromDate, Date toDate);
    boolean updateTrangThai(int doiTraID, int trangThai);
}