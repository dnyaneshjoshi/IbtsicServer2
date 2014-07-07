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
import org.iiitb.ibtsic.action.model.Run;
import org.iiitb.util.ConnectionPool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * Author: Joshi Dnyanesh Madhav
 * */
public class AddPathAction extends HttpServlet
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
			
			request.getRequestDispatcher("Path/AddPathPage.jsp").forward(request, response);
		}
		catch(Exception e)
		{
			request.setAttribute("nodeList", new ArrayList<Node>());
			request.getRequestDispatcher("Path/AddPathPage.jsp").forward(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException
	{
		try
		{
			int i=0;
			List<Integer> nodeIdList=new ArrayList<Integer>();
			for(String s:request.getParameterValues("nodesInPath"))
				nodeIdList.add(Integer.parseInt(s));
			
			Gson gson=new GsonBuilder().create();
			String[] arrivalTimes=gson.fromJson(request.getParameter("arrivalTimes"), String[].class);
			String[] departureTimes=gson.fromJson(request.getParameter("departureTimes"), String[].class);
			String[] distances=gson.fromJson(request.getParameter("distances"), String[].class);
			
			Connection cn=ConnectionPool.getConnection();
			PathDao pathDao=new PathDao(cn);
			int pathId=pathDao.addPath(new Path(-1, request.getParameter("name")));
			if(pathId!=-1)
			{
				pathDao.addNodesToPath(pathId, nodeIdList, arrivalTimes, departureTimes, distances);
				
				List<Run> runList=new ArrayList<Run>();
				for(String s:request.getParameterValues("runsOnPath"))
				{
					String startTime=s.split("-")[0].trim();
					String endTime=s.split("-")[1].trim();
					runList.add(new Run(-1,
										-1,
										startTime,
										endTime,
										pathId));
				}
				pathDao.addRunsOnPath(pathId, runList);
			}
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
