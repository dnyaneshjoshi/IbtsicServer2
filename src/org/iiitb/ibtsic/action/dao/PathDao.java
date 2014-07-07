package org.iiitb.ibtsic.action.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.iiitb.ibtsic.action.model.Bus;
import org.iiitb.ibtsic.action.model.Node;
import org.iiitb.ibtsic.action.model.Path;
import org.iiitb.ibtsic.action.model.Run;

/*
 * Author: Joshi Dnyanesh Madhav
 * */
public class PathDao
{
	private static final String GET_NTH_NODE_IN_PATH_QUERY=
			"select Node.* from Node, PathNode, Path where Node.id=PathNode.nodeId and PathNode.pathId=Path.id and Path.name=? and PathNode.seqNo=?;";
	
	private static final String GET_NTH_NODE_IN_PATH_BY_PATHID_QUERY=
			"select Node.* from Node, PathNode, Path where Node.id=PathNode.nodeId and PathNode.pathId=Path.id and Path.id=? and PathNode.seqNo=?;";
	
	private static final String GET_NODE_COUNT_IN_PATH_QUERY=
			"select max(seqNo) from PathNode, Path where PathNode.pathId=Path.id and Path.name=?;";
	
	private static final String GET_NODE_COUNT_IN_PATH_BY_PATHID_QUERY=
			"select max(seqNo) from PathNode, Path where PathNode.pathId=Path.id and Path.id=?;";
	
	private static final String GET_ALL_RUNS_ON_PATH=
			"select Run.* from Run, Path where Run.pathId=Path.id and Path.name=? order by Run.number;";
	
	private static final String ADD_PATH_QUERY=
			"insert into Path(name) values(?);";
	
	private static final String GET_MAX_PATH_ID_QUERY=
			"select max(id) from Path;";
	
	private static final String DELETE_ALL_NODES_IN_PATH_QUERY=
			"delete from PathNode where pathId=?;";
	
	private static final String ADD_NODE_TO_PATH_QUERY=
			"insert into PathNode(pathId, nodeId, seqNo, arrivalTime, departureTime, distance) values(?, ?, ?, ?, ?, ?);";
	
	private static final String DELETE_ALL_RUNS_ON_PATH_QUERY=
			"delete from Run where pathId=?;";
	
	private static final String ADD_RUN_ON_PATH_QUERY=
			"insert into Run(number, startTime, endTime, pathId) values(?, ?, ?, ?);";
	
	private static final String GET_ALL_PATHNAMES_QUERY=
			"select distinct(substring_index(name, '.', 1)) name from Path order by name;";
	
	private static final String GET_PATHID_FROM_PATHNAME_QUERY=
			"select id from Path where name=?;";
	
	private static final String GET_ALL_ONWARD_PATHS_QUERY=
			"select * from Path where name like '%.onward' order by name;";
	
	private static final String GET_PATHNAMES_WITH_PREFIX_QUERY=
			"select distinct(substring_index(name, '.', 1)) name from Path where name like ? order by name;";
	
	private static final String GET_NODES_IN_PATH_QUERY=
			"select Node.* from Node, PathNode, Path where Node.id=PathNode.nodeId and PathNode.pathId=Path.id and Path.id=? order by PathNode.seqNo;";
	
	private static final String GET_PATHS_CONNECTING_TWO_NODES_QUERY=
			"select c.* from (select * from PathNode where nodeId=?) a, (select * from PathNode where nodeId=?) b, Path c where a.pathId=b.pathId and a.seqNo<b.seqNo and c.id=a.pathId;";
	
	private static final String GET_NEXT_DEPARTURE_TIME_QUERY=
			"select min(a.t) from (select addtime(Run.startTime, sec_to_time(time_to_sec(PathNode.departureTime)*time_to_sec(subtime(Run.endTime, Run.startTime))/time_to_sec(pn.arrivalTime))) t from PathNode, Run, PathNode pn where PathNode.pathId=? and PathNode.nodeId=? and Run.pathId=PathNode.pathId and pn.seqNo=(select max(seqNo) from PathNode where pathId=pn.PathId) and pn.pathId=PathNode.pathId) a where a.t>curtime();";
	
	private static final String GET_NEXT_DEPARTURE_DURATION_QUERY=
			"select min(a.dur) from (select a.*, b.dt, c.dAt, subtime(c.dAt, b.dt) dur from (select Run.id, addtime(Run.startTime, sec_to_time(time_to_sec(PathNode.arrivalTime)*time_to_sec(subtime(Run.endTime, Run.startTime))/time_to_sec(pn.arrivalTime))) at from PathNode, Run, PathNode pn where PathNode.pathId=? and PathNode.nodeId=? and Run.pathId=PathNode.pathId and pn.seqNo=(select max(seqNo) from PathNode where pathId=pn.PathId) and pn.pathId=PathNode.pathId) a, (select Run.id, addtime(Run.startTime, sec_to_time(time_to_sec(PathNode.departureTime)*time_to_sec(subtime(Run.endTime, Run.startTime))/time_to_sec(pn.arrivalTime))) dt from PathNode, Run, PathNode pn where PathNode.pathId=? and PathNode.nodeId=? and Run.pathId=PathNode.pathId and pn.seqNo=(select max(seqNo) from PathNode where pathId=pn.PathId) and pn.pathId=PathNode.pathId) b, (select Run.id, addtime(Run.startTime, sec_to_time(time_to_sec(PathNode.arrivalTime)*time_to_sec(subtime(Run.endTime, Run.startTime))/time_to_sec(pn.arrivalTime))) dAt from PathNode, Run, PathNode pn where PathNode.pathId=? and PathNode.nodeId=? and Run.pathId=PathNode.pathId and pn.seqNo=(select max(seqNo) from PathNode where pathId=pn.PathId) and pn.pathId=PathNode.pathId) c where a.id=b.id and b.id=c.id) a where a.dt>curtime();";
	
	private static final String GET_RT_BUSES_ON_PATH_QUERY=
			"select * from Bus where currentPathId=?;";
	
	private static final String DELETE_PATH_QUERY=
			"delete from Path where id=?;";
	
	private static final String GET_ALL_PATHS_QUERY=
			"select * from Path order by name;";
	
	private Connection cn;
	
	public PathDao(Connection cn)
	{
		this.cn=cn;
	}
	
	public Node getNthNodeInPath(String pathName, int n) throws SQLException
	{
		Node r=null;
		PreparedStatement ps=cn.prepareStatement(GET_NTH_NODE_IN_PATH_QUERY);
		int ind=0;
		ps.setString(++ind, pathName);
		ps.setInt(++ind, n);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
			r=new Node(rs.getInt("id"),
					rs.getString("name"), 
					rs.getDouble("latitude"), 
					rs.getDouble("longitude"));

		rs.close();
		ps.close();
		return r;
	}
	
	public Node getNthNodeInPath(int pathId, int n) throws SQLException
	{
		Node r=null;
		PreparedStatement ps=cn.prepareStatement(GET_NTH_NODE_IN_PATH_BY_PATHID_QUERY);
		int ind=0;
		ps.setInt(++ind, pathId);
		ps.setInt(++ind, n);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
			r=new Node(rs.getInt("id"),
					rs.getString("name"), 
					rs.getDouble("latitude"), 
					rs.getDouble("longitude"));

		rs.close();
		ps.close();
		return r;
	}
	
	public int getNodeCountInPath(String pathName) throws SQLException
	{
		int r=-1;
		PreparedStatement ps=cn.prepareStatement(GET_NODE_COUNT_IN_PATH_QUERY);
		ps.setString(1, pathName);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
			r=rs.getInt(1);
		rs.close();
		ps.close();
		return r;
	}
	
	public int getNodeCountInPath(int pathId) throws SQLException
	{
		int r=-1;
		PreparedStatement ps=cn.prepareStatement(GET_NODE_COUNT_IN_PATH_BY_PATHID_QUERY);
		ps.setInt(1, pathId);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
			r=rs.getInt(1);
		rs.close();
		ps.close();
		return r;
	}
	
	public Node getSourceNodeOfPath(String pathName) throws SQLException
	{
		return getNthNodeInPath(pathName, 1);
	}
	
	public Node getSourceNodeOfPath(int pathId) throws SQLException
	{
		return getNthNodeInPath(pathId, 1);
	}
	
	public Node getDestinationNodeOfPath(String pathName) throws SQLException
	{
		return getNthNodeInPath(pathName, getNodeCountInPath(pathName));
	}
	
	public Node getDestinationNodeOfPath(int pathId) throws SQLException
	{
		return getNthNodeInPath(pathId, getNodeCountInPath(pathId));
	}
	
	public List<Run> getAllRunsOnPath(String pathName) throws SQLException
	{
		List<Run> r=new ArrayList<Run>();
		PreparedStatement ps=cn.prepareStatement(GET_ALL_RUNS_ON_PATH);
		ps.setString(1, pathName);
		ResultSet rs=ps.executeQuery();
		while(rs.next())
			r.add(new Run(rs.getInt("id"),
					rs.getInt("number"), 
					rs.getString("startTime"), 
					rs.getString("endTime"),
					rs.getInt("pathId")));
		rs.close();
		ps.close();
		return r;
	}
	
	public int addPath(Path path) throws SQLException
	{
		int r=-1;
		PreparedStatement ps=cn.prepareStatement(ADD_PATH_QUERY);
		ps.setString(1, path.name);
		ps.executeUpdate();
		ps.close();
		ps=cn.prepareStatement(GET_MAX_PATH_ID_QUERY);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
			r=rs.getInt(1);
		rs.close();
		ps.close();
		return r;
	}
	
	public void deleteAllNodesInPath(int pathId) throws SQLException
	{
		PreparedStatement ps=cn.prepareStatement(DELETE_ALL_NODES_IN_PATH_QUERY);
		ps.setInt(1, pathId);
		ps.executeUpdate();
		ps.close();
	}
	
	public int addNodesToPath(int pathId, List<Integer> nodeIdList, String[] arrivalTimes, 
			String[] departureTimes, String[] distances) throws SQLException
	{
		deleteAllNodesInPath(pathId);
		
		PreparedStatement ps=cn.prepareStatement(ADD_NODE_TO_PATH_QUERY);
		int seqNo=0;
		for(int nodeId:nodeIdList)
		{
			ps.setInt(1, pathId);
			ps.setInt(2, nodeId);
			ps.setString(4, arrivalTimes[seqNo]);
			ps.setString(5, departureTimes[seqNo]);
			ps.setString(6, distances[seqNo]);
			ps.setInt(3, ++seqNo);
			ps.executeUpdate();
		}
		ps.close();
		return seqNo;
	}
	
	public void deleteAllRunsOnPath(int pathId) throws SQLException
	{
		PreparedStatement ps=cn.prepareStatement(DELETE_ALL_RUNS_ON_PATH_QUERY);
		ps.setInt(1, pathId);
		ps.executeUpdate();
		ps.close();
	}
	
	public int addRunsOnPath(int pathId, List<Run> runList) throws SQLException
	{
		deleteAllRunsOnPath(pathId);
		
		PreparedStatement ps=cn.prepareStatement(ADD_RUN_ON_PATH_QUERY);
		int seqNo=0;
		for(Run run:runList)
		{
			ps.setInt(1, ++seqNo);
			ps.setString(2, run.startTime);
			ps.setString(3, run.endTime);
			ps.setInt(4, run.pathId);
			ps.executeUpdate();
		}
		ps.close();
		return seqNo;
	}
	
	public List<String> getAllPathNames() throws SQLException
	{
		PreparedStatement ps=cn.prepareStatement(GET_ALL_PATHNAMES_QUERY);
		ResultSet rs=ps.executeQuery();
		List<String> r=new ArrayList<String>();
		while(rs.next())
			r.add(rs.getString(1));
		rs.close();
		ps.close();
		return r;
	}
	
	public int getPathIdFromPathName(String pathName) throws SQLException
	{
		int r=-1;
		PreparedStatement ps=cn.prepareStatement(GET_PATHID_FROM_PATHNAME_QUERY);
		ps.setString(1, pathName);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
			r=rs.getInt(1);
		rs.close();
		ps.close();
		return r;
	}
	
	public List<Path> getAllOnwardPaths() throws SQLException
	{
		PreparedStatement ps=cn.prepareStatement(GET_ALL_ONWARD_PATHS_QUERY);
		ResultSet rs=ps.executeQuery();
		List<Path> r=new ArrayList<Path>();
		while(rs.next())
			r.add(new Path(rs.getInt("id"), rs.getString("name")));
		rs.close();
		ps.close();
		return r;
	}
	
	public List<Path> getAllPaths() throws SQLException
	{
		PreparedStatement ps=cn.prepareStatement(GET_ALL_PATHS_QUERY);
		ResultSet rs=ps.executeQuery();
		List<Path> r=new ArrayList<Path>();
		while(rs.next())
			r.add(new Path(rs.getInt("id"), rs.getString("name")));
		rs.close();
		ps.close();
		return r;
	}
	
	public List<String> getPathNamesWithPrefix(String pathNamePrefix) throws SQLException
	{
		PreparedStatement ps=cn.prepareStatement(GET_PATHNAMES_WITH_PREFIX_QUERY);
		ps.setString(1, pathNamePrefix+"%");
		ResultSet rs=ps.executeQuery();
		List<String> r=new ArrayList<String>();
		while(rs.next())
			r.add(rs.getString(1));
		rs.close();
		ps.close();
		return r;
	}
	
	public List<Node> getNodesInPath(int pathId) throws SQLException
	{
		List<Node> r=new ArrayList<Node>();
		PreparedStatement ps=cn.prepareStatement(GET_NODES_IN_PATH_QUERY);
		ps.setInt(1, pathId);
		ResultSet rs=ps.executeQuery();
		while(rs.next())
			r.add(new Node(rs.getInt("id"),
						rs.getString("name"), 
						rs.getDouble("latitude"), 
						rs.getDouble("longitude")));
		rs.close();
		ps.close();
		return r;
	}
	
	
	public List<Path> getPathsConnectingTwoNodes(int node1Id, int node2Id)
			throws SQLException
	{
		List<Path> r=new ArrayList<Path>();
		PreparedStatement ps=cn.prepareStatement(GET_PATHS_CONNECTING_TWO_NODES_QUERY);
		int ind=0;
		ps.setInt(++ind, node1Id);
		ps.setInt(++ind, node2Id);
		ResultSet rs=ps.executeQuery();
		while(rs.next())
			r.add(new Path(rs.getInt("id"), rs.getString("name")));
		rs.close();
		ps.close();
		return r;
	}
	
	public String getNextDepartureTime(int pathId, int nodeId) throws SQLException
	{
		String r=null;
		PreparedStatement ps=cn.prepareStatement(GET_NEXT_DEPARTURE_TIME_QUERY);
		int ind=0;
		ps.setInt(++ind, pathId);
		ps.setInt(++ind, nodeId);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
			r=rs.getString(1);
		rs.close();
		ps.close();
		return r;
	}
	
	public String getNextDepartureDuration(int pathId, int node1Id, int node2Id) throws SQLException
	{
		String r=null;
		PreparedStatement ps=cn.prepareStatement(GET_NEXT_DEPARTURE_DURATION_QUERY);
		int ind=0;
		ps.setInt(++ind, pathId);
		ps.setInt(++ind, node1Id);
		ps.setInt(++ind, pathId);
		ps.setInt(++ind, node1Id);
		ps.setInt(++ind, pathId);
		ps.setInt(++ind, node2Id);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
			r=rs.getString(1);
		rs.close();
		ps.close();
		return r;
	}
	
	public List<Bus> getRtBusesOnPath(int pathId) throws SQLException
	{
		List<Bus> r=new ArrayList<Bus>();
		PreparedStatement ps=cn.prepareStatement(GET_RT_BUSES_ON_PATH_QUERY);
		ps.setInt(1, pathId);
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
	
	public void deletePath(int pathId) throws SQLException
	{
		PreparedStatement ps=cn.prepareStatement(DELETE_PATH_QUERY);
		ps.setInt(1, pathId);
		ps.executeUpdate();
		ps.close();
	}
}
