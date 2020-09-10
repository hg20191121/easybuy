<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
  var contextPath = "${ctx}";
</script>
<div class="content mar_20">
    <img src="${ctx}/statics/images/img1.jpg"/>
</div>
<!--Begin 第一步：查看购物车 Begin -->
<div class="content mar_20" >
    <table border="0" class="car_tab" style="width:1200px; margin-bottom:50px;" cellspacing="0" cellpadding="0">
        <tr>
            <td class="car_th" width="200">商品名称</td>
            <td class="car_th" width="150">单价</td>
            <td class="car_th" width="150">购买数量</td>
            <td class="car_th" width="130">小计</td>
            <td class="car_th" width="150">操作</td>
        </tr>
        <c:forEach items="${sessionScope.shoppingCar.items}" var="temp">
            <tr>
                <td>
                    <div class="c_s_img">
                        <a href="${ctx}/Product?action=queryProductDetail&id=${temp.product.p_id}"><img src="${ctx}/files/${temp.product.p_fileName}" width="73" height="73"/></a>
                    </div>
                        ${temp.product.p_name}
                </td>
                <td align="center" style="color:#ff4e00;">￥${temp.product.p_price}</td>
                <td align="center">
                    <div class="c_num">
                        <input type="button" value="" onclick="subQuantity(this,'${temp.product.p_id}');" class="car_btn_1"/>
                        <input type="text" value="${temp.quantity}" name="" class="car_ipt"/>
                        <input type="button" value="" onclick="addQuantity(this,'${temp.product.p_id}',${temp.product.p_stock});" class="car_btn_2"/>
                    </div>
                </td>
                <td align="center" style="color:#ff4e00;">￥${temp.cost}</td>
                <td align="center"><a href="javascript:void(0);" onclick="removeCart('${temp.product.p_id}');" >删除</a>&nbsp; &nbsp;</td>
            </tr>
        </c:forEach>
        <tr height="70">
            <td colspan="6" style="font-family:'Microsoft YaHei'; border-bottom:0;">
                <c:if test="${sessionScope.shoppingCar==null || sessionScope.shoppingCar.sum==null}">
                    <span class="fr">商品总价：<b style="font-size:22px; color:#ff4e00;">￥0</b></span>
                </c:if>
                <c:if test="${sessionScope.shoppingCar!=null && sessionScope.shoppingCar.sum!=null}">
                    <span class="fr">商品总价：<b style="font-size:22px; color:#ff4e00;">￥${sessionScope.shoppingCar.sum}</b></span>
                </c:if>
            </td>
        </tr>
        <tr valign="top" height="150">
            <td colspan="6" align="right">
                <a href="${ctx}/Home?action=index"><img src="${ctx}/statics/images/buy1.gif" /></a>&nbsp;&nbsp;
                <c:if test="${sessionScope.shoppingCar!=null && sessionScope.shoppingCar.sum>0}">
                    <a href="javascript:void(0);" onclick="settlement2();"><img src="${ctx}/statics/images/buy2.gif" /></a>
                </c:if>
            </td>
        </tr>
    </table>
</div>



