function fillAuthTree(){
    // 1.发送ajax请求查询Auth
    $.ajax({
        url:"assgin/get/all/auth.do",
        tyupe:"post",
        dataType:"json",
        success:function(response){
            var result = response.result;
            if(result == "SUCCESS") {
                // 2.获取Auth集合
                var authList = response.data;
                // 3.准备对Ztree进行的json对象
                var setting = {
                    data:{
                        simpleData:{
                            // 开启简单json
                            enable:true,
                            //使用categoryId属性关联父节点，不用默认的pId
                            pIdKey: "categoryId"
                        },
                        key: {
                            // 使用title属性显示节点名称,不用默认的name作为属性名了
                            name: "title"
                        }
                    }, check: {
                        // 显示复选框
                        enable:true
                    }

                };
                // 4.找到页面Ztree树结构标签，生成树形结构
                $.fn.zTree.init($("#authTreeDemo"), setting, authList);

                // 获取zTreeObj对象
                var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
                // 调用zTreeObj对象的方法，把节点展开
                zTreeObj.expandAll(true);


                // 5查询已分配的Auth的id 有哪些
                $.ajax({
                    url:"assign/get/assigned/auth/id/by/role/id.do",
                    type:"post",
                    data:{roleId:window.roleId},
                    dataType:"json",
                    async:false,
                    success:function(response){
                        var authIdArray = response.data;
                        // 6.完成数据回显，将已分配的权限重写勾选上
                        for(var i = 0; i < authIdArray.length; i++){
                            var authId = authIdArray[i];
                            // 一遍历json获取回显的权限结点
                            var treeNode = zTreeObj.getNodeByParam("id",authId);
                            // 二checked设置为true表示节点勾选
                            var checked = true;
                            // 三checkTypeFlag设置为false,表示不“联动”，不联动是为了避免把不该勾选的勾选上
                            var checkTypeFlag = false;
                            // 执行
                            zTreeObj.checkNode(treeNode, checked, checkTypeFlag);
                        }
                    }
                });
            }
        }
    })
}


//执行分页，生成页面效果，任何时候调用这个函数都会重新加载页面
function generatePage() {
    $.ajax({
        url: "role/get/page/Info.do",
        type: "post",
        data: {"pageNum": window.pageNum, "pageSize": window.pageSize, "keyword": window.keyword},
        dataType: "json",
        success: function(response) {
            // 1.将获取PageInfo
            var pageInfo = response.data;
            // 2.填充表格方法，传入pageInfo
            fillTableBody(pageInfo);
            // 3.生成分页导航条
            generateNavigator(pageInfo);
            // 4.复选框刷新
            $("#summaryBox").prop("checked", false);
        }
    });
}

// 填充表格
function fillTableBody(pageInfo) {
    // 1.清除tbody中的旧的内容
    $("#rolePageBody").empty();

    // 2.判断pageInfo对象是否有效
    if (pageInfo == null || pageInfo == undefined ||
        pageInfo.list == null || pageInfo.list.length == 0) {
        // 页面给出提示
        $("#rolePageBody").html("<tr><td colspan='4' align='center'>抱歉!没有查询到您搜索的数据!</td></tr>");
    } else {
        // 3.添加数据
        for (var i = 0; i < pageInfo.list.length; i++){
            var roleId = pageInfo.list[i].id;
            var roleName = pageInfo.list[i].name;
            var numberTd = "<td>"+ (i+1) +"</td>";
            var checkboxTd = "<td><input type='checkbox' class='itemBox'></td>";
            var roleNameTd = "<td>"+ roleName +"</td>";

            var checkBtn = "<button id='"+roleId+"' type='button' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>";
            var pencilBtn = "<button type='button' value='"+ i +"' class='btn btn-primary btn-xs'><i class=' glyphicon glyphicon-pencil'></i></button>";
            var removeBtn = "<button type='button' value='"+ i +"' class='btn btn-danger btn-xs singleRemove'><i class=' glyphicon glyphicon-remove'></i></button>";

            var buttonTd = "<td>"+checkBtn + " " + pencilBtn + " " + removeBtn +"</td>";
            $("#rolePageBody").append("<tr value='"+ roleId +"'>"+ numberTd + checkboxTd+ roleNameTd+ buttonTd +"</tr>")
        }
    }
}

//生成分页页码导航条
function generateNavigator(pageInfo) {
    // 获取总记录数
    var totalRecord = pageInfo.total;

    $("#pagination").pagination(totalRecord, {
        num_edge_entries: 2,                         //边缘页数
        num_display_entries: 5,                      //主体页数
        items_per_page: pageInfo.pageSize,           // 每页要显示的数据的数量
        // Pagination内部使用pageIndex来管理页码，pageIndex从0开始，pageNum从1开始，所以要减1
        current_page: pageInfo.pageNum - 1,
        prev_text: "上一页",                          // 上一页按钮上显示的文本
        next_text: "下一页",                          // 下一页按钮上显示的文本
        // 用户点击“上一页、下一页、1、2、3……”这样的页码时调用这个函数实现页面跳转
        // pageIndex是Pagination传给我们的那个“从0开始”的页码
        callback: function (pageIndex, jQuery) {     // 指定用户点击“翻页”的按钮时跳转页面的回调函数
            // 根据pageIndex计算得到pageNum
            window.pageNum = pageIndex + 1;
            // 调用分页函数
            generatePage();
            // 由于每一个页码按钮都是超链接，所以在这个函数最后取消超链接的默认行为
            return false;
        }
    })
}
