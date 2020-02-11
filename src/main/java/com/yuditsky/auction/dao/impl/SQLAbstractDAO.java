package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.GenericDAO;
import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.dao.connection.ConnectionPoolException;
import com.yuditsky.auction.entity.Identified;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class SQLAbstractDAO<T extends Identified> implements GenericDAO<T> {
    private final static Logger logger = LogManager.getLogger(SQLAbstractDAO.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    protected abstract List<T> parseResultSet(ResultSet resultSet) throws DAOException;

    protected abstract String getSelectByIdQuery();

    protected abstract String getSelectAllQuery();

    protected abstract String getInsertQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws DAOException;

    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws DAOException;

    @Override
    public void save(T object) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(getInsertQuery());

            prepareStatementForInsert(statement, object);

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL error", e);
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, statement);
        }
    }

    @Override
    public T findById(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(getSelectByIdQuery());

            statement.setString(1, String.valueOf(id));

            resultSet = statement.executeQuery();

            List<T> list = parseResultSet(resultSet);

            if (list.size() == 0) {
                return null;
            }

            if (list.size() > 1) {
                logger.error("More than one record found by id");
                throw new DAOException();
            }

            return list.iterator().next();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL error", e);
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
    }

    @Override
    public List<T> findAll() throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(getSelectAllQuery());

            resultSet = statement.executeQuery();

            List<T> list = parseResultSet(resultSet);

            return list;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL error", e);
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
    }

    @Override
    public void update(T object) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(getUpdateQuery());

            prepareStatementForUpdate(statement, object);

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL error", e);
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, statement);
        }
    }

    @Override
    public void delete(T object) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(getDeleteQuery());

            statement.setString(1, String.valueOf(object.getId()));

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL error", e);
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, statement);
        }
    }
}
