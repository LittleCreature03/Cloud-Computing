package org.cloudbus.cloudsim.core;

import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.RankVMList;

public class FaultVMList {
	
	private int Faultid;
	private int Faultrank;
	
	public FaultVMList(int id,int rank)
	{
		setFaultId(id);
		setFaultRankId(rank);
	}
	
	private  static List<FaultVMList>FaultList=new LinkedList<FaultVMList>();







public int getFaultId() {
	return Faultid;
}

/**
 * Sets the id.
 * 
 * @param id the new id
 */
protected void setFaultId(int id) {
	this.Faultid = id;
}


public int getRankId() {
	return   Faultrank;
}

/**
 * Sets the id.
 * 
 * @param id the new id
 */
protected void setFaultRankId(int rank) {
	this.Faultrank =rank;
}





public static void RemoveFaultVM(int id) {
	FaultList.remove(id);
	
}






}