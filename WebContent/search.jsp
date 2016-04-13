<!DOCTYPE html>
<html>
<div class="container">
	<%@include file="header.jsp"%>
	<div class="main">
		<div id="searchbar">
				<form id=searchform action="results" method="post" target="_self">
					<input id=searchbutton type="submit" value="Search"
						style="float: right" />
					<div style="overflow: hidden">
						<input id="searchbox" type="text" name="search" placeholder="Search by name, cpr or account number"
							style="width: 100%" maxlength="50" />
				</form>
			</div>
			<div id="table">
			<script>
				$(document).ready(function() {
					$("#submit").click(function() {
						var queryData = $("#input").val();
						$.ajax({
							type : 'GET',
							url : 'http://localhost:9080/Uniccol Bank/Search',
							data : {
								query : queryData
							},
							success : function(data) {
								$("#result").empty();
								$("#result").html(data);
							}
						});
					});
				});
			</script>
	</div>
	<%@include file="footer.jsp"%>
</div>
</html>
