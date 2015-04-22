Use Case 7 
==========

WSO2 Message Broker 2.2.0 Configuration
---------------------------------------

Port offset 0

1. [Optional step]
If you are running demo files on Mac OS X
Copy the libsnappyjava.jnilib file to [MB_HOME] from snappy-1.1.1-Mac-x86_64 folder.

2. Copy other necessary configuration files

3. Start WSO2 MB 2.2.0

4. Login to admin console

5. Create 3 Queues as follows in WSO2 MB 2.2.0

* javaVendor
* otherVendor
* UC7_QueueListnerProxy

WSO2 Enterprise Service Bus 4.8.1 configuration
-----------------------------------------------

Port offset 2

1. Copy the related configuration files

2. Start WSO2 ESB 4.8.1

Testing
-------

Login to WSO2 MB admin web console

Go to Home -> Manage -> Queues -> Browse

Locate UC7_QueueListnerProxy and click on Publish messages

Send the messages to queue UC7_QueueListnerProxy as follows.
* Set the Number of Messages, e.g.: 2
* Set the message body, e.g.:

<m:getQuote xmlns:m="http://services.samples">
    <m:request>
        <m:symbol>IBM</m:symbol>
    </m:request>
</m:getQuote>

