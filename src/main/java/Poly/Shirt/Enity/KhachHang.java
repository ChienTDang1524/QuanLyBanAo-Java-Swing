package Poly.Shirt.Enity;

import java.sql.Date;

public class KhachHang {
    private int khachHangID;
    private String tenKH;
    private String soDienThoai;
    private String email;
    private String diaChi;

    public KhachHang() {
    }

    public KhachHang(int khachHangID, String tenKH, String soDienThoai, String email, String diaChi) {
        this.khachHangID = khachHangID;
        this.tenKH = tenKH;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
    }

    public KhachHang(String tenKH, String soDienThoai, String email, String diaChi) {
        this.tenKH = tenKH;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
    }

    public int getKhachHangID() {
        return khachHangID;
    }

    public void setKhachHangID(int khachHangID) {
        this.khachHangID = khachHangID;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
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

 @Override
    public String toString() {
        return "KhachHang{" + 
               "khachHangID=" + khachHangID + 
               ", tenKH=" + tenKH + 
               ", soDienThoai=" + soDienThoai + 
               ", email=" + email + 
               ", diaChi=" + diaChi + '}';
    }
   

   
}
