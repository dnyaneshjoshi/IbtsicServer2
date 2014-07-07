package org.iiitb.ibtsic.action.model;

/*
 * Author: Joshi Dnyanesh Madhav
 * */
public class Bus
{
	public int id;
	public String regNo;
	public double latitude;
	public double longitude;
	public int onwardPathId;
	public int returnPathId;
	public int currentPathId;
	
	public Bus(int id, String regNo, double latitude, double longitude,
			int onwardPathId, int returnPathId, int currentPathId)
	{
		this.id=id;
		this.regNo=regNo;
		this.latitude=latitude;
		this.longitude=longitude;
		this.onwardPathId=onwardPathId;
		this.returnPathId=returnPathId;
		this.currentPathId=currentPathId;
	}
}
