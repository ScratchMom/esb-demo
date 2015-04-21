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
 * Created by suhanr on 4/21/15.
 */
public class SimpleClassMediator extends AbstractMediator {
    private static final Log log = LogFactory.getLog(SimpleClassMediator.class);

    private String splitLength = "3";

    public SimpleClassMediator() {
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
