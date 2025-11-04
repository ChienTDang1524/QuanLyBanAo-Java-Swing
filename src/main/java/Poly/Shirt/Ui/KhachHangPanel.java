package Poly.Shirt.Ui;

import Poly.Shirt.dao.KhachHangDAO;
import Poly.Shirt.impl.KhachHangDAOImpl;
import Poly.Shirt.Enity.KhachHang;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class KhachHangPanel extends JPanel {

    private JTextField txtTenKH, txtSoDienThoai, txtEmail, txtDiaChi;
    
    private JButton btnThem, btnSua, btnXoa, btnLamMoi;
    private JTable tblKhachHang;
    private DefaultTableModel model;
    private KhachHangDAO dao = new KhachHangDAOImpl();

    // Các trường tìm kiếm
    private JTextField txtTimTenKH, txtTimSoDienThoai;
    private JButton btnTimKiem, btnLamMoiTim;

    // Màu sắc
    private final Color MAIN_COLOR = new Color(70, 130, 180); // SteelBlue
    private final Color SECONDARY_COLOR = new Color(240, 240, 240); // Light gray

    public KhachHangPanel() {
        initComponents();
        addEventListeners();
        loadTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel tiêu đề
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        JLabel lblTitle = new JLabel("QUẢN LÝ KHÁCH HÀNG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(MAIN_COLOR);
        titlePanel.add(lblTitle);

        // Panel form nhập liệu
        JPanel pnlForm = new JPanel(new GridLayout(5, 2, 10, 10));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));
        pnlForm.setBackground(new Color(240, 240, 240));

        addFormField(pnlForm, "Tên KH:", txtTenKH = new JTextField());
        addFormField(pnlForm, "SĐT:", txtSoDienThoai = new JTextField());
        addFormField(pnlForm, "Email:", txtEmail = new JTextField());
        addFormField(pnlForm, "Địa chỉ:", txtDiaChi = new JTextField());

       

        // Panel nút chức năng
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlButton.setBackground(new Color(240, 240, 240));

        btnThem = createButton("Thêm", new Color(46, 125, 50));
        btnSua = createButton("Sửa", new Color(41, 98, 255));
        btnXoa = createButton("Xóa", new Color(198, 40, 40));
        btnLamMoi = createButton("Làm mới", new Color(158, 158, 158));

        pnlButton.add(btnThem);
        pnlButton.add(btnSua);
        pnlButton.add(btnXoa);
        pnlButton.add(btnLamMoi);

        // Panel tìm kiếm
        JPanel pnlTimKiem = new JPanel(new GridBagLayout());
        pnlTimKiem.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Tìm kiếm khách hàng",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                MAIN_COLOR
        ));
        pnlTimKiem.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Dòng 1: Tìm kiếm cơ bản
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlTimKiem.add(new JLabel("Tên KH:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        txtTimTenKH = new JTextField(15);
        pnlTimKiem.add(txtTimTenKH, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        pnlTimKiem.add(new JLabel("SĐT:"), gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        txtTimSoDienThoai = new JTextField(15);
        pnlTimKiem.add(txtTimSoDienThoai, gbc);

        // Dòng 2: Nút tìm kiếm
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.CENTER;
        JPanel pnlButtonTim = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        pnlButtonTim.setBackground(new Color(240, 240, 240));

        btnTimKiem = createButton("Tìm kiếm", MAIN_COLOR);
        btnLamMoiTim = createButton("Làm mới", new Color(158, 158, 158));

        pnlButtonTim.add(btnTimKiem);
        pnlButtonTim.add(btnLamMoiTim);

        pnlTimKiem.add(pnlButtonTim, gbc);

        // Panel top (form + button + search)
        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setBackground(new Color(240, 240, 240));
        pnlTop.add(pnlForm, BorderLayout.NORTH);
        pnlTop.add(pnlButton, BorderLayout.CENTER);
        pnlTop.add(pnlTimKiem, BorderLayout.SOUTH);

        // Bảng danh sách
        model = new DefaultTableModel(new Object[]{"ID", "Tên KH", "SĐT", "Email", "Địa chỉ"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblKhachHang = new JTable(model);
        tblKhachHang.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblKhachHang.setRowHeight(30);
        tblKhachHang.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Sự kiện click vào bảng
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tblKhachHang.getSelectedRow();
                if (row >= 0) {
                    txtTenKH.setText(model.getValueAt(row, 1).toString());
                    txtSoDienThoai.setText(model.getValueAt(row, 2).toString());
                    txtEmail.setText(model.getValueAt(row, 3).toString());
                    txtDiaChi.setText(model.getValueAt(row, 4).toString());
                    
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tblKhachHang);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách khách hàng"));

        // Layout chính
        add(titlePanel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(pnlTop, BorderLayout.NORTH);
        centerPanel.add(scroll, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void addEventListeners() {
        btnThem.addActionListener(e -> themKhachHang());
        btnSua.addActionListener(e -> suaKhachHang());
        btnXoa.addActionListener(e -> xoaKhachHang());
        btnLamMoi.addActionListener(e -> lamMoiForm());

        // Sự kiện tìm kiếm
        btnTimKiem.addActionListener(e -> timKiemKhachHang());
        btnLamMoiTim.addActionListener(e -> lamMoiTimKiem());

        // Cho phép tìm kiếm khi nhấn Enter
        KeyAdapter enterKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    timKiemKhachHang();
                }
            }
        };

        txtTimTenKH.addKeyListener(enterKeyAdapter);
        txtTimSoDienThoai.addKeyListener(enterKeyAdapter);

    }

    private void themKhachHang() {
        try {
            KhachHang kh = getFormData();
            dao.create(kh);
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
            loadTable();
            clearForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaKhachHang() {
        int row = tblKhachHang.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần sửa");
            return;
        }

        try {
            KhachHang kh = getFormData();
            kh.setKhachHangID((int) model.getValueAt(row, 0));
            dao.update(kh);
            JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!");
            loadTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaKhachHang() {
        int row = tblKhachHang.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int khachHangID = (int) model.getValueAt(row, 0);
                dao.deleteById(khachHangID);
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
                loadTable();
                clearForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void lamMoiForm() {
        clearForm();
        tblKhachHang.clearSelection();
    }

    private void timKiemKhachHang() {
        try {
            String tenKH = txtTimTenKH.getText().trim();
            String soDienThoai = txtTimSoDienThoai.getText().trim();

            // Gọi DAO để tìm kiếm
            List<KhachHang> list = dao.findByCriteria(tenKH, soDienThoai);

            // Hiển thị kết quả
            model.setRowCount(0);
            for (KhachHang kh : list) {
                model.addRow(new Object[]{
                    kh.getKhachHangID(),
                    kh.getTenKH(),
                    kh.getSoDienThoai(),
                    kh.getEmail(),
                    kh.getDiaChi(),});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void lamMoiTimKiem() {
        txtTimTenKH.setText("");
        txtTimSoDienThoai.setText("");
        loadTable(); // Tải lại toàn bộ dữ liệu
    }

    private KhachHang getFormData() {
        KhachHang kh = new KhachHang();
        kh.setTenKH(txtTenKH.getText().trim());
        kh.setSoDienThoai(txtSoDienThoai.getText().trim());
        kh.setEmail(txtEmail.getText().trim());
        kh.setDiaChi(txtDiaChi.getText().trim());

        // Validate
        if (kh.getTenKH().isEmpty() || kh.getSoDienThoai().isEmpty()) {
            throw new IllegalArgumentException("Tên KH và SĐT không được để trống");
        }

        return kh;
    }

    private void loadTable() {
        model.setRowCount(0);
        try {
            List<KhachHang> list = dao.findAll();
            for (KhachHang kh : list) {
                model.addRow(new Object[]{
                    kh.getKhachHangID(),
                    kh.getTenKH(),
                    kh.getSoDienThoai(),
                    kh.getEmail(),
                    kh.getDiaChi(),});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtTenKH.setText("");
        txtSoDienThoai.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
      
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

        return btn;
    }
}
