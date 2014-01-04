<%@page session="true"%>
<html>
<head>
	<link href="resources/css/MainStyles.css" type="text/css" rel="stylesheet">
	<script src="resources/js/ajax.js" type="text/javascript"></script>
	<script src="resources/js/downloads.js" type="text/javascript"></script>
	<script src="resources/js/jquery-1.10.2.js" type="text/javascript"></script>
	<title>Lucas' Programming Resources</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body>
	<%@ include file="Header.jsp" %>
	<table width="100%" height="84%" border="0" align="left" valign="top">
		<tr valign="top">
			<td class="tdMenuLeft" valign="top" width="10%">
				<%@ include file="Menu.jsp" %>
			</td>
			<td class="tdMainSection" valign="top" width="90%">
				<h3>Downloads<br/></h3>
				<c:choose>
					<c:when test="${isGuest}">
					</c:when>
					<c:otherwise>
						<a href="PostDownload.html">Post a download</a><br>
					</c:otherwise>
				</c:choose>
				<br/>
				<table width="100%" height="100%" border="0" align="left" valign="top">
					<c:forEach var="download" items="${downloads}" >
					<tr><td>
						<p>
							<a class = "heading" href = "javascript: toggleDiv('div_download_${download.id}','DownloadContent.html?downloadId=${download.id}')"><b>${download.subject}</b></a>
						</p>
						<div class = "widthlessbox" id = "div_download_${download.id}"></div>
					</td></tr>
					</c:forEach> 
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
