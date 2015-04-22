/*
*Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*WSO2 Inc. licenses this file to you under the Apache License,
*Version 2.0 (the "License"); you may not use this file except
*in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing,
*software distributed under the License is distributed on an
*"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*KIND, either express or implied.  See the License for the
*specific language governing permissions and limitations
*under the License.
*/

package org.wso2.sample.mediator;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;

import javax.xml.namespace.QName;

/**
 * This class will process the Outgoing SOAP Response payload
 * Sample Input: PQRXYZ
 * Sample Processed Output <PQR>, <XYZ>
 */
public class MessagePostProcessor extends AbstractMediator {
    private static final Log log = LogFactory.getLog(MessagePostProcessor.class);

    private String splitLength = "3";

    public MessagePostProcessor() {
    }

    public boolean mediate(MessageContext mc) {

        SOAPFactory fac = OMAbstractFactory.getSOAP11Factory();
        Integer iSplitLength = Integer.parseInt(splitLength);
        SOAPBody soapBody = mc.getEnvelope().getBody();
        System.out.println("soapbody007: " + soapBody);

        SOAPBody soapBody1 =  mc.getEnvelope().getBody();
        OMElement parentElement = (OMElement) soapBody1.getFirstElement();
        String variableString = parentElement.getFirstChildWithName(new QName("http://esb.wso2.org","return","ns")).getText();
        System.out.println("return: "+variableString);

        if (variableString.length()<iSplitLength){
            System.out.println(" *** Backend Service returned payload length is not enough to split into given length in splitLength parameter *** ");
            return false;
        }

        String var1 = variableString.substring(0,iSplitLength);
        String var2 = variableString.substring(iSplitLength);
        OMElement newChildElement1 = fac.createOMElement(new QName("http://esb.wso2.org", "elementA", "ns"));
        newChildElement1.setText(var1);
        OMElement newChildElement2 = fac.createOMElement(new QName("http://esb.wso2.org", "elementB", "ns"));
        newChildElement2.setText(var2);

        parentElement.getFirstChildWithName(new QName("http://esb.wso2.org","return","ns")).detach();
        parentElement.addChild(newChildElement1);
        parentElement.addChild(newChildElement2);

        return true;
    }

    public String getType() {
        return null;
    }

    public void setTraceState(int traceState) {
        traceState = 0;
    }

    public int getTraceState() {
        return 0;
    }

    public String getSplitLength() {
        return splitLength;
    }

    public void setSplitLength(String splitLength) {
        this.splitLength = splitLength;
    }
}
