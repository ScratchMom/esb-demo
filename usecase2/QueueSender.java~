package org.wso2.carbon.mb.jms.sender;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.util.Properties;

public class QueueSender {
    public static final String QPID_ICF = "org.wso2.andes.jndi.PropertiesFileInitialContextFactory";
    private static final String CF_NAME_PREFIX = "connectionfactory.";
    private static final String QUEUE_NAME_PREFIX = "queue.";
    private static final String CF_NAME = "qpidConnectionfactory";
    String userName = "admin";
    String password = "admin";
    private static String CARBON_CLIENT_ID = "carbon";
    private static String CARBON_VIRTUAL_HOST_NAME = "carbon";
    private static String CARBON_DEFAULT_HOSTNAME = "localhost";
    private static String CARBON_DEFAULT_PORT = "5674";
    String queueName = "testQueue";

    public static void main(String[] args) throws NamingException, JMSException {
        QueueSender queueSender = new QueueSender();
      //  for (int i = 0; i < 50000; i++) {
               // long startTime = System.currentTimeMillis();
                queueSender.sendMessages(2);
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
            // if((System.currentTimeMillis() - startTime)<1000){
//                    try {
//                        Thread.sleep(900);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//                    }

      //  }
    }
    public void sendMessages(int number) throws NamingException, JMSException {
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, QPID_ICF);
        properties.put(CF_NAME_PREFIX + CF_NAME, getTCPConnectionURL(userName, password));
        System.out.println("getTCPConnectionURL(userName,password) = " + getTCPConnectionURL(userName, password));
        InitialContext ctx = new InitialContext(properties);
        // Lookup connection factory
        QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.lookup(CF_NAME);
        QueueConnection queueConnection = connFactory.createQueueConnection();
        queueConnection.start();
        QueueSession queueSession =
                queueConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
        // Send message
        Queue queue =  queueSession.createQueue(queueName);

        // create the message to send
       // TextMessage textMessage = queueSession.createTextMessage("msg"+number);


        TextMessage textMessage = queueSession.createTextMessage(getMessage(number));
        textMessage.setJMSCorrelationID("1234");
        System.out.println("creating message");
        System.out.println("message created");
        BytesMessage bytesMessage = queueSession.createBytesMessage();
        javax.jms.QueueSender queueSender = queueSession.createSender(queue);
        queueSender.send(textMessage);
        System.out.println(number);
        queueSender.close();
        queueSession.close();
        queueConnection.close();
    }
    public String getTCPConnectionURL(String username, String password) {
        // amqp://{username}:{password}@carbon/carbon?brokerlist='tcp://{hostname}:{port}'
        return new StringBuffer()
                .append("amqp://").append(username).append(":").append(password)
                .append("@").append(CARBON_CLIENT_ID)
                .append("/").append(CARBON_VIRTUAL_HOST_NAME)
                .append("?brokerlist='tcp://").append(CARBON_DEFAULT_HOSTNAME).append(":").append(CARBON_DEFAULT_PORT).append("'")
                .toString();
    }

    public String getMessage(int i){
        BufferedReader br = null;
        String msg="<q:VIN>1234567<q:VIN><q:DEALER>suburban<q:DEALER>";
        return msg;

    }


}
