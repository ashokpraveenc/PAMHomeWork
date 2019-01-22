package com.poc.customWorkItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;

public class GovtPermit implements org.kie.api.runtime.process.WorkItemHandler {
	
	private static HashMap<String, Integer> ep=new HashMap<String, Integer>();
	private static HashMap<String, Integer> sp=new HashMap<String, Integer>();
	private static String arr[]={"Undefined","Accepted","Rejected","Pending","Rescinded"};
	private HashMap<String, Object> results=new HashMap<String, Object>();
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {
		// TODO Auto-generated method stub
		
	}

	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {
		try{
			System.out.println("<><><>In Govt.Permit<><><>");
			
			String reqType=arg0.getParameter("reqType").toString();
			String num=arg0.getParameter("num").toString();
			
			if(reqType.equalsIgnoreCase("ElectricalRequest")){
				
				System.out.println("<><><>In ElectricalPermit<><><>");
				ep.put(num, new Integer(0));

				Integer t=sp.get(num);
				if(t==null)
				{
					System.out.println("Error SP Number not found!!!");
					results.put("response", arr[0]);
				}
				else if(t.intValue()==2 || t.intValue()==4)
				{
					System.out.println("SP status check:"+arr[t.intValue()]+" ...Rescinding EP");
					ep.put(num, new Integer(4));
					results.put("response", arr[4]);
					//break;
				}
				else{
					System.out.println("Checking electrical permit status....");
					int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
					System.out.println("New Electrical Permit Status:"+arr[randomNum]);
					ep.put(num, new Integer(randomNum));
					if(randomNum==1)
					{
						results.put("response", arr[1]);
						//break;
						
					}
					else if(randomNum==2)
					{
						System.out.println("Rescinding EP...");
						ep.put(num, 4);
						results.put("response", arr[4]);
						//break;
					}
					else
					{
						results.put("response", arr[3]);
					}
				}
				
			}
			
			else if(reqType.equalsIgnoreCase("StructuralRequest")){
				System.out.println("<><><>In StructuralPermit<><><>");
				sp.put(num, new Integer(0));

				Integer t=ep.get(num);
				if(t==null)
				{
					System.out.println("Error EP Number not found!!!");
					results.put("response", arr[0]);
				}
				else if(t.intValue()==2 || t.intValue()==4)
				{
					System.out.println("EP status check:"+arr[t.intValue()]+" ...Rescinding SP");
					sp.put(num, new Integer(4));
					results.put("response", arr[4]);
					//break;
				}
				else{
					System.out.println("Checking structural permit status....");
					int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
					System.out.println("New Structural Permit Status:"+arr[randomNum]);
					sp.put(num, new Integer(randomNum));
					if(randomNum==1)
					{
						results.put("response", arr[1]);
						//break;
					}
					else if(randomNum==2)
					{
						System.out.println("Rescinding SP...");
						sp.put(num, 4);
						results.put("response", arr[4]);
						//break;
					}
					else
					{
						results.put("response", arr[3]);
					}
				}

			}
			
			else{
				throw new RuntimeException("<!><!><!>Invalid Req.Type<!><!><!>");
			}
			
			arg1.completeWorkItem(arg0.getId(), results);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
