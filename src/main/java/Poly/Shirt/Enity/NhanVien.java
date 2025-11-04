package Poly.Shirt.Enity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;


public class NhanVien {
    private int nhanVienID;
    private String tenNV;
    private String soDienThoai;
    private String email;
    private String diaChi;
    private boolean trangThai;

    public NhanVien() {
    }

    public NhanVien(int nhanVienID, String tenNV, String soDienThoai, String email, String diaChi, boolean trangThai) {
        this.nhanVienID = nhanVienID;
        this.tenNV = tenNV;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
    }

    public NhanVien(String tenNV, String soDienThoai, String email, String diaChi, boolean trangThai) {
        this.tenNV = tenNV;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
    }

    public int getNhanVienID() {
        return nhanVienID;
    }

    public void setNhanVienID(int nhanVienID) {
        this.nhanVienID = nhanVienID;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
    
    
    }
