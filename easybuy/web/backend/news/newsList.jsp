<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ include file="/common/pre/header.jsp" %>
    <link type="text/css" rel="stylesheet" href="${ctx}/statics/css/style.css" />
</head>
<body>
<%@ include file="/common/backend/searchBar.jsp" %>
<div class="i_bg bg_color">
    <!--Begin 用户中心 Begin -->
	<div class="m_content">
        <%@ include file="/common/backend/leftBar.jsp"%>
        <div class="m_right">
            <p></p>
            <div class="mem_tit">资讯列表</div>
            <table border="0" class="order_tab" style="width:930px; text-align:center; margin-bottom:30px;" cellspacing="0" cellpadding="0">
                <tbody>
                <tr>
                    <td width="20%">文章标题</td>
                    <td width="25%">创建时间</td>
                </tr>
                <c:forEach items="${newsList}" var="temp">
                    <tr>
                        <td><a href="${ctx}/news?action=newsDetail&id=${temp.n_Id}">${temp.n_Title}</a></td>
                        <td><fmt:formatDate type="date" dateStyle="long" value="${temp.n_Date}" ></fmt:formatDate></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <%@ include file="/common/pre/pagerBar.jsp" %>
        </div>
    </div>
    <%@ include file="/common/pre/footer.jsp" %>
</div>
</body>

</html>
