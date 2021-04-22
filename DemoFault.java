
 /* Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation
 *               of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */


package org.cloudbus.cloudsim.examples;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.FaultRecovery;
import org.cloudbus.cloudsim.core.FaultTags;
import org.cloudbus.cloudsim.core.FaultVMList;
import org.cloudbus.cloudsim.lists.CloudletList;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import org.cloudbus.cloudsim.RankVM;
import org.cloudbus.cloudsim.RankVMList;


/**
 * An example showing how to create
 * scalable simulations.
 */
public class DemoFault {

	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList;

	/** The vmlist. */
	private static List<Vm> vmlist;
	
	private  static List<RankVMList> RankList=new LinkedList<RankVMList>();
	private  static List<FaultVMList>FaultList=new LinkedList<FaultVMList>();
	private static  List<FaultTags>TagList=new LinkedList<FaultTags>();
	static RankVM rank;
	
	static boolean Execution=true;
	

	private static List<Vm> createVM(int userId, int vms, List<Float>Speed) {
		
		int i,k;
		float temp;
		Scanner in=new  Scanner(System.in);

		//Creates a container to store VMs. This list is passed to the broker later
		LinkedList<Vm> list = new LinkedList<Vm>();
		
		Float VMSpeed[]=new Float[vms];
		float VM_mips[]=new float[vms];
		
		

		//VM Parameters
		long size = 10000; //image size (MB)
		int ram = 512; //vm memory (MB)
		float mips;
		long bw = 1000;
		int pesNumber = 1; //number of cpus
		String vmm = "Xen"; //VMM name

		//create VMs
		
		System.out.println(Speed);
		Vm[] vm = new Vm[vms];
		Speed.toArray(VMSpeed);
		
		
		
	
		for(i=0;i<vms;i++)
		
		{
			
			mips=0;
			mips=VMSpeed[i];
			
			
			//for creating a VM with a space shared scheduling policy for cloudlets:
			//vm[i] = Vm(i, userId, mips, pesNumber, ram, bw, size, priority, vmm, new CloudletSchedulerSpaceShared());
			
			vm[i] = new Vm(i, userId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			list.add(vm[i]);
			
		}
		return list;
	}


	

	private static List<Cloudlet> createCloudlet(int userId, int cloudlets){
		
		
		
		@SuppressWarnings("unused")
		int i,k,temp;
		
		Scanner in=new Scanner(System.in);
		
		// Creates a container to store Cloudlets
		LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();
		int TaskLength[]=new int[cloudlets];

		//cloudlet parameters
		long length;
		long fileSize = 300;
		long outputSize = 300;
		int pesNumber = 1;
		UtilizationModel utilizationModel = new UtilizationModelFull();

		Cloudlet[] cloudlet = new Cloudlet[cloudlets];

		
		//getting the length  of the tasks
		for(i=0;i<cloudlets;i++)
		{
			System.out.println("Enter the length of Tasks"+i);
			TaskLength[i]=in.nextInt();
			
		}
		
		
		

		
		//Assigning the sorted  tasks 
		for(i=0;i<cloudlets;i++)
			
		{
			for(k=i+1;k<cloudlets;k++)
			{
				if(TaskLength[i]<TaskLength[k])
				{
					temp=TaskLength[i];
					TaskLength[i]=TaskLength[k];
					TaskLength[k]=temp;
				}
			}
		}
		
for(i=0;i<cloudlets;i++)
			
		{
			
			
			length=0;
			length=TaskLength[i];
			
			
			cloudlet[i] = new Cloudlet(i, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet[i].setUserId(userId);
			list.add(cloudlet[i]);
			
		}
		
		

		return list;
	
	}
	
	
	
	private static List<RankVMList> SortByRank(List<Float> Speed,int vms)
	{
		List<RankVMList> Rankls=new LinkedList<RankVMList>();
		rank=new RankVM(Speed,vms);
		
		Rankls=rank.AssignRank(vms);
		Rankls=rank.getRankList();
		return Rankls;
	}

	////////////////////////// STATIC METHODS ///////////////////////

	/**
	 * Creates main() to run this example
	 */
	public static void main(String[] args) {
		
		Scanner in=new Scanner(System.in);
		Log.printLine("Starting Ranking Scheme");

		try {
			// First step: Initialize the CloudSim package. It should be called
			// before creating any entities.
			int No_of_vm,No_of_Tasks,i,k;
			int j=0;
			int exec=0;
			int cloudletsid=0;
			int vmsid=0;
			float temp;
		

			//Creates a container to store VMs. This list is passed to the broker later
			LinkedList<Vm> list = new LinkedList<Vm>();
			List<Float>Speed=new ArrayList<Float>();
			
		
			
			
			System.out.println("Enter the Number of Tasks");
			No_of_Tasks=in.nextInt();
			
			System.out.println("Enter  the Number of Vms");
			No_of_vm=in.nextInt();
			
			float VM_mips[]=new float[No_of_vm];
			int Assign=0;
			
			for(i=0;i<No_of_vm;i++)
			{
				
				System.out.println("Enter the speed of the vm"+i);
				VM_mips[i]=in.nextInt();
				
			}
			
			
			for(i=0;i<No_of_vm;i++)
			{
				for(k=i+1;k<No_of_vm;k++)
				{
					if(VM_mips[i]<VM_mips[k])
					{
						temp=VM_mips[i];
						VM_mips[i]=VM_mips[k];
						VM_mips[k]=temp;
					}
				}
			}
			
			
			for(i=0;i<No_of_vm;i++)
			{
				
				Speed.add(VM_mips[i]);
			}
			
			
			int num_user = 1;   // number of grid users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false;  // mean trace events

			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);

			// Second step: Create Datacenters
			//Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
			@SuppressWarnings("unused")
			Datacenter datacenter0 = createDatacenter("Datacenter_0");
			@SuppressWarnings("unused")
			Datacenter datacenter1 = createDatacenter("Datacenter_1");

			//Third step: Create Broker
			DatacenterBroker broker = createBroker();
			int brokerId = broker.getId();
			
			
			
			//Fourth step: Create VMs and Cloudlets and send them to broker
			vmlist = createVM(brokerId,No_of_vm,Speed); //creating 'n' vms
			cloudletList = createCloudlet(brokerId,No_of_Tasks); // creating 'n' cloudlets
			RankList=SortByRank(Speed,No_of_vm);
			
			

			broker.submitVmList(vmlist);
			broker.submitCloudletList(cloudletList);
			
		
			
			Cloudlet[] cloudlet = new Cloudlet[No_of_Tasks];
			Vm[] vm = new Vm[No_of_vm];
			RankVMList[] rank=new RankVMList[No_of_vm];
			FaultVMList[] faultvm=new FaultVMList[No_of_vm];
			FaultTags[] ft=new FaultTags[No_of_vm];
			
			
			vmlist.toArray(vm);
			cloudletList.toArray(cloudlet);
			RankList.toArray(rank);
			FaultList.toArray(faultvm);
			TagList.toArray(ft);
			
			
			int r=0;
			j=0;
			int occurence=0;
			int id=0;
			int vmid=0;
			int RANK=1;
		
			int faultvms=0;
			int faultrank=0;
			
	
			for(i=0;i<No_of_Tasks;i++)
			{
				
				 if(occurence<2)
					{
					 
					 
						
						vmid=rank[r].getByRank(RankList,j,RANK);
						
						
						cloudletsid=cloudlet[i].getCloudletId();
						broker.bindCloudletToVm(cloudletsid,vmid);
						System.out.println("The clouudlet"+i+" is assigned to the vm id"+vmid+"sucessfully");
						occurence++;
						}
					else
					{
						System.out.println("The VM has been shutdown due to overheating issue"
								+"  "+ "it will resume later");
						
						faultvms=rank[j].getId();
						faultrank=rank[j].getRankId();
						faultvm[j]=new FaultVMList(faultvms,faultrank);
						TagList=addTag(faultvms,No_of_vm);
						
						FaultList.add(faultvm[j]);
						
						
						
						rank[r].RemoveVM(RankList);
						i--;
						
						j=nextvm(j,No_of_vm);
						
						RANK=nextRank(RANK,No_of_vm);
						occurence=0;
						
				
						RecoverVm(RankList,FaultList,TagList,No_of_vm);
						
						
					}
			}

  System.out.println("hai");
			// Fifth step: Starts the simulation
			CloudSim.startSimulation();

			// Final step: Print results when simulation is over
			List<Cloudlet> newList1 = broker.getCloudletReceivedList();
			
		

			CloudSim.stopSimulation();

			printCloudletList(newList1,No_of_Tasks);

			Log.printLine("Ranking Scheme has been finished successfully!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.printLine("The simulation has been terminated due to an unexpected error");
		}
	}

	



private static int nextRank(int RANK, int vms) {
		// TODO Auto-generated method stub
	
	if(RANK<vms)
	{
	RANK++;
	}
	else
	{
		RANK=1;
	}
	
	return RANK;
		
	}




	private static int nextvm(int j, int vms) {
		
		if(j<vms-1)
		{
		j++;
		}
		else
		{
			j=0;
		}
		
		return j;
	}




	private static List<FaultTags> addTag(int id,int vms) {
		
		
		int size;
		size=TagList.size();
		FaultTags[] ft=new FaultTags[vms];
		
		if(size<vms)
		{
			ft[id]=new FaultTags(id,"FAULT");
			TagList.add(ft[id]);
		
		}
		else
		{
			FaultTags[] ft1=new FaultTags[size];
			TagList.toArray(ft1);
			@SuppressWarnings("unlikely-arg-type")
			boolean result=TagList.contains(ft1[id].getId());
		if(result)
		{
			ft[id].setTag("FAULT");
		}
		
		else
		{
		
		ft[id]=new FaultTags(id,"FAULT");
		TagList.add(ft[id]);
		}
		}
		return TagList;
	}
	



	public static  void RecoverVm(List<RankVMList>RankList, List<FaultVMList>FaultList, List<FaultTags>TagList, int no_of_vm) {
		
		if(RankList.size()<(no_of_vm)/2)
		{
			new FaultRecovery(FaultList,TagList,RankList,no_of_vm);
		}
		
		
	}








	private static Datacenter createDatacenter(String name){

		// Here are the steps needed to create a PowerDatacenter:
		// 1. We need to create a list to store one or more
		//    Machines
		List<Host> hostList = new ArrayList<Host>();

		// 2. A Machine contains one or more PEs or CPUs/Cores. Therefore, should
		//    create a list to store these PEs before creating
		//    a Machine.
		List<Pe> peList1 = new ArrayList<Pe>();

		int mips = 1000;

		// 3. Create PEs and add these into the list.
		//for a quad-core machine, a list of 4 PEs is required:
		peList1.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
		peList1.add(new Pe(1, new PeProvisionerSimple(mips)));
		peList1.add(new Pe(2, new PeProvisionerSimple(mips)));
		peList1.add(new Pe(3, new PeProvisionerSimple(mips)));

		//Another list, for a dual-core machine
		List<Pe> peList2 = new ArrayList<Pe>();

		peList2.add(new Pe(0, new PeProvisionerSimple(mips)));
		peList2.add(new Pe(1, new PeProvisionerSimple(mips)));

		//4. Create Hosts with its id and list of PEs and add them to the list of machines
		int hostId=0;
		int ram = 2048; //host memory (MB)
		long storage = 1000000; //host storage
		int bw = 10000;

		hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList1,
    				new VmSchedulerTimeShared(peList1)
    			)
    		); // This is our first machine

		hostId++;

		hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList2,
    				new VmSchedulerTimeShared(peList2)
    			)
    		); // Second machine


		//To create a host with a space-shared allocation policy for PEs to VMs:
		//hostList.add(
    	//		new Host(
    	//			hostId,
    	//			new CpuProvisionerSimple(peList1),
    	//			new RamProvisionerSimple(ram),
    	//			new BwProvisionerSimple(bw),
    	//			storage,
    	//			new VmSchedulerSpaceShared(peList1)
    	//		)
    	//	);

		//To create a host with a oportunistic space-shared allocation policy for PEs to VMs:
		//hostList.add(
    	//		new Host(
    	//			hostId,
    	//			new CpuProvisionerSimple(peList1),
    	//			new RamProvisionerSimple(ram),
    	//			new BwProvisionerSimple(bw),
    	//			storage,
    	//			new VmSchedulerOportunisticSpaceShared(peList1)
    	//		)
    	//	);


		// 5. Create a DatacenterCharacteristics object that stores the
		//    properties of a data center: architecture, OS, list of
		//    Machines, allocation policy: time- or space-shared, time zone
		//    and its price (G$/Pe time unit).
		String arch = "x86";      // system architecture
		String os = "Linux";          // operating system
		String vmm = "Xen";
		double time_zone = 10.0;         // time zone this resource located
		double cost = 3.0;              // the cost of using processing in this resource
		double costPerMem = 0.05;		// the cost of using memory in this resource
		double costPerStorage = 0.1;	// the cost of using storage in this resource
		double costPerBw = 0.1;			// the cost of using bw in this resource
		LinkedList<Storage> storageList = new LinkedList<Storage>();	//we are not adding SAN devices by now

		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);


		// 6. Finally, we need to create a PowerDatacenter object.
		Datacenter datacenter = null;
		try {
			datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return datacenter;
	}

	//We strongly encourage users to develop their own broker policies, to submit vms and cloudlets according
	//to the specific rules of the simulated scenario
	private static DatacenterBroker createBroker(){

		DatacenterBroker broker = null;
		try {
			broker = new DatacenterBroker("Broker");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return broker;
	}
	
	
	

	
	
	private static void printCloudletList(List<Cloudlet> list,int cloudlets) {
		int size = list.size();
		Cloudlet[] cloudlet = new Cloudlet[cloudlets];

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
				"Data center ID" + indent + "VM ID" + indent +indent+"Time" + indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet[i] = list.get(i);
			
			Log.print(indent + cloudlet[i].getCloudletId() + indent + indent);

			if (cloudlet[i].getCloudletStatus() == Cloudlet.SUCCESS){
				Log.print("SUCCESS");

				Log.printLine( indent + indent + cloudlet[i].getResourceId() + indent + indent + indent + cloudlet[i].getVmId() +
						indent + indent + indent+dft.format(cloudlet[i].getActualCPUTime()) +
						indent + indent + dft.format(cloudlet[i].getExecStartTime())+ indent + indent + indent + dft.format(cloudlet[i].getFinishTime()));
			}
			else
			{
				System.out.println("Not succesfull");
			}
		}

	}
}
