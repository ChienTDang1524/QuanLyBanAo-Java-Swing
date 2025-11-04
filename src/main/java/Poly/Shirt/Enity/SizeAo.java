/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poly.Shirt.Enity;

/**
 *
 * @author trinh
 */
public class SizeAo {
    private int sizeID;
    private String tenSize;
    private String moTa;
    private boolean trangThai;

    public SizeAo() {
    }

    public SizeAo(int sizeID, String tenSize, String moTa, boolean trangThai) {
        this.sizeID = sizeID;
        this.tenSize = tenSize;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public SizeAo(String tenSize, String moTa, boolean trangThai) {
        this.tenSize = tenSize;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public int getSizeID() {
        return sizeID;
    }

    public void setSizeID(int sizeID) {
        this.sizeID = sizeID;
    }

    public String getTenSize() {
        return tenSize;
    }

    public void setTenSize(String tenSize) {
        this.tenSize = tenSize;
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
