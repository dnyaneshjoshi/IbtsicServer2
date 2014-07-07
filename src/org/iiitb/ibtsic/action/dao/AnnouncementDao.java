package org.iiitb.ibtsic.action.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.iiitb.ibtsic.action.model.Announcement;
import org.iiitb.ibtsic.action.model.Bus;
import org.iiitb.ibtsic.action.model.Node;
import org.iiitb.ibtsic.action.model.Path;

/*
 * Author: Joshi Dnyanesh Madhav
 * */
public class AnnouncementDao
{
	private static final String GET_ALL_ACTIVE_ANNOUNCEMENTS=
			"select * from Announcement where TIMESTAMPADD(SECOND, time_to_sec(validity), startInstant)>CURRENT_TIMESTAMP;";
	
	private static final String ADD_ANNOUNCEMENT_QUERY=
			"insert into Announcement(name, description, startInstant, validity, node1Id, node2Id) values(?, ?, CURRENT_TIMESTAMP, ?, ?, ?);";
	
	private Connection cn;
	
	public AnnouncementDao(Connection cn)
	{
		this.cn=cn;
	}
	
	public List<Announcement> getAllActiveAnnouncements() throws SQLException 
	{
		List<Announcement> r=new ArrayList<Announcement>();
		PreparedStatement ps=cn.prepareStatement(GET_ALL_ACTIVE_ANNOUNCEMENTS);
		ResultSet rs=ps.executeQuery();
		while(rs.next())
			r.add(new Announcement(rs.getInt("id"),
								rs.getString("name"), 
								rs.getString("description"), 
								rs.getString("startInstant"), 
								rs.getString("validity"), 
								rs.getInt("node1Id"), 
								rs.getInt("node2Id")));
		rs.close();
		ps.close();
		return r;
	}
	
	public List<Announcement> getActiveAnnouncementsForPath(String pathName) throws SQLException
	{
		List<Announcement> r=new ArrayList<Announcement>();
		PathDao pathDao=new PathDao(cn);
		for(Announcement a:this.getAllActiveAnnouncements())
			for(Path p:pathDao.getPathsConnectingTwoNodes(a.node1Id, a.node2Id))
				if(pathName.equals(p.name))
				{
					r.add(a);
					break;
				}
		return r;
	}
	
	public void addAnnouncement(String name, String description, int node1Id, int node2Id, String validity)
		throws SQLException
	{
		PreparedStatement ps=cn.prepareStatement(ADD_ANNOUNCEMENT_QUERY);
		int ind=0;
		ps.setString(++ind, name);
		ps.setString(++ind, description);
		ps.setString(++ind, validity);
		ps.setInt(++ind, node1Id);
		ps.setInt(++ind, node2Id);
		ps.executeUpdate();
		ps.close();
	}
}
