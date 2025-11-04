package Poly.Shirt.Ui;

import Poly.Shirt.dao.DoiTraDAO;
import Poly.Shirt.impl.DoiTraDAOImpl;
import Poly.Shirt.Enity.DoiTra;
import Poly.Shirt.Util.AppContext;
import Poly.Shirt.Util.XDate;
import Poly.Shirt.Util.XDialog;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DoiTraPanel extends JPanel {

    private JTextField txtHoaDonID, txtLyDo;
    private JComboBox<String> cboTrangThai;
    private JButton btnTao, btnCapNhat, btnXoa, btnLamMoi;
    private JTable tblDoiTra;
    private DefaultTableModel model;
    private DoiTraDAO dao = new DoiTraDAOImpl();

    // Các trường tìm kiếm
    private JTextField txtTimHoaDonID, txtTimNhanVienID;
    private JComboBox<String> cboTimTrangThai;
    private JTextField txtTimTuNgay, txtTimDenNgay;
    private JButton btnTimKiem, btnLamMoiTim;

    // Màu sắc
    private final Color MAIN_COLOR = new Color(70, 130, 180); // SteelBlue
    private final Color SECONDARY_COLOR = new Color(240, 240, 240); // Light gray

    public DoiTraPanel() {
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
        JLabel lblTitle = new JLabel("QUẢN LÝ ĐỔI TRẢ HÀNG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(MAIN_COLOR);
        titlePanel.add(lblTitle);

        // Panel form nhập liệu
        JPanel pnlForm = new JPanel(new GridLayout(4, 2, 10, 10));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin đổi trả"));
        pnlForm.setBackground(SECONDARY_COLOR);

        addFormField(pnlForm, "Mã hóa đơn:", txtHoaDonID = new JTextField());
        addFormField(pnlForm, "Lý do đổi trả:", txtLyDo = new JTextField());

        pnlForm.add(new JLabel("Trạng thái:"));
        cboTrangThai = new JComboBox<>(new String[]{"Chờ xử lý", "Đã chấp nhận", "Từ chối"});
        pnlForm.add(cboTrangThai);

        // Panel nút chức năng
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlButton.setBackground(SECONDARY_COLOR);

        btnTao = createButton("Tạo yêu cầu", new Color(46, 125, 50));
        btnCapNhat = createButton("Cập nhật", new Color(41, 98, 255));
        btnXoa = createButton("Xóa", new Color(198, 40, 40));
        btnLamMoi = createButton("Làm mới", new Color(158, 158, 158));

        pnlButton.add(btnTao);
        pnlButton.add(btnCapNhat);
        pnlButton.add(btnXoa);
        pnlButton.add(btnLamMoi);

        // Panel tìm kiếm
        JPanel pnlTimKiem = new JPanel(new GridLayout(2, 4, 10, 10));
        pnlTimKiem.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Tìm kiếm yêu cầu đổi trả",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                MAIN_COLOR
        ));
        pnlTimKiem.setBackground(SECONDARY_COLOR);

        addSearchField(pnlTimKiem, "Mã hóa đơn:", txtTimHoaDonID = new JTextField());
        addSearchField(pnlTimKiem, "Mã nhân viên:", txtTimNhanVienID = new JTextField());

        pnlTimKiem.add(new JLabel("Trạng thái:"));
        cboTimTrangThai = new JComboBox<>(new String[]{"Tất cả", "Chờ xử lý", "Đã chấp nhận", "Từ chối"});
        pnlTimKiem.add(cboTimTrangThai);

        addSearchField(pnlTimKiem, "Từ ngày:", txtTimTuNgay = new JTextField());
        addSearchField(pnlTimKiem, "Đến ngày:", txtTimDenNgay = new JTextField());

        JPanel pnlButtonTim = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        pnlButtonTim.setBackground(SECONDARY_COLOR);

        btnTimKiem = createButton("Tìm kiếm", MAIN_COLOR);
        btnLamMoiTim = createButton("Làm mới", new Color(158, 158, 158));

        pnlButtonTim.add(btnTimKiem);
        pnlButtonTim.add(btnLamMoiTim);

        pnlTimKiem.add(pnlButtonTim);

        // Panel top (form + button + search)
        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setBackground(SECONDARY_COLOR);
        pnlTop.add(pnlForm, BorderLayout.NORTH);
        pnlTop.add(pnlButton, BorderLayout.CENTER);
        pnlTop.add(pnlTimKiem, BorderLayout.SOUTH);

        // Bảng danh sách
        model = new DefaultTableModel(new Object[]{"Mã ĐT", "Mã HĐ", "Mã NV", "Lý do", "Ngày ĐT", "Trạng thái"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDoiTra = new JTable(model);
        tblDoiTra.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblDoiTra.setRowHeight(30);
        tblDoiTra.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Sự kiện click vào bảng
        tblDoiTra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tblDoiTra.getSelectedRow();
                if (row >= 0) {
                    txtHoaDonID.setText(model.getValueAt(row, 1).toString());
                    txtLyDo.setText(model.getValueAt(row, 3).toString());
                    cboTrangThai.setSelectedIndex(getTrangThaiIndex(model.getValueAt(row, 5).toString()));
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tblDoiTra);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách yêu cầu đổi trả"));

        // Layout chính
        add(titlePanel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(pnlTop, BorderLayout.NORTH);
        centerPanel.add(scroll, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void addEventListeners() {
        btnTao.addActionListener(e -> taoYeuCau());
        btnCapNhat.addActionListener(e -> capNhatYeuCau());
        btnXoa.addActionListener(e -> xoaYeuCau());
        btnLamMoi.addActionListener(e -> lamMoiForm());

        btnTimKiem.addActionListener(e -> timKiemYeuCau());
        btnLamMoiTim.addActionListener(e -> lamMoiTimKiem());
    }

    private void taoYeuCau() {
        try {
            DoiTra dt = getFormData();
            dt.setNhanVienID(AppContext.getTaiKhoanDangNhap().getNhanVienID());
            dt.setTrangThai(1); // Mặc định là chờ xử lý
            dt.setNgayDoiTra(new java.sql.Date(new Date().getTime()));

            DoiTra created = dao.create(dt);
            if (created != null) {
                XDialog.alert("Tạo yêu cầu đổi trả thành công!");
                loadTable();
                lamMoiForm();
            } else {
                XDialog.alert("Tạo yêu cầu thất bại!");
            }
        } catch (Exception ex) {
            XDialog.alert("Lỗi: " + ex.getMessage());
        }
    }

    private void capNhatYeuCau() {
        int row = tblDoiTra.getSelectedRow();
        if (row < 0) {
            XDialog.alert("Vui lòng chọn yêu cầu cần cập nhật");
            return;
        }

        try {
            DoiTra dt = getFormData();
            dt.setDoiTraID((int) model.getValueAt(row, 0));
            dt.setNhanVienID(AppContext.getTaiKhoanDangNhap().getNhanVienID());

            // Giữ nguyên ngày hiện tại nếu không có thay đổi
            if (dt.getNgayDoiTra() == null) {
                // Lấy ngày từ dòng đang chọn hoặc ngày hiện tại nếu không có
                String ngayStr = model.getValueAt(row, 4).toString();
                Date ngay = ngayStr.isEmpty() ? new Date() : XDate.parse(ngayStr, "dd/MM/yyyy");
                dt.setNgayDoiTra(new java.sql.Date(ngay.getTime()));
            }

            dao.update(dt);
            XDialog.alert("Cập nhật yêu cầu thành công!");
            loadTable(); // Tải lại bảng để hiển thị dữ liệu mới
        } catch (Exception ex) {
            XDialog.alert("Lỗi: " + ex.getMessage());
        }
    }

    private void xoaYeuCau() {
        int row = tblDoiTra.getSelectedRow();
        if (row < 0) {
            XDialog.alert("Vui lòng chọn yêu cầu cần xóa");
            return;
        }

        if (XDialog.confirm("Bạn có chắc chắn muốn xóa yêu cầu này?")) {
            try {
                int doiTraID = (int) model.getValueAt(row, 0);
                dao.deleteById(doiTraID);
                XDialog.alert("Xóa yêu cầu thành công!");
                loadTable();
                lamMoiForm();
            } catch (Exception ex) {
                XDialog.alert("Lỗi: " + ex.getMessage());
            }
        }
    }

    private void lamMoiForm() {
        txtHoaDonID.setText("");
        txtLyDo.setText("");
        cboTrangThai.setSelectedIndex(0);
        tblDoiTra.clearSelection();
    }

    private void timKiemYeuCau() {
        try {
            Integer hoaDonID = txtTimHoaDonID.getText().isEmpty() ? null : Integer.parseInt(txtTimHoaDonID.getText());
            Integer nhanVienID = txtTimNhanVienID.getText().isEmpty() ? null : Integer.parseInt(txtTimNhanVienID.getText());

            Integer trangThai = null;
            if (cboTimTrangThai.getSelectedIndex() > 0) {
                trangThai = cboTimTrangThai.getSelectedIndex(); // 1: Chờ xử lý, 2: Đã chấp nhận, 3: Từ chối
            }

            Date tuNgay = null;
            Date denNgay = null;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            try {
                if (!txtTimTuNgay.getText().isEmpty()) {
                    tuNgay = sdf.parse(txtTimTuNgay.getText());
                }
                if (!txtTimDenNgay.getText().isEmpty()) {
                    denNgay = sdf.parse(txtTimDenNgay.getText());
                }
            } catch (Exception e) {
                XDialog.alert("Định dạng ngày không hợp lệ (dd/MM/yyyy)");
                return;
            }

            List<DoiTra> list;
            if (tuNgay != null && denNgay != null) {
                list = dao.findByDateRange(tuNgay, denNgay);
            } else if (hoaDonID != null) {
                list = dao.findByHoaDonID(hoaDonID);
            } else if (nhanVienID != null) {
                list = dao.findByNhanVienID(nhanVienID);
            } else if (trangThai != null) {
                list = dao.findByTrangThai(trangThai);
            } else {
                list = dao.findAll();
            }

            // Hiển thị kết quả
            model.setRowCount(0);
            for (DoiTra dt : list) {
                model.addRow(new Object[]{
                    dt.getDoiTraID(),
                    dt.getHoaDonID(),
                    dt.getNhanVienID(),
                    dt.getLyDo(),
                    XDate.format(dt.getNgayDoiTra(), "dd/MM/yyyy"),
                    dt.getTrangThaiString()
                });
            }

        } catch (NumberFormatException ex) {
            XDialog.alert("Vui lòng nhập số hợp lệ cho mã hóa đơn hoặc mã nhân viên");
        } catch (Exception ex) {
            XDialog.alert("Lỗi khi tìm kiếm: " + ex.getMessage());
        }
    }

    private void lamMoiTimKiem() {
        txtTimHoaDonID.setText("");
        txtTimNhanVienID.setText("");
        txtTimTuNgay.setText("");
        txtTimDenNgay.setText("");
        cboTimTrangThai.setSelectedIndex(0);
        loadTable(); // Tải lại toàn bộ dữ liệu
    }

    private DoiTra getFormData() {
        DoiTra dt = new DoiTra();

        // Validate
        if (txtHoaDonID.getText().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (txtLyDo.getText().isEmpty()) {
            throw new IllegalArgumentException("Lý do không được để trống");
        }

        dt.setHoaDonID(Integer.parseInt(txtHoaDonID.getText()));
        dt.setLyDo(txtLyDo.getText());
        dt.setTrangThai(cboTrangThai.getSelectedIndex() + 1);

        // Thiết lập ngày hiện tại nếu chưa có
        if (dt.getNgayDoiTra() == null) {
            dt.setNgayDoiTra(new java.sql.Date(new Date().getTime()));
        }

        return dt;
    }

    private void loadTable() {
        model.setRowCount(0);
        try {
            List<DoiTra> list = dao.findAll();
            for (DoiTra dt : list) {
                String ngayStr = dt.getNgayDoiTra() != null
                        ? XDate.format(dt.getNgayDoiTra(), "dd/MM/yyyy")
                        : XDate.format(new Date(), "dd/MM/yyyy"); // Hiển thị ngày hiện tại nếu null

                model.addRow(new Object[]{
                    dt.getDoiTraID(),
                    dt.getHoaDonID(),
                    dt.getNhanVienID(),
                    dt.getLyDo(),
                    ngayStr,
                    dt.getTrangThaiString()
                });
            }
        } catch (Exception e) {
            XDialog.alert("Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    private int getTrangThaiIndex(String trangThai) {
        switch (trangThai) {
            case "Chờ xử lý":
                return 0;
            case "Đã chấp nhận":
                return 1;
            case "Từ chối":
                return 2;
            default:
                return 0;
        }
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

    private void addSearchField(JPanel panel, String label, JTextField textField) {
        panel.add(new JLabel(label));
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
