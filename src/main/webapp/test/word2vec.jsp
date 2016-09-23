<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="form" class="form" action="api/word2Vecs/fit" method="post" enctype="multipart/form-data">
    <input type="file" name="content" multiple><br>
    <input id="submit" type="button" value="开始训练">
</form>

<br>
<button id="btnSaveModel">保存模型</button>

<br>
<input id="word" type="text"><button id="btnTop">获取前十个关联词</button>
<p id="topWords"></p>

<a href="api/word2Vecs/vectors">下载词向量</a>
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
        $("#btnSaveModel").on("click",function(){
            App.startPageLoading();
            $.get("api/word2Vecs/saveModel",function(){
                App.stopPageLoading();
                success("保存成功！");
            });
        });
        $("#btnTop").on("click",function(){
            $.get("api/word2Vecs/nearest",{word:$("#word").val(),length:10},function(words){
                $("#topWords").html(words.join("<br>"));
            });
        });
    });
</script>
