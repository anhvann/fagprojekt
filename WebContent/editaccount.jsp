<!DOCTYPE HTML><%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<div class="container">
	<%@include file="employeeheader.jsp"%>

	<div class="main" >
			<div class="pagetitle">Edit Account</div>
			<hr width="95%" noshade>
				<form action="Activity?ID=${cpr}&action=changeaccount&accountID=<%=request.getParameter("accountID")%>" method="post" target="_self">
					<label class="control-label col-sm-5">Name:</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" name="name" placeholder="Enter name" value=${name}>
					</div>
					<br><br>
					
					<label class="control-label col-sm-5">Interest:</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" name="interest" placeholder="Enter interest" value="${interest}">
					</div>
					<br><br>
					
					<label class="control-label col-sm-5">Status:</label>
					<div class="col-sm-5">
						<input type="radio" name="status" value="1"> Active 
						<input type="radio" name="status" value="0"> Inactive
					</div>
					<br><br>
					
					<div class="col-sm-offset-5 col-sm-5">
						<input type="submit" class="btn btn-default" name="saveChanges" value="Save Changes">
					</div>
				</form>	
			</hr>
	</div>
</div>
<%@include file="footer.jsp"%>
</html>