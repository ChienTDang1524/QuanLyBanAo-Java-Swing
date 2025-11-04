package Poly.Shirt.Ui;

import java.net.InetAddress;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Poly.Shirt.Util.XDialog;
import Poly.Shirt.dao.TaiKhoanDAO;
import Poly.Shirt.impl.TaiKhoanDAOImpl;
import Poly.Shirt.Enity.TaiKhoan;
import Poly.Shirt.Util.AppContext;
import Poly.Shirt.impl.LichSuDangNhapDAOImpl;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAOImpl();

    public LoginFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Đăng nhập hệ thống bán quần áo");
        setSize(420, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Font chính
        Font font = new Font("Segoe UI", Font.PLAIN, 16);

        // Màu nền
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(new Color(245, 245, 245));
        setContentPane(content);

        // Tiêu đề
        JLabel lblTitle = new JLabel("ĐĂNG NHẬP HỆ THỐNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        content.add(lblTitle, BorderLayout.NORTH);

        // Panel nhập
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tên đăng nhập
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Tên đăng nhập:"), gbc);

        txtUsername = new JTextField();
        txtUsername.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        panel.add(txtUsername, gbc);

        // Mật khẩu
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Mật khẩu:"), gbc);

        txtPassword = new JPasswordField();
        txtPassword.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        panel.add(txtPassword, gbc);

        // Nút đăng nhập
        btnLogin = new JButton("Đăng nhập");
        btnLogin.setFont(font);
        btnLogin.setBackground(new Color(33, 150, 243));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnLogin, gbc);

        content.add(panel, BorderLayout.CENTER);

        // Bắt sự kiện
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        // Allow login on Enter key press
        txtPassword.addActionListener(e -> login());
    }

    private void login() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String ipAddress = getClientIP();

        if (username.isEmpty() || password.isEmpty()) {
            XDialog.alert("Vui lòng nhập tên đăng nhập và mật khẩu!");
            return;
        }

        try {
            TaiKhoan taiKhoan = taiKhoanDAO.findByUsername(username);
            
            if (taiKhoan == null) {
                // Log failed login attempt
                new LichSuDangNhapDAOImpl().logLogin(0, ipAddress, false);
                XDialog.alert("Tên đăng nhập không tồn tại!");
                return;
            }

            if (!taiKhoan.getMatKhau().equals(password)) {
                // Log failed login attempt
                new LichSuDangNhapDAOImpl().logLogin(taiKhoan.getTaiKhoanID(), ipAddress, false);
                XDialog.alert("Mật khẩu không chính xác!");
                return;
            }

            if (!taiKhoan.isTrangThai()) {
                // Log failed login attempt (account disabled)
                new LichSuDangNhapDAOImpl().logLogin(taiKhoan.getTaiKhoanID(), ipAddress, false);
                XDialog.alert("Tài khoản đã bị khóa!");
                return;
            }

            // Log successful login
            new LichSuDangNhapDAOImpl().logLogin(taiKhoan.getTaiKhoanID(), ipAddress, true);
            
            AppContext.setTaiKhoanDangNhap(taiKhoan);
            XDialog.alert("Đăng nhập thành công!");
            new HomeFrame(taiKhoan).setVisible(true);
            dispose();
        } catch (Exception e) {
            XDialog.alert("Lỗi hệ thống khi đăng nhập. Vui lòng thử lại sau!");
            e.printStackTrace();
        }
    }

    private String getClientIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "127.0.0.1"; // Default to localhost if can't determine IP
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}