package Poly.Shirt.impl;

import Poly.Shirt.Enity.HoaDon;
import Poly.Shirt.dao.HoaDonDAO;
import Poly.Shirt.Util.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDonDAOImpl implements HoaDonDAO {

    @Override
    public HoaDon create(HoaDon entity) {
        String sql = "INSERT INTO HoaDon(KhachHangID, NhanVienID, KhuyenMaiID, NgayTao, TongTien, TienGiamGia, ThanhTien, TrangThai, GhiChu) "
                + "OUTPUT INSERTED.* VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (ResultSet rs = XJdbc.executeQuery(sql,
                entity.getKhachHangID(),
                entity.getNhanVienID(),
                entity.getKhuyenMaiID(),
                new java.sql.Date(entity.getNgayTao().getTime()),
                entity.getTongTien(),
                entity.getTienGiamGia(),
                entity.getThanhTien(),
                entity.getTrangThai(),
                entity.getGhiChu())) {

            if (rs.next()) {
                return readFromResultSet(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(HoaDon entity) {
        String sql = "UPDATE HoaDon SET KhachHangID=?, NhanVienID=?, KhuyenMaiID=?, NgayTao=?, "
                + "TongTien=?, TienGiamGia=?, ThanhTien=?, TrangThai=?, GhiChu=? WHERE HoaDonID=?";
        XJdbc.executeUpdate(sql,
                entity.getKhachHangID(),
                entity.getNhanVienID(),
                entity.getKhuyenMaiID(),
                new java.sql.Date(entity.getNgayTao().getTime()),
                entity.getTongTien(),
                entity.getTienGiamGia(),
                entity.getThanhTien(),
                entity.getTrangThai(),
                entity.getGhiChu(),
                entity.getHoaDonID());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM HoaDon WHERE HoaDonID=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public List<HoaDon> findAll() {
        String sql = "SELECT * FROM HoaDon ORDER BY NgayTao DESC";
        return select(sql);
    }

    @Override
    public HoaDon findById(Integer id) {
        String sql = "SELECT * FROM HoaDon WHERE HoaDonID=?";
        List<HoaDon> list = select(sql, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<HoaDon> findByNhanVienID(int nhanVienID) {
        String sql = "SELECT * FROM HoaDon WHERE NhanVienID=? ORDER BY NgayTao DESC";
        return select(sql, nhanVienID);
    }

    @Override
    public List<HoaDon> findByKhachHangID(int khachHangID) {
        String sql = "SELECT * FROM HoaDon WHERE KhachHangID=? ORDER BY NgayTao DESC";
        return select(sql, khachHangID);
    }

    @Override
    public List<HoaDon> findByTrangThai(int trangThai) {
        String sql = "SELECT * FROM HoaDon WHERE TrangThai=? ORDER BY NgayTao DESC";
        return select(sql, trangThai);
    }

    @Override
    public List<HoaDon> findByDateRange(Date fromDate, Date toDate) {
        String sql = "SELECT * FROM HoaDon WHERE NgayTao BETWEEN ? AND ? ORDER BY NgayTao DESC";
        return select(sql, new java.sql.Date(fromDate.getTime()), new java.sql.Date(toDate.getTime()));
    }

    @Override
    public List<HoaDon> findByKeyword(String keyword) {
        String sql = "SELECT hd.* FROM HoaDon hd "
                + "LEFT JOIN KhachHang kh ON hd.KhachHangID = kh.KhachHangID "
                + "LEFT JOIN NhanVien nv ON hd.NhanVienID = nv.NhanVienID "
                + "WHERE kh.TenKH LIKE ? OR nv.TenNV LIKE ? OR hd.HoaDonID LIKE ? "
                + "ORDER BY hd.NgayTao DESC";
        String searchTerm = "%" + keyword + "%";
        return select(sql, searchTerm, searchTerm, searchTerm);
    }

    @Override
    public List<HoaDon> findByCriteria(Integer nhanVienID, Integer khachHangID, Integer trangThai, Date fromDate, Date toDate) {
        StringBuilder sql = new StringBuilder("SELECT * FROM HoaDon WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (nhanVienID != null) {
            sql.append(" AND NhanVienID = ?");
            params.add(nhanVienID);
        }
        if (khachHangID != null) {
            sql.append(" AND KhachHangID = ?");
            params.add(khachHangID);
        }
        if (trangThai != null) {
            sql.append(" AND TrangThai = ?");
            params.add(trangThai);
        }
        if (fromDate != null && toDate != null) {
            sql.append(" AND NgayTao BETWEEN ? AND ?");
            params.add(new java.sql.Date(fromDate.getTime()));
            params.add(new java.sql.Date(toDate.getTime()));
        }

        sql.append(" ORDER BY NgayTao DESC");
        return select(sql.toString(), params.toArray());
    }

    @Override
    public double getTotalRevenue() {
        String sql = "SELECT SUM(ThanhTien) FROM HoaDon WHERE TrangThai = 2"; // Đã thanh toán
        try {
            ResultSet rs = XJdbc.executeQuery(sql);
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public double getTotalRevenueByDateRange(Date fromDate, Date toDate) {
        String sql = "SELECT SUM(ThanhTien) FROM HoaDon WHERE TrangThai = 2 AND NgayTao BETWEEN ? AND ?";
        try {
            ResultSet rs = XJdbc.executeQuery(sql,
                    new java.sql.Date(fromDate.getTime()),
                    new java.sql.Date(toDate.getTime()));
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int countByField(String fieldName, Object value) {
        String sql = "SELECT COUNT(*) FROM HoaDon WHERE " + fieldName + "=?";
        try {
            ResultSet rs = XJdbc.executeQuery(sql, value);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private List<HoaDon> select(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                list.add(readFromResultSet(rs));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private HoaDon readFromResultSet(ResultSet rs) throws SQLException {
        HoaDon hd = new HoaDon();
        hd.setHoaDonID(rs.getInt("HoaDonID"));
        hd.setKhachHangID(rs.getObject("KhachHangID") != null ? rs.getInt("KhachHangID") : null);
        hd.setNhanVienID(rs.getInt("NhanVienID"));
        hd.setKhuyenMaiID(rs.getObject("KhuyenMaiID") != null ? rs.getInt("KhuyenMaiID") : null);
        hd.setNgayTao(rs.getDate("NgayTao"));
        hd.setTongTien(rs.getDouble("TongTien"));
        hd.setTienGiamGia(rs.getDouble("TienGiamGia"));
        hd.setThanhTien(rs.getDouble("ThanhTien"));
        hd.setTrangThai(rs.getInt("TrangThai"));
        hd.setGhiChu(rs.getString("GhiChu"));
        return hd;
    }

    
    public List<HoaDon> findAllWithCustomerAndStaffInfo() {
        String sql = "SELECT hd.*, kh.TenKH AS TenKhachHang, nv.TenNV AS TenNhanVien "
                + "FROM HoaDon hd "
                + "LEFT JOIN KhachHang kh ON hd.KhachHangID = kh.KhachHangID "
                + "JOIN NhanVien nv ON hd.NhanVienID = nv.NhanVienID "
                + "ORDER BY hd.NgayTao DESC";

        List<HoaDon> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(sql)) {
            while (rs.next()) {
                HoaDon hd = readFromResultSet(rs);
                // Add extended info
                hd.setTenKhachHang(rs.getString("TenKhachHang"));
                hd.setTenNhanVien(rs.getString("TenNhanVien"));
                list.add(hd);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
