package Poly.Shirt.impl;

import Poly.Shirt.Enity.AoChiTiet;
import Poly.Shirt.dao.AoChiTietDAO;
import Poly.Shirt.Util.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AoChiTietDAOImpl implements AoChiTietDAO {

    @Override
    public AoChiTiet create(AoChiTiet entity) {
        String sql = "INSERT INTO AoChiTiet(AoID, SizeID, MauSacID, SoLuongTon, GiaGoc, GiaBan) "
                + "VALUES(?, ?, ?, ?, ?, ?)";

        Integer id = XJdbc.executeUpdate(sql,
                entity.getAoID(),
                entity.getSizeID(),
                entity.getMauSacID(),
                entity.getSoLuongTon(),
                entity.getGiaGoc(),
                entity.getGiaBan());

        if (id != null) {
            entity.setAoChiTietID(id);
        }
        return entity;
    }

    @Override
    public void update(AoChiTiet entity) {
        String sql = "UPDATE AoChiTiet SET AoID=?, SizeID=?, MauSacID=?, SoLuongTon=?, GiaGoc=?, GiaBan=? WHERE AoChiTietID=?";
        XJdbc.executeUpdate(sql,
                entity.getAoID(),
                entity.getSizeID(),
                entity.getMauSacID(),
                entity.getSoLuongTon(),
                entity.getGiaGoc(),
                entity.getGiaBan(),
                entity.getAoChiTietID());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM AoChiTiet WHERE AoChiTietID=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public List<AoChiTiet> findAll() {
        String sql = "SELECT * FROM AoChiTiet";
        return select(sql);
    }

    @Override
    public AoChiTiet findById(Integer id) {
        String sql = "SELECT * FROM AoChiTiet WHERE AoChiTietID=?";
        List<AoChiTiet> list = select(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<AoChiTiet> findByAoID(int aoID) {
        String sql = "SELECT * FROM AoChiTiet WHERE AoID=?";
        return select(sql, aoID);
    }

    @Override
    public List<AoChiTiet> findBySizeID(int sizeID) {
        String sql = "SELECT * FROM AoChiTiet WHERE SizeID=?";
        return select(sql, sizeID);
    }

    @Override
    public List<AoChiTiet> findByMauSacID(int mauSacID) {
        String sql = "SELECT * FROM AoChiTiet WHERE MauSacID=?";
        return select(sql, mauSacID);
    }

    @Override
    public boolean updateSoLuong(int aoChiTietID, int soLuong) {
        String sql = "UPDATE AoChiTiet SET SoLuongTon=? WHERE AoChiTietID=?";
        return XJdbc.executeUpdate(sql, soLuong, aoChiTietID) > 0;
    }

    private List<AoChiTiet> select(String sql, Object... args) {
        List<AoChiTiet> list = new ArrayList<>();
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

    private AoChiTiet readFromResultSet(ResultSet rs) throws SQLException {
        AoChiTiet ct = new AoChiTiet();
        ct.setAoChiTietID(rs.getInt("AoChiTietID"));
        ct.setAoID(rs.getInt("AoID"));
        ct.setSizeID(rs.getInt("SizeID"));
        ct.setMauSacID(rs.getInt("MauSacID"));
        ct.setSoLuongTon(rs.getInt("SoLuongTon"));
        ct.setGiaGoc(rs.getDouble("GiaGoc"));
        ct.setGiaBan(rs.getDouble("GiaBan"));

        // Thêm các trường mới nếu có trong ResultSet
        try {
            ct.setTenAo(rs.getString("TenAo"));
            ct.setTenSize(rs.getString("TenSize"));
            ct.setTenMau(rs.getString("TenMau"));
        } catch (SQLException e) {
            // Bỏ qua nếu các cột này không tồn tại trong ResultSet
        }
        return ct;
    }

    @Override
    public int countByAoID(int aoID) {
        String sql = "SELECT COUNT(*) FROM AoChiTiet WHERE AoID=?";
        try {
            ResultSet rs = XJdbc.executeQuery(sql, aoID);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int countByField(String fieldName, Object value) {
        String sql = "SELECT COUNT(*) FROM AoChiTiet WHERE " + fieldName + "=?";
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

    @Override
    public int countBySizeID(int sizeID) {
        String sql = "SELECT COUNT(*) FROM AoChiTiet WHERE SizeID=?";
        try {
            ResultSet rs = XJdbc.executeQuery(sql, sizeID);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int countByMauSacID(int mauSacID) {
        String sql = "SELECT COUNT(*) FROM AoChiTiet WHERE MauSacID=?";
        try {
            ResultSet rs = XJdbc.executeQuery(sql, mauSacID);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public boolean existsByAoAndSizeAndMau(int aoID, int sizeID, int mauSacID) {
        String sql = "SELECT COUNT(*) FROM AoChiTiet WHERE AoID = ? AND SizeID = ? AND MauSacID = ?";
        try {
            ResultSet rs = XJdbc.executeQuery(sql, aoID, sizeID, mauSacID);
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Object[] getProductDetails(int aoChiTietID) {
        String sql = "SELECT a.TenAo, sa.TenSize, ms.TenMau, act.GiaBan "
                + "FROM AoChiTiet act "
                + "JOIN Ao a ON act.AoID = a.AoID "
                + "JOIN SizeAo sa ON act.SizeID = sa.SizeID "
                + "JOIN MauSac ms ON act.MauSacID = ms.MauSacID "
                + "WHERE act.AoChiTietID = ?";

        try (ResultSet rs = XJdbc.executeQuery(sql, aoChiTietID)) {
            if (rs.next()) {
                return new Object[]{
                    rs.getString("TenAo"),
                    rs.getString("TenSize"),
                    rs.getString("TenMau"),
                    rs.getDouble("GiaBan")
                };
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<AoChiTiet> findAllWithDetails() {
        String sql = "SELECT act.*, a.TenAo, sa.TenSize, ms.TenMau "
                + "FROM AoChiTiet act "
                + "JOIN Ao a ON act.AoID = a.AoID "
                + "JOIN SizeAo sa ON act.SizeID = sa.SizeID "
                + "JOIN MauSac ms ON act.MauSacID = ms.MauSacID";

        List<AoChiTiet> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(sql)) {
            while (rs.next()) {
                AoChiTiet act = readFromResultSet(rs);
                act.setTenAo(rs.getString("TenAo"));
                act.setTenSize(rs.getString("TenSize"));
                act.setTenMau(rs.getString("TenMau"));
                list.add(act);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    @Override
    public boolean giamSoLuongTon(int aoChiTietID, int soLuong)  {
        String sql = "UPDATE AoChiTiet SET SoLuongTon = SoLuongTon - ? WHERE AoChiTietID = ? AND SoLuongTon >= ?";
        return XJdbc.executeUpdate(sql, soLuong, aoChiTietID, soLuong) > 0;
    }

    @Override
    public boolean tangSoLuongTon(int aoChiTietID, int soLuong)  {
        String sql = "UPDATE AoChiTiet SET SoLuongTon = SoLuongTon + ? WHERE AoChiTietID = ?";
        return XJdbc.executeUpdate(sql, soLuong, aoChiTietID) > 0;
    }

}
