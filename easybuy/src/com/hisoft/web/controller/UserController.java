package com.hisoft.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hisoft.entity.User;
import com.hisoft.service.UserService;
import com.hisoft.service.impl.UserServiceImpl;
import com.hisoft.util.PageHelper;
import com.hisoft.util.RequestUtil;
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

/**
 * 处理与用户相关的servlet
 */
@WebServlet(urlPatterns = {"/user", "/admin/user"})
public class UserController extends HttpServlet {

    //service层的对象
    private UserService userService = new UserServiceImpl();
    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");


        if (action == null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }

        if (req.getServletPath().equals("/user")) {
            switch (action) {
                //查询个人信息
                case "index": {
                    execIndex(req, resp);
                    break;
                }
                //用户登录
                case "login": {
                    login(req, resp);
                    break;
                }
                //用户注册
                case "register": {
                    register(req, resp);
                    break;
                }
                //用户更新密码
                case "updatePassword": {
                    updatePassword(req, resp);
                    break;
                }
                //用户登出
                case "logOut": {
                    logOut(req, resp);
                    break;
                }
                default: {
                    //默认处理流程,上面的都不匹配,那就跳转至首页
                    resp.sendRedirect(req.getContextPath());
                }
            }
        } else {
            //  /admin/user :   做权限校验

            if (!RequestUtil.checkOut(req)) {
                resp.sendRedirect(req.getContextPath());
                return;
            }

            switch (action) {
                //管理员查询所有用户的列表信息
                case "queryUserList": {
                    execQueryUserListWithPrivilegeByPage(req, resp);
                    break;
                }
                //管理员修改用户信息
                case "toUpdateUser": {
                    execToUpdateUserWithPrivilege(req, resp);
                    break;
                }
                //管理员根据id删除用户
                case "deleteUserById": {
                    execDeleteUserByIdWithPrivilege(req, resp);
                    break;
                }
                //管理员添加用户
                case "addUser": {
                    execAddUserWithPrivilege(req, resp);
                    break;
                }
                //管理员修改用户信息(不是用户自己修改自己信息)
                case "updateUser": {
                    execUpdateUserWithPrivilege(req, resp);
                    break;
                }
            }
        }

    }


    /**
     * 与doGet使用同一逻辑,可以改写
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 登录具体方法,成功后跳转至首页,并将user加入到session
     * <p>
     * session域中数据要求如下:
     * session.setAttribute("loginUser",user);
     * <p>
     * user:   查询到的user
     *
     * @param request
     * @param response
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("loginName");
        String password = request.getParameter("password");
        PrintWriter out = response.getWriter();
        RetHolder retHolder = new RetHolder("1", "登录成功");
        //服务端验证
        if (username.equals("")) {
            out.println("{\"message\":\"用户名不能为空\"}");
            return;
        }
        if (password.equals("")) {
            retHolder.setStatus("0");
            retHolder.setMessage("密码不能为空");
            out.println(objectMapper.writeValueAsString(retHolder));
        }
        User user = userService.queryUserByUserNameAndPassword(username, password);
        if (user.getU_id() == null) {
            retHolder.setStatus("0");
            retHolder.setMessage("用户名或者密码不正确");
            out.println(objectMapper.writeValueAsString(retHolder));
        } else {
            request.getSession().setAttribute(RequestUtil.USER_LOGIN_FLAG, user);
            out.println(objectMapper.writeValueAsString(retHolder));
            return;
        }

    }

    /**
     * 注册具体的方法,成功后跳转至登录页面
     *
     * @param request
     * @param response
     */
    private void register(HttpServletRequest request, HttpServletResponse response) {
        //密码需要加密才能保存
        User user = new User();
        user.setU_userName(request.getParameter("userName"));
        user.setU_loginName(request.getParameter("loginName"));
        user.setU_email(request.getParameter("email"));
        user.setU_identityCode(request.getParameter("identityCode"));
        user.setU_mobile(request.getParameter("mobile"));
        user.setU_sex(Character.valueOf(request.getParameter("sex").charAt(0)));
        user.setU_password(request.getParameter("password"));

        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //先判断登录名是否存在，已经存在不能注册
        User userByLoginName = userService.queryUserByLoginName(user.getU_loginName());
        RetHolder retHolder = new RetHolder("1", "注册成功");
        if (userByLoginName.getU_id() != null) {
            try {
                retHolder.setStatus("0");
                retHolder.setMessage("登录名已存在");
                out.println(objectMapper.writeValueAsString(retHolder));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        //如果判断可以注册就注册
        Integer integer = userService.insertOne(user);
        //判断是否添加成功
        if (integer == 1) {
            //添加成功
            try {

                out.println(objectMapper.writeValueAsString(retHolder));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return;
        } else {
            //添加失败
            try {
                retHolder.setStatus("0");
                retHolder.setMessage("注册用户失败，联系管理员");
                out.println(objectMapper.writeValueAsString(retHolder));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 用于用户忘记密码,修改密码的方法,成功后跳转至登录页面让用户重新登录
     *
     * @param request
     * @param response
     */
    private void updatePassword(HttpServletRequest request, HttpServletResponse response) {

    }


    /**
     * 用户退出登录的方法,主要就是从session中移出用户信息
     *
     * @param request
     * @param response
     */
    private void logOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!RequestUtil.checkOutIfLogin(request)) {
            RequestUtil.redirectToIndex(request, response);
            return;
        }
        request.getSession().removeAttribute(RequestUtil.USER_LOGIN_FLAG);
        response.sendRedirect(request.getContextPath()+"/pre/login.jsp");
    }


    /**
     * 用户查询个人信息的处理方法
     * <p>
     * 首先判断session域中有没有"loginUser"这个对象,如果没有返回到登录页
     * <p>
     * 如果有,请求转发到:  http://localhost:8080/EasyBuy_war_exploded/backend/user/userInfo.jsp
     *
     * @param request
     * @param response
     */
    private void execIndex(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!RequestUtil.checkOutIfLogin(request)) {
            response.sendRedirect(request.getContextPath()+"/pre/login.jsp");
            return;
        }
        request.getRequestDispatcher("/backend/user/userInfo.jsp").forward(request,response);
    }


    /**
     * 管理员查询所有用户列表的执行方法,需要首先校验身份合法,如果校验失败,重定向到index页面
     * <p>
     * 请求参数为
     * currentPage    :   当前页数
     * <p>
     * 查询成功后请求转发到:     http://localhost:8080/EasyBuy_war_exploded/backend/user/userList.jsp
     * <p>
     * request域中数据要求如下:
     * <p>
     * request.setAttribute("userList",userLIst);
     * request.setAttribute("pager",pageHelper);
     * <p>
     * userList:   查询到的所有用户
     * pageHelper: 分页得辅助对象
     *
     * @param request
     * @param response
     */
    private void execQueryUserListWithPrivilegeByPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String currentPage = request.getParameter("currentPage");
        if (!RequestUtil.checkParameters(currentPage)) {
            RequestUtil.redirectToIndex(request, response);
            return;
        }
        Integer iCurrentPage;
        try {
            iCurrentPage = Integer.parseInt(currentPage);
        }catch (NumberFormatException e) {
            RequestUtil.redirectToIndex(request, response);
            return;
        }
        List<User> userList = userService.queryUsersByPage(iCurrentPage, PageHelper.PAGE_SIZE);
        PageHelper pageHelper = PageHelper.createPageHelper("/admin/user?action=queryUserList", userService.queryCount(), iCurrentPage);
        request.setAttribute("userList",userList);
        request.setAttribute("pager",pageHelper);
        request.getRequestDispatcher("/backend/user/userList.jsp").forward(request,response);
    }


    /**
     * 执行去更新用户的界面的请求处理方法,查询到旧的信息返回,需要权限验证
     * <p>
     * 请求中参数如下:
     * id  :   需要更新的用户id
     * <p>
     * 请求转发到:      http://localhost:8080/EasyBuy_war_exploded/backend/user/toUpdateUser.jsp
     * request域中数据要求如下:
     * request.setAttribute("user",user);
     * <p>
     * user:   根据id查询到的user信息
     *
     * @param req
     * @param resp
     */
    private void execToUpdateUserWithPrivilege(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String id = req.getParameter("id");
        if (!RequestUtil.checkParameters(id)) {
            RequestUtil.redirectToIndex(req, resp);
            return;
        }
        Integer iId = Integer.parseInt(id);
        User user = userService.queryOneByPrimaryKey(iId);
        req.setAttribute("user",user);
        req.getRequestDispatcher("/user/toUpdateUser.jsp").forward(req,resp);
    }


    /**
     * 删除用户的请求处理方法
     * <p>
     * request请求参数如下:
     * id: 要删除用户的id
     * <p>
     * 删除后返回json信息,格式如下
     * new 一个RetHolder然后转换成json返回
     * <p>
     * 例如: new RetHolder("success","删除成功")
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    private void execDeleteUserByIdWithPrivilege(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (!RequestUtil.checkParameters(id)) {
            RequestUtil.redirectToIndex(req, resp);
            return;
        }
        Integer iId = Integer.parseInt(id);

        RetHolder retHolder;
        PrintWriter writer = resp.getWriter();

        Integer remove = userService.remove(iId);
        if (remove > 0) {
            retHolder = new RetHolder("success","删除成功");
        }else {
            retHolder = new RetHolder("error","删除失败");
        }

        writer.write(objectMapper.writeValueAsString(retHolder));
    }

    /**
     * 管理员添加用户的处理方法,需要权限验证
     * <p>
     * request请求的参数如下:
     * id: id,
     * loginName: loginName,
     * userName: userName,
     * identityCode: identityCode,
     * email: email,
     * mobile: mobile,
     * type: type,
     * password:password
     * <p>
     * 添加成功后,返回json数据,格式如下:
     * new RetHolder("success","添加成功")
     *
     * @param req
     * @param resp
     */

    private void execAddUserWithPrivilege(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = new User();
        String loginName = req.getParameter("loginName");
        String userName = req.getParameter("userName");
        String identityCode = req.getParameter("identityCode");
        String email = req.getParameter("email");
        String mobile = req.getParameter("mobile");
        String type = req.getParameter("type");
        String password = req.getParameter("password");

        if (!RequestUtil.checkParameters(loginName,userName,identityCode,email,mobile,type,password)) {
            RequestUtil.redirectToIndex(req, resp);
            return;
        }

        Integer iType = Integer.parseInt(type);

        PrintWriter writer = resp.getWriter();
        RetHolder retHolder;


        user.setU_mobile(mobile);
        user.setU_type(iType);
        user.setU_email(email);
        user.setU_identityCode(identityCode);
        user.setU_loginName(loginName);
        user.setU_password(password);
        user.setU_userName(userName);

        Integer integer = userService.insertOne(user);

        if (integer>0) {
            retHolder = new RetHolder("success","添加成功");
        }else {
            retHolder = new RetHolder("error","添加失败");
        }

        writer.write(objectMapper.writeValueAsString(retHolder));
    }


    /**
     * 管理员修改用户的处理方法,需要权限验证
     * <p>
     * request请求的参数如下:
     * id: id,
     * loginName: loginName,
     * userName: userName,
     * identityCode: identityCode,
     * email: email,
     * mobile: mobile,
     * type: type,
     * password:password
     * <p>
     * <p>
     * 修改成功后,返回json数据,格式如下:
     * new RetHolder("success","修改成功")
     *
     * @param req
     * @param resp
     */
    private void execUpdateUserWithPrivilege(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = new User();
        String loginName = req.getParameter("loginName");
        String userName = req.getParameter("userName");
        String identityCode = req.getParameter("identityCode");
        String email = req.getParameter("email");
        String mobile = req.getParameter("mobile");
        String type = req.getParameter("type");
        String password = req.getParameter("password");
        String id = req.getParameter("id");

        if (!RequestUtil.checkParameters(loginName,userName,identityCode,email,mobile,type,password,id)) {
            RequestUtil.redirectToIndex(req, resp);
            return;
        }

        Integer iType = Integer.parseInt(type);

        user.setU_mobile(mobile);
        user.setU_type(iType);
        user.setU_email(email);
        user.setU_identityCode(identityCode);
        user.setU_loginName(loginName);
        user.setU_password(password);
        user.setU_userName(userName);

        PrintWriter writer = resp.getWriter();
        RetHolder retHolder;

        Integer iId = Integer.parseInt(id);

        Integer updateOne = userService.updateOne(user, iId);

        if (updateOne>0) {
            retHolder = new RetHolder("success","更新成功");
        }else {
            retHolder = new RetHolder("error","更新失败");
        }

        writer.write(objectMapper.writeValueAsString(retHolder));
    }
}
