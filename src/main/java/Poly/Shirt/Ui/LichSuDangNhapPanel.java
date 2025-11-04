package Poly.Shirt.Ui;

import Poly.Shirt.Enity.LichSuDangNhap;
import Poly.Shirt.Enity.TaiKhoan;
import Poly.Shirt.dao.LichSuDangNhapDAO;
import Poly.Shirt.dao.TaiKhoanDAO;
import Poly.Shirt.impl.LichSuDangNhapDAOImpl;
import Poly.Shirt.impl.TaiKhoanDAOImpl;
import Poly.Shirt.Util.XDate;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;

public class LichSuDangNhapPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private LichSuDangNhapDAO dao = new LichSuDangNhapDAOImpl();
    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAOImpl();

    // Phân trang
    private int currentPage = 1;
    private int pageSize = 20;
    private int totalRecords = 0;

    // Bộ lọc
    private JComboBox<String> cboTaiKhoan;
    private JTextField txtFromDate;
    private JTextField txtToDate;
    private JButton btnFilter;
    private JButton btnReset;
    private JButton btnRefresh;

    // Phân trang
    private JButton btnFirst;
    private JButton btnPrev;
    private JLabel lblPageInfo;
    private JButton btnNext;
    private JButton btnLast;

    // Màu sắc
    private static final Color MAIN_COLOR = new Color(70, 130, 180); // SteelBlue
    private static final Color SUCCESS_COLOR = new Color(46, 125, 50); // Màu xanh thành công
    private static final Color FAILURE_COLOR = new Color(198, 40, 40); // Màu đỏ thất bại
    private static final Color LIGHT_SUCCESS = new Color(220, 255, 220);
    private static final Color LIGHT_FAILURE = new Color(255, 220, 220);
    private static final Color HEADER_COLOR = new Color(240, 240, 240);

    public LichSuDangNhapPanel() {
        initComponents();
        loadData();
        initTableRenderer();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel tiêu đề
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        JLabel lblTitle = new JLabel("LỊCH SỬ ĐĂNG NHẬP");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(MAIN_COLOR);
        titlePanel.add(lblTitle);
        add(titlePanel, BorderLayout.NORTH);

        // Panel chính chứa cả bộ lọc và bảng
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Panel bộ lọc
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(MAIN_COLOR, 1),
            " Bộ lọc ",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.BOLD, 12),
            MAIN_COLOR
        ));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setPreferredSize(new Dimension(800, 100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ComboBox tài khoản
        gbc.gridx = 0; gbc.gridy = 0;
        filterPanel.add(new JLabel("Tài khoản:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        cboTaiKhoan = new JComboBox<>();
        cboTaiKhoan.addItem("Tất cả");
        List<TaiKhoan> taiKhoans = taiKhoanDAO.findAll();
        for (TaiKhoan tk : taiKhoans) {
            cboTaiKhoan.addItem(tk.getTenDangNhap());
        }
        filterPanel.add(cboTaiKhoan, gbc);

        // Ngày bắt đầu
        gbc.gridx = 0; gbc.gridy = 1;
        filterPanel.add(new JLabel("Từ ngày:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        txtFromDate = new JTextField(12);
        txtFromDate.setText(XDate.format(XDate.now(), "dd/MM/yyyy"));
        filterPanel.add(txtFromDate, gbc);

        // Ngày kết thúc
        gbc.gridx = 2; gbc.gridy = 1;
        filterPanel.add(new JLabel("Đến ngày:"), gbc);
        
        gbc.gridx = 3; gbc.gridy = 1;
        txtToDate = new JTextField(12);
        txtToDate.setText(XDate.format(XDate.now(), "dd/MM/yyyy"));
        filterPanel.add(txtToDate, gbc);

        // Nút lọc
        gbc.gridx = 4; gbc.gridy = 1;
        btnFilter = createStyledButton("Lọc", MAIN_COLOR);
        btnFilter.addActionListener(e -> {
            currentPage = 1;
            loadData();
        });
        filterPanel.add(btnFilter, gbc);

        // Nút reset
        gbc.gridx = 5; gbc.gridy = 1;
        btnReset = createStyledButton("Reset", Color.GRAY);
        btnReset.addActionListener(e -> {
            cboTaiKhoan.setSelectedIndex(0);
            txtFromDate.setText(XDate.format(XDate.now(), "dd/MM/yyyy"));
            txtToDate.setText(XDate.format(XDate.now(), "dd/MM/yyyy"));
            currentPage = 1;
            loadData();
        });
        filterPanel.add(btnReset, gbc);

        // Nút refresh
        gbc.gridx = 6; gbc.gridy = 1;
        btnRefresh = createStyledButton("Làm mới", MAIN_COLOR);
        btnRefresh.addActionListener(e -> loadData());
        filterPanel.add(btnRefresh, gbc);

        mainPanel.add(filterPanel, BorderLayout.NORTH);

        // Tạo model cho table
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("ID");
        model.addColumn("Tài khoản");
        model.addColumn("Thời gian");
        model.addColumn("IP Address");
        model.addColumn("Trạng thái");
       

        table = new JTable(model);
        table.setRowHeight(30);
        table.setAutoCreateRowSorter(true);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Tùy chỉnh header của bảng
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(HEADER_COLOR);
        header.setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Panel phân trang
        JPanel paginationPanel = new JPanel();
        paginationPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        paginationPanel.setBackground(Color.WHITE);

        btnFirst = createPaginationButton("<<");
        btnFirst.addActionListener(e -> {
            currentPage = 1;
            loadData();
        });
        paginationPanel.add(btnFirst);

        btnPrev = createPaginationButton("<");
        btnPrev.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                loadData();
            }
        });
        paginationPanel.add(btnPrev);

        lblPageInfo = new JLabel();
        lblPageInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        paginationPanel.add(lblPageInfo);

        btnNext = createPaginationButton(">");
        btnNext.addActionListener(e -> {
            if (currentPage < getTotalPages()) {
                currentPage++;
                loadData();
            }
        });
        paginationPanel.add(btnNext);

        btnLast = createPaginationButton(">>");
        btnLast.addActionListener(e -> {
            currentPage = getTotalPages();
            loadData();
        });
        paginationPanel.add(btnLast);

        add(paginationPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        model.setRowCount(0);

        String username = cboTaiKhoan.getSelectedIndex() == 0 ? null : cboTaiKhoan.getSelectedItem().toString();
        String fromDate = txtFromDate.getText();
        String toDate = txtToDate.getText();

        // Lấy tổng số bản ghi
        totalRecords = dao.countByFilter(username, fromDate, toDate);
        updatePaginationInfo();

        // Lấy dữ liệu phân trang
        List<LichSuDangNhap> list = dao.findByFilter(username, fromDate, toDate, currentPage, pageSize);

        for (LichSuDangNhap log : list) {
            String tenTaiKhoan = log.getTaiKhoanID() > 0
                    ? taiKhoanDAO.findById(log.getTaiKhoanID()).getTenDangNhap() : "N/A";

            model.addRow(new Object[]{
                log.getLogID(),
                tenTaiKhoan,
                XDate.format(log.getThoiGian(), "dd/MM/yyyy HH:mm:ss"),
                log.getIPAddress(),
                log.isTrangThai() ? "Thành công" : "Thất bại",
                log.getMoTa()
            });
        }
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    private JButton createPaginationButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        button.setFocusPainted(false);
        return button;
    }

    private void initTableRenderer() {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                // Căn giữa các cột ID và Trạng thái
                if (column == 0 || column == 4) {
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                }

                // Lấy giá trị cột Trạng thái (cột 4)
                String status = table.getModel().getValueAt(row, 4).toString();

                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                } else {
                    if (status.equals("Thành công")) {
                        c.setBackground(LIGHT_SUCCESS);
                        if (column == 4) {
                            ((JLabel) c).setForeground(SUCCESS_COLOR);
                        }
                    } else {
                        c.setBackground(LIGHT_FAILURE);
                        if (column == 4) {
                            ((JLabel) c).setForeground(FAILURE_COLOR);
                        }
                    }
                    c.setForeground(Color.BLACK);
                }

                return c;
            }
        });
    }

    private int getTotalPages() {
        return (int) Math.ceil((double) totalRecords / pageSize);
    }

    private void updatePaginationInfo() {
        lblPageInfo.setText(String.format("Trang %d/%d - Tổng %d bản ghi",
                currentPage, getTotalPages(), totalRecords));

        btnFirst.setEnabled(currentPage > 1);
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < getTotalPages());
        btnLast.setEnabled(currentPage < getTotalPages());
    }
}