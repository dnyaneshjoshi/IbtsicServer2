package org.iiitb.ibtsic.action.model;

/*
 * Author: Joshi Dnyanesh Madhav
 * */
public class Announcement
{
	public int id;
	public String name;
	public String description;
	public String startInstant;
	public String validity;
	public int node1Id;
	public int node2Id;
	
	public Announcement(int id, String name, String description, String startInstant,
			String validity, int node1Id, int node2Id)
	{
		this.id=id;
		this.name=name;
		this.description=description;
		this.startInstant=startInstant;
		this.validity=validity;
		this.node1Id=node1Id;
		this.node2Id=node2Id;
	}
}
