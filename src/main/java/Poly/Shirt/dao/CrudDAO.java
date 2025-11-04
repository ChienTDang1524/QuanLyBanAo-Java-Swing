/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Poly.Shirt.dao;

import java.util.List;

public interface CrudDAO<T, ID> {

    T create(T entity);

    void update(T entity);

    void deleteById(Integer id);

    List<T> findAll();

    T findById(ID id);

    int countByField(String fieldName, Object value);
}
