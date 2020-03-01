package com.yuditsky.auction.dao.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private final static Logger logger = LogManager.getLogger(ConnectionPool.class);

    private String driver;
    private String url;
    private String user;
    private String password;
    private int poolSize;

    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> givenAwayConnectionQueue;
    private static ConnectionPool instance;
    private static ReentrantLock lock = new ReentrantLock();

    private ConnectionPool() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        driver = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        url = dbResourceManager.getValue(DBParameter.DB_URL);
        user = dbResourceManager.getValue(DBParameter.DB_USER);
        password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
        poolSize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POOL_SIZE));
        try {
            initPoolData();
        } catch (ConnectionPoolException e) {
            logger.error("Can't init pool data");
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            lock.lock();
            if (instance == null) {
                instance = new ConnectionPool();
            }
            lock.unlock();
        }
        return instance;
    }

    public void initPoolData() throws ConnectionPoolException {
        try {
            Class.forName(driver);

            givenAwayConnectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
            connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                PooledConnection pooledConnection = new PooledConnection(connection);
                connectionQueue.add(pooledConnection);
            }
        } catch (SQLException e) {
            logger.error("Can't init connection pool data", e);
            throw new ConnectionPoolException(e);
        } catch (ClassNotFoundException e) {
            logger.error("Can't find database driver class", e);
            throw new ConnectionPoolException(e);
        }
    }

    public void dispose() throws ConnectionPoolException {
        clearConnectionQueue();
    }

    public void clearConnectionQueue() throws ConnectionPoolException {
        try {
            closeConnectionsQueue(givenAwayConnectionQueue);
            closeConnectionsQueue(connectionQueue);
        } catch (SQLException e) {
            logger.error("Error closing the connection", e);
            throw new ConnectionPoolException(e);
        }
    }

    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            connection = connectionQueue.take();
            givenAwayConnectionQueue.add(connection);
        } catch (InterruptedException e) {
            logger.error("Error connecting to the datasource", e);
            throw new ConnectionPoolException(e);
        }
        return connection;
    }

    public void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        closeResultSet(resultSet);
        closeStatement(statement);
        closeConnection(connection);
    }

    public void closeConnection(Connection connection, Statement statement) {
        closeStatement(statement);
        closeConnection(connection);
    }

    private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
        Connection connection;

        while (((connection = queue.poll()) != null)) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            ((PooledConnection) connection).reallyClose();
        }
    }

    public void releaseConnection(Connection connection) throws SQLException {
        if (!givenAwayConnectionQueue.remove(connection)) {
            throw new SQLException("Error deleting connection from the given away connections pool");
        }

        if (!connectionQueue.offer(connection)) {
            throw new SQLException("Error allocating connection in the pool");
        }
    }

    private void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error("Statement isn't closed", e);
        }
    }

    private void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.error("ResultSet isn't closed", e);
        }
    }

    private void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Connection isn't return to the pool", e);
        }
    }
}
