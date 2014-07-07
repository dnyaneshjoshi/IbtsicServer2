<%@page import="java.util.List"%>
<%@page import="org.iiitb.ibtsic.action.model.Node"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Joshi Dnyanesh Madhav -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Delete Bus Stop</title>
</head>
<body>
	<div><h1><a href="default.jsp" style="text-decoration: none; color: gray;">IBTSIC Admin</a></h1></div>
	<hr />
	<div style="float: left; width: 300px; color: #555555; background-color: #efefef; height: 100%; padding-left: 10px;">
		<h4>Menu</h4>
		<hr />
		<a href="addNodeAction" style="text-decoration: none; color: gray;">Add New Bus Stop</a><br />
		<a href="editNodeAction" style="text-decoration: none; color: gray;">Edit Bus Stop Details</a><br />
		<a href="deleteNodeAction" style="text-decoration: none; color: gray;">Delete Bus Stop &gt;</a><br />
		<hr />
		<a href="addPathAction" style="text-decoration: none; color: gray;">Add New Bus Route</a><br />
		<a href="deletePathAction" style="text-decoration: none; color: gray;">Delete Bus Route</a><br />
		<hr />
		<a href="addBusAction" style="text-decoration: none; color: gray;">Add New Bus</a><br />
		<a href="editBusAction" style="text-decoration: none; color: gray;">Edit Bus Details</a><br />
		<a href="deleteBusAction" style="text-decoration: none; color: gray;">Delete Bus</a><br />
		<hr />
	</div>
	<div style="margin-left: 350px;">
		<h2>Delete Bus Stop</h2>
		<form id='frmDeleteNode' action="deleteNodeAction" method="post">
			<table>
				<tr>
					<td>Select Bus Stop to Delete: </td>
					<td>
						<select id="nodeId" name="nodeId" onchange="onChange_node(this)">
							<option></option>
							<%for(Node node:(List<Node>)request.getAttribute("nodeList")) {%>
								<option value="<%=node.id+"|"+node.name+"|"+node.latitude+"|"+node.longitude %>"><%=node.name %></option>
							<%} %>
						</select>
					</td>
				</tr>
				<tr>
					<td>Name: </td>
					<td><input id="name" name="name" type="text" disabled="disabled" /></td>
				</tr>
				<tr>
					<td>Latitude: </td>
					<td><input id="latitude" name="latitude" type="text" disabled="disabled" /></td>
				</tr>
				<tr>
					<td>Longitude: </td>
					<td><input id="longitude" name="longitude" type="text" disabled="disabled" /></td>
				</tr>
			</table><br />
			<input type="button" value="Delete" onclick="onClick_delete()" /><br />
		</form>
		<%if(request.getAttribute("message")!=null) {%>
			<%=request.getAttribute("message").toString() %>
		<%} %>
	</div>
</body>
<script type="text/javascript">
	function onChange_node(l)
	{
		var a=l.value.split('|');
		document.getElementById('name').value=a[1];
		document.getElementById('latitude').value=a[2];
		document.getElementById('longitude').value=a[3];
	}
	
	function onClick_delete()
	{
		if(document.getElementById('nodeId').value=='')
			alert('Please select a bus stop.');
		else
			document.getElementById('frmDeleteNode').submit();
	}
</script>
</html>