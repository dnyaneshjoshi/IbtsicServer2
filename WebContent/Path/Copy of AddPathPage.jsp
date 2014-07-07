<%@page import="java.util.List"%>
<%@page import="org.iiitb.ibtsic.action.model.Node"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Joshi Dnyanesh Madhav -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add New Bus Route</title>
</head>
<body>
	<div><h1><a href="default.jsp" style="text-decoration: none; color: gray;">IBTSIC Admin</a></h1></div>
	<hr />
	<div style="float: left; width: 300px; border-right: medium; border-style: solid; border-bottom: none; border-left: none; border-top: none; color: gray; background-color: #efefef; height: 700px; padding-left: 10px;">
		<h4>Menu</h4>
		<a href="addNodeAction" style="text-decoration: none; color: gray;">Add New Bus Stop</a><br />
		<a href="editNodeAction" style="text-decoration: none; color: gray;">Edit Bus Stop Details</a><br />
		<a href="deleteNodeAction" style="text-decoration: none; color: gray;">Delete Bus Stop</a><br />
		<a href="addPathAction" style="text-decoration: none; color: gray;">Add New Bus Route &gt;</a><br />
		<a href="addBusAction" style="text-decoration: none; color: gray;">Add New Bus</a><br />
		<a href="editBusAction" style="text-decoration: none; color: gray;">Edit Bus Details</a><br />
		<a href="deleteBusAction" style="text-decoration: none; color: gray;">Delete Bus</a><br />
	</div>
	<div style="margin-left: 350px;">
		<h2>Add New Bus Route</h2>
		<form id="frmAddPath" action="addPathAction" method="post">
			<table>
				<tr>
					<td>Bus Route Name: </td>
					<td><input id="name" name="name" type="text" /></td>
				</tr>
			</table>
			<h4>Choose Bus Stops</h4>
			<div style="float: left;">
				<select id="l1" multiple="multiple" size="10" style="width: 300px;">
					<%for(Node node:(List<Node>)request.getAttribute("nodeList")) {%>
						<option value="<%=node.id%>"><%=node.name %></option>
					<%} %>
				</select>
			</div>
			<div style="float: left; margin-top: 70px;">
				<input type="button" value="->" onclick="onClick_l2r(document.getElementById('l1'), document.getElementById('l2'))" /><br />
				<input type="button" value="<-" onclick="onClick_r2l(document.getElementById('l1'), document.getElementById('l2'))" /><br />
			</div>
			<div style="float: left;">
				<select id="l2" name="nodesInPath" multiple="multiple" size="10" style="width: 300px;">
				</select>
			</div>
			<div style="margin-top: 95px;">
				<input type="button" value="^" onclick="onClick_moveUp(document.getElementById('l2'))" /><br />
				<input type="button" value="v" onclick="onClick_moveDn(document.getElementById('l2'))" /><br />
				<input type="button" value="*" onclick="onClick_selectAll(document.getElementById('l2'))" /><br />
			</div><br /><br />
			<h4>Add Run</h4>
			<div style="float: left;">
				<table>
					<tr>
						<td>Start Time: </td>
						<td><input id="t1" type="text" /></td>
					</tr>
					<tr>
						<td>End Time: </td>
						<td><input id="t2" type="text" /></td>
					</tr>
				</table>
			</div>
			<div style="float: left; margin-top: 70px;" align="center">
				<input type="button" value="->" onclick="onClick_t2s(document.getElementById('t1').value+' - '+document.getElementById('t2').value, document.getElementById('l4'))" /><br />
				<input type="button" value="-" onclick="onClick_del(document.getElementById('l4'))" /><br />
			</div>
			<div style="float: left;">
				<select id="l4" name="runsOnPath" multiple="multiple" size="10" style="width: 300px;">
				</select>
			</div>
			<div style="margin-top: 95px;">
				<input type="button" value="^" onclick="onClick_moveUp(document.getElementById('l4'))" /><br />
				<input type="button" value="v" onclick="onClick_moveDn(document.getElementById('l4'))" /><br />
				<input type="button" value="*" onclick="onClick_selectAll(document.getElementById('l4'))" /><br />
			</div><br /><br />
			<input type="button" value="Add" onclick="onClick_add()" />
		</form>
		<%if(request.getAttribute("message")!=null) {%>
			<%=request.getAttribute("message").toString() %>
		<%} %>
	</div>
</body>

<script type="text/javascript" src="behavior.js"></script>

<script type="text/javascript">
	function onClick_add()
	{
		if(document.getElementById('name').value=='')
			alert('Please enter the route name.');
		else if(l2.options.length<2)
			alert('Please choose at least 2 bus stops.');
		else if(l4.options.length<1)
			alert('Please add at least 1 run.');
		else
		{
			onClick_selectAll(l2);
			onClick_selectAll(l4);
			document.getElementById('frmAddPath').submit();
		}
	}
</script>
</html>