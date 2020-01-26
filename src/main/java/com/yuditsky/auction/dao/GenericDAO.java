package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Identified;

import java.util.List;

public interface GenericDAO<T extends Identified> {

    void save(T object) throws DAOException;

    T findById(int id) throws DAOException;

    List<T> findAll() throws DAOException;

    void update(T object) throws DAOException;

    void delete(T object) throws DAOException;
}
