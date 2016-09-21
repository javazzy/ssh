<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    .btn{
        padding: 9px 12px;
    }
</style>

<!-- Begin: life time stats -->
<div class="portlet light portlet-fit portlet-datatable bordered">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-settings font-dark"></i>
            <span class="caption-subject font-dark sbold uppercase"> 用户管理 </span>
        </div>
        <div class="actions">
            <div class="btn-group btn-group-devided" data-toggle="buttons">
                <label class="btn btn-transparent grey-salsa btn-outline btn-circle btn-sm add">
                    <input type="radio" name="options" class="toggle">新增</label>
                <label class="btn btn-transparent grey-salsa btn-outline btn-circle btn-sm edit">
                    <input type="radio" name="options" class="toggle">编辑</label>
                <label class="btn btn-transparent grey-salsa btn-outline btn-circle btn-sm remove">
                    <input type="radio" name="options" class="toggle">删除</label>
            </div>
        </div>
    </div>
    <div class="portlet-body">
        <div class="table-container">
            <table class="table table-striped table-bordered table-hover table-checkable" id="datatable_ajax">
                <thead>
                    <tr role="row" class="heading">
                        <th width="30">
                            <input type="checkbox" class="group-checkable"> </th>
                        <th> 用户名 </th>
                        <th> 性别 </th>
                        <th> 生日 </th>
                        <th> 邮箱 </th>
                        <th> 手机 </th>
                        <th> 住址 </th>
                        <th> 注册时间 </th>
                        <th> 状态 </th>


                        <th width="60"> 操作 </th>
                    </tr>
                    <tr role="row" class="filter">
                        <td> </td>
                        <td>
                            <input type="text" class="form-control form-filter input-sm" name="username">
                        </td>
                        <td>

                        </td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>
                            <div class="margin-bottom-5">
                                <button class="btn btn-sm green btn-outline filter-submit margin-bottom">
                                    <i class="fa fa-search"></i> 搜索 </button>
                            </div>
                            <button class="btn btn-sm red btn-outline filter-cancel">
                                <i class="fa fa-times"></i> 清空</button>
                        </td>
                    </tr>
                </thead>
                <tbody> </tbody>
            </table>
        </div>
    </div>
</div>
<!-- End: life time stats -->


<!-- Begin:form modal -->
<!-- stackable -->
<div class="modal container fade form-modal" tabindex="-1" data-backdrop="static" data-width="600">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4 class="modal-title">用户</h4>
    </div>
    <div class="modal-body form">

        <form id="sysUserForm" class="form-horizontal" role="form">
            <div class="form-body">
                <div class="form-group">
                    <label class="col-md-3 control-label">用户名<span class="required"> * </span></label>
                    <div class="col-md-7">
                        <div class="input-icon right">
                            <i class="fa"></i>
                            <input type="text" class="form-control" required="true" minlength="2" maxlength="20" name="username">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">密码<span class="required"> * </span></label>
                    <div class="col-md-7">
                        <div class="input-icon right">
                            <i class="fa"></i>
                            <input name="password" type="password" class="form-control" required="true" minlength="6" maxlength="20">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">确认密码<span class="required"> * </span></label>
                    <div class="col-md-7">
                        <div class="input-icon right">
                            <i class="fa"></i>
                            <input name="re-password" type="password" class="form-control" required="true" equalTo="[name='password']">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">性别</label>
                    <div class="col-md-7">
                        <div class="radio-list">
                            <label class="radio-inline">
                                <input type="radio" name="dicSex.id" value="1" checked> <span aria-hidden="true" class="icon-user"></span> 男 </label>
                            <label class="radio-inline">
                                <input type="radio" name="dicSex.id" value="2"> <span aria-hidden="true" class="icon-user-female"></span> 女 </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">生日</label>
                    <div class="col-md-7">
                        <div class="input-group date date-picker">
                            <input type="text" class="form-control" name="birthday">
                            <span class="input-group-btn">
                                <button class="btn default">
                                    <i class="fa fa-calendar"></i>
                                </button>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">邮箱</label>
                    <div class="col-md-7">
                        <div class="input-icon right">
                            <i class="fa"></i>
                            <input type="text" class="form-control" name="email" email="true">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">是否禁用</label>
                    <div class="col-md-7">
                        <div class="radio-list">
                            <label class="radio-inline">
                                <input type="radio" name="enabled" value="1" checked> 启用 </label>
                            <label class="radio-inline">
                                <input type="radio" name="enabled" value="0"> 禁用 </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">手机</label>
                    <div class="col-md-7">
                        <div class="input-icon right">
                            <i class="fa"></i>
                            <input name="phone" type="text" class="form-control" phone="true">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">住址</label>
                    <div class="col-md-7">
                        <textarea name="address" class="form-control" rows="3" maxlength="200"></textarea>
                    </div>
                </div>

            </div>
        </form>

    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn btn-outline dark">取消</button>
        <button type="button" class="btn green">保存</button>
    </div>
</div>
<!-- Begin:form modal -->

<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="sys/sysUser.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
