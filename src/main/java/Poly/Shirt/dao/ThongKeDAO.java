/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Poly.Shirt.dao;

import java.util.List;
import java.util.Map;

/**
 *
 * @author trinh
 */
public interface ThongKeDAO {
    List<Map<String, Object>> getRevenueStatistics();
    Map<String, Double> getRevenueTotals();
}
