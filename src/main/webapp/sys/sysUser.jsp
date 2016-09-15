<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<link href="assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
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
                <label class="btn btn-transparent grey-salsa btn-outline btn-circle btn-sm active" onclick="alert('aaa')">
                    <input type="radio" name="options" class="toggle" id="option1">Actions</label>
                <label class="btn btn-transparent grey-salsa btn-outline btn-circle btn-sm" onclick="alert()">
                    <input type="radio" name="options" class="toggle" id="option2">Settings</label>
            </div>
            <div class="btn-group">
                <a class="btn red btn-outline btn-circle" href="javascript:;" data-toggle="dropdown">
                    <i class="fa fa-share"></i>
                    <span class="hidden-xs"> Tools </span>
                    <i class="fa fa-angle-down"></i>
                </a>
                <ul class="dropdown-menu pull-right">
                    <li>
                        <a href="javascript:;"> Export to Excel </a>
                    </li>
                    <li>
                        <a href="javascript:;"> Export to CSV </a>
                    </li>
                    <li>
                        <a href="javascript:;"> Export to XML </a>
                    </li>
                    <li class="divider"> </li>
                    <li>
                        <a href="javascript:;"> Print Invoices </a>
                    </li>
                </ul>
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


<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->

<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="sys/sysUser.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
