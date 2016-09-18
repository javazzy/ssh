<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- BEGIN PAGE LEVEL PLUGINS -->

<!-- END PAGE LEVEL PLUGINS -->

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
            <div class="table-actions-wrapper">
                <span> </span>
                <select class="table-group-action-input form-control input-inline input-small input-sm">
                    <option value="">Select...</option>
                    <option value="Cancel">Cancel</option>
                    <option value="Cancel">Hold</option>
                    <option value="Cancel">On Hold</option>
                    <option value="Close">Close</option>
                </select>
                <button class="btn btn-sm green table-group-action-submit">
                    <i class="fa fa-check"></i> Submit</button>
            </div>
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
<div class="modal container fade form-modal" tabindex="-1" data-backdrop="static" data-width="900">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4 class="modal-title">用户</h4>
    </div>
    <div class="modal-body form">

        <form class="form-horizontal" role="form">
            <div class="form-body">
                <div class="form-group">
                    <label class="col-md-2 control-label">用户名</label>
                    <div class="col-md-4">
                        <input name="username" type="text" class="form-control">
                    </div>

                    <label class="col-md-2 control-label">性别</label>
                    <div class="col-md-4">
                        <div class="radio-list">
                            <label class="radio-inline">
                                <input type="radio" name="dicSex.id" value="1" checked> 男 </label>
                            <label class="radio-inline">
                                <input type="radio" name="dicSex.id" value="2"> 女 </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">密码</label>
                    <div class="col-md-4">
                        <div class="input-group">
                            <input name="password" type="password" class="form-control">
                            <span class="input-group-addon"><i class="fa fa-user"></i></span>
                        </div>
                    </div>

                    <label class="col-md-2 control-label">确认密码</label>
                    <div class="col-md-4">
                        <div class="input-group">
                            <input name="re-password" type="password" class="form-control">
                            <span class="input-group-addon"><i class="fa fa-user"></i></span>
                        </div>
                    </div>

                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">生日</label>
                    <div class="col-md-4">
                        <div class="input-group date date-picker">
                            <input type="text" class="form-control"  name="birthday">
                            <span class="input-group-btn">
                                <button class="btn default" type="button">
                                    <i class="fa fa-calendar"></i>
                                </button>
                            </span>
                        </div>
                    </div>

                    <label class="col-md-2 control-label">邮箱</label>
                    <div class="col-md-4">
                        <div class="input-group">
                                                <span class="input-group-addon">
                                                    <i class="fa fa-envelope"></i>
                                                </span>
                            <input name="email" type="email" class="form-control"> </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">手机</label>
                    <div class="col-md-4">
                        <input name="phone" type="text" class="form-control">
                    </div>
                    <label class="col-md-2 control-label">住址</label>
                    <div class="col-md-4">
                        <textarea name="address" class="form-control" rows="3"></textarea>
                    </div>
                </div>

            </div>
        </form>

    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn btn-outline dark">Close</button>
        <button type="button" class="btn green">Ok</button>
    </div>
</div>
<!-- Begin:form modal -->

<!-- BEGIN PAGE LEVEL PLUGINS -->

<!-- END PAGE LEVEL PLUGINS -->

<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="sys/sysUser.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
