<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="addModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">尚筹网系统弹窗</h4>
            </div>
            <div class="modal-body">
                <div class="form-group has-success has-feedback">
                    <input type= "text" name="roleName" class="form-control" id= "saveInput" placeholder="请输入角色名称" autofocus>
                </div>
            </div>
            <div class="modal-footer">
                <button id="saveRoleBtn" type="button" class="btn btn-primary">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->