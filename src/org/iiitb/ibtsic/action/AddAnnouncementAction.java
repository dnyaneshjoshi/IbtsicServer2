package org.iiitb.ibtsic.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iiitb.ibtsic.action.dao.AnnouncementDao;
import org.iiitb.ibtsic.action.dao.BusDao;
import org.iiitb.ibtsic.action.dao.NodeDao;
import org.iiitb.util.ConnectionPool;

/*
 * Author: Joshi Dnyanesh Madhav
 * */
public class AddAnnouncementAction extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			String name=request.getParameter("name");
			String description=request.getParameter("description");
			String node1Name=request.getParameter("node1Name");
			String node2Name=request.getParameter("node2Name");
			String validity=request.getParameter("validity");
			
			Connection cn=ConnectionPool.getConnection();
			AnnouncementDao announcementDao=new AnnouncementDao(cn);
			NodeDao nodeDao=new NodeDao(cn);
			int node1Id=nodeDao.getNodeIdFromNodeName(node1Name);
			int node2Id=nodeDao.getNodeIdFromNodeName(node2Name);
			if(node1Id!=-1 && node2Id!=-1)
				announcementDao.addAnnouncement(name, description, node1Id, node2Id, validity);
			ConnectionPool.freeConnection(cn);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
