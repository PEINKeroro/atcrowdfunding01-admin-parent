<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include/include-head.jsp" %>
<script type="text/javascript">

    $(function () {
        $("#toRightBtn").click(function () {
            // 选择页面中第一个列表 中被选中的，然后追加到第二个列表中。
            $("select:eq(0)>option:selected").appendTo("select:eq(1)");
        });
        $("#toLeftBtn").click(function () {
            $("select:eq(1)>option:selected").appendTo("select:eq(0)");
        });

        $("#submitBtn").click(function () {
            // 在提交表单前把“已分配”部分的option全部选中
            $("select:eq(1)>option").prop("selected", "selected");
        });
    });

</script>
<body>
<%@include file="/WEB-INF/include/include-navigator.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include/include-div-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form action= assign/do/role/assign.do?adminId=${param.adminId} method="post" role="form" class="form-inline">
                        <div class="form-group">
                            <label for="exampleInputPassword1">未分配角色列表</label><br>
                            <select class="form-control" multiple="" size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${requestScope.unAssignedRoleList}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="toRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="toLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="exampleInputPassword1">已分配角色列表</label><br>
                            <select name="roleIdList" class="form-control" multiple="" size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${requestScope.assignedRoleList}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                            <button id="submitBtn" type="submit" class="btn btn-default"><i class="glyphicon glyphicon-edit"></i> 保存</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>