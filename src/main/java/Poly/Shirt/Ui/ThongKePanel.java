package Poly.Shirt.Ui;

import Poly.Shirt.dao.ThongKeDAO;
import Poly.Shirt.impl.ThongKeDAOImpl;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class ThongKePanel extends JPanel {
    private JTable revenueTable;
    private JLabel totalRevenueLabel, totalCostLabel, totalProfitLabel, totalOrdersLabel;
    private DefaultCategoryDataset dataset;
    private ThongKeDAO thongKeDAO = new ThongKeDAOImpl();

    public ThongKePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("THỐNG KÊ DOANH THU VÀ LỢI NHUẬN");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Main content panel with table and chart
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        revenueTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(revenueTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(tablePanel);

        // Chart panel
        dataset = new DefaultCategoryDataset();
        JFreeChart chart = createChart();
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 400));
        mainPanel.add(chartPanel);

        add(mainPanel, BorderLayout.CENTER);

        // Summary panel
        JPanel summaryPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Tổng kết"));

        summaryPanel.add(new JLabel("Tổng doanh thu:"));
        totalRevenueLabel = new JLabel("0 đ");
        summaryPanel.add(totalRevenueLabel);

        summaryPanel.add(new JLabel("Tổng giá vốn:"));
        totalCostLabel = new JLabel("0 đ");
        summaryPanel.add(totalCostLabel);

        summaryPanel.add(new JLabel("Tổng lợi nhuận:"));
        totalProfitLabel = new JLabel("0 đ");
        summaryPanel.add(totalProfitLabel);

        summaryPanel.add(new JLabel("Tổng số hóa đơn:"));
        totalOrdersLabel = new JLabel("0");
        summaryPanel.add(totalOrdersLabel);

        add(summaryPanel, BorderLayout.SOUTH);

        // Load data
        loadRevenueData();
    }

    private JFreeChart createChart() {
        JFreeChart chart = ChartFactory.createBarChart(
                "BIỂU ĐỒ DOANH THU THEO THÁNG",
                "Tháng",
                "Giá trị (đ)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        
        // Custom colors
        renderer.setSeriesPaint(0, new Color(65, 105, 225));  // Doanh thu
        renderer.setSeriesPaint(1, new Color(220, 20, 60));   // Giá vốn
        renderer.setSeriesPaint(2, new Color(34, 139, 34));    // Lợi nhuận

        return chart;
    }

    private void loadRevenueData() {
        try {
            // Lấy dữ liệu từ DAO
            List<Map<String, Object>> revenueData = thongKeDAO.getRevenueStatistics();
            Map<String, Double> totals = thongKeDAO.getRevenueTotals();

            // Tạo model cho bảng
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Tháng/Năm");
            model.addColumn("Số hóa đơn");
            model.addColumn("Số sản phẩm");
            model.addColumn("Doanh thu");
            model.addColumn("Giá vốn");
            model.addColumn("Lợi nhuận");

            // Định dạng tiền tệ
            DecimalFormat vndFormat = new DecimalFormat("#,##0 đ");

            // Xóa dữ liệu cũ trong biểu đồ
            dataset.clear();

            // Thêm dữ liệu vào bảng và biểu đồ
            for (Map<String, Object> row : revenueData) {
                String monthYear = row.get("ThangNam").toString();
                int orderCount = ((Number) row.get("SoHoaDon")).intValue();
                int productCount = ((Number) row.get("SoSanPham")).intValue();
                double revenue = ((Number) row.get("DoanhThu")).doubleValue();
                double cost = ((Number) row.get("GiaVon")).doubleValue();
                double profit = ((Number) row.get("LoiNhuan")).doubleValue();

                model.addRow(new Object[]{
                    monthYear,
                    orderCount,
                    productCount,
                    vndFormat.format(revenue),
                    vndFormat.format(cost),
                    vndFormat.format(profit)
                });

                // Thêm dữ liệu vào biểu đồ
                dataset.addValue(revenue, "Doanh thu", monthYear);
                dataset.addValue(cost, "Giá vốn", monthYear);
                dataset.addValue(profit, "Lợi nhuận", monthYear);
            }

            revenueTable.setModel(model);

            // Cập nhật tổng kết
            totalRevenueLabel.setText(vndFormat.format(totals.get("TongDoanhThu")));
            totalCostLabel.setText(vndFormat.format(totals.get("TongGiaVon")));
            totalProfitLabel.setText(vndFormat.format(totals.get("TongLoiNhuan")));
            totalOrdersLabel.setText(String.valueOf(totals.get("TongHoaDon").intValue()));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}