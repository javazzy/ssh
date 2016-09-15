var windowAlert = window.alert;
var windowConfirm = window.confirm;
var windowPrompt = window.prompt;

/**
 * 常规消息提示
 * @param msg
 */
var info = function(msg,clickFn) {
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "positionClass": "toast-top-right",
        "onclick": clickFn,
        "showDuration": "1000",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
    toastr["info"](msg,"信息提示");
}
/**
 * 警告消息提示（推荐用alert）
 * @param msg
 */
var warning = function(msg,clickFn) {
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "positionClass": "toast-top-right",
        "onclick": clickFn,
        "showDuration": "1000",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
    toastr["warning"](msg,"警告");
}
/**
 * 成功消息提示
 * @param msg
 */
var success = function(msg,clickFn) {
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "positionClass": "toast-top-right",
        "onclick": clickFn,
        "showDuration": "1000",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
    toastr["success"](msg,"成功提示");
}

/**
 * 失败消息提示
 * @param msg
 */
var error = function(msg,clickFn) {
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "positionClass": "toast-top-right",
        "onclick": clickFn,
        "showDuration": "1000",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
    toastr["error"](msg,"错误提示");
}

/**
 * 警告模态框
 * @param msg
 * @param callback
 */
var alert = function(msg, callback) {
    if(!msg){
        windowAlert();
    }else{
        bootbox.dialog({
            message: '<i class="fa-lg fa fa-warning"></i> '+msg,
            title: "警告",
            buttons: {
                main: {
                    label: "确定",
                    className: "blue",
                    callback: callback
                }
            }
        });
    }
}

/**
 * 确认模态框
 * @param msg
 * @param fnYes
 * @param eventElement
 */
var confirm = function(msg, fnOk,fnCancen) {
    if(!fnOk && !fnCancen){
        return windowConfirm(msg);
    } else {
        bootbox.dialog({
            message: '<i class="fa-lg fa fa-question-circle"></i> '+msg,
            title: "确认提示",
            buttons: {
                main: {
                    label: "确定",
                    className: "blue",
                    callback: fnOk
                },
                cancel: {
                    label: "取消",
                    className: "default",
                    callback: fnCancen
                }
            }
        });
    }
}

/**
 * 输入模态框
 * @param msg
 * @param callback
 */
var prompt = function(msg, callback) {
    if(!callback){
        return windowPrompt(msg);
    }else{
        bootbox.prompt(msg, function(result) {
            callback(result);
        });
    }
}