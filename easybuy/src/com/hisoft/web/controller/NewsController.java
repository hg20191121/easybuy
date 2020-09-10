package com.hisoft.web.controller;

import com.hisoft.entity.News;
import com.hisoft.service.NewsService;
import com.hisoft.service.impl.NewsServiceImpl;
import com.hisoft.util.PageHelper;
import com.hisoft.util.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 与新闻操作相关的servlet
 */
@WebServlet("/news")
public class NewsController extends HttpServlet {

    private NewsService newsService = new NewsServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }

        switch (action) {
            //处理点击了一条新闻,下面的方法就是到新闻详情页的页面
            case "newsDetail": {
                execNewsDetail(req, resp);
                break;
            }
            case "queryNewsList": {
                execQueryNewsListByPage(req, resp);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 处理点击新闻的方法,请求转发到以下路径
     *
     *  request请求的参数如下:
     *      id      :       查询的新闻id
     *
     * http://localhost:8080/EasyBuy_war_exploded/backend/news/newsDetail.jsp
     * request域中数据要求如下:
     *  request.setAttribute("news",news);
     *      news:   查询到的一条新闻
     * @param request
     * @param response
     */
    private void execNewsDetail(HttpServletRequest request,HttpServletResponse response) {
        try {
            News news = newsService.queryOneByPrimaryKey(Integer.valueOf(request.getParameter("id")));
            request.setAttribute("news",news);
            request.getRequestDispatcher("backend/news/newsDetail.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     *  请求参数如下:
     *      currentPage :   当前的查询页数
     *      <b>至于一页查询几条,请参考PageHelper.PAGE_SIZE<b/>
     *      如果currentPage为null,则默认为第一页
     *
     * 处理点击新闻更多的方法,请求转发到以下路径
     * http://localhost:8080/EasyBuy_war_exploded/backend/news/newsList.jsp
     * request域中数据要求如下:
     *  request.setAttribute("newsList",newsList);
     *      newsList:   查询到的新闻集合
     *  request.setAttribute("pager",pageHelper);
     *      pageHelper:  分页的辅助对象
     * @param request
     * @param response
     */
    private void execQueryNewsListByPage(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        String currentPage = request.getParameter("currentPage");
        if (!RequestUtil.checkParameters(request,currentPage)) {
            RequestUtil.redirectToIndex(request,response);
            return;
        }
        Integer iCurrentPage;
        try {
            iCurrentPage = Integer.parseInt(currentPage);
        }catch (NumberFormatException e) {
            RequestUtil.redirectToIndex(request,response);
            return;
        }

        List<News> newsList = newsService.queryNewsByPage(iCurrentPage, PageHelper.PAGE_SIZE);
        PageHelper pageHelper = PageHelper.createPageHelper("news?action=queryNewsList", newsService.queryCount(), iCurrentPage);
        request.setAttribute("newsList",newsList);
        request.setAttribute("pager",pageHelper);

        request.getRequestDispatcher("/backend/news/newsList.jsp").forward(request,response);
    }
}
