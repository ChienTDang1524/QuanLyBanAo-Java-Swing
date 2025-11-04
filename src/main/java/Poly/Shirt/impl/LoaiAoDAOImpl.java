package Poly.Shirt.impl;

import Poly.Shirt.Enity.LoaiAo;
import Poly.Shirt.dao.LoaiAoDAO;
import Poly.Shirt.Util.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoaiAoDAOImpl implements LoaiAoDAO {

    @Override
    public LoaiAo create(LoaiAo entity) {
        String sql = "INSERT INTO LoaiAo(TenLoai) VALUES(?)";
        XJdbc.executeUpdate(sql, entity.getTenLoai());
        return entity;
    }

    @Override
    public void update(LoaiAo entity) {
        String sql = "UPDATE LoaiAo SET TenLoai=? WHERE LoaiAoID=?";
        XJdbc.executeUpdate(sql, entity.getTenLoai(), entity.getLoaiAoID());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM LoaiAo WHERE LoaiAoID=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public List<LoaiAo> findAll() {
        String sql = "SELECT * FROM LoaiAo";
        return select(sql);
    }

    @Override
    public LoaiAo findById(Integer id) {
        String sql = "SELECT * FROM LoaiAo WHERE LoaiAoID=?";
        List<LoaiAo> list = select(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public String getTenLoaiAoByID(int id) {
        String sql = "SELECT TenLoai FROM LoaiAo WHERE LoaiAoID=?";
        try {
            ResultSet rs = XJdbc.executeQuery(sql, id);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public int getIdByName(String tenLoai) {
        String sql = "SELECT LoaiAoID FROM LoaiAo WHERE TenLoai=?";
        try {
            ResultSet rs = XJdbc.executeQuery(sql, tenLoai);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    private List<LoaiAo> select(String sql, Object... args) {
        List<LoaiAo> list = new ArrayList<>();
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

    private LoaiAo readFromResultSet(ResultSet rs) throws SQLException {
        LoaiAo loai = new LoaiAo();
        loai.setLoaiAoID(rs.getInt("LoaiAoID"));
        loai.setTenLoai(rs.getString("TenLoai"));
        return loai;
    }

    @Override
    public int countByField(String fieldName, Object value) {
        String sql = "SELECT COUNT(*) FROM " + this.getClass().getSimpleName().replace("DAOImpl", "") + " WHERE " + fieldName + "=?";
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
}
