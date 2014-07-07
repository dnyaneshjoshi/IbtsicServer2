package org.iiitb.ibtsic.action.admin;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iiitb.ibtsic.action.dao.BusDao;
import org.iiitb.ibtsic.action.dao.PathDao;
import org.iiitb.ibtsic.action.model.Bus;
import org.iiitb.util.ConnectionPool;

/*
 * Author: Joshi Dnyanesh Madhav
 * */
public class AddBusAction extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException		
	{
		try
		{
			Connection cn=ConnectionPool.getConnection();
			PathDao pathDao=new PathDao(cn);
			request.setAttribute("pathNameList", pathDao.getAllPathNames());
			ConnectionPool.freeConnection(cn);
			
			request.getRequestDispatcher("Bus/AddBusPage.jsp").forward(request, response);
		}
		catch(Exception e)
		{
			request.setAttribute("pathNameList", new ArrayList<String>());
			request.getRequestDispatcher("Bus/AddBusPage.jsp").forward(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException
	{
		try
		{
			Connection cn=ConnectionPool.getConnection();
			PathDao pathDao=new PathDao(cn);
			Bus bus=new Bus(-1, 
							request.getParameter("regNo"), 
							-1, 
							-1, 
							pathDao.getPathIdFromPathName(request.getParameter("pathName")+".onward"), 
							pathDao.getPathIdFromPathName(request.getParameter("pathName")+".return"),
							-1);
			
			BusDao busDao=new BusDao(cn);
			busDao.addBus(bus);
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
