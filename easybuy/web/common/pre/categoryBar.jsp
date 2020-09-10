<%@ page import="com.hisoft.dao.impl.CategoryDaoImpl" %>
<%@ page import="com.hisoft.service.impl.CategoryServiceImpl" %>
<%@ page import="com.hisoft.entity.Category" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/statics/js/common/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
    var contextPath = "${ctx}";
</script>
<%
    List<Category> list = new CategoryServiceImpl().queryLevel_1_Categories();
    for (Category category : list) {
        System.out.println(category);
    }
    request.setAttribute("categoryList",new CategoryServiceImpl().queryLevel_1_Categories());
%>
<div class="nav">
    <div class="nav_t">全部商品分类</div>
    <div class="leftNav none" style="display: none;">
        <ul id="leftMenu">
            <c:forEach items="${categoryList}" var="temp">
                <li>
                    <div class="fj">
                        <span class="n_img"><span></span>
                            <img src="${ctx}/statics/images/${temp.c_iconClass}"/></span>
                        <span class="fl">${temp.c_name}</span>
                    </div>
                    <div class="zj">
                        <div class="zj_l">
                            <c:forEach items="${temp.c_children}" var="vo">
                                <div class="zj_l_c">
                                    <h2>
                                        <a href="${ctx}/Product?action=queryProductList&categoryId=${vo.c_id}&level=2">${vo.c_name}</a>
                                    </h2>
                                    <c:forEach items="${vo.c_children}" var="vo2">
                                        <a href="${ctx}/Product?action=queryProductList&categoryId=${vo2.c_id}&level=3">${vo2.c_name}</a>
                                    </c:forEach>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
<ul class="menu_r">
    <li><a href="${ctx}/Home?action=index">首页</a></li>
    <c:forEach items="${categoryList}" var="temp">
        <li><a href="${ctx}/Product?action=queryProductList&level=1&categoryId=${temp.c_id}">${temp.c_name}</a></li>
    </c:forEach>
</ul>
<div class="m_ad">中秋送好礼！</div>
<script>
    $(".nav_t").on("mouseover",function () {
        $(".nav").find(".leftNav").slideDown(500);
    })
    $(".nav").on("mouseleave",function () {
        $(".nav").find(".leftNav").slideUp(500);
    })
</script>