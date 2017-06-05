<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.InetAddress" %>

    <script src="assets/global/plugins/jwplayer/7.10.2/jwplayer.js"></script>
    <script>jwplayer.key = "T0RlAAB/lx240KpnicwGl5e4uhc+I7SwM6vyKQ==";</script>
    <script type='text/javascript'>
        var baseUrl = '${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/';
        var rtmpUrl = 'rtmp://${pageContext.request.serverName}${pageContext.request.contextPath}/';
        var player = null;
        function play(mediaUrl) {
            var skin = $("#skin").val();
            if (mediaUrl) {
                console.log(baseUrl + mediaUrl);
                (player ? player : player = jwplayer('mediaspace')).setup({
                    file: mediaUrl,
                    autostart: true,
                    skin: {
                        name: skin
                    },
                    width: '848',
                    height: '360',
                    modes: [
                        {type: "flash", "src": "assets/global/plugins/jwplayer/7.10.2/jwplayer.flash.swf"},
                        {type: "html5"},
                        {type: "download"}
                    ]
                });
            }
        }

    </script>

IP:<%=InetAddress.getLocalHost().getHostAddress()%><br>
皮肤：<select id="skin">
    <option value="seven">seven</option>
    <option value="beelden">beelden</option>
    <option value="bekle">bekle</option>
    <option value="five">five</option>
    <option value="glow">glow</option>
    <option value="roundster">roundster</option>
    <option value="six">six</option>
    <option value="stormtrooper">stormtrooper</option>
    <option value="vapor">vapor</option>
</select><br/>

<input id="url"/><input type="button" onclick="play(url.value)" value="播放"/>
<div id='mediaspace'>This text will be replaced</div>

