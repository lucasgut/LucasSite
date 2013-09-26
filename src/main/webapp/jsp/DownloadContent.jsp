<div class="boxhead">
	<h2>
	<table width="90%" border="0">
	<tr> 
	<td width="40%">Last modified: ${download.lastModified}</td>
	<td width="30%"><a href="${download.url}">Download</a></td>
	<c:choose>
		<c:when test="${isGuest}">
		</c:when>
		<c:otherwise>
			<td width="15%"><div class="outer"><a class="button" href="PostDownload.html?downloadId=${download.id}">Modify</a></div></td><td width="15%"><div class="outer"><a class="button" href="javascript:deleteRecord(${download.id})">Delete</a></div></td>
		</c:otherwise>
	</c:choose>
	</tr>
	</table>
	</h2>
</div>
<div class="boxbody">
	<table width="90%" border="0">
	<tr> 
	<td width="20%"><img src="DownloadImage.html?downloadId=${download.id}";width="100%";height="100%";background="ffffff"/></td>
	<td width="80%">${download.description}</td>
	</tr>
	</table>
</div>