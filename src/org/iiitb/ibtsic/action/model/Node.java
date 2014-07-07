package org.iiitb.ibtsic.action.model;

/*
 * Author: Joshi Dnyanesh Madhav
 * */
public class Node
{
	public int id;
	public String name;
	public double latitude;
	public double longitude;
	
	public Node(int id, String name, double latitude, double longitude)
	{
		this.id=id;
		this.name=name;
		this.latitude=latitude;
		this.longitude=longitude;
	}
}
