package org.iiitb.ibtsic.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iiitb.ibtsic.action.dao.BusDao;
import org.iiitb.util.ConnectionPool;

/*
 * Author: Joshi Dnyanesh Madhav
 * */
public class SetBusLocationAction extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			String regNo=request.getParameter("regNo");
			Double latitude=Double.parseDouble(request.getParameter("latitude"));
			Double longitude=Double.parseDouble(request.getParameter("longitude"));
			
			Connection cn=ConnectionPool.getConnection();
			BusDao busDao=new BusDao(cn);
			busDao.setBusLocationAndDirection(regNo, latitude, longitude);
			ConnectionPool.freeConnection(cn);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
