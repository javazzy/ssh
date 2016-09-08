<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<div id="message"></div>
<div>
    <h1>submitUserList_4</h1>
    <form id="form1" method="post" onsubmit="return ajaxSubmit()">
        ID:<input type="text" name="id"><br/>
        NAME:<input type="text" name="name"><br/>

        ID:<input type="text" name="id"><br/>
        NAME:<input type="text" name="name"><br/>
        <input type="submit" value="submit">
    </form>
</div>

<!-- END THEME GLOBAL SCRIPTS -->
<script src="../assets/global/plugins/jquery.form.js" type="text/javascript"></script>
<script src="../assets/global/scripts/form2json.js" type="text/javascript"></script>

<script type="text/javascript">
    function ajaxSubmit(){
        $.ajax({
            url: "/api/sysUsers/deleteAll",
            type: "POST",
            contentType : 'application/json;charset=utf-8', //设置请求头信息
            dataType:"json",
            data: $("#form1").serializeJson(),
            success: function(data){
                console.log(data);
            },
            error: function(res){
                console.log(res);
            }
        });
        return false;
    }
</script>