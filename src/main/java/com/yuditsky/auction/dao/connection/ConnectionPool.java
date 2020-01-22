package com.yuditsky.auction.dao.connection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private final static Logger logger = LogManager.getLogger(ConnectionPool.class);

    private String driver;
    private String url;
    private String user;
    private String password;
    private int poolSize;
    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> givenAwayConnectionQueue;
    private final static ConnectionPool instance = new ConnectionPool();

    private ConnectionPool() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        driver = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        url = dbResourceManager.getValue(DBParameter.DB_URL);
        user = dbResourceManager.getValue(DBParameter.DB_USER);
        password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
        poolSize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POOL_SIZE));
    }

    public static ConnectionPool getInstance() {
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
            logger.log(Level.ERROR, "Can't init connection pool data", e);
            throw new ConnectionPoolException(e);
        } catch (ClassNotFoundException e) {
            logger.log(Level.ERROR, "Can't find database driver class", e);
            throw new ConnectionPoolException(e);
        }
    }

    public void dispose() { //
        clearConnectionQueue();
    }

    public void clearConnectionQueue() {
        try {
            closeConnectionsQueue(givenAwayConnectionQueue);
            closeConnectionsQueue(connectionQueue);
        } catch (SQLException e) {
            //bez throw&
            logger.log(Level.ERROR, "Error closing the connection", e);
        }
    }

    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            connection = connectionQueue.take();
            givenAwayConnectionQueue.add(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Error connecting to the datasource", e);
            throw new ConnectionPoolException(e);
        }
        return connection;
    }

    //poryadok&
    public void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            connection.close();
        } catch (SQLException e) {
            //bez throw&
            logger.log(Level.ERROR, "Connection isn't return to the pool", e);
        }

        try {
            resultSet.close();
        } catch (SQLException e) {
            //
            logger.log(Level.ERROR, "ResultSet isn't closed", e);
        }

        try {
            statement.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Statement isn't closed", e);
            //
        }
    }

    public void closeConnection(Connection connection, Statement statement) {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Connection isn't return to the pool", e);
            //
        }

        try {
            statement.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "ResultSet isn't closed", e);
            //
        }
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
}
