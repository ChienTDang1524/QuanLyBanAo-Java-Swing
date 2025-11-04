package Poly.Shirt.Enity;

import java.util.Date;

public class HoaDon {
    private int hoaDonID;
    private Integer khachHangID;
    private int nhanVienID;
    private Integer khuyenMaiID;
    private Date ngayTao;
    private double tongTien;
    private double tienGiamGia;
    private double thanhTien;
    private int trangThai; // 1: Chờ xử lý, 2: Đã thanh toán, 3: Đã hủy
    private String ghiChu;
    
    private String TenNhanVien;
    private String TenKhachHang;

    public String getTenNhanVien() {
        return TenNhanVien;
    }

    public void setTenNhanVien(String TenNhanVien) {
        this.TenNhanVien = TenNhanVien;
    }

    public String getTenKhachHang() {
        return TenKhachHang;
    }

    public void setTenKhachHang(String TenKhachHang) {
        this.TenKhachHang = TenKhachHang;
    }
    
    // Constructors
    public HoaDon() {
    }

    public HoaDon(int nhanVienID, double tongTien, double thanhTien) {
        this.nhanVienID = nhanVienID;
        this.tongTien = tongTien;
        this.thanhTien = thanhTien;
        this.trangThai = 1; // Mặc định là chờ xử lý
    }

    // Getters and Setters
    public int getHoaDonID() {
        return hoaDonID;
    }

    public void setHoaDonID(int hoaDonID) {
        this.hoaDonID = hoaDonID;
    }

    public Integer getKhachHangID() {
        return khachHangID;
    }

    public void setKhachHangID(Integer khachHangID) {
        this.khachHangID = khachHangID;
    }

    public int getNhanVienID() {
        return nhanVienID;
    }

    public void setNhanVienID(int nhanVienID) {
        this.nhanVienID = nhanVienID;
    }

    public Integer getKhuyenMaiID() {
        return khuyenMaiID;
    }

    public void setKhuyenMaiID(Integer khuyenMaiID) {
        this.khuyenMaiID = khuyenMaiID;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public double getTienGiamGia() {
        return tienGiamGia;
    }

    public void setTienGiamGia(double tienGiamGia) {
        this.tienGiamGia = tienGiamGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}