package com.hisoft.web.controller;

import com.hisoft.entity.Category;
import com.hisoft.entity.News;
import com.hisoft.service.CategoryService;
import com.hisoft.service.NewsService;
import com.hisoft.service.impl.CategoryServiceImpl;
import com.hisoft.service.impl.NewsServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Home的servlet,处理来自主页的请求
 */
@WebServlet("/Home")
public class HomeController extends HttpServlet {

    private CategoryService categoryService = new CategoryServiceImpl();

    private NewsService newsService = new NewsServiceImpl();

    private Logger logger = Logger.getLogger(HomeController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }
        switch (action) {
            //判断action属于什么
            case "index": {
                execIndex(req, resp);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    /**
     * 用户请求首页,查询category和news信息并且返回首页,在request域中存的数据如下:
     * request.setAttribute("categoryList",categoryList)
     * request.setAttribute("newsList",newsList)
     * <p>
     * categoryList:   一级category的集合,但其中包含二级category数组,并且二级数组中的每一个category包含三级category数组
     * newsList:   就是新闻的集合,取出最新的前n个
     * <p>
     * 返回首页:请求转发至首页,路径为下:
     * http://localhost:8080/EasyBuy_war_exploded/pre/index.jsp
     *
     * @param request
     * @param response
     */
    private void execIndex(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categoryList = categoryService.queryLevel_1_Categories();
        List<News> newsList = newsService.queryNewsByDateByOrder(5);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("newsList", newsList);
        try {
            request.getRequestDispatcher("pre/index.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
