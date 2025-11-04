package Poly.Shirt.impl;

import Poly.Shirt.Enity.ChatLieu;
import Poly.Shirt.dao.ChatLieuDAO;
import Poly.Shirt.Util.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatLieuDAOImpl implements ChatLieuDAO {

    @Override
    public ChatLieu create(ChatLieu entity) {
        String sql = "INSERT INTO ChatLieu(TenChatLieu) VALUES(?)";
        XJdbc.executeUpdate(sql, entity.getTenChatLieu());
        return entity;
    }

    @Override
    public void update(ChatLieu entity) {
        String sql = "UPDATE ChatLieu SET TenChatLieu=? WHERE ChatLieuID=?";
        XJdbc.executeUpdate(sql, entity.getTenChatLieu(), entity.getChatLieuID());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM ChatLieu WHERE ChatLieuID=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public List<ChatLieu> findAll() {
        String sql = "SELECT * FROM ChatLieu";
        return select(sql);
    }

    @Override
    public ChatLieu findById(Integer id) {
        String sql = "SELECT * FROM ChatLieu WHERE ChatLieuID=?";
        List<ChatLieu> list = select(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public String getTenChatLieuByID(int id) {
        String sql = "SELECT TenChatLieu FROM ChatLieu WHERE ChatLieuID=?";
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
    public int getIdByName(String tenChatLieu) {
        String sql = "SELECT ChatLieuID FROM ChatLieu WHERE TenChatLieu=?";
        try {
            ResultSet rs = XJdbc.executeQuery(sql, tenChatLieu);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    private List<ChatLieu> select(String sql, Object... args) {
        List<ChatLieu> list = new ArrayList<>();
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

    private ChatLieu readFromResultSet(ResultSet rs) throws SQLException {
        ChatLieu cl = new ChatLieu();
        cl.setChatLieuID(rs.getInt("ChatLieuID"));
        cl.setTenChatLieu(rs.getString("TenChatLieu"));
        return cl;
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
