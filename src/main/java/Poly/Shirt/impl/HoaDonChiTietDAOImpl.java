package Poly.Shirt.impl;

import Poly.Shirt.Enity.HoaDonChiTiet;
import Poly.Shirt.dao.HoaDonChiTietDAO;
import Poly.Shirt.Util.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HoaDonChiTietDAOImpl implements HoaDonChiTietDAO {

    @Override
    public List<HoaDonChiTiet> findByHoaDonID(int hoaDonID) {
        String sql = "SELECT * FROM HoaDonChiTiet WHERE HoaDonID=?";
        
        return select(sql, hoaDonID);
    }

    @Override
    public HoaDonChiTiet create(HoaDonChiTiet entity) {
        // Bỏ HDCT_ID khỏi câu lệnh INSERT vì nó là identity tự động tăng
        String sql = "INSERT INTO HoaDonChiTiet(HoaDonID, AoChiTietID, SoLuong, DonGia, ThanhTien) "
                + "OUTPUT INSERTED.* VALUES(?, ?, ?, ?, ?)";

        try (ResultSet rs = XJdbc.executeQuery(sql,
                entity.getHoaDonID(),
                entity.getAoChiTietID(),
                entity.getSoLuong(),
                entity.getDonGia(),
                entity.getThanhTien())) {

            if (rs.next()) {
                return readFromResultSet(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<HoaDonChiTiet> select(String sql, Object... args) {
        List<HoaDonChiTiet> list = new ArrayList<>();
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

    private HoaDonChiTiet readFromResultSet(ResultSet rs) throws SQLException {
        HoaDonChiTiet hdct = new HoaDonChiTiet();
        hdct.setHdctID(rs.getInt("HDCT_ID"));
        hdct.setHoaDonID(rs.getInt("HoaDonID"));
        hdct.setAoChiTietID(rs.getInt("AoChiTietID"));
        hdct.setSoLuong(rs.getInt("SoLuong"));
        hdct.setDonGia(rs.getDouble("DonGia"));
        hdct.setThanhTien(rs.getDouble("ThanhTien"));
        return hdct;
    }

    // Các phương thức khác giữ nguyên nhưng triển khai đầy đủ
    @Override
    public List<HoaDonChiTiet> findByAoChiTietID(int aoChiTietID) {
        String sql = "SELECT * FROM HoaDonChiTiet WHERE AoChiTietID=?";
        return select(sql, aoChiTietID);
    }

    @Override
    public boolean deleteByHoaDonID(int hoaDonID) {
        String sql = "DELETE FROM HoaDonChiTiet WHERE HoaDonID=?";
        return XJdbc.executeUpdate(sql, hoaDonID) > 0;
    }

    @Override
    public List<HoaDonChiTiet> findWithProductDetails(int hoaDonID) {
        String sql = "SELECT hdct.*, a.TenAo, sa.TenSize, ms.TenMau "
                + "FROM HoaDonChiTiet hdct "
                + "JOIN AoChiTiet act ON hdct.AoChiTietID = act.AoChiTietID "
                + "JOIN Ao a ON act.AoID = a.AoID "
                + "JOIN SizeAo sa ON act.SizeID = sa.SizeID "
                + "JOIN MauSac ms ON act.MauSacID = ms.MauSacID "
                + "WHERE hdct.HoaDonID=?";
        return selectWithDetails(sql, hoaDonID);
    }

    private List<HoaDonChiTiet> selectWithDetails(String sql, Object... args) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                HoaDonChiTiet hdct = readFromResultSet(rs);
                // Thêm thông tin chi tiết sản phẩm
                hdct.setTenAo(rs.getString("TenAo"));
                hdct.setTenSize(rs.getString("TenSize"));
                hdct.setTenMau(rs.getString("TenMau"));
                list.add(hdct);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getTotalRevenueByProduct(int aoChiTietID) {
        String sql = "SELECT SUM(ThanhTien) FROM HoaDonChiTiet WHERE AoChiTietID=?";
        try (ResultSet rs = XJdbc.executeQuery(sql, aoChiTietID)) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public void update(HoaDonChiTiet entity) {
        String sql = "UPDATE HoaDonChiTiet SET SoLuong=?, DonGia=?, ThanhTien=? WHERE HDCT_ID=?";
        XJdbc.executeUpdate(sql,
                entity.getSoLuong(),
                entity.getDonGia(),
                entity.getThanhTien(),
                entity.getHdctID());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM HoaDonChiTiet WHERE HDCT_ID=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public List<HoaDonChiTiet> findAll() {
        String sql = "SELECT * FROM HoaDonChiTiet";
        return select(sql);
    }

    @Override
    public HoaDonChiTiet findById(Integer id) {
        String sql = "SELECT * FROM HoaDonChiTiet WHERE HDCT_ID=?";
        List<HoaDonChiTiet> list = select(sql, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public int countByField(String fieldName, Object value) {
        String sql = "SELECT COUNT(*) FROM HoaDonChiTiet WHERE " + fieldName + "=?";
        try (ResultSet rs = XJdbc.executeQuery(sql, value)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    
   
    
}
