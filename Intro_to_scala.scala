/** mannually declare type */
var msg: String = "Hello world!"

def max(x: Int, y: Int): Int = {
	if (x > y) x
	else y
}

def ff(x: Int) {println("whatever")}
//with no equal before {}, the function will always return Unit!
//therefore it's only used for functions that only work by side effects
//and with no return values


//use arguments from console
//by accessing args array 
//note it's not [] rathr ()
println("Hello" + args(0) + "!")


//print every arguments from command line
var i = 0
while (i < args.length){
	if (i != 0)
	  //print doesn't creat new line
	  //if there's only one statement, the {} can be ommited
	  print(' ')
	print(args(i))
	i += 1
}

//more concise way to do it
args.foreach(arg => println(arg))
//or
args.foreach((arg: String) => println(arg))
//or even better
args.foreach(println)
//this only words when there is only one statement and one argument

//for loop
for (arg <- args){
//notice here arg is a val not a var
//therefore arg can not be revalued
	println(arg)
}


val greetStrings = new Array[String](3)
  greetStrings(0) = "Hello"
  greetStrings(1) = ", "
  greetStrings(2) = "world!\n"
for (i <- 0 to 2)
  print(greetStrings(i))
//notice the val greetString can not be reassigned,
//but the instance it's pointing to is mutable

Scala doesn’t technically have operator overloading, because it doesn’t actually have operators in the traditional sense. 
Instead, characters such as +, -, *, and / can be used in method names. 
Thus, when you typed 1 + 2 into the Scala interpreter in Step 1, 
you were actually invoking a method named + on the Int object 1, passing in 2 as a parameter. 

so 1+2 is essentially (1).+(2), + is the method of Int class

The reason why Array(2) use parenthesis is because it transforms into
Array.apply(2) that returns the third element, so if 
newly defined class has apply method it can also use the similar syntax.

similarly when using Array(2) = 'Test', it's interpretered as 
Array.update(2, "Test")

//same as before
val greetStrings = new Array[String](3)
 greetStrings.update(0, "Hello")
 greetStrings.update(1, ",")
 greetStrings.update(2, "World!\n")
for (i <- 0 to 2)
  print(greetStrings.apply(i))

//Better way to initialize array
val greetStrings = Array("Hello", ",", "world")


Array share the same type and is mutable
List share the same type but is immutable
Because List is immutable, any kind of operation that seems to 'mutate' List
is actually returning a copy

val a = List(1,2)
val b = List(3,4)
// ::: concatenate, returning a copy
// ::  prepends another element to the begining of the List
val c = a ::: b
val d = 5 :: a


val test = List("aa", "bb", "cc", "d", "e")
//number of elements with length of 2
test.count(s => s.length==2)
//without the first 2
test.drop(2)
//similar
test.dropRight(2)
//exist, like any
test.exists(s => s.length==1)
//filter
test.filter(s => s.length==2)
//like all
test.forall(s => s.endsWith("a"))
//for each
test.foreach(print)
//first element
test.head
//last one
test.last
//all but the first one
test.tail
//mapping 
test.map(s => s + "ohhhhh")
//join in python
test.mkString("+")
//remove
test.filterNot(s => s.length==2)
//sort
test.sortWith((s,t) => s.charAt(0).toLower < t.charAt(0).toLower)
//or natural sorting
test.sorted
//some syntax sugar
test.sortWith(_ < _)


tuples in scala can contain different data types
access tuples by one index! and a leading underscore
val pair = ("a", 123)
pair._1 
//return "a"


both set an map have their mutable and immutable version
default using immutable
//if use val will be an error
var testSet = Set("a", "b")
testSet += "c"
println(testSet)

//mutable version
import scala.collection.mutable.HashSet
val testSet = HashSet("a", "b")
testSet += "c"
println(testSet)

mutable Maps in scala, the 1 -> "whatever" actually envokes the the method of 
integer 1, (1).->"whatever", and it returns a tuple (1, "whatever") feeds into the map

import scala.collection.mutable.Map
val treasureMap = Map[Int, String]()
  treasureMap += (1 -> "Go to island.")
  treasureMap += (2 -> "Find big X on ground.")
  treasureMap += (3 -> "Dig.")
 println(treasureMap(2))

//default immutable map
val romanNumeral = Map(
    1 -> "I", 2 -> "II", 3 -> "III", 4 -> "IV", 5 -> "V"
  )

You can also use assert in scala!

The functional way of programming:
//original  
def printing(args: Array[String]): Unit ={
	var i = 0 
	while (i < args.length){
		println(args(i))
		i += 1
	}
}
//functional way 
def printing(args: Array[String]): Unit = {
	args.foreach(println)
}

def printing(args: Array[String]): Unit ={
	for (arg <- args)
		println(arg)
}

But there's still problem, because the function we defined is only useful in its
side effects, meaning that it doesn’t return anything. So a more functional way:

def ms(args: Array[String]): Unit = {
	args.mkString('\n')
}
println(ms(testArray))


A balanced attitude for Scala programmers
Prefer vals, immutable objects, and methods without side effects. 
Reach for them first. Use vars, mutable objects, and methods with side effects 
when you have a specific need and justification for them.
 

//classes
class test {
	var a = 0 
}
val test = new test
test.a //0
test.a = 2 //It's ok since a is a var

To prevent end-user from changing the internal values
class test{
	private var a = 0
}
val test = new test
test.a = 3 //error

