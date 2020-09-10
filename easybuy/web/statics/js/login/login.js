/**
 * Created by wlg on 2018/4/27.
 */
//登录的方法
function login() {
    var loginName = $("#loginName").val();
    var password = $("#password").val();

    $.ajax({
        url: contextPath + "/user",
        method: "post",
        data: {loginName: loginName, password: password, action: "login"},
        dataType: "JSON",
        success: function (data) {
            // var result=eval("("+jsonStr+")");
            // if(result.status==1){
            if(data.status=="1"){
                var time = 2;
                showMessageShow(data.message+","+time+"秒之后跳转");
                //登录成功等待两秒跳转
                //通过setTimeInterval来动态展示秒数
                var interval = setInterval(function () {
                    time-=1;
                    if(time == 0){
                        clearInterval(interval);
                        window.location.href=contextPath+"/Home?action=index";
                    }
                    showMessageShow(data.message+","+time+"秒之后跳转");
                },1000);
            }else{
                showMessage(data.message);
            }
        },
        error: function () {
            alert("error");
        }
    })
}