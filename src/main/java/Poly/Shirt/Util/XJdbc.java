package Poly.Shirt.Util;

import java.sql.*;

public class XJdbc {

    // Kết nối duy nhất dùng lại
    private static Connection connection = null;

    // Thông tin kết nối
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://localhost;database=CuaHangBanAo;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASS = "123";

    // Maaở kết nối nếu cần
    public static Connection openConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL, USER, PASS);
            }
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Không thể mở kết nối CSDL", e);
        }
    }

    // Đóng kết nối
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Không thể đóng kết nối", e);
        }
    }

    // Thực hiện INSERT, UPDATE, DELETE
    public static int executeUpdate(String sql, Object... args) {
        try (PreparedStatement stmt = prepareStatement(sql, args)) {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thực hiện câu lệnh: " + sql, e);
        }
    }

    // Truy vấn SELECT
    public static ResultSet executeQuery(String sql, Object... args) {
        try {
            PreparedStatement stmt = prepareStatement(sql, args);
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi truy vấn: " + sql, e);
        }
    }

    // Lấy một giá trị duy nhất (ví dụ: tổng, max, count...)
    @SuppressWarnings("unchecked")
    public static <T> T getValue(String sql, Object... args) {
        try (ResultSet rs = executeQuery(sql, args)) {
            if (rs.next()) {
                return (T) rs.getObject(1);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy giá trị", e);
        }
    }

    // Chuẩn bị PreparedStatement, tự bind tham số
    private static PreparedStatement prepareStatement(String sql, Object... args) throws SQLException {
        Connection conn = openConnection();
        PreparedStatement stmt = sql.trim().startsWith("{")
                ? conn.prepareCall(sql)
                : conn.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }
        return stmt;
    }

    
}
