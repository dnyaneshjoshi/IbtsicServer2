package org.iiitb.ibtsic.action.admin;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iiitb.ibtsic.action.dao.NodeDao;
import org.iiitb.ibtsic.action.model.Node;
import org.iiitb.util.ConnectionPool;

/*
 * Author: Joshi Dnyanesh Madhav
 * */
public class AddNodeAction extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException		
	{
		request.getRequestDispatcher("Node/AddNodePage.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException
	{
		try
		{
			Node node=new Node(-1,
				request.getParameter("name"),
				Double.parseDouble(request.getParameter("latitude")),
				Double.parseDouble(request.getParameter("longitude")));
			
			Connection cn=ConnectionPool.getConnection();
			NodeDao nodeDao=new NodeDao(cn);
			nodeDao.addNode(node);
			ConnectionPool.freeConnection(cn);
			
			request.setAttribute("message", "Success");
			doGet(request, response);
		}
		catch(Exception e)
		{
			request.setAttribute("message", "Failure");
			doGet(request, response);
		}
	}
}
