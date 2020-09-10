package com.hisoft.dao.impl;

import com.hisoft.dao.NewsDao;
import com.hisoft.entity.Category;
import com.hisoft.entity.News;
import com.hisoft.util.JdbcConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsDaoImpl extends ConnectionHolder implements NewsDao {


    public NewsDaoImpl(ThreadLocal<Connection> threadLocal) {
        super(threadLocal);
    }



    /*    实现BaseDao的部分开始,注释请查看BaseDao    */

    @Override
    public Integer remove(Integer integer) throws SQLException {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstat = null;
        Integer key = null;
        String sql = null;
        ResultSet rs = null;

        sql = "delete from easybuy_news where id=?";
        pstat = connection.prepareStatement(sql);
        pstat.setInt(1, integer);
        key = pstat.executeUpdate();

        JdbcConnectionUtil.close(pstat, rs, null);
        return key;
    }

    @Override
    public List<News> queryAll() throws SQLException {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstmt = connection.prepareStatement("select id,title,content,createTime from easybuy_news ");
        ResultSet rs = pstmt.executeQuery();
        List<News> newsList = new ArrayList<>();
        while (rs.next()) {
            News news = new News();
            news.setN_Id(rs.getInt("id"));
            news.setN_Title(rs.getString("title"));
            news.setN_Content(rs.getString("content"));
            news.setN_Date(rs.getDate("createTime"));
            newsList.add(news);
        }
        JdbcConnectionUtil.close(pstmt, rs, null);
        return newsList;
    }

    @Override
    public News queryOneByPrimaryKey(Integer integer) throws SQLException {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstmt = connection.prepareStatement("select id,title,content,createTime from easybuy_news where id=? ");
        pstmt.setInt(1, integer);
        ResultSet rs = pstmt.executeQuery();
        News news = new News();
        if (rs.next()) {
            news.setN_Id(rs.getInt("id"));
            news.setN_Title(rs.getString("title"));
            news.setN_Content(rs.getString("content"));
            news.setN_Date(rs.getDate("createTime"));
        }
        JdbcConnectionUtil.close(pstmt, rs, null);
        return news;
    }

    @Override
    public Integer queryCount() throws SQLException {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstmt = connection.prepareStatement("select count(1) num from easybuy_news");
        ResultSet rs = pstmt.executeQuery();
        Integer num = null;
        if (rs.next()) {
            num = rs.getInt("num");
        }
        JdbcConnectionUtil.close(pstmt, rs, null);
        return num;
    }

    @Override
    public Integer insertOne(News news) {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstmt = null;
        Integer key = null;
        try {
            pstmt = connection.prepareStatement("insert into easybuy_news (id,title,content,createTime)values(?,?,?,?) ");
            pstmt.setInt(1, news.getN_Id());
            pstmt.setString(2, news.getN_Title());
            pstmt.setString(3, news.getN_Content());
            pstmt.setObject(4, news.getN_Date());
            key = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(pstmt, null, null);
        }
        return key;
    }

    @Override
    public Integer updateOne(News news, Integer integer) {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstmt = null;
        Integer key = null;
        try {
            pstmt = connection.prepareStatement("Update easybuy_news set title=(?),cantent=(?),createTime=(?) where id=(?)");
            pstmt.setString(1, news.getN_Title());
            pstmt.setString(2, news.getN_Content());
            pstmt.setObject(3, news.getN_Content());
            pstmt.setInt(4, news.getN_Id());
            key = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JdbcConnectionUtil.close(pstmt, null, null);
        return key;
    }


    /*    实现BaseDao的部分结束    注释请查看BaseDao    */





    /*   实现NewsDao的部分----开始----,注释请查看NewsDao    */

    @Override
    public List<News> queryNewsByDateByOrder(Integer n) throws SQLException {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstmt = connection.prepareStatement("select id,title,content,createTime from easybuy_news group by createTime desc limit ?");
        pstmt.setInt(1, n);
        ResultSet rs = pstmt.executeQuery();
        List<News> newsList = new ArrayList<>();
        while (rs.next()) {
            News news = new News();
            news.setN_Id(rs.getInt("id"));
            news.setN_Title(rs.getString("title"));
            news.setN_Content(rs.getString("content"));
            news.setN_Date(rs.getDate("createTime"));
            newsList.add(news);
        }
        JdbcConnectionUtil.close(pstmt, rs, null);
        return newsList;
    }

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public List<News> queryNewsByPage(Integer currentPage, Integer pageSize) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<News> list = new ArrayList<>();
        try {
            sql = "select id,title,content,createTime from easybuy_news limit ?,?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, (currentPage - 1) * pageSize);
            statement.setInt(2, pageSize);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                News news = new News();
                news.setN_Id(resultSet.getInt("id"));
                news.setN_Title(resultSet.getString("title"));
                news.setN_Content(resultSet.getString("content"));
                news.setN_Date(resultSet.getDate("createTime"));
                list.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return list;
    }


    /*   实现NewsDao的部分----结束----,注释请查看NewsDao    */
}
