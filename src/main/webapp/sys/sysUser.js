var SysUser = function () {

    var grid = new Datatable();

    var initPickers = function () {
        //init date pickers
        $('.date-picker').datepicker({
            rtl: App.isRTL(),
            autoclose: true
        });
    }

    var handleRecords = function () {

        grid.init({
            src: $("#datatable_ajax"),
            onSuccess: function (grid, response) {
                // grid:        grid object
                // response:    json object of server side ajax response
                // execute some code after table records loaded
            },
            onError: function (grid) {
                // execute some code on network or other general error
            },
            onDataLoad: function(grid) {
                // execute some code on ajax data load
            },
            // loadingMessage: '载入中...',
            dataTable: { // here you can define a typical datatable settings from http://datatables.net/usage/options

                // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
                // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/scripts/datatable.js).
                // So when dropdowns used the scrollable div should be removed.
                //"dom": "<'row'<'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'<'table-group-actions pull-right'>>r>t<'row'<'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'>>",

                // bStateSave: true, // save datatable state(pagination, sort, etc) in cookie.


                // pageLength: 10, // default record count per page
                ajax: {
                    "url": "api/sysUsers/searchPage", // ajax source
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
                        }else if(!row.accountNonExpired){
                            return Icons.stop+" 账号过期";
                        }else if(!row.accountNonLocked){
                            return Icons.stop+" 账户锁定";
                        }else if(!row.credentialsNonExpired){
                            return Icons.stop+" 密码过期";
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
                    targets: [0,6]
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

    var initForm = function () {

        $(".add").click(function(){
            $(".form-modal").modal("show");
        });
        $(".edit").click(function(){
            var c = grid.getSelectedRowsCount();
            if(c == 0){
                warning("请选择一条记录进行编辑！")
                return;
            }else if(c > 1){
                warning("最多选择一条记录进行编辑！")
                return;
            }
            console.log(c);
            $('body').modalmanager('loading');

            $('#form-modal').modal();
        });

        $('#blockui_sample_3_1_0').click(function() {
            App.blockUI({
                target: '#basic',
                overlayColor: 'none',
                cenrerY: true,
                animate: true
            });

            window.setTimeout(function() {
                App.unblockUI('#basic');
            }, 2000);
        });

        $('#blockui_sample_3_1').click(function() {
            App.blockUI({
                target: '#blockui_sample_3_1_element',
                overlayColor: 'none',
                animate: true
            });
        });

        $('#blockui_sample_3_1_1').click(function() {
            App.unblockUI('#blockui_sample_3_1_element');
        });

        $('#blockui_sample_3_2').click(function() {
            App.blockUI({
                target: '#blockui_sample_3_2_element',
                boxed: true
            });
        });

        $('#blockui_sample_3_2_1').click(function() {
            App.unblockUI('#blockui_sample_3_2_element');
        });
    }

    return {

        //main function to initiate the module
        init: function () {

            initPickers();
            handleRecords();
            initForm();
        }

    };

}();

jQuery(document).ready(function() {
    SysUser.init();
});