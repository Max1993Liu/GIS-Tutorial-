#ggplot2 tutorial
library(ggplot2)
library(dplyr)

data <- read.delim('http://bit.ly/avml_ggplot2_data')

p <- ggplot(data, aes(x = Dur_msec, y = F1.n)) #1. data layer

p <- p + geom_point( color = 'red', size= 2) #2. geometries layer

p <- p + stat_smooth(color = 'blue') #3. add a smooth line

p <- p + scale_x_log10(breaks = c(50,100,200,300,400)) + scale_y_reverse() #scale transformation

p <- p + ylab('Normalized F1') + xlab('Vowel Duration') + theme_bw() #change the labels

data %>% group_by(Word) %>% summarise(n = n()) #most of the word is I

data2 <- data[data$Word != 'I',]

ggplot(data2, aes(Dur_msec, F1.n, color = Word)) + geom_point()

ggplot(data2, aes(Dur_msec, F1.n, color = Word)) + geom_point() + geom_line() #The line also inheritate the color

ggplot(data2, aes(Dur_msec, F1.n)) + geom_point() + geom_line() #Note the difference on sub-grouping

ggplot(data2, aes(Dur_msec, F1.n)) + geom_point(aes(color = Word)) + geom_line() #Note the difference on sub-grouping

ggplot(data2, aes(Dur_msec, F1.n)) + geom_point() + geom_line(aes(color = Word)) #Note the difference on sub-grouping

ggplot(data2, aes(Dur_msec, F1.n, color = Word)) + geom_point() + geom_line() #Note how the line is connected

ggplot(data2, aes(Dur_msec, F1.n, color = Word)) + geom_point() + geom_path() #The line connects according to the order in dataset

ggplot(data2, aes(Dur_msec, F1.n, color = Word)) + geom_point() + geom_line() + stat_smooth(se = F) #se = F not showing standard error

ggplot(data2, aes(Dur_msec, F1.n)) + geom_point(aes(color = Word)) + geom_line() + stat_smooth() #se = F not showing standard error

ggplot(data2, aes(Dur_msec, F1.n, color = Word)) + geom_point() + geom_line() + stat_smooth(se = F, aes(group = 1)) #Overiding aes

ggplot(data, aes(Word, color = Word)) + geom_bar() #color vs. fill, also note that there's only one data column in aes

ggplot(data, aes(Word, fill = Word)) + geom_bar(color = 'black') 

ggplot(data2, aes(Name, fill = Word)) + geom_bar(color = 'black') #suggest using black color as border line in bar chart

ggplot(data2, aes(Dur_msec, F1.n)) + geom_text(aes(label = Word)) #adding text labels, special aes for geom_text:label

'''move on to geoms, as inheritance is important for aesthetics, positioning is the big idea for geoms
1. identity: default in most cases, ploting geometries where they\'re defined by x and y
2. jitter: add a little random noise, typically just for points
3. stack: stacks geometries on top of each other, default for bars
4. dodge: pushes geometries out of each other\' way to the left and right
5. fill: stacks geometries on top of each other , and expands or contracts them to fill the space between 0 and 1. 
Good for plotting proportions.'''

ggplot(data,aes(Word, F1.n, color = Word)) + geom_point()

ggplot(data,aes(Word, F1.n, color = Word)) + geom_point(position = 'jitter') #showing the amount of red points

ggplot(data,aes(Word, F1.n, color = Word)) + geom_point() + geom_jitter() #same as the previous one

data2$Dur_cat <- data2$Dur_msec > mean(data2$Dur_msec) #create a new variable

ggplot(data2, aes(Dur_cat, fill = Word)) + geom_bar(color = 'black') #default stacked barchart

ggplot(data2, aes(Dur_cat, fill = Word)) + geom_bar(color = 'black', position = 'dodge') #line up the bars

ggplot(data2, aes(Dur_cat, fill = Word)) + geom_bar(color = 'black', position = 'fill') #proportion

ggplot(data2, aes(Dur_msec, fill = Word)) + geom_density(alpha = 0.5, position = 'stack', aes(y = ..count..))

ggplot(data2, aes(Word, F1.n)) + geom_dotplot(binaxis = 'y', stackdir = 'center',color = 'white', aes(fill = Word)) 

'''Statistics Rule:
1. Every geometry displays a statistics and every statistics are displayed with a geometry.
2. All statistics have a default geometry, which can be overrode'''

ggplot(data, aes(Dur_msec, F1.n)) + stat_smooth(geom = 'point') #Rule No.2

ggplot(data, aes(Dur_msec,F1.n)) + geom_point(stat = 'smooth', method = 'loess') #Ruel No.1

ggplot(data, aes(Dur_msec, F1.n)) + stat_smooth(method = lm, formula = y ~ poly(x, 4))

ggplot(data, aes(Word, Dur_msec)) + stat_summary(fun.y = mean, geom = 'bar', fill = 'purple')

ggplot(data, aes(Word, Dur_msec)) + stat_summary(fun.y = mean, geom = 'bar') + stat_summary(fun.data = mean_cl_normal, geom = 'pointrange')

ggplot(data, aes(Dur_msec, color = Word)) + geom_density()

ggplot(data, aes(Dur_msec, color = Word)) + geom_density(aes(y = ..count..))

#ggplot(data, aes(-F2.n)) + geom_density(aes(y = ..count.., fill = 'red')) 

ggplot(data, aes(-F2.n, F1.n))  + stat_density2d(geom = 'polygon', aes(fill = ..level..))

ggplot(data, aes(-F2.n, F1.n)) + stat_density2d(contour = F, geom = 'point', aes(color = ..density..), alpha = 0.3)

ggplot(data, aes(-F2.n, F1.n)) + stat_density2d(contour = F, geom = 'tile', aes(fill = ..density..))

ggplot(data, aes(-F2.n)) + geom_histogram(aes(y = ..count..,fill = ..count..))

#scale_[aes]_
ggplot(data, aes(Word, Dur_msec, fill = Word)) + stat_summary(fun.y = mean, geom = 'bar') + scale_fill_hue(name = 'legend Item')

library('RColorBrewer')
ggplot(data, aes(Word, Dur_msec, fill = Word)) + stat_summary(fun.y = mean, geom = 'bar') + scale_fill_brewer(palette = 'Set2')

ggplot(data, aes(-F2.n, F1.n)) + stat_density2d(contour = F, geom = 'tile', aes(fill = ..density..)) + 
  scale_fill_gradient(low = 'darkblue', high = 'darkred')

ggplot(data, aes(-F2.n, F1.n)) + stat_density2d(contour = F, geom = 'tile', aes(fill = ..density..)) + 
  scale_fill_gradientn(colors = rainbow(10))

#faceting
data$Dur_cat <- data$Dur_msec > mean(data$Dur_msec)

ggplot(data, aes(-F2.n, -F1.n)) + geom_point() + facet_wrap('Word')

ggplot(data, aes(-F2.n, -F1.n)) + geom_point() + facet_wrap(c('Word','Dur_cat'))

ggplot(data, aes(-F2.n, -F1.n)) + geom_point() + facet_grid(Dur_cat~Word) + theme_bw()

#for additional options
#legend position
ggplot(data, aes(-F2.n, F1.n)) + stat_density2d(contour = F, geom = 'tile', aes(fill = ..density..)) + theme(legend.position = 'bottom')

#adding titles 
+labs(x = 'xlab', y = 'ylab', title = 'title')

