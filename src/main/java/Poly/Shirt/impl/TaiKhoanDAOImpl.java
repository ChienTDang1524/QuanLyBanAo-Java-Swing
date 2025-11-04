package Poly.Shirt.impl;

import Poly.Shirt.dao.CrudDAO;
import Poly.Shirt.Enity.TaiKhoan;
import Poly.Shirt.Util.XJdbc;
import Poly.Shirt.dao.TaiKhoanDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAOImpl implements TaiKhoanDAO {

    @Override
    public TaiKhoan create(TaiKhoan taiKhoan) {
        String sql = "INSERT INTO TaiKhoan(TenDangNhap, MatKhau, NhanVienID, VaiTroID, TrangThai) "
                + "OUTPUT INSERTED.* "
                + "VALUES(?, ?, ?, ?, ?)";

        try (ResultSet rs = XJdbc.executeQuery(sql,
                taiKhoan.getTenDangNhap(),
                taiKhoan.getMatKhau(),
                taiKhoan.getNhanVienID(),
                taiKhoan.getVaiTroID(),
                taiKhoan.isTrangThai())) {

            if (rs.next()) {
                return readFromResultSet(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(TaiKhoan taiKhoan) {
        String sql = "UPDATE TaiKhoan SET TenDangNhap=?, MatKhau=?, NhanVienID=?, VaiTroID=?, TrangThai=? "
                + "WHERE TaiKhoanID=?";
        XJdbc.executeUpdate(sql,
                taiKhoan.getTenDangNhap(),
                taiKhoan.getMatKhau(),
                taiKhoan.getNhanVienID(),
                taiKhoan.getVaiTroID(),
                taiKhoan.isTrangThai(),
                taiKhoan.getTaiKhoanID());
    }

    @Override
    public void deleteById(Integer taiKhoanID) {
        String sql = "DELETE FROM TaiKhoan WHERE TaiKhoanID=?";
        XJdbc.executeUpdate(sql, taiKhoanID);
    }

    @Override
    public List<TaiKhoan> findAll() {
        String sql = "SELECT * FROM TaiKhoan";
        return select(sql);
    }

    @Override
    public TaiKhoan findById(Integer taiKhoanID) {
        String sql = "SELECT * FROM TaiKhoan WHERE TaiKhoanID=?";
        List<TaiKhoan> list = select(sql, taiKhoanID);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public TaiKhoan findByUsername(String tenDangNhap) {
        String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap=?";
        List<TaiKhoan> list = select(sql, tenDangNhap);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<TaiKhoan> findByKeyword(String keyword) {
        String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap LIKE ?";
        return select(sql, "%" + keyword + "%");
    }

    private List<TaiKhoan> select(String sql, Object... args) {
        List<TaiKhoan> list = new ArrayList<>();
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

    private TaiKhoan readFromResultSet(ResultSet rs) throws SQLException {
        TaiKhoan tk = new TaiKhoan();
        tk.setTaiKhoanID(rs.getInt("TaiKhoanID"));
        tk.setTenDangNhap(rs.getString("TenDangNhap"));
        tk.setMatKhau(rs.getString("MatKhau"));
        tk.setNhanVienID(rs.getInt("NhanVienID"));
        tk.setVaiTroID(rs.getInt("VaiTroID"));
        tk.setTrangThai(rs.getBoolean("TrangThai"));
        return tk;
    }

    @Override
    public List<TaiKhoan> findByCriteria(String tenDangNhap, String tenNV, Boolean trangThai, Integer vaiTro) {
        StringBuilder sql = new StringBuilder(
                "SELECT tk.* FROM TaiKhoan tk "
                + "JOIN NhanVien nv ON tk.NhanVienID = nv.NhanVienID "
                + "WHERE 1=1 ");

        if (tenDangNhap != null && !tenDangNhap.isEmpty()) {
            sql.append("AND tk.TenDangNhap LIKE ? ");
        }
        if (tenNV != null && !tenNV.isEmpty()) {
            sql.append("AND nv.TenNV LIKE ? ");
        }
        if (trangThai != null) {
            sql.append("AND tk.TrangThai = ? ");
        }
        if (vaiTro != null) {
            sql.append("AND tk.VaiTroID = ? ");
        }

        List<Object> params = new ArrayList<>();

        if (tenDangNhap != null && !tenDangNhap.isEmpty()) {
            params.add("%" + tenDangNhap + "%");
        }
        if (tenNV != null && !tenNV.isEmpty()) {
            params.add("%" + tenNV + "%");
        }
        if (trangThai != null) {
            params.add(trangThai);
        }
        if (vaiTro != null) {
            params.add(vaiTro);
        }

        return select(sql.toString(), params.toArray());
    }

    @Override
    public int countByField(String fieldName, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
