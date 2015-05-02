Use Case 1
==========

Create a one way SOAP service to process an incoming string and break into a fixed length elements  
and transform into an XML format and put on a queue. WS-Security username-token mechanism to be used
as Authentication mechanism.

In this scenario two WSO2 products are used, WSO2 Enterprise Service Bus (ESB) that will host the SOAP service, WSO2 Message Broker (MB) which will manage the JMS queue that we are using for this use case.

Products used
-------------
* WSO2 Enterprise Service Bus (ESB) 4.8.1   
* WSO2 Message Broker (MB) 2.2.0  

| Server | Offset | URL |
|:-------|:-------|:-------|
| WSO2 ESB | 0 | https://localhost:9443/carbon |
| WSO2 MB | 2 | https://localhost:9445/carbon |

You can simply change the port offset from ```<PRODUCT_HOME>/repository/conf/carbon.xml``` as follows: change the ```<Ports>``` section, ```<Offset>1</Offset>```. Once you change the carbon.xml file, start/restart the server.

WSO2 Message Broker 2.2.0 Configuration
---------------------------------------

1. Configure port offset as instructed above.

2. [Optional step]
If you are running this use case on Mac OS X  
Copy the [libsnappyjava.jnilib](snappy-1.1.1-Mac-x86_64/libsnappyjava.jnilib) file to ```<MB_HOME>```.

3. Start WSO2 MB 2.2.0

4. Login to admin console

5. Create queue messageQueue in WSO2 MB 2.2.0

Simple Axis2 Server configuration
---------------------------------

1. Copy [BackendService.aar](Axis2Service/BackendService.aar) to ```<ESB_HOME>/samples/axis2Server/repository/services```   

2. Start Simple Axis2Server from ```<ESB_HOME>/samples/axis2Server```  
e.g.: sh axis2server.sh

*Note: You must see the following message.*  
```
INFO deployment.DeploymentEngine: Deploying Web service: BackendService.aar - file:/<path_to_your_ESB_HOME>/samples/axis2Server/repository/services/BackendService.aar
```

*Note: You can view the WSDL file from the link below to make sure that the back end service is deployed successfully.*
[http://localhost:9000/services/BackendService?wsdl](http://localhost:9000/services/BackendService?wsdl)

WSO2 Enterprise Service Bus 4.8.1 configuration
-----------------------------------------------

1. Copy the related configuration files OR  
refer ```Setting up WSO2 ESB``` section of [Integrating WSO2 MB with WSO2 ESB](https://docs.wso2.com/display/MB220/Integrating+WSO2+ESB).

2. Copy [MessageProcessor-1.0-SNAPSHOT.jar](MessageProcessor/target/MessageProcessor-1.0-SNAPSHOT.jar) file to ```<ESB_HOME>/repository/components/lib/``` 

2. Create the two sequences as per [BackendServiceMsgProcessorSequence.xml](ESB481/repository/registry-sequences/BackendServiceMsgProcessorSequence.xml) and [PayloadProcessorSequence.xml](ESB481/repository/registry-sequences/PayloadProcessorSequence.xml) and add them to the registry  

3. Create the proxy service as per [UC1_MessageProcessorAndQueue.xml](ESB481/repository/deployment/server/synapse-configs/default/proxy-services/OrganizationalInfoAPI.xml)

4. Start WSO2 ESB 4.8.1

Usecase task flow
-----------------

**STEP 1 - Proxy (Message Acceptance)**
```
<env:Envelope>
   <env:Header>
      <wsse:Security>
         <wsse:UsernameToken>
            <wsse:Username>USER</wsse:Username>
            <wsse:Password>PASS</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </env:Header>
   <env:Body>
      <urn:Request>A|B|C|D|E|F</urn:Request>
   </env:Body>
</env:Envelope>
```

**STEP 2 - Class Mediator (Removing pipes)**  
ABCDEF

**STEP 3 - Backend Response**  
PQRXYZ

**STEP 4 - Class Mediator (Split incoming value)**  
PQR  
XYZ  

**STEP 5 - Payload Factory**  
```
<env:Envelope>
   <env:Body>
      <urn:Response>
	<urn:elementA>PQR</urn:elementA>
	<urn:elementB>ZYZ</urn:elementB>
      </urn:Response>
   </env:Body>
</env:Envelope>
```

**STEP 6 - Send to Queue**
