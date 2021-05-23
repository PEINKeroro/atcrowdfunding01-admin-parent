<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
    <%@include file="/WEB-INF/include/include-head.jsp"%>
    <link rel="stylesheet" href="css/pagination.css" />
    <script type="text/javascript" src="jquery/jquery.pagination.js"></script>
    <script type="text/javascript">
        $(function(){
            // 调用后面声明的函数对页码导航条进行初始化操作
            var initPagination = function () {
                // 获取总记录数
                var totalRecord = ${requestScope.pageInfo.total};
                $("#pagination").pagination(totalRecord, {
                    num_edge_entries: 2,                                //边缘页数
                    num_display_entries: 5,                             //主体页数
                    items_per_page: ${requestScope.pageInfo.pageSize},	// 每页要显示的数据的数量
                    // Pagination内部使用pageIndex来管理页码，pageIndex从0开始，pageNum从1开始，所以要减1
                    current_page: ${requestScope.pageInfo.pageNum - 1},
                    prev_text: "上一页",									// 上一页按钮上显示的文本
                    next_text: "下一页",									// 下一页按钮上显示的文本
                    // 用户点击“上一页、下一页、1、2、3……”这样的页码时调用这个函数实现页面跳转
                    // pageIndex是Pagination传给我们的那个“从0开始”的页码
                    callback: function (pageIndex, jQuery) {            // 指定用户点击“翻页”的按钮时跳转页面的回调函数
                        // 根据pageIndex计算得到pageNum
                        var pageNum = pageIndex + 1;
                        // 跳转页面
                        window.location.href = "admin/get/page.do?pageNum="+pageNum+"&keyword=${param.keyword}";
                        // 由于每一个页码按钮都是超链接，所以在这个函数最后取消超链接的默认行为
                        return false;
                    }
                });
            }();
        });


        // 声明方法：弹出警告删除窗
        function updateAdmin(trId, adminId) {

            var tdArray = new Array();
            var valueArray = new Array();

            var isEdited = false;

            var param = "id=" + adminId;

            // 数组的元素顺序与循环一致
            var paramArray = [null,null,"&loginAcct=", "&userName=", "&email="];
            for (var i=2; i<=4; i++){
                // 获取td对象
                var $td = $("#trOne" + trId + " > td:eq("+ i +")");
                // 获取文本框的value
                var inputValue = $("#trTwo" + trId + " > td:eq("+ i +") > input").val();


                // 筛选被修改的数据
                if ($td.text() !== inputValue){
                    // 1.如果修改了则返回true;
                    isEdited = true;

                    // 2.被修改的数据就放进数组中。
                    tdArray.push($td);
                    valueArray.push(inputValue);

                    // 3.拼接url
                    param = param + paramArray[i] + inputValue;
                }
            }

            if (isEdited) {
                // 使用提示框
                var result = window.confirm("你确定要保存对【" +
                    $("#trTwo" + trId + " > td:eq(2) > input").val() + "】的修改吗");
                // 确定修改
                if (result == true) {
                    // 1.发送修改请求
                    $.ajax({
                        type:"post",
                        url:"admin/do/update.do",
                        data:param,
                        dataType:"json",
                        success:function (result) {
                            if (result.result == "FAILED") layer.alert(result.result + "：" + result.message);
                            else {
                                layer.msg(result.data);
                                // 1.获取td中的div，修改其中的数据，保持一致
                                $.each(tdArray, function (index, $elemTd) {
                                    $elemTd.children("div").text(valueArray[index]);
                                })
                                // 2.关闭修改界面
                                cancelEdit(trId);
                            }
                        }
                    })
                }
                // 关闭修改界面
            } else cancelEdit(trId);
        }

        // 声明方法：弹出警告删除窗
        function alertDelet(trId, adminId, keyword, pageNum) {
            // 获取账号列的文本内容
            var $tdAcct = $("#trOne" + trId + " > td:eq(2)");
            // 使用提示框
            var result = window.confirm("你确定要删除【" + $tdAcct.text() + "】吗");
            if (result == true) {
                window.location.href = "admin/do/remove.do?adminId=" + adminId
                    + "&keyword=" + keyword + "&pageNum=" + pageNum;
            }
        }

        function Edit(trId) {
            $("#trOne" + trId).hide();
            $("#trTwo" + trId).show();
        }

        function cancelEdit(trId) {
            $("#trTwo" + trId).hide();
            $("#trOne" + trId).show();
            // 恢复文本框内容
            for (var i=2; i<=4; i++){
                // 1.获取文本框对象
                var $input = $("#trTwo" + trId + " > td:eq("+ i +") > input");
                // 2.获取td中的文本内容
                var tdText = $("#trOne" + trId + " > td:eq("+ i +")").text();
                // 3.将文本内容写进文本框
                $input.val(tdText)
            }
        }
    </script>
<body>
<%@include file="/WEB-INF/include/include-navigator.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include/include-div-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form action="admin/get/page.do" type="post" class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input name = "keyword" value="${param.keyword}" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <a href="admin/to/add/page.html" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</a>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
<%--                                <th>登录密码</th>--%>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test= "${empty requestScope.pageInfo.list}">
                                <tr><!-- 合并单元格六列 -->
                                    <td colspan= "6" align= "center">抱歉!没有查询到您要的数据! </td>
                                </tr>
                            </c:if>
                            <c:if test= "${!empty requestScope.pageInfo.list}">
                                <c:forEach items="${requestScope.pageInfo.list}" var= "admin" varStatus="myStatus">
                                    <tr id="trOne${myStatus.count}">
                                        <td id="text">${myStatus.count}</td>
                                        <td><input type="checkbox"></td>
                                        <td><div style="width:61px;">${admin.loginAcct}</div></td>
                                        <td><div style="width:61px;">${admin.userName}</div></td>
                                        <td><div style="width:61px;">${admin.email}</div></td>
                                        <td>
                                            <a href="assign/to/assign/role/page.do?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}" type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></a>
                                            <button onclick="Edit(${myStatus.count})" class="btn btn-primary btn-xs btn-form"><i class=" glyphicon glyphicon-pencil"></i></button>
                                            <button onclick="alertDelet(${myStatus.count}, ${admin.id}, '${param.keyword}', ${requestScope.pageInfo.pageNum})" class="btn btn-danger btn-xs">
                                                <i class=" glyphicon glyphicon-remove"></i></button>
                                        </td>
                                    </tr>

                                    <tr id="trTwo${myStatus.count}" style="display:none">
                                        <td>${myStatus.count}</td>
                                        <td><input type="checkbox"></td>
                                        <td><input type="text" value="${admin.loginAcct}" style="height:22px; width:70%"/></td>
                                        <td><input type="text" value="${admin.userName}" style="height:22px; width:70%"/></td>
                                        <td><input type="text" value="${admin.email}" style="height:22px; width:70%"/></td>
                                        <td>
                                            <button onclick="updateAdmin(${myStatus.count}, ${admin.id})" class="btn btn-success btn-xs">&nbsp&nbsp<i class="glyphicon glyphicon-saved"></i>&nbsp&nbsp</button>
                                            <button onclick="cancelEdit(${myStatus.count})" class="btn btn-danger btn-xs">&nbsp&nbsp<li class="glyphicon glyphicon-remove-sign"></li>&nbsp&nbsp</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

</body>
</html>