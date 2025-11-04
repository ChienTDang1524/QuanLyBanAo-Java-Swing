package Poly.Shirt.Util;

import Poly.Shirt.Enity.TaiKhoan;

public class AppContext {
    private static TaiKhoan taiKhoanDangNhap;
    
    public static void setTaiKhoanDangNhap(TaiKhoan taiKhoan) {
        taiKhoanDangNhap = taiKhoan;
    }
    
    public static TaiKhoan getTaiKhoanDangNhap() {
        return taiKhoanDangNhap;
    }
    
    public static boolean isAdmin() {
        return taiKhoanDangNhap != null && taiKhoanDangNhap.getVaiTroID() == 1;
    }
}