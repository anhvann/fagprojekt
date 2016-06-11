<!DOCTYPE HTML>
<html>
<div id="wrap">
	<%@include file="clientcheck.jsp" %>
	<%@ page import="java.util.LinkedList"%>
	<%@ page import="model.Account" %>
	<div class="main">
		<div class="pagetitle">Transfer money</div>
		<hr width="95%" noshade>
			<form class="form-inline" action="TransactionActivity?action=transfer" method="post" target="_self">
				<label class="control-label col-sm-5">Transaction name:</label>
				<div class="col-sm-5"><input type="text" class="form-control" name="transName" placeholder="Enter transaction name" required/></div>
				<br><br>
				<label class="control-label col-sm-5">Sending Account:</label>
				<div class="col-sm-5"><select class="form-control" name="accountID">
				<%LinkedList<Account> accounts = (LinkedList<Account>) request.getAttribute("accounts");%>
					<%for (Account acc : accounts){%>
						<option value="<%=acc.getAccountID()%>"><%=acc.getAccountID()%></option>
				<%}%>
  				</select>
  				</div>
  				
				<br><br>
				<label class="control-label col-sm-5">Receiving Account:</label>
				<div class="col-sm-5"><input type="text" class="form-control" name="accountID2" placeholder="Enter account ID" required/></div>
				<br><br>
				<label class="control-label col-sm-5">Amount:</label>
				<div class="col-sm-5"><input type="text" class="form-control" name="amount" placeholder="Enter amount" min="0" step="0.01" required/>
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
    				<option value="NZD">NCD</option>
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
				<input type="submit" class="btn btn-default" name="transferButton" value="Transfer"></div>
			</form>		
	</div>
</div>
<%@include file="footer.jsp"%>
</html>