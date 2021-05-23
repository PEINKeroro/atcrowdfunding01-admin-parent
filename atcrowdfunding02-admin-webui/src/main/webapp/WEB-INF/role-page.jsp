<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include/include-head.jsp"%>
<link rel="stylesheet" href="css/pagination.css" />
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="crowd/role-page.js"></script>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">
    $(function(){
    // 1.设置全局属性
        window.trId = null;
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";
        window.roleId = null;
        generatePage();

        // 关键字查询
        $(".btn-warning").click(function () {
            window.keyword = $(".has-success")[0].value;
            generatePage();
        })

        // 点击新增按钮打开模态框
        $("#showAddModalBtn").click(function(){
            $("#addModal").modal("show");
        });

        // 模态框保存
        $("#saveRoleBtn").click(function () {
            var roleName = $("#saveInput").val().trim();
            $.ajax({
                url: "role/do/save.do",
                type: "post",
                data: "name="+ roleName,
                dataType: "json",
                success:function (result) {
                    if (result.result == "FAILED") layer.alert(result.result + "：" + result.message);
                    else {
                        // 1.成功
                        layer.msg(result.data);
                        // 2.页面数据更新
                        generatePage();
                        // 3.清空模态框
                        $("#saveRoleInput").val("");
                        // 4.隐藏模态窗
                        $("#addModal").modal("hide");
                    }
                }
            })
        })

        //给动态代码绑定
        // 弹出修改模态框
        $("#rolePageBody").on("click" ,".btn-primary" , function(){
            // 1.获取trId
            var trId = $(this).attr("value");
            // 2.获取id
            window.roleId = $("#rolePageBody > tr:eq("+ trId +")").attr("value");
            // 3.获取表格中的roleNAme
            var roleName = $("#rolePageBody > tr:eq("+ trId +") > td:eq(2)").text();
            // 4.显示模态框
            $("#editModal").modal("show");
            // 5.模态框显示roleName
            $("#editInput").val(roleName);
        });

        // 模态框确认修改按钮
        $("#updateRoleBtn").click(function () {
            var roleName = $("#editInput").val().trim();
            $.ajax({
                url: "role/do/update.do",
                type: "post",
                data: "id="+ window.roleId +"&name="+ roleName,
                dataType: "json",
                success:function (result) {
                    if (result.result == "FAILED") layer.alert(result.result + "：" + result.message);
                    else {
                        // 1.成功
                        layer.msg(result.data);
                        // 2.隐藏模态窗
                        $("#editModal").modal("hide");
                        // 3.页面数据更新
                        generatePage();
                    }
                }
            });
        });

        // 删除
        $(".panel-body").on("click", ".btn-danger", function(){

            // 如果有trid 就将此行复选框选中
            window.trId = this.value;
            if (trId !== null && trId !== "") {
                $("#rolePageBody > tr:eq(" + trId + ") > td:eq(1)").children("input").prop("checked", true);
            }

            // 1.声明一个数组，存放role
            window.roleArray = new Array();
            // 2.遍历选中的多选框
            $(".itemBox:checked").each(function (index, elemBox) {
                // 3.获取id与name
                var roleId = $(elemBox).parent().parent().attr("value");
                var roleName = $(elemBox).parent().next().text()// 文本框的 td标签 的后一个td
                // 4.以json格式存入数组
                roleArray.push(roleId);
                // 5.模态框内显示角色名称
                if (index > 0) $("#roleNameSpan").append("，");
                $("#roleNameSpan").append(roleName);
            });

            // 检查roleArray的长度是否为0
            if (roleArray.length == 0) {
                layer.msg("请至少选择一个执行删除");
            } else {
                // 1.显示模态框
                $("#removeModal").modal("show");
            }
        });

        // 模态框确认删除按钮
        $("#removeRoleBtn").click(function () {
            $("#removeModal").modal("hide");

            var jsonArray = JSON.stringify(window.roleArray);

            $.ajax({
                url: "role/do/remove.do",
                type: "post",
                data: jsonArray,
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success: function (result) {
                    if (result.result == "FAILED") layer.alert(result.result + "：" + result.message);
                    else {
                        // 1.成功
                        layer.msg(result.data);
                        // 2.页面数据更新
                        generatePage();
                    }
                }
            });
            // 清空数据
            $("#roleNameSpan").empty();
            window.roleArray = null;
            window.trId = null;
        });

        // 复选框全选
        $("#summaryBox").click(function(){
            // 获取当前多选框自身的状态
            var currentStatus = this.checked;
            // 用当前多选框的状态设置其他多选框 attr 只能执行一次
            $(".itemBox").prop("checked", currentStatus);
        });

        // 全选、全不选的反向操作
        $("#rolePageBody").on("click", ".itemBox", function() {
            //获取当前已经选中的。itemBox的数量
            var checkedBoxCount = $(".itemBox:checked").length;
            //获取全部itemBox的数量
            var totalBoxCount = $(".itemBox").length;
            //使用二者的比较结果设置总的checkbox
            $("#summaryBox").prop("checked", checkedBoxCount == totalBoxCount);
        });

        // 取消后 清空数据
        $(".cancelRemoveRole").click(function () {
            // 如果有trid 就将此行复选框 取消
            if (window.trId !== null && trId !== "") {
                $("#rolePageBody > tr:eq(" + trId + ") > td:eq(1)").children("input").prop("checked", false);
                $("#summaryBox").prop("checked", false);
                trId = null;
            }

            $("#roleNameSpan").empty();
            window.roleArray = null;
        });

        // 13. 给分配权限按钮绑定单击响应函数
        $("#rolePageBody").on("click", ".checkBtn", function(){
            // 打开模态框
            $("#assignModal").modal("show");
            // 在模态框中装载树Auth的形结构数据
            window.roleId = this.id;
            fillAuthTree();
        });

// 14.给分配权限模态框中的“分配”按钮绑定单击响应函数
        $("#assignBtn").click(function(){
            // 1.收集树形结构中各个被勾选的节点
            // [1]声明一个专门的数组存放id
            var authIdArray = [];
            // [2]获取zTreeObj对象
            var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
            // [3]获取全部被勾选的节点
            var checkedNodes = zTreeObj.getCheckedNodes();
            // [4]遍历checkedNodes
            for(var i = 0; i < checkedNodes.length; i++) {
                // 获取一个权限结点
                var checkedNode = checkedNodes[i];
                // 获取权限的id放入数组中
                var authId = checkedNode.id;
                authIdArray.push(authId);
            }

            var requestBody = {
                "authIdArray":authIdArray,
                //为了服务器端handler方法能够统一使用List<Integer>方式接收数据, roleId也存入数组
                "roleId":[window.roleId]
            };
            // 将json对象 转为字符串
            requestBody = JSON.stringify(requestBody);

            // 2.发送请求
            $.ajax({
                url:"assign/do/role/assign/auth.do",
                type:"post",
                data:requestBody,
                contentType:"application/json;charset=UTF-8",
                dataType:"json",
                success : function(response){
                    var result = response . result;
                    if(result == "SUCCESS") {
                        layer.msg("操作成功!");
                    } else layer.alert("操作失败");
                },
                error:function(response) {
                    layer.msg(response.status+"!"+response.statusText);
                }
            });
        });

    });





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
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="text" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button id="showAddModalBtn" type="button" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox" id="summaryBox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">
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
<%@include file="/WEB-INF/include/modal/role-add.jsp"%>
<%@include file="/WEB-INF/include/modal/role-edit.jsp"%>
<%@include file="/WEB-INF/include/modal/role-remove.jsp"%>
<%@include file="/WEB-INF/include/modal/modal-role-assign-auth.jsp"%>
</body>
</html>>