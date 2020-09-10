package com.hisoft.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hisoft.entity.Product;
import com.hisoft.service.FavoriteService;
import com.hisoft.service.ProductService;
import com.hisoft.service.impl.FavoriteServiceImpl;
import com.hisoft.service.impl.ProductServiceImpl;
import com.hisoft.util.PageHelper;
import com.hisoft.util.RequestUtil;
import com.hisoft.util.RetHolder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/Favorite")
public class FavoriteController extends HttpServlet {

    private FavoriteService favoriteService = new FavoriteServiceImpl() ;

    private ProductService  productService = new ProductServiceImpl();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (!RequestUtil.checkParameters(action)) {
            RequestUtil.redirectToIndex(req, resp);
            return;
        }
        switch (action) {
            case "index": {
                execIndex(req,resp);
            }
            case "addFavorite": {
                execAddFavorite(req,resp);
            }
        }
    }

    private void execAddFavorite(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //商品id
        String id = req.getParameter("id");
        if (!RequestUtil.checkParameters(id)) {
            RequestUtil.redirectToIndex(req, resp);
            return;
        }

        RetHolder retHolder;
        PrintWriter writer = resp.getWriter();

        Integer iId;
        try {
            iId = Integer.parseInt(id);
        }catch (NumberFormatException e) {
            RequestUtil.redirectToIndex(req, resp);
            return;
        }
        Product product = productService.queryOneByPrimaryKey(iId);



        if (product == null) {
            retHolder = new RetHolder("error","您要收藏的商品不存在~~~");
        }else {
            if (!RequestUtil.checkOutIfLogin(req)) {
                retHolder = new RetHolder("error","您还未登录~~~");
            }else {
                String user_prefix = RequestUtil.getUserFromSession(req).getU_id().toString();
                if (favoriteService.queryFavoriteProductsFromMemcached(user_prefix).stream().filter(p -> p.getP_id().equals(iId)).count()!=0) {
                    retHolder = new RetHolder("error","您已添加了此商品~~~");
                }
                if (!favoriteService.addFavoriteProduct(product, user_prefix)) {
                    retHolder = new RetHolder("error","添加失败~~~");
                }else {
                    retHolder = new RetHolder("success","添加成功~~~");
                }
            }
        }

        writer.write(objectMapper.writeValueAsString(retHolder));
    }

    /**
     *
     * 查询favorite和分页
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    private void execIndex(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String id = req.getParameter("userId");
        String currentPage = req.getParameter("currentPage");
        if (!RequestUtil.checkParameters(id,currentPage)) {
            RequestUtil.redirectToIndex(req, resp);
            return;
        }

        Integer iCurrentPage;

        try {
            iCurrentPage = Integer.parseInt(currentPage);
        }catch (NumberFormatException e) {
            e.printStackTrace();
            RequestUtil.redirectToIndex(req, resp);
            return;
        }

        List<Product> productList = favoriteService.queryFavoriteProductsFromMemcached(id);
        PageHelper.createPageHelper("favorite?action=index",productList.size(),iCurrentPage);
        req.setAttribute("favoriteProducts",productList);
        req.getRequestDispatcher("/pre/product/favoriteList.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
