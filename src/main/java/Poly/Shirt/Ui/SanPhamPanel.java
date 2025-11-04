package Poly.Shirt.Ui;

import Poly.Shirt.Enity.*;
import Poly.Shirt.dao.*;
import Poly.Shirt.impl.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;

public class SanPhamPanel extends JPanel {

    private JTable tblAo, tblAoChiTiet;
    private DefaultTableModel modelAo, modelAoChiTiet;
    private JTextField txtMaAo, txtTenAo, txtMaCT, txtSoLuong, txtGiaGoc, txtGiaBan, txtAnhChinh;
    private JComboBox<String> cboLoaiAo, cboThuongHieu, cboChatLieu, cboSize, cboMauSac;
    private JButton btnThem, btnSua, btnXoa, btnMoi;
    private JButton btnThemCT, btnSuaCT, btnXoaCT, btnMoiCT;
    private JTextArea txtMoTa;
    // Các bảng mới cho thuộc tính
    private JTable tblLoaiAo, tblThuongHieu, tblChatLieu, tblSize, tblMauSac;
    private DefaultTableModel modelLoaiAo, modelThuongHieu, modelChatLieu, modelSize, modelMauSac;
    private JTextField txtMaLoaiAo, txtTenLoaiAo, txtMaThuongHieu, txtTenThuongHieu;
    private JTextField txtMaChatLieu, txtTenChatLieu, txtMaSize, txtTenSize, txtMaMau, txtTenMau;

    // DAO
    private AoDAO aoDAO = new AoDAOImpl();
    private AoChiTietDAO aoChiTietDAO = new AoChiTietDAOImpl();
    private LoaiAoDAO loaiAoDAO = new LoaiAoDAOImpl();
    private ThuongHieuDAO thuongHieuDAO = new ThuongHieuDAOImpl();
    private ChatLieuDAO chatLieuDAO = new ChatLieuDAOImpl();
    private SizeAoDAO sizeAoDAO = new SizeAoDAOImpl();
    private MauSacDAO mauSacDAO = new MauSacDAOImpl();

    // Màu sắc
    private final Color MAIN_COLOR = new Color(70, 130, 180);
    private final Color SECONDARY_COLOR = new Color(240, 240, 240);
    private final Color BUTTON_ADD_COLOR = new Color(46, 125, 50);
    private final Color BUTTON_EDIT_COLOR = new Color(41, 98, 255);
    private final Color BUTTON_DELETE_COLOR = new Color(198, 40, 40);
    private final Color BUTTON_REFRESH_COLOR = new Color(158, 158, 158);

    public SanPhamPanel() {
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
        JLabel lblTitle = new JLabel("QUẢN LÝ SẢN PHẨM");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(MAIN_COLOR);
        titlePanel.add(lblTitle);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Tab Quản lý Áo
        JPanel pnlAo = new JPanel(new BorderLayout(10, 10));
        pnlAo.add(createAoInputPanel(), BorderLayout.NORTH);
        pnlAo.add(createAoTablePanel(), BorderLayout.CENTER);
        tabbedPane.addTab("QUẢN LÝ ÁO", pnlAo);

        // Tab Quản lý Chi tiết Áo
        JPanel pnlAoChiTiet = new JPanel(new BorderLayout(10, 10));
        pnlAoChiTiet.add(createAoChiTietInputPanel(), BorderLayout.NORTH);
        pnlAoChiTiet.add(createAoChiTietTablePanel(), BorderLayout.CENTER);
        tabbedPane.addTab("CHI TIẾT ÁO", pnlAoChiTiet);

        // Tab Quản lý Loại Áo
        tabbedPane.addTab("LOẠI ÁO", createThuocTinhPanel(
                "Loại Áo",
                new String[]{"Mã Loại", "Tên Loại"},
                tblLoaiAo = new JTable(),
                modelLoaiAo = new DefaultTableModel(),
                txtMaLoaiAo = createTextField(false),
                txtTenLoaiAo = createTextField(true),
                loaiAoDAO
        ));

        // Tab Quản lý Thương hiệu
        tabbedPane.addTab("THƯƠNG HIỆU", createThuocTinhPanel(
                "Thương hiệu",
                new String[]{"Mã TH", "Tên TH"},
                tblThuongHieu = new JTable(),
                modelThuongHieu = new DefaultTableModel(),
                txtMaThuongHieu = createTextField(false),
                txtTenThuongHieu = createTextField(true),
                thuongHieuDAO
        ));

        // Tab Quản lý Chất liệu
        tabbedPane.addTab("CHẤT LIỆU", createThuocTinhPanel(
                "Chất liệu",
                new String[]{"Mã CL", "Tên CL"},
                tblChatLieu = new JTable(),
                modelChatLieu = new DefaultTableModel(),
                txtMaChatLieu = createTextField(false),
                txtTenChatLieu = createTextField(true),
                chatLieuDAO
        ));

        // Tab Quản lý Size
        tabbedPane.addTab("SIZE", createThuocTinhPanel(
                "Size",
                new String[]{"Mã Size", "Tên Size"},
                tblSize = new JTable(),
                modelSize = new DefaultTableModel(),
                txtMaSize = createTextField(false),
                txtTenSize = createTextField(true),
                sizeAoDAO
        ));

        // Tab Quản lý Màu sắc
        tabbedPane.addTab("MÀU SẮC", createThuocTinhPanel(
                "Màu sắc",
                new String[]{"Mã Màu", "Tên Màu"},
                tblMauSac = new JTable(),
                modelMauSac = new DefaultTableModel(),
                txtMaMau = createTextField(false),
                txtTenMau = createTextField(true),
                mauSacDAO
        ));

        // Layout chính
        add(titlePanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private <T> JPanel createThuocTinhPanel(String title, String[] columns, JTable table,
            DefaultTableModel model, JTextField txtMa, JTextField txtTen, CrudDAO dao) {
        JPanel pnl = new JPanel(new BorderLayout(10, 10));

        // Panel nhập liệu
        JPanel pnlInput = new JPanel(new GridLayout(1, 2, 10, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Thông tin " + title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                MAIN_COLOR
        ));
        pnlInput.setBackground(SECONDARY_COLOR);

        addFormField(pnlInput, "Mã:", txtMa);
        addFormField(pnlInput, "Tên:", txtTen);

        // Panel button
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlButton.setBackground(SECONDARY_COLOR);

        JButton btnThem = createButton("Thêm", BUTTON_ADD_COLOR);
        JButton btnSua = createButton("Sửa", BUTTON_EDIT_COLOR);
        JButton btnXoa = createButton("Xóa", BUTTON_DELETE_COLOR);
        JButton btnMoi = createButton("Làm mới", BUTTON_REFRESH_COLOR);

        btnThem.addActionListener(e -> themThuocTinh(dao, txtMa, txtTen, model, table));
        btnSua.addActionListener(e -> suaThuocTinh(dao, txtMa, txtTen, model, table));
        btnXoa.addActionListener(e -> xoaThuocTinh(dao, txtMa, model, table));
        btnMoi.addActionListener(e -> clearFormThuocTinh(txtMa, txtTen, table));

        pnlButton.add(btnThem);
        pnlButton.add(btnSua);
        pnlButton.add(btnXoa);
        pnlButton.add(btnMoi);

        // Bảng dữ liệu
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        styleTable(table);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMa.setText(table.getValueAt(row, 0).toString());
                    txtTen.setText(table.getValueAt(row, 1).toString());
                }
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Danh sách " + title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                MAIN_COLOR
        ));

        pnl.add(pnlInput, BorderLayout.NORTH);
        pnl.add(scroll, BorderLayout.CENTER);
        pnl.add(pnlButton, BorderLayout.SOUTH);

        return pnl;
    }

    private <T> void themThuocTinh(CrudDAO dao, JTextField txtMa, JTextField txtTen,
            DefaultTableModel model, JTable table) {
        try {
            if (txtTen.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (dao instanceof LoaiAoDAO) {
                LoaiAo loai = new LoaiAo();
                loai.setTenLoai(txtTen.getText().trim());
                ((LoaiAoDAO) dao).create(loai);
            } else if (dao instanceof ThuongHieuDAO) {
                ThuongHieu th = new ThuongHieu();
                th.setTenThuongHieu(txtTen.getText().trim());
                ((ThuongHieuDAO) dao).create(th);
            } else if (dao instanceof ChatLieuDAO) {
                ChatLieu cl = new ChatLieu();
                cl.setTenChatLieu(txtTen.getText().trim());
                ((ChatLieuDAO) dao).create(cl);
            } else if (dao instanceof SizeAoDAO) {
                SizeAo size = new SizeAo();
                size.setTenSize(txtTen.getText().trim());
                ((SizeAoDAO) dao).create(size);
            } else if (dao instanceof MauSacDAO) {
                MauSac mau = new MauSac();
                mau.setTenMau(txtTen.getText().trim());
                ((MauSacDAO) dao).create(mau);
            }

            JOptionPane.showMessageDialog(this, "Thêm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadTableThuocTinh(dao, model);
            clearFormThuocTinh(txtMa, txtTen, table);
            loadComboBoxData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private <T> void suaThuocTinh(CrudDAO dao, JTextField txtMa, JTextField txtTen,
            DefaultTableModel model, JTable table) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dữ liệu cần sửa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (txtTen.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = Integer.parseInt(txtMa.getText());

            if (dao instanceof LoaiAoDAO) {
                LoaiAo loai = new LoaiAo();
                loai.setLoaiAoID(id);
                loai.setTenLoai(txtTen.getText().trim());
                ((LoaiAoDAO) dao).update(loai);
            } else if (dao instanceof ThuongHieuDAO) {
                ThuongHieu th = new ThuongHieu();
                th.setThuongHieuID(id);
                th.setTenThuongHieu(txtTen.getText().trim());
                ((ThuongHieuDAO) dao).update(th);
            } else if (dao instanceof ChatLieuDAO) {
                ChatLieu cl = new ChatLieu();
                cl.setChatLieuID(id);
                cl.setTenChatLieu(txtTen.getText().trim());
                ((ChatLieuDAO) dao).update(cl);
            } else if (dao instanceof SizeAoDAO) {
                SizeAo size = new SizeAo();
                size.setSizeID(id);
                size.setTenSize(txtTen.getText().trim());
                ((SizeAoDAO) dao).update(size);
            } else if (dao instanceof MauSacDAO) {
                MauSac mau = new MauSac();
                mau.setMauSacID(id);
                mau.setTenMau(txtTen.getText().trim());
                ((MauSacDAO) dao).update(mau);
            }

            JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadTableThuocTinh(dao, model);
            loadComboBoxData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private <T> void xoaThuocTinh(CrudDAO dao, JTextField txtMa, DefaultTableModel model, JTable table) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dữ liệu cần xóa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(txtMa.getText());

        if (isThuocTinhDangSuDung(dao, id)) {
            JOptionPane.showMessageDialog(this,
                    "Không thể xóa vì thuộc tính này đang được sử dụng bởi sản phẩm!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                dao.deleteById(id);
                JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadTableThuocTinh(dao, model);
                loadComboBoxData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private <T> boolean isThuocTinhDangSuDung(CrudDAO dao, int id) {
        if (dao instanceof LoaiAoDAO) {
            return aoDAO.countByLoaiAoID(id) > 0;
        } else if (dao instanceof ThuongHieuDAO) {
            return aoDAO.countByThuongHieuID(id) > 0;
        } else if (dao instanceof ChatLieuDAO) {
            return aoDAO.countByChatLieuID(id) > 0;
        } else if (dao instanceof SizeAoDAO) {
            return aoChiTietDAO.countBySizeID(id) > 0;
        } else if (dao instanceof MauSacDAO) {
            return aoChiTietDAO.countByMauSacID(id) > 0;
        }
        return false;
    }

    private <T> void loadTableThuocTinh(CrudDAO dao, DefaultTableModel model) {
          try {
        model.setRowCount(0);
        
        List<T> ds = dao.findAll();
        
         if (ds == null || ds.isEmpty()) {
            return;
        }
        for (T item : ds) {
            if (item instanceof LoaiAo) {
                LoaiAo loai = (LoaiAo) item;
                model.addRow(new Object[]{loai.getLoaiAoID(), loai.getTenLoai()});
            } else if (item instanceof ThuongHieu) {
                ThuongHieu th = (ThuongHieu) item;
                model.addRow(new Object[]{th.getThuongHieuID(), th.getTenThuongHieu()});
            } else if (item instanceof ChatLieu) {
                ChatLieu cl = (ChatLieu) item;
                model.addRow(new Object[]{cl.getChatLieuID(), cl.getTenChatLieu()});
            } else if (item instanceof SizeAo) {
                SizeAo size = (SizeAo) item;
                model.addRow(new Object[]{size.getSizeID(), size.getTenSize()});
            } else if (item instanceof MauSac) {
                MauSac mau = (MauSac) item;
                model.addRow(new Object[]{mau.getMauSacID(), mau.getTenMau()});
            }
        }
          model.fireTableDataChanged();
          
            } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), 
                                     "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    }

    private void clearFormThuocTinh(JTextField txtMa, JTextField txtTen, JTable table) {
        txtMa.setText("");
        txtTen.setText("");
        table.clearSelection();
    }

    private void loadData() {
        loadAoTable();
        loadComboBoxData();

        loadTableThuocTinh(loaiAoDAO, modelLoaiAo);
        loadTableThuocTinh(thuongHieuDAO, modelThuongHieu);
        loadTableThuocTinh(chatLieuDAO, modelChatLieu);
        loadTableThuocTinh(sizeAoDAO, modelSize);
        loadTableThuocTinh(mauSacDAO, modelMauSac);
    }

    private JPanel createAoInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Thông tin Áo",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                MAIN_COLOR
        ));
        panel.setBackground(SECONDARY_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaAo = createTextField(false);
        txtTenAo = createTextField(true);
        cboLoaiAo = createComboBox();
        cboThuongHieu = createComboBox();
        cboChatLieu = createComboBox();
        txtMoTa = new JTextArea(3, 20);
        txtMoTa.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtMoTa.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 5, 5, 5)
        ));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Mã Áo:"), gbc);
        gbc.gridx = 1;
        panel.add(txtMaAo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Tên Áo:"), gbc);
        gbc.gridx = 1;
        panel.add(txtTenAo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Loại Áo:"), gbc);
        gbc.gridx = 1;
        panel.add(cboLoaiAo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Thương hiệu:"), gbc);
        gbc.gridx = 1;
        panel.add(cboThuongHieu, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Chất liệu:"), gbc);
        gbc.gridx = 1;
        panel.add(cboChatLieu, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(new JLabel("Mô tả:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        JScrollPane scrollMoTa = new JScrollPane(txtMoTa);
        panel.add(scrollMoTa, gbc);

        return panel;
    }

    private JPanel createAoTablePanel() {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBackground(SECONDARY_COLOR);

        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlButton.setBackground(SECONDARY_COLOR);

        btnThem = createButton("Thêm", BUTTON_ADD_COLOR);
        btnSua = createButton("Sửa", BUTTON_EDIT_COLOR);
        btnXoa = createButton("Xóa", BUTTON_DELETE_COLOR);
        btnMoi = createButton("Làm mới", BUTTON_REFRESH_COLOR);

        btnThem.addActionListener(e -> themAo());
        btnSua.addActionListener(e -> suaAo());
        btnXoa.addActionListener(e -> xoaAo());
        btnMoi.addActionListener(e -> clearFormAo());

        pnlButton.add(btnThem);
        pnlButton.add(btnSua);
        pnlButton.add(btnXoa);
        pnlButton.add(btnMoi);

        modelAo = new DefaultTableModel(
                new Object[]{"Mã Áo", "Tên Áo", "Loại Áo", "Thương hiệu", "Chất liệu", "Trạng thái"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblAo = new JTable(modelAo);
        styleTable(tblAo);

        tblAo.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tblAo.getSelectedRow();
                if (row >= 0) {
                    loadAoToForm(row);
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tblAo);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Danh sách Áo",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                MAIN_COLOR
        ));

        pnl.add(scroll, BorderLayout.CENTER);
        pnl.add(pnlButton, BorderLayout.SOUTH);
        return pnl;
    }

    private JPanel createAoChiTietInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Thông tin Chi tiết Áo",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                MAIN_COLOR
        ));
        panel.setBackground(SECONDARY_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Thêm label hiển thị ID áo
        JLabel lblAoID = new JLabel("ID Áo hiện tại:");
        JLabel lblAoIDValue = new JLabel("");
        lblAoIDValue.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblAoIDValue.setForeground(MAIN_COLOR);

        txtMaCT = createTextField(false);
        txtSoLuong = createTextField(true);
        txtGiaBan = createTextField(true);
        txtGiaGoc = createTextField(true); // Thêm field giá gốc
        txtAnhChinh = createTextField(true);
        cboSize = createComboBox();
        cboMauSac = createComboBox();

        // Hàng 1: ID Áo và Mã CT
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblAoID, gbc);

        gbc.gridx = 1;
        panel.add(lblAoIDValue, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Mã CT:"), gbc);

        gbc.gridx = 3;
        panel.add(txtMaCT, gbc);

        // Hàng 2: Size và Màu sắc
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Size:"), gbc);

        gbc.gridx = 1;
        panel.add(cboSize, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Màu sắc:"), gbc);

        gbc.gridx = 3;
        panel.add(cboMauSac, gbc);

        // Hàng 3: Số lượng và Giá bán
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Số lượng:"), gbc);

        gbc.gridx = 1;
        panel.add(txtSoLuong, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Giá bán:"), gbc);

        gbc.gridx = 3;
        panel.add(txtGiaBan, gbc);

        // Hàng 4: Giá gốc và Ảnh chính
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Giá gốc:"), gbc);

        gbc.gridx = 1;
        panel.add(txtGiaGoc, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Ảnh chính:"), gbc);

        gbc.gridx = 3;
        panel.add(txtAnhChinh, gbc);

        // Cập nhật giá trị khi load áo
        txtMaAo.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                update();
            }

            public void removeUpdate(DocumentEvent e) {
                update();
            }

            public void insertUpdate(DocumentEvent e) {
                update();
            }

            private void update() {
                lblAoIDValue.setText(txtMaAo.getText());
            }
        });

        return panel;
    }

    private JPanel createAoChiTietTablePanel() {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBackground(SECONDARY_COLOR);

        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlButton.setBackground(SECONDARY_COLOR);

        btnThemCT = createButton("Thêm CT", BUTTON_ADD_COLOR);
        btnSuaCT = createButton("Sửa CT", BUTTON_EDIT_COLOR);
        btnXoaCT = createButton("Xóa CT", BUTTON_DELETE_COLOR);
        btnMoiCT = createButton("Làm mới", BUTTON_REFRESH_COLOR);

        btnThemCT.addActionListener(e -> themAoChiTiet());
        btnSuaCT.addActionListener(e -> suaAoChiTiet());
        btnXoaCT.addActionListener(e -> xoaAoChiTiet());
        btnMoiCT.addActionListener(e -> clearFormAoChiTiet());

        pnlButton.add(btnThemCT);
        pnlButton.add(btnSuaCT);
        pnlButton.add(btnXoaCT);
        pnlButton.add(btnMoiCT);

        modelAoChiTiet = new DefaultTableModel(
                new Object[]{"Mã CT", "Size", "Màu", "Số lượng", "Giá gốc", "Giá bán", "Ảnh"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblAoChiTiet = new JTable(modelAoChiTiet);
        styleTable(tblAoChiTiet);

        tblAoChiTiet.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tblAoChiTiet.getSelectedRow();
                if (row >= 0) {
                    loadAoChiTietToForm(row);
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tblAoChiTiet);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Danh sách Chi tiết Áo",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                MAIN_COLOR
        ));

        pnl.add(scroll, BorderLayout.CENTER);
        pnl.add(pnlButton, BorderLayout.SOUTH);
        return pnl;
    }

    private void styleTable(JTable table) {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(MAIN_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setBackground(Color.WHITE);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(true);
    }

    private JTextField createTextField(boolean editable) {
        JTextField txt = new JTextField();
        txt.setEditable(editable);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 5, 5, 5)
        ));
        return txt;
    }

    private JComboBox<String> createComboBox() {
        JComboBox<String> cbo = new JComboBox<>();
        cbo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cbo.setBackground(Color.WHITE);
        cbo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 5, 5, 5)
        ));
        return cbo;
    }

    private void addFormField(JPanel panel, String label, JComponent component) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(lbl);
        if (component != null) {
            panel.add(component);
        }
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

    private void loadAoTable() {
        modelAo.setRowCount(0);

        List<Ao> dsAo = aoDAO.findAll();
        for (Ao ao : dsAo) {
            modelAo.addRow(new Object[]{
                ao.getAoID(),
                ao.getTenAo(),
                loaiAoDAO.getTenLoaiAoByID(ao.getLoaiAoID()),
                thuongHieuDAO.getTenThuongHieuByID(ao.getThuongHieuID()),
                chatLieuDAO.getTenChatLieuByID(ao.getChatLieuID()),
                ao.isTrangThai() ? "Hoạt động" : "Ngừng"
            });
        }
    }

    private void loadComboBoxData() {
        cboLoaiAo.removeAllItems();
        cboThuongHieu.removeAllItems();
        cboChatLieu.removeAllItems();
        cboSize.removeAllItems();
        cboMauSac.removeAllItems();

        loaiAoDAO.findAll().forEach(loai -> cboLoaiAo.addItem(loai.getTenLoai()));
        thuongHieuDAO.findAll().forEach(th -> cboThuongHieu.addItem(th.getTenThuongHieu()));
        chatLieuDAO.findAll().forEach(cl -> cboChatLieu.addItem(cl.getTenChatLieu()));
        sizeAoDAO.findAll().forEach(size -> cboSize.addItem(size.getTenSize()));
        mauSacDAO.findAll().forEach(mau -> cboMauSac.addItem(mau.getTenMau()));
    }

    private void loadAoToForm(int row) {
        int aoID = (int) modelAo.getValueAt(row, 0);
        Ao ao = aoDAO.findById(aoID);

        if (ao != null) {
            txtMaAo.setText(String.valueOf(ao.getAoID()));
            txtTenAo.setText(ao.getTenAo());
            txtMoTa.setText(ao.getMoTaChiTiet());

            selectComboBoxItem(cboLoaiAo, loaiAoDAO.getTenLoaiAoByID(ao.getLoaiAoID()));
            selectComboBoxItem(cboThuongHieu, thuongHieuDAO.getTenThuongHieuByID(ao.getThuongHieuID()));
            selectComboBoxItem(cboChatLieu, chatLieuDAO.getTenChatLieuByID(ao.getChatLieuID()));

            // Load chi tiết áo
            loadAoChiTietTable(aoID);
        }
    }

    private void loadAoChiTietTable(int aoID) {
        modelAoChiTiet.setRowCount(0);

        List<AoChiTiet> dsCT = aoChiTietDAO.findByAoID(aoID);

        for (AoChiTiet ct : dsCT) {
            modelAoChiTiet.addRow(new Object[]{
                ct.getAoChiTietID(),
                sizeAoDAO.getTenSizeByID(ct.getSizeID()),
                mauSacDAO.getTenMauByID(ct.getMauSacID()),
                ct.getSoLuongTon(),
                String.format("%,.0f", ct.getGiaGoc()), // Giá gốc
                String.format("%,.0f", ct.getGiaBan()), // Giá bán
                ct.getAnhChinh()
            });
        }
    }

    private void loadAoChiTietToForm(int row) {
        int aoChiTietID = (int) modelAoChiTiet.getValueAt(row, 0);

        AoChiTiet ct = aoChiTietDAO.findById(aoChiTietID);
        if (ct != null) {
            txtMaCT.setText(String.valueOf(ct.getAoChiTietID()));
            txtSoLuong.setText(String.valueOf(ct.getSoLuongTon()));
            txtGiaGoc.setText(String.format("%,.0f", ct.getGiaGoc())); // Thêm dòng này
            txtGiaBan.setText(String.format("%,.0f", ct.getGiaBan()));
            txtAnhChinh.setText(ct.getAnhChinh());

            selectComboBoxItem(cboSize, sizeAoDAO.getTenSizeByID(ct.getSizeID()));
            selectComboBoxItem(cboMauSac, mauSacDAO.getTenMauByID(ct.getMauSacID()));
        }
    }

    private void selectComboBoxItem(JComboBox<String> comboBox, String value) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).equals(value)) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    private void clearFormAo() {
        txtMaAo.setText("");
        txtTenAo.setText("");
        txtMoTa.setText("");
        if (cboLoaiAo.getItemCount() > 0) {
            cboLoaiAo.setSelectedIndex(0);
        }
        if (cboThuongHieu.getItemCount() > 0) {
            cboThuongHieu.setSelectedIndex(0);
        }
        if (cboChatLieu.getItemCount() > 0) {
            cboChatLieu.setSelectedIndex(0);
        }
        tblAo.clearSelection();
        modelAoChiTiet.setRowCount(0);
    }

    private void clearFormAoChiTiet() {
        txtMaCT.setText("");
        txtSoLuong.setText("");
        txtGiaGoc.setText(""); // Thêm dòng này
        txtGiaBan.setText("");
        txtAnhChinh.setText("");
        cboSize.setSelectedIndex(0);
        cboMauSac.setSelectedIndex(0);
        tblAoChiTiet.clearSelection();
    }

    private JTabbedPane findParentTabbedPane(Component comp) {
        while (comp != null && !(comp instanceof JTabbedPane)) {
            comp = comp.getParent();
        }
        return (JTabbedPane) comp;
    }

    private void themAo() {
        try {
            if (!validateAoInput()) {
                return;
            }

            Ao ao = new Ao();
            ao.setTenAo(txtTenAo.getText().trim());
            ao.setLoaiAoID(loaiAoDAO.getIdByName(cboLoaiAo.getSelectedItem().toString()));
            ao.setThuongHieuID(thuongHieuDAO.getIdByName(cboThuongHieu.getSelectedItem().toString()));
            ao.setChatLieuID(chatLieuDAO.getIdByName(cboChatLieu.getSelectedItem().toString()));
            ao.setMoTaChiTiet(txtMoTa.getText().trim());
            ao.setTrangThai(true);

            ao = aoDAO.create(ao);

            JOptionPane.showMessageDialog(this, "Thêm áo thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadAoTable();

            // Tự động chọn áo vừa thêm
            for (int i = 0; i < modelAo.getRowCount(); i++) {
                if ((int) modelAo.getValueAt(i, 0) == ao.getAoID()) {
                    tblAo.setRowSelectionInterval(i, i);
                    tblAo.scrollRectToVisible(tblAo.getCellRect(i, 0, true));
                    loadAoToForm(i);
                    break;
                }
            }

            // Tự động chuyển sang tab Chi tiết Áo và focus vào các field
            JTabbedPane tabbedPane = findParentTabbedPane(this);
            if (tabbedPane != null) {
                tabbedPane.setSelectedIndex(1);
            }

            // Focus vào field đầu tiên của chi tiết áo
            cboSize.requestFocusInWindow();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm áo: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaAo() {
        int selectedRow = tblAo.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn áo cần sửa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (!validateAoInput()) {
                return;
            }

            int aoID = Integer.parseInt(txtMaAo.getText());

            Ao ao = new Ao();
            ao.setAoID(aoID);
            ao.setTenAo(txtTenAo.getText().trim());
            ao.setLoaiAoID(loaiAoDAO.getIdByName(cboLoaiAo.getSelectedItem().toString()));
            ao.setThuongHieuID(thuongHieuDAO.getIdByName(cboThuongHieu.getSelectedItem().toString()));
            ao.setChatLieuID(chatLieuDAO.getIdByName(cboChatLieu.getSelectedItem().toString()));
            ao.setMoTaChiTiet(txtMoTa.getText().trim());
            ao.setTrangThai(true);

            aoDAO.update(ao);

            JOptionPane.showMessageDialog(this, "Cập nhật áo thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadAoTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật áo: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaAo() {
        int selectedRow = tblAo.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn áo cần xóa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int aoID = (int) tblAo.getValueAt(selectedRow, 0);

        // Kiểm tra áo có chi tiết không
        if (aoChiTietDAO.countByAoID(aoID) > 0) {
            JOptionPane.showMessageDialog(this,
                    "Không thể xóa áo này vì đã có chi tiết sản phẩm!\nHãy xóa chi tiết trước khi xóa áo.",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa áo này?",
                "Xác nhận xóa áo",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                aoDAO.deleteById(aoID);
                JOptionPane.showMessageDialog(this, "Xóa áo thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadAoTable();
                clearFormAo();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa áo: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void themAoChiTiet() {
        try {
            // Kiểm tra đã chọn áo chưa
            if (txtMaAo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn hoặc thêm áo trước khi thêm chi tiết",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!validateAoChiTietInput()) {
                return;
            }

            int aoID = Integer.parseInt(txtMaAo.getText());
            int sizeID = sizeAoDAO.getIdByName(cboSize.getSelectedItem().toString());
            int mauSacID = mauSacDAO.getIdByName(cboMauSac.getSelectedItem().toString());

            // Kiểm tra trùng lặp
            if (aoChiTietDAO.existsByAoAndSizeAndMau(aoID, sizeID, mauSacID)) {
                JOptionPane.showMessageDialog(this,
                        "Đã tồn tại chi tiết với Size và Màu sắc này cho áo này",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            AoChiTiet ct = new AoChiTiet();
            ct.setAoID(aoID);
            ct.setSizeID(sizeID);
            ct.setMauSacID(mauSacID);
            ct.setSoLuongTon(Integer.parseInt(txtSoLuong.getText()));
            ct.setGiaGoc(Double.parseDouble(txtGiaGoc.getText().replace(",", ""))); // Thêm dòng này
            ct.setGiaBan(Double.parseDouble(txtGiaBan.getText().replace(",", "")));
            ct.setAnhChinh(txtAnhChinh.getText().trim());
            ct.setTrangThai(true);

            aoChiTietDAO.create(ct);

            JOptionPane.showMessageDialog(this,
                    "Thêm chi tiết áo thành công!",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);

            // Làm mới form và cập nhật bảng
            loadAoChiTietTable(aoID);
            clearFormAoChiTiet();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi thêm chi tiết áo: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaAoChiTiet() {
        int selectedRow = tblAoChiTiet.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chi tiết áo cần sửa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (!validateAoChiTietInput()) {
                return;
            }

            int aoChiTietID = Integer.parseInt(txtMaCT.getText());

            // Xử lý giá trị số trước khi parse
            String giaGocText = txtGiaGoc.getText().replaceAll("\\.", "").replace(",", ".");
            String giaBanText = txtGiaBan.getText().replaceAll("\\.", "").replace(",", ".");

            AoChiTiet ct = new AoChiTiet();
            ct.setAoChiTietID(aoChiTietID);
            ct.setAoID(Integer.parseInt(txtMaAo.getText())); // Thêm dòng này
            ct.setSizeID(sizeAoDAO.getIdByName(cboSize.getSelectedItem().toString()));
            ct.setMauSacID(mauSacDAO.getIdByName(cboMauSac.getSelectedItem().toString()));
            ct.setSoLuongTon(Integer.parseInt(txtSoLuong.getText()));
            ct.setGiaGoc(Double.parseDouble(giaGocText)); // Sửa lại cách parse
            ct.setGiaBan(Double.parseDouble(giaBanText)); // Sửa lại cách parse
            ct.setAnhChinh(txtAnhChinh.getText().trim());
            ct.setTrangThai(true);

            aoChiTietDAO.update(ct);

            int aoID = (int) tblAo.getValueAt(tblAo.getSelectedRow(), 0);
            JOptionPane.showMessageDialog(this, "Cập nhật chi tiết áo thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadAoChiTietTable(aoID);
        } catch (Exception e) {
            e.printStackTrace(); // In stack trace để debug
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật chi tiết áo: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaAoChiTiet() {
        int selectedRow = tblAoChiTiet.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chi tiết áo cần xóa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa chi tiết áo này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int aoChiTietID = (int) tblAoChiTiet.getValueAt(selectedRow, 0);
                aoChiTietDAO.deleteById(aoChiTietID);

                int aoID = (int) tblAo.getValueAt(tblAo.getSelectedRow(), 0);
                JOptionPane.showMessageDialog(this, "Xóa chi tiết áo thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadAoChiTietTable(aoID);
                clearFormAoChiTiet();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa chi tiết áo: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateAoInput() {
        if (txtTenAo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên áo", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

   private boolean validateAoChiTietInput() {
    if (txtMaAo.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Vui lòng chọn hoặc thêm áo trước",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }

    if (txtSoLuong.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    if (txtGiaGoc.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập giá gốc", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    if (txtGiaBan.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập giá bán", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    try {
        Integer.parseInt(txtSoLuong.getText());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    try {
        // Kiểm tra định dạng số
        String giaGocText = txtGiaGoc.getText().replaceAll("\\.", "").replace(",", ".");
        Double.parseDouble(giaGocText);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Giá gốc phải là số hợp lệ", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    try {
        // Kiểm tra định dạng số
        String giaBanText = txtGiaBan.getText().replaceAll("\\.", "").replace(",", ".");
        Double.parseDouble(giaBanText);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Giá bán phải là số hợp lệ", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    return true;
}
}
