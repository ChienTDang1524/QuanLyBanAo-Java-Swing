package Poly.Shirt.Ui;

import Poly.Shirt.Enity.NhanVien;
import Poly.Shirt.Enity.TaiKhoan;
import Poly.Shirt.dao.NhanVienDAO;
import Poly.Shirt.impl.NhanVienDAOImpl;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import Poly.Shirt.Util.AppContext;

public class HomeFrame extends JFrame {

    private JPanel sidebar;
    private JPanel content;
    private Color sidebarColor = new Color(45, 55, 72);
    private Color hoverColor = new Color(60, 75, 100);
    private Color selectedColor = new Color(0, 150, 200);
    private Color textColor = new Color(240, 240, 240);
    private Color accountInfoColor = new Color(200, 200, 200);
    private JPanel currentSelectedMenu;

    private TaiKhoan taiKhoanDangNhap;
    private NhanVienDAO nhanVienDAO = new NhanVienDAOImpl();

    public HomeFrame(TaiKhoan taiKhoan) {
        if (taiKhoan == null) {
            throw new IllegalArgumentException("TaiKhoan không được null");
        }
        this.taiKhoanDangNhap = taiKhoan;
        AppContext.setTaiKhoanDangNhap(taiKhoan); // Lưu vào context
        initComponents();
    }

    private void initComponents() {
        setTitle("Trang chủ hệ thống Poly Shirt");
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sidebar
        sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(250, getHeight()));
        sidebar.setBackground(sidebarColor);
        sidebar.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(sidebarColor.darker());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("POLY SHIRT", SwingConstants.CENTER);
        lblTitle.setForeground(textColor);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(lblTitle, BorderLayout.CENTER);

        // Menu
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(sidebarColor);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Thêm các menu item
        menuPanel.add(createMenuButton("Quản lý sản phẩm", "product.png", () -> showProductManagement()));
        menuPanel.add(createMenuButton("Quản lý đơn hàng", "order.png", () -> showOrderManagement()));
        menuPanel.add(createMenuButton("Quản lý khách hàng", "customer.png", () -> showCustomerManagement()));
        menuPanel.add(createMenuButton("Quản lý đổi trả", "product-return.png", () -> showReturnManagement()));

        // Chỉ thêm menu quản lý nhân viên nếu là Admin
        if (AppContext.isAdmin()) {
            menuPanel.add(createMenuButton("Quản lý nhân viên", "teamwork.png", () -> showStaffManagement()));
        }

        // Chỉ thêm menu quản lý tài khoản nếu là Admin
        if (AppContext.isAdmin()) {
            menuPanel.add(createMenuButton("Quản lý tài khoản", "profile-user.png", () -> showAccountManagement()));
        }
        menuPanel.add(createMenuButton("Thống kê", "bar-chart.png", () -> showStatistics()));
        // Thêm dòng này để thêm menu Lịch sử đăng nhập
        if (AppContext.isAdmin()) {
            menuPanel.add(createMenuButton("Lịch sử đăng nhập", "history.png", () -> showLoginHistory()));
        }
        if (AppContext.isAdmin()) {
            menuPanel.add(createMenuButton("Quản lý khuyến mãi", "promotion.png", () -> showKhuyenMaiManagement()));
        }
        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
        footerPanel.setBackground(sidebarColor);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Thêm thông tin tài khoản
        JPanel accountPanel = createAccountInfoPanel();
        footerPanel.add(accountPanel);

        // Thêm đường kẻ phân cách
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(80, 80, 80));
        footerPanel.add(separator);

        // Thêm nút đăng xuất
        footerPanel.add(createMenuButton("Đăng xuất", "logout.png", () -> {
            AppContext.setTaiKhoanDangNhap(null); // Xóa thông tin đăng nhập
            new LoginFrame().setVisible(true);
            dispose();
        }));

        // Gắn các phần vào sidebar
        sidebar.add(headerPanel, BorderLayout.NORTH);
        sidebar.add(menuPanel, BorderLayout.CENTER);
        sidebar.add(footerPanel, BorderLayout.SOUTH);

        // Content chính
        content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Màn hình chào mừng
        showWelcomeScreen();

        // Thêm sidebar và content vào frame
        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);
    }

    private JPanel createAccountInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(sidebarColor);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Lấy thông tin nhân viên nếu có
        String tenNhanVien = "";
        if (taiKhoanDangNhap.getNhanVienID() > 0) {
            NhanVien nv = nhanVienDAO.findById(taiKhoanDangNhap.getNhanVienID());
            if (nv != null) {
                tenNhanVien = nv.getTenNV();
            }
        }

        JLabel lblAccount = new JLabel("<html><b>Tài khoản:</b> " + taiKhoanDangNhap.getTenDangNhap()
                + "<br><b>Vai trò:</b> " + (AppContext.isAdmin() ? "Quản lý" : "Nhân viên")
                + (tenNhanVien.isEmpty() ? "" : "<br><b>Tên NV:</b> " + tenNhanVien) + "</html>");
        lblAccount.setForeground(accountInfoColor);
        lblAccount.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        // Thêm icon user
        try {
            ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "/src/main/java/Poly/Shirt/Icons/user.png");
            lblAccount.setIcon(new ImageIcon(icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
            lblAccount.setIconTextGap(10);
        } catch (Exception e) {
            System.err.println("Không tải được icon user: " + e.getMessage());
        }

        panel.add(lblAccount, BorderLayout.CENTER);
        return panel;
    }

    private void showWelcomeScreen() {
        content.removeAll();

        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setBackground(new Color(245, 245, 245));

        JLabel lblWelcome = new JLabel("Chào mừng bạn đến với hệ thống POLY SHIRT!");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblWelcome.setForeground(new Color(60, 60, 60));

        JLabel lblSubWelcome = new JLabel("Hệ thống quản lý cửa hàng thời trang");
        lblSubWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSubWelcome.setForeground(new Color(120, 120, 120));

        // Thông tin đăng nhập
        String roleInfo = AppContext.isAdmin()
                ? "Bạn đang đăng nhập với quyền Quản lý hệ thống"
                : "Bạn đang đăng nhập với quyền Nhân viên bán hàng";

        JLabel lblLoginInfo = new JLabel(roleInfo);
        lblLoginInfo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblLoginInfo.setForeground(AppContext.isAdmin() ? new Color(0, 100, 0) : new Color(0, 0, 150));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(245, 245, 245));
        textPanel.add(lblWelcome);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(lblSubWelcome);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(lblLoginInfo);

        welcomePanel.add(textPanel);
        content.add(welcomePanel, BorderLayout.CENTER);

        content.revalidate();
        content.repaint();
    }

    // Các phương thức showManagement() giữ nguyên như cũ
    private void showProductManagement() {
        content.removeAll();
        SanPhamPanel aoPanel = new SanPhamPanel();
        content.add(aoPanel, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    private void showOrderManagement() {

        content.removeAll();
        QuanLyDonHang quanLyDonHangPanel = new QuanLyDonHang();
        content.add(quanLyDonHangPanel, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();

    }

    private void showCustomerManagement() {
        content.removeAll();
        KhachHangPanel khachHangPanel = new KhachHangPanel();
        content.add(khachHangPanel, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    private void showReturnManagement() {
        content.removeAll();
        DoiTraPanel doiTraPanel = new DoiTraPanel();
        content.add(doiTraPanel, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    private void showStaffManagement() {
        if (!AppContext.isAdmin()) {
            JOptionPane.showMessageDialog(this,
                    "Bạn không có quyền truy cập chức năng này!",
                    "Lỗi quyền hạn",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        content.removeAll();
        NhanVienPanel nhanVienPanel = new NhanVienPanel();
        content.add(nhanVienPanel, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    private void showLoginHistory() {
        if (!AppContext.isAdmin()) {
            JOptionPane.showMessageDialog(this,
                    "Bạn không có quyền truy cập chức năng này!",
                    "Lỗi quyền hạn",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        content.removeAll();
        LichSuDangNhapPanel loginHistoryPanel = new LichSuDangNhapPanel();
        content.add(loginHistoryPanel, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    private void showAccountManagement() {
        if (!AppContext.isAdmin()) {
            JOptionPane.showMessageDialog(this,
                    "Bạn không có quyền truy cập chức năng này!",
                    "Lỗi quyền hạn",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        content.removeAll();
        TaiKhoanPanel taiKhoanPanel = new TaiKhoanPanel();
        content.add(taiKhoanPanel, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    private void showKhuyenMaiManagement() {
        if (!AppContext.isAdmin()) {
            JOptionPane.showMessageDialog(this,
                    "Bạn không có quyền truy cập chức năng này!",
                    "Lỗi quyền hạn",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        content.removeAll();
        KhuyenMaiPanel khuyenMaiPanel = new KhuyenMaiPanel();
        content.add(khuyenMaiPanel, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    private void showStatistics() {
        content.removeAll();
        ThongKePanel thongKePanel = new ThongKePanel();
        content.add(thongKePanel, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    private void showContent(String title) {
        content.removeAll();
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title, SwingConstants.LEFT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 70, 70));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        contentPanel.add(titleLabel, BorderLayout.NORTH);
        content.add(contentPanel, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    private JPanel createMenuButton(String text, String iconFilename, Runnable action) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        panel.setBackground(sidebarColor);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel label = new JLabel(text);
        label.setForeground(textColor);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        try {
            String iconPath = System.getProperty("user.dir") + "/src/main/java/Poly/Shirt/Icons/" + iconFilename;
            BufferedImage original = ImageIO.read(new File(iconPath));
            BufferedImage coloredIcon = recolorIcon(original, textColor);
            label.setIcon(new ImageIcon(coloredIcon.getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
            label.setIconTextGap(15);
        } catch (IOException e) {
            label.setIcon(UIManager.getIcon("OptionPane.warningIcon"));
            System.err.println("Lỗi khi load icon: " + e.getMessage());
        }

        panel.add(label, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (panel != currentSelectedMenu) {
                    panel.setBackground(hoverColor);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (panel != currentSelectedMenu) {
                    panel.setBackground(sidebarColor);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                for (Component comp : ((JPanel) panel.getParent()).getComponents()) {
                    if (comp instanceof JPanel) {
                        comp.setBackground(sidebarColor);
                    }
                }
                panel.setBackground(selectedColor);
                currentSelectedMenu = panel;
                action.run();
            }
        });

        return panel;
    }

    private BufferedImage recolorIcon(BufferedImage original, Color newColor) {
        BufferedImage colored = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = colored.createGraphics();
        g.drawImage(original, 0, 0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(newColor);
        g.fillRect(0, 0, original.getWidth(), original.getHeight());
        g.dispose();
        return colored;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            TaiKhoan taiKhoanTest = new TaiKhoan();
            taiKhoanTest.setTenDangNhap("admin");
            taiKhoanTest.setVaiTroID(1); // Quản lý
            taiKhoanTest.setNhanVienID(1);
            new HomeFrame(taiKhoanTest).setVisible(true);
        });
    }

}
