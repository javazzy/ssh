var SysUser = function () {

    var grid = new Datatable();
    var submitType = "POST";

    var initPickers = function () {
        //init date pickers
        $('.date-picker').datepicker({
            language:"zh-CN",
            format:"yyyy-mm-dd",
            rtl: App.isRTL(),
            autoclose: true
        });
    }

    var handleRecords = function () {

        grid.init({
            src: $("#datatable_sysUser"),
            dataTable: { // here you can define a typical datatable settings from http://datatables.net/usage/options
                // bStateSave: true, // save datatable state(pagination, sort, etc) in cookie.

                // pageLength: 10, // default record count per page
                ajax: {
                    type:'GET',
                    url: "api/sysUsers/searchPage" // ajax source
                },
                columns: [
                    {data:"id", mRender: function (value, display, row) {
                        return '<input type="checkbox" class="checkbox" name="id" value="' + value + '">';
                    }},
                    {data:"username"},
                    {data:"dicSex.name"},
                    {data:"birthday"},
                    {data:"email"},
                    {data:"phone"},
                    {data:"address"},
                    {data:"createTime", mRender: function (value, display, row) {
                        return value?new Date(value).format("yyyy-MM-dd HH:mm:ss"):"";
                    }},
                    {data: "enabled",mRender:function(value, display, row) {
                        if(!row.enabled){
                            return Icons.stop+" 用户禁用";
                        // }else if(!row.accountNonExpired){
                        //     return Icons.stop+" 账号过期";
                        // }else if(!row.accountNonLocked){
                        //     return Icons.stop+" 账户锁定";
                        // }else if(!row.credentialsNonExpired){
                        //     return Icons.stop+" 密码过期";
                        }else{
                            return Icons.on+" 正常";
                        }
                    }},
                    {mRender: function (value, display, row) {
                        return '';
                    }}
                ],
                order: [
                    // [1, "asc"]
                ],columnDefs: [{ // define columns sorting options(by default all columns are sortable extept the first checkbox column)
                    orderable: false,
                    targets: [0,9]
                }],
            }
        });

        // handle group actionsubmit button click
        grid.getTableWrapper().on('click', '.table-group-action-submit', function (e) {
            e.preventDefault();
            var action = $(".table-group-action-input", grid.getTableWrapper());
            if (action.val() != "" && grid.getSelectedRowsCount() > 0) {
                grid.setAjaxParam("customActionType", "group_action");
                grid.setAjaxParam("customActionName", action.val());
                grid.setAjaxParam("id", grid.getSelectedRows());
                grid.getDataTable().ajax.reload();
                grid.clearAjaxParams();
            } else if (action.val() == "") {
                App.alert({
                    type: 'danger',
                    icon: 'warning',
                    message: 'Please select an action',
                    container: grid.getTableWrapper(),
                    place: 'prepend'
                });
            } else if (grid.getSelectedRowsCount() === 0) {
                App.alert({
                    type: 'danger',
                    icon: 'warning',
                    message: 'No record selected',
                    container: grid.getTableWrapper(),
                    place: 'prepend'
                });
            }
        });

        // grid.setAjaxParam("customActionType", "group_action");
        grid.getDataTable().ajax.reload();
        grid.clearAjaxParams();
    }

    var handleControl = function () {

        $(".add").click(function(){
            submitType = "POST";
            $(".form-modal").modal("show");
        });
        $(".edit").click(function(){
            submitType = "PUT";
            var c = grid.getSelectedRowsCount();
            if(c == 0){
                warning("请选择一条记录进行编辑！")
                return;
            }else if(c > 1){
                warning("最多选择一条记录进行编辑！")
                return;
            }

            $('.form-modal').modal("show");
            $.get("api/sysUsers/"+grid.getSelectedRows()[0],function(data){
                $('#form_sysUser').loalData(data);
                $("[name='re-password']").val(data.password);
            });
        });
        $(".remove").click(function(){
            var c = grid.getSelectedRowsCount();
            if(c == 0){
                warning("请选择要删除的记录！")
                return;
            }
            
        });

        $('.btn-cancen').click(function() {
            App.unblockUI('.form-modal');
        });

    }

    // validation using icons
    var handleValidation = function() {
        // for more info visit the official plugin documentation:
        // http://docs.jquery.com/Plugins/Validation

        var form = $('#form_sysUser');
        var errorAlert = $('.alert-danger', form);
        var successAlert = $('.alert-success', form);

        var validator = form.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input
            invalidHandler: function (event, validator) { //display error alert on form submit
                successAlert.hide();
                errorAlert.show();
            },

            errorPlacement: function (error, element) { // render error placement for each input type
                var icon = $(element).parent('.input-icon').children('i');
                icon.removeClass('fa-check').addClass("fa-warning");
                icon.attr("data-original-title", error.text()).tooltip({'container': "form"});
            },

            highlight: function (element) { // hightlight error inputs
                $(element).closest('.form-group').removeClass("has-success").addClass('has-error'); // set error class to the control group
            },

            success: function (label, element) {
                var icon = $(element).parent('.input-icon').children('i');
                $(element).closest('.form-group').removeClass('has-error').addClass('has-success'); // set success class to the control group
                icon.removeClass("fa-warning").addClass("fa-check");
            },

            submitHandler: function (form) {
                App.blockUI({
                    target: '.form-modal',
                    overlayColor: 'none',
                    cenrerY: true,
                    animate: true
                });

                // submit the form
                $(form).ajaxSubmit({
                    url:"/api/sysUsers",
                    type:submitType,
                    success:function(){
                        success("保存成功！");
                        App.unblockUI('.form-modal');
                        validator.resetForm();
                        $(form).find(".has-success").removeClass("has-success")
                        $(form).find("i.fa-check").remove();
                        $(".form-modal").modal("hide");
                        refreshGrid();
                    },
                    error:function (e) {
                        error(e);
                    }
                });
            }
        });

    }

    var refreshGrid = function(){
        grid.getDataTable().ajax.reload();
    }

    return {

        //main function to initiate the module
        init: function () {
            initPickers();
            handleRecords();
            handleControl();
            handleValidation();
        }

    };

}();

jQuery(document).ready(function() {
    SysUser.init();
});