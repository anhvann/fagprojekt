<!DOCTYPE HTML><%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<div id="wrap">
	<%@include file="employeecheck.jsp" %>
	<div class="main">
		<div class="pagetitle">Deposit money</div>
		<hr width="95%" noshade>
			<%String accID = request.getParameter("accountID");%>
			<form class="form-inline" action="TransactionActivity?action=deposit" method="post" target="_self">
				<label class="control-label col-sm-5">Account:</label>
				<div class="col-sm-5"><input type="number" class="form-control" name="accountID" placeholder="Enter account ID" value="<%=accID%>" required/></div>
				<br><br>
				<label class="control-label col-sm-5">Amount:</label>
				<div class="col-sm-5"><input type="number" class="form-control" name="amount" placeholder="Enter amount" min="0" step="0.01" max="99999999999.99" required/>
				<select class="form-control" name="ISOCode">
    				<option value="DKK" selected>DKK</option>
    				<option value="USD">USD</option>
    				<option value="EUR">EUR</option> 
    				<option value="SEK">SEK</option>
    				<option value="NOK">NOK</option>  
    				<option value="GBP">GBP</option>
    				<option value="AED">AED</option>
    				<option value="AUD">AUD</option>
    				<option value="BGN">BGN</option>
    				<option value="CAD">CAD</option>
    				<option value="CHF">CHF</option>
    				<option value="CZK">CZK</option> 
    				<option value="EGP">EGP</option>
    				<option value="HKD">HKD</option>
    				<option value="HRK">HRK</option>
    				<option value="HUF">HUF</option>
    				<option value="ILS">ILS</option>
    				<option value="JPY">JPY</option>    				
    				<option value="NZD">NZD</option>
    				<option value="PLN">PLN</option>
    				<option value="RON">RON</option>
    				<option value="RUB">RUB</option>
    				<option value="SAR">SAR</option>
    				<option value="SGD">SGD</option>
    				<option value="THB">THB</option>
    				<option value="TRY">TRY</option>
    				<option value="ZAR">ZAR</option>
  				</select>
  				</div>
				<div class="col-sm-offset-5 col-sm-5"><font size="2"><font color="red">${errormessage}</font></font><br>
				<input type="submit" class="btn btn-default" name="depositButton" value="Deposit"></div>
			</form>		
			</div>
	</div>
</div>
<%@include file="footer.jsp"%>
</html>