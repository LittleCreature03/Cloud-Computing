package org.cloudbus.cloudsim;

import java.util.List;

public class RankVMList {

	private int id;
	private int ranks;

	public RankVMList(int id, int rank) {

	setId(id);
	setRankId(rank);
		
	}
	
	
	public int getByRank(List<RankVMList> RankList, int vid, int RANK) {
		int vmid = 0;
		int  id=0;
		int size = RankList.size();
		RankVMList[] rank = new RankVMList[RankList.size()];
		for (int i = 0; i < size; i++) {
			rank[i] = RankList.get(i);
			
		}
	
			if(rank[id].getId()==vid&&rank[id].getRankId()==RANK)
			{
				vmid=rank[id].getId();
				
			}
		
		return vmid;
		
	}

	
public void RemoveVM(List<RankVMList> RankList) {
		int id=0;
		RankList.remove(id);
		
		
	}
	
	
	
	
	
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id the new id
	 */
	protected void setId(int id) {
		this.id = id;
	}
	

	public int getRankId() {
		return ranks;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id the new id
	 */
	protected void setRankId(int rank) {
		this.ranks =rank;
	}


	
	
}
