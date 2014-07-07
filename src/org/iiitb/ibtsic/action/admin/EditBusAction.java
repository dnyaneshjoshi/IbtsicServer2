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
public class EditBusAction extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException		
	{
		try
		{
			Connection cn=ConnectionPool.getConnection();
			BusDao busDao=new BusDao(cn);
			request.setAttribute("busList", busDao.getAllBuses());
			PathDao pathDao=new PathDao(cn);
			request.setAttribute("pathList", pathDao.getAllOnwardPaths());
			ConnectionPool.freeConnection(cn);
			
			request.getRequestDispatcher("Bus/EditBusPage.jsp").forward(request, response);
		}
		catch(Exception e)
		{
			request.setAttribute("busList", new ArrayList<Bus>());
			request.getRequestDispatcher("Bus/EditBusPage.jsp").forward(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException
	{
		try
		{
			Connection cn=ConnectionPool.getConnection();
			PathDao pathDao=new PathDao(cn);
			
			Bus bus=new Bus(Integer.parseInt(request.getParameter("busId").split("[|]")[0]), 
							request.getParameter("regNo"), 
							-1, 
							-1, 
							pathDao.getPathIdFromPathName(request.getParameter("pathName")+".onward"), 
							pathDao.getPathIdFromPathName(request.getParameter("pathName")+".return"),
							-1);
			
			BusDao busDao=new BusDao(cn);
			busDao.setBusDetails(bus);
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
