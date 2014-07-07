<%@page import="java.util.List"%>
<%@page import="org.iiitb.ibtsic.action.model.Node"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Joshi Dnyanesh Madhav -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit Bus Stop Details</title>
</head>
<body>
	<div><h1><a href="default.jsp" style="text-decoration: none; color: gray;">IBTSIC Admin</a></h1></div>
	<hr />
	<div style="float: left; width: 300px; color: #555555; background-color: #efefef; height: 100%; padding-left: 10px;">
		<h4>Menu</h4>
		<hr />
		<a href="addNodeAction" style="text-decoration: none; color: gray;">Add New Bus Stop</a><br />
		<a href="editNodeAction" style="text-decoration: none; color: gray;">Edit Bus Stop Details &gt;</a><br />
		<a href="deleteNodeAction" style="text-decoration: none; color: gray;">Delete Bus Stop</a><br />
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
		<h2>Edit Bus Stop Details</h2>
		<form id='frmEditNode' action="editNodeAction" method="post">
			<table>
				<tr>
					<td>Select Bus Stop to Edit: </td>
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
					<td>New Name: </td>
					<td><input id="name" name="name" type="text" /></td>
				</tr>
				<tr>
					<td>New Latitude: </td>
					<td><input id="latitude" name="latitude" type="text" /></td>
				</tr>
				<tr>
					<td>New Longitude: </td>
					<td><input id="longitude" name="longitude" type="text" /></td>
				</tr>
			</table><br />
			<input type="button" value="Done" onclick="onClick_done()" /><br />
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
	
	function onClick_done()
	{
		if(document.getElementById('nodeId').value=='')
			alert('Please select a bus stop.');
		else if(document.getElementById('name').value=='')
			alert('Name is empty.');
		else if(document.getElementById('latitude').value=='')
			alert('Latitude is empty.');
		else if(document.getElementById('longitude').value=='')
			alert('Longitude is empty.');
		else
			document.getElementById('frmEditNode').submit();
	}
</script>
</html>