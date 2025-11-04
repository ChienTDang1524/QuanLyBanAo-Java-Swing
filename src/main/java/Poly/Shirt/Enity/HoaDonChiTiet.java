/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poly.Shirt.Enity;

/**
 *
 * @author trinh
 */
public class HoaDonChiTiet {
      private int hdctID;
    private int hoaDonID;
    private int aoChiTietID;
    private int soLuong;
    private double donGia;
    private double thanhTien;

      private String tenAo;
    private String tenSize;
    private String tenMau;

    
    // Constructors
    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(int hoaDonID, int aoChiTietID, int soLuong, double donGia) {
        this.hoaDonID = hoaDonID;
        this.aoChiTietID = aoChiTietID;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = soLuong * donGia;
    }

    // Getters and Setters
    public int getHdctID() {
        return hdctID;
    }

    public void setHdctID(int hdctID) {
        this.hdctID = hdctID;
    }

    public int getHoaDonID() {
        return hoaDonID;
    }

    public void setHoaDonID(int hoaDonID) {
        this.hoaDonID = hoaDonID;
    }

    public int getAoChiTietID() {
        return aoChiTietID;
    }

    public void setAoChiTietID(int aoChiTietID) {
        this.aoChiTietID = aoChiTietID;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
        this.thanhTien = this.soLuong * this.donGia;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
        this.thanhTien = this.soLuong * this.donGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
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
