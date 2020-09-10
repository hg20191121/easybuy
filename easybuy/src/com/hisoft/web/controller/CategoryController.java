package com.hisoft.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hisoft.entity.Category;
import com.hisoft.service.CategoryService;
import com.hisoft.service.impl.CategoryServiceImpl;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 跟category操作相关的servlet
 */
@WebServlet("/admin/category")
public class CategoryController extends HttpServlet {

    private CategoryService categoryService = new CategoryServiceImpl();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
            if (!RequestUtil.checkParameters(req,action)) {
                RequestUtil.redirectToIndex(req,resp);
                return;
            }
        if (!RequestUtil.checkOut(req)) {
            //不是管理员
            RequestUtil.redirectToIndex(req,resp);
            return;
        }
        switch (action) {
            //去productCategoryList.jsp
            case "index": {
                execIndexWithPrivilegeByPage(req,resp);
                break;
            }
            case "toAddProductCategory": {
                //查productCategoryList1和productCategoryList2
                List<Category> productCategoryList1 = categoryService.queryLevel_1_Categories();
                List<Category> productCategoryList2 = new ArrayList<>();
                productCategoryList1.stream()
                        .forEach(category -> {
                            productCategoryList2.addAll(category.getC_children());
                        });
                req.setAttribute("productCategoryList1",productCategoryList1);
                req.setAttribute("productCategoryList2",productCategoryList2);
                req.getRequestDispatcher("/backend/productCategory/toAddProductCategory.jsp").forward(req,resp);
                break;
            }
            case "addCategory": {
                execAddCategory(req,resp);
                break;
            }
            //根据父id查询子集合
            case "queryCategoryChild": {
                execQueryCategoryChild(req,resp);
                break;
            }
            case "deleteCategoryById": {
                execDeleteCategoryById(req,resp);
                break;
            }
        }
    }

    private void execDeleteCategoryById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        Integer iId = Integer.parseInt(id);
        PrintWriter writer = resp.getWriter();
        RetHolder retHolder;
        if (categoryService.remove(iId)>0) {
            retHolder = new RetHolder("success","删除成功");
        }else {
            retHolder = new RetHolder("error","删除失败");
        }
        writer.write(objectMapper.writeValueAsString(retHolder));
    }

    private void execQueryCategoryChild(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String parentId = req.getParameter("parentId");
        if (!RequestUtil.checkParameters(parentId)) {
            RequestUtil.redirectToIndex(req, resp);
            return;
        }
        int parseInt = Integer.parseInt(parentId);
        List<Category> children = categoryService.queryCategoriesByParentId(parseInt);
        PrintWriter writer = resp.getWriter();
        writer.write(objectMapper.writeValueAsString(children));
    }

    private void execAddCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String type = req.getParameter("type");
        String productCategoryLevel1 = req.getParameter("productCategoryLevel1");
        String productCategoryLevel2 = req.getParameter("productCategoryLevel2");
        if (!RequestUtil.checkParameters(name,type)) {
            RequestUtil.redirectToIndex(req, resp);
            return;
        }

        PrintWriter writer = resp.getWriter();
        RetHolder retHolder;

        Category category = new Category();
        if (productCategoryLevel1 != null) {
            category.setC_parentId(Integer.parseInt(productCategoryLevel1));
        }
        if (productCategoryLevel2!=null) {
            category.setC_parentId(Integer.parseInt(productCategoryLevel2));
        }

        category.setC_name(name);
        category.setC_type(Integer.parseInt(type));
        if (categoryService.insertOne(category)>0) {
            retHolder = new RetHolder("success","添加分类成功");
        }else {
            retHolder = new RetHolder("error","添加分类失败");
        }

        writer.write(objectMapper.writeValueAsString(retHolder));
    }

    /**
     *  请求参数:
     *      currentPage :   当前页
     *
     *      request域中设置:
     *
     *          req.setAttribute("categoryList",categories);
     *
     * @param req
     * @param resp
     */
    private void execIndexWithPrivilegeByPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String currentPage = req.getParameter("currentPage");
            if (!RequestUtil.checkParameters(req,currentPage)) {
                //参数有问题
                RequestUtil.redirectToIndex(req,resp);
                return;
            }
        Integer iCurrentPage;

        try {
            iCurrentPage =   Integer.parseInt(currentPage);
        }catch (NumberFormatException e) {
            //参数有问题
            RequestUtil.redirectToIndex(req,resp);
            return;
        }

        List<Category> categories = categoryService.queryCategoryByPage(iCurrentPage, PageHelper.PAGE_SIZE);

        PageHelper pageHelper = PageHelper.createPageHelper("admin/category?action=index", categoryService.queryCount(), iCurrentPage);

        req.setAttribute("categoryList",categories);
        req.setAttribute("pager",pageHelper);
        req.getRequestDispatcher("/backend/productCategory/productCategoryList.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
