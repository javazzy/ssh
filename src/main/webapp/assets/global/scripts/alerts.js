var windowAlert = window.alert;
var windowConfirm = window.confirm;
var windowPrompt = window.prompt;

/**
 * 常规消息提示
 * @param msg
 */
var info = function (msg, clickFn) {
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
    toastr["info"](msg, "信息提示");
}
/**
 * 警告消息提示（推荐用alert）
 * @param msg
 */
var warning = function (msg, clickFn) {
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
    toastr["warning"](msg, "警告");
}
/**
 * 成功消息提示
 * @param msg
 */
var success = function (msg, clickFn) {
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
    toastr["success"](msg, "成功提示");
}

/**
 * 失败消息提示
 * @param msg
 */
var error = function (msg, clickFn) {
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
    toastr["error"](msg, "错误提示");
}

/**
 * 警告模态框
 * @param msg
 * @param callback
 */
var alert = function (msg, callback) {
    if (!msg) {
        windowAlert();
    } else {
        swal({
                title: msg,
                type: "warning",
                allowOutsideClick: true,
                showConfirmButton: true,
                showCancelButton: false,
                confirmButtonClass: "btn-info",
                closeOnConfirm: true,
                confirmButtonText: "确定"
            },
            function (isConfirm) {
                if (isConfirm && callback) {
                    callback();
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
var confirm = function (msg, fnOk, fnCancen) {
    if (!fnOk && !fnCancen) {
        return windowConfirm(msg);
    } else {
        swal({
                title: msg,
                type: "warning",
                allowOutsideClick: true,
                showConfirmButton: true,
                showCancelButton: true,
                confirmButtonClass: "btn-info",
                cancelButtonClass: "btn-default",
                closeOnConfirm: true,
                closeOnCancel: true,
                confirmButtonText: "确定",
                confirmCancelText: "取消"
            },
            function (isConfirm) {
                if (isConfirm && fnOk) {
                    fnOk();
                } else if (!isConfirm && fnCancen) {
                    fnCancen();
                }
            });
    }
}