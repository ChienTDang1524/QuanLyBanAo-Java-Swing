package Poly.Shirt.Enity;

import java.sql.Date;

public class DoiTra {
    private int doiTraID;
    private int hoaDonID;
    private int nhanVienID;
    private String lyDo;
    private Date ngayDoiTra;
    private int trangThai; // 1: Chờ xử lý, 2: Đã chấp nhận, 3: Từ chối

    public DoiTra() {
    }

    public DoiTra(int doiTraID, int hoaDonID, int nhanVienID, String lyDo, 
                 Date ngayDoiTra, int trangThai) {
        this.doiTraID = doiTraID;
        this.hoaDonID = hoaDonID;
        this.nhanVienID = nhanVienID;
        this.lyDo = lyDo;
        this.ngayDoiTra = ngayDoiTra;
        this.trangThai = trangThai;
    }

    // Getters and Setters
    public int getDoiTraID() {
        return doiTraID;
    }

    public void setDoiTraID(int doiTraID) {
        this.doiTraID = doiTraID;
    }

    public int getHoaDonID() {
        return hoaDonID;
    }

    public void setHoaDonID(int hoaDonID) {
        this.hoaDonID = hoaDonID;
    }

    public int getNhanVienID() {
        return nhanVienID;
    }

    public void setNhanVienID(int nhanVienID) {
        this.nhanVienID = nhanVienID;
    }

    public String getLyDo() {
        return lyDo;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public Date getNgayDoiTra() {
        return ngayDoiTra;
    }

    public void setNgayDoiTra(Date ngayDoiTra) {
        this.ngayDoiTra = ngayDoiTra;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getTrangThaiString() {
        switch (trangThai) {
            case 1: return "Chờ xử lý";
            case 2: return "Đã chấp nhận";
            case 3: return "Từ chối";
            default: return "Không xác định";
        }
    }

    @Override
    public String toString() {
        return "DoiTra{" + "doiTraID=" + doiTraID + ", hoaDonID=" + hoaDonID + 
               ", nhanVienID=" + nhanVienID + ", lyDo=" + lyDo + 
               ", ngayDoiTra=" + ngayDoiTra + ", trangThai=" + getTrangThaiString() + '}';
    }
}