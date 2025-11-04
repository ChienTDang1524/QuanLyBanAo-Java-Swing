package Poly.Shirt.Ui;

import javax.swing.*;
import java.awt.*;

public class SidebarPanel extends JPanel {
    public SidebarPanel(JFrame parent) {
        setPreferredSize(new Dimension(220, parent.getHeight()));
        setBackground(new Color(33, 33, 33));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel("POLY SHIRT", SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(lblTitle);
        add(Box.createVerticalStrut(20));

        add(createMenuButton("Quản lý nhân viên", () -> {
            parent.dispose();
//            new NhanVienFrame().setVisible(true);
        }));
        add(createMenuButton("Quản lý khách hàng", () -> {
            parent.dispose();
            new KhachHangPanel().setVisible(true);
        }));
        add(createMenuButton("Quản lý sản phẩm", () -> {
            parent.dispose();
//            new AoFrame().setVisible(true);
        }));
        add(createMenuButton("Thống kê", () -> {
            parent.dispose();
            
        }));

        add(Box.createVerticalGlue());
        add(createMenuButton("Đăng xuất", () -> {
            parent.dispose();
            new LoginFrame().setVisible(true);
        }));
    }

    private JPanel createMenuButton(String text, Runnable action) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panel.setBackground(new Color(44, 44, 44));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(label, BorderLayout.CENTER);

        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            Color defaultColor = panel.getBackground();
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                panel.setBackground(new Color(66, 133, 244));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                panel.setBackground(defaultColor);
            }
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                action.run();
            }
        });
        return panel;
    }
}
