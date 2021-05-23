<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="removeModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close cancelRemoveRole" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">尚筹网系统弹窗</h4>
            </div>
            <div class="modal-body">
                <h5>请确认是否要删除下列角色: </h5>
                <span id= "roleNameSpan" style="text-align: center;display:block;"></span>
            </div>
            <div class="modal-footer">
                <button id="removeRoleBtn" type="button" class="btn btn-success">确定</button>
                <button type="button" class="btn btn-default cancelRemoveRole" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>