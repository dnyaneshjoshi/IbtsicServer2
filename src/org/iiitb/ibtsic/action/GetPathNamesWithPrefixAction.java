package org.iiitb.ibtsic.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iiitb.ibtsic.action.dao.PathDao;
import org.iiitb.util.ConnectionPool;

/*
 * Author: Joshi Dnyanesh Madhav
 * */
public class GetPathNamesWithPrefixAction extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			response.setContentType("text");
			PrintWriter pw=response.getWriter();
			
			String pathNamePrefix=request.getParameter("pathNamePrefix");
			
			Connection cn=ConnectionPool.getConnection();
			PathDao pathDao=new PathDao(cn);
			for(String s:pathDao.getPathNamesWithPrefix(pathNamePrefix))
				pw.println(s);
			ConnectionPool.freeConnection(cn);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
