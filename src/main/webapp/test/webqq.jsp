<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%--<link href="assets/global/plugins/lightbox2/css/lightbox.min.css" rel="stylesheet" type="text/css"/>--%>
<link href="assets/global/plugins/dropzone/dropzone.min.css" rel="stylesheet" type="text/css"/>
<link href="assets/global/plugins/dropzone/basic.min.css" rel="stylesheet" type="text/css"/>
<link href="assets/global/plugins/bootstrap-summernote/summernote.css" rel="stylesheet" type="text/css"/>

<div>
    <div id="conversationDiv">
        <div id="message" style="border:1px solid gray;"></div>
        当前用户：<sec:authentication property="principal.username"/><br>
        其他用户：<span id="allUser"></span><br><br>
        <label>目 标：</label><strong><span id="toLabel"></span></strong><input type="hidden" id="to" name="to"/><br>
        <div class="tabbable-custom">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="#divMessage" data-toggle="tab"> 信息 </a>
                </li>
                <li>
                    <a href="#divFile" data-toggle="tab"> 附件 </a>
                </li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane active" id="divMessage">
                    <form id="sendMessageForm" action="api/message" method="post" onsubmit="return false">
                        <input type="hidden" name="type" value="message">
                        <input type="hidden" name="from" value="<sec:authentication property="principal.username"/>">
                        <input type="hidden" name="to">
                        <input type="hidden" name="content">
                        <div id="contentEditor"></div>
                        <button onclick="requestHandler()"> 发 送</button>
                    </form>
                </div>
                <div class="tab-pane" id="divFile">
                    <form action="api/upload" class="dropzone" enctype="multipart/form-data">
                        <input type="hidden" name="type" value="file">
                        <input type="hidden" name="from" value="<sec:authentication property="principal.username"/>">
                        <input type="hidden" name="to">
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div>
        <button id="connect" onclick="connect();">连接</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();">断开</button>
    </div>
</div>

<%--<br>--%>
<%--﻿<div id="fullSizeImage" class="modal hide">--%>
    <%--<div class="modal-header">--%>
        <%--<button data-dismiss="modal" class="close" type="button"></button>--%>
    <%--</div>--%>
    <%--<div class="modal-body">--%>
        <%--<div id="img_show"></div>--%>
    <%--</div>--%>
<%--</div>--%>
<!-- BEGIN THEME GLOBAL SCRIPTS -->

<!-- END THEME GLOBAL SCRIPTS -->
<!-- websocket功能支持 -->
<script type="text/javascript" src="assets/global/plugins/sockjs-1.0.3.min.js"></script>
<script type="text/javascript" src="assets/global/plugins/stomp-2.3.3.min.js"></script>
<!-- 图片点击放大 -->
<%--<script type="text/javascript" src="assets/global/plugins/lightbox2/js/lightbox.min.js"></script>--%>
<!-- 文件拖拽上传 -->
<script type="text/javascript" src="assets/global/plugins/dropzone/dropzone.min.js"></script>
<!-- summernote富文本编辑器 -->
<script src="assets/global/plugins/bootstrap-summernote/summernote.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/bootstrap-summernote/lang/summernote-zh-CN.js" type="text/javascript"></script>

<script type="text/javascript">
    var username = '<sec:authentication property="principal.username"/>';
</script>
<script type="text/javascript" src="test/webqq.js"></script>

