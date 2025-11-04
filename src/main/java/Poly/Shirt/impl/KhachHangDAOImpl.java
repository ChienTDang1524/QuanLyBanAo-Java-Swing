package Poly.Shirt.impl;

import Poly.Shirt.dao.CrudDAO;
import Poly.Shirt.Enity.KhachHang;
import Poly.Shirt.Util.XJdbc;
import Poly.Shirt.dao.KhachHangDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KhachHangDAOImpl implements KhachHangDAO {

    @Override
    public KhachHang create(KhachHang khachHang) {
        String sql = "INSERT INTO KhachHang(TenKH, SoDienThoai, Email, DiaChi) "
                + "OUTPUT INSERTED.* "
                + "VALUES(?, ?, ?, ?)";

        try (ResultSet rs = XJdbc.executeQuery(sql,
                khachHang.getTenKH(),
                khachHang.getSoDienThoai(),
                khachHang.getEmail(),
                khachHang.getDiaChi())) {

            if (rs.next()) {
                return readFromResultSet(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(KhachHang khachHang) {
        String sql = "UPDATE KhachHang SET TenKH=?, SoDienThoai=?, Email=?, DiaChi=? "
                + "WHERE KhachHangID=?";
        XJdbc.executeUpdate(sql,
                khachHang.getTenKH(),
                khachHang.getSoDienThoai(),
                khachHang.getEmail(),
                khachHang.getDiaChi(),
                khachHang.getKhachHangID());
    }

    @Override
    public void deleteById(Integer khachHangID) {
        String sql = "DELETE FROM KhachHang WHERE KhachHangID=?";
        XJdbc.executeUpdate(sql, khachHangID);
    }

    @Override
    public List<KhachHang> findAll() {
        String sql = "SELECT * FROM KhachHang";
        return select(sql);
    }

    @Override
    public KhachHang findById(Integer khachHangID) {
        String sql = "SELECT * FROM KhachHang WHERE KhachHangID=?";
        List<KhachHang> list = select(sql, khachHangID);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<KhachHang> select(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
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

     private KhachHang readFromResultSet(ResultSet rs) throws SQLException {
        KhachHang kh = new KhachHang();
        kh.setKhachHangID(rs.getInt("KhachHangID"));
        kh.setTenKH(rs.getString("TenKH"));
        kh.setSoDienThoai(rs.getString("SoDienThoai"));
        kh.setEmail(rs.getString("Email"));
        kh.setDiaChi(rs.getString("DiaChi"));
        return kh;
    }

    @Override
    public KhachHang findById(int khachHangID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<KhachHang> findByKeyword(String keyword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Thay đổi phương thức findByCriteria
    @Override
    public List<KhachHang> findByCriteria(String tenKH, String soDienThoai) {
        StringBuilder sql = new StringBuilder("SELECT * FROM KhachHang WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (tenKH != null && !tenKH.isEmpty()) {
            sql.append("AND TenKH LIKE ? ");
            params.add("%" + tenKH + "%");
        }
        if (soDienThoai != null && !soDienThoai.isEmpty()) {
            sql.append("AND SoDienThoai LIKE ? ");
            params.add("%" + soDienThoai + "%");
        }

        return select(sql.toString(), params.toArray());
    }

    @Override
    public int countByField(String fieldName, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
