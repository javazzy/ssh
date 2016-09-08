<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<div>
    <div id="conversationDiv">
        <div id="message" style="border:1px solid gray;"></div>

        <div id="datetime"></div>
        当前用户：<sec:authentication property="principal.username"/><br>

        其他用户：<span id="allUser"></span><br><br>

        <label>目 标：</label><strong><span id="toLabel"></span></strong><input type="hidden" id="to"/><br>
        <label>内 容：</label><input type="text" id="content" onkeypress="
            if (window.event.keyCode == 13) {
                sendName();
            }
        "/>
        <%--<label>附件:</label><input type="file" id="file"/><br>--%>
        <button onclick="sendName()"> 发 送 </button>
        <br><br>
    </div>
    <div>
        <button id="connect" onclick="connect();">连接</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();">断开</button>
    </div>
</div>

<br>

<!-- BEGIN THEME GLOBAL SCRIPTS -->

<!-- END THEME GLOBAL SCRIPTS -->
<script type="text/javascript" src="../assets/global/plugins/sockjs-1.0.3.min.js"></script>
<script type="text/javascript" src="../assets/global/plugins/stomp-2.3.3.min.js"></script>


<script type="text/javascript">
    var stompClient = null;
    var username = '<sec:authentication property="principal.username"/>';
    var allusers = [];

    function setConnected(connected) {
        document.getElementById('connect').disabled = connected;
        document.getElementById('disconnect').disabled = !connected;
        document.getElementById('conversationDiv').style.display = connected ? '' : 'none';
        document.getElementById('message').innerHTML = '';
    }

    function connect() {
        var socket = new SockJS('/api/websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect("", "", function (frame) {
            setConnected(true);
            stompClient.subscribe('/topic/notice', function (response) {
                show(response);
            });
            stompClient.subscribe('/user/'+username+'/message', function (response) {
                show(response);
            });

            stompClient.subscribe('/topic/addUser', function (response) {
                allusers[allusers.length]=response.body;
                showAllUser();
            });
            stompClient.subscribe('/topic/removeUser', function (response) {
                for(var i in allusers){
                    if(allusers[i] == response.body){
                        allusers.splice(i,1);
                        break;
                    }
                }
                showAllUser();
            });
            $.get('api/users',function(users){
                allusers = users;
                showAllUser();
            });
        });
    }

    function showAllUser(){
        var toAllHref = '<a onclick="to.value=\'\';toLabel.innerHTML=this.innerHTML">所有人</a> ';
        $("#allUser").html(toAllHref);
        $(allusers).each(function(i,user){
            if(user == username) return;
            var toHref = '<a onclick="to.value=this.innerHTML;toLabel.innerHTML=this.innerHTML">'+user+'</a>';
            $("#allUser").append(toHref).append(" ");
        });
    }

    function disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        setConnected(false);
    }

    function sendName() {
        stompClient.send("/app/send", {from:username,to:$("#to").val(),time:new Date().getTime()}, $("#content").val());
        $("#content").val("");
    }

    function show(response) {
        var from = response.headers.from;
        var to = response.headers.to;
        var time = new Date(parseInt(response.headers.time)).format("yyyy-MM-dd HH:mm:ss");
        var message = response.body;

        from = from==username?"<font color='green'>你</font>":from;
        to = to==username?"<font color='green'>你</font>":to;
        to = to?to:"<font color='black'>所有人</font>";

        var line = "<div>";
        line += "<b><font color='blue'>"+from+"</font></b>";
        line += "@";
        line += "<b><font color='blue'>"+to+"</font></b>";
        line += " <font color='gray' size='1'>"+time+"</font>";
        line += "<p>"+message+"</p>";
        line += "</div>";

        $("#message").append(line);
    }

    disconnect();
</script>
