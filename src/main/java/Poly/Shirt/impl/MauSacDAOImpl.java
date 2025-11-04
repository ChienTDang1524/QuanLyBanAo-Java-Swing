package Poly.Shirt.impl;

import Poly.Shirt.Enity.MauSac;
import Poly.Shirt.dao.MauSacDAO;
import Poly.Shirt.Util.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MauSacDAOImpl implements MauSacDAO {

    @Override
    public MauSac create(MauSac entity) {
        String sql = "INSERT INTO MauSac(TenMau) VALUES(?)";
        XJdbc.executeUpdate(sql, entity.getTenMau());
        return entity;
    }

    @Override
    public void update(MauSac entity) {
        String sql = "UPDATE MauSac SET TenMau=? WHERE MauSacID=?";
        XJdbc.executeUpdate(sql, entity.getTenMau(), entity.getMauSacID());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM MauSac WHERE MauSacID=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public List<MauSac> findAll() {
        String sql = "SELECT * FROM MauSac";
        return select(sql);
    }

    @Override
    public MauSac findById(Integer id) {
        String sql = "SELECT * FROM MauSac WHERE MauSacID=?";
        List<MauSac> list = select(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public String getTenMauByID(int id) {
        String sql = "SELECT TenMau FROM MauSac WHERE MauSacID=?";
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
    public int getIdByName(String tenMau) {
        String sql = "SELECT MauSacID FROM MauSac WHERE TenMau=?";
        try {
            ResultSet rs = XJdbc.executeQuery(sql, tenMau);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    private List<MauSac> select(String sql, Object... args) {
        List<MauSac> list = new ArrayList<>();
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

    private MauSac readFromResultSet(ResultSet rs) throws SQLException {
        MauSac mau = new MauSac();
        mau.setMauSacID(rs.getInt("MauSacID"));
        mau.setTenMau(rs.getString("TenMau"));
        return mau;
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
