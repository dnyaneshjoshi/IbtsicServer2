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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * Author: Joshi Dnyanesh Madhav
 * */
public class GetNodesInPathAction extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			response.setContentType("text");
			PrintWriter pw=response.getWriter();
			Gson gson=new GsonBuilder().create();
			
			String pathName=request.getParameter("pathName");
			
			Connection cn=ConnectionPool.getConnection();
			PathDao pathDao=new PathDao(cn);
			int pathId=pathDao.getPathIdFromPathName(pathName);
			if(pathId!=-1)
				pw.println(gson.toJson(pathDao.getNodesInPath(pathId)));
			ConnectionPool.freeConnection(cn);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
