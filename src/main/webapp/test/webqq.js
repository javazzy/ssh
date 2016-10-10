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
    document.getElementById('content').innerHTML = '';
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

        stompClient.subscribe('/topic/files', function (response) {
            receiveFile(response.body);
        });
        stompClient.subscribe('/user/' + username + '/files', function (response) {
            receiveFile(response.body);
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
    var to = $("#to").val();
    var type = "message";
    var name = "";

    var files = document.getElementById("file").files;
    if (files.length > 0) {
        type = files[0].type;
        name = files[0].name;

        var reader = new FileReader();
        //文件读取完毕后该函数响应
        reader.onload = function (evt) {
            sendMessage(username, to, this.result,type,name);
        }

        if(files[0].type.startWith("application")) {
            reader.readAsArrayBuffer(files[0]);
        } else if(files[0].type.startWith("image")){
            reader.readAsDataURL(files[0]);
        } else if(files[0].type.startWith("text")) {
            reader.readAsText(files[0],"utf8");
        } else {
            reader.readAsText(files[0]);
        }
    }

    if ($("#content").val()) {
        sendMessage(username, to, $("#content").val(),type,name);
        $("#content").val("");
    }
}
function sendMessage(from,to,content,type,name){
    var headers = {
        from: from,
        to: to,
        type:type,
        name:name,
        taskId: from+'-'+to+'-'+new Date().getTime(),
        packageIndex: 0,
        packageTotal: 1,
        time: 0,
    };

    var packageIndex = 0;
    var splitSize = 10000; //最大传输字符数:65536
    var contentLength = content.byteLength || content.length;
    headers["packageTotal"]=Math.ceil(contentLength / splitSize);
    for (var i = 0; i < contentLength; i += splitSize) {
        headers["packageIndex"]=packageIndex;

        if(contentLength - i < splitSize){
            splitSize = contentLength - i;
        }
        var subContent = content.substr?content.substr(i, splitSize):content.slice(i, i+splitSize);
        stompClient.send("/app/send", headers, subContent);
        packageIndex++;
    }
}

function responseHandler(response) {
    var from = response.headers.from;
    var to = response.headers.to;
    var type = response.headers.type;
    var name = response.headers.name;
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
        } else if(type.startWith("image")) {
            var a = document.createElement("a");
            a.href=allContent;
            a.setAttribute("data-lightbox",from);
            a.setAttribute("data-title",name);

            var img = document.createElement("img");
            img.width = 150;
            img.src = allContent;

            a.innerHTML = img.outerHTML;
            content = a.outerHTML;
        }else if(type.startWith("file")) {
            var a = document.createElement("a");
            a.innerHTML = name;
            a.href="api/download/"+encodeURI(name);
            a.alt = "点击下载";
            content = a.outerHTML;
        }else{
            var pre = document.createElement("pre");
            pre.innerHTML = allContent;
            content = pre.outerHTML;
        }
        show(from, to, time, content);
    }
}

function receiveFile(responseBody){
    var info = eval("("+responseBody+")");
    var a = document.createElement("a");
    a.innerHTML = info.name;
    a.href="api/download?fileName="+encodeURI(info.name);
    a.alt = "点击下载";
    show(info.from, info.to, info.time, a.outerHTML)
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
