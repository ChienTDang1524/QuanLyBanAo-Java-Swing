package Poly.Shirt.Ui;

import Poly.Shirt.Enity.*;
import Poly.Shirt.Util.AppContext;
import Poly.Shirt.dao.*;
import Poly.Shirt.impl.*;
import Poly.Shirt.Util.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuanLyDonHang extends JPanel {

    private JTable tblHoaDon, tblChiTiet, tblSanPham;
    private DefaultTableModel hoaDonModel, chiTietModel, sanPhamModel;
    private JComboBox<String> cboKhuyenMai, cboKhachHang;
    private JTextField txtTongTien, txtTienGiam, txtThanhTien, txtTienKhachDua, txtTienThua;
    private JButton btnTaoHD, btnThanhToan, btnHuyHD, btnXoaSP;
    private JLabel lblTrangThai;

    private HoaDonDAO hoaDonDAO = new HoaDonDAOImpl();
    private HoaDonChiTietDAO hdctDAO = new HoaDonChiTietDAOImpl();
    private AoChiTietDAO aoChiTietDAO = new AoChiTietDAOImpl();
    private KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAOImpl();
    private KhachHangDAO khachHangDAO = new KhachHangDAOImpl();

    private int currentHoaDonID = -1;
    private boolean isAdjusting = false;

    public QuanLyDonHang() {
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // Panel chính chứa các bảng và nút chức năng
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // Panel trái chứa 3 bảng xếp dọc
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Bảng hóa đơn (danh sách đơn hàng)
        hoaDonModel = new DefaultTableModel(new Object[]{"Mã HĐ", "Khách hàng", "Nhân viên", "Ngày tạo", "Tổng tiền", "Trạng thái"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblHoaDon = new JTable(hoaDonModel);
        tblHoaDon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblHoaDon.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tblHoaDon.getSelectedRow();
                if (row >= 0 && row < hoaDonModel.getRowCount()) {
                    currentHoaDonID = (int) hoaDonModel.getValueAt(row, 0);
                    loadChiTietHoaDon(currentHoaDonID);
                } else {
                    currentHoaDonID = -1;
                }
                updateButtonStates();
            }
        });
        JScrollPane scrollHoaDon = new JScrollPane(tblHoaDon);
        scrollHoaDon.setBorder(BorderFactory.createTitledBorder("Danh sách đơn hàng"));
        leftPanel.add(scrollHoaDon);

        // Bảng chi tiết hóa đơn (giỏ hàng)
        chiTietModel = new DefaultTableModel(new Object[]{"Mã SP", "Tên SP", "Size", "Màu", "Số lượng", "Đơn giá", "Thành tiền"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Chỉ cho phép sửa số lượng
            }
        };
        tblChiTiet = new JTable(chiTietModel);

        // Sự kiện thay đổi số lượng
        tblChiTiet.getModel().addTableModelListener(e -> {
            if (e.getColumn() == 4) {
                int row = e.getFirstRow();
                try {
                    int aoChiTietID = (int) chiTietModel.getValueAt(row, 0);
                    int soLuong = Integer.parseInt(tblChiTiet.getValueAt(row, 4).toString());
                    int soLuongTon = getSoLuongTon(aoChiTietID);

                    if (soLuong <= 0) {
                        JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        tblChiTiet.setValueAt(1, row, 4);
                        return;
                    }

                    if (soLuong > soLuongTon) {
                        JOptionPane.showMessageDialog(this, "Số lượng vượt quá tồn kho (" + soLuongTon + ")", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        tblChiTiet.setValueAt(soLuongTon, row, 4);
                        return;
                    }

                    double donGia = (double) tblChiTiet.getValueAt(row, 5);
                    double thanhTien = soLuong * donGia;
                    tblChiTiet.setValueAt(thanhTien, row, 6);
                    tinhTongTien();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    tblChiTiet.setValueAt(1, row, 4);
                }
            }
        });

        JScrollPane scrollChiTiet = new JScrollPane(tblChiTiet);
        scrollChiTiet.setBorder(BorderFactory.createTitledBorder("Giỏ hàng"));
        leftPanel.add(scrollChiTiet);

        // Nút xóa sản phẩm
        btnXoaSP = new JButton("Xóa sản phẩm");
        btnXoaSP.addActionListener(e -> {
            int row = tblChiTiet.getSelectedRow();
            if (row >= 0) {
                chiTietModel.removeRow(row);
                tinhTongTien();
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });
        leftPanel.add(btnXoaSP);

        // Bảng sản phẩm
        sanPhamModel = new DefaultTableModel(new Object[]{"Mã SP", "Tên SP", "Size", "Màu", "SL tồn", "Giá bán"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblSanPham = new JTable(sanPhamModel);
        tblSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    themSanPhamVaoHoaDon();
                }
            }
        });
        JScrollPane scrollSanPham = new JScrollPane(tblSanPham);
        scrollSanPham.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm"));
        leftPanel.add(scrollSanPham);

        // Panel phải chứa các nút chức năng và thông tin thanh toán
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setPreferredSize(new Dimension(350, getHeight()));

        // Panel thông tin khách hàng
        JPanel khachHangPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        khachHangPanel.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));

        khachHangPanel.add(new JLabel("Khách hàng:"));
        cboKhachHang = new JComboBox<>();
        cboKhachHang.addItem("Khách lẻ");
        khachHangPanel.add(cboKhachHang);

        rightPanel.add(khachHangPanel);

        // Panel thông tin thanh toán
        JPanel infoPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin thanh toán"));

        infoPanel.add(new JLabel("Khuyến mãi:"));
        cboKhuyenMai = new JComboBox<>();
        cboKhuyenMai.addItem("Không áp dụng");
        cboKhuyenMai.addActionListener(e -> {
            if (!isAdjusting) {
                apDungKhuyenMai();
            }
        });
        infoPanel.add(cboKhuyenMai);

        infoPanel.add(new JLabel("Tổng tiền:"));
        txtTongTien = new JTextField("0");
        txtTongTien.setEditable(false);
        infoPanel.add(txtTongTien);

        infoPanel.add(new JLabel("Tiền giảm:"));
        txtTienGiam = new JTextField("0");
        txtTienGiam.setEditable(false);
        infoPanel.add(txtTienGiam);

        infoPanel.add(new JLabel("Thành tiền:"));
        txtThanhTien = new JTextField("0");
        txtThanhTien.setEditable(false);
        infoPanel.add(txtThanhTien);

        infoPanel.add(new JLabel("Tiền khách đưa:"));
        txtTienKhachDua = new JTextField("0");
        txtTienKhachDua.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                tinhTienThua();
            }

            public void removeUpdate(DocumentEvent e) {
                tinhTienThua();
            }

            public void insertUpdate(DocumentEvent e) {
                tinhTienThua();
            }
        });
        infoPanel.add(txtTienKhachDua);

        infoPanel.add(new JLabel("Tiền thừa:"));
        txtTienThua = new JTextField("0");
        txtTienThua.setEditable(false);
        infoPanel.add(txtTienThua);

        // Label hiển thị trạng thái
        lblTrangThai = new JLabel("Trạng thái: Chưa chọn hóa đơn");
        lblTrangThai.setForeground(Color.BLUE);
        infoPanel.add(lblTrangThai);
        infoPanel.add(new JLabel()); // Placeholder

        rightPanel.add(infoPanel);

        // Panel nút chức năng
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Chức năng"));

        btnTaoHD = new JButton("Tạo hóa đơn");
        btnTaoHD.addActionListener(e -> taoHoaDon());

        btnThanhToan = new JButton("Thanh toán");
        btnThanhToan.addActionListener(e -> thanhToanHoaDon());

        btnHuyHD = new JButton("Hủy hóa đơn");
        btnHuyHD.addActionListener(e -> huyHoaDon());

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> {
            chiTietModel.setRowCount(0);
            resetThanhToan();
            currentHoaDonID = -1;
            tblHoaDon.clearSelection();
            updateButtonStates();
        });
        buttonPanel.add(btnClear);

        buttonPanel.add(btnTaoHD);
        buttonPanel.add(btnThanhToan);
        buttonPanel.add(btnHuyHD);
        buttonPanel.add(Box.createVerticalGlue());

        rightPanel.add(buttonPanel);

        // Thêm vào panel chính
        mainPanel.add(leftPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        add(mainPanel, BorderLayout.CENTER);

        // Cập nhật trạng thái nút ban đầu
        updateButtonStates();
    }

    private void updateButtonStates() {
        boolean hasSelectedInvoice = currentHoaDonID != -1 && tblHoaDon.getSelectedRow() >= 0;

        if (hasSelectedInvoice) {
            try {
                int row = tblHoaDon.getSelectedRow();
                if (row >= 0 && row < hoaDonModel.getRowCount()) {
                    int trangThai = (int) hoaDonModel.getValueAt(row, 5);

                    btnThanhToan.setEnabled(trangThai == 1); // Chỉ enable nếu trạng thái "Chờ xử lý"
                    btnHuyHD.setEnabled(trangThai == 1);    // Chỉ enable nếu trạng thái "Chờ xử lý"

                    lblTrangThai.setText("Trạng thái: " + getTrangThaiText(trangThai));
                    lblTrangThai.setForeground(trangThai == 1 ? Color.BLUE
                            : trangThai == 2 ? Color.GREEN
                                    : Color.RED);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Xử lý lỗi nếu cần
            }
        } else {
            btnThanhToan.setEnabled(false);
            btnHuyHD.setEnabled(false);
            lblTrangThai.setText("Trạng thái: Chưa chọn hóa đơn");
            lblTrangThai.setForeground(Color.BLUE);
        }

        btnTaoHD.setEnabled(chiTietModel.getRowCount() > 0);
    }

    private int getSoLuongTon(int aoChiTietID) {
        try {
            String sql = "SELECT SoLuongTon FROM AoChiTiet WHERE AoChiTietID = ?";
            try (ResultSet rs = XJdbc.executeQuery(sql, aoChiTietID)) {
                if (rs.next()) {
                    return rs.getInt("SoLuongTon");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void loadData() {
        loadHoaDon();
        loadSanPham();
        loadKhuyenMai();
        loadKhachHang();
    }

    private void loadKhachHang() {
        try {
            cboKhachHang.removeAllItems();
            cboKhachHang.addItem("Khách lẻ");

            List<KhachHang> list = khachHangDAO.findAll();
            for (KhachHang kh : list) {
                cboKhachHang.addItem(kh.getTenKH() + " - " + kh.getSoDienThoai() + " (ID: " + kh.getKhachHangID() + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách khách hàng", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadHoaDon() {
        try {
            hoaDonModel.setRowCount(0);
            List<HoaDon> list = hoaDonDAO.findAllWithCustomerAndStaffInfo();

            for (HoaDon hd : list) {
                hoaDonModel.addRow(new Object[]{
                    hd.getHoaDonID(),
                    hd.getTenKhachHang() != null ? hd.getTenKhachHang() : "Khách lẻ",
                    hd.getTenNhanVien(),
                    hd.getNgayTao(),
                    formatCurrency(hd.getThanhTien()),
                    hd.getTrangThai() // Lưu mã trạng thái để xử lý
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách hóa đơn", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSanPham() {
        try {
            sanPhamModel.setRowCount(0);
            String sql = "SELECT act.AoChiTietID, a.TenAo, sa.TenSize, ms.TenMau, act.SoLuongTon, act.GiaBan "
                    + "FROM AoChiTiet act "
                    + "JOIN Ao a ON act.AoID = a.AoID "
                    + "JOIN SizeAo sa ON act.SizeID = sa.SizeID "
                    + "JOIN MauSac ms ON act.MauSacID = ms.MauSacID "
                    + "WHERE act.TrangThai = 1 AND act.SoLuongTon > 0";

            try (ResultSet rs = XJdbc.executeQuery(sql)) {
                while (rs.next()) {
                    sanPhamModel.addRow(new Object[]{
                        rs.getInt("AoChiTietID"),
                        rs.getString("TenAo"),
                        rs.getString("TenSize"),
                        rs.getString("TenMau"),
                        rs.getInt("SoLuongTon"),
                        rs.getDouble("GiaBan")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách sản phẩm", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadKhuyenMai() {
        isAdjusting = true;
        try {
            cboKhuyenMai.removeAllItems();
            cboKhuyenMai.addItem("Không áp dụng");

            List<KhuyenMai> activePromotions = khuyenMaiDAO.findActivePromotions(new Date());

            for (KhuyenMai km : activePromotions) {
                String displayText = String.format("%s (Giảm %.0f%s) (ID: %d)",
                        km.getTenKM(),
                        km.getGiaTriGiam(),
                        km.getLoaiGiamGia() == 1 ? "%" : "VND",
                        km.getKhuyenMaiID());
                cboKhuyenMai.addItem(displayText);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải khuyến mãi: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            isAdjusting = false;
        }
    }

    private void loadChiTietHoaDon(int hoaDonID) {
        try {
            chiTietModel.setRowCount(0);
            List<HoaDonChiTiet> list = hdctDAO.findByHoaDonID(hoaDonID);

            double tongTien = 0;
            for (HoaDonChiTiet hdct : list) {
                Object[] productDetails = getProductDetails(hdct.getAoChiTietID());

                chiTietModel.addRow(new Object[]{
                    hdct.getAoChiTietID(),
                    productDetails[0],
                    productDetails[1],
                    productDetails[2],
                    hdct.getSoLuong(),
                    hdct.getDonGia(),
                    hdct.getThanhTien()
                });

                tongTien += hdct.getThanhTien();
            }

            HoaDon hd = hoaDonDAO.findById(hoaDonID);
            if (hd != null) {
                txtTongTien.setText(formatCurrency(hd.getTongTien()));
                txtTienGiam.setText(formatCurrency(hd.getTienGiamGia()));
                txtThanhTien.setText(formatCurrency(hd.getThanhTien()));

                if (hd.getKhuyenMaiID() != null) {
                    for (int i = 0; i < cboKhuyenMai.getItemCount(); i++) {
                        if (cboKhuyenMai.getItemAt(i).contains("ID: " + hd.getKhuyenMaiID())) {
                            cboKhuyenMai.setSelectedIndex(i);
                            break;
                        }
                    }
                } else {
                    cboKhuyenMai.setSelectedIndex(0);
                }
            }

            updateButtonStates();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải chi tiết hóa đơn", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Object[] getProductDetails(int aoChiTietID) {
        String sql = "SELECT a.TenAo, sa.TenSize, ms.TenMau "
                + "FROM AoChiTiet act "
                + "JOIN Ao a ON act.AoID = a.AoID "
                + "JOIN SizeAo sa ON act.SizeID = sa.SizeID "
                + "JOIN MauSac ms ON act.MauSacID = ms.MauSacID "
                + "WHERE act.AoChiTietID = ?";

        try (ResultSet rs = XJdbc.executeQuery(sql, aoChiTietID)) {
            if (rs.next()) {
                return new Object[]{
                    rs.getString("TenAo"),
                    rs.getString("TenSize"),
                    rs.getString("TenMau")
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Object[]{"", "", ""};
    }

    private void themSanPhamVaoHoaDon() {
        int row = tblSanPham.getSelectedRow();
        if (row == -1) {
            return;
        }

        int aoChiTietID = (int) sanPhamModel.getValueAt(row, 0);
        String tenSP = sanPhamModel.getValueAt(row, 1).toString();
        String size = sanPhamModel.getValueAt(row, 2).toString();
        String mau = sanPhamModel.getValueAt(row, 3).toString();
        int soLuongTon = (int) sanPhamModel.getValueAt(row, 4);
        double giaBan = (double) sanPhamModel.getValueAt(row, 5);

        // Kiểm tra số lượng tồn
        if (soLuongTon <= 0) {
            JOptionPane.showMessageDialog(this, "Sản phẩm đã hết hàng", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra sản phẩm đã có trong giỏ chưa
        for (int i = 0; i < chiTietModel.getRowCount(); i++) {
            if ((int) chiTietModel.getValueAt(i, 0) == aoChiTietID) {
                int soLuongHienTai = (int) chiTietModel.getValueAt(i, 4);
                if (soLuongHienTai + 1 > soLuongTon) {
                    JOptionPane.showMessageDialog(this, "Số lượng trong kho không đủ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                chiTietModel.setValueAt(soLuongHienTai + 1, i, 4);
                double thanhTien = (soLuongHienTai + 1) * giaBan;
                chiTietModel.setValueAt(thanhTien, i, 6);
                tinhTongTien();
                return;
            }
        }

        // Thêm mới sản phẩm vào giỏ
        chiTietModel.addRow(new Object[]{
            aoChiTietID,
            tenSP,
            size,
            mau,
            1,
            giaBan,
            giaBan
        });

        tinhTongTien();
        updateButtonStates();
    }

    private void tinhTongTien() {
        double tongTien = 0;
        for (int i = 0; i < chiTietModel.getRowCount(); i++) {
            try {
                Object value = chiTietModel.getValueAt(i, 6);
                double thanhTien = (value instanceof Number) ? ((Number) value).doubleValue()
                        : Double.parseDouble(value.toString());
                tongTien += thanhTien;
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi tính toán thành tiền", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        txtTongTien.setText(formatCurrency(tongTien));
        apDungKhuyenMai();
        updateButtonStates();
    }

    private void apDungKhuyenMai() {
        try {
            if (isAdjusting) {
                return;
            }

            double tongTien = parseCurrency(txtTongTien.getText());
            if (tongTien <= 0) {
                return;
            }

            double tienGiam = 0;
            double thanhTien = tongTien;

            // Nếu không chọn khuyến mãi
            if (cboKhuyenMai.getSelectedIndex() <= 0) {
                txtTienGiam.setText(formatCurrency(0));
                txtThanhTien.setText(formatCurrency(tongTien));
                tinhTienThua();
                return;
            }

            // Lấy thông tin khuyến mãi từ combobox
            String selectedText = cboKhuyenMai.getSelectedItem().toString();
            int khuyenMaiID = extractKhuyenMaiID(selectedText);

            if (khuyenMaiID <= 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin khuyến mãi", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Lấy thông tin khuyến mãi từ database
            KhuyenMai km = khuyenMaiDAO.findById(khuyenMaiID);
            if (km == null || km.getNgayKetThuc().before(new Date())) {
                JOptionPane.showMessageDialog(this, "Khuyến mãi không tồn tại hoặc đã hết hạn", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                cboKhuyenMai.setSelectedIndex(0);
                return;
            }

            // Tính toán tiền giảm theo loại khuyến mãi
            if (km.getLoaiGiamGia() == 1) { // Giảm theo %
                tienGiam = tongTien * km.getGiaTriGiam() / 100;
            } else { // Giảm trực tiếp
                tienGiam = Math.min(km.getGiaTriGiam(), tongTien);
            }

            // Tính thành tiền cuối cùng
            thanhTien = tongTien - tienGiam;
            thanhTien = Math.max(thanhTien, 0); // Đảm bảo không âm

            // Cập nhật UI
            txtTienGiam.setText(formatCurrency(tienGiam));
            txtThanhTien.setText(formatCurrency(thanhTien));
            tinhTienThua();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi áp dụng khuyến mãi: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int extractKhuyenMaiID(String selectedText) {
        try {
            if (selectedText == null || !selectedText.contains("ID: ")) {
                return -1;
            }

            Pattern pattern = Pattern.compile(".*ID: (\\d+)\\)");
            Matcher matcher = pattern.matcher(selectedText);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void tinhTienThua() {
        try {
            double thanhTien = parseCurrency(txtThanhTien.getText());
            double tienKhachDua = parseCurrency(txtTienKhachDua.getText());

            // Kiểm tra tiền khách đưa hợp lệ
            if (tienKhachDua < 0) {
                txtTienKhachDua.setText("0");
                tienKhachDua = 0;
            }

            double tienThua = tienKhachDua - thanhTien;
            txtTienThua.setText(formatCurrency(Math.max(tienThua, 0)));
        } catch (Exception e) {
            txtTienThua.setText(formatCurrency(0));
        }
    }

    private void taoHoaDon() {
        if (chiTietModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm sản phẩm vào giỏ hàng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            HoaDon hd = new HoaDon();
            hd.setNhanVienID(AppContext.getTaiKhoanDangNhap().getNhanVienID());
            hd.setNgayTao(new Date());

            int selectedKhachHangIndex = cboKhachHang.getSelectedIndex();
            if (selectedKhachHangIndex > 0) {
                String selectedItem = cboKhachHang.getSelectedItem().toString();
                int khachHangID = Integer.parseInt(selectedItem.substring(selectedItem.indexOf("(ID: ") + 5, selectedItem.indexOf(")")));
                hd.setKhachHangID(khachHangID);
            }

            hd.setTongTien(parseCurrency(txtTongTien.getText()));
            hd.setTienGiamGia(parseCurrency(txtTienGiam.getText()));
            hd.setThanhTien(parseCurrency(txtThanhTien.getText()));
            hd.setTrangThai(1);

            // Phần xử lý khuyến mãi
            int selectedIndex = cboKhuyenMai.getSelectedIndex();
            if (selectedIndex > 0) {
                String selectedItem = cboKhuyenMai.getSelectedItem().toString();
                // Kiểm tra xem chuỗi có chứa "ID: " không trước khi xử lý
                if (selectedItem.contains("ID: ")) {
                    try {
                        int startIndex = selectedItem.indexOf("ID: ") + 4;
                        int endIndex = selectedItem.indexOf(")", startIndex);
                        if (endIndex > startIndex) {
                            int khuyenMaiID = Integer.parseInt(selectedItem.substring(startIndex, endIndex));
                            hd.setKhuyenMaiID(khuyenMaiID);
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Không thể đọc ID khuyến mãi", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            HoaDon newHd = hoaDonDAO.create(hd);
            if (newHd != null) {
                currentHoaDonID = newHd.getHoaDonID();
                // Cập nhật selection trong bảng
                for (int i = 0; i < hoaDonModel.getRowCount(); i++) {
                    if ((int) hoaDonModel.getValueAt(i, 0) == currentHoaDonID) {
                        tblHoaDon.setRowSelectionInterval(i, i);
                        break;
                    }
                }

                for (int i = 0; i < chiTietModel.getRowCount(); i++) {
                    HoaDonChiTiet hdct = new HoaDonChiTiet();
                    hdct.setHoaDonID(currentHoaDonID);
                    hdct.setAoChiTietID((int) chiTietModel.getValueAt(i, 0));
                    hdct.setSoLuong((int) chiTietModel.getValueAt(i, 4));
                    hdct.setDonGia((double) chiTietModel.getValueAt(i, 5));
                    hdct.setThanhTien((double) chiTietModel.getValueAt(i, 6));

                    hdctDAO.create(hdct);

                    // Cập nhật số lượng tồn kho
                    if (!aoChiTietDAO.giamSoLuongTon(hdct.getAoChiTietID(), hdct.getSoLuong())) {
                        throw new RuntimeException("Không đủ số lượng tồn kho cho sản phẩm ID: " + hdct.getAoChiTietID());
                    }
                }
                updateButtonStates(); // Gọi sau khi đã cập nhật selection
                JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công! Mã HĐ: " + currentHoaDonID,
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadHoaDon();
                loadSanPham(); // Cập nhật lại số lượng tồn kho
                chiTietModel.setRowCount(0);
                resetThanhToan();
                updateButtonStates();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tạo hóa đơn: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void thanhToanHoaDon() {
        if (currentHoaDonID == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần thanh toán", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double tienKhachDua = parseCurrency(txtTienKhachDua.getText());
            double thanhTien = parseCurrency(txtThanhTien.getText());

            if (tienKhachDua < thanhTien) {
                JOptionPane.showMessageDialog(this, "Số tiền khách đưa không đủ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Xác nhận thanh toán hóa đơn " + currentHoaDonID + "?",
                    "Xác nhận thanh toán",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            HoaDon hd = hoaDonDAO.findById(currentHoaDonID);
            if (hd != null) {
                hd.setTrangThai(2);
                hoaDonDAO.update(hd);

                // Cập nhật trạng thái trong model
                int row = tblHoaDon.getSelectedRow();
                if (row >= 0) {
                    hoaDonModel.setValueAt(2, row, 5); // Cập nhật cột trạng thái
                }

                updateButtonStates(); // Gọi sau khi đã cập nhật
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thanh toán hóa đơn", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void huyHoaDon() {
        if (currentHoaDonID == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần hủy", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn hủy hóa đơn " + currentHoaDonID + "?",
                    "Xác nhận hủy hóa đơn",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            HoaDon hd = hoaDonDAO.findById(currentHoaDonID);
            if (hd != null) {
                // Hoàn trả số lượng tồn kho trước khi hủy

                List<HoaDonChiTiet> chiTietList = hdctDAO.findByHoaDonID(currentHoaDonID);
                for (HoaDonChiTiet hdct : chiTietList) {
                    aoChiTietDAO.tangSoLuongTon(hdct.getAoChiTietID(), hdct.getSoLuong());
                }

                hd.setTrangThai(3);
                hoaDonDAO.update(hd);

                JOptionPane.showMessageDialog(this, "Hủy hóa đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadHoaDon();
                loadSanPham(); // Cập nhật lại số lượng tồn kho
                updateButtonStates();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi hủy hóa đơn", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetThanhToan() {
        isAdjusting = true;
        try {
            txtTongTien.setText(formatCurrency(0));
            txtTienGiam.setText(formatCurrency(0));
            txtThanhTien.setText(formatCurrency(0));
            txtTienKhachDua.setText(formatCurrency(0));
            txtTienThua.setText(formatCurrency(0));
            cboKhuyenMai.setSelectedIndex(0);
            cboKhachHang.setSelectedIndex(0);
        } finally {
            isAdjusting = false;
        }
    }

    private String getTrangThaiText(int trangThai) {
        switch (trangThai) {
            case 1:
                return "Chờ xử lý";
            case 2:
                return "Đã thanh toán";
            case 3:
                return "Đã hủy";
            default:
                return "Không xác định";
        }
    }

    private String formatCurrency(double amount) {
        try {
            // Làm tròn đến hàng đơn vị
            long roundedAmount = Math.round(amount);
            NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
            return formatter.format(roundedAmount) + " VND";
        } catch (Exception e) {
            return String.format("%,.0f VND", amount);
        }
    }

    private double parseCurrency(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        try {
            // Loại bỏ tất cả ký tự không phải số
            String cleanText = text.replaceAll("[^\\d]", "");
            return Double.parseDouble(cleanText);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
