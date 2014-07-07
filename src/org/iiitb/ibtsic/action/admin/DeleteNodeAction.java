package org.iiitb.ibtsic.action.admin;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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
public class DeleteNodeAction extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException		
	{
		try
		{
			Connection cn=ConnectionPool.getConnection();
			NodeDao nodeDao=new NodeDao(cn);
			request.setAttribute("nodeList", nodeDao.getAllNodes());
			ConnectionPool.freeConnection(cn);
			
			request.getRequestDispatcher("Node/DeleteNodePage.jsp").forward(request, response);
		}
		catch(Exception e)
		{
			request.setAttribute("nodeList", new ArrayList<Node>());
			request.getRequestDispatcher("Node/DeleteNodePage.jsp").forward(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException
	{
		try
		{
			Connection cn=ConnectionPool.getConnection();
			NodeDao nodeDao=new NodeDao(cn);
			nodeDao.deleteNode(Integer.parseInt(request.getParameter("nodeId").split("[|]")[0]));
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
