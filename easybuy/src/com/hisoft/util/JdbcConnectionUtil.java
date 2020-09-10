package com.hisoft.util;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.log4j.Logger;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JdbcConnectionUtil {

    //数据库连接池
    private static DataSource dataSource;
    private static Logger logger = Logger.getLogger(JdbcConnectionUtil.class);
    //此文件放在resources文件夹下,配置数据库的信息
    private static Properties properties;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            logger.info("驱动加载完成");
            dataSource = new DruidDataSource();
            properties = new Properties();
            properties.load(JdbcConnectionUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));
            initDataSource(dataSource);
        } catch (ClassNotFoundException | IOException e) {
            logger.fatal("无法初始化DataSource或数据库配置文件");
            e.printStackTrace();
        }
    }

    /**
     * 初始化数据库连接池
     * @param dataSource
     */
    private static void initDataSource(DataSource dataSource) {
        logger.info("准备初始化DataSource");
        DruidDataSource druidDataSource = (DruidDataSource) dataSource;
        if (properties == null) {
            logger.fatal("无法指定数据库信息,无properties配置文件");
            throw new IllegalStateException();
        }
        druidDataSource.setUsername(properties.getProperty("jdbc.username"));
        druidDataSource.setDriverClassName(properties.getProperty("jdbc.driver"));
        druidDataSource.setUrl(properties.getProperty("jdbc.url"));
        druidDataSource.setPassword(properties.getProperty("jdbc.password"));
        logger.info("初始化DataSource完毕");
    }


    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close(Statement statement, ResultSet resultSet,Connection connection) {
        try {
            if (statement!=null && !statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error("关闭statement失败");
            e.printStackTrace();
        }
        try {
            if (resultSet!=null && !resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.error("关闭resultSet失败");
            e.printStackTrace();
        }
        try {
            if (connection!=null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("关闭connection失败");
            e.printStackTrace();
        }
    }

    public static void close(Statement statement,Connection connection) {
        close(statement,null,connection);
    }


    public static void close(Connection connection) {
        close(null,connection);
    }
}
