<!DOCTYPE html>
<div id="wrap">
	<%@include file="header.jsp"%>
	<%@ page import="model.Currency"%>
	<%@ page import="java.util.ArrayList"%>
	<div class="body">
		<div class="main">
			<div class="pagetitle">Currency Exchange Rates</div>
			<hr width="95%" noshade>
			<div align="center">
				<div class="content">
					<table>
						<col width="20%">
						<col width="30%">
						<col width="25%">
						<col width="25%">
						<tr>
							<th>ISO Code</th>
							<th>Country</th>
							<th>Buy Rate</th>
							<th>Sell Rate</th>
						</tr>
						<%
							ArrayList<Currency> rates = (ArrayList<Currency>) request.getAttribute("rates");
							for (Currency cur : rates) {
						%>
						<tr>
							<td><%=cur.getISO()%></td>
							<td><%=cur.getCountry()%></td>
							<td><%=cur.getBuyRate()%></td>
							<td><%=cur.getSellRate()%></td>
						</tr>
						<%
							}
						%>
					</table>
					<br>
					<br>
					<br>
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="footer.jsp"%>
</html>