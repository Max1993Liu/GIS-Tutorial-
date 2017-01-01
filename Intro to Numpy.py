#introduction to Numpy
import numpy as np

#creating array
data = [1, 2, 3, 4, 5]
arr = np.array(data)

data2 = [[1,2],[3,4]]
arr2 = np.array(data2, dtype = np.float64) #result will be 2-dimentional


data3 = [[1,2],[3,4,5]]
arr3 = np.array(data3) #result will be 1-dimensional


np.zeros(10) #new 2*3 ndarray with all zeros

np.zeros((2,3)) #new 2*3 ndarray with all ones

np.empty((2,3,2))

np.arange(5) #array([0, 1, 2, 3, 4])

np.zeros_like(data2) #ndarary with same shape as data2, but with all 0s

np.identity(3) #identity matrix

#dimension
arr.ndim
arr.shape
arr.dtype

#dtype in numpy
arr = np.array([1,2,3,4,5])
float_arr = arr.astype(np.float64)

arr = np.array([1.2, 2.3, 3.5, 4.5])
truncate_arr = arr.astype(np.int32)

arr = np.array(['1.2', '33', '12'])
num_arr = arr.astype(np.float64)

#indexing
arr = np.arange(10)
arr[5]
arr[1:5]

'''Be careful! Making changes to the slice of the 
original array will actually change the original array'''
arr_slice = arr[1:5]
arr_slice[:] = 100
arr #array([  0, 100, 100, 100, 100,   5,   6,   7,   8,   9])

arr[5:8].copy() #if you want an actual copy of the original array

arr = np.arange(20)
arr.shape = (4,5)
arr[2] #return the third row
arr[2,0] #return the first element of the third row
arr[2][0] #return the first element of the third row
arr[:2] #return first two rows
arr[:2,1:]
arr[:,1:]

#boolean indexing
index = np.array(['a','b','c','a'])
data = np.random.randn(4,2) #4*2 array of random number
data[index == 'a'] #return the first and last row of data, note that it can't be replaced a python boolean list

'''note that the and or operation doesn't work for boolean array'''
data_index = (index == 'a') | (index == 'b') 
data[data_index]

data[data<0] #return 1d array with all negative numbers

#fancy indexing
arr = np.empty((8,4))
for i in range(8):
	arr[i] = i
arr[[3,2,1]]

arr = np.arange(16).reshape((4,4))
arr[[0,1,2,3],[0,1,2,3]] #return the diagnol elements

#transpose
arr = np.random.randn(12).reshape((3,4))
np.dot(arr.T, arr) #covariance matrix

#elementwise calculations
arr = np.random.randn(12)
np.sqrt(arr)
np.exp(arr)
x = randn(8)
y = randn(8)
np.maximum(x,y)

#expressing conditional logic as array operations
x = np.arange(1,4)
y = np.arange(-4,-1)
cond = np.array([True,False,True])
result = [(x if c else y) for x,y,c in zip(x,y,cond)] #The pythonic way of doing this

x = np.arange(1,4)
y = np.arange(-4,-1)
cond = np.array([True,False,True])
np.where(cond, x, y) #Faster way

arr = np.random.randn(4,4)
np.where(arr > 0, 1,-1) #Positive numbers to 1, negative numbers to -1
np.where(arr >0, 1, arr) #Only replace the positive numbers

#statistical methods
arr = np.arange(10).reshape(2,5)
arr.mean() #total average
arr.mean(axis = 0) #columnwise average
arr.mean(1) #rowwise average
arr.sum() 

arr.cumsum(0) #cumulativ sum along the column
arr.cumprod(1) #cumulative product along the row

arr.argmin(1) #return the index of the minimum value
arr.argmax(1)

#boolean arrays
arr = np.random.randn(100)
(arr > 0).sum() #return the number of positive numbers

bool_arr = np.array([True, True, False])
bool_arr.any()
bool_arr.all()

arr = np.arange(-5,5)
arr.all() #it also walks with the numeric array with 0 as False
arr.any()

#Sort
arr = np.random.randn(10)
arr.sort() #sort in place
sorted_arr = np.sort(arr) #make a copy

arr = np.random.randn(4,4)
np.sort(arr, axis = 0) #sort each column by their own
arr[:,np.argsort(arr[0])] #sort each row regarding to the first row

#set operations
names = np.array(['Bob','Joe','Bob','Will','Joe'])
np.unique(names) #return a sorted distinct array of names

np.in1d(names, ['Bob','Joe'])
np.intersect1d(names,['Bob','Joe'])

arr = np.loadtxt('filename.txt', delimiter = ',')
np.savetxt('filename.txt',arr)
