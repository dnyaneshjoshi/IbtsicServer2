package org.iiitb.ibtsic.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iiitb.ibtsic.action.dao.NodeDao;
import org.iiitb.ibtsic.action.dao.PathDao;
import org.iiitb.ibtsic.action.model.Path;
import org.iiitb.util.ConnectionPool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * Author: Joshi Dnyanesh Madhav
 * */
public class GetPathsConnectingTwoNodesAction extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			response.setContentType("text");
			PrintWriter pw=response.getWriter();
			Gson gson=new GsonBuilder().create();
			
			String node1Name=request.getParameter("node1Name");
			String node2Name=request.getParameter("node2Name");
			
			Connection cn=ConnectionPool.getConnection();
			NodeDao nodeDao=new NodeDao(cn);
			PathDao pathDao=new PathDao(cn);
			int node1Id=nodeDao.getNodeIdFromNodeName(node1Name);
			int node2Id=nodeDao.getNodeIdFromNodeName(node2Name);
			if(node1Id!=-1 && node2Id!=-1)
			{
				List<Path> pathList=pathDao.getPathsConnectingTwoNodes(node1Id, node2Id);
				List<String> departureTimesList=new ArrayList<String>();
				List<String> departureDurationsList=new ArrayList<String>();
				for(Path p:pathList)
				{
					departureTimesList.add(pathDao.getNextDepartureTime(p.id, node1Id));
					departureDurationsList.add(pathDao.getNextDepartureDuration(p.id, node1Id, node2Id));
				}
				pw.println(gson.toJson(pathList));
				pw.println(gson.toJson(departureTimesList));
				pw.println(gson.toJson(departureDurationsList));
			}
			ConnectionPool.freeConnection(cn);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
