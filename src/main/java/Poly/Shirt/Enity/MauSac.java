/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poly.Shirt.Enity;

/**
 *
 * @author trinh
 */
public class MauSac {
      private int mauSacID;
    private String tenMau;
    private String hexCode;
    private boolean trangThai;

    public MauSac() {
    }

    public MauSac(int mauSacID, String tenMau, String hexCode, boolean trangThai) {
        this.mauSacID = mauSacID;
        this.tenMau = tenMau;
        this.hexCode = hexCode;
        this.trangThai = trangThai;
    }

    public MauSac(String tenMau, String hexCode, boolean trangThai) {
        this.tenMau = tenMau;
        this.hexCode = hexCode;
        this.trangThai = trangThai;
    }

    public int getMauSacID() {
        return mauSacID;
    }

    public void setMauSacID(int mauSacID) {
        this.mauSacID = mauSacID;
    }

    public String getTenMau() {
        return tenMau;
    }

    public void setTenMau(String tenMau) {
        this.tenMau = tenMau;
    }

    public String getHexCode() {
        return hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
    
}
