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
<div class="modal fade form-modal" tabindex="-1" data-backdrop="static">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4 class="modal-title">Stack One</h4>
    </div>
    <div class="modal-body form">
        <form class="form-horizontal" role="form">
            <div class="form-body">
                <%--<div class="form-group">--%>
                    <%--<label class="col-md-3 control-label">Block Help</label>--%>
                    <%--<div class="col-md-9">--%>
                        <%--<input type="text" class="form-control" placeholder="Enter text">--%>
                        <%--<span class="help-block"> A block of help text. </span>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="col-md-3 control-label">Inline Help</label>--%>
                    <%--<div class="col-md-9">--%>
                        <%--<input type="text" class="form-control input-inline input-medium" placeholder="Enter text">--%>
                        <%--<span class="help-inline"> Inline help. </span>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="col-md-3 control-label">Input Group</label>--%>
                    <%--<div class="col-md-9">--%>
                        <%--<div class="input-inline input-medium">--%>
                            <%--<div class="input-group">--%>
                                                            <%--<span class="input-group-addon">--%>
                                                                <%--<i class="fa fa-user"></i>--%>
                                                            <%--</span>--%>
                                <%--<input type="email" class="form-control" placeholder="Email Address"> </div>--%>
                        <%--</div>--%>
                        <%--<span class="help-inline"> Inline help. </span>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="col-md-3 control-label">Email Address</label>--%>
                    <%--<div class="col-md-9">--%>
                        <%--<div class="input-group">--%>
                                                        <%--<span class="input-group-addon">--%>
                                                            <%--<i class="fa fa-envelope"></i>--%>
                                                        <%--</span>--%>
                            <%--<input type="email" class="form-control" placeholder="Email Address"> </div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="col-md-3 control-label">Password</label>--%>
                    <%--<div class="col-md-9">--%>
                        <%--<div class="input-group">--%>
                            <%--<input type="password" class="form-control" placeholder="Password">--%>
                            <%--<span class="input-group-addon">--%>
                                                            <%--<i class="fa fa-user"></i>--%>
                                                        <%--</span>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="col-md-3 control-label">Left Icon</label>--%>
                    <%--<div class="col-md-9">--%>
                        <%--<div class="input-icon">--%>
                            <%--<i class="fa fa-bell-o"></i>--%>
                            <%--<input type="text" class="form-control" placeholder="Left icon"> </div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="col-md-3 control-label">Right Icon</label>--%>
                    <%--<div class="col-md-9">--%>
                        <%--<div class="input-icon right">--%>
                            <%--<i class="fa fa-microphone"></i>--%>
                            <%--<input type="text" class="form-control" placeholder="Right icon"> </div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="col-md-3 control-label">Icon Input in Group Input</label>--%>
                    <%--<div class="col-md-9">--%>
                        <%--<div class="input-group">--%>
                            <%--<div class="input-icon">--%>
                                <%--<i class="fa fa-lock fa-fw"></i>--%>
                                <%--<input id="newpassword" class="form-control" type="text" name="password" placeholder="password"> </div>--%>
                            <%--<span class="input-group-btn">--%>
                                                            <%--<button id="genpassword" class="btn btn-success" type="button">--%>
                                                                <%--<i class="fa fa-arrow-left fa-fw"></i> Random</button>--%>
                                                        <%--</span>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="col-md-3 control-label">Input With Spinner</label>--%>
                    <%--<div class="col-md-9">--%>
                        <%--<input type="password" class="form-control spinner" placeholder="Password"> </div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="col-md-3 control-label">Static Control</label>--%>
                    <%--<div class="col-md-9">--%>
                        <%--<p class="form-control-static"> email@example.com </p>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="col-md-3 control-label">Disabled</label>--%>
                    <%--<div class="col-md-9">--%>
                        <%--<input type="password" class="form-control" placeholder="Disabled" disabled=""> </div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="col-md-3 control-label">Readonly</label>--%>
                    <%--<div class="col-md-9">--%>
                        <%--<input type="password" class="form-control" placeholder="Readonly" readonly=""> </div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="col-md-3 control-label">Dropdown</label>--%>
                    <%--<div class="col-md-9">--%>
                        <%--<select class="form-control">--%>
                            <%--<option>Option 1</option>--%>
                            <%--<option>Option 2</option>--%>
                            <%--<option>Option 3</option>--%>
                            <%--<option>Option 4</option>--%>
                            <%--<option>Option 5</option>--%>
                        <%--</select>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="col-md-3 control-label">Multiple Select</label>--%>
                    <%--<div class="col-md-9">--%>
                        <%--<select multiple="" class="form-control">--%>
                            <%--<option>Option 1</option>--%>
                            <%--<option>Option 2</option>--%>
                            <%--<option>Option 3</option>--%>
                            <%--<option>Option 4</option>--%>
                            <%--<option>Option 5</option>--%>
                        <%--</select>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="col-md-3 control-label">Textarea</label>--%>
                    <%--<div class="col-md-9">--%>
                        <%--<textarea class="form-control" rows="3"></textarea>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <div class="form-group">
                    <label for="exampleInputFile" class="col-md-3 control-label">File input</label>
                    <div class="col-md-9">
                        <input type="file" id="exampleInputFile">
                        <p class="help-block"> some help text here. </p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">Checkboxes</label>
                    <div class="col-md-9">
                        <div class="checkbox-list">
                            <label>
                                <div class="checker"><span><input type="checkbox"></span></div> Checkbox 1 </label>
                            <label>
                                <div class="checker"><span><input type="checkbox"></span></div> Checkbox 1 </label>
                            <label>
                                <div class="checker disabled"><span><input type="checkbox" disabled=""></span></div> Disabled </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">Inline Checkboxes</label>
                    <div class="col-md-9">
                        <div class="checkbox-list">
                            <label class="checkbox-inline">
                                <div class="checker" id="uniform-inlineCheckbox21"><span><input type="checkbox" id="inlineCheckbox21" value="option1"></span></div> Checkbox 1 </label>
                            <label class="checkbox-inline">
                                <div class="checker" id="uniform-inlineCheckbox22"><span><input type="checkbox" id="inlineCheckbox22" value="option2"></span></div> Checkbox 2 </label>
                            <label class="checkbox-inline">
                                <div class="checker disabled" id="uniform-inlineCheckbox23"><span><input type="checkbox" id="inlineCheckbox23" value="option3" disabled=""></span></div> Disabled </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">Radio</label>
                    <div class="col-md-9">
                        <div class="radio-list">
                            <label>
                                <div class="radio" id="uniform-optionsRadios22"><span><input type="radio" name="optionsRadios" id="optionsRadios22" value="option1" checked=""></span></div> Option 1 </label>
                            <label>
                                <div class="radio" id="uniform-optionsRadios23"><span><input type="radio" name="optionsRadios" id="optionsRadios23" value="option2" checked=""></span></div> Option 2 </label>
                            <label>
                                <div class="radio disabled" id="uniform-optionsRadios24"><span><input type="radio" name="optionsRadios" id="optionsRadios24" value="option2" disabled=""></span></div> Disabled </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">Inline Radio</label>
                    <div class="col-md-9">
                        <div class="radio-list">
                            <label class="radio-inline">
                                <div class="radio" id="uniform-optionsRadios25"><span><input type="radio" name="optionsRadios" id="optionsRadios25" value="option1" checked=""></span></div> Option 1 </label>
                            <label class="radio-inline">
                                <div class="radio" id="uniform-optionsRadios26"><span class="checked"><input type="radio" name="optionsRadios" id="optionsRadios26" value="option2" checked=""></span></div> Option 2 </label>
                            <label class="radio-inline">
                                <div class="radio disabled" id="uniform-optionsRadios27"><span><input type="radio" name="optionsRadios" id="optionsRadios27" value="option3" disabled=""></span></div> Disabled </label>
                        </div>
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
