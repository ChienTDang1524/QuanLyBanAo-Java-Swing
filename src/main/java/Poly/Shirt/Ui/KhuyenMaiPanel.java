package Poly.Shirt.Ui;

import Poly.Shirt.Enity.KhuyenMai;
import Poly.Shirt.dao.KhuyenMaiDAO;
import Poly.Shirt.impl.KhuyenMaiDAOImpl;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import Poly.Shirt.Util.JDateChooser;
import javax.swing.table.TableCellRenderer;

public class KhuyenMaiPanel extends JPanel {

    // Màu sắc hiện đại
    private final Color PRIMARY_COLOR = new Color(59, 89, 152); // Màu chủ đạo
    private final Color SECONDARY_COLOR = new Color(246, 247, 249); // Màu nền
    private final Color ACCENT_COLOR = new Color(66, 134, 244); // Màu nhấn
    private final Color TEXT_COLOR = new Color(51, 51, 51); // Màu chữ
    private final Color BORDER_COLOR = new Color(221, 223, 226); // Màu viền
    private final Color TABLE_ROW_COLOR = new Color(255, 255, 255); // Màu nền bảng
    private final Color TABLE_ALTERNATE_COLOR = new Color(245, 245, 245); // Màu nền xen kẽ
    
    // Components
    private JTextField txtTenKM, txtGiaTriGiam, txtTimTenKM;
    private JTextArea txtMoTa;
    private JComboBox<String> cboLoaiGiamGia, cboTrangThaiTim;
    private JDateChooser dateNgayBatDau, dateNgayKetThuc;
    private JCheckBox chkTrangThai;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem, btnLamMoiTim;
    private JTable tblKhuyenMai;
    private DefaultTableModel model;
    private KhuyenMaiDAO dao = new KhuyenMaiDAOImpl();

    public KhuyenMaiPanel() {
        initComponents();
        addEventListeners();
        loadTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 0));
        setBackground(SECONDARY_COLOR);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // Panel tiêu đề
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(SECONDARY_COLOR);
        JLabel lblTitle = new JLabel("QUẢN LÝ CHƯƠNG TRÌNH KHUYẾN MÃI");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(PRIMARY_COLOR);
        titlePanel.add(lblTitle);

        // Panel chính chứa form và bảng
        JPanel mainPanel = new JPanel(new BorderLayout(10, 15));
        mainPanel.setBackground(SECONDARY_COLOR);
        mainPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        // Panel form nhập liệu
        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = createButtonPanel();
        JPanel searchPanel = createSearchPanel();

        // Panel top chứa form và button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(SECONDARY_COLOR);
        topPanel.add(formPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        // Bảng danh sách
        JPanel tablePanel = createTablePanel();

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Dòng 1: Tên KM và Mô tả
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(createLabel("Tên khuyến mãi:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        txtTenKM = createTextField();
        panel.add(txtTenKM, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(createLabel("Mô tả:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        txtMoTa = new JTextArea(3, 20);
        txtMoTa.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtMoTa.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR),
            new EmptyBorder(5, 5, 5, 5)
        ));
        JScrollPane scrollMoTa = new JScrollPane(txtMoTa);
        panel.add(scrollMoTa, gbc);
        
        // Dòng 3: Ngày bắt đầu và kết thúc
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(createLabel("Ngày bắt đầu:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        dateNgayBatDau = new JDateChooser();
        dateNgayBatDau.setDateFormat("dd/MM/yyyy");
        dateNgayBatDau.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panel.add(dateNgayBatDau, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(createLabel("Ngày kết thúc:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        dateNgayKetThuc = new JDateChooser();
        dateNgayKetThuc.setDateFormat("dd/MM/yyyy");
        dateNgayKetThuc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panel.add(dateNgayKetThuc, gbc);
        
        // Dòng 5: Loại giảm giá và giá trị
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(createLabel("Loại giảm giá:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        cboLoaiGiamGia = new JComboBox<>(new String[]{"Giảm theo %", "Giảm tiền trực tiếp"});
        cboLoaiGiamGia.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panel.add(cboLoaiGiamGia, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(createLabel("Giá trị giảm:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 6;
        txtGiaTriGiam = createTextField();
        panel.add(txtGiaTriGiam, gbc);
        
        // Dòng 7: Trạng thái
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(createLabel("Trạng thái:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 7;
        chkTrangThai = new JCheckBox("Hoạt động");
        chkTrangThai.setSelected(true);
        chkTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkTrangThai.setBackground(Color.WHITE);
        panel.add(chkTrangThai, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(SECONDARY_COLOR);
        panel.setBorder(new EmptyBorder(5, 0, 15, 0));

        // Màu nút mới đảm bảo rõ ràng
        btnThem = createButton("Thêm", new Color(56, 142, 60)); // Xanh lá đậm hơn
        btnSua = createButton("Sửa", new Color(41, 98, 255)); // Xanh dương đậm hơn
        btnXoa = createButton("Xóa", new Color(211, 47, 47)); // Đỏ đậm hơn
        btnLamMoi = createButton("Làm mới", new Color(120, 144, 156)); // Xám đậm hơn

        panel.add(btnThem);
        panel.add(btnSua);
        panel.add(btnXoa);
        panel.add(btnLamMoi);

        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Dòng 1: Tìm kiếm theo tên
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(createLabel("Tên khuyến mãi:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        txtTimTenKM = createTextField();
        panel.add(txtTimTenKM, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(createLabel("Trạng thái:"), gbc);
        
        gbc.gridx = 3;
        gbc.gridy = 0;
        cboTrangThaiTim = new JComboBox<>(new String[]{"Tất cả", "Hoạt động", "Ngừng hoạt động"});
        cboTrangThaiTim.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panel.add(cboTrangThaiTim, gbc);
        
        // Dòng 2: Nút tìm kiếm
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.CENTER;
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        btnTimKiem = createButton("Tìm kiếm", new Color(30, 136, 229)); // Xanh dương đậm
        btnLamMoiTim = createButton("Làm mới", new Color(120, 144, 156)); // Xám đậm
        
        buttonPanel.add(btnTimKiem);
        buttonPanel.add(btnLamMoiTim);
        
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(SECONDARY_COLOR);
        panel.setBorder(new EmptyBorder(10, 0, 0, 0));

        // Tạo model cho bảng
        model = new DefaultTableModel(new Object[]{
            "ID", "Tên KM", "Mô tả", "Ngày bắt đầu", "Ngày kết thúc",
            "Loại giảm giá", "Giá trị giảm", "Trạng thái"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblKhuyenMai = new JTable(model) {
            // Tô màu xen kẽ các dòng
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? TABLE_ROW_COLOR : TABLE_ALTERNATE_COLOR);
                }
                return c;
            }
        };
        
        // Tăng chiều cao bảng lên 1.5 lần (từ 35 lên 52)
        tblKhuyenMai.setRowHeight(52);
        tblKhuyenMai.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblKhuyenMai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblKhuyenMai.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblKhuyenMai.getTableHeader().setBackground(PRIMARY_COLOR);
        tblKhuyenMai.getTableHeader().setForeground(Color.WHITE);
        tblKhuyenMai.setShowGrid(true);
        tblKhuyenMai.setGridColor(new Color(230, 230, 230));
        tblKhuyenMai.setIntercellSpacing(new Dimension(0, 0));
        tblKhuyenMai.setFillsViewportHeight(true);

        // Sự kiện click vào bảng
        tblKhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tblKhuyenMai.getSelectedRow();
                if (row >= 0) {
                    txtTenKM.setText(model.getValueAt(row, 1).toString());
                    txtMoTa.setText(model.getValueAt(row, 2).toString());

                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        dateNgayBatDau.setDate(sdf.parse(model.getValueAt(row, 3).toString()));
                        dateNgayKetThuc.setDate(sdf.parse(model.getValueAt(row, 4).toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    cboLoaiGiamGia.setSelectedIndex(model.getValueAt(row, 5).toString().contains("%") ? 0 : 1);
                    txtGiaTriGiam.setText(model.getValueAt(row, 6).toString().replaceAll("[^0-9.]", ""));
                    chkTrangThai.setSelected(model.getValueAt(row, 7).equals("Hoạt động"));
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblKhuyenMai);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(0, 0, 0, 0)
        ));

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR),
            new EmptyBorder(5, 10, 5, 10)
        ));
        return textField;
    }

    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(bgColor.darker(), 1),
            new EmptyBorder(8, 20, 8, 20)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Hiệu ứng hover
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bgColor.brighter());
                btn.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
                btn.setForeground(Color.WHITE);
            }
        });

        return btn;
    }

    // Các phương thức xử lý sự kiện và nghiệp vụ giữ nguyên như cũ
    private void addEventListeners() {
        btnThem.addActionListener(e -> themKhuyenMai());
        btnSua.addActionListener(e -> suaKhuyenMai());
        btnXoa.addActionListener(e -> xoaKhuyenMai());
        btnLamMoi.addActionListener(e -> lamMoiForm());

        btnTimKiem.addActionListener(e -> timKiemKhuyenMai());
        btnLamMoiTim.addActionListener(e -> lamMoiTimKiem());

        KeyAdapter enterKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    timKiemKhuyenMai();
                }
            }
        };

        txtTimTenKM.addKeyListener(enterKeyAdapter);
    }

    private void themKhuyenMai() {
        try {
            KhuyenMai km = getFormData();
            dao.create(km);
            JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadTable();
            clearForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaKhuyenMai() {
        int row = tblKhuyenMai.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần sửa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            KhuyenMai km = getFormData();
            km.setKhuyenMaiID((int) model.getValueAt(row, 0));
            dao.update(km);
            JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaKhuyenMai() {
        int row = tblKhuyenMai.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần xóa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa khuyến mãi này?", "Xác nhận",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int khuyenMaiID = (int) model.getValueAt(row, 0);
                dao.deleteById(khuyenMaiID);
                JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadTable();
                clearForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void lamMoiForm() {
        clearForm();
        tblKhuyenMai.clearSelection();
    }

    private void timKiemKhuyenMai() {
        try {
            String tenKM = txtTimTenKM.getText().trim();

            Boolean trangThai = null;
            if (cboTrangThaiTim.getSelectedIndex() == 1) {
                trangThai = true;
            } else if (cboTrangThaiTim.getSelectedIndex() == 2) {
                trangThai = false;
            }

            List<KhuyenMai> list = dao.findByCriteria(tenKM, trangThai);

            model.setRowCount(0);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (KhuyenMai km : list) {
                model.addRow(new Object[]{
                    km.getKhuyenMaiID(),
                    km.getTenKM(),
                    km.getMoTa(),
                    sdf.format(km.getNgayBatDau()),
                    sdf.format(km.getNgayKetThuc()),
                    km.getLoaiGiamGia() == 1 ? km.getGiaTriGiam() + "%" : "Giảm " + km.getGiaTriGiam(),
                    km.getGiaTriGiam(),
                    km.isTrangThai() ? "Hoạt động" : "Ngừng hoạt động"
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void lamMoiTimKiem() {
        txtTimTenKM.setText("");
        cboTrangThaiTim.setSelectedIndex(0);
        loadTable();
    }

    private KhuyenMai getFormData() throws Exception {
        KhuyenMai km = new KhuyenMai();
        km.setTenKM(txtTenKM.getText().trim());
        km.setMoTa(txtMoTa.getText().trim());
        km.setNgayBatDau(new java.sql.Date(dateNgayBatDau.getDate().getTime()));
        km.setNgayKetThuc(new java.sql.Date(dateNgayKetThuc.getDate().getTime()));
        km.setLoaiGiamGia(cboLoaiGiamGia.getSelectedIndex() + 1);
        km.setGiaTriGiam(Double.parseDouble(txtGiaTriGiam.getText().trim()));
        km.setTrangThai(chkTrangThai.isSelected());

        // Validate
        if (km.getTenKM().isEmpty()) {
            throw new IllegalArgumentException("Tên khuyến mãi không được để trống");
        }
        if (km.getNgayKetThuc().before(km.getNgayBatDau())) {
            throw new IllegalArgumentException("Ngày kết thúc phải sau ngày bắt đầu");
        }
        if (km.getGiaTriGiam() <= 0) {
            throw new IllegalArgumentException("Giá trị giảm phải lớn hơn 0");
        }
        if (km.getLoaiGiamGia() == 1 && km.getGiaTriGiam() > 100) {
            throw new IllegalArgumentException("Giảm % không được vượt quá 100%");
        }

        return km;
    }

    private void loadTable() {
        model.setRowCount(0);
        try {
            List<KhuyenMai> list = dao.findAll();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date now = new Date(); // Ngày hiện tại
            
            for (KhuyenMai km : list) {
                // Kiểm tra nếu khuyến mãi đã hết hạn
                boolean isExpired = km.getNgayKetThuc().before(now);
                
                // Nếu hết hạn thì cập nhật trạng thái trong database
                if (isExpired && km.isTrangThai()) {
                    km.setTrangThai(false);
                    dao.update(km); // Cập nhật vào CSDL
                }
                
                model.addRow(new Object[]{
                    km.getKhuyenMaiID(),
                    km.getTenKM(),
                    km.getMoTa(),
                    sdf.format(km.getNgayBatDau()),
                    sdf.format(km.getNgayKetThuc()),
                    km.getLoaiGiamGia() == 1 ? km.getGiaTriGiam() + "%" : "Giảm " + km.getGiaTriGiam(),
                    km.getGiaTriGiam(),
                    (!isExpired && km.isTrangThai()) ? "Hoạt động" : "Ngừng hoạt động"
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtTenKM.setText("");
        txtMoTa.setText("");
        dateNgayBatDau.setDate(null);
        dateNgayKetThuc.setDate(null);
        cboLoaiGiamGia.setSelectedIndex(0);
        txtGiaTriGiam.setText("");
        chkTrangThai.setSelected(true);
    }
}