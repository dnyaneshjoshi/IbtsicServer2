package org.iiitb.ibtsic.action.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.iiitb.ibtsic.action.model.Bus;
import org.iiitb.ibtsic.action.model.Node;

/*
 * Author: Joshi Dnyanesh Madhav
 * */
public class BusDao
{
	private static final String SET_BUS_LOCATION_AND_DIRECTION_QUERY=
			"update Bus set latitude=?, longitude=?, currentPathId=? where regNo=?;"; 
	
	private static final String ADD_BUS_QUERY=
			"insert into Bus(regNo, onwardPathId, returnPathId) values(?, ?, ?);";
	
	private static final String GET_MAX_BUS_ID_QUERY=
			"select max(id) from Bus;";
	
	private static final String GET_ALL_BUSES_QUERY=
			"select * from Bus;";
	
	private static final String SET_BUS_DETAILS_QUERY=
			"update Bus set regNo=?, onwardPathId=?, returnPathId=? where id=?;";
	
	private static final String DELETE_BUS_QUERY=
			"delete from Bus where id=?;";
	
	private static final String GET_BUS_BY_REGNO_QUERY=
			"select * from Bus where regNo=?;";
	
	private Connection cn;
	
	public BusDao(Connection cn)
	{
		this.cn=cn;
	}
	
	public void setBusLocationAndDirection(String regNo, double latitude, double longitude) throws SQLException
	{
		int ind=0;
		PreparedStatement ps=cn.prepareStatement(SET_BUS_LOCATION_AND_DIRECTION_QUERY);
		
		Bus bus=getBusByRegNo(regNo);
		if(bus!=null)
		{
			PathDao pathDao=new PathDao(cn);
			Node n=pathDao.getSourceNodeOfPath(bus.onwardPathId);
			
			Double d1=Math.sqrt(Math.pow((bus.latitude-n.latitude), 2)+Math.pow((bus.longitude-n.longitude), 2));
			Double d2=Math.sqrt(Math.pow((latitude-n.latitude), 2)+Math.pow((longitude-n.longitude), 2));
			
			ps.setDouble(++ind, latitude);
			ps.setDouble(++ind, longitude);
			if(d2>d1)
				ps.setInt(++ind, bus.onwardPathId);
			else if(d2<d1)
				ps.setInt(++ind, bus.returnPathId);
			else
				ps.setInt(++ind, bus.currentPathId);
		}
		
		ps.executeUpdate();
		ps.close();
	}
	
	public int addBus(Bus bus) throws SQLException
	{
		int ind=0, r=-1;
		PreparedStatement ps=cn.prepareStatement(ADD_BUS_QUERY);
		ps.setString(++ind, bus.regNo);
		ps.setInt(++ind, bus.onwardPathId);
		ps.setInt(++ind, bus.returnPathId);
		ps.executeUpdate();
		ps.close();
		ps=cn.prepareStatement(GET_MAX_BUS_ID_QUERY);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
			r=rs.getInt(1);
		rs.close();
		ps.close();
		return r;
	}
	
	public List<Bus> getAllBuses() throws SQLException
	{
		List<Bus> r=new ArrayList<Bus>();
		PreparedStatement ps=cn.prepareStatement(GET_ALL_BUSES_QUERY);
		ResultSet rs=ps.executeQuery();
		while(rs.next())
			r.add(new Bus(rs.getInt("id"), 
						rs.getString("regNo"), 
						rs.getDouble("latitude"), 
						rs.getDouble("longitude"), 
						rs.getInt("onwardPathId"), 
						rs.getInt("returnPathId"),
						rs.getInt("currentPathId")));
		rs.close();
		ps.close();
		return r;
	}
	
	public void setBusDetails(Bus bus) throws SQLException
	{
		PreparedStatement ps=cn.prepareStatement(SET_BUS_DETAILS_QUERY);
		int ind=0;
		ps.setString(++ind, bus.regNo);
		ps.setInt(++ind, bus.onwardPathId);
		ps.setInt(++ind, bus.returnPathId);
		ps.setInt(++ind, bus.id);
		ps.executeUpdate();
		ps.close();
	}
	
	public void deleteBus(int busId) throws SQLException
	{
		PreparedStatement ps=cn.prepareStatement(DELETE_BUS_QUERY);
		ps.setInt(1, busId);
		ps.executeUpdate();
		ps.close();
	}
	
	public Bus getBusByRegNo(String regNo) throws SQLException
	{
		Bus bus=null;
		PreparedStatement ps=cn.prepareStatement(GET_BUS_BY_REGNO_QUERY);
		ps.setString(1, regNo);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
			bus=new Bus(rs.getInt("id"), 
					rs.getString("regNo"), 
					rs.getDouble("latitude"), 
					rs.getDouble("longitude"), 
					rs.getInt("onwardPathId"), 
					rs.getInt("returnPathId"),
					rs.getInt("currentPathId"));
		rs.close();
		ps.close();
		return bus;
	}
}
