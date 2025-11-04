/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poly.Shirt.Enity;

/**
 *
 * @author trinh
 */
public class AoChiTiet {
      private int aoChiTietID;
    private int aoID;
    private int sizeID;
    private int mauSacID;
    private int soLuongTon;
    private double giaBan;
    private int khuyenMaiID;
    private String anhChinh;
    private boolean trangThai;
    private double giaGoc;

    private String tenAo;
    private String tenSize;
    private String tenMau;

    
    public AoChiTiet() {
    }

    public AoChiTiet(int aoChiTietID, int aoID, int sizeID, int mauSacID, int soLuongTon, double giaBan, int khuyenMaiID, String anhChinh, boolean trangThai, double giaGoc) {
        this.aoChiTietID = aoChiTietID;
        this.aoID = aoID;
        this.sizeID = sizeID;
        this.mauSacID = mauSacID;
        this.soLuongTon = soLuongTon;
        this.giaBan = giaBan;
        this.khuyenMaiID = khuyenMaiID;
        this.anhChinh = anhChinh;
        this.trangThai = trangThai;
        this.giaGoc = giaGoc;
    }

    public AoChiTiet(int aoID, int sizeID, int mauSacID, int soLuongTon, double giaBan, int khuyenMaiID, String anhChinh, boolean trangThai, double giaGoc) {
        this.aoID = aoID;
        this.sizeID = sizeID;
        this.mauSacID = mauSacID;
        this.soLuongTon = soLuongTon;
        this.giaBan = giaBan;
        this.khuyenMaiID = khuyenMaiID;
        this.anhChinh = anhChinh;
        this.trangThai = trangThai;
        this.giaGoc = giaGoc;
    }

    public int getAoChiTietID() {
        return aoChiTietID;
    }

    public void setAoChiTietID(int aoChiTietID) {
        this.aoChiTietID = aoChiTietID;
    }

    public int getAoID() {
        return aoID;
    }

    public void setAoID(int aoID) {
        this.aoID = aoID;
    }

    public int getSizeID() {
        return sizeID;
    }

    public void setSizeID(int sizeID) {
        this.sizeID = sizeID;
    }

    public int getMauSacID() {
        return mauSacID;
    }

    public void setMauSacID(int mauSacID) {
        this.mauSacID = mauSacID;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public int getKhuyenMaiID() {
        return khuyenMaiID;
    }

    public void setKhuyenMaiID(int khuyenMaiID) {
        this.khuyenMaiID = khuyenMaiID;
    }

    public String getAnhChinh() {
        return anhChinh;
    }

    public void setAnhChinh(String anhChinh) {
        this.anhChinh = anhChinh;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public double getGiaGoc() {
        return giaGoc;
    }

    public void setGiaGoc(double giaGoc) {
        this.giaGoc = giaGoc;
    }

    public String getTenAo() {
        return tenAo;
    }

    public void setTenAo(String tenAo) {
        this.tenAo = tenAo;
    }

    public String getTenSize() {
        return tenSize;
    }

    public void setTenSize(String tenSize) {
        this.tenSize = tenSize;
    }

    public String getTenMau() {
        return tenMau;
    }

    public void setTenMau(String tenMau) {
        this.tenMau = tenMau;
    }


 
    
}
