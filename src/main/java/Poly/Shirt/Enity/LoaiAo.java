/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poly.Shirt.Enity;

/**
 *
 * @author trinh
 */
public class LoaiAo {
    private int loaiAoID;
    private String tenLoai;
    private String moTa;
    private boolean trangThai;

    public LoaiAo() {
    }

    public LoaiAo(int loaiAoID, String tenLoai, String moTa, boolean trangThai) {
        this.loaiAoID = loaiAoID;
        this.tenLoai = tenLoai;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public LoaiAo(String tenLoai, String moTa, boolean trangThai) {
        this.tenLoai = tenLoai;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public int getLoaiAoID() {
        return loaiAoID;
    }

    public void setLoaiAoID(int loaiAoID) {
        this.loaiAoID = loaiAoID;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
    
}
