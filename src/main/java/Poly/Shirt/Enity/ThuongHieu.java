/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poly.Shirt.Enity;

/**
 *
 * @author trinh
 */
public class ThuongHieu {
    private int thuongHieuID;
    private String tenThuongHieu;
    private String logo;
    private String quocGia;
    private boolean trangThai; 

    public ThuongHieu() {
    }

    public ThuongHieu(int thuongHieuID, String tenThuongHieu, String logo, String quocGia, boolean trangThai) {
        this.thuongHieuID = thuongHieuID;
        this.tenThuongHieu = tenThuongHieu;
        this.logo = logo;
        this.quocGia = quocGia;
        this.trangThai = trangThai;
    }

    public ThuongHieu(String tenThuongHieu, String logo, String quocGia, boolean trangThai) {
        this.tenThuongHieu = tenThuongHieu;
        this.logo = logo;
        this.quocGia = quocGia;
        this.trangThai = trangThai;
    }

    public int getThuongHieuID() {
        return thuongHieuID;
    }

    public void setThuongHieuID(int thuongHieuID) {
        this.thuongHieuID = thuongHieuID;
    }

    public String getTenThuongHieu() {
        return tenThuongHieu;
    }

    public void setTenThuongHieu(String tenThuongHieu) {
        this.tenThuongHieu = tenThuongHieu;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getQuocGia() {
        return quocGia;
    }

    public void setQuocGia(String quocGia) {
        this.quocGia = quocGia;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
    
}
