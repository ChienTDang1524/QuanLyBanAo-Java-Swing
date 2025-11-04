package Poly.Shirt.impl;

import Poly.Shirt.Enity.KhuyenMai;
import Poly.Shirt.Util.XJdbc;
import Poly.Shirt.dao.KhuyenMaiDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KhuyenMaiDAOImpl implements KhuyenMaiDAO {

    @Override
    public KhuyenMai create(KhuyenMai entity) {
        String sql = "INSERT INTO KhuyenMai(TenKM, MoTa, NgayBatDau, NgayKetThuc, LoaiGiamGia, GiaTriGiam, TrangThai) "
                   + "OUTPUT INSERTED.* VALUES(?, ?, ?, ?, ?, ?, ?)";
        
        try (ResultSet rs = XJdbc.executeQuery(sql,
                entity.getTenKM(),
                entity.getMoTa(),
                new java.sql.Date(entity.getNgayBatDau().getTime()),
                new java.sql.Date(entity.getNgayKetThuc().getTime()),
                entity.getLoaiGiamGia(),
                entity.getGiaTriGiam(),
                entity.isTrangThai())) {
            
            if (rs.next()) {
                return readFromResultSet(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(KhuyenMai entity) {
        String sql = "UPDATE KhuyenMai SET TenKM=?, MoTa=?, NgayBatDau=?, NgayKetThuc=?, "
                   + "LoaiGiamGia=?, GiaTriGiam=?, TrangThai=? WHERE KhuyenMaiID=?";
        XJdbc.executeUpdate(sql,
                entity.getTenKM(),
                entity.getMoTa(),
                new java.sql.Date(entity.getNgayBatDau().getTime()),
                new java.sql.Date(entity.getNgayKetThuc().getTime()),
                entity.getLoaiGiamGia(),
                entity.getGiaTriGiam(),
                entity.isTrangThai(),
                entity.getKhuyenMaiID());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM KhuyenMai WHERE KhuyenMaiID=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public List<KhuyenMai> findAll() {
        String sql = "SELECT * FROM KhuyenMai";
        return select(sql);
    }

    @Override
    public KhuyenMai findById(Integer id) {
        String sql = "SELECT * FROM KhuyenMai WHERE KhuyenMaiID=?";
        List<KhuyenMai> list = select(sql, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<KhuyenMai> findByCriteria(String tenKM, Boolean trangThai) {
        StringBuilder sql = new StringBuilder("SELECT * FROM KhuyenMai WHERE 1=1");
        List<Object> params = new ArrayList<>();
        
        if (tenKM != null && !tenKM.isEmpty()) {
            sql.append(" AND TenKM LIKE ?");
            params.add("%" + tenKM + "%");
        }
        
        if (trangThai != null) {
            sql.append(" AND TrangThai = ?");
            params.add(trangThai);
        }
        
        return select(sql.toString(), params.toArray());
    }

    @Override
    public List<KhuyenMai> findActivePromotions(Date currentDate) {
        String sql = "SELECT * FROM KhuyenMai WHERE TrangThai = 1 "
                   + "AND ? BETWEEN NgayBatDau AND NgayKetThuc";
        return select(sql, new java.sql.Date(currentDate.getTime()));
    }

    private List<KhuyenMai> select(String sql, Object... args) {
        List<KhuyenMai> list = new ArrayList<>();
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

    private KhuyenMai readFromResultSet(ResultSet rs) throws SQLException {
        KhuyenMai km = new KhuyenMai();
        km.setKhuyenMaiID(rs.getInt("KhuyenMaiID"));
        km.setTenKM(rs.getString("TenKM"));
        km.setMoTa(rs.getString("MoTa"));
        km.setNgayBatDau(rs.getDate("NgayBatDau"));
        km.setNgayKetThuc(rs.getDate("NgayKetThuc"));
        km.setLoaiGiamGia(rs.getInt("LoaiGiamGia"));
        km.setGiaTriGiam(rs.getDouble("GiaTriGiam"));
        km.setTrangThai(rs.getBoolean("TrangThai"));
        return km;
    }

    @Override
    public int countByField(String fieldName, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
   
}