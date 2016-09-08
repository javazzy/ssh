<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="formAnalyzer" action="api/jcseg/analyzer.html" method="post" onsubmit="return ajaxSubmitAnalyzer()">
    <input type="checkbox" name="lexicon" value="/lexicons/country">国家
    <input type="checkbox" name="lexicon" value="/lexicons/food">食物
    <br>
    <textarea rows="10" cols="80" name="content">我们中华人民共和国！中国有麻辣烫和麻辣干锅。美国有可口可乐和肯德鸡</textarea>
    <br>
    <input type="submit" value="提交">
    <br>

    <p id="words"></p>
</form>

<br>

<form id="formExtract" action="api/jcseg/extract.json" method="post" onsubmit="return ajaxSubmitExtract()">

    <textarea rows="10" cols="80" name="content">我买了一个轮船，总重量约194吨!</textarea>
    <br>
    <input type="submit" value="提交">
    <br>
    <p id="attrs"></p>
</form>
<script src="../assets/global/plugins/jquery.form.js" type="text/javascript"></script>

<script type="text/javascript">
    function ajaxSubmitAnalyzer() {
        $("#formAnalyzer").ajaxSubmit({
            success:function(result){
                $("#words").html(result);
            }
        });
        return false;
    }
    function ajaxSubmitExtract() {
        $("#formExtract").ajaxSubmit({
            success:function(result){
                var html = "";
                for(var key in result){
                    html += key+"："+result[key]+"<br>";
                }
                $("#attrs").html(html);
            }
        });
        return false;
    }
</script>
