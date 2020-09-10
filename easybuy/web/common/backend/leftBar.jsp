<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="m_left">
    <div class="left_n">管理中心</div>
    <div class="left_m">
        <div class="left_m_t t_bg1">订单中心</div>
        <ul>
            <li><a href="${ctx}/order?action=index&userId=${sessionScope.loginUser.u_id}" <c:if test="${menu==1}"> class="now" </c:if>>我的订单</a></li>
            <li><a href="${ctx}/Favorite?action=index&userId=${sessionScope.loginUser.u_id}&currentPage=1" <c:if test="${menu==1}"> class="now" </c:if>>我的收藏</a></li>
        	<c:if test="${sessionScope.loginUser.u_type==2}">
        		<li><a href="${ctx}/admin/order?action=queryAllOrder&currentPage=1" <c:if test="${menu==9}"> class="now" </c:if>>全部订单</a></li>
        	</c:if>
        </ul>
    </div>
    <div class="left_m">
        <div class="left_m_t t_bg2">会员中心</div>
        <ul>
            <li><a href="${ctx}/user?action=index"  <c:if test="${menu==2}"> class="now" </c:if>>用户信息</a></li>
            <c:if test="${sessionScope.loginUser.u_type==2}">
            	<li><a href="${ctx}/admin/user?action=queryUserList&currentPage=1"  <c:if test="${menu==8}"> class="now" </c:if>>用户列表</a></li>
            </c:if>
        </ul>
    </div>
    <c:if test="${sessionScope.loginUser.u_type==2}">
    <div class="left_m">
        <div class="left_m_t t_bg2">商品管理</div>
        <ul>
            <li><a href="${ctx}/admin/category?action=index&currentPage=1" <c:if test="${menu==4}"> class="now" </c:if>>分类管理</a></li>
            <li><a href="${ctx}/admin/product?action=index&currentPage=1"  <c:if test="${menu==5}"> class="now" </c:if>>商品管理</a></li>
            <li><a href="${ctx}/admin/product?action=toAddProduct" <c:if test="${menu==6}"> class="now" </c:if>>商品上架</a></li>
        </ul>
    </div>
    </c:if>
    <div class="left_m">
        <div class="left_m_t t_bg2">资讯中心</div>
        <ul>
            <li><a href="${ctx}/news?action=queryNewsList&currentPage=1"  <c:if test="${menu==7}"> class="now" </c:if>>资讯列表</a></li>
        </ul>
    </div>
</div>