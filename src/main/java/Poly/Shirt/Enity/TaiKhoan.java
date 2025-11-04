/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poly.Shirt.Enity;

/**
 *
 * @author trinh
 */
public class TaiKhoan {
     private int taiKhoanID;
    private String tenDangNhap;
    private String matKhau;
    private int nhanVienID;
    private int vaiTroID;
    private boolean trangThai;

    public TaiKhoan(int taiKhoanID, String tenDangNhap, String matKhau, int nhanVienID, int vaiTroID, boolean trangThai) {
        this.taiKhoanID = taiKhoanID;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.nhanVienID = nhanVienID;
        this.vaiTroID = vaiTroID;
        this.trangThai = trangThai;
    }

    public TaiKhoan() {
    }

    public TaiKhoan(String tenDangNhap, String matKhau, int nhanVienID, int vaiTroID, boolean trangThai) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.nhanVienID = nhanVienID;
        this.vaiTroID = vaiTroID;
        this.trangThai = trangThai;
    }

    public int getTaiKhoanID() {
        return taiKhoanID;
    }

    public void setTaiKhoanID(int taiKhoanID) {
        this.taiKhoanID = taiKhoanID;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getNhanVienID() {
        return nhanVienID;
    }

    public void setNhanVienID(int nhanVienID) {
        this.nhanVienID = nhanVienID;
    }

    public int getVaiTroID() {
        return vaiTroID;
    }

    public void setVaiTroID(int vaiTroID) {
        this.vaiTroID = vaiTroID;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" + "taiKhoanID=" + taiKhoanID + ", tenDangNhap=" + tenDangNhap + ", matKhau=" + matKhau + ", nhanVienID=" + nhanVienID + ", vaiTroID=" + vaiTroID + ", trangThai=" + trangThai + '}';
    }
    
}
