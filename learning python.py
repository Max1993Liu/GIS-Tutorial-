"""pytho can use both single quote and double quote"""
'''python diffrentiate upper case and lower case
** for power, and % for remainder'''
'''python sring, set, dictionary is iterated defaultly by keys'''
'''python unpacking can be used in list, set, tuple, dictionary(keys)'''


print (9/2)

print(9.0/2)
'''now it's gonna be 4.5'''

name= raw_input('who are you?')
print 'welcome', name

'''return the type of a variable'''
type()

if X>5:
	print 
elif:
	print
else:
	print

'''the try/except structure'''
astr = 'hello bob'
try:
    istr=int(astr)
except:
    istr= -1
else:
	istr=-2    #the else is runned when the 'except' is not runned 
finally:
	print 'cleaning up'

def test():
	print 'fuck off'

while n>0:
	n=n-1
    if n<3:
    	break
 		continue """stop the current iteration and coninue with the next iteration"""

 for i in [1,2,3,4,5]:
 	print i

 while True:
 	dododododoo

 x='banana'
 x[0:4]  '''from 0 up to but not include 4'''

 x= 'hello BOB'
 x.lower() '''lower case''' 
 
 dir(x) '''show all the build-in functions!!!!!!!!!!!!!!!!!!'''

 '''remove the space from the string'''
 x.lstrip() #left strip
 x.rstrip() #right strip
 x.strip()

x.startswith() #return true and false

'''open a file'''
handle = open('mobile.txt','r') #r stands for read, and w stands for write, 'r+'' for read and write, 'a' for append at the end
for x in handle:
	print x   #print each line in the file 

handle.read() #read the whole file into a single string

for line in handle:
	if line.startswith('From'):
		line.rstrip()    #delete the /n in the end of the line, becuase the print function gonna add another /n at the end of the string
		print line

for line in handle:
	line.rstrip()
	if not line.startswith('From'):
		continue
	print line


print range(len('friend'))
range(4) '''outcome is [0,1,2,3]'''

list[:] #show all the numbers

[1,2,3]+[4,5,6] '''the outcome the concatenated 6 number list'''

x = list() # a new list
x.append()
x.sort() #change the original list

numlist = list()
while True:
	inp = raw_input()
	if inp == 'done': break
	value = float(inp)
	numlist.append(value)
average= sum(numlist)/len(numlist)

'''split the string'''
abc= 'fuck you'
stuff = abc.split(' ')
print stuff

'''getting the data from a document'''***
fhand =open('mb.txt')
for line in fhand:
	line = line.rstrip()
	if not line.startswith('xtab'): continue
    words = line.split()
    lst.append(words[2])


'''set current working directory'''
import os
os.chdir() #change directory
os.getcwd() #get current working directory

'''dictionary'''
aa = dict()
aa['a']=5

aa= {}
aa= {'a':1, 'b':[1,2,3]}

counts= {}
names = ['a','b','c']
for name in names:
	counts[name] = counts.get(name,0)+1
print counts
'''dict.get(a,0) try to find a in the dictionary, if yes, return the value of a, if no, return the value of 0'''

aa={}
aa.keys()
aa.values() #return a list of values
aa.items()


for a,b in aa.items():
	print a,b
''' python can do two variable iteration , so a shows the key and b shows the values'''

'''tuple is a list that's immutable'''
x= (1,2,3)
'''tuples are comparable'''
(1,2,3) < (2, 3, 4)

'''sorting list of tuples'''
d= {'a':3, 'b':4}
t= d.items()
t.sort()
sorted(d.items())
for a,b in sorted(d.items()) #sort by key

'''sort by value!!!!!'''
d= {'a':3, 'b':4, 'c':5}
temp = []
for a,b in d.items():
    temp.append((b,a))     ###mention that the temp is actually a list
temp.sort(reverse=True)
print temp

d= {'a':3, 'b':4, 'c':5}
print sorted( (v,k) for k,v in d.items())




''''Python web scrap'''
'''regular expression is a programming language to deal with string'''
import re  #regular expression
re.search() #see if a string matches a regular expression
y= re.findall() #extract portions of a string that matches, retrun a python list !!!!!!!!!!!!!!!!!

import re
hand = open()
for line in hand:
	if re.search('From:' , line)
	print line

'''socket library'''
import socket
mysock = socket.socket(socket.AF_INET, socket.SOCK_STREAM) #no need to change
mysock.connect( ('www.py4inf.com', 80)) #tuple in there

mysock.send('GET http://www.py4inf.com/code/romeo.txt HTTP/1.0\n\n') #\n\n is double enter

while True:
	data = mysock.recv(512) #receive up to 512 characters
	if (len(data) <1 ):
		break
	print(data)
mysock.close()

'''urllib  library'''
import urllib
fhand = urllib.urlopen('http://www.py4inf.com/code/romeo.txt')

for line in fhand:
	print line.rstrip()

'''web scrap with BeautifulSoup'''
import urllib
from BeautifulSoup import * #load all the functions of beautiful soup into our workspace

url= raw_input()

html = urllib.urlopen(url). read()
soup = BeautifulSoup(html)
tags = soup('a')
for tag in tags:
	print tag.get('href',None) #tag is like a library

''''requests package can also load the 'web string' into the computer'''
import requests
r = requests.get('url')
r.content #is the html string
soup = BeautifulSoup(r.content)
g_dat = soup('div',{'class':'info'})
for item in gdat:
	print item.text
    print item.contents[0].text   
    print item.content[1]('a', {'class':'business-name'})
    print item.contents[1]('a', {'class':'business-name'})[0].text

'''if we wanna loop through all the pages'''
url='http://sdjoiojjij&page'+str(i)+'ssss.com' #i is an integer that we can loop with

'''parsing XML in python, from lxml package'''
import xml.etree.ElemenTree as ET

data = '''
<person>
  <name>Chuck</name>
  <phone type="intl">
     +1 734 303 4456
   </phone>
   <email hide="yes"/>
</person>'''

tree= ET.fromstring(data)
print tree.find('name').text
print tree.find('email').get('hide')

import xml.etree.ElementTree as ET

input = '''
<stuff>
    <users>
        <user x="2">
            <id>001</id>
            <name>Chuck</name>
        </user>
        <user x="7">
            <id>009</id>
            <name>Brent</name>
            </user>
        </users>
</stuff>'''

stuff = ET.fromstring(input)
lst = stuff.findall('users/user')
print 'User count:', len(lst)

for item in lst:
    print 'Name', item.find('name').text
    print 'Id', item.find('id').text
    print 'Attribute', item.get("x")

'''parsing jason'''
import json

data = '''
{
  "name" : "Chuck",
  "phone" : {
    "type" : "intl",
    "number" : "+1 734 303 4456"
   },
   "email" : {
     "hide" : "yes"
   }
}'''    '''it actually returns a list, if the data is nested in [], then when you load the data, it'll return a list'''

info = json.loads(data)
print 'Name:',info["name"]
print 'Hide:',info["email"]["hide"]




'''Accessing API in python'''
import urllib
import json
serviceurl1= 'http://maps.googleapis.com/maps/api/geocode/json?'

import urllib
import json

serviceurl = 'http://maps.googleapis.com/maps/api/geocode/json?'

while True:
    address = raw_input('Enter location: ')
    if len(address) < 1 : break

    url = serviceurl + urllib.urlencode({'sensor':'false', 'address': address})
    print 'Retrieving', url
    uh = urllib.urlopen(url)
    data = uh.read()
    print 'Retrieved',len(data),'characters'

    try: js = json.loads(str(data))  #loads meaning load string
    except: js = None
    if 'status' not in js or js['status'] != 'OK':
        print '==== Failure To Retrieve ===='
        print data
        continue

    print json.dumps(js, indent=4)

    lat = js["results"][0]["geometry"]["location"]["lat"]
    lng = js["results"][0]["geometry"]["location"]["lng"]
    print 'lat',lat,'lng',lng
    location = js['results'][0]['formatted_address']
    print location



'''python hexin'''
// #float divide
print '%s is a fucking genius' %'Max'

'''string index'''
s='123456789'
s[::-1] #starting from the end to the beginning
s[::2]  #一个隔一个
s[:-1]  #show all the string except for the last character
s[:None] #show all the string

'''remove part of the list'''
del list[1]
list.remove('abc')


[x+2 for x in range(10) if x%2== 0] #列表解析
[(x+1,y+1) for x in range(5) for y in range(5)]#return a list with all the combinations

'''similar to the previous one, we can use generator'''
((x+1,y+1) for x in range(5) for y in range(5)) #return a generator object not a list

for x,y in enumerate(strings):
	if 'a' in y:
		strings[x]='censored'

list(reversed(strings))
join(reversed(strings))

from math import sqrt
scope= {}
exec'x=1' in scope
eval(x*2,scope)
scope['sqrt']

help(function_name) #return what the function is used for 

def hello(gretting= 'hello', name='world') #using defalut variable in functionsn

def test(x=3, *y, **z):
	print x
	print y
	print z
test(1,2,3,4,j=1,k=2)

def foo(x,y,z,m=0,n=1):
	print x,y,z,m,n
def call_foo(*args, **kwds):
	foo(*args,**kwds)

vars() #return the library of global variable
locals() #return the library of local variable

def a(number):
	def b(factor):
		return number*factor
	return b 
'''when using the a function, it returns a function b'''

_metaclass_=type #at the start of the script, using the new type of class

class test(object):
    is_exist = True    #member variable, accessible to the whole class
    def setName(self,name):
        self.name=name
    def getName(self):
        return self.name
    def greet(self):
        print 'You are fucking genius',self.name
 
shit=test()
shit.is_exist   #print out True
shit.setName('Max')
shit.getName()
shit.greet()

class se:    #private class
    def __secret(self):
        print 'You can not see me'
    def opensee (self):
        print ' you can see me anytime'


class Filter:
    def init(self):
        self.block=[]
    def filter(self,sequence):
        return [x for x in sequence if x not in self.block]

class NumberFilter(Filter):
    def init(self):
        self.block=[1,2,3,4,5,6,7,8,9,0]
       
test = NumberFilter()
test.init()
test.filter([1,2,3,4,5,'a','b','c'])

'''test wether a class has a method'''
hasattr(Filter, 'init')
callable(getattr(Filter, 'init'))

'''initializing class using __init__'''
_metaclass_=type

class foo:
    def __init__(self,value=42):
        self.var = value
    def greet(slef):
        print 'whats wrong fucker'
test =foo(41)
test.var

'''inherant from super class method 1'''
_metaclass_=type

class foo(object):          '''!!!! adding (object) in order to make it a new class'''
    def __init__(self,value=42):
        self.var = value
    def greet(self):
        print 'whats wrong fucker'

class soo(foo):
    def __init__(self):
        foo.__init__(self,value=42) #init the super class
        self.var2= 44
    def greet(self):
        print 'var1=',self.var,'var2=',self.var2

test=soo()
test.greet()

'''inherant from super class method 2'''
class foo(object):
    def __init__(self,value=42):
        self.var = value
    def greet(self):
        print 'whats wrong fucker'

class soo(foo):
    def __init__(self):
        super(soo,self).__init__()
        self.var2= 44
    def greet(self):
        print 'var1=',self.var,'var2=',self.var2

test=soo()
test.greet()

'''running the main function'''
'''put it at the end of the program'''
if __name__=='__main':
	main()

dir()'showing the available functions in a package'
help(sys.exit)
help(len) 'showing how the function works'


'''custom sorting'''
def last(s): retrun s[-1]
sorted(a,key=last)  '''sort a list by the last character'''

'''concatenate a list'''
a=['a','b','c']
':'.join(a)
a.split(':')

'''three different ways to read in data'''
f = open(file,'u')
for line in f: 
	print line    #only using a single line of ram each time
f.readlines() #read in as a python list
f.read()  #read in as a very long string into the ram

'''using enumerate to slice the file'''
for i,x in enumerate(reader):
    if 1<i<10:       #only read in 9 line
        print i,x
    elif i>=10:
        break

from itertools import islice
for line in islice(reader,1,10):  #another method
    print line

'''Access Restful API in python'''
'''GET get information; POST send new information to the website; PUT update existing information from the specified source; DELETE remove exsiting
information from the specified source'''
import requests
handle = requests.get('http') '''GET'''
print handle.text
print handle.status_code  '200 means OK'
print handle.headers

'''the combination of filter and lambda expression'''
my_list = range(16)
print filter(lambda x: x % 3 == 0, my_list) 'filter(function, iterable) return what meets the requirement'

'''python list operation'''
list.extend() #adding another list at the end
list.append() #adding a single item
list.index()
list.remove(a) #remove the first value a

'''python set operation'''
set.discard(a) #doesn't raise error when a don't exist
set.remove(a) #raise error when a don't exist
set.add()
set.update(a) # a must be an iterable item like list or dictionary
a.union(b) == b.union(a)
a.intersection(b) == b.intersection(a)
a.difference(b) == b.difference(a) #false

'''itertools'''
from itertools import product
A = [[1,2,3],[2,3]]
print list(product(*A)) # *is unpacking, giving too lists from A to the product function, and the product return any combinations of the lists

itertools.permutations(a,2) #return a permutation object with the combinations of two variables
itertools.combinations(a,2) #the result has order, if a= [1,3,2] it will only return [(1,3),(1,2),(3,2)]
itertools.combinations_with_replacement(a,2) #still have order but adding (1,1) (2,2) (3,3)


'''collections package'''
from collections import Counter  #Counter
size = [2,3,4,5,6,8,7,6,5,18]
money = 0 
inventory = dict(Counter(size))

for i in range(6):
    k = raw_input().split()
    k = map(int,k)
    temp_1 = k[0]
    temp_2 = k[1]
    if temp_1 not in inventory.keys():continue
    if inventory[temp_1] > 0 :
        inventory[temp_1] -= 1
        money += temp_2        
print money

'''collections. defaultdict''' #will not have an error if a key doesn't exist
from collections import defaultdict
d = defaultdict(list)  #but need to set the default type

'''collections. namedtuple'''
from collections import namedtuple
point = namedtuple('point',['a','b'])
test = point(11,12)
t = [11,12]
test = point._make(t) #return a point type 
test.a
test.b

'''python input'''
input() #which is equal to eval(raw_input(prompt))

'''sort using the second item'''
sorted(t, key = lambda x: x[1]) #t can be a list of list, list of tuples, list of numbers

any() #takes in an iterable, if any element is True return True
all() #all of em are True return true

'''underscore/underline in python
_ single underline before name---> a private method or variable, won't be imported when from aa import *
__ double underline before name--> python will automatically change the name into _class__name; see it in class.__dict__
'''

'''the decorator is a callable that takes a function as an argument and returns a replacement function'''
def outer(somefunction):
    def inner():
        ret = somefunction() 
        return ret + 1
    return inner
def foo():
    return 1
decorated = outer(foo())
decorated() ###### 2

'''*args and **kwargs'''
def add(x, y):
    print x+y
add(*[1,2]) #3
add(**{'x':1,'y':2}) #3 the ** basically takes in key-value pair

'''changing the system path for python'''
import sys
sys.path.append()

'''basic iterator'''
class sequenceIterator:
    def __init__(self,sequence):
        self._seq = sequence
        self._k = -1
    
    def __next__(self):
        self._k += 1
        if self._k < len(self._seq):
            return self._seq[self._k]
        else:
            raise StopIteration()
        
    def __iter__(self):
        return self

'''Inheritance, calling construction function of parent class'''
class parent(object):
    def __init__(self,a):
        self.output1 = a

# method1 recommended
class child():
    def __init__(self,a,b):
        super(child,self).init(a)
        self.output2 = b

#method2 
class child():
    def __init__(self,a,b):
        parent.__init__(a)
        self.output2 = b

'''Class data member'''
class CreditCard():
    FEE = 5      'the fee variable is a constant for all the instances'

'''__slots__ cut down RAM usage'''
class CreditCard():
    __slots__ = '_balance', '_bank'    
    def __init__(self,balance, bank):
        self._balance = balance
        self._bank = bank
# the inheritance only need to add new variables into the __slots__

'''create a new string with all the alphabets from the original string'''
letters = ''.join([c for c in document if c.isalhpa()])
letters = ''.join(c for c in document if c.isalpha())

'''range'''
range(2,10) #2,3,4,5,6,7,8,9,
range(2,10,2) #2,4,6,8

'''The proper way of initializeing a two-dimentional list'''
[[0] * c for i  in range(r)]

'''property'''
class circle(object):
    def __init__(self,r):
        self._radius = r
    
    @property
    def area(self):
        return 3.14 * (self._radius ** 2)
c = circle(4)
print c.area  #making area an property of circle

'''in the last example everytime we use area, it needs to be calculated again'''
'''using lazy property'''

'''defining an error in python'''
class errorname(Exception):
    pass
'''when you want to pop up an error'''
if ...:
    raise errorname('the content shown to user')

'''overwritting functon from parent class'''

def samename(self,a):
    original_result = super(childclassname, self
        ).samename(a)
    return new_result

'''reverse string in python'''
test = range(10)
test.reverse()  #reversing string in place
reversed(test) #return an iterator for reversed list

# really verbose
new = {}
for (key, value) in data:
    if key in new:
        new[key].append( value )
    else:
        new[key] = [value]


# easy with setdefault
new = {}
for (key, value) in data:
    group = new.setdefault(key, []) # key might exist already
    group.append( value )


# even simpler with defaultdict 
new = defaultdict(list)
for (key, value) in data:
    new[key].append( value ) # all keys have a default already

new = defaultdict(lambda:4) #initializing with values all equal to 4

#changing the column name in pandas
data.columns = ['new name']

'''for ipython notebook to run matplotlib inline'''
%matplotlib inline

'''poping a column in pandas dataframe'''
data.pop('col_name') #useful in feature-output split

'''using pickle to seriealize and deserialize object'''
import pickle
a = 2
fileobject = open('testfile', 'w+')
pickle.dump(a, fileobject) #saving

fileobject = open('testfile', 'r+')
b = pickle.load(fileobject) #b=2
#saving a program's state data to disk so that it can carry on where it left off when restarted 

'''counting unique elements in pd.Series'''
series.nunique()

'''plotting seperately with different group'''
ax1, ax2 = data.plot(kind = 'bar', subplot = True) #subplot = True seperates into two plots

'''labelencoder'''
#Encode labels with value between 0 and n_classes-1
from sklearn.preprocessing import labelencoder
arg = LabelEncoder().fit(data)
y = arg.transform(data)
n_classes = len(arg.classes_)

'''k-fold cross_validation'''
from sklearn.cross_validation import KFold
kf = KFold(data.shape[0], n_folds = 10, shuffle = True)
for train_index, test_index in kf:
    train, test = data[train_index], data[test_index]

'''format printing'''
print('{}: {}'.format(c, phone[c].nunique())) #device_id: 186716

'''drop duplicates pandas dataframe'''
dataframe.drop_duplicates('col_name')

'''pandas series concat'''
pd.Series.str.cat(anotherSeries, sep = ',')

'''reading and ploting image'''
#reading image
from scipy import ndimage
img  = ndimage.imread('file', mode = 'L') #return a 2D np array
img  = ndimage.imread('file', mode = 'RGB') #return 3D np array RGB

#ploting
from ipython.display import Image, display
display(Image('file'))

plt.imshow('img') #should be a ndarray

'''pandas, elementwise apply'''
df.applymap(function)

df['a'].unique() #return unique values

df['a'].isin([2,3])

#merge
pd.merge(left, right, left_on = 'lkey',right_on = 'rkey', how = 'inner')
#left_on is the key name of the left dataset
#default using inner join
pd.merge(left, right, left_on = 'lkey',right_index = True, how = 'inner')
#if the right dataset is using the index column

#concatenate
pd.concat()

#duplicated
df.duplocated()
df.drop_duplicates()
df.drop_duplocated(['col1','col2'], take_last = True)

#cut
pd.cut()
pd.qcut() #quantile cut

#detecting outliers
(np.abs(data) > 3).any(1) #return boolean row index


