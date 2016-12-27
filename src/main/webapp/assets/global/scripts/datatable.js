/***
Wrapper/Helper Class for datagrid based on jQuery Datatable Plugin
***/
var Datatable = function() {

    var tableOptions; // main options
    var dataTable; // datatable object
    var table; // actual table jquery object
    var tableContainer; // actual table container object
    var tableWrapper; // actual table wrapper jquery object
    var tableInitialized = false;
    var ajaxParams = {}; // set filter mode
    var the;

    var countSelectedRecords = function() {
        var selected = $('tbody > tr > td:nth-child(1) input[type="checkbox"]:checked', table).size();
        var text = tableOptions.dataTable.language.metronicGroupActions;
        if (selected > 0) {
            $('.table-group-actions', tableWrapper).text(text.replace("_TOTAL_", selected));
        } else {
            $('.table-group-actions', tableWrapper).text("");
        }
    };

    var checkboxChange = function() {
        var flag = true;
        $('tbody > tr > td:nth-child(1) input[type="checkbox"]',table).each(function(i,chk){
            if(this.checked){
                $(this).parents('tr').addClass("active");
            } else {
                flag = false;
                $(this).parents('tr').removeClass("active");
            }
        });
        $(this).parents('table').find('.group-checkable').prop("checked",flag);
    };


    return {

        //main function to initiate the module
        init: function(options) {

            if (!$().dataTable) {
                return;
            }

            the = this;

            // default settings
            options = $.extend(true, {
                src: "", // 渲染元素（jQuery对象）
                filterApplyAction: "filter",
                filterCancelAction: "filter_cancel",
                resetGroupActionInputOnSuccess: true,
                loadingMessage: '载入中...',
                dataTable: {
                    //语法结构
                    //l - 每页数量选择select
                    //f – 搜索框search
                    //t – 表单内容table
                    //i – 当前条数，总共条数information
                    //p – 翻页按钮pagination
                    //r – 请求中的提示信息
                    //< 和 > – 一个div的开始与结束
                    //<"class"> – class为div的class名称
                    dom: "<'row' <'col-md-12'B>><'table-responsive't><'row'<'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'<'table-group-actions pull-right'>>r>", // datatable layout
                    // dom: "<'table-responsive't><'row'<'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'<'table-group-actions pull-right'>>r>", // datatable layout
                    lengthMenu: [5,10,20,50,100], //更改显示记录数选项
                    language: { // 国际化配置
                        // metronic spesific
                        metronicGroupActions: "_TOTAL_ 条数据被选中",
                        metronicAjaxRequestGeneralError: "无法完成请求。请检查你的网络连接！",

                        // data tables spesific
                        lengthMenu: "<span class='seperator'>|</span>每页 _MENU_ 条",
                        info: "<span class='seperator'>|</span>共 _TOTAL_ 条",
                        infoEmpty: "没有数据",
                        infoFiltered: '(过滤总条数 _MAX_ 条)',
                        emptyTable: "表中无数据存在！",
                        zeroRecords: "对不起，查询不到相关数据！",
                        processing: "载入中...",// 处理页面数据的时候的显示
                        paginate: {
                            previous: "上一页",
                            next: "下一页",
                            last: "末页",
                            first: "首页",
                            page: "转到页",
                            pageOf: "/"
                        },
                        aria: {
                            sortAscending: ": 单击正排序",
                            sortDescending: ": 单击倒排序"
                        }
                    },

                    serverSide : true,// 分页，取数据等等的都放到服务端去

                    ordering:true,// 排序操作在服务端进行，所以可以关了

                    columnDefs: [{
                        defaultContent: '',
                        orderable: true,
                        searchable: false,
                        targets: ['_all']
                    }],

                    processing : true,// 载入数据的时候是否显示“载入中”
                    pageLength : 5,// 默认每页显示条数
                    pagingType: "bootstrap_extended", // pagination type(bootstrap, bootstrap_full_number or bootstrap_extended)
                    autoWidth: true, // disable fixed width and enable fluid table

                    ajax: { // define ajax settings
                        url: "", // ajax URL
                        type: "GET", // request type
                        timeout: 20000,
                        data: function(data) { // add request parameters before submit
                            $.each(ajaxParams, function(key, value) {
                                data[key] = value;
                            });

                            var orderby = [];
                            for(var i in data.order){
                                var order = data.order[i];
                                var orderColumnIndex = order.column;
                                var orderColumnName = data.columns[orderColumnIndex].data;
                                var orderColumnDir = order.dir;
                                orderby.push(orderColumnName+' '+orderColumnDir);
                            }
                            delete data.columns;
                            data.order = orderby.join(",");

                            // App.blockUI({
                            //     message: tableOptions.loadingMessage,
                            //     target: tableContainer,
                            //     overlayColor: 'none',
                            //     cenrerY: true,
                            //     boxed: true
                            // });
                        },
                        dataSrc: function(res) { // Manipulate the data returned from the server
                            if (res.customActionMessage) {
                                App.alert({
                                    type: (res.customActionStatus == 'OK' ? 'success' : 'danger'),
                                    icon: (res.customActionStatus == 'OK' ? 'check' : 'warning'),
                                    message: res.customActionMessage,
                                    container: tableWrapper,
                                    place: 'prepend'
                                });
                            }

                            if (res.customActionStatus) {
                                if (tableOptions.resetGroupActionInputOnSuccess) {
                                    $('.table-group-action-input', tableWrapper).val("");
                                }
                            }

                            if ($('.group-checkable', table).size() === 1) {
                                $('.group-checkable', table).attr("checked", false);
                            }

                            if (tableOptions.onSuccess) {
                                tableOptions.onSuccess.call(undefined, the, res);
                            }

                            App.unblockUI(tableContainer);

                            return res.data;
                        },
                        error: function() { // handle general connection errors
                            if (tableOptions.onError) {
                                tableOptions.onError.call(undefined, the);
                            }

                            App.alert({
                                type: 'danger',
                                icon: 'warning',
                                message: tableOptions.dataTable.language.metronicAjaxRequestGeneralError,
                                container: tableWrapper,
                                place: 'prepend'
                            });

                            App.unblockUI(tableContainer);
                        }
                    },

                    drawCallback: function(oSettings) { // run some code on table redraw
                        if (tableInitialized === false) { // check if table has been initialized
                            tableInitialized = true; // set table initialized
                            table.show(); // display table
                        }
                        countSelectedRecords(); // reset selected records indicator

                        // callback for ajax data load
                        if (tableOptions.onDataLoad) {
                            tableOptions.onDataLoad.call(undefined, the);
                        }

                    }
                }
            }, options);

            tableOptions = options;

            // create table's jquery object
            table = $(options.src);
            tableContainer = table.parents(".table-container");

            // apply the special class that used to restyle the default datatable
            var tmp = $.fn.dataTableExt.oStdClasses;

            $.fn.dataTableExt.oStdClasses.sWrapper = $.fn.dataTableExt.oStdClasses.sWrapper + " dataTables_extended_wrapper";
            $.fn.dataTableExt.oStdClasses.sFilterInput = "form-control input-xs input-sm input-inline";
            $.fn.dataTableExt.oStdClasses.sLengthSelect = "form-control input-xs input-sm input-inline";

            // 初始化表格
            dataTable = table.DataTable(options.dataTable);

            // revert back to default
            $.fn.dataTableExt.oStdClasses.sWrapper = tmp.sWrapper;
            $.fn.dataTableExt.oStdClasses.sFilterInput = tmp.sFilterInput;
            $.fn.dataTableExt.oStdClasses.sLengthSelect = tmp.sLengthSelect;

            // 获取表格所在域
            tableWrapper = table.parents('.dataTables_wrapper');

            // build table group actions panel
            if ($('.table-actions-wrapper', tableContainer).size() === 1) {
                $('.table-group-actions', tableWrapper).html($('.table-actions-wrapper', tableContainer).html()); // place the panel inside the wrapper
                $('.table-actions-wrapper', tableContainer).remove(); // remove the template container
            }

            if ($('.portlet .portlet-title .tools').size() === 1) {
                $('.portlet .portlet-title .tools').html($('.dt-buttons', tableContainer).parents("div.row")); // place the panel inside the wrapper
                // $('.dt-buttons', tableContainer).remove(); // remove the template container
            }

            // 注册全量控制复选框改变事件
            $('.group-checkable', table).change(function() {
                var set = table.find('tbody > tr > td:nth-child(1) input[type="checkbox"]');
                var checked = $(this).prop("checked");
                $(set).each(function() {
                    $(this).prop("checked", checked);
                });
                countSelectedRecords();
                checkboxChange();
            });

            // 注册行单击事件
            table.on('click', 'tbody > tr', function() {
                if(event.target.tagName == "SPAN"){
                    var checkbox = $(this).find('td:nth-child(1) input[type="checkbox"]')
                    checkbox.prop("checked", !checkbox.prop("checked"));
                    return false;
                }else{
                    $(this).siblings().removeClass("active");
                    $(this).siblings().find('td:nth-child(1) input[type="checkbox"]').prop("checked", false);

                    $(this).find('td:nth-child(1) input[type="checkbox"]').prop("checked", true);

                    countSelectedRecords();
                    checkboxChange();
                }
            });

            // 注册内容行第一列复选框改变事件
            table.on('change', 'tbody > tr > td:nth-child(1) input[type="checkbox"]', function() {
                countSelectedRecords();
                checkboxChange();
            });

            // 注册搜索按钮点击事件
            table.on('click', '.filter-submit', function(e) {
                e.preventDefault();
                the.submitFilter();
            });

            // 注册搜索重置按钮点击事件
            table.on('click', '.filter-cancel', function(e) {
                e.preventDefault();
                the.resetFilter();
            });
        },

        submitFilter: function() {
            the.setAjaxParam("action", tableOptions.filterApplyAction);

            // get all typeable inputs
            $('textarea.form-filter, select.form-filter, input.form-filter:not([type="radio"],[type="checkbox"])', table).each(function() {
                the.setAjaxParam($(this).attr("name"), $(this).val());
            });

            // get all checkboxes
            $('input.form-filter[type="checkbox"]:checked', table).each(function() {
                the.addAjaxParam($(this).attr("name"), $(this).val());
            });

            // get all radio buttons
            $('input.form-filter[type="radio"]:checked', table).each(function() {
                the.setAjaxParam($(this).attr("name"), $(this).val());
            });

            dataTable.ajax.reload();
        },

        resetFilter: function() {
            $('textarea.form-filter, select.form-filter, input.form-filter', table).each(function() {
                $(this).val("");
            });
            $('input.form-filter[type="checkbox"]', table).each(function() {
                $(this).attr("checked", false);
            });
            the.clearAjaxParams();
            the.addAjaxParam("action", tableOptions.filterCancelAction);
            dataTable.ajax.reload();
        },

        getSelectedRowsCount: function() {
            return $('tbody > tr > td:nth-child(1) input[type="checkbox"]:checked', table).size();
        },

        getSelectedRows: function() {
            var rows = [];
            $('tbody > tr > td:nth-child(1) input[type="checkbox"]:checked', table).each(function() {
                rows.push($(this).val());
            });

            return rows;
        },

        setAjaxParam: function(name, value) {
            ajaxParams[name] = value;
        },

        addAjaxParam: function(name, value) {
            if (!ajaxParams[name]) {
                ajaxParams[name] = [];
            }

            skip = false;
            for (var i = 0; i < (ajaxParams[name]).length; i++) { // check for duplicates
                if (ajaxParams[name][i] === value) {
                    skip = true;
                }
            }

            if (skip === false) {
                ajaxParams[name].push(value);
            }
        },

        clearAjaxParams: function(name, value) {
            ajaxParams = {};
        },

        getDataTable: function() {
            return dataTable;
        },

        getTableWrapper: function() {
            return tableWrapper;
        },

        getTableContainer: function() {
            return tableContainer;
        },

        getTable: function() {
            return table;
        }

    };

};