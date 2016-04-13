<!DOCTYPE html>
<%@include file="header.jsp"%>
<body>
	<div class="container">
		<div id="searchbar">
			<form id=searchform action="results" method="post" target="_self">
				<input type="submit" value="Search" style="float: right" />
				<div style="overflow: hidden">
					<input type="text" name="search" id="search"
						placeholder="Search by name, cpr or account number"	style="width: 100%" />
				</div>
			</form>
		</div>
</body>
<%@include file="footer.jsp"%>
</html>