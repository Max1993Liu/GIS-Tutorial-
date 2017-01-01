##### DO SOME PRE-SET UPS#####
setwd(file.choose())
setwd('/Users/Max/Documents/')
getwd()

install.packages('plyr')
library('plyr')
install.packages('sqldf')
library('sqldf')

##### NOW SOME BASIC DATA TYPES#####

####### Vectors ############
x1 <- c(1,2,3)
x2 <- c('a','b','c')
x3 <- c(x1,x2)
x3[4]                           #indexing in vector,not like python
x3[-4]                          #also different from python
x4 <- seq(1,5)                  #generate a sequence of number
x3[x4]                          #indexing using sequence
x5 <- rep(1:3,5)                #generate repetitions
rep(1:3,5,each=3)               #variation of repetitions

####### Matrix #############
mat <- matrix(seq(1,12), nrow = 3, byrow=T) #matrix can only hold a single type of data
mat[2,3]                        #indexing in matrix
mat[2,]
nrow(mat)                       
ncol(mat)
rownames(mat)<- c('a','b','c')
colMeans(mat)
rowMeans(mat)
rowSums(mat)
colSums(mat)
mat <- cbind(mat,c(1,2,3))
mat <- rbind(mat,c(1:5))
dim(mat) <- c(4,3) 

###### Data frame ##########  
dat <- data.frame(a=c(1,2,3),b=c('a','b','c'),c=c(1,2,3)) #data frame can hold different type of data
mtcars$cyl                      #indexing in data.frame
as.data.frame(mat)

###### List ###############
li <- list(mat,x3)
li[[1]]                         #indexing in list

##### Simple Indexing and manipulation ###
head(mtcars)                   #First glance
str(mtcars)

mtcars[seq(1,31,2),]            #only observation from  odd rows
mtcars[order(mpg),]             #by order in mpg

diff(range(mtcars$mpg))         #calculate the range

sum(mtcars$cyl>=6)              #number of cars w/ more than 6 cylinders
mtcars[mtcars$cyl>=6,]          
mean(mtcars[mtcars$cyl>=6,]$mpg)  #average miles per gallon for cars w/ more than 5 cylinders

attach(mtcars)
cyl
mtcars$cyl
detach(mtcars)

tab <- table(cyl)                          #using table function to do the counting
prop.table(tab)

tab2 <- table(cyl,vs)
prop.table(tab2)
margin.table(tab2,2)

aggregate(x = mtcars$mpg, by=list(cyl,vs),mean)
aggregate(mpg ~ cyl + vs,data=mtcars, mean)

apply(mtcars,1,sum)                #rowsum 
apply(mtcars,1,function(x){x+2})  

lapply(list(cyl,mpg),sum)          #takes in and returns a list

sapply(list(cyl,mpg),sum)
sapply(list(cyl=cyl,mpg=mpg),sum)

l<- list(a=c(1,2,3),b=c(1,2,3))
mapply('*',l$a,l$b)                #mind the order of the variables


hist(mpg,breaks=12,col='blue',freq=F)
lines(density(mpg),lwd=2)

x.fit <- seq(10,50,length.out = 1000)
y.fit <- dnorm(x.fit,mean=mean(mpg),sd=sd(mpg))
lines(x.fit,y.fit,lwd=2,col='red',lty=2)

boxplot(mpg ~ cyl,data=mtcars)     
pairs(mpg~hp+wt,upper.panel=NULL) 
plot(mpg~wt,data= mtcars,pch=19,type='p',cex=1.5)

lm.fit <- lm(mpg~wt,data=mtcars)
summary(lm.fit)

abline(lm.fit)                              
abline(v = seq(2,5),h=seq(10,30,by=5))

library('car')
scatterplotMatrix(~mpg+wt+hp,data=mtcars,lty.smooth=2,spread=F)

library('scatterplot3d')
scatterplot3d(wt,mpg,disp,pch=19,highlight.3d = T,type='h')

######### Using SQL in R ##########
sqldf('select * from mtcars')
sqldf('select avg(mpg) as averageMpg,cyl from mtcars where cyl>5 group by cyl')

















library('ggplot2')
x<- rnorm(500)
ggplot() + geom_histogram(aes(x),binwidth = 0.1, col='blue', fill='blue',alpha=0.5)

p <- ggplot() + geom_density(aes(x),fill='green', col='blue')

#store the plot and use it in anyway u want
p + coord_cartesian(xlim=c(-2,2))

p + ylab('Mylab')+ggtitle('test')

x <- seq(1,100,by=1)
y <-  rnorm(100)
ggplot()+ geom_point(aes(x = x, y = y))+ geom_smooth(aes(x=x, y=y),method=glm)

#defining global variabal
dat <- data.frame(x=x,y=y)
ggplot(data=dat, aes(x=x,y=y))+geom_point()+ geom_smooth()

#fitting based on a single condition
class <- rep.int(c(0,1), c(50,50))
x <- runif(100, 2)
y <- 5*x + 40 + rnorm(100) + class*(-2)*x
df <- data.frame(input=x , output= y, condition=class)
ggplot(df, aes(x=input, y=output))+ geom_point()+ facet_grid(.~condition)+geom_smooth()+theme_grey()

mtcars
ggplot(mtcars, aes(x=mpg, y=wt)) + geom_point() + geom_smooth()+ facet_grid(.~vs)


