<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<img src="resources/images/headLogo.gif">
<table width="100%" height="10%" border="0">
	<tr colspan="2"> 
		<td width="50%" >Visitor no.: ${hitcount}<br/>
		<c:choose>
			<c:when test="${isGuest}">
				User:  Guest &nbsp;<a href="Login.html">Sign-in</a><br/>
			</c:when>
			<c:otherwise>
				User:  ${user.userName} &nbsp;<a href="Logout.html">Sign-out</a><br/>
			</c:otherwise>
		</c:choose>
		</td>
	</tr>
</table>

