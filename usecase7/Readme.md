Use Case 7 
==========

A JMS messages will be dynamically routed to a destination Queue based on business rules stored.

In this scenario two WSO2 products are used, WSO2 Enterprise Service Bus (ESB) that will host the proxy service which will listen to the ```UC7_QueueListnerProxy``` queue, WSO2 Message Broker (MB) that will manage the three JMS queues that we are using for this use case.

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

5. Create 3 Queues as follows in WSO2 MB 2.2.0

* javaVendor
* otherVendor
* UC7_QueueListnerProxy

WSO2 Enterprise Service Bus 4.8.1 configuration
-----------------------------------------------

1. Copy the related configuration files OR  
refer ```Setting up WSO2 ESB``` section of [Integrating WSO2 MB with WSO2 ESB](https://docs.wso2.com/display/MB220/Integrating+WSO2+ESB).

2. Start WSO2 ESB 4.8.1

Testing
-------

* Login to WSO2 MB admin web console
* Go to Home -> Manage -> Queues -> Browse
* Locate ```UC7_QueueListnerProxy``` and click on Publish messages
* Send the messages to queue ```UC7_QueueListnerProxy``` as follows.

| Attribute | Value |
|:-------|:-------|
| Number of Messages | 2 |
| Message body text | ```<m:getQuote xmlns:m="http://services.samples"><m:request><m:symbol>IBM</m:symbol></m:request></m:getQuote>```|

You can see the ESB console output as follows.

```
[2015-04-29 10:54:46,770]  INFO - LogMediator SYMBOL*********** = IBM
[2015-04-29 10:54:46,770]  INFO - LogMediator ACCEPT********* = javaVendorEndPoint
[2015-04-29 10:54:46,770]  INFO - LogMediator located = javaVendorEndPoint
```

Initially message will be stored in ```UC7_QueueListnerProxy``` queue. Instantly the message will be processed  
and will be placed in ```javaVendor``` queue as per the rule logic. 
 
Usecase task flow
----------------- 
 
**STEP 1 - Proxy (Message Acceptance from Queue)**

Message formats  

```
<m:getQuote xmlns:m="http://services.samples">
    <m:request>
        <m:symbol>IBM</m:symbol>
    </m:request>
</m:getQuote>


<m:getQuote xmlns:m="http://services.samples">
    <m:request>
        <m:symbol>MFST</m:symbol>
    </m:request>
</m:getQuote>
```

**STEP 2 - Rule Mediator (Endpoint selection based on Rule Library)**

**STEP 3 - Switch Mediator (Send message to selected Endpoint; JMS Queue)**

**STEP 4 - Drop Mediator (To terminate the Mediation sequence)**
