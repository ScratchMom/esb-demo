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
public class CharTrimMediator extends AbstractMediator {
    private static final Log log = LogFactory.getLog(SimpleClassMediator.class);

    public CharTrimMediator() {
    }

    public boolean mediate(MessageContext mc) {

        System.out.println("CharTrimMediator class hit...");

        SOAPFactory fac = OMAbstractFactory.getSOAP11Factory();
        SOAPBody soapBody1 =  mc.getEnvelope().getBody();
        OMElement parentElement = (OMElement) soapBody1.getFirstElement();
        String variableString = parentElement.getFirstChildWithName(new QName("http://esb.wso2.org","args0","esb")).getText();
        System.out.println("variableString before trim: "+variableString);

        variableString = variableString.replace("|", "");

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

}
