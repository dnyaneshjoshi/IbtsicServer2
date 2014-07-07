<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Joshi Dnyanesh Madhav -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add New Bus</title>
</head>
<body>
	<div><h1><a href="default.jsp" style="text-decoration: none; color: gray;">IBTSIC Admin</a></h1></div>
	<hr />
	<div style="float: left; width: 300px; color: #555555; background-color: #efefef; height: 100%; padding-left: 10px;">
		<h4>Menu</h4>
		<hr />
		<a href="addNodeAction" style="text-decoration: none; color: gray;">Add New Bus Stop</a><br />
		<a href="editNodeAction" style="text-decoration: none; color: gray;">Edit Bus Stop Details</a><br />
		<a href="deleteNodeAction" style="text-decoration: none; color: gray;">Delete Bus Stop</a><br />
		<hr />
		<a href="addPathAction" style="text-decoration: none; color: gray;">Add New Bus Route</a><br />
		<a href="deletePathAction" style="text-decoration: none; color: gray;">Delete Bus Route</a><br />
		<hr />
		<a href="addBusAction" style="text-decoration: none; color: gray;">Add New Bus &gt;</a><br />
		<a href="editBusAction" style="text-decoration: none; color: gray;">Edit Bus Details</a><br />
		<a href="deleteBusAction" style="text-decoration: none; color: gray;">Delete Bus</a><br />
		<hr />
	</div>
	<div style="margin-left: 350px;">
		<h2>Add New Bus</h2>
		<form id='frmAddBus' action="addBusAction" method="post">
			<table>
				<tr>
					<td>Bus Registration No.: </td>
					<td><input id="regNo" name="regNo" type="text" /></td>
				</tr>
				<tr>
					<td>Route Name: </td>
					<td>
						<select name='pathName'>
							<%for(String pathName:(List<String>)request.getAttribute("pathNameList")) {%>
								<option><%=pathName %></option>
							<%} %>
						</select>
					</td>
				</tr>
			</table><br />
			<input type="button" value="Add" onclick="onClick_add()" /><br />
		</form>
		<%if(request.getAttribute("message")!=null) {%>
			<%=request.getAttribute("message").toString() %>
		<%} %>
	</div>
</body>
<script type="text/javascript">
	function onClick_add()
	{
		if(document.getElementById('regNo').value=='')
			alert('Bus Registration No. is empty.');
		else
			document.getElementById('frmAddBus').submit();
	}
</script>
</html>