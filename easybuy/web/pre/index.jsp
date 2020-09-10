<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ include file="/common/pre/header.jsp" %>
    <title>易买网</title>
</head>
<body>
<!--Begin Header Begin-->
<%@ include file="/common/pre/searchBar.jsp" %>
<!--End Header End-->
<!--Begin Menu Begin-->
<div class="menu_bg">
    <div class="menu">
        <!--Begin 商品分类详情 Begin-->
        <div class="nav">
            <div class="nav_t">全部商品分类</div>
            <div class="leftNav">
                <ul>
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
                                                <a href="${ctx}/Product?action=queryProductList&categoryId=${vo2.c_id}&level=3">${vo2.c_name}</a> |
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
            <!--页面控制如果商品类别没有加载上，就跳转到主页重新加载-->
            <c:if test="${categoryList==null}">
                <jsp:forward page="index.jsp"/>
            </c:if>
            <%--category的一级分类的name的列表--%>
            <li><a href="${ctx}/Home?action=index">首页</a></li>
            <c:forEach items="${categoryList}" var="temp">
                <li><a href="${ctx}/Product?action=queryProductList&level=1&categoryId=${temp.c_id}">${temp.c_name}</a>
                </li>
            </c:forEach>
        </ul>
        <div class="m_ad">中秋送好礼！</div>
        <!--End 商品分类详情 End-->
    </div>
</div>
<!--End Menu End-->
<div class="i_bg bg_color">
    <div class="i_ban_bg">
        <!--Begin Banner Begin-->
        <div class="banner">
            <div class="top_slide_wrap">
                <ul class="slide_box bxslider">
                    <li><img src="${ctx}/statics/images/ban1.jpg" width="740" height="401"/></li>
                    <li><img src="${ctx}/statics/images/ban1.jpg" width="740" height="401"/></li>
                    <li><img src="${ctx}/statics/images/ban1.jpg" width="740" height="401"/></li>
                </ul>
                <div class="op_btns clearfix">
                    <a href="#" class="op_btn op_prev"><span></span></a>
                    <a href="#" class="op_btn op_next"><span></span></a>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            var jq2 = jQuery.noConflict();
            (function () {
                $(".bxslider").bxSlider({
                    auto: true,
                    prevSelector: jq2(".top_slide_wrap .op_prev")[0], nextSelector: jq2(".top_slide_wrap .op_next")[0]
                });
            })();
        </script>
        <!--End Banner End-->
        <div class="inews">
            <div class="news_t">
                <span class="fr"><a href="${ctx}/news?action=queryNewsList&currentPage=1">更多 ></a></span>新闻资讯
            </div>
            <ul>
                <c:forEach items="${newsList}" var="temp">
                    <li><span>[ 公告 ]</span>
                        <a href="${ctx}/news?action=newsDetail&id=${temp.n_Id}">${temp.n_Title}</a>
                    </li>
                </c:forEach>
            </ul>
            <div class="charge_t">
                话费充值
                <div class="ch_t_icon"></div>
            </div>
            <form>
                <table border="0" style="width:205px; margin-top:10px;" cellspacing="0" cellpadding="0">
                    <tr height="35">
                        <td width="33">号码</td>
                        <td><input type="text" value="" class="c_ipt"/></td>
                    </tr>
                    <tr height="35">
                        <td>面值</td>
                        <td>
                            <select class="jj" name="city">
                                <option value="0" selected="selected">100元</option>
                                <option value="1">50元</option>
                                <option value="2">30元</option>
                                <option value="3">20元</option>
                                <option value="4">10元</option>
                            </select>
                            <span style="color:#ff4e00; font-size:14px;">￥99.5</span>
                        </td>
                    </tr>
                    <tr height="35">
                        <td colspan="2"><input type="submit" value="立即充值" class="c_btn"/></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div class="content mar_20">
        <img src="${ctx}/statics/images/mban_1.jpg" width="1200" height="110"/>
    </div>
    <!--Begin 进口 生鲜 Begin-->
    <c:forEach items="${categoryList}" var="temp" end="${fn:length(categoryList)}" varStatus="status">
        <div class="i_t mar_10">
            <span class="floor_num">${status.index+1}F</span>
            <span class="fl">${temp.c_name}</span>
        </div>
        <div class="content">
            <div class="fresh_left">
                <div class="fre_ban">
                    <div id="imgPlay1">
                        <ul class="imgs" id="actor1">
                            <li><a href="#"><img src="${ctx}/statics/images/fre_r.jpg" width="211" height="286"/></a>
                            </li>
                            <li><a href="#"><img src="${ctx}/statics/images/fre_r.jpg" width="211" height="286"/></a>
                            </li>
                            <li><a href="#"><img src="${ctx}/statics/images/fre_r.jpg" width="211" height="286"/></a>
                            </li>
                        </ul>
                        <div class="prevf">上一张</div>
                        <div class="nextf">下一张</div>
                    </div>
                </div>
                <div class="fresh_txt">
                    <div class="fresh_txt_c">
                        <c:forEach items="${temp.c_children}" var="vo">
                            <a href="${ctx}/Product?action=queryProductList&categoryId=${vo.c_id}&level=2">${vo.c_name}</a>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="fresh_mid">
                <ul>
                    <c:forEach items="${temp.c_products}" var="productVo">
                        <li>
                            <div class="name"><a href="#">${productVo.p_name}</a></div>
                            <div class="price">
                                <font>￥<span>${productVo.p_price}</span></font> &nbsp;
                            </div>
                            <div class="img">
                                <a href="${ctx}/Product?action=queryProductDetail&id=${productVo.p_id}">
                                    <img src="${ctx}/files/${productVo.p_fileName}" width="185" height="155"/>
                                </a>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="fresh_right">
                <ul>
                    <li><a href="#"><img src="${ctx}/statics/images/fre_b1.jpg" width="260" height="220"/></a></li>
                    <li><a href="#"><img src="${ctx}/statics/images/fre_b2.jpg" width="260" height="220"/></a></li>
                </ul>
            </div>
        </div>
    </c:forEach>
    <%@ include file="/common/pre/footer.jsp" %>
</div>
</body>
<script !src="">

    $(document).ready(function () {
        //页面加载时，在application作用域上绑定商品类别


    })
</script>
</html>
