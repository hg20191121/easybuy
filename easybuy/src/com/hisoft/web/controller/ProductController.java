package com.hisoft.web.controller;

import com.alibaba.druid.sql.visitor.functions.Lcase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hisoft.dao.CategoryDao;
import com.hisoft.dao.impl.CategoryDaoImpl;
import com.hisoft.entity.Category;
import com.hisoft.entity.Order;
import com.hisoft.entity.Product;
import com.hisoft.service.CategoryService;
import com.hisoft.service.ProductService;
import com.hisoft.service.impl.CategoryServiceImpl;
import com.hisoft.service.impl.ProductServiceImpl;
import com.hisoft.util.*;
import com.hisoft.util.RequestUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 与产品操作相关的servlet
 */
@WebServlet(urlPatterns = {"/Product","/admin/product"})
public class ProductController extends HttpServlet {

    private ProductService productService = new ProductServiceImpl();

    private ObjectMapper objectMapper = new ObjectMapper();

    private CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }

        //不需要权限验证
        if (req.getServletPath().equals("/Product")) {
            switch (action) {
                //点击category的图标进入商品展示列表
                case "queryProductList": {
                    execQueryProductList(req, resp);
                    break;
                }
                //查询商品详情列表
                case "queryProductDetail": {
                    execQueryProductDetail(req, resp);
                    break;
                }
                case "searchProductsByKeyWord": {
                    execSearchProductByKeyWord(req,resp);
                    break;
                }
            }
        }else {
            //需要权限验证,判断当前登录的用户是不是管理员
            if (!RequestUtil.checkOut(req)) {
                resp.sendRedirect(req.getContextPath());
            }
            switch (action) {
                //管理员点击商品管理,进入此流程
                case "index": {
                    execQueryProductListWithPrivilegeByPage(req,resp);
                    break;
                }
                //管理员点击修改商品,进入此流程
                case "toUpdateProduct": {
                    execToUpdateProductWithPrivilege(req,resp);
                    break;
                }
                //管理员点击删除商品,进入此流程
                case "deleteById": {
                    execDeleteProductByIdWithPrivilege(req,resp);
                    break;
                }
                case "toAddProduct": {
                    execToAddProductWithPrivilege(req,resp);
                    break;
                }
                case "addProduct": {
                    try {
                        execAddProductWithPrivilege(req,resp);
                    } catch (FileUploadException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "updateProduct": {
                    try {
                        execUpdateProductWithPrivilege(req, resp);
                    } catch (FileUploadException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    /**
     * 根据关键字查找,请求转发到http://localhost:8080/EasyBuy_war_exploded/pre/product/queryProductList.jsp
     * @param req
     * @param resp
     */
    private void execSearchProductByKeyWord(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String keyword = req.getParameter("keyWord");
        if (!RequestUtil.checkParameters(keyword)) {
            RequestUtil.redirectToIndex(req, resp);
            return;
        }
        List<Product> products = productService.queryProductsByKeyWord(keyword);
        req.setAttribute("total",products.size());
        req.setAttribute("productList",products);
        req.getRequestDispatcher("pre/product/queryProductList.jsp").forward(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 请求的参数:
     *      categoryId  :   category的id属性
     *      level       :   category的level属性
     *
     *
     * 根据category的id查询productList(点击主页上的超链接到此方法),此方法成功后应该
     * 请求转发到    http://localhost:8080/EasyBuy_war_exploded/pre/product/queryProductList.jsp
     *  并且在request域中添加total属性(Integer类型productList的size)和productList属性(List类型,是List<Product>)
     * @param request
     * @param response
     */
    private void execQueryProductList(HttpServletRequest request,HttpServletResponse response) {
        String categoryId = request.getParameter("categoryId");
        String level = request.getParameter("level");
        List<Product> products = productService.qurryProductsByCategoryLevel1Id(Integer.parseInt(categoryId));
        request.setAttribute("total",products.size());
        request.setAttribute("productList",products);
        try {
            request.getRequestDispatcher("pre/product/queryProductList.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 请求的参数:
     *
     *      id      :       product的id
     *
     * 根据product的id查询product并且请求转发到以下路径:
     *      http://localhost:8080/EasyBuy_war_exploded/pre/product/productDetail.jsp
     *
     *
     *  request域中数据要求如下:
     *      request.setAttribute("product",product);
     *
     *      product:    Product类型的
     *
     *
     *
     * @param request
     * @param response
     */
    private void execQueryProductDetail(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
            if (!RequestUtil.checkParameters(request,id)) {
                RequestUtil.redirectToIndex(request, response);
                return;
            }

        Integer iId;

        try {
            iId = Integer.parseInt(id);
        }catch (NumberFormatException e) {
            RequestUtil.redirectToIndex(request,response);
            return;
        }

        Product product = productService.queryOneByPrimaryKey(iId);
        request.setAttribute("product",product);
        request.getRequestDispatcher("/pre/product/productDetail.jsp").forward(request,response);
    }


    /**
     * 管理员分页查询所有商品列表的处理流程
     *
     *  请求的参数:
     *      currentPage:    当前查询的页数
     *
     *   查询成功后,请求转发至:   http://localhost:8080/EasyBuy_war_exploded/backend/product/productList.jsp
     *
     *      request域中数据要求如下:
     *
     *          request.setAttribute("productList",productList);
     *              productList:    分页查询的集合
     *          request.setAttribute("pager",pageHelper);
     *              pageHelper:     分页的辅助对象
     *
     * @param req
     * @param resp
     */
    private void execQueryProductListWithPrivilegeByPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String currentPage = req.getParameter("currentPage");
        if (!RequestUtil.checkParameters(req,currentPage)) {
            RequestUtil.redirectToIndex(req, resp);
        }
        Integer iCurrentPage;
        try {
            iCurrentPage = Integer.parseInt(currentPage);
        }catch (NumberFormatException e) {
            RequestUtil.redirectToIndex(req,resp);
            return;
        }
        List<Product> products = productService.queryProductsByPage(iCurrentPage, PageHelper.PAGE_SIZE);
        PageHelper pageHelper = PageHelper.createPageHelper("admin/product?action=index", productService.queryCount(), iCurrentPage);
        req.setAttribute("productList",products);
        req.setAttribute("pager",pageHelper);
        req.getRequestDispatcher("/backend/product/productList.jsp").forward(req,resp);
    }


    /**
     * 管理员删除某个商品的处理方法
     *
     *  request的请求参数为:
     *      id: 要删除的产品的id
     *
     *  执行完毕后,返回前台json数据格式,内容如下:
     *      new RetHolder("success","删除成功")   或   new RetHolder("error","删除失败")
     *
     * @param req
     * @param resp
     */
    private void execDeleteProductByIdWithPrivilege(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");

        PrintWriter writer = resp.getWriter();

        if (!RequestUtil.checkParameters(req,id)) {
            RequestUtil.redirectToIndex(req, resp);
        }
        Integer iId;
        RetHolder retHolder;
        try {
            iId = Integer.parseInt(id);
        }catch (NumberFormatException e) {
            RequestUtil.redirectToIndex(req,resp);
            return;
        }
        Integer remove = productService.remove(iId);
        if (remove > 0) {
            retHolder = new RetHolder("success","删除成功");
        }else {
            retHolder = new RetHolder("error","删除失败");
        }

        writer.write(objectMapper.writeValueAsString(retHolder));
    }



    /**
     * 管理员点击修改产品是经由此转发器请求转发至以下路径:
     *      http://localhost:8080/EasyBuy_war_exploded/backend/product/toAddProduct.jsp
     *
     *      (经由此转发器拿到旧的数据渲染前端页面)
     *
     *      request请求数据如下:
     *          id  :   要修改的产品id
     *
     *      返回时request域中数据要求如下:
     *          request.setAttribute("product",product);
     *              product:    查询到的product
     *          request.setAttribute("productCategoryList1",categories_level1);
     *              categories_level1:  查询到的等级为1的category集合,用于前端渲染
     *
     * @param req
     * @param resp
     */
    private void execToUpdateProductWithPrivilege(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String id = req.getParameter("id");


        if (!RequestUtil.checkParameters(req,id)) {
            RequestUtil.redirectToIndex(req, resp);
        }
        Integer iId;
        RetHolder retHolder;
        try {
            iId = Integer.parseInt(id);
        }catch (NumberFormatException e) {
            RequestUtil.redirectToIndex(req,resp);
            return;
        }

        Product product = productService.queryOneByPrimaryKey(iId);
        List<Category> level1Categories = categoryService.queryLevel_1_Categories();

        req.setAttribute("product",product);
        req.setAttribute("productCategoryList1",level1Categories);
        req.getRequestDispatcher("/backend/product/toAddProduct.jsp").forward(req,resp);
    }


    /**
     * 管理员点击商品上架是经由此转发器请求转发至以下路径:
     *      http://localhost:8080/EasyBuy_war_exploded/backend/product/toAddProduct.jsp
     *
     *      (经由此转发器拿到旧的数据渲染前端页面)
     *
     *
     *      返回时request域中数据要求如下:
     *          request.setAttribute("productCategoryList1",categories_level1);
     *              categories_level1:  查询到的等级为1的category集合,用于前端渲染
     *
     * @param req
     * @param resp
     */

    private void execToAddProductWithPrivilege(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> list = categoryService.queryLevel_1_Categories();
        req.setAttribute("productCategoryList1",list);
        req.getRequestDispatcher("/backend/product/toAddProduct.jsp").forward(req,resp);
    }


    /**
     * 管理员添加商品的处理流程,注意:是multipart/form类型,也就是上传文件的格式
     *
     *  请求数据为:
     *      categoryLevel1Id    :   商品所属种类的等级1的id
     *      categoryLevel2Id    :   商品所属种类的等级2的id
     *      categoryLevel3Id    :   商品所属种类的等级3的id
     *      name                :   商品名称
     *      photo               :   商品图片(这是上传的图片,需要上传到服务器,路径请使用PathUtil.getUploadPath()得到上传的文件夹的路径)
     *      price               :   单价
     *      stock               :   库存
     *      description         :   描述
     *
     *      更新完以后重定向至至以下路径:
     *          http://localhost:8080/EasyBuy_war_exploded/admin/product/index
     *
     *
     * @param req
     * @param resp
     */
    private void execAddProductWithPrivilege(HttpServletRequest req, HttpServletResponse resp) throws IOException, FileUploadException {
        if (!ServletFileUpload.isMultipartContent(req)) {
            RequestUtil.redirectToIndex(req, resp);
            return;
        }
        PrintWriter writer = resp.getWriter();
        RetHolder retHolder;
        Product product = makeProductFromMultipartForm(req, resp);
        if (productService.insertOne(product)>0) {
                //成功
            retHolder = new RetHolder("success","添加商品成功~~~");
            }else {
                //失败
            retHolder = new RetHolder("error","添加商品失败~~~");
            }

        writer.write(objectMapper.writeValueAsString(retHolder));
    }


    /**
     * 管理员更新商品的处理流程,注意:是multipart/form类型,也就是上传文件的格式
     *
     *  请求数据为:
     *      id                  :   商品的id(根据这个更新)
     *      categoryLevel1Id    :   商品所属种类的等级1的id
     *      categoryLevel2Id    :   商品所属种类的等级2的id
     *      categoryLevel3Id    :   商品所属种类的等级3的id
     *      name                :   商品名称
     *      photo               :   商品图片(这是上传的图片,需要上传到服务器,路径请使用PathUtil.getUploadPath()得到上传的文件夹的路径)
     *      price               :   单价
     *      stock               :   库存
     *      description         :   描述
     *
     *
     *      更新完以后重定向至至以下路径:
     *          http://localhost:8080/EasyBuy_war_exploded/admin/product/index
     *
     *
     * @param req
     * @param resp
     */
    private void execUpdateProductWithPrivilege(HttpServletRequest req, HttpServletResponse resp) throws IOException, FileUploadException {
        if (!ServletFileUpload.isMultipartContent(req)) {
            RequestUtil.redirectToIndex(req, resp);
            return;
        }
        PrintWriter writer = resp.getWriter();
        RetHolder retHolder;
        Product product = makeProductFromMultipartForm(req, resp);
        if (productService.updateOne(product,product.getP_id())>0) {
            //成功
            retHolder = new RetHolder("success","更新商品成功~~~");
        }else {
            //失败
            retHolder = new RetHolder("error","更新商品失败~~~");
        }
        writer.write(objectMapper.writeValueAsString(retHolder));
    }


    private Product makeProductFromMultipartForm(HttpServletRequest request,HttpServletResponse response) throws IOException, FileUploadException {
        String uploadRoot = FileUtil.getUploadPath(request);
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        List<FileItem> items = servletFileUpload.parseRequest(request);
        Product product = new Product();
        for (FileItem item : items) {
            //表单的name属性
            String fieldName = item.getFieldName();

            if (item.isFormField()) {
                String value = item.getString("utf-8");
                switch (fieldName) {
                    case "categoryLevel1Id": {
                        product.setP_categoryLevel1(Integer.parseInt(value));
                        break;
                    }
                    case "categoryLevel2Id": {
                        product.setP_categoryLevel2(Integer.parseInt(value));
                        break;
                    }
                    case "categoryLevel3Id": {
                        product.setP_categoryLevel3(Integer.parseInt(value));
                        break;
                    }
                    case "name": {
                        product.setP_name(value);
                        break;
                    }
                    case "price": {
                        product.setP_price(Float.parseFloat(value));
                        break;
                    }
                    case "stock": {
                        product.setP_stock(Integer.parseInt(value));
                        break;
                    }
                    case "description": {
                        product.setP_description(value);
                        break;
                    }
                    case "id": {
                        if (value.equals("")) {
                            product.setP_id(null);
                        }else {
                            product.setP_id(Integer.parseInt(value));
                        }
                        break;
                    }
                }
            } else {
                //处理文件上传
                long size = item.getSize();
                //上传的文件名
                String name = item.getName();
                System.out.println(name);
                String suffix = name.substring(name.lastIndexOf("."));
                System.out.println(suffix);
                byte[] bytes = new byte[(int) size];
                item.getInputStream().read(bytes);
                String fileName = UUID.randomUUID().toString().substring(0, 32).replace("-", "")+suffix;
                String filePath = uploadRoot + fileName;
                File file = new File(filePath);
                product.setP_fileName(fileName);
                try {
                    item.write(file);
                    Files.copy(Paths.get(filePath),Paths.get("E:/temp/"+fileName));
                } catch (Exception e) {
                }
            }
        }
        return product;
    }
}
