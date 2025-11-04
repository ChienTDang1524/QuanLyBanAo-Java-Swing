/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poly.Shirt.impl;

import Poly.Shirt.Enity.NhanVien;
import Poly.Shirt.dao.NhanVienDAO;
import Poly.Shirt.Util.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAOImpl implements NhanVienDAO {

    @Override
    public NhanVien create(NhanVien entity) {
        String sql = "INSERT INTO NhanVien(TenNV, SoDienThoai, Email, DiaChi, TrangThai) VALUES(?, ?, ?, ?, ?)";
        XJdbc.executeUpdate(sql,
                entity.getTenNV(),
                entity.getSoDienThoai(),
                entity.getEmail(),
                entity.getDiaChi(),
                entity.isTrangThai());
        return entity;
    }

    @Override
    public void update(NhanVien entity) {
        String sql = "UPDATE NhanVien SET TenNV=?, SoDienThoai=?, Email=?, DiaChi=?, TrangThai=? WHERE NhanVienID=?";
        XJdbc.executeUpdate(sql,
                entity.getTenNV(),
                entity.getSoDienThoai(),
                entity.getEmail(),
                entity.getDiaChi(),
                entity.isTrangThai(),
                entity.getNhanVienID());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM NhanVien WHERE NhanVienID=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public List<NhanVien> findAll() {
        String sql = "SELECT * FROM NhanVien";
        return select(sql);
    }

 
    @Override
    public NhanVien findById(Integer id) {
        String sql = "SELECT * FROM NhanVien WHERE NhanVienID=?";
        List<NhanVien> list = this.select(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    private List<NhanVien> select(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
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

    private NhanVien readFromResultSet(ResultSet rs) throws SQLException {
        NhanVien nv = new NhanVien();
        nv.setNhanVienID(rs.getInt("NhanVienID"));
        nv.setTenNV(rs.getString("TenNV"));
        nv.setSoDienThoai(rs.getString("SoDienThoai"));
        nv.setEmail(rs.getString("Email"));
        nv.setDiaChi(rs.getString("DiaChi"));
        nv.setTrangThai(rs.getBoolean("TrangThai"));
        return nv;
    }

    @Override
    public List<NhanVien> findByCriteria(String tenNV, String soDienThoai, Boolean trangThai) {
        StringBuilder sql = new StringBuilder("SELECT * FROM NhanVien WHERE 1=1");
        List<Object> params = new ArrayList<>();
        
        if (tenNV != null && !tenNV.isEmpty()) {
            sql.append(" AND TenNV LIKE ?");
            params.add("%" + tenNV + "%");
        }
        
        if (soDienThoai != null && !soDienThoai.isEmpty()) {
            sql.append(" AND SoDienThoai LIKE ?");
            params.add("%" + soDienThoai + "%");
        }
        
        if (trangThai != null) {
            sql.append(" AND TrangThai = ?");
            params.add(trangThai);
        }
        
        return select(sql.toString(), params.toArray());
    }

    @Override
     public boolean hasTaiKhoan(int nhanVienID) {
        String sql = "SELECT COUNT(*) FROM TaiKhoan WHERE NhanVienID=?";
        try {
            ResultSet rs = XJdbc.executeQuery(sql, nhanVienID);
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public int countByField(String fieldName, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}