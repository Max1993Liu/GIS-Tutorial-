!!
Transactions are LAZY! (return new RDD)
Actions are EAGER! (return other data types)


!!To check whether shuffle is needed
1. .toDebugString //printout the execution process of Spark
2. .dependencies //whether it includes shuffled RDD


//Ask Spark to save a dataset in the memory
RDD.persist()

Spark runs in a lazy fashion, and default recomputed each time
when runing an action on RDD. So if need to reuse it lotta times, 
better use RDD.persist()

//create an RDD from the dataset in memory
val lines = sc.parallelize(List("A", "B", "C"))

!! Transformation return another RDD
!! Action return other data types

foldLeft() is not parallelizable becuase of the type changing
Because fold always need the same type, fold is parallelizable
Aggregate is parallelizable, and able to change type
Aggregate example: 
rdd.aggregate((0, 0))
((x, y) =>
(x._1 + y, x._2 + 1),
(x, y) =>
(x._1 + y._1, x._2 + y._2))


Pair RDD operations:
Transformations:
1. groupByKey    2. reduceByKey   3.mapValues
4. keys    5. join     6.leftOuterJoin/rightOuterJoin
Actions:
1. countByKey




--- Save Results ------------------------
rdd.saveAsTextFile


--- Transformations ------------------------
filter
map
flatmap
union(RDD2), distinct(), intersection(RDD2), subtract(RDD2)
cartesian(RDD2) //all possible combinations
RDD.sample(false, 0.5) //sample(withReplacement, fraction, seed)
aggregate(initial_zero, accumulator_function, reducer_function)
ex: average of a list of numbers:
//note the curring
val result = input.aggregate((0, 0))(
	(acc, value) => (acc._1 + value, acc._2 + 1),
	(acc1, acc2) => (acc1._1 + acc2._1, acc1._2 + acc2._2))
val avg = result._1 / result._2.toDouble



--- Actions --------------------------------
take:
println("Input had " + badLinesRDD.count() + " concerning lines")
println("Here are 10 examples:")
//only take 10 lines
badLinesRDD.take(10).foreach(println)

map:
badLinesRDD.take(10).map(_.length)
//top 5
RDD.top(5)
//return to local memory
RDD.collect()
//perform action on all of the elements without returning anything
RDD.foreach()
//Number of times each element occurs in RDD
RDD.countByValue


when passing functions to Spark, the function we pass and
the data referenced in it needs to be serializable
//passing functions
import org.apache.spark.rdd.RDD

class SearchFunctions(val query: String) {
	def isMatch(s: String): Boolean = {
		s.contains(query)
	}
	def getMatchesFunctionReference(rdd: RDD[String]): RDD[String] = {
	// Problem: "isMatch" means "this.isMatch", so we pass all of "this"
		rdd.map(isMatch)
	}
	def getMatchesFieldReference(rdd: RDD[String]): RDD[String] = {
	// Problem: "query" means "this.query", so we pass all of "this"
		rdd.map(x => x.split(query))
	}
	def getMatchesNoReference(rdd: RDD[String]): RDD[String] = {
	// Safe: extract just the field we need into a local variable
		val query_ = this.query
		rdd.map(x => x.split(query_))
	}
}


//square the input
val input = sc.parallelize(List(1, 2, 3, 4))
val result = input.map(x => x * x)
//back to local memory
println(result.collect().mkString(","))


size of partitions:
RDD.partitions.size

access by key in a key/value RDD:
RDD.lookup(key)

count the number of elements for each key:
RDD.countByKey()

collect the result as a map for easier lookup:
RDD.collectAsMap()

UserData is a big table, every five minutes join the logFile:
This approach is inefficient because it needs to shuffle  UserData everytime

val sc = new SparkContext()
val userData = sc.sequenceFile[UserID, UserInfo]("source").perist()
def processNewLogs(logFileName: String){
	val events = sc.sequenceFile[UserID, LinkInfo](logFileName)
	val joined = userData.join(events)
	val offTopicVisits = joined.filter{
		case (UserId, (userInfo, linkInfo)) => 
			!userInfo.topics.contains(linkInfo.topic)
	}.count()
	println("Number of visits to non-subscribed topics")
}

val sc = new SparkContext()
//pre-hash the big table, so when doing the join, Spark only need to 
//shuffle the small table
val userData = sc.sequenceFile[UserID, UserInfo]("source").
					partitionBy(new HashPartitioner(100)).
					persist()

Chage Persist Level:
import org.apache.spark.storage.StorageLevel.MEMORY_AND_DISK_SER
rdd.persist(MEMORY_AND_DISK_SER)


custom partitioners:
class DomainNamePartitioner(numParts: Int) extends Partitioner {
	override def numPartitions: Int = numParts
	override def getPartition(key: Any): Int = {
		val domain = new Java.net.URL(key.toString).getHost()
		val code = (domain.hashCode % numPartitions)
		if (code < 0) {
		code + numPartitions // Make it non-negative
		} else {
		code
		}
}
// Java equals method to let Spark compare our Partitioner objects
	override def equals(other: Any): Boolean = other match {
		case dnp: DomainNamePartitioner =>
		dnp.numPartitions == numPartitions
		case _ =>
		false
		}
}


###############################################
#											  #
#           Shuffle                           #
#											  #
###############################################

usually after groupby function, each group needs to 
sit in a single node, so it causes web communication (shuffle)


ReduceByKey can be thought of as a combination of groupByKey
and then reducing on all the values in each group. It's 
actually more efficient than using seperately.
Reason: ReduceByKey runs on each node (mapper level) first, so less data is 
sent for the reduce on reducer level. 



###############################################
#											  #
#           Partition                         #
#											  #
###############################################

1. Partition never span over multiple machines
2. Each machine in the cluster contains one or more partitions
3. The number of partitions to use is configurable. By default
it equals the total number of cores on all executor nodes
4. Default hash partitioning is used

Two kinds of partitioning available in Spark:
1. Hash partitioning
2. Range partitioning 


!!! Customizing a partitioning is only possible for pair RDD
!!! Partitions is done based on keys


Spark computes the partition by calculating:
p = key.hashCode() % numPartitions //P is the partition to store

Intutition: hash partitioning attempts to spread data evenly 
across partitions based on key.


range partitioning, keys are partitioned according to
1. an ordering for keys
2. a set to sorted ranges of keys

tuples with keys in the same range appear on the same machine


###############################################
#											  #
#           set partition                     #
#											  #
###############################################


1. Call partitionBy on an RDD, providing an explicit partitioner
2. Using transformations that return RDDs with specific partitioners


ex. for method 1
val pairs = data.map(p => (p.a, p.b))
//pass in a pair RDD, spark will sample it and calcualte the range partitions

!!!!!!!!!!!!!!!!!!!!!!!!!!!
!!! Key must be ordered !!!
!!!!!!!!!!!!!!!!!!!!!!!!!!!

val tunedPartitioner = new RangePartitioner(8, pairs)
//data will be shuffled again and again if it don't persist()
val partitioned = pairs.partitionBy(tunedPartitioner).persist()



ex. for method 2
val tunedPartitioner = new HashPartitioner(8)
val partitioned = pairs.partitionBy(tunedPartitioner).persist()



Operations on pair RDD that hold to (and propagate) a partitioner:

cogroup
groupWith //alias for cogroup
join
leftOuterJoin
rightOuterJoin
groupByKey
reduceByKey
foldByKey
combineByKey
partitionBy
sort
mapValues (if parent has a partitioner)
flatMapValues (if parent has a partitioner)
filter (if parent has a partitioner)

!! note map(flatMap) is not on the list, therefore try use more mapValues



RULE OF THUMB
1. a shuffle can occur when the resulting RDD depends on other 
elements from the same RDD or other RDD

2. use .toDebugString to see the execution plan


SOME EXAMPLE TO REDUCE Shuffle
1. reduceByKey instead of groupByKey
2. join called on two RDD that are pre-partitioned with the 
same partitioner and cached on the same machine wil cause the 
join to be computed locally with no shuffling across the network


###############################################
#											  #
#         wide & narrow dependency            #
#											  #
###############################################

















###############################################
#											  #
#         Spark SQL                           #
#											  #
###############################################

!!! SQLlite API

1. Spark SQL is a Spark module for structured data processing
2. It's implemented as a library on top of Spark

Three Main API
1. SQL literal syntax
2. DataFrames: Conceptually RD with a known schema
3. Datasets

Two Specialized Backend Components
1. Catalyst: query optimizer
2. Tungsten: off-heap serializer

!!!!!!!!!!!!!!!!!!!!!!!!!!!!
!!!DataFrames are untyped!!!
!!!!!!!!!!!!!!!!!!!!!!!!!!!!
RDD[T]
DataFrame


To get started using Spark SQL, everything starts with the SparkSession
It's like the SparkContext for RDDs


 
import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().appName().
	config().getOrCreate()



DataFrames Can Be Created with
1. From an existing RDD
2. Reading in a specific data source (ex. JSON)

ex.1 
val tupleRDD = ... //RDD[(Int, String, String)]
val tupleDF = tupleRDD.toDF("id", "name", "city")


ex.2
val df = spark.read.option("header", "true").csv(path)


After Read In File
//Register the DataFrame as a SQL temporary view
df.createOrReplaceTempView("df")
//gives a name to our DataFrame in SQL so we can 
//refer to it in an SQL FROM statement

val ret = spark.sql("select * from df where age > 19")



###############################################
#											  #
#         Spark DataFrame API                 #
#											  #
###############################################


Complex Scala Type vs. 		SQL Type
Array[T]                  ArrayType(elementType, containsNul)
Map[k, v]                 MapType(keyType, valueType, valueContainsNull)
case class                StructType(List[StructFields])


!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
In order to access any of these data types
either baisc like string, Int or Complex
you need to first import Spark SQL types

import org.apache.spark.sql.types._
import org.apache.spark.sql.functions_

//print schema in tree structure
df.printScheme()


Transformations on DF are also lazy!!!


filter is kinda same as where but filter can be more complex


How to select column:

import spark.sql.implicit._
df.groupBy($"attribute1").agg(sum($"attribute2"))

or

df("attribute")
$("df.attribute")




postsDF.groupBy($"subforum").groupBy($"authorID")


drop() drop rows that contain null or NaN in any column
drop("all") drop rows that contrain null or Nan in all columns
drop(Array("id", "name")) specify columns


fill(0) replace all null or nan in numeric columns with 0
fill(Map("attribute" -> 0)) specify column
replace(Array("id"), Map(1234 -> 4321))



Actions of DataFrame
collect()
count()
first() //first row
show()
take(n) //return first n rows


joins:::
df1.join(df2, $"df1.id" === $"df2.id")
df1.join(df2, $"df1.id" === $"df2.id", "right_outer")



###############################################
#											  #
#         Spark Datasets                      #
#											  #
###############################################

Dataset is DataFrame with type safety

type DataFrame = Dataset[Row]

What is Dataset?
1. Dataset can be thought of as a typed distributed collections of data.
2. Dataset API unifies the DataFrame and RDD APIs. Mix and Match!
3. Datasets require structured/semi-structured data. Schemas and Encoders core part of Dataset

Think of Dataset as a compromise between RDD and DataFrame
You get more type information on Dataset than DataFrame, and you 
get more optimizations on Datasets than you get on RDDs


We can do something like:
DS.groupByKey(x => x.zip)
	.avg($"price").as[Double]



Creating a DataSet
1. 
import spark.implicits._
myDF.toDS

2. 
case class person(name: String, age: Int)
val myDS = spark.read.json("somjson").as[person]

3. 
import spark.implicits._
myRDD.toDS

4. 
List("A", "B", "C").toDS



Sometime when error: type mismatch
found: Column
required: TypedColumn[]

use $"attribute".as[Double]


groupByKey + agg


!!!!!!!!!!!!
mapGroups requires shuffling all the data in the Dataset.
If application intends to perform aggregation on each key
use reduce function or an org.apache.spark.sql.expressions#Aggregator


//Don't do
val keyValues = List((3, "Me"), (1, "Thi"), (2, "Se"), (1, "sIsA"), 
	(3, "ge"), (2, "cre"), (2, "t"))

val keyValuesDS = keyValues.toDS

keyValuesDS.groupByKey(x => x._1).
	mapGroups((k, v) => (k, v.foldLeft(""){(acc, n) => acc+n._2})).show


//Use reduce
keyValuesDS.groupByKey(x => x._1).mapValues(x => x._2).
	reduceGroups((acc, s) => acc + s).show()


//Aggregator
import org.apache.spark.sql.expressions.Aggregator

class Aggregator[-IN, BUF, OUT]
In -> input type
BUF -> intermediae type during aggregation
OUT -> type of the output of the aggregation

val myAgg = new Aggregator[INT, BUF, OUT]{
	def zero: BUF =   //initial value
	def reduce(b: BUF, a:IN): BUF = //add an element to running total
	def merge(b1: BUF, b2: BUF): BUF = //merge intermediate values
	def finish(b: BUF): OUT = ..
}.toColumn



val strConat = new Aggregator[(Int, String), String, String]{
	def zero: String = ""
	def reduce(b: String, a: (Int, String)): String = b + a._2
	def merge(b1: String, b2: String): BUF = b1 + b2
	def finish(b: String): String = b
}.toColumn

//this will cause encoder error
keyValuesDS.groupByKey(x => x._1).agg(strConat.as[String])




Encoders
1. encoders are what convert your data between JVM objects and 
Spark SQL's specialized internal (tabular) representation
2. They're required by all Datasets

Include encoders
1. Automatically(generally the case) via implicits from a SparkSession
import saprk.implicits._
2. Explicitly via org.apache.spark.sql.Encoder, which contains a large
selection of methods for creating Encoders from scala primitive types and products

ex.
Encoders.scalaInt //for scala int
Encoders.STRING
Encoders.product
Encoders.tuple

INT/LONG/STRING for nullable primitives
scalaInt/scalaLong/scalaByte for scala primitives
product/tuple for scala product and tuple


//this works
val strConat = new Aggregator[(Int, String), String, String]{
	def zero: String = ""
	def reduce(b: String, a: (Int, String)): String = b + a._2
	def merge(b1: String, b2: String): BUF = b1 + b2
	def finish(b: String): String = b
	override def bufferEncoder: Encoder[String] = Encoders.STRING
	override def outputEncoder: Encoder[String] = Encoders.STRING
}.toColumn



