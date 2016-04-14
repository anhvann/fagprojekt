<!DOCTYPE html>
<html>
<div class="container">
	<%@include file="header.jsp"%>
	<div class="main">
		<form id=searchbar action="Search" method="post" target="_self">
			<input id=searchbutton type="submit" value="Search"	style="float: right" />
			<div style="overflow: hidden">
				<input id="searchfield" type="text" name="searchfield"
					placeholder="Search by name, cpr or account number"
					style="width: 100%"/>
			</div>
		</form>
	</div>
	<%@include file="footer.jsp"%>
</html>
