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
public class LichSuDangNhap {

    private int logID;
    private int taiKhoanID;
    private Date thoiGian;
    private String IPAddress;
    private boolean trangThai;
    private String moTa;

    public LichSuDangNhap() {
    }

    public LichSuDangNhap(int logID, int taiKhoanID, Date thoiGian, String IPAddress, boolean trangThai, String moTa) {
        this.logID = logID;
        this.taiKhoanID = taiKhoanID;
        this.thoiGian = thoiGian;
        this.IPAddress = IPAddress;
        this.trangThai = trangThai;
        this.moTa = moTa;
    }

    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public int getTaiKhoanID() {
        return taiKhoanID;
    }

    public void setTaiKhoanID(int taiKhoanID) {
        this.taiKhoanID = taiKhoanID;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @Override
    public String toString() {
        return "LichSuDangNhap{" + "logID=" + logID + ", taiKhoanID=" + taiKhoanID + ", thoiGian=" + thoiGian + ", IPAddress=" + IPAddress + ", trangThai=" + trangThai + ", moTa=" + moTa + '}';
    }

}
