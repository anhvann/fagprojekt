<!DOCTYPE HTML><%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<div class="container">
	<%@include file="header.jsp"%>
	<div class="main">
		<div class="pagetitle">
			<div class="col-sm-10">Activity for ${accountID}</div>
		</div>
		<div class="pagesubtitle2" align="right"><a href="Activity?ID=${cpr}&action=closeaccount&accountID=${accountID}">Close Account</a></div>
		<div align="center"><table>
		  		<col width="15%">
		  		<col width="50%">
		  		<col width="15%">
		  		<col width="20%">
            	<tr>
                	<th>Date</th>
                	<th>Category</th>
                	<th>Amount</th>
               		<th>Balance</th>
           		</tr>
           		<tr>
               		<td> 10-10-2016</td>
               		<td> Salary </td>
					<td align="right"> +1534.00</td>
               		<td align="right"> 63,542.11</td>
           		<tr/>
           		<tr>
               		<td> 10-10-2016</td>
               		<td> Salary </td>
					<td align="right"> +1534.00</td>
               		<td align="right"> 63,542.11</td>
           		<tr/>
       		</table></div>	
	</div>
</div>
<%@include file="footer.jsp"%>
</html>