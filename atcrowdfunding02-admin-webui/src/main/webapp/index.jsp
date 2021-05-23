<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" +
            request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}
    ${pageContext.request.contextPath}/"/>
    <title>Title</title>
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript">
        $(function(){
            $("#btn1").click(function () {
                var array = [5,8,12];
                var requestBody = JSON.stringify(array);
                console.log(array.length);
                console.log(requestBody);
                $.ajax({
                    url:"send/array.do",        //请求目标资源的地址
                    type:"post",                  //请求方式
                    data:requestBody,             //要发送的请求参数
                    contentType:"application/json;charset=UTF-8",
                    dataType:"text",              //如何对待服务器端返回的数据
                    success:function (response) { //服务器端成功处理请求后调用的回调函数
                        alert(response);
                    },
                    error:function (response) {   //服务器端处理请求失败后调用的回调函数
                        alert(response);
                    }
                })
            })


            $(function () {
                $.ajax({
                    url:"two/province.do",
                    type:"post",
                    dataType:"json",
                    success:function (response) {
                        var html = "";
                        $.each(response, function (index, elem) {
                            html = html+"<option class='pro' value='"+ elem.id +"'>"+ elem.name+ "</option>";
                        })
                        $("#province").html(html);
                    }
                })


                $("body").on("click", ".pro", function(){
                    $.ajax({
                        url:"two/city.do",
                        type:"post",
                        data:"pid="+ this.value,
                        dataType:"json",
                        success:function (response) {
                            var html = "<option disabled>--请选择城市--</option>";
                            $.each(response, function (index, elem) {
                                html = html+"<option>"+ elem.name +"</option>";
                            });
                            $("#city").html(html);
                        }
                    });
                })

            });




        })
    </script>
</head>
<body>
    <a href="test/ssm.html">测试SSM整合环境</a>

    <button id="btn1">Send [5,8,12] One</button>

    <a href="admin/to/login/page.html">管理员登录页面</a>
    <a href="admin/to/main/page.html">管理员主页面</a>

    <select id="province"></select>

    <select id="city">
        <option>--请选择城市--</option>
    </select>



</body>
</html>
