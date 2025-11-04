/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poly.Shirt.Enity;

import java.util.Date;

/**
 *
 * @author trinh
 */


public class KhuyenMai {
    private int khuyenMaiID;
    private String tenKM;
    private String moTa;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private int loaiGiamGia; // 1: Giảm %, 2: Giảm tiền trực tiếp
    private double giaTriGiam;
    private boolean trangThai;

    // Constructors
    public KhuyenMai() {
    }

    public KhuyenMai(int khuyenMaiID, String tenKM, String moTa, Date ngayBatDau, Date ngayKetThuc, 
                    int loaiGiamGia, double giaTriGiam, boolean trangThai) {
        this.khuyenMaiID = khuyenMaiID;
        this.tenKM = tenKM;
        this.moTa = moTa;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.loaiGiamGia = loaiGiamGia;
        this.giaTriGiam = giaTriGiam;
        this.trangThai = trangThai;
    }

    // Getters and Setters
    public int getKhuyenMaiID() {
        return khuyenMaiID;
    }

    public void setKhuyenMaiID(int khuyenMaiID) {
        this.khuyenMaiID = khuyenMaiID;
    }

    public String getTenKM() {
        return tenKM;
    }

    public void setTenKM(String tenKM) {
        this.tenKM = tenKM;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public int getLoaiGiamGia() {
        return loaiGiamGia;
    }

    public void setLoaiGiamGia(int loaiGiamGia) {
        this.loaiGiamGia = loaiGiamGia;
    }

    public double getGiaTriGiam() {
        return giaTriGiam;
    }

    public void setGiaTriGiam(double giaTriGiam) {
        this.giaTriGiam = giaTriGiam;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

}
