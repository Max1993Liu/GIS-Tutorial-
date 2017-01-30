#using NULL value in search conditions
IS NULL #right
= NULL #wrong
CASE WHEN  VAR IS NULL THEN #right
CASE WHEN VAR NULL THEN #wrong

#Case clause
SELECT DISTINCT product.model, 
 CASE 
 WHEN price IS NULL 
 THEN 'Not available' 
 ELSE CAST(price AS CHAR(20)) 
 END price 
FROM Product LEFT JOIN 
 PC ON Product.model = PC.model
WHERE product.type = 'pc';

#Cartisian product
SELECT Laptop.model, Product.model
FROM Laptop, Product;
#The number of rows in the result will be a*b

