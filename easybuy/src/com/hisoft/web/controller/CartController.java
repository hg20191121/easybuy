package com.hisoft.web.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hisoft.entity.*;
import com.hisoft.service.AddressService;
import com.hisoft.service.OrderDetailService;
import com.hisoft.service.OrderService;
import com.hisoft.service.ProductService;
import com.hisoft.service.impl.AddressServiceImpl;
import com.hisoft.service.impl.OrderDetailServiceImpl;
import com.hisoft.service.impl.OrderServiceImpl;
import com.hisoft.service.impl.ProductServiceImpl;
import com.hisoft.util.RequestUtil;
import com.hisoft.util.RequestUtil;
import com.hisoft.util.RetHolder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@WebServlet("/Cart")
public class CartController extends HttpServlet {

    private AddressService addressService = new AddressServiceImpl();

    private OrderService orderService = new OrderServiceImpl();

    private ProductService productService = new ProductServiceImpl();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();

        //检验是否登录
        if (!RequestUtil.checkOutIfLogin(req)) {
            writer.write(objectMapper.writeValueAsString(new RetHolder("error", "您还未登录,请登录~~~")));
            return;
        }

        //初始化购物车
        RequestUtil.initShoppingCarIfAbsent(req);

        String action = req.getParameter("action");

        if (action == null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }


        switch (action) {
            case "add": {
                //添加到购物车
                execAddProductToShoppingCar(req, resp);
                break;
            }
            case "refreshCart": {
                //刷新购物车,其实就是请求转发到searchBar.jsp里面(并携带数据)
                execRefreshCart(req, resp);
                break;
            }
            //删除购物车中的一种商品
            case "removeOneItemInCart": {
                execRemoveItemFromShoppingCar(req, resp);
                break;
            }
            //去结算
            case "toSettlement": {
                //直接请求转发
                resp.sendRedirect(req.getContextPath() + "/pre/settlement/toSettlement.jsp");
                break;
            }
            //第一阶段的请求,请求转发settlement1.jsp

            case "settlement1": {
                resp.sendRedirect(req.getContextPath() + "/pre/settlement/settlement1.jsp");
                break;
            }
            //第二阶段的请求,请求转发settlement2.jsp
            case "settlement2": {
                execToSettlement2(req, resp);
                break;
            }
            //第三阶段的请求,请求转发settlement3.jsp
            case "settlement3": {
                execToSettlement3(req, resp);
                break;
            }
            case "modCart": {
                execModifyCart(req,resp);
                break;
            }
        }

    }

    private void execModifyCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("entityId");
        String quantity = req.getParameter("quantity");
        Integer iId = Integer.parseInt(id);
        Integer iQuantity = Integer.parseInt(quantity);

        ShoppingCar shoppingCarFromSession = RequestUtil.getShoppingCarFromSession(req);
        final RetHolder retHolder = new RetHolder("error","修改失败");
        PrintWriter writer = resp.getWriter();
        if (shoppingCarFromSession == null) {
            retHolder.setMessage("您还尚未往购物车添加商品");
            retHolder.setStatus("error");
        }else {
            List<ProductComposite> items = shoppingCarFromSession.getItems();
            items.forEach(productComposite -> {
                if (productComposite.getProduct().getP_id().equals(iId)) {
                    productComposite.setQuantity(iQuantity);
                    productComposite.setCost(productComposite.getProduct().getP_price()*productComposite.getQuantity());
                    retHolder.setStatus("success");
                    retHolder.setMessage("成功修改");
                }
            });
        }
        shoppingCarFromSession.updateSum();
        req.getSession().setAttribute(RequestUtil.SHOPPING_CAR_FLAG,shoppingCarFromSession);
        writer.write(objectMapper.writeValueAsString(retHolder));
    }

    /**
     * remove
     * 返回json数据
     *
     * @param request
     * @param response
     */
    private void execRemoveItemFromShoppingCar(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();

        Integer iEntityId;

        RetHolder retHolder = new RetHolder("error", "删除失败");

        ShoppingCar shoppingCarFromSession = RequestUtil.getShoppingCarFromSession(request);
        //判断当前购物车是否有商品
        int size = shoppingCarFromSession.getItems().size();
        if (size == 0) {
            writer.write(objectMapper.writeValueAsString(retHolder));
        }

        String entityId = request.getParameter("entityId");
        //检验非空
        if (!RequestUtil.checkParameters(request, entityId)) {
            RequestUtil.redirectToIndex(request, response);
        }

        try {
            iEntityId = Integer.parseInt(entityId);
        } catch (NumberFormatException e) {
            RequestUtil.redirectToIndex(request, response);
            return;
        }


//        shoppingCarFromSession.getItems().forEach(productComposite -> {
//            if (productComposite.getProduct().getP_id().equals(iEntityId)) {
//                shoppingCarFromSession.getItems().remove(productComposite);
//
//                retHolder.setMessage("删除成功~~");
//                retHolder.setStatus("success");
//            }
//        });

        for (int i = 0; i < size; i++) {
            List<ProductComposite> items = shoppingCarFromSession.getItems();
            ProductComposite productComposite = items.get(i);
            if (productComposite.getProduct().getP_id().equals(iEntityId)) {
                items.remove(shoppingCarFromSession.getItems().get(i));
                //如果删除成功，重新计算总价格
                shoppingCarFromSession.setSum(shoppingCarFromSession.getSum() == null ? 0 : shoppingCarFromSession.getSum() - productComposite.getCost());
                retHolder.setMessage("删除成功~~");
                retHolder.setStatus("success");
                writer.write(objectMapper.writeValueAsString(retHolder));
                return;
            }
        }
        writer.write(objectMapper.writeValueAsString(retHolder));
    }

    /**
     * 第三阶段的处理流程,用户可能没有地址列表,需要添加新地址,而且此阶段已经付费了,所以需要清除session中的购物车信息
     *
     * @param req
     * @param resp
     */
    private void execToSettlement3(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String addressId = req.getParameter("addressId");
        String newAddress = req.getParameter("newAddress");
        String newRemark = req.getParameter("newRemark");
        User loginUser = RequestUtil.getUserFromSession(req);
        ShoppingCar shoppingCar = RequestUtil.getShoppingCarFromSession(req);

        if (addressId == null || addressId.trim().equals("")) {
            resp.sendRedirect(req.getContextPath());
            return;
        }

        Address address;

        if (addressId.equals("-1")) {
            //新添加地址
            address = new Address();
            address.setAddress(newAddress);
            address.setRemark(newRemark);
            address.setUserId(loginUser.getU_id());
            addressService.insertOne(address);
        } else {
            //使用原有地址
            address = addressService.queryOneByPrimaryKey(Integer.parseInt(addressId));
        }


        //创建订单信息,并且请求转发到settlement3.jsp
        Order order = new Order();
        User user = (User) req.getSession().getAttribute(RequestUtil.USER_LOGIN_FLAG);

        order.setO_userId(user.getU_id());
        order.setO_cost(shoppingCar.getSum());
        order.setO_createTime(new Date());
        order.setO_loginName(loginUser.getU_loginName());
        order.setO_userAddress(address.getAddress());
        order.setO_status(1);
        order.setO_type(0);
        order.setO_serialNumber(UUID.randomUUID().toString().substring(0, 32).replace("-", ""));

        Integer orderId = orderService.insertOne(order);

        if (orderId > 0) {
            //订单生成成功
            order.setO_id(orderId);
            req.setAttribute("currentOrder", order);
            OrderDetailService orderDetailService = new OrderDetailServiceImpl();
            //把订单中的产品绑定到 order_product_detail中
            for (ProductComposite item : shoppingCar.getItems()) {
                OrderDetail orderDetail = new OrderDetail();
                //查新加入订单的id
                orderDetail.setOrdetId(orderId);
                orderDetail.setCost(item.getCost());
                orderDetail.setQuantity(item.getQuantity());
                orderDetail.setProductId(item.getProduct().getP_id());
               if(orderDetailService.insertOne(orderDetail)<=0){
                   //订单商品绑定失败
                   req.getRequestDispatcher("pre/settlement/toSettlement.jsp").forward(req, resp);
               }
            }


            //删除购物车信息
            req.getSession().removeAttribute("shoppingCar");

            req.getRequestDispatcher("pre/settlement/settlement3.jsp").forward(req, resp);
        } else {
            //订单生成失败
            req.getRequestDispatcher("pre/settlement/toSettlement.jsp").forward(req, resp);
        }

    }

    /**
     * 需要查询地址信息
     *
     * @param req
     * @param resp
     */
    private void execToSettlement2(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //检验是否登录
        if (!RequestUtil.checkOutIfLogin(req)) {
            resp.sendRedirect(req.getContextPath());
            return;
        }

        List<Address> addresses = addressService.queryAddressByUserId(((User) (req.getSession().getAttribute("loginUser"))).getU_id());
        if (addresses == null) {
            addresses = new ArrayList<>();
        }
        req.setAttribute("userAddressList", addresses);
        req.getRequestDispatcher("/pre/settlement/settlement2.jsp").forward(req, resp);
    }

    /**
     * 直接请求转发到
     * /common/backend/searchBar.jsp
     *
     * @param req
     * @param resp
     */
    private void execRefreshCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            req.getRequestDispatcher("/common/backend/searchBar.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath());
        }

    }

    /**
     * 添加商品到购物车的处理流程,注意:只是添加到session中
     * request请求的参数为:
     * <p>
     * entityId: 产品的id
     * quantity: 添加几个
     * <p>
     * 执行完毕后,返回json数据,格式如下:
     * new RetHolder("success","添加购物车成功");  |   new RetHolder("error","添加购物车失败");
     *
     * @param req
     * @param resp
     */
    private void execAddProductToShoppingCar(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();

        RetHolder retHolder;

        RequestUtil.initShoppingCarIfAbsent(req);
        String entityId = req.getParameter("entityId");
        String quantity = req.getParameter("quantity");
        if (entityId == null || quantity == null || entityId.trim().equals("") || quantity.trim().equals("")) {
            RequestUtil.redirectToIndex(req, resp);
        }
        Integer iEntityId = null;
        Integer iQuantity = null;
        try {
            iEntityId = Integer.parseInt(entityId);
            iQuantity = Integer.parseInt(quantity);
        } catch (Exception e) {
            RequestUtil.redirectToIndex(req, resp);
            return;
        }

        ShoppingCar shoppingCar = RequestUtil.getShoppingCarFromSession(req);
        if (shoppingCar == null) {
            retHolder = new RetHolder("error", "添加购物车失败");
        } else {
            ProductComposite composite = new ProductComposite();
            Product product = productService.queryOneByPrimaryKey(iEntityId);
            composite.setQuantity(iQuantity);
            composite.setCost(product.getP_price() * iQuantity);
            composite.setProduct(product);

            shoppingCar.getItems().add(composite);
            shoppingCar.setSum(shoppingCar.getSum() == null ? 0 + composite.getCost() : shoppingCar.getSum() + composite.getCost());
            req.getSession().setAttribute(RequestUtil.SHOPPING_CAR_FLAG, shoppingCar);
            retHolder = new RetHolder("success", "添加购物车成功");
        }

        writer.write(objectMapper.writeValueAsString(retHolder));

    }

    private void refreshShoppingCarSum(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
