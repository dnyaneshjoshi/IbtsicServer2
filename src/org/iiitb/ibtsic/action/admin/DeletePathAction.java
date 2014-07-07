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
import org.iiitb.ibtsic.action.dao.PathDao;
import org.iiitb.ibtsic.action.model.Node;
import org.iiitb.ibtsic.action.model.Path;
import org.iiitb.util.ConnectionPool;

/*
 * Author: Joshi Dnyanesh Madhav
 * */
public class DeletePathAction extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException		
	{
		try
		{
			Connection cn=ConnectionPool.getConnection();
			PathDao pathDao=new PathDao(cn);
			request.setAttribute("pathList", pathDao.getAllPaths());
			ConnectionPool.freeConnection(cn);
			
			request.getRequestDispatcher("Path/DeletePathPage.jsp").forward(request, response);
		}
		catch(Exception e)
		{
			request.setAttribute("pathList", new ArrayList<Path>());
			request.getRequestDispatcher("Path/DeletePathPage.jsp").forward(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException
	{
		try
		{
			Connection cn=ConnectionPool.getConnection();
			PathDao pathDao=new PathDao(cn);
			pathDao.deletePath(Integer.parseInt(request.getParameter("pathId")));
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
