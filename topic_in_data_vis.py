'''seaborn'''
import seaborn as sns
sns.distplot(kde = False, rug = False) #the combination of bin, kde and rug plot
sns.regplot(x = , y = , data = )



'''matplotlib two vertical plot'''
fig = plt.figure(figsize=(9, 7))
plt.subplot(311)
plt.scatter(df.YearBuilt.values, price)
plt.title('YearBuilt')

plt.subplot(312)
plt.scatter(df.YearRemodAdd.values, price)
plt.title('YearRemodAdd')

plt.subplot(313)
sns.heatmap(data, square = True)
plt.title('Seaborn plot')

fig.text(-0.01, 0.5, 'Sale Price', va = 'center', rotation = 'vertical', fontsize = 12)

plt.tight_layout()

