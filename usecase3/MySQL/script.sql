create database dss_sample;
use dss_sample;
CREATE TABLE Employee(EmployeeID int PRIMARY KEY, FirstName varchar(255), LastName varchar(255),Team varchar(255));
INSERT INTO Employee VALUES (100 , 'Nuwan', 'Bandara', 'Solutions');
INSERT INTO Employee VALUES (101 , 'Suhan', 'Dharmasuriya', 'TestAuto');
INSERT INTO Employee VALUES (102 , 'Sajini', 'De Silva', 'MB');
INSERT INTO Employee VALUES (103 , 'Kasun', 'Indrasiri', 'ESB');
CREATE PROCEDURE GetEmployeeByID(IN EmpID int) SELECT FirstName , LastName , Team FROM Employee WHERE EmployeeID = EmpID;
CREATE PROCEDURE addEmployee(EmployeeID int(11),FirstName VARCHAR(255),LastName VARCHAR(255),Team VARCHAR(255)) 
INSERT INTO Employee VALUES (EmployeeID, FirstName, LastName, Team);
