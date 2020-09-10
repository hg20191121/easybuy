package com.hisoft.web.controller;

import com.hisoft.entity.Order;
import com.hisoft.entity.OrderDetail;
import com.hisoft.entity.Product;
import com.hisoft.entity.User;
import com.hisoft.service.OrderService;
import com.hisoft.service.ProductService;
import com.hisoft.service.impl.OrderServiceImpl;
import com.hisoft.service.impl.ProductServiceImpl;
import com.hisoft.util.PageHelper;
import com.hisoft.util.RequestUtil;
import com.hisoft.util.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 与order操作相关的servlet
 */
@WebServlet(urlPatterns = {"/order","/admin/order"})
public class OrderController extends HttpServlet {

    private OrderService orderService = new OrderServiceImpl();

    private ProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }

        if (req.getServletPath().equals("/order")) {
            switch (action) {
                //查询我的订单请求
                case "index": {
                    execQueryMyOrders(req,resp);
                    break;
                }
                case "queryOrderDetail": {
                    execQueryOrderDetail(req, resp);
                    break;
                }
            }
        }else {
            //  /admin/order
            //做权限限定
            //如果校验不成功,返回首页
            if (!RequestUtil.checkOut(req)) {
                //返回首页
                resp.sendRedirect(req.getContextPath());
            }

            switch (action) {
                //管理员查询所有订单信息
                case "queryAllOrder": {
                    execQueryAllOrdersWithPrivilegeByPage(req, resp);
                    break;
                }
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    /**
     * 查询我的订单的请求处理方法,请求过来的参数如下:
     *
     *      userId      :       用户的id,根据这个id查询order列表,并且加入到request域中
     *
     *          请求转发到:      http://localhost:8080/EasyBuy_war_exploded/backend/order/orderList.jsp
     *
     *          request域中数据要求如下:
     *
     *              request.setAttribute("orderList",orderList);
     *
     *              orderList:  根据用户id查询到的该用户的订单列表,如果没有就返回null
     *
     *
     * @param request
     * @param response
     */
    private void execQueryMyOrders(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userId = request.getParameter("userId");
        //判断非空
        if (!RequestUtil.checkParameters(request,userId)) {
            RequestUtil.redirectToIndex(request, response);
            return;
        }

        Integer iUserId = null;
        try {
            iUserId = Integer.parseInt(userId);
        }catch (NumberFormatException e) {
            //如果有异常就返回发送请求的页面
            RequestUtil.redirectToIndex(request, response);
            return;
        }
        //这里需要通过userId找到orderId
        //单个/多个orderId
        //根据orderId找到绑定在这个orderId下的产品，

        List<Order> orderList = orderService.queryOrderListByUserId(iUserId);

        request.setAttribute("orderList",orderList);
        request.getRequestDispatcher("/backend/order/orderList.jsp").forward(request,response);
    }


    /**
     * 查询订单详情页的处理方法,请求过来的参数如下:
     *
     *      orderId     :       order的id
     *
     *      请求转发到:      http://localhost:8080/EasyBuy_war_exploded/backend/order/orderDetailList.jsp
     *
     *      request域中数据要求如下:
     *
     *          request.setAttribute("orderProductsList",orderProductsList)
     *
     *          orderProductsList   :   查询到的此订单下的product列表
     *
     *
     *
     * @param request
     * @param response
     */
    private void execQueryOrderDetail(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String orderId = request.getParameter("orderId");
        if (!RequestUtil.checkParameters(request,orderId)) {
            RequestUtil.redirectToIndex(request,response);
            return;
        }
        Integer iOrderId;
        try {
            iOrderId = Integer.parseInt(orderId);
        }catch (NumberFormatException e) {
            RequestUtil.redirectToIndex(request,response);
            return;
        }
        //根据订单id查出绑定到这个订单下的产品列表
        List<OrderDetail> orderDetails = orderService.queryOrderDetailByOrderId(iOrderId);

        request.setAttribute("orderDetailList",orderDetails);
        request.getRequestDispatcher("/backend/order/orderDetailList.jsp").forward(request,response);

    }


    /**
     * 管理员请求所有订单信息的查询方法,需要先进行身份校验
     *
     *  校验不成功就返回首页,否则继续以下流程:
     *
     *      查询所有订单信息,并请求转发到:    http://localhost:8080/EasyBuy_war_exploded/backend/order/orderList.jsp
     *
     *      request域中数据要求如下:
     *
     *          request.setAttribute("orderList",orderList);
     *
     *              orderList:  查询到的所有订单集合
     *
     *          request.setAttribute("pager",pageHelper);
     *
     *              pageHelper: 分页的辅助对象
     *
     * @param request
     * @param response
     */
    private void execQueryAllOrdersWithPrivilegeByPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String currentPage = request.getParameter("currentPage");
        if (!RequestUtil.checkParameters(request,currentPage)) {
            RequestUtil.redirectToIndex(request, response);
        }
        Integer iCurrentPage;
        try {
            iCurrentPage = Integer.parseInt(currentPage);
        }catch (NumberFormatException e) {
            RequestUtil.redirectToIndex(request,response);
            return;
        }

        List<Order> orderList = orderService.queryOrdersByPage(iCurrentPage, PageHelper.PAGE_SIZE);
        PageHelper pageHelper = PageHelper.createPageHelper("admin/order?action=queryAllOrder", orderService.queryCount(), iCurrentPage);
        request.setAttribute("orderList",orderList);
        request.setAttribute("pager",pageHelper);
        request.getRequestDispatcher("/backend/order/orderList.jsp").forward(request,response);
    }
}
