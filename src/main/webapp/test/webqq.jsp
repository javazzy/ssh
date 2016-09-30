<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<div>
    <div id="conversationDiv">

        <div id="message" style="border:1px solid gray;"></div>

        <div id="datetime"></div>
        <img id="img">

        当前用户：<sec:authentication property="principal.username"/><br>

        其他用户：<span id="allUser"></span><br><br>

        <label>目 标：</label><strong><span id="toLabel"></span></strong><input type="hidden" id="to"/><br>
        <label>内 容：</label><input type="text" id="content" onkeypress="
            if (window.event.keyCode == 13) {
                requestHandler();
            }
        "/><br>
        <label>附件:</label><input type="file" id="file"/>
        <button onclick="requestHandler()"> 发 送 </button>
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
    var username = '<sec:authentication property="principal.username"/>';
</script>
<script type="text/javascript" src="test/webqq.js"></script>
