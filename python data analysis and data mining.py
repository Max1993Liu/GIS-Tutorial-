import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

data = pd.read

#basic imformation of data, checking NA values and outliers
data.describe()
#if len(data) is larger than the 'count' value in describe(), then there're NA values

#using boxplots
plt.figure()
p = data.boxplot()
x = p['fliers'][0].get_xdata()
y = p['fliers'][0].get_ydata()
y.sort()

#annotating outliers
for i in range(len(x)):
	plt.annotate(y[i], xy= (x[i], y[i]))

#visualizing distribution by histogram
plt.figure()
p = data.hist(bins = 20)
plt.show()

#using scattermatrix to explore correlation
from pandas.tools.plotting import scattermatrix
scattermatrix(data)

#correlation matrix
data.corr()
data.corr()['column name']
data['a'].corr(data['b'])

'''dealing wth NA values'''
data.fillna(method = )

#lagrange method 
from scipy.interpolate import lagrange
def lagrange_fill(target_col, missing_index, k = 5):
	y = target_col[list(range(missing_index - k, missing_index)) + list(range(missing_index + 1, missing_index + 1 + k))]
	y = y[y.notnull()]
	return lagrange(y.index, list(y))(missing_index)

for i in data.columns:
	for j in range(len(data)):
		if (data[i].isnull())[j]:
			data[i][j] = lagrange_fill(data[i], j)



'''discretization'''
#equal width 
d1 = pd.cut(data, bins = 4) #cut into 4 ranges

#equal frequency
k =  4 #cut into 4 pieces
w = [1.0 * i/k for i in range(k+1)]
w = data.describe(percentiles = w)[4:4+k+1]
d2 = pd.cut(data, w)

#based on clustering
kmodel = KMeans(n_cluster= 4, n_jobs = 4)
kmodel.fit(data.reshape((len(data),1)))
c = DataFrame(kmodel.cluster_centers_).sort(0)
w = pd.rolling_mean(c, 2).ix[1:] #use the mean of neighbouring centers as cut point
w = [0] + list(w) + [data.max()] #add 0 and max value
d3 = pd.cut(data, w)

'''dimensional reduction'''
from sklearn.decomposition import PCA
pca = PCA()
pca.fit(data)
pca.components_
pca.explained_variance_ratio_ #decide how many components we actually need

#now we decide to use 3 features
pca = PCA(3)
pca.fit(data)
low_d = pca.transform(data) #numpy-ndarray
DataFrame(low_d)


'''After cleaning the data, time to set up a model'''
'''Logistic regression'''
#x is the feature data, y is the outcome
x = datax.as_matrix() #!! turn it into a numpy array
y = datay.as_matrix() #!! turn it into a numpy array
from sklearn.linear_model import LogisticRegression as LR
from sklearn.linear_model import RandomizedLogisticRegression as RLR
rlr.fit(x,y)
rlr.get_support() #see what features should be used
x = data[data.columns[rlr.get_support()]].as_matrix()

lr = LR()
lr.fit(x, y)
lr.score(x,y)

'''decision tree'''
from sklearn.tree import DecisionTreeClassifier as DTC
dtc = DTC(criterion = 'entropy')
dtc.fit(x,y)

'''Artificial neural network ANN'''
from keras.models import Sequential
from keras.layers.core import Dense, Activation

model = Sequential() #set up a model
model.add(Dense(2,input_dim = 10)) #output_dim is 2, input_dim is 10
model.add(Activation('relu'))

model.add(Dense(1, input_dim = 2))
model.add(Activation('sigmoid')) #for 0-1 output
model.compil(loss = , optimizer = , class_mode = )
model.fit(x, y , nb_epoch = 1000 ,)  #train 1000 times
model.predict_classes(x)

'''K-means'''
data = 1.0 * (data - data.mean())/data.std() #standardizing data
from sklearn.cluster import KMeans
model = KMeans(n_cluster = 4, n_jobs = 4, max_iter = 1000)
model.fit(data)

r1 = pd.Series(model.labels_).value_counts()
r2 = pd.DataFrame(model.cluster_centers_)
r = pd.concat([r2,r1], axis = 1) #get the result matrix

'''clustering visualization tool TSNE'''

'''outlier testing with Kmeans'''

'''training - testing split'''
y = data.pop('output')
x = data
from sklearn.cross_validation import train_test_split
x_train, x_test, y_train , y_test = train_test_split(x.index, y, test_size = 0.2)
training = x.ix[x_train] #training set

'''ROC curve'''
from sklearn.metrics import roc_curve
fpr, tpr, thresholds = rorc_curve(predict_result, test_x)
plt.plot(fpr, tpr)

'''feature selection'''
from sklearn.feature_selection import SelectPercentile
from sklearn.feature_selection import f_classif,chi2
from sklearn.preprocessing import Binarizer, scale

# First select features based on chi2 and f_classif
p = 3

X_bin = Binarizer().fit_transform(scale(X))
selectChi2 = SelectPercentile(chi2, percentile=p).fit(X_bin, y)
selectF_classif = SelectPercentile(f_classif, percentile=p).fit(X, y)

chi2_selected = selectChi2.get_support()
chi2_selected_features = [ f for i,f in enumerate(X.columns) if chi2_selected[i]]
print('Chi2 selected {} features {}.'.format(chi2_selected.sum(),
   chi2_selected_features))
f_classif_selected = selectF_classif.get_support()
f_classif_selected_features = [ f for i,f in enumerate(X.columns) if f_classif_selected[i]]
print('F_classif selected {} features {}.'.format(f_classif_selected.sum(),
   f_classif_selected_features))
selected = chi2_selected & f_classif_selected
print('Chi2 & F_classif selected {} features'.format(selected.sum()))
features = [ f for f,s in zip(X.columns, selected) if s]
print (features)

'''seaborn Facetplot'''
sns.Facetplot(x = , y = , col = , hue = ).map(sns.kdeplot, 'Variable') #x, y is the coordinate, col is the variable used to divided the plot
#into several subplots in the same row

'''get dummy variable'''
pd.get_dummies()

'''seaborn distplot'''
sns.distplot() #combination of histograme and kdeplot

'''cross_validation in sklearn'''
from sklearn import cross_validation
