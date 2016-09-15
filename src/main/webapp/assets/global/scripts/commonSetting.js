$(function(){
    $.fn.loalData = function (obj) {
        var key, value, tagName, type, arr;
        for (x in obj) {
            key = x;
            value = obj[x];

            $(this).find("[name='" + key + "'],[name='" + key + "[]']").each(function () {
                tagName = $(this)[0].tagName;
                type = $(this).attr('type');
                if (tagName == 'INPUT') {
                    if (type == 'radio') {
                        $(this).attr('checked', $(this).val() == value);
                    } else if (type == 'checkbox') {
                        arr = value.split(',');
                        for (var i = 0; i < arr.length; i++) {
                            if ($(this).val() == arr[i]) {
                                $(this).attr('checked', true);
                                break;
                            }
                        }
                    } else {
                        $(this).val(value);
                    }
                } else if (tagName == 'SELECT' || tagName == 'TEXTAREA') {
                    $(this).val(value);
                }

            });
        }
    }

    $.ajaxSetup({
        global: true,
        dataFilter :function(data,type){
            var json = null;
            try{
                if(type == "json" && eval("("+data+")")){
                    return data;
                }
            }catch(e){}
            try{
                if ((type == "html" || type == "json") && $(data).find("form:eq(0)").attr("class") == "login-form") {
                    alert("页面登录已失效，请重新登录!",function(){
                        top.window.location.href = "/";
                    });
                    return false;
                }
            }catch(e){}
            return data;
        },
        beforeSend:function(){
            //startLoading();
        },
        complete:function(){
            //stopLoading();
        },
        statusCode: {
            404 : function(){
                warning("没有找到指定的资源!");
            },
            500 : function(){
                warning("服务器产生内部错误!");
            },
            503 : function(){
                warning("服务器过载或暂停维修!");
            }
        }
    });

    $(document).keydown(function (event) {
        if (event.keyCode == 8) { // 回置（删格）
            try{
                var target = $(event.target);
                if(target.is(":text")
                    || target.is(":password")
                    || target.hasClass("cke_editable")){
                    return true;
                }
            }catch(e){}
            return false;
        }
    });
});