package org.cloudbus.cloudsim.core;

public class FaultTags {
	
	static final int FAULT = 0;
	static final int RECOVERED = 1;
	int tagValue;
	int id;
	

	
	public FaultTags(int vid,String tag)
	{
	setTag(tag);
	setID(vid);
	}



	public void setID(int vid) {
		this.id=vid;

		
	}



	public void setTag(String tag)
	{
		
		if(tag=="FAULT")
		{
			this.tagValue=FAULT;
		}	
		else if(tag=="RECOVERED")
		{
			this.tagValue=RECOVERED;
		}
	}

	
	
	
	
	public int getTagValue()
	{
		return tagValue;
	}
	
	public int getId()
	{
		return id;
	}




}
