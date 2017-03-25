package com.fisher.bostoudmp.db;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by fisher on 17-3-22.
 */
public class DBManager {
    private static Logger logger = Logger.getLogger(DBManager.class.toString());
    private static DataSource ds = null;

    static {
        Properties properties = new Properties();
        try {
            properties.load(DBManager.class.getClassLoader().getResourceAsStream("db.properties"));
            logger.info("Init DBManger >> :");
            logger.info("db properties ：\n"
                    + "url: " + properties.getProperty("url") + "\n"
                    + "username: " + properties.getProperty("username") + "\n"
                    + "password: " + properties.getProperty("password") + "\n"
            );
            ds = BasicDataSourceFactory.createDataSource(properties);
            logger.info("DB initialized success !");
        } catch (Exception e) {
            logger.error("Sorry db cant not connetct ..........................");
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            logger.error("Get Connection error ......................");
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Close Connection error ......................");
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
