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
 * This class will process the Incoming SOAP Request payload
 * Sample Input: A|B|C|D|E|F
 * Sample Processed Output ABCDEF
 */
public class MessagePreProcessor extends AbstractMediator {
    private static final Log log = LogFactory.getLog(MessagePostProcessor.class);

    private String separator = "|";

    public MessagePreProcessor() {
    }

    public boolean mediate(MessageContext mc) {

        SOAPFactory fac = OMAbstractFactory.getSOAP11Factory();
        SOAPBody soapBody1 =  mc.getEnvelope().getBody();
        OMElement parentElement = (OMElement) soapBody1.getFirstElement();
        String variableString = parentElement.getFirstChildWithName(new QName("http://esb.wso2.org","args0","esb")).getText();
        System.out.println("variableString before trim: "+variableString);

        variableString = variableString.replace(separator, "");

        OMElement newChildElement1 = fac.createOMElement(new QName("http://esb.wso2.org", "args0", "esb"));
        newChildElement1.setText(variableString);

        System.out.println("trimmed: "+variableString);

        parentElement.getFirstChildWithName(new QName("http://esb.wso2.org","args0","esb")).detach();
        parentElement.addChild(newChildElement1);

        System.out.println("end of class");
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

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

}
