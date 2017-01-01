#installing mysql
#let's create a database to store all tables
CREATE DATABASE test;

#Switch to testdatabase
USE test;

#Let's create our first table in test database
CREATE TABLE contacts
(
	last_name VARCHAR(30) NOT NULL, 
	first_name VARCHAR(20) NOT NULL, #must insert value
	email VARCHAR(50),
	birthday DATE,
	job VARCHAR(50) DEFAULT 'self-employed' #default value
);

#other data types
DEC(a,b) #floating number
INT
CHAR(10) #constant length
BLOB #long text
NULL #note that you can't write xx = NULL, instead you should use xx is NULL


#take a look at the table
DESC contacts; #describe

#delete the table
#be careful!
DROP TABLE contacts;

#insert values into our table
INSERT INTO contacts
(last_name, first_name, email)
VALUES
('Liu','Zhehao', 'aa@gmail.com');

#you can also insert multiple rows at the same time
INSERT INTO contacts
(last_name, first_name, email)
VALUES
('Green','Dan','dg@gmail.com'),
('leeman','George','lg@gmail.com');

#you can insert without the column names
INSERT INTO contacts
VALUES
('Walker','Johnny','jw@gmail.com','1988-10-12','Police');

#the LIKE clause
# '#' for any amount of characters
# '_' for a single character

#the IN clause
SELECT * 
FROM data
WHERE value IN ('a','b','c');


#the NOT clause
#NOTE that NOT ALWAYS follows right after WHERE, AND, OR
SELECT * 
FROM data
WHERE NOT col1 BETWEEN 1 AND 5;

#delete
DELETE 
FROM data
WHERE col1 = x;

#delete all rows
DELETE 
FROM data

#update
UPDATE data
SET col1 = a,col2 = b
WHERE col1 = c, col2 = d;

Road to normalization
step1 原子性
规则1:具有原子性的数据的列中不会有多个类型相同的值
规则2:具有原子性数据的表中不会有多个存储同类型数据的列

step2 每个数据行必须含有独一无二的识别项目(primary key)
主键不可以为NULL

#show the original creat table 
SHOW CREATE TABLE table;


#set up primary key
CREATE TABLE contacts
(
	ID INT NOT NULL AUTO_INCREMENT,
	first_name VARCHAR(30),
	last_name VARCHAR(20),
	PRIMARY KEY (ID)
);


#alter table
ALTER TABLE contacts
ADD COLUMN phone_number VARCHAR(30) FIRST; #adding FIRST to put phone_number into the first column

ALTER TABLE contacts
DROP COLUMN phone_number;

ALTER TABLE contacts
ADD COLUMN phone_number VARCHAR(30) AFTER first_name;

ALTER TABLE contact
CHANGE COLUMN first_name f_n VARCHAR(20) FIRST; #change the name and data type of a column

#if you only want to change the data type, then use modify
ALTER TABLE contact
MODIFY COLUMN first_name varchar(120) FIRST;

#remove primary key
ALTER TABLE contacts
DROP PRIMARY KEY;

#add another column, whose value is relevant to an existing column
ALTER TABLE contacts
ADD COLUMN first_letter VARCHAR(1);

UPDATE contacts
SET first_letter = LEFT(last_name, 1);

#Case when
UPDATE table
SET new_column = 
CASE 
	WHEN col1 = 'a' THEN 'c'
	WHEN col1 = 'b' THEN 'd'
	ELSE 'e'
END;

#limit clause
SELECT * FROM contacts
LIMIT 10   #showing only 10 rows

SELECT * FROM contacts
LIMIT 0, 2  #showing 2 rows starting from row 0

Foreign key constrain:
1. Referential integrity: the value of the foreign key must within parent key
2. The parent key doesnt neccesarily be the the primary key of the parent table, 
it can be any column in the parent table with non-overlapping values

#adding foreign key constraint
CREATE TABLE table2
(
ID VARCHAR(20) NOT NULL PRIMARY KEY,
FOREIGN KEY(ID) REFERENCES contacts (ID)
);


For a multiple vs. multiple situation we need a junction table

2NF: include the function dependency, in which a certain row is depending on the other rows of the table
1. In the form of 1NF
2. No partial dependency

3NF:
1. in the form of 2NF
2. No transitional function dependency (Non-key columns depend on each other)


#workflow of create, insert and alter
CREATE TABLE new_table
(
ID INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
profession VARCHAR(20) NOT NULL
)
AS
SELECT DISTINCT profession FROM contacts;

#sub-queries as the substitution of join 
1. 
SELECT a.col1, b.col2 
FROM 
	table1 a INNER JOIN table2 b 
	ON a.index_col = b.index_col
WHERE b.col2 = value

2.
SELECT col1 
FROM table1
WHERE index_col = (
SELECT index_col
FROM table2
WHERE col2 = value)







EX-----------
#selecting duplicated things
select x from table
group by x
having count(x) > 1;









