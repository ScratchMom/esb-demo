package org.wso2.esb;

import java.lang.System;

public class BackendService {

	public String serviceMethod(String inputString) {
        String var;
		if("ABCDEF".equalsIgnoreCase(inputString)) {
            var = "PQRXYZ";
            System.out.println("Expected Input received by BackendService: "+inputString);
        }
        else{
            var = "ERROR";
            System.out.println("Invalid Input received by BackendService: "+inputString);
        }
		return var;
	}
}