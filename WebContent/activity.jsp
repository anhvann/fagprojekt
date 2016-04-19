<!DOCTYPE html>
<div class="container">
	<%@include file="header.jsp"%>
	<div class="main" align="center">
	<div>
		<form action="Activity" id="AccountID" method="post">
			<input type="text" value="${ requestScope.value}">
		</form> 
	</div>
	<table>
		<tr>
			<td>Account</td>
			<td>Balance</td>
		</tr>
		<tr>
			<td>1234-0056789</td>
			<td>5.000,00 DKK</td>
		</tr>
		<tr>
			<td>9999-8765432</td>
			<td>10.000,00 DKK</td>
	</table></div>
	<%@include file="footer.jsp"%>
	</div>
</html>