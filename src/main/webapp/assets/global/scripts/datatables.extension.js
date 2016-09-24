$(function() {
    $.extend( $.fn.dataTable.defaults, {
        autoWidth: false,
        ajax : {// 类似jquery的ajax参数，基本都可以用。
            type : "get",// 后台指定了方式，默认get，外加datatable默认构造的参数很长，有可能超过get的最大长度。
//            url : "api/sysUsers/page",
            dataSrc : "data",// 默认data，也可以写其他的，格式化table的时候取里面的数据
            data : function(paramObj,datatable) {// d 是原始的发送给服务器的数据，默认很长。
                var columns = paramObj.columns;
                var orderArr = paramObj.order;
                var orderby = [];
                for(var i in orderArr){
                    var order = orderArr[i];
                    var orderColumnIndex = order.column;
                    var orderColumnName = columns[orderColumnIndex].data;
                    var orderColumnDir = order.dir;
                    orderby.push(orderColumnName+' '+orderColumnDir);
                }
                delete paramObj.columns;
                paramObj.order = orderby.join(",");

                var formData = $(datatable.nTable).parents(".dataTables_wrapper").find(".table-search-form").serializeArray();// 把form里面的数据序列化成数组
                for(var i in formData){
                    if(formData[i].value){
                        paramObj[formData[i].name] = formData[i].value;
                    }
                }

                if(window.onRefreshData && typeof window.onRefreshData == "function"){
                    try{
                        window.onRefreshData(d);
                    }catch(e){}
                }
                return paramObj;// 自定义需要传递的参数。
            },
        },
        //语法结构
        //l - 每页数量选择select
        //f – 搜索框search
        //t – 表单内容table
        //i – 当前条数，总共条数information
        //p – 翻页按钮pagination
        //r – 请求中的提示信息
        //< 和 > – 一个div的开始与结束
        //<"class"> – class为div的class名称
        dom: '<"datatable-header"><"datatable-scroll"t><"datatable-footer"ilp>',
        lengthMenu: [5,10,20,50,100], //更改显示记录数选项
        order: [],
        columnDefs: [{
            targets: 0,
            orderable: false,
            searchable: false
        }, {
            defaultContent: '',
            orderable: true,
            targets: ['_all']
        }],
        serverSide : true,// 分页，取数据等等的都放到服务端去
        processing : true,// 载入数据的时候是否显示“载入中”
        pageLength : 5,// 首次加载的数据条数
        ordering : true,// 排序操作在服务端进行，所以可以关了
        language: {
            search: '<span>搜索：</span> _INPUT_',
            lengthMenu: '<span>每页条数：</span> _MENU_',
            processing: "载入中",// 处理页面数据的时候的显示
            paginate: {// 分页的样式文本内容。
                "previous" : "上一页",
                "next" : "下一页",
                "first" : "第一页",
                "last" : "最后一页"
            },
            emptyTable:"没有记录",// tbody内容为空时，tbody的内容。
            zeroRecords:"没有过滤到记录",
            // 下面三者构成了总体的左下角的内容。
            infoEmpty: "0条记录",// 筛选为空时左下角的显示。
            info: "共_PAGES_ 页，显示第_START_ 到第 _END_ 条，过滤结果 _TOTAL_ 条，总数_MAX_ 条 ",// 左下角的信息显示，大写的词为关键字。
            infoFiltered: ""// 筛选之后的左下角筛选提示(另一个是分页信息显示，在上面的info中已经设置，所以可以不显示)，
        },
        preDrawCallback: function() { //表格初始化完成（未加载数据前）
            $(this).find('tbody tr').slice(-3).find('.dropdown, .btn-group').removeClass('dropup');

            //复选框批量选中
            $(this).find('.group-checkable').click(function() {
                var set = jQuery(this).attr("data-set");
                var checked = jQuery(this).is(":checked");
                jQuery(set).each(function () {
                    if (checked) {
                        $(this).prop("checked", true);
                        $(this).parents('tr').addClass("active");
                    } else {
                        $(this).prop("checked", false);
                        $(this).parents('tr').removeClass("active");
                    }
                });
                jQuery.uniform.update(set);
            });

            //$('.dataTables_filter input[type=search]').attr('placeholder','Type to filter...');
            // 美化下拉框样式
            $('.dataTables_length select').select2({
                minimumResultsForSearch: Infinity,
                width: 'auto'
            });
            // 美化复选框框样式
            $(this).find("input[type='checkbox']").uniform({
                radioClass: 'choice',
                wrapperClass: 'border-primary text-primary'
            });

            $(this).parents(".dataTables_wrapper").find(".datatable-header").append($(this).parents(".dataTables_wrapper").prev(".table-search"));
            $(this).parents(".dataTables_wrapper").find(".datatable-header").append($(this).parents(".dataTables_wrapper").prev(".table-toolbar"));

            if(window.preDrawCallback && typeof window.preDrawCallback == "function"){
                try{
                    window.preDrawCallback(this);
                }catch(e){}
            }
        },
        drawCallback: function () { //表格加载数据完成
            $(this).find('tbody tr').slice(-3).find('.dropdown, .btn-group').addClass('dropup');

            // 美化复选框框样式
            $(this).find("input[type='checkbox']").uniform({
                radioClass: 'choice',
                wrapperClass: 'border-primary text-primary'
            });

            //注册点击行事件
            $(this).find('tbody tr').click(function (e) {
                if(e.target.className == "checkbox"){
                    return true;
                }
                $(this).parent("tbody").find("tr").removeClass("active");

                $(this).addClass("active");

                $(this).parents("table").find("thead .group-checkable").prop("checked",false);
                $(this).parent("tbody").find(".checkbox").prop("checked",false);
                $(this).find(".checkbox").prop("checked",true);

                $.uniform.update();
            });

            //注册点击复选框事件
            $(this).find(".checkbox").change(function(){
                if(this.checked){
                    $(this).parents('tr').addClass("active");
                } else {
                    $(this).parents('tr').removeClass("active");
                }
                var flag = true;
                $(this).parents('table').find("tbody .checkbox").each(function(i,chk){
                    if(!this.checked){
                        flag = false;
                        return false;
                    }
                });
                $(this).parents('table').find('.group-checkable').prop("checked",flag);
                $.uniform.update();
            });

            if(window.drawCallback && typeof window.drawCallback == "function"){
                try{
                    window.drawCallback(this);
                }catch(e){}
            }
        }
    });

});

// 获取选中行
function getSelectedRows(table){
    var nTrs = table.fnGetNodes();//fnGetNodes获取表格所有行，nTrs[i]表示第i行tr对象
    var rows = [];
    for(var i = 0; i < nTrs.length; i++){
        if($(nTrs[i]).hasClass('active')){
            rows.push(table.fnGetData(i));//fnGetData获取一行的数据
        }
    }
    return rows;
}