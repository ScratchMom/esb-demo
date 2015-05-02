Use Case 3
==========

In this use case a RESTful web service is hosted using OAuth authentication.  
A request will be saved into a database and the response will be returned to the service consumer.  
This request and response should be in JSON format.  

In this scenario three WSO2 products are used, WSO2 Enterprise Service Bus (ESB) that will host the RESTful web service, WSO2 Identity Server (IS) that will manage the OAuth authentication and WSO2 Data Services Server (DSS) that will manage data services. Locally installed MySQL database will be used throughout the scenario. SOAP UI is used to send JSON requests to the REST API. The complete scenario can be tested on a single computer instance.

Products used
-------------
* WSO2 Enterprise Service Bus (ESB) 4.8.1   
* WSO2 Identity Server (IS) 5.0.0  
* WSO2 Data Services Server (DSS) 3.2.1   
* MySQL DB
* SOAP UI

| Server | Offset | URL |
|:-------|:-------|:-------|
| WSO2 ESB | 0 | https://localhost:9443/carbon |
| WSO2 IS | 1 | https://localhost:9444/carbon |
| WSO2 DSS | 10 | https://localhost:9453/carbon |

You can simply change the port offset from ```<PRODUCT_HOME>/repository/conf/carbon.xml``` as follows: change the ```<Ports>``` section, ```<Offset>1</Offset>```. Once you change the carbon.xml file, start/restart the server.

MySQL Database configuration
----------------------------
Install MySQL database locally and run [script.sql](MySQL/script.sql) to create database and tables

WSO2 Data Services Server 3.2.1 Configuration
---------------------------------------------
* Create a datasource as follows by referring the MySQL database.  
Configure -> Data Sources -> Add Data Source

|Attribute | Value|
|:------------|:-------------|
|Data Source Type | RDBMS |
|Name | EmployeeDataSource | 
|Data Source Provider | default | 
|Driver | com.mysql.jdbc.Driver | 
|URL | jdbc:mysql://localhost:3306/dss_sample | 
|UserName | root |  
|UserName | rootPassword |

* Auto generate the Data service from datasource created above.  
Main -> Services -> Data Services -> Generate

**Carbon Datasource Details**  

|Attribute | Value|
|:--------|:---------|
| Carbon Datasource(s) | EmployeeDataSource |
| Database Name | dss_sample |

**Select Table(s)**  
Press Next

**Select Service Generation Mode**  
Single Service

|Attribute | Value|
|:--------|:---------|
| Data Service Namespace | http://employees.us.wso2.com |
| Data Service Name | EmployeesDataService |

When you go to newly created EmployeesDataService Dashboard under operations you can see that five default operations automatically created when the data service was generated based on the data source we created earlier.  
We can use them for our usecase without having to define new queries and operations.

*Note: Sample data service configuration [EmployeesDataService.dbs](DSS321/repository/deployment/server/dataservices/EmployeesDataService.dbs) is attached for your reference only.*

WSO2 Identity Server 5.0.0 - Getting the access_token
-----------------------------------------------------
* create a service provider
* under inbound authentication add an application under OAuth/OpenID Connect configs sub menu
* Save OAuth client key and OAuth client secret  
e.g.:  
```
dT5XgvH7sm_ryIJXce9j281mezYa
LJzBSzwkUAziq_sKelBg8odRUzMa
```  
* Issue the following curl command to get access_token  
```
curl -v -k -X POST --user dT5XgvH7sm_ryIJXce9j281mezYa:LJzBSzwkUAziq_sKelBg8odRUzMa -H "Content-Type: application/x-www-form-urlencoded;charset=UTF-8" -d 'grant_type=password&username=admin&password=admin' https://localhost:9444/oauth2/token
```  
You will get a response as follows.  
```
{"token_type":"bearer","expires_in":3599999700,"refresh_token":"3662fae89f3bf7e5e1f912933a3191e3","access_token":"e3443b37211e299be2c8f3aee7f73519"}
```  
* Extract the access_token part i.e. ```e3443b37211e299be2c8f3aee7f73519```   
This is to be included in the Authorization Header value field of the SOAP UI
project [usecase3-soapui-project.xml](usecase3-soapui-project.xml) in following steps.  
e.g.: Bearer ```e3443b37211e299be2c8f3aee7f73519```

WSO2 Enterprise Service Bus 4.8.1 - Configurations
-------------------------------
* Create the two sequences as per [ProcessPayloadForEmpDSSSequence.xml](ESB481/repository/deployment/synapse-configs/registry-sequences/ProcessPayloadForEmpDSSSequence.xml) and [processResponseFromEmpDSSService.xml](ESB481/repository/deployment/synapse-configs/registry-sequences/processResponseFromEmpDSSService.xml) and add them to the registry  

* Create the API as per [OrganizationalInfoAPI.xml](ESB481/repository/deployment/synapse-configs/default/api/OrganizationalInfoAPI.xml)

Testing 
-------
* Open the SOAP UI Project [usecase3-soapui-project.xml](usecase3-soapui-project.xml)
* Edit the IP addresses of the API according to your server IP
* Use the access_token generated in Configuring WSO2 IS 5.0.0
* Send JSON request from the SOAP project

Sample Request JSON payload sent.  
```
{"employee":{"firstName":"Walter","lastName":"White","team":"IT"}}
```

Sample Response received is as follows.  
```
{"EmployeeRecord":{"EmployeeID":14, "Status":"Successfully created"}}
```

Usecase task flow
-----------------

**STEP 1 - Sequence 1**  

1. OAuth mediator Authorization
2. Convert payload from JSON to XML
3. Payload Factory message xml request generation for DSS

**STEP 2 - Call DSS data service**  

1. DSS to query MySQL database and Insert database record and get response 

**STEP 3 - Sequence 2**  

1. Convert received message to customized xml format
2. Convert payload from XML to JSON
