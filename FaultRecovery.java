package org.cloudbus.cloudsim.core;

import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.RankVM;
import org.cloudbus.cloudsim.RankVMList;


public class FaultRecovery {
	
	int vmid=0;
	int i=0;
	
public FaultRecovery(List<FaultVMList> FaultList, List<FaultTags>TagList, List<RankVMList> RankList,int nom) {
	int FaultVid;
	int RecoverVid;
	int FaultRank;
	
	int size = FaultList.size();
	
	
	
	if(size==0)
		{
		System.out.println("All VM's are recovered");
		}
	else
		
	{
	FaultTags[] ft=new FaultTags[TagList.size()];
	FaultVMList[] faultvm=new FaultVMList[size];
	RankVMList[] RankLs=new RankVMList[nom];
	RankList.toArray(RankLs);
	FaultList.toArray(faultvm);
	TagList.toArray(ft);
	
	
	
	
	
		
		
		FaultVid = faultvm[i].getFaultId();
		FaultRank=faultvm[i].getRankId();
		
		RecoverVid=ft[FaultVid].getId();
		
		
		
		
		if(size!=0&&FaultVid==RecoverVid)
		{
			
			
			switch(ft[FaultVid].getTagValue())
			{
			case  FaultTags.FAULT:
				
				FaultRank=faultvm[i].getRankId();
				RankLs[i]=new RankVMList(RecoverVid,FaultRank);
				RankList.add(RankLs[i]);
	
				
				FaultList.remove(i);
				
				
				System.out.println("The Vm "+FaultVid+"is recovered");
				
				
				
		
				break;
				
			case FaultTags.RECOVERED:
				break;
			}
			
		}
		
		
		else
		{
			System.out.println("Cannot recover vm");
		}
		
		i++;
		
	}
	}
	
	}



	
	
	

	


