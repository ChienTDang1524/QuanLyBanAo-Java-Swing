package Poly.Shirt.impl;

import Poly.Shirt.dao.DoiTraDAO;
import Poly.Shirt.Enity.DoiTra;
import Poly.Shirt.Util.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DoiTraDAOImpl implements DoiTraDAO {

    @Override
    public DoiTra create(DoiTra doiTra) {
        String sql = "INSERT INTO DoiTra(HoaDonID, NhanVienID, LyDo, NgayDoiTra, TrangThai) "
                + "OUTPUT INSERTED.* "
                + "VALUES(?, ?, ?, ?, ?)";

        try (ResultSet rs = XJdbc.executeQuery(sql,
                doiTra.getHoaDonID(),
                doiTra.getNhanVienID(),
                doiTra.getLyDo(),
                doiTra.getNgayDoiTra() != null ? new java.sql.Timestamp(doiTra.getNgayDoiTra().getTime()) : null,
                doiTra.getTrangThai())) {

            if (rs.next()) {
                return readFromResultSet(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(DoiTra doiTra) {
        String sql = "UPDATE DoiTra SET HoaDonID=?, NhanVienID=?, LyDo=?, "
                + "NgayDoiTra=?, TrangThai=? WHERE DoiTraID=?";
        XJdbc.executeUpdate(sql,
                doiTra.getHoaDonID(),
                doiTra.getNhanVienID(),
                doiTra.getLyDo(),
                doiTra.getNgayDoiTra() != null ? new java.sql.Timestamp(doiTra.getNgayDoiTra().getTime()) : null,
                doiTra.getTrangThai(),
                doiTra.getDoiTraID());
    }

    @Override
    public void deleteById(Integer doiTraID) {
        String sql = "DELETE FROM DoiTra WHERE DoiTraID=?";
        XJdbc.executeUpdate(sql, doiTraID);
    }

    @Override
    public List<DoiTra> findAll() {
        String sql = "SELECT * FROM DoiTra";
        return select(sql);
    }

    @Override
    public DoiTra findById(Integer doiTraID) {
        String sql = "SELECT * FROM DoiTra WHERE DoiTraID=?";
        List<DoiTra> list = select(sql, doiTraID);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<DoiTra> findByHoaDonID(int hoaDonID) {
        String sql = "SELECT * FROM DoiTra WHERE HoaDonID=?";
        return select(sql, hoaDonID);
    }

    @Override
    public List<DoiTra> findByTrangThai(int trangThai) {
        String sql = "SELECT * FROM DoiTra WHERE TrangThai=?";
        return select(sql, trangThai);
    }

    @Override
    public List<DoiTra> findByNhanVienID(int nhanVienID) {
        String sql = "SELECT * FROM DoiTra WHERE NhanVienID=?";
        return select(sql, nhanVienID);
    }

    @Override
    public List<DoiTra> findByDateRange(Date fromDate, Date toDate) {
        String sql = "SELECT * FROM DoiTra WHERE NgayDoiTra BETWEEN ? AND ?";
        return select(sql,
                new java.sql.Timestamp(fromDate.getTime()),
                new java.sql.Timestamp(toDate.getTime()));
    }

    @Override
    public boolean updateTrangThai(int doiTraID, int trangThai) {
        String sql = "UPDATE DoiTra SET TrangThai=? WHERE DoiTraID=?";
        return XJdbc.executeUpdate(sql, trangThai, doiTraID) > 0;
    }

    private List<DoiTra> select(String sql, Object... args) {
        List<DoiTra> list = new ArrayList<>();
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

    private DoiTra readFromResultSet(ResultSet rs) throws SQLException {
        DoiTra dt = new DoiTra();
        dt.setDoiTraID(rs.getInt("DoiTraID"));
        dt.setHoaDonID(rs.getInt("HoaDonID"));
        dt.setNhanVienID(rs.getInt("NhanVienID"));
        dt.setLyDo(rs.getString("LyDo"));
        dt.setNgayDoiTra(rs.getDate("NgayDoiTra"));
        dt.setTrangThai(rs.getInt("TrangThai"));
        return dt;
    }

    @Override
    public int countByField(String fieldName, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
