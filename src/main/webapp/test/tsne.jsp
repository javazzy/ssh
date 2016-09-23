<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="form" class="form" action="api/tsnes/fit" method="post" enctype="multipart/form-data">
    <input type="file" name="content"><br>
    <input id="submit" type="button" value="降维">
</form>

<a href="api/tsnes/weights">下载 weights.csv</a>
<script type="text/javascript">
    $(function () {
       $("#submit").on("click",function(){
           App.startPageLoading();
            $("#form").ajaxSubmit({
                success:function(result){
                    App.stopPageLoading();
                    if(result.success){
                        success(result.msg);
                    }else {
                        error(result.msg);
                    }
                },
                error:function(result){
                    App.stopPageLoading();
                    error(result);
                }
            });
           return false;
       });
    });
</script>
