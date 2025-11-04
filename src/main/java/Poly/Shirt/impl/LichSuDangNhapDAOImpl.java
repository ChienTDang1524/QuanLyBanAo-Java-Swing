package Poly.Shirt.impl;

import Poly.Shirt.dao.LichSuDangNhapDAO;
import Poly.Shirt.Enity.LichSuDangNhap;
import Poly.Shirt.Util.XJdbc;
import Poly.Shirt.Util.XDate;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LichSuDangNhapDAOImpl implements LichSuDangNhapDAO {

    @Override
    public void logLogin(int taiKhoanID, String ipAddress, boolean trangThai) {
        String sql = "INSERT INTO LichSuDangNhap (TaiKhoanID, IPAddress, TrangThai) VALUES (?, ?, ?)";
        XJdbc.executeUpdate(sql, taiKhoanID, ipAddress, trangThai);
    }

    @Override
    public List<LichSuDangNhap> findByTaiKhoanID(int taiKhoanID) {
        String sql = "SELECT * FROM LichSuDangNhap WHERE TaiKhoanID = ? ORDER BY ThoiGian DESC";
        return selectBySql(sql, taiKhoanID);
    }

    @Override
    public List<LichSuDangNhap> findAll() {
        String sql = "SELECT * FROM LichSuDangNhap ORDER BY ThoiGian DESC";
        return selectBySql(sql);
    }

    @Override
    public List<LichSuDangNhap> findByFilter(String username, String fromDate, String toDate, int page, int pageSize) {
        StringBuilder sql = new StringBuilder("SELECT l.* FROM LichSuDangNhap l ");

        if (username != null) {
            sql.append("JOIN TaiKhoan t ON l.TaiKhoanID = t.TaiKhoanID ");
        }

        sql.append("WHERE 1=1 ");

        if (username != null) {
            sql.append("AND t.TenDangNhap = ? ");
        }

        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append("AND CONVERT(DATE, l.ThoiGian) >= ? ");
        }

        if (toDate != null && !toDate.isEmpty()) {
            sql.append("AND CONVERT(DATE, l.ThoiGian) <= ? ");
        }

        sql.append("ORDER BY l.ThoiGian DESC ");
        sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        List<Object> params = new ArrayList<>();
        if (username != null) {
            params.add(username);
        }

        if (fromDate != null && !fromDate.isEmpty()) {
            params.add(XDate.parse(fromDate, "dd/MM/yyyy"));
        }

        if (toDate != null && !toDate.isEmpty()) {
            params.add(XDate.parse(toDate, "dd/MM/yyyy"));
        }

        params.add((page - 1) * pageSize);
        params.add(pageSize);

        return selectBySql(sql.toString(), params.toArray());
    }

    @Override
    public int countByFilter(String username, String fromDate, String toDate) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM LichSuDangNhap l ");

        if (username != null) {
            sql.append("JOIN TaiKhoan t ON l.TaiKhoanID = t.TaiKhoanID ");
        }

        sql.append("WHERE 1=1 ");

        if (username != null) {
            sql.append("AND t.TenDangNhap = ? ");
        }

        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append("AND CONVERT(DATE, l.ThoiGian) >= ? ");
        }

        if (toDate != null && !toDate.isEmpty()) {
            sql.append("AND CONVERT(DATE, l.ThoiGian) <= ? ");
        }

        List<Object> params = new ArrayList<>();
        if (username != null) {
            params.add(username);
        }

        if (fromDate != null && !fromDate.isEmpty()) {
            params.add(XDate.parse(fromDate, "dd/MM/yyyy"));
        }

        if (toDate != null && !toDate.isEmpty()) {
            params.add(XDate.parse(toDate, "dd/MM/yyyy"));
        }

        return XJdbc.<Integer>getValue(sql.toString(), params.toArray());
    }

    private List<LichSuDangNhap> selectBySql(String sql, Object... args) {
        List<LichSuDangNhap> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(sql, args)) {
            while (rs.next()) {
                LichSuDangNhap log = new LichSuDangNhap();
                log.setLogID(rs.getInt("LogID"));
                log.setTaiKhoanID(rs.getInt("TaiKhoanID"));
                log.setThoiGian(rs.getTimestamp("ThoiGian"));
                log.setIPAddress(rs.getString("IPAddress"));
                log.setTrangThai(rs.getBoolean("TrangThai"));
                list.add(log);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

  
}