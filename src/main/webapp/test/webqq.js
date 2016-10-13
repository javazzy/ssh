/**
 * Created by admin on 2016/9/27.
 */
var stompClient = null;
var allUsers = [];
var taskMessage = {};

function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('conversationDiv').style.display = connected ? '' : 'none';
}

function connect() {
    var socket = new SockJS('/api/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect("", "", function (frame) {
        setConnected(true);
        stompClient.subscribe('/topic/notice', function (response) {
            responseHandler(response);
        });
        stompClient.subscribe('/user/' + username + '/message', function (response) {
            responseHandler(response);
        });

        stompClient.subscribe('/topic/addUser', function (response) {
            allUsers[allUsers.length] = response.body;
            showAllUser();
        });
        stompClient.subscribe('/topic/removeUser', function (response) {
            for (var i in allUsers) {
                if (allUsers[i] == response.body) {
                    allUsers.splice(i, 1);
                    break;
                }
            }
            showAllUser();
        });
        $.get('api/users', function (users) {
            allUsers = users;
            showAllUser();
        });
    });
}

function changeTo(to,label){
    $("#toLabel").html(label);
    $("input[name='to']").val(to);
}

function showAllUser() {
    var toAllHref = '<a onclick="changeTo(\'\',this.innerHTML)">所有人</a> ';
    $("#allUser").html(toAllHref);
    $(allUsers).each(function (i, user) {
        if (user == username) return;
        var toHref = '<a onclick="changeTo(this.innerHTML,this.innerHTML)">' + user + '</a>';
        $("#allUser").append(toHref).append(" ");
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
}

function requestHandler() {
    var content = $('#contentEditor').code();
    if (content != '<p><br></p>') {
        $("[name='content']").val(content);
        $("#sendMessageForm").ajaxSubmit({
            success:function(){
                $('#content').code("<p><br></p>");
                $("[name='content']").val("");
            }
        });
    }else{
        warning("发送信息为空！");
    }
}

function responseHandler(response) {
    try{
        var from = response.headers.from;
        var to = response.headers.to;
        var type = response.headers.type;
        var time = new Date(parseInt(response.headers.time)).format("yyyy-MM-dd HH:mm:ss");
        var taskId = response.headers.taskId;
        var packageIndex = response.headers.packageIndex;
        var packageTotal = response.headers.packageTotal;
        var content = response.body;

        if(!taskMessage[taskId]){
            taskMessage[taskId] = {};
        }
        taskMessage[taskId][packageIndex] = content;

        if(Object.keys(taskMessage[taskId]).length == packageTotal){
            var allContent = "";
            for (var i=0; i < parseInt(packageTotal); i++) {
                allContent += taskMessage[taskId][i];
            }
            if(type == "message") {
                content = allContent;
            }else if(type == "file") {
                var a = document.createElement("a");
                a.innerHTML = content;
                a.href="api/download?fileName="+encodeURI(content);
                a.alt = "点击下载";
                content = a.outerHTML;
            }
            delete taskMessage[taskId];
            show(from, to, time, content);
        }
    }catch(e){
        console.log(e);
    }

}

function show(from, to, time, content) {
    from = from == username ? "<font color='green'>你</font>" : from;
    to = to == username ? "<font color='green'>你</font>" : to;
    to = to ? to : "<font color='black'>所有人</font>";

    var line = "<div>";
    line += "<b><font color='blue'>" + from + "</font></b>";
    line += "@";
    line += "<b><font color='blue'>" + to + "</font></b>";
    line += " <font color='gray' size='1'>" + time + "</font>";
    line += "<p>" + content + "</p>";
    line += "</div>";

    $("#message").append(line);
}
disconnect();


$(function () {
    $(".dropzone").dropzone({
        dictDefaultMessage: "点击选择文件或拖拽文件到此",
        init: function () {
            this.on("addedfile", function (file) {
                var removeButton = Dropzone.createElement("<a href='javascript:;'' class='btn red btn-sm btn-block'>删除</a>");
                var _this = this;
                removeButton.addEventListener("click", function (e) {
                    e.preventDefault();
                    e.stopPropagation();
                    _this.removeFile(file);
                });
                file.previewElement.appendChild(removeButton);
            });
        },
        url: "api/upload"
    });
    $('#contentEditor').summernote({
//            width:800,
        height: 200,
        lang: 'zh-CN'
    });
    //API:
    //var sHTML = $('#content').code(); // get code
    //$('#content').destroy(); // destroy
});