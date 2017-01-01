#introduction to pandas
from pandas import Series, DataFrame
import pandas as pd

obj = Series([1,2,3],index = ['a','b','c'])
obj['a'] #1
obj.index #Index([u'a', u'b', u'c'], dtype='object')

obj[obj > 2] #boolean indexing
np.exp(obj) #numpy calculating

data = {'a':1, 'b':2, 'c':3, 'd':4}
obj = Series(data)

obj = Series(data, index= ['a', 'b', 'c', 'd', 'e', 'f']) #e,f get assigned an NaN value
obj.isnull() #return a series of boolean variables
obj.isnull().sum() #return the number of NaN values
obj.name = 'Test'
obj.index.name = 'Index_column'

#DataFrame
data = {'state':['Ohio','Ohio','Nevada','Nevada'],
		'year':[2001,2002,2001,2002],
		'pop':[1.5,1.6,1.7,1.8]}
frame = DataFrame(data, columns = ['year', 'pop', 'state'], index =['a','b','c','d']) 
frame.columns #return the name of the columns
#indexing DataFrame
frame['year'] #return 'year' column
frame.year #another way of column indexing 

frame['new_col'] = frame.year #attach a new column
del frame['new_col'] #delete a column
frame.values #return the data contained as a numpy array
frame.reindex(['e','d','c','b','a'], fill_value = 0) #reindexing

frame2 = DataFrame(np.arange(9).reshape((3,3)),index = ['a','b','c'], columns = ['col1','col2', 'col3'])
frame2.reindex(index = ['a','b','c','d'], columns = [1,2,3])

#dropping items
frame.drop('year', axis = 1) #delete the year column
frame.drop('a') #delete row a

#indexing in Series
'''note that slicing with labels in Pandas include both ends'''
obj['a':'c'] #return a,b and c

frame[:2] #the first two rows
frame[frame.year == 2001]

frame.ix[['a','b'],['year','pop']] #numpy-like indexing with ix
frame.ix[frame.year == 2001,:2]

df1 = DataFrame(np.arange(9).reshape((3,3)))
df2 = DataFrame(np.arange(12).reshape((4,3)))
df1 + df2 #causing NaN values
df1.add(df2,fill_value = 0) #with no overlapping values

frame = DataFrame(np.arange(9).reshape((3,3)))

frame.apply(lambda x:x.max() - x.min(), axis = 1) #return the range of each row
frame.apply(lambda x:Series([x.min(),x.max()], index = ['min','max']), axis = 1)

#sorting
obj = Series(range(4),index = ['d','a','b','c'])
obj.sort_index()
obj.order() #sort by value
obj.rank() #return the rank of value

frame = DataFrame(np.arange(9).reshape((3,3)),index = [2,1,3], columns = ['c','b','a'])
frame.sort_index(axis = 1)
frame.sort_index(by = ['a','c']) #sort by a specific column
frame.rank(axis =1)

frame.idxmax() #return the position of max value
frame.describe() #showing the basic statistics of the dataset
frame.head(2) #show the top 2 rows of dataset
frame.tail(2) #show the bottom 2 rows of dataset

#unique value, value counts and membership
obj = Series(['c','a','a','b','b','b','d'])
obj.unique() #return the unique elements in obj 
obj.value_counts() 
pd.value_counts(obj)	
obj[obj.isin(['b','c'])] #filter only elements b and c

data = DataFrame(np.arange(12).reshape((3,4)),columns = ['a','b','c','d'])
data.apply(pd.value_counts).fillna(0)

#missing data
obj[:2] = np.NaN
obj.dropna() 

data.ix[[0,2],[1,2]] = np.nan
data.dropna() #default drops all the rows the NAN appears
data.dropna(how = 'all') #only drop rows with all NaNs
data.dropna(thresh = 2) #keep the rows with at least 2 non-NaN value 

data.fillna(0) #fill in with 0
data.fillna({'b':1, 'c':2}) #fill in by column
data.fillna(method = 'ffill') #forward fill
data.fillna(method = 'ffill', limit = 2) #forward fill
data.fillna(data.mean()) #fill in with the mean of each column

'''Data Wrangling'''
df1 = DataFrame({'key':['a','b','c'], 'data1':[1,2,3]})
df2 = DataFrame({'key':['a','c'], 'data2':[3,4]})
pd.merge(df1,df2, on = 'key') #the names of the columns are the same
pd.merge(df1,df2, left_on = 'key', right_on = 'key') #if the names of the columns are different
df1.join(df2, rsuffix = '_2') #join by index, suffix is used when there're columns with replicated name

s1 = Series([0,1], index = ['a','b'])
s2 = Series([2,3,4], index = ['c','d','e'])
s3 = Series([5,6], index = ['f','g'])
pd.concat([s1,s2,s3]) #defualt concatenating along axis = 0
pd.concat([s1,s2,s3],keys = [1,2,3]) #hierarchical indexing

df1 = DataFrame(np.arange(6).reshape((3,2)),index = ['a','b','c'], columns = ['one', 'two'])
df2 = DataFrame(np.arange(4).reshape((2,2)),index = ['a','c'], columns = ['three', 'four'])
pd.concat([df1,df2], axis = 1, keys = ['level1', 'level2'])
pd.concat([df1,df2],keys = ['level1','level2'], ignore_index = True) #when the index doesn't have meanings

a = Series([1,np.nan,3], index = ['a','b','c'])
b = Series([4,5,6], index = ['a','b','c'])
a.combine_first(b) #first use a 

'''stack pivots from the columns in data to the row
   unstack pivots from the rows to the columns'''
data = DataFrame(np.arange(6).reshape((2,3)), index = pd.Index(['ohio','colorado'], name = 'state'), columns = pd.Index(['one', 'two', 'three'], name = 'number'))
result = data.stack()
result.unstack() #default unstacking the innermost level
result.unstack(0) #unstack the outtermost level

#removing duplicates
data.duplicated() #return a boolean series
data.drop_duplicates(col_name) #remove dupicates based on the column, default considering every column

#mapping on a Series
data = Series([1,2,3])
mapping_rule = {1:'a',2:'b',3:'c'}
data.map(mapping_rule) #only works for pd.Seires

#where clause
df = pd.DataFrame(np.arange(10).reshape(-1, 2), columns=['A', 'B'])
m = (df % 3 == 0)
df.where(m, other = -1) #the number that not dividable by 3 turns into -1

#replacing value
data.replace({1:'a',2:'b'})

#discretization and binning
data = np.arange(20)
bins = [0,5,10,15,20]
data = pd.cut(data, bins)
data = pd.cut(data,4) #cut into 4 equally sized categories
data.value_counts()
pd.qcut(data, [0.1,0.5,1]) #quantile cut

#filter outliers
data = DataFrame(np.random.randn(1000,4))
data[(np.abs(data)>3).any(axis = 1)] #select all rows having a value >3 or <-3
data[np.abs(data) >3] = np.sign(data) * 3 #sign returns an array of 1 and -1 depending on the sign of the value

#permutation and random sampling
df = DataFrame(np.arange(20).reshape(5,4))
sampler = np.random.permutations(5) #without replacement
df.take(sampler) 
df.ix[sampler] #same as take

sampler_with_replacement = np.random.randint(0, len(df), size = 10)
df.ix[sampler_with_replacement]

#dummy variable
df = DataFrame({'key':['b','a','b','a','c'], 'data':range(5)})
dummies = pd.get_dummies(df.key, prefix = 'key')
df_with_dummy = dummies.join(df.data)

values = np.random.rand(10)
bins = [0, 0.2, 0.4, 0.6, 0.8, 1]
pd.get_dummies(pd.cut(values, bins))




'''Ploting and visualization'''
improt matplotlib.pyplot as plt
from numpy.random import randn
fig = plt.figure() #create a new figure
ax1 = fig.add_subplot(2,2,1)
ax2 = fig.add_subplot(2,2,2)
plt.plot(randn(50).cumsum(), 'k--')
ax1.scatter(np.arange(30), np.arange(30) + 3 * randn(30))
plt.show()

fig, axes = plt.subplots(2,3) #easier way to creating subplots

fig, axes = plt.subplots(2,2, sharex = True, sharey = True) #the coordinates will all be the same, good for comparing data
for i in range(2):
	for j in range(2):
		axes[i,j].hist(randn(500), bins=50, color = 'k', alpha = 0.5)
plt.subplots_adjust(wspace = 0 , hspace = 0) #no space between subplots

plt.plot(randn(30).cumsum(),'k--')
plt.plot(randn(30).cumsum(), 'ko--') #'--' for the line style, 'o' for the point style
plt.plot(randn(30).cumsum(), linestyle = '--', color = 'k', marker = 'o')

plt.plot(randn(30).cumsum(), 'k--', label = 'steps-post', drawstyle ='steps-post')
plt.legend(loc = 'best')

plt.xlim()
plt.xticks() #with no input variable returns the current xlim and xticks
plt.xlim([0,20])
plt.xticks(range(0,21,5)) #with the input variable to set new xlim and xticks

fig = plt.figure()
ax = fig.add_subplot(1,1,1)
ax.plot(randn(1000).cumsum(), 'g--')
ticks = ax.set_xticks([0, 250, 500, 750, 1000])
labels = ax.set_xticklabels([1,2,3,4,5],rotation =30, fontsize = 'small')
ax.set_title('Test Plot')
ax.set_xlabel('x')
ax.set_ylabel('y')

fig, axes = plt.subplots(2,1)
data = pd.Series(np.random.randn(16))
data.plot(kind = 'bar', ax = axes[0], color = 'k', alpha = 0.5)
data.plot(kind = 'line', ax = axes[1], color = 'g', alpha = 0.5)

data = pd.Series(np.random.randint(1,10,1000)).value_counts().plot(kind = 'bar', alpha = 0.5) #useful


'''Data aggregation and group operations'''
df = DataFrame({'key1':['a','a','b','b','a'],
	'key2':['one','two','one','two','one'],
	'data1':np.random.randn(5),
	'data2':np.random.randn(5)})

grouped = df['data1'].groupby(df.key1)
grouped.mean() 
df['data1'].groupby([df.key1, df.key2]).mean()
df['data1'].groupby([df.key1, df.key2], as_index = False).mean() #unable multilevel index
df.ix[:,0:2].groupby(df.key1).mean()

df.groupby('key1').mean() #if the key column is in the same dataset, only the column name is enough
df.groupby('key1').size() #number of elements in each group
df.groupby('key1').groups #return a dist of all the groups

pieces = dict(list(df.groupby('key1')))
pieces['a'] #return a DataFrame which key1 == a

#grouping with function
people = DataFrame(np.random.rand(5,5), columns = ['a','b','c','d','e'], index = ['joe','steve','Wes', 'Jim', 'Travis'])
people.ix[2:3,['b','c']] = np.nan
people.groupby(len).sum() #group by the length of the name 

#aggregation
grouped = df.groupby('key1')
grouped.mean()
grouped.agg(['mean', 'std']) #pass your own aggregation function to agg()
grouped.agg([('foo', 'mean'), ('bar', 'std')]) #set column names as foo and bar
grouped.agg({'data1':'mean', 'data2':'max'}) #apply different function to different columns
grouped.describe()

#groupwise Operations and transformations
key = ['one', 'two', 'one', 'two', 'one']
people.groupby(key).transform(np.mean) #replace with group mean
da
'''fill in NA value with group mean'''
data.groupby(group_key).apply(lambda x: x.fillna(x.mean()))

'''filter'''
sf = pd.Series(np.arange(10))
sf.groupby(sf).filter(lambda x: x.sum() > 5)

'''pivot table'''
data.pivot_table(values = , index = , columns = , aggfunc = [len,max], margins = True)

'''Numpy topic'''
arr.flatten() #always return a copy of the data
arr.ravel() #return the view of the data whenever possible, may change the original data
np.hstack(arr1, arr2) #horizontal stack
np.vstack(arr1, arr2) #vertical stack

np.repeat()
np.tile()

arr.sort() #sort in-place, change the original array if it's the view
np.sort(arr) #always return the copy


'''useful pandas function'''
'''select numerical columns'''
data.select_dtypes(include = ['float64', 'int64'])



