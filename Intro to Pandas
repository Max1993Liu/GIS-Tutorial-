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

frame[:2]
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

  
