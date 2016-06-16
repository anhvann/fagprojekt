<!DOCTYPE HTML><%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<div id="wrap">
	<%@include file="employeecheck.jsp" %>
	<div class="main">
		<div class="pagetitle">Create new account</div>
		<hr width="95%" noshade>
			<form action="AccountActivity?ID=${cpr}&action=createaccount" method="post" target="_self">
				<label class="control-label col-sm-5">Name:</label>
				<div class="col-sm-5"><input type="text" class="form-control" name="accountName" placeholder="Enter name" required/></div>
				<br><br>
				<label class="control-label col-sm-5">Interest (%):</label>
				<div class="col-sm-5"><input type="number" step="0.01"  min="0" max="100" class="form-control" name="interest" placeholder="Enter interest" required/></div>
				<br><br>
				<label class="control-label col-sm-5">Currency:</label>
				<div class="col-sm-5">
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
				<div class="col-sm-offset-5 col-sm-5">
				<input type="submit" class="btn btn-default" name="createButton" value="Create">
				</div>
			</form>		
	</div>
</div>
<%@include file="footer.jsp"%>
</html>