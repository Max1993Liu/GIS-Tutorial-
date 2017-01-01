#intro to data.table
#why data.table? Because it's FAST and AWESOME!
library(data.table)
library(plyr)

#creating data.table, same as data frame
DT = data.table(a = c(1,2,3), b = c(3,2,1))

#data frame >> data.table
cars = data.table(mtcars)

cars[gear == 4] 
#note the difference in indexing between DT and dataframe
#1. We don need cars[cars$gear == 4]
#2. No need to specify columns
cars[c(T,F)] #return only the odd rows
cars[,2,with = FALSE] #better to set with = FALSE when indexing with number in data.table

#check out all the data tables we have
tables()

#the KEY!
setkey(cars, gear)

#see what happens
tables()
cars  #it's sorted by the key variable

#making indexing even more easier
cars[.(4)] #now you only need to give the values!, .() for quoted expression, since it's a number
cars[!.(4)] #all rows with gear not equal to 4

#we can set keys using two columns as well
setkey(cars, gear, cyl)

cars[list(4,4)] #indexing with two key columns
cars[.(4,4)]

#grouping
cars[,mean(wt)]
cars[.(4,4),mean(wt)]
cars[, .(mean = mean(wt)), by = carb]
cars[, .(mean = mean(wt), std = sd(wt)), by = carb]


#from example(data.table)
DT = data.table(x=rep(c("a","b","c"),each=3), y=c(1,3,6), v=1:9)
X = data.table(c("b","c"),foo=c(4,2))
setkey(DT, x)

DT[X] #join
DT[X,sum(v),by= .EACHI] # join and eval j for each row in i
DT[X,sum(v)*foo,by=.EACHI] # join inherited scope

setkey(DT, x, y)
DT[.('a',3:6), nomatch = 0] #remove NA row
DT[.('a',3:6), roll= T] #forward filling 

#add new column 
DT[, z := 32]
DT['a' ,z := 16] #partial assignment
DT[, m := mean(v), by = x][] #postfix p[] to print 

#on clause
DT1 = data.table(x=c("c", "a", "b", "a", "b"), a=1:5)
DT2 = data.table(y=c("d", "c", "b"), mul=6:8)

DT1[DT2, on = c(x = 'y')] #simlar to left join on DT1



