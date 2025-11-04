/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Poly.Shirt.dao;

/**
 *
 * @author trinh
 */

import Poly.Shirt.Enity.Ao;
import java.util.List;

public interface AoDAO extends CrudDAO<Ao, Integer> {
    List<Ao> findByCriteria(String tenAo, Integer loaiAoID, Integer thuongHieuID, Integer chatLieuID, Boolean trangThai);
    List<Ao> findByTenAo(String tenAo);
    int countByLoaiAoID(int loaiAoID);
    int countByThuongHieuID(int thuongHieuID);
    int countByChatLieuID(int chatLieuID);
    int getLastInsertID();
}
