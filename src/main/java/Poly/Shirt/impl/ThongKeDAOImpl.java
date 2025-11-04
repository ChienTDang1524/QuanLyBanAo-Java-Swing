package Poly.Shirt.impl;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import Poly.Shirt.dao.ThongKeDAO;
import Poly.Shirt.Util.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author trinh
 */
public class ThongKeDAOImpl implements ThongKeDAO {
     
    @Override
    public List<Map<String, Object>> getRevenueStatistics() {
        String sql = "SELECT \n" +
                "    FORMAT(hd.NgayTao, 'MM/yyyy') AS ThangNam, \n" +
                "    COUNT(DISTINCT hd.HoaDonID) AS SoHoaDon, \n" +
                "    SUM(hdct.SoLuong) AS SoSanPham, \n" +
                "    SUM(hdct.ThanhTien) AS DoanhThu, \n" +
                "    SUM(hdct.SoLuong * act.GiaGoc) AS GiaVon, \n" +
                "    SUM(hdct.ThanhTien - (hdct.SoLuong * act.GiaGoc)) AS LoiNhuan \n" +
                "FROM HoaDon hd \n" +
                "JOIN HoaDonChiTiet hdct ON hd.HoaDonID = hdct.HoaDonID \n" +
                "JOIN AoChiTiet act ON hdct.AoChiTietID = act.AoChiTietID \n" +
                "WHERE hd.TrangThai = 2 \n" + // Chỉ lấy hóa đơn đã thanh toán
                "GROUP BY FORMAT(hd.NgayTao, 'MM/yyyy') \n" +
                "ORDER BY MAX(hd.NgayTao) DESC";
        
        List<Map<String, Object>> list = new ArrayList<>();
        
        try (ResultSet rs = XJdbc.executeQuery(sql)) {
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("ThangNam", rs.getString("ThangNam"));
                map.put("SoHoaDon", rs.getInt("SoHoaDon"));
                map.put("SoSanPham", rs.getInt("SoSanPham"));
                map.put("DoanhThu", rs.getDouble("DoanhThu"));
                map.put("GiaVon", rs.getDouble("GiaVon"));
                map.put("LoiNhuan", rs.getDouble("LoiNhuan"));
                list.add(map);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Map<String, Double> getRevenueTotals() {
        String sql = "SELECT \n" +
                "    COUNT(DISTINCT hd.HoaDonID) AS TongHoaDon, \n" +
                "    SUM(hdct.SoLuong) AS TongSanPham, \n" +
                "    SUM(hdct.ThanhTien) AS TongDoanhThu, \n" +
                "    SUM(hdct.SoLuong * act.GiaGoc) AS TongGiaVon, \n" +
                "    SUM(hdct.ThanhTien - (hdct.SoLuong * act.GiaGoc)) AS TongLoiNhuan \n" +
                "FROM HoaDon hd \n" +
                "JOIN HoaDonChiTiet hdct ON hd.HoaDonID = hdct.HoaDonID \n" +
                "JOIN AoChiTiet act ON hdct.AoChiTietID = act.AoChiTietID \n" +
                "WHERE hd.TrangThai = 2"; // Chỉ lấy hóa đơn đã thanh toán
        
        Map<String, Double> totals = new HashMap<>();
        
        try (ResultSet rs = XJdbc.executeQuery(sql)) {
            if (rs.next()) {
                totals.put("TongHoaDon", (double) rs.getInt("TongHoaDon"));
                totals.put("TongSanPham", (double) rs.getInt("TongSanPham"));
                totals.put("TongDoanhThu", rs.getDouble("TongDoanhThu"));
                totals.put("TongGiaVon", rs.getDouble("TongGiaVon"));
                totals.put("TongLoiNhuan", rs.getDouble("TongLoiNhuan"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totals;
    }
}
