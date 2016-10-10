<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<link href="assets/global/plugins/lightbox2/css/lightbox.min.css" rel="stylesheet" type="text/css" />
<link href="assets/global/plugins/dropzone/dropzone.min.css" rel="stylesheet" type="text/css" />
<link href="assets/global/plugins/dropzone/basic.min.css" rel="stylesheet" type="text/css" />

<div>
    <div id="conversationDiv">

        <div id="message" style="border:1px solid gray;"></div>

        <div id="datetime"></div>
        <img id="img">

        当前用户：<sec:authentication property="principal.username"/><br>

        其他用户：<span id="allUser"></span><br><br>

        <label>目 标：</label><strong><span id="toLabel"></span></strong><input type="hidden" id="to" name="to"/><br>
        <label>内 容：</label><input type="text" id="content" onkeypress="
            if (window.event.keyCode == 13) {
                requestHandler();
            }
        "/><br>
        <label>附件:</label><input type="file" id="file"/>
        <form class="dropzone" enctype="multipart/form-data">
            <input type="hidden" name="form" value="<sec:authentication property="principal.username"/>">
            <input type="hidden" name="to">
        </form>
        <button onclick="requestHandler()"> 发 送 </button>
        <br><br>
    </div>
    <div>
        <button id="connect" onclick="connect();">连接</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();">断开</button>
    </div>
</div>

<br>
﻿<div id="fullSizeImage" class="modal hide">
<div class="modal-header">
    <button data-dismiss="modal" class="close" type="button"></button>
</div>
<div class="modal-body">
    <div id="img_show">
    </div>
</div>
</div>
<!-- BEGIN THEME GLOBAL SCRIPTS -->

<!-- END THEME GLOBAL SCRIPTS -->
<script type="text/javascript" src="assets/global/plugins/sockjs-1.0.3.min.js"></script>
<script type="text/javascript" src="assets/global/plugins/stomp-2.3.3.min.js"></script>

<script type="text/javascript" src="assets/global/plugins/lightbox2/js/lightbox.min.js"></script>

<script type="text/javascript" src="assets/global/plugins/dropzone/dropzone.min.js"></script>

<script type="text/javascript">
    var username = '<sec:authentication property="principal.username"/>';
    $(".dropzone").dropzone({
        dictDefaultMessage: "点击选择文件或拖拽文件到此",
        init: function() {
            this.on("addedfile", function(file) {
                // Create the remove button
                var removeButton = Dropzone.createElement("<a href='javascript:;'' class='btn red btn-sm btn-block'>删除</a>");
                // Capture the Dropzone instance as closure.
                var _this = this;
                // Listen to the click event
                removeButton.addEventListener("click", function(e) {
                    // Make sure the button click doesn't submit the form:
                    e.preventDefault();
                    e.stopPropagation();

                    // Remove the file preview.
                    _this.removeFile(file);
                    // If you want to the delete the file on the server as well,
                    // you can do the AJAX request here.
                });

                // Add the button to the file preview element.
                file.previewElement.appendChild(removeButton);
            });
        },
        url: "api/upload"
    });

</script>
<script type="text/javascript" src="test/webqq.js"></script>

