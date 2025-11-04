package Poly.Shirt.impl;

import Poly.Shirt.Enity.ThuongHieu;
import Poly.Shirt.dao.ThuongHieuDAO;
import Poly.Shirt.Util.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThuongHieuDAOImpl implements ThuongHieuDAO {

    @Override
    public ThuongHieu create(ThuongHieu entity) {
        String sql = "INSERT INTO ThuongHieu(TenThuongHieu) VALUES(?)";
        XJdbc.executeUpdate(sql, entity.getTenThuongHieu());
        return entity;
    }

    @Override
    public void update(ThuongHieu entity) {
        String sql = "UPDATE ThuongHieu SET TenThuongHieu=? WHERE ThuongHieuID=?";
        XJdbc.executeUpdate(sql, entity.getTenThuongHieu(), entity.getThuongHieuID());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM ThuongHieu WHERE ThuongHieuID=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public List<ThuongHieu> findAll() {
        String sql = "SELECT * FROM ThuongHieu";
        return select(sql);
    }

    @Override
    public ThuongHieu findById(Integer id) {
        String sql = "SELECT * FROM ThuongHieu WHERE ThuongHieuID=?";
        List<ThuongHieu> list = select(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public String getTenThuongHieuByID(int id) {
        String sql = "SELECT TenThuongHieu FROM ThuongHieu WHERE ThuongHieuID=?";
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
    public int getIdByName(String tenThuongHieu) {
        String sql = "SELECT ThuongHieuID FROM ThuongHieu WHERE TenThuongHieu=?";
        try {
            ResultSet rs = XJdbc.executeQuery(sql, tenThuongHieu);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    private List<ThuongHieu> select(String sql, Object... args) {
        List<ThuongHieu> list = new ArrayList<>();
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

    private ThuongHieu readFromResultSet(ResultSet rs) throws SQLException {
        ThuongHieu th = new ThuongHieu();
        th.setThuongHieuID(rs.getInt("ThuongHieuID"));
        th.setTenThuongHieu(rs.getString("TenThuongHieu"));
        return th;
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
