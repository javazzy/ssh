<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- BEGIN PAGE HEADER-->
<!-- BEGIN PAGE BAR -->
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <a href="index.html">Home</a>
            <i class="fa fa-circle"></i>
        </li>
        <li>
            <span>Page Layouts</span>
        </li>
    </ul>
</div>
<!-- END PAGE BAR -->
<!-- BEGIN PAGE TITLE-->
<h3 class="page-title"> Blank Page Layout
    <small>blank page layout</small>
</h3>
<!-- END PAGE TITLE-->
<!-- END PAGE HEADER-->
<div class="note note-info">
    <p> 我是首页 </p>
    <div class="col-md-4">
        <div class="input-group date date-picker" data-date-format="dd-mm-yyyy">
            <input type="text" class="form-control" readonly="" name="datepicker">
            <span class="input-group-btn">
                                                            <button class="btn default" type="button">
                                                                <i class="fa fa-calendar"></i>
                                                            </button>
                                                        </span>
        </div>
        <!-- /input-group -->
        <span class="help-block"> select a date </span>
    </div>
</div>

<script>
    $(function () {
        //init date pickers
        $('.date-picker').datepicker({
            language:"zh-CN",
            format:"yyyy-mm-dd",
            rtl: App.isRTL(),
            autoclose: true
        });
    });
</script>