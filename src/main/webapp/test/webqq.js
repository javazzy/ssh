/**
 * Created by admin on 2016/9/27.
 */
var stompClient = null;
var allusers = [];
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

        stompClient.subscribe('/topic/addUser', function (response) {
            allusers[allusers.length] = response.body;
            showAllUser();
        });
        stompClient.subscribe('/topic/removeUser', function (response) {
            for (var i in allusers) {
                if (allusers[i] == response.body) {
                    allusers.splice(i, 1);
                    break;
                }
            }
            showAllUser();
        });
        $.get('api/users', function (users) {
            allusers = users;
            showAllUser();
        });
    });
}

function showAllUser() {
    var toAllHref = '<a onclick="to.value=\'\';toLabel.innerHTML=this.innerHTML">所有人</a> ';
    $("#allUser").html(toAllHref);
    $(allusers).each(function (i, user) {
        if (user == username) return;
        var toHref = '<a onclick="to.value=this.innerHTML;toLabel.innerHTML=this.innerHTML">' + user + '</a>';
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

    var files = document.getElementById("file").files;
    if (files.length > 0) {
        var reader = new FileReader();
        //文件读取完毕后该函数响应
        reader.onload = function (evt) {
            sendMessage(username, to, evt.target.result);
        }
        reader.readAsDataURL(files[0]);
    }

    if ($("#content").val()) {
        sendMessage(username, to, $("#content").val());
        $("#content").val("");
    }
}
function sendMessage(from,to,content){
    var headers = {
        from: from,
        to: to,
        taskId: from+'-'+to+'-'+new Date().getTime(),
        packageIndex: 0,
        packageTotal: 1,
        time: 0,
    };

    var packageIndex = 0;
    var splitSize = 10000; //最大传输字符数:65536
    var contentLength = content.length;
    headers["packageTotal"]=Math.ceil(contentLength / splitSize);
    for (var i = 0; i < contentLength; i += splitSize) {
        headers["packageIndex"]=packageIndex;

        if(contentLength - i < splitSize){
            splitSize = contentLength - i;
        }
        var subContent = content.substr(i, splitSize);
        stompClient.send("/app/send", headers, subContent);
        packageIndex++;
    }
}

function responseHandler(response) {
    console.log(response);
    var from = response.headers.from;
    var to = response.headers.to;
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
        console.log(allContent);
        if(allContent.startsWith("data:image")){
            var img = document.createElement("img");
            img.src = allContent;
            content = img.outerHTML;
        }
        show(from, to, time, content);
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