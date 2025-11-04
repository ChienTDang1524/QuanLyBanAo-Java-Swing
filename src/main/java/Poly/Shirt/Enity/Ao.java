/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poly.Shirt.Enity;

/**
 *
 * @author trinh
 */
public class Ao {
     private int aoID;
    private String tenAo;
    private int loaiAoID;
    private int thuongHieuID;
    private int chatLieuID;
    private String moTaChiTiet;
    private boolean trangThai;

    public Ao() {
    }

    public Ao(int aoID, String tenAo, int loaiAoID, int thuongHieuID, int chatLieuID, String moTaChiTiet, boolean trangThai) {
        this.aoID = aoID;
        this.tenAo = tenAo;
        this.loaiAoID = loaiAoID;
        this.thuongHieuID = thuongHieuID;
        this.chatLieuID = chatLieuID;
        this.moTaChiTiet = moTaChiTiet;
        this.trangThai = trangThai;
    }

    public Ao(String tenAo, int loaiAoID, int thuongHieuID, int chatLieuID, String moTaChiTiet, boolean trangThai) {
        this.tenAo = tenAo;
        this.loaiAoID = loaiAoID;
        this.thuongHieuID = thuongHieuID;
        this.chatLieuID = chatLieuID;
        this.moTaChiTiet = moTaChiTiet;
        this.trangThai = trangThai;
    }

    public int getAoID() {
        return aoID;
    }

    public void setAoID(int aoID) {
        this.aoID = aoID;
    }

    public String getTenAo() {
        return tenAo;
    }

    public void setTenAo(String tenAo) {
        this.tenAo = tenAo;
    }

    public int getLoaiAoID() {
        return loaiAoID;
    }

    public void setLoaiAoID(int loaiAoID) {
        this.loaiAoID = loaiAoID;
    }

    public int getThuongHieuID() {
        return thuongHieuID;
    }

    public void setThuongHieuID(int thuongHieuID) {
        this.thuongHieuID = thuongHieuID;
    }

    public int getChatLieuID() {
        return chatLieuID;
    }

    public void setChatLieuID(int chatLieuID) {
        this.chatLieuID = chatLieuID;
    }

    public String getMoTaChiTiet() {
        return moTaChiTiet;
    }

    public void setMoTaChiTiet(String moTaChiTiet) {
        this.moTaChiTiet = moTaChiTiet;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
    
}
