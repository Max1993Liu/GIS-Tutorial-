import numpy as np

np.ones((3, 3))

np.zeros((2, 2))

np.eye(3)
#array([[ 1.,  0.,  0.],
#       [ 0.,  1.,  0.],
#       [ 0.,  0.,  1.]])


np.diag(np.array([1, 2, 3, 4]))
#array([[1, 0, 0, 0],
#       [0, 2, 0, 0],
#       [0, 0, 3, 0],
#       [0, 0, 0, 4]])


np.random.rand(4) #uniform in [0, 1]

np.random.randn(4) #gaussian



----------------------------------------
Copies and views

A slicing operation creates a view on the original array
which is just a way of accessing array data. Thus the original 
array is not copied in memory. Use np.may_share_memory() to check
if two arrays share the same memory block

a = np.arange(10)
#array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9])

b = a[::2]
#array([0, 2, 4, 6, 8])

b[0] = 12

a
#array([12,  1,  2,  3,  4,  5,  6,  7,  8,  9])

a = np.arange(10)

c = a[::2].copy()

c[0] = 12

a
#array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9])

np.may_share_memory(a, c)
#False


----------------------------------------
Indexing can be done with an array of integers
When a new array is created by indexing with an array of integers, 
the new array has the same shape than the array of integers

a = np.arange(10)
idx = np.array([[3, 4], [9, 7]])
idx.shape
#(2, 2)
a[idx]
#array([[3, 4],
#       [9, 7]])


----------------------------------------
Logical operations

a = np.array([1, 1, 0, 0], dtype=bool)
b = np.array([1, 0, 1, 0], dtype=bool)
np.logical_or(a, b)
#array([ True,  True,  True, False], dtype=bool)




----------------------------------------
Transposition

a
a.T #transposition

!!! transposition is a view !!!


----------------------------------------
Ravel returns view!
Flatten returns a copy!



