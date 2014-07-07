package org.iiitb.ibtsic.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iiitb.ibtsic.action.dao.NodeDao;
import org.iiitb.util.ConnectionPool;

/*
 * Author: Joshi Dnyanesh Madhav
 * */
public class GetNodeNamesWithPrefixAction extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			response.setContentType("text");
			PrintWriter pw=response.getWriter();
			
			String nodeNamePrefix=request.getParameter("nodeNamePrefix");
			
			Connection cn=ConnectionPool.getConnection();
			NodeDao nodeDao=new NodeDao(cn);
			for(String s:nodeDao.getNodeNamesWithPrefix(nodeNamePrefix))
				pw.println(s);
			ConnectionPool.freeConnection(cn);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
