/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Poly.Shirt.dao;

/**
 *
 * @author trinh
 */


import Poly.Shirt.Enity.ChatLieu;
import java.util.List;

public interface ChatLieuDAO extends CrudDAO<ChatLieu, Integer> {
    String getTenChatLieuByID(int id);
    int getIdByName(String tenChatLieu);
}
