package Poly.Shirt.Ui;

import Poly.Shirt.Enity.TaiKhoan;
import Poly.Shirt.Enity.NhanVien;
import Poly.Shirt.dao.TaiKhoanDAO;
import Poly.Shirt.dao.NhanVienDAO;
import Poly.Shirt.impl.TaiKhoanDAOImpl;
import Poly.Shirt.impl.NhanVienDAOImpl;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class TaiKhoanPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtTenDangNhap, txtMatKhau;
    private JTextField txtTimTenDangNhap, txtTimTenNV;
    private JComboBox<String> cboVaiTro, cboNhanVien;
    private JComboBox<String> cboTrangThaiTim, cboVaiTroTim;
    private JCheckBox chkTrangThai;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi;
    private JButton btnTimKiem, btnLamMoiTim;
    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAOImpl();
    private NhanVienDAO nhanVienDAO = new NhanVienDAOImpl();
    private Map<String, Integer> nhanVienMap = new HashMap<>();
    
    // Màu sắc
    private final Color MAIN_COLOR = new Color(70, 130, 180); // SteelBlue
    private final Color SECONDARY_COLOR = new Color(240, 240, 240); // Light gray
    private final Color BUTTON_ADD_COLOR = new Color(46, 125, 50);
    private final Color BUTTON_EDIT_COLOR = new Color(41, 98, 255);
    private final Color BUTTON_DELETE_COLOR = new Color(198, 40, 40);
    private final Color BUTTON_REFRESH_COLOR = new Color(158, 158, 158);

    public TaiKhoanPanel() {
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Panel tiêu đề
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        JLabel lblTitle = new JLabel("QUẢN LÝ TÀI KHOẢN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(MAIN_COLOR);
        titlePanel.add(lblTitle);
        
        // Panel form nhập liệu
        JPanel pnlForm = new JPanel(new GridLayout(5, 2, 10, 10));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin tài khoản"));
        pnlForm.setBackground(SECONDARY_COLOR);

        addFormField(pnlForm, "Tên đăng nhập:", txtTenDangNhap = new JTextField());
        addFormField(pnlForm, "Mật khẩu:", txtMatKhau = new JTextField());
        
        // Thêm combobox cho Nhân viên
        JLabel lblNhanVien = new JLabel("Nhân viên:");
        lblNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pnlForm.add(lblNhanVien);
        
        cboNhanVien = new JComboBox<>();
        cboNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cboNhanVien.setBackground(Color.WHITE);
        loadNhanVienComboBox();
        pnlForm.add(cboNhanVien);
        
        // Thêm combobox cho Vai trò
        JLabel lblVaiTro = new JLabel("Vai trò:");
        lblVaiTro.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pnlForm.add(lblVaiTro);
        
        cboVaiTro = new JComboBox<>();
        cboVaiTro.addItem("Quản lý");
        cboVaiTro.addItem("Nhân viên bán hàng");
        cboVaiTro.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cboVaiTro.setBackground(Color.WHITE);
        pnlForm.add(cboVaiTro);

        pnlForm.add(new JLabel("Trạng thái:"));
        chkTrangThai = new JCheckBox("Hoạt động");
        chkTrangThai.setSelected(true);
        chkTrangThai.setBackground(SECONDARY_COLOR);
        pnlForm.add(chkTrangThai);

        // Panel nút chức năng
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlButton.setBackground(SECONDARY_COLOR);

        btnThem = createButton("Thêm", BUTTON_ADD_COLOR);
        btnSua = createButton("Sửa", BUTTON_EDIT_COLOR);
        btnXoa = createButton("Xóa", BUTTON_DELETE_COLOR);
        btnLamMoi = createButton("Làm mới", BUTTON_REFRESH_COLOR);

        pnlButton.add(btnThem);
        pnlButton.add(btnSua);
        pnlButton.add(btnXoa);
        pnlButton.add(btnLamMoi);

        // Panel tìm kiếm
        JPanel pnlTimKiem = new JPanel(new GridBagLayout());
        pnlTimKiem.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            "Tìm kiếm tài khoản", 
            TitledBorder.LEFT, 
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            MAIN_COLOR
        ));
        pnlTimKiem.setBackground(SECONDARY_COLOR);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Dòng 1: Tìm kiếm cơ bản
        gbc.gridx = 0; gbc.gridy = 0;
        pnlTimKiem.add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        txtTimTenDangNhap = new JTextField(15);
        pnlTimKiem.add(txtTimTenDangNhap, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        pnlTimKiem.add(new JLabel("Tên nhân viên:"), gbc);
        gbc.gridx = 3; gbc.gridy = 0;
        txtTimTenNV = new JTextField(15);
        pnlTimKiem.add(txtTimTenNV, gbc);
        
        // Dòng 2: Trạng thái và vai trò
        gbc.gridx = 0; gbc.gridy = 1;
        pnlTimKiem.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        cboTrangThaiTim = new JComboBox<>(new String[]{"Tất cả", "Hoạt động", "Không hoạt động"});
        pnlTimKiem.add(cboTrangThaiTim, gbc);
        
        gbc.gridx = 2; gbc.gridy = 1;
        pnlTimKiem.add(new JLabel("Vai trò:"), gbc);
        gbc.gridx = 3; gbc.gridy = 1;
        cboVaiTroTim = new JComboBox<>(new String[]{"Tất cả", "Quản lý", "Nhân viên bán hàng"});
        pnlTimKiem.add(cboVaiTroTim, gbc);
        
        // Dòng 3: Nút tìm kiếm
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.CENTER;
        JPanel pnlButtonTim = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        pnlButtonTim.setBackground(SECONDARY_COLOR);
        
        btnTimKiem = createButton("Tìm kiếm", MAIN_COLOR);
        btnLamMoiTim = createButton("Làm mới", BUTTON_REFRESH_COLOR);
        
        pnlButtonTim.add(btnTimKiem);
        pnlButtonTim.add(btnLamMoiTim);
        
        pnlTimKiem.add(pnlButtonTim, gbc);

        // Panel top (form + button + search)
        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setBackground(SECONDARY_COLOR);
        pnlTop.add(pnlForm, BorderLayout.NORTH);
        pnlTop.add(pnlButton, BorderLayout.CENTER);
        pnlTop.add(pnlTimKiem, BorderLayout.SOUTH);

        // Bảng danh sách
        model = new DefaultTableModel(new Object[]{"ID", "Tên đăng nhập", "Mật khẩu", "Nhân viên", "Vai trò", "Trạng thái"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(MAIN_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setBackground(Color.WHITE);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(true);

        // Sự kiện click vào bảng
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtTenDangNhap.setText(model.getValueAt(row, 1).toString());
                    txtMatKhau.setText(model.getValueAt(row, 2).toString());
                    
                    // Tìm và chọn đúng nhân viên trong combobox
                    String nhanVienInfo = model.getValueAt(row, 3).toString();
                    for (int i = 0; i < cboNhanVien.getItemCount(); i++) {
                        if (cboNhanVien.getItemAt(i).equals(nhanVienInfo)) {
                            cboNhanVien.setSelectedIndex(i);
                            break;
                        }
                    }
                    
                    String vaiTro = model.getValueAt(row, 4).toString();
                    if (vaiTro.equals("Quản lý")) {
                        cboVaiTro.setSelectedIndex(0);
                    } else {
                        cboVaiTro.setSelectedIndex(1);
                    }
                    
                    chkTrangThai.setSelected(model.getValueAt(row, 5).equals("Hoạt động"));
                }
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách tài khoản"));

        // Layout chính
        add(titlePanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(pnlTop, BorderLayout.NORTH);
        centerPanel.add(scroll, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Thêm sự kiện cho các nút
        btnThem.addActionListener(e -> themTaiKhoan());
        btnSua.addActionListener(e -> suaTaiKhoan());
        btnXoa.addActionListener(e -> xoaTaiKhoan());
        btnLamMoi.addActionListener(e -> lamMoiForm());
        
        // Sự kiện tìm kiếm
        btnTimKiem.addActionListener(e -> timKiemTaiKhoan());
        btnLamMoiTim.addActionListener(e -> lamMoiTimKiem());
        
        // Cho phép tìm kiếm khi nhấn Enter
        KeyAdapter enterKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    timKiemTaiKhoan();
                }
            }
        };
        
        txtTimTenDangNhap.addKeyListener(enterKeyAdapter);
        txtTimTenNV.addKeyListener(enterKeyAdapter);
    }

    private void loadNhanVienComboBox() {
        nhanVienMap.clear();
        cboNhanVien.removeAllItems();
        
        List<NhanVien> list = nhanVienDAO.findAll();
        for (NhanVien nv : list) {
            String displayName = nv.getTenNV() + " (ID: " + nv.getNhanVienID() + ")";
            nhanVienMap.put(displayName, nv.getNhanVienID());
            cboNhanVien.addItem(displayName);
        }
    }

    private boolean kiemTraNhanVienDaCoTaiKhoan(int nhanVienID) {
        List<TaiKhoan> dsTaiKhoan = taiKhoanDAO.findAll();
        for (TaiKhoan tk : dsTaiKhoan) {
            if (tk.getNhanVienID() == nhanVienID) {
                return true;
            }
        }
        return false;
    }

    private void addFormField(JPanel panel, String label, JTextField textField) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(lbl);

        textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 5, 5, 5)
        ));
        panel.add(textField);
    }

    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);

        // Hiệu ứng hover
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });

        return btn;
    }

    private void loadData() {
        model.setRowCount(0);
        List<TaiKhoan> list = taiKhoanDAO.findAll();
        for (TaiKhoan tk : list) {
            // Lấy thông tin nhân viên
            NhanVien nv = nhanVienDAO.findById(tk.getNhanVienID());
            String tenNhanVien = nv != null ? nv.getTenNV() + " (ID: " + nv.getNhanVienID() + ")" : "Không xác định";
            
            model.addRow(new Object[]{
                tk.getTaiKhoanID(),
                tk.getTenDangNhap(),
                tk.getMatKhau(),
                tenNhanVien,
                tk.getVaiTroID() == 1 ? "Quản lý" : "Nhân viên bán hàng",
                tk.isTrangThai() ? "Hoạt động" : "Không hoạt động"
            });
        }
    }

    private void timKiemTaiKhoan() {
        try {
            String tenDangNhap = txtTimTenDangNhap.getText().trim();
            String tenNV = txtTimTenNV.getText().trim();
            
            Boolean trangThai = null;
            if (cboTrangThaiTim.getSelectedIndex() == 1) {
                trangThai = true;
            } else if (cboTrangThaiTim.getSelectedIndex() == 2) {
                trangThai = false;
            }
            
            Integer vaiTro = null;
            if (cboVaiTroTim.getSelectedIndex() == 1) {
                vaiTro = 1; // Quản lý
            } else if (cboVaiTroTim.getSelectedIndex() == 2) {
                vaiTro = 2; // Nhân viên bán hàng
            }
            
            // Gọi DAO để tìm kiếm
            List<TaiKhoan> list = taiKhoanDAO.findByCriteria(tenDangNhap, tenNV, trangThai, vaiTro);
            
            // Hiển thị kết quả
            model.setRowCount(0);
            for (TaiKhoan tk : list) {
                // Lấy thông tin nhân viên
                NhanVien nv = nhanVienDAO.findById(tk.getNhanVienID());
                String tenNhanVien = nv != null ? nv.getTenNV() + " (ID: " + nv.getNhanVienID() + ")" : "Không xác định";
                
                model.addRow(new Object[]{
                    tk.getTaiKhoanID(),
                    tk.getTenDangNhap(),
                    tk.getMatKhau(),
                    tenNhanVien,
                    tk.getVaiTroID() == 1 ? "Quản lý" : "Nhân viên bán hàng",
                    tk.isTrangThai() ? "Hoạt động" : "Không hoạt động"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void lamMoiTimKiem() {
        txtTimTenDangNhap.setText("");
        txtTimTenNV.setText("");
        cboTrangThaiTim.setSelectedIndex(0);
        cboVaiTroTim.setSelectedIndex(0);
        loadData(); // Tải lại toàn bộ dữ liệu
    }

    private void themTaiKhoan() {
        try {
            // Kiểm tra nhân viên đã có tài khoản chưa
            String selectedNhanVien = (String) cboNhanVien.getSelectedItem();
            int nhanVienID = nhanVienMap.get(selectedNhanVien);
            
            if (kiemTraNhanVienDaCoTaiKhoan(nhanVienID)) {
                JOptionPane.showMessageDialog(this, "Nhân viên này đã có tài khoản!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Kiểm tra tên đăng nhập trống
            if (txtTenDangNhap.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Kiểm tra mật khẩu trống
            if (txtMatKhau.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            TaiKhoan tk = new TaiKhoan();
            tk.setTenDangNhap(txtTenDangNhap.getText());
            tk.setMatKhau(txtMatKhau.getText());
            tk.setNhanVienID(nhanVienID);
            tk.setVaiTroID(cboVaiTro.getSelectedIndex() + 1); // 1 hoặc 2
            tk.setTrangThai(chkTrangThai.isSelected());

            taiKhoanDAO.create(tk);
            JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadData();
            lamMoiForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm tài khoản: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaTaiKhoan() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần sửa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Kiểm tra tên đăng nhập trống
            if (txtTenDangNhap.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Kiểm tra mật khẩu trống
            if (txtMatKhau.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            TaiKhoan tk = new TaiKhoan();
            tk.setTaiKhoanID((int) model.getValueAt(row, 0));
            tk.setTenDangNhap(txtTenDangNhap.getText());
            tk.setMatKhau(txtMatKhau.getText());
            
            // Lấy ID nhân viên từ combobox
            String selectedNhanVien = (String) cboNhanVien.getSelectedItem();
            tk.setNhanVienID(nhanVienMap.get(selectedNhanVien));
            
            tk.setVaiTroID(cboVaiTro.getSelectedIndex() + 1); // 1 hoặc 2
            tk.setTrangThai(chkTrangThai.isSelected());

            taiKhoanDAO.update(tk);
            JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật tài khoản: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaTaiKhoan() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần xóa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa tài khoản này?\nLưu ý: Thao tác này chỉ xóa tài khoản, không xóa nhân viên.",
                "Xác nhận xóa tài khoản",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int taiKhoanID = (int) model.getValueAt(row, 0);
                taiKhoanDAO.deleteById(taiKhoanID);
                JOptionPane.showMessageDialog(this, "Xóa tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadData();
                lamMoiForm();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa tài khoản: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void lamMoiForm() {
        txtTenDangNhap.setText("");
        txtMatKhau.setText("");
        if (cboNhanVien.getItemCount() > 0) {
            cboNhanVien.setSelectedIndex(0);
        }
        cboVaiTro.setSelectedIndex(0);
        chkTrangThai.setSelected(true);
        table.clearSelection();
    }
}