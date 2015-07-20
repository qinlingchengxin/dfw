<%@page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
	<META HTTP-EQUIV="Expires" CONTENT="0"> 
	<title>健康知识系列游戏online</title>

    <style type="text/css" media="screen"> 
		html, body	{ height:100%; }
		body { 
			margin:0; 
			padding:0;
			
			overflow:auto; 
			text-align:center;
			font-size:12px;
			background-color: #000000;
		} 
		.content { 
       	   margin: 0 auto;
       	   padding: 0px; 
       	   overflow-y:hidden;
		}
		#flashcontent { 
           float:left;
           background-color:#000000;
		}
    </style>
    <script type="text/javascript" src="setting.js"></script>
	<script type="text/javascript" src="swfobject.js"></script>
</head>	
<body class = "content" onbeforeunload="window.event.returnValue='确实要关闭游戏吗?'">
		
<!--iframe src="top.php?openid=" frameborder="0" height="23px;" scrolling="no" width="100%"></iframe-->
	
<tr>
<td>
<iframe  frameborder="0" scrolling="no" style="margin:0 auto;width:100%;overflow:hidden;float:none;" id="flashcontent">
<p>
					        	To view this page ensure that Adobe Flash Player version 
								11.1.0 or greater is installed. 
</p>
<script type="text/javascript">
							    var pageHost = ((document.location.protocol == "https:") ? "https://" : "http://");
							    document.write("<a href='http://www.adobe.com/go/getflashplayer'><img src='"+ pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>"); 
</script>
</iframe>
</div>
</td>
</tr>
<script type="text/javascript">
			function setGmFrmHgt()
			{
				var m_ch=document.body.clientHeight;
				document.getElementById("flashcontent").height=m_ch+"px";
			}
			window.onresize=setGmFrmHgt;
			
			var swfObj = null;
			function onLoadComplete(callobj)
			{
				swfObj = callobj.ref;
				setGmFrmHgt();
			}
			
			window.document.onkeydown = function HandleTabKey(evt) {
				if (evt.keyCode == 9) {
					if (evt.preventDefault) 
					{ 
						evt.preventDefault(); 
					}
					else { evt.returnValue = false; }
				}
			}

		 	var swfVersionStr = "11.1.0";
		 	var xiSwfUrlStr = "playerProductInstall.swf";
            var flashvars = {};
            document.title =  "健康知识系列游戏";
            var params = {};
            params.quality = "high";
            params.bgcolor = "#000000";
            params.allowScriptAccess = "always";
            params.allowFullScreen = "true";
            params.menu = "false";
            params.name = "flashcontent";
	        params.id = "flashcontent";
            var attributes = {};
            attributes.id = "flashcontent";
            attributes.style="flashcontent";          
            swfobject.embedSWF("main.swf", "flashcontent", "100%", "100%", swfVersionStr, xiSwfUrlStr, flashvars, params, attributes,onLoadComplete);
	</script>
<script language="javascript">function setGmFrmHgt(){var m_ch=document.body.clientHeight;document.getElementById("flashcontent").height=m_ch+"px";}window.onresize=setGmFrmHgt;setGmFrmHgt();</script>
</body>
</html>