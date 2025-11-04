package Poly.Shirt.impl;

import Poly.Shirt.Enity.SizeAo;
import Poly.Shirt.dao.SizeAoDAO;
import Poly.Shirt.Util.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SizeAoDAOImpl implements SizeAoDAO {

    @Override
    public SizeAo create(SizeAo entity) {
        String sql = "INSERT INTO SizeAo(TenSize) VALUES(?)";
        XJdbc.executeUpdate(sql, entity.getTenSize());
        return entity;
    }

    @Override
    public void update(SizeAo entity) {
        String sql = "UPDATE SizeAo SET TenSize=? WHERE SizeID=?";
        XJdbc.executeUpdate(sql, entity.getTenSize(), entity.getSizeID());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM SizeAo WHERE SizeID=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public List<SizeAo> findAll() {
        String sql = "SELECT * FROM SizeAo";
        return select(sql);
    }

    @Override
    public SizeAo findById(Integer id) {
        String sql = "SELECT * FROM SizeAo WHERE SizeID=?";
        List<SizeAo> list = select(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public String getTenSizeByID(int id) {
        String sql = "SELECT TenSize FROM SizeAo WHERE SizeID=?";
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
    public int getIdByName(String tenSize) {
        String sql = "SELECT SizeID FROM SizeAo WHERE TenSize=?";
        try {
            ResultSet rs = XJdbc.executeQuery(sql, tenSize);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    private List<SizeAo> select(String sql, Object... args) {
        List<SizeAo> list = new ArrayList<>();
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

    private SizeAo readFromResultSet(ResultSet rs) throws SQLException {
        SizeAo size = new SizeAo();
        size.setSizeID(rs.getInt("SizeID"));
        size.setTenSize(rs.getString("TenSize"));
        return size;
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
