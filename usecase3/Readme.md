Use Case 3 - Instructions
=========================

Products used
-------------
* WSO2 Enterprise Service Bus 4.8.1   
* WSO2 Data Services Server 3.2.1   
* WSO2 Identity Server 5.0.0   
* MySQL DB
* SOAP UI

MySQL Database configuration
----------------------------
Install MySQL database locally and run MySQL/script.sql to create database and tables

WSO2 Data Services Server 3.2.1 Configuration
---------------------------------------------
Create a datasource as follows.

|Attribute | Value|
|:------------|:-------------|
|Data Source Type | RDBMS |
|Name | EmployeeDataSource | 
|Data Source Provider | default | 
|Driver | com.mysql.jdbc.Driver | 
|URL | jdbc:mysql://localhost:3306/dss_sample | 
|UserName | root |  
|UserName | rootPassword |

Note: Auto generate the Data service from datasource created above.

Sample data service configuration EmployeesDataService.dbs is attached for your reference.

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
* Extract the access_token part i.e. e3443b37211e299be2c8f3aee7f73519
which is to be included in the Authorization Header value field of the SOAP UI
project (usecase3-soapui-project.xml)  
e.g.: Bearer e3443b37211e299be2c8f3aee7f73519

WSO2 Enterprise Service Bus 4.8.1 - Configurations
-------------------------------
Create the two sequences and add them to the registry
as per the repository/deployment/synapse-configs/registry-sequences

Add the API as per OrganizationalInfoAPI.xml

Testing 
-------
Open the SOAP UI Project usecase3-soapui-project.xml
Use the access_token generated in Configuring WSO2 IS 5.0.0
Send JSON request from the SOAP project

Sample Request JSON payload sent.  

```
{"employee":{"firstName":"Walter","lastName":"White","team":"IT"}}
```

Sample Response received is as follows.  

```
{"EmployeeRecord":{"EmployeeID":14, "Status":"Successfully created"}}
```
