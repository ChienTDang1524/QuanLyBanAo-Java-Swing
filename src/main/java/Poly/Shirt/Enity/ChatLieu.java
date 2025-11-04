/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poly.Shirt.Enity;

/**
 *
 * @author trinh
 */
public class ChatLieu {
    private int chatLieuID;
    private String tenChatLieu;
    private String moTa;
    private boolean trangThai;

    public ChatLieu() {
    }

    public ChatLieu(int chatLieuID, String tenChatLieu, String moTa, boolean trangThai) {
        this.chatLieuID = chatLieuID;
        this.tenChatLieu = tenChatLieu;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public ChatLieu(String tenChatLieu, String moTa, boolean trangThai) {
        this.tenChatLieu = tenChatLieu;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public int getChatLieuID() {
        return chatLieuID;
    }

    public void setChatLieuID(int chatLieuID) {
        this.chatLieuID = chatLieuID;
    }

    public String getTenChatLieu() {
        return tenChatLieu;
    }

    public void setTenChatLieu(String tenChatLieu) {
        this.tenChatLieu = tenChatLieu;
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
