package com.demo.mediators;

import javax.xml.namespace.QName;

import org.apache.synapse.MessageContext; 
import org.apache.synapse.mediators.AbstractMediator;

public class SampleClassMediator extends AbstractMediator { 

	public boolean mediate(MessageContext context) { 
		
		double average=0;
		
		String riskFactor="negative";
		
		String high= context.getEnvelope().getBody().getFirstElement().getFirstElement().
                getFirstChildWithName(new QName("http://services.samples/xsd","high")).getText();
		String low= context.getEnvelope().getBody().getFirstElement().getFirstElement().
                getFirstChildWithName(new QName("http://services.samples/xsd","low")).getText();
		String change= context.getEnvelope().getBody().getFirstElement().getFirstElement().
                getFirstChildWithName(new QName("http://services.samples/xsd","change")).getText();
		
		if(high!=null && low!=null && change!=null){
			average=(Double.parseDouble(high)+Double.parseDouble(low))/2;
			if(average*(Double.parseDouble(change))<0){
				riskFactor = "positive";
			}
		}
		
		context.setProperty("riskFactor", riskFactor);
		context.setProperty("average", average);
		
		System.out.println("Risk Factor was Measured");
        System.out.println("Average: " + average);
        System.out.println("Risk : " + riskFactor);
		
		return true;
	}
}
