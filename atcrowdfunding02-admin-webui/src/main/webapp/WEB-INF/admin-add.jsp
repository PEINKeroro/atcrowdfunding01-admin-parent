<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="zh-CN">
<%@include file="/WEB-INF/include/include-head.jsp" %>
<script type="text/javascript">
    $(function () {
        $("#save").click(function () {
            $.ajax({
                type:"post",
                url:"admin/do/save.do",
                data:$("form").serialize(),
                dataType:"json",
                success:function (result) {
                    if (result.result == "FAILED") layer.alert(result.result+ "：" + result.message);
                    else layer.msg(result.data);
                }
            })
        })
    })
</script>
<body>
<%@include file="/WEB-INF/include/include-navigator.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include/include-div-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="admin/to/main/page.html">首页</a></li>
                <li><a href="admin/get/page.do">数据列表</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form role="form">
                        <div class="form-group">
                            <label for="exampleInputPassword1">登陆账号</label>
                            <input name="LoginAcct" type="text" class="form-control" id="exampleInputloginAcct1" placeholder="请输入登陆账号">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">登陆密码</label>
                            <input name="UserPswd" type="text" class="form-control" id="exampleInputPassword1" placeholder="请输入登陆密码">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">用户昵称</label>
                            <input name="userName" type="text" class="form-control" id="exampleInputUserName1" placeholder="请输入用户名称">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">邮箱地址</label>
                            <input name="email" type="email" class="form-control" id="exampleInputEmail1" placeholder="请输入邮箱地址">
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
                        <button type="button" id="save" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                        <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
