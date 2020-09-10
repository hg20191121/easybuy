package com.hisoft.util;

import com.hisoft.entity.ShoppingCar;
import com.hisoft.entity.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RequestUtil {

    public static final String USER_LOGIN_FLAG = "loginUser";

    public static final String SHOPPING_CAR_FLAG = "shoppingCar";

    /**
     * 控制是否登录，且是管理员登录
     * @param request 需要先从request中拿到session后获得attribute(loginUser)也就是绑定在
     *                session作用域中的用户登录名
     * @return
     */
    public static boolean checkOut(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user == null || user.getU_type() != 2) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param request
     * @return  返回false就代表没有登陆
     */
    public static boolean checkOutIfLogin(HttpServletRequest request) {
        return request.getSession().getAttribute(USER_LOGIN_FLAG) != null;
    }

    /**
     * 拿到当前登录的用户信息
     * @param request
     * @return
     */
    public static User getUserFromSession(HttpServletRequest request) {
        return (User)request.getSession().getAttribute(USER_LOGIN_FLAG);
    }

    /**
     * 从session作用域中拿到ShoppingCar对象
     * @param request
     * @return
     */
    public static ShoppingCar getShoppingCarFromSession(HttpServletRequest request) {

        return (ShoppingCar)request.getSession().getAttribute(SHOPPING_CAR_FLAG);
    }

    /**
     * 初始化购物车
     * @param request
     */
    public static void initShoppingCarIfAbsent(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute(SHOPPING_CAR_FLAG) == null) {
            session.setAttribute(SHOPPING_CAR_FLAG,new ShoppingCar());
        }
    }

    /**
     * 快速转发回请求原地址
     * @param request
     * @param response
     * @throws IOException
     */
    public static void redirectToIndex(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath());
    }

    /**
     *
     * @param request
     * @param names
     * @return  返回true,校验成功,返回false,校验失败
     * @throws IllegalArgumentException
     */
    public static boolean checkParameters(HttpServletRequest request,String...names) throws IllegalArgumentException {
        Boolean flag = true;
        for (String name : names) {
            if (name == null || name.trim().equals("")) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * 检验传入的参数是否非空
     * @param names
     * @return
     * @throws IllegalArgumentException
     */
    public static boolean checkParameters(String...names) throws IllegalArgumentException {
        Boolean flag = true;
        for (String name : names) {
            if (name == null || name.trim().equals("")) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
