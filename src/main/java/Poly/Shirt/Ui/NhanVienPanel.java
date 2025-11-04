package Poly.Shirt.Ui;

import Poly.Shirt.Enity.NhanVien;
import Poly.Shirt.dao.NhanVienDAO;
import Poly.Shirt.impl.NhanVienDAOImpl;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class NhanVienPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtTenNV, txtSoDienThoai, txtEmail, txtDiaChi;
    private JTextField txtTimTenNV, txtTimSDT;
    private JComboBox<String> cboTrangThaiTim;
    private JCheckBox chkTrangThai;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi;
    private JButton btnTimKiem, btnLamMoiTim;
    private NhanVienDAO nhanVienDAO = new NhanVienDAOImpl();
    
    // Màu sắc
    private final Color MAIN_COLOR = new Color(70, 130, 180); // SteelBlue
    private final Color SECONDARY_COLOR = new Color(240, 240, 240); // Light gray
    private final Color BUTTON_ADD_COLOR = new Color(46, 125, 50);
    private final Color BUTTON_EDIT_COLOR = new Color(41, 98, 255);
    private final Color BUTTON_DELETE_COLOR = new Color(198, 40, 40);
    private final Color BUTTON_REFRESH_COLOR = new Color(158, 158, 158);

    public NhanVienPanel() {
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
        JLabel lblTitle = new JLabel("QUẢN LÝ NHÂN VIÊN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(MAIN_COLOR);
        titlePanel.add(lblTitle);
        
        // Panel form nhập liệu
        JPanel pnlForm = new JPanel(new GridLayout(5, 2, 10, 10));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin nhân viên"));
        pnlForm.setBackground(SECONDARY_COLOR);

        addFormField(pnlForm, "Tên nhân viên:", txtTenNV = new JTextField());
        addFormField(pnlForm, "Số điện thoại:", txtSoDienThoai = new JTextField());
        addFormField(pnlForm, "Email:", txtEmail = new JTextField());
        addFormField(pnlForm, "Địa chỉ:", txtDiaChi = new JTextField());

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
            "Tìm kiếm nhân viên", 
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
        pnlTimKiem.add(new JLabel("Tên nhân viên:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        txtTimTenNV = new JTextField(15);
        pnlTimKiem.add(txtTimTenNV, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        pnlTimKiem.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 3; gbc.gridy = 0;
        txtTimSDT = new JTextField(15);
        pnlTimKiem.add(txtTimSDT, gbc);
        
        // Dòng 2: Trạng thái
        gbc.gridx = 0; gbc.gridy = 1;
        pnlTimKiem.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        cboTrangThaiTim = new JComboBox<>(new String[]{"Tất cả", "Hoạt động", "Không hoạt động"});
        pnlTimKiem.add(cboTrangThaiTim, gbc);
        
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
        model = new DefaultTableModel(new Object[]{"ID", "Tên nhân viên", "Số điện thoại", "Email", "Địa chỉ", "Trạng thái"}, 0) {
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
                    txtTenNV.setText(model.getValueAt(row, 1).toString());
                    txtSoDienThoai.setText(model.getValueAt(row, 2).toString());
                    txtEmail.setText(model.getValueAt(row, 3).toString());
                    txtDiaChi.setText(model.getValueAt(row, 4).toString());
                    chkTrangThai.setSelected(model.getValueAt(row, 5).equals("Hoạt động"));
                }
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách nhân viên"));

        // Layout chính
        add(titlePanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(pnlTop, BorderLayout.NORTH);
        centerPanel.add(scroll, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Thêm sự kiện cho các nút
        btnThem.addActionListener(e -> themNhanVien());
        btnSua.addActionListener(e -> suaNhanVien());
        btnXoa.addActionListener(e -> xoaNhanVien());
        btnLamMoi.addActionListener(e -> lamMoiForm());
        
        // Sự kiện tìm kiếm
        btnTimKiem.addActionListener(e -> timKiemNhanVien());
        btnLamMoiTim.addActionListener(e -> lamMoiTimKiem());
        
        // Cho phép tìm kiếm khi nhấn Enter
        KeyAdapter enterKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    timKiemNhanVien();
                }
            }
        };
        
        txtTimTenNV.addKeyListener(enterKeyAdapter);
        txtTimSDT.addKeyListener(enterKeyAdapter);
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
        List<NhanVien> list = nhanVienDAO.findAll();
        for (NhanVien nv : list) {
            model.addRow(new Object[]{
                nv.getNhanVienID(),
                nv.getTenNV(),
                nv.getSoDienThoai(),
                nv.getEmail(),
                nv.getDiaChi(),
                nv.isTrangThai() ? "Hoạt động" : "Không hoạt động"
            });
        }
    }

    private void timKiemNhanVien() {
        try {
            String tenNV = txtTimTenNV.getText().trim();
            String soDienThoai = txtTimSDT.getText().trim();
            
            Boolean trangThai = null;
            if (cboTrangThaiTim.getSelectedIndex() == 1) {
                trangThai = true;
            } else if (cboTrangThaiTim.getSelectedIndex() == 2) {
                trangThai = false;
            }
            
            // Gọi DAO để tìm kiếm
            List<NhanVien> list = nhanVienDAO.findByCriteria(tenNV, soDienThoai, trangThai);
            
            // Hiển thị kết quả
            model.setRowCount(0);
            for (NhanVien nv : list) {
                model.addRow(new Object[]{
                    nv.getNhanVienID(),
                    nv.getTenNV(),
                    nv.getSoDienThoai(),
                    nv.getEmail(),
                    nv.getDiaChi(),
                    nv.isTrangThai() ? "Hoạt động" : "Không hoạt động"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void lamMoiTimKiem() {
        txtTimTenNV.setText("");
        txtTimSDT.setText("");
        cboTrangThaiTim.setSelectedIndex(0);
        loadData(); // Tải lại toàn bộ dữ liệu
    }

    private void themNhanVien() {
        try {
            // Kiểm tra dữ liệu nhập
            if (txtTenNV.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên nhân viên không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (txtSoDienThoai.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            NhanVien nv = new NhanVien();
            nv.setTenNV(txtTenNV.getText());
            nv.setSoDienThoai(txtSoDienThoai.getText());
            nv.setEmail(txtEmail.getText());
            nv.setDiaChi(txtDiaChi.getText());
            nv.setTrangThai(chkTrangThai.isSelected());

            nhanVienDAO.create(nv);
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadData();
            lamMoiForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm nhân viên: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaNhanVien() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Kiểm tra dữ liệu nhập
            if (txtTenNV.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên nhân viên không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (txtSoDienThoai.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            NhanVien nv = new NhanVien();
            nv.setNhanVienID((int) model.getValueAt(row, 0));
            nv.setTenNV(txtTenNV.getText());
            nv.setSoDienThoai(txtSoDienThoai.getText());
            nv.setEmail(txtEmail.getText());
            nv.setDiaChi(txtDiaChi.getText());
            nv.setTrangThai(chkTrangThai.isSelected());

            nhanVienDAO.update(nv);
            JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật nhân viên: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaNhanVien() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int nhanVienID = (int) model.getValueAt(row, 0);
        
        // Kiểm tra nhân viên có tài khoản không
        if (nhanVienDAO.hasTaiKhoan(nhanVienID)) {
            JOptionPane.showMessageDialog(this, 
                "Không thể xóa nhân viên này vì đã có tài khoản liên kết!\nHãy xóa tài khoản trước khi xóa nhân viên.", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa nhân viên này?",
                "Xác nhận xóa nhân viên",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                nhanVienDAO.deleteById(nhanVienID);
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadData();
                lamMoiForm();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa nhân viên: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void lamMoiForm() {
        txtTenNV.setText("");
        txtSoDienThoai.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
        chkTrangThai.setSelected(true);
        table.clearSelection();
    }
}