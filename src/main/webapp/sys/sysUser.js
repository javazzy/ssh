var SysUser = function () {

    var grid = new Datatable();
    var form = $('#form_sysUser');
    var validate = null;
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

        var tableId = "#datatable_sysUser";

        grid.init({
            src: $(tableId),
            dataTable: { // here you can define a typical datatable settings from http://datatables.net/usage/options
                // bStateSave: true, // save datatable state(pagination, sort, etc) in cookie.

                // pageLength: 10, // default record count per page
                ajax: {
                    type:'GET',
                    url: "api/sysUsers/searchPage" // ajax source
                },
                buttons: [
                    { className: 'btn green-jungle btn-outline add', text: '<i class="fa fa-plus"></i> 新 增'},
                    { className: 'btn blue btn-outline edit', text: '<i class="fa fa-edit"></i> 编 辑'},
                    { className: 'btn red-sunglo btn-outline remove', text: '<i class="fa fa-trash-o"></i> 删 除'},
                    { extend: 'excel', className: 'btn yellow btn-outline', text: '<i class="fa fa-file-excel-o"></i> 导出Excel'},
                    { extend: 'colvis', className: 'btn dark btn-outline', text: '<i class="fa fa-columns"></i> 定制列'}
                ],
                columns: [
                    {
                        data:"id",
                        title:'<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"><input type="checkbox" class="group-checkable" data-set="'+tableId+' .checkboxes" /><span></span></label>',
                        orderable:false,
                        render: function (value, display, row) {
                            return '<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"><input name="id" type="checkbox" class="checkboxes" value="' + value + '"/><span></span></label>';
                        }
                    },
                    {data:"username",title:"用户名"},
                    {data:"dicSex.name",title: "性别"},
                    {data:"birthday",title: "生日"},
                    {data:"email",title: "邮箱"},
                    {data:"phone",title: "手机"},
                    {data:"address",title: "住址"},
                    {data:"createTime",title: "注册时间",render: function (value, display, row) {
                        return value?new Date(value).format("yyyy-MM-dd HH:mm:ss"):"";
                    }},
                    {data: "enabled",title: "状态",render:function(value, display, row) {
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
                    {
                        className: 'control',
                        orderable: false
                    }
                ],
                responsive: {
                    details: {
                        type: 'column',
                        // display: $.fn.dataTable.Responsive.display.modal( {
                        //     header: function ( row ) {
                        //         var data = row.data();
                        //         return '详细信息：'+data["username"];
                        //     }
                        // } ),
                        // // renderer: $.fn.dataTable.Responsive.renderer.tableAll()
                        // renderer: function ( api, rowIdx, columns ) {
                        //
                        //     var data = $.map( columns, function ( col, i ) {
                        //         if($(col.title).find("input.group-checkable").length){
                        //             return "";
                        //         }
                        //
                        //         return /*col.hidden ? */'<tr data-dt-row="'+col.rowIndex+'" data-dt-column="'+col.columnIndex+'">'+
                        //             '<td>'+col.title+':'+'</td> '+
                        //             '<td>'+col.data+'</td>'+
                        //             '</tr>'/*:''*/;
                        //     } ).join('');
                        //
                        //     return data ? '<table class="table dtr-details" width="100%"><tbody>'+data+'</tbody></table>' : false;
                        // }
                    }
                },
                order: [
                    // [1, "asc"]
                ],
                columnDefs: [{
                    defaultContent: '',
                    orderable: true,
                    searchable: false,
                    targets: ['_all']
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

            confirm("确定要删除该记录吗？",function(){
                $.ajax({
                    url:"api/sysUsers/"+grid.getSelectedRows().join(","),
                    type:"DELETE",
                    success:function(data){
                        success("删除成功！");
                        refreshGrid();
                    }
                });
            });
        });

        $('.btn-cancen').click(function() {
            App.unblockUI('.form-modal');
        });

    }

    // validation using icons
    var handleSubmit = function() {
        // for more info visit the official plugin documentation:
        // http://docs.jquery.com/Plugins/Validation


        var errorAlert = $('.alert-danger', form);
        var successAlert = $('.alert-success', form);

        validate = form.validate({
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

    var clearValidate = function(){
        validate.resetForm();
        form.find(".has-success").removeClass("has-success")
        form.find("i.fa-check").remove();
        form.find(".has-error").removeClass("has-error")
        form.find("i.fa-warning").remove();
    }

    var refreshGrid = function(){
        grid.getDataTable().ajax.reload();
    }

    var initFormModal = function(){
        $('.form-modal').on('hidden.bs.modal', function () {
            clearValidate();
        })
    }

    return {

        //main function to initiate the module
        init: function () {
            initPickers();
            handleRecords();
            handleControl();
            handleSubmit();
            initFormModal();
        }

    };

}();

jQuery(document).ready(function() {
    SysUser.init();
});