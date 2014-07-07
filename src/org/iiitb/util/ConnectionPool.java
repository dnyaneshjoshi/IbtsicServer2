package org.iiitb.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/*
 * Author: Joshi Dnyanesh Madhav
 * */
public class ConnectionPool
{

	private static DataSource datasource;

	public static DataSource getDatasource()
	{
		return datasource;
	}

	public static void setDatasource(DataSource datasource)
	{
		ConnectionPool.datasource = datasource;
	}

	public static Connection getConnection()
	{
		Connection cn = null;
		try
		{
			if (datasource != null)
			{
				cn = datasource.getConnection();
			}
			else
			{
				Context context = new InitialContext();
				datasource = (DataSource) context.lookup("java:comp/env/jdbc/db");
				cn = datasource.getConnection();
			}
		}

		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (NamingException e)
		{
			e.printStackTrace();
		}
		return cn;
	}

	public static void freeConnection(Connection cn)
	{
		try
		{
			cn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}