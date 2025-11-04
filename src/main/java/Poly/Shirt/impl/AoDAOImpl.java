package Poly.Shirt.impl;

import Poly.Shirt.Enity.Ao;
import Poly.Shirt.dao.AoDAO;
import Poly.Shirt.Util.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AoDAOImpl implements AoDAO {

    @Override
public Ao create(Ao entity) {
    String sql = "INSERT INTO Ao(TenAo, LoaiAoID, ThuongHieuID, ChatLieuID, MoTaChiTiet, TrangThai) "
               + "OUTPUT INSERTED.AoID " // Thêm dòng này để lấy ID vừa tạo
               + "VALUES(?, ?, ?, ?, ?, ?)";
    
    try {
        ResultSet rs = XJdbc.executeQuery(sql,
                entity.getTenAo(),
                entity.getLoaiAoID(),
                entity.getThuongHieuID(),
                entity.getChatLieuID(),
                entity.getMoTaChiTiet(),
                entity.isTrangThai());
        
        if (rs.next()) {
            entity.setAoID(rs.getInt(1));
        }
        return entity;
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

    @Override
    public void update(Ao entity) {
        String sql = "UPDATE Ao SET TenAo=?, LoaiAoID=?, ThuongHieuID=?, ChatLieuID=?, MoTaChiTiet=?, TrangThai=? WHERE AoID=?";
        XJdbc.executeUpdate(sql,
                entity.getTenAo(),
                entity.getLoaiAoID(),
                entity.getThuongHieuID(),
                entity.getChatLieuID(),
                entity.getMoTaChiTiet(),
                entity.isTrangThai(),
                entity.getAoID());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM Ao WHERE AoID=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public List<Ao> findAll() {
        String sql = "SELECT * FROM Ao";
        return select(sql);
    }

    @Override
    public Ao findById(Integer id) {
        String sql = "SELECT * FROM Ao WHERE AoID=?";
        List<Ao> list = select(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Ao> findByCriteria(String tenAo, Integer loaiAoID, Integer thuongHieuID, Integer chatLieuID, Boolean trangThai) {
        StringBuilder sql = new StringBuilder("SELECT * FROM Ao WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (tenAo != null && !tenAo.isEmpty()) {
            sql.append(" AND TenAo LIKE ?");
            params.add("%" + tenAo + "%");
        }

        if (loaiAoID != null) {
            sql.append(" AND LoaiAoID = ?");
            params.add(loaiAoID);
        }

        if (thuongHieuID != null) {
            sql.append(" AND ThuongHieuID = ?");
            params.add(thuongHieuID);
        }

        if (chatLieuID != null) {
            sql.append(" AND ChatLieuID = ?");
            params.add(chatLieuID);
        }

        if (trangThai != null) {
            sql.append(" AND TrangThai = ?");
            params.add(trangThai);
        }

        return select(sql.toString(), params.toArray());
    }

    @Override
    public List<Ao> findByTenAo(String tenAo) {
        String sql = "SELECT * FROM Ao WHERE TenAo LIKE ?";
        return select(sql, "%" + tenAo + "%");
    }

    private List<Ao> select(String sql, Object... args) {
        List<Ao> list = new ArrayList<>();
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

    private Ao readFromResultSet(ResultSet rs) throws SQLException {
        Ao ao = new Ao();
        ao.setAoID(rs.getInt("AoID"));
        ao.setTenAo(rs.getString("TenAo"));
        ao.setLoaiAoID(rs.getInt("LoaiAoID"));
        ao.setThuongHieuID(rs.getInt("ThuongHieuID"));
        ao.setChatLieuID(rs.getInt("ChatLieuID"));
        ao.setMoTaChiTiet(rs.getString("MoTaChiTiet"));
        ao.setTrangThai(rs.getBoolean("TrangThai"));
        return ao;
    }

    @Override
    public int countByLoaiAoID(int loaiAoID) {
        String sql = "SELECT COUNT(*) FROM Ao WHERE LoaiAoID=?";
        try {
            ResultSet rs = XJdbc.executeQuery(sql, loaiAoID);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int countByThuongHieuID(int thuongHieuID) {
        String sql = "SELECT COUNT(*) FROM Ao WHERE ThuongHieuID=?";
        try {
            ResultSet rs = XJdbc.executeQuery(sql, thuongHieuID);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int countByChatLieuID(int chatLieuID) {
        String sql = "SELECT COUNT(*) FROM Ao WHERE ChatLieuID=?";
        try {
            ResultSet rs = XJdbc.executeQuery(sql, chatLieuID);
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
        String sql = "SELECT COUNT(*) FROM Ao WHERE " + fieldName + "=?";
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
    public int getLastInsertID() {
        String sql = "SELECT SCOPE_IDENTITY()"; // Cho SQL Server
        // Hoặc "SELECT LAST_INSERT_ID()" nếu dùng MySQL
        try {
            ResultSet rs = XJdbc.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
}
