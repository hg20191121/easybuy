<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
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
            <div class="mem_tit">${news.n_Title}</div>
            <p style="padding:0 40px; margin:0 auto 20px auto;">
                ${news.n_Content}
            </p>
        </div>
    </div>
	<!--End 用户中心 End--> 
    <!--Begin Footer Begin -->
    <%@ include file="/common/pre/footer.jsp" %>
    <!--End Footer End -->    
</div>
</body>
</html>
