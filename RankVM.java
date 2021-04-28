package org.cloudbus.cloudsim;

import java.io.*;
import java.util.*;
import org.cloudbus.cloudsim.*;


public class RankVM {
	
	
	
    int RANK=0;
	/** The vm list. */
	protected List<? extends Vm> vmList;
	List<Float>VMCap=new ArrayList<Float>();
	List<RankVMList>RankList=new LinkedList<RankVMList>();

	
	

	
	public RankVM(List<Float> speed, int vms)
	{
		VMCap=CalculateVmCapacity(speed,vms);	
		setVMCapacity(VMCap);
		RankList=AssignRank(vms);
		setRankList(RankList);

	}
	
	
	


	public List<Float> CalculateVmCapacity(List<Float> speed,int vms)
	{
	   float VMCapacity[]=new float[vms];
	   float Tot_speed=0;
	   float sp[]=new float[speed.size()];
	   List<Float>list=new ArrayList<Float>();
	   int i;
	   
	   for(i=0;i<vms;i++)
	   {
	   sp[i]=speed.get(i);
	   Tot_speed=Tot_speed+sp[i];
	   }
	   
	   
	   	for(i=0;i<vms;i++)
	   	{
	   	sp[i]=speed.get(i);
		VMCapacity[i]=(float) (((sp[i]*100)/(Tot_speed)));
		list.add(VMCapacity[i]);
	   	}
	
	for(i=0;i<vms;i++)
	{
	System.out.println("The Capacity of VM"+"  "+i+" is"+VMCapacity[i]);
	}
	
	return list;
	}
	
	
	private void setRankList(List<RankVMList> rankList) {
		
		this.RankList=rankList;
	}	


	
	@SuppressWarnings("unchecked")
	public LinkedList<RankVMList> getRankList() {
		return (LinkedList<RankVMList>)RankList;
	}

	
	


	protected  void setVMCapacity(List<Float> VMCap) {
		this.VMCap = VMCap;
	}


	public <T> List<Float> getVMCapacity() {
		return (List<Float>)VMCap;
	}
	
	
	
	public List<RankVMList> AssignRank(int vms)
	{
		
		int i;

	   List<Float>VMCap=new ArrayList <Float>();
	   Float VMCapacity[]=new Float[vms]; 
		List<RankVMList>RankList=new LinkedList <RankVMList>();
		RankVMList[] Rank=new RankVMList[vms];
		VMCap=getVMCapacity();
		Collections.sort(VMCap,Collections.reverseOrder());
		VMCap.toArray(VMCapacity);
		for(i=0;i<VMCapacity.length;i++)
			{
	
		RANK++;
		Rank[i]=new RankVMList(i,RANK);
		RankList.add(Rank[i]);
		
			
	}
		
	
	return RankList;
	
	}




  





	




	





	
}