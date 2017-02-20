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


use three quote signs for raw string
"""This is a "raw string", no need for any escapes"""

in scala, you can not pass a value to a function without declaring it previously
but you could pass in a symbol with just  'aSymbol without declaring it
//symbols in scala
val s = 'aSymbol
//only feature of a symbol
s.name

val s = "What is it that matters"
//index
s.indexOf('a')


logical operators AND && OR ||

//Usage of scala classes: if the class doesn't have any parameters, you could escape the parenthesis
----- following is a class for Rational numbers------------
----- core concepts: required, private, overlaod-----------
----- core concepts: implicit conversion ------------------

class Rational(n: Int, d: Int) {
  require(d!=0)

  //calculate the greatest common divisors
  private val g = gcd(n.abs, d.abs)

  val numer = n/g
  val denom = d/g
  override def toString = numer + "/" + denom

  //auxillary constructor
  //every auxillary constructor is eventually calling the
  //primary constructor in a scala class
  def this(n: Int) = this(n, 1)


  def add(that: Rational): Rational =
  {
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )
  }

  def lessThan(that: Rational) =
    this.numer * that.denom < that.numer * this.denom

  def max(that: Rational) =
    if (this.lessThan(that)) that else this

  def * (that: Rational): Rational =
    new Rational(numer * that.numer, denom * that.denom)

  def + (that: Rational): Rational =
    new Rational(numer * that.denom + that.numer * denom,
      denom * that.denom)

  //method overloading
  //so and Int can be added to a Rational
  def + (i: Int): Rational =
    new Rational(numer + i * denom, denom)

  private def gcd(a: Int, b: Int): Int =
    if (b==0) a else gcd(b, a%b)
}

//imlicit conversion
//now it's possible to do Int + Rational
//for an implicit conversion to work, it needs to be in scope
//it won't work if it's only put in a class
implicit def intToRational(x: Int) = new Rational(x)


val test = new Rational(2, 3)
val another = new Rational(4, 6)
2 + another

---------------- End of class -----------------------------

-------- control flows --------------------------
//if 
var filename = "default.txt"
if ( !args.isEmpty ) 
	filename  = args[0]

//a more functionl way of writing if conditions
//note that this time filename is a val not a var
val filename = if (!args.isEmpty) args[0] else "default.txt"


//for loop
val filesHere = (new java.io.File(".")).listFiles
//print all the files
for ( file <- filesHere) 
	println(file) 
//only print the file ends with .py
for ( file <- filesHere if file.getName.endsWith(".py"))
    println(file)
//or even more defensive
for ( file <- filesHere if file.getName.endsWIth(".py") if file.isFile)
	printLn(file)

//nested iteration
def fileLines(file: java.io.File) =
    scala.io.Source.fromFile(file).getLines().toList
def grep(pattern: String) =
    for (
         file <- filesHere
         //note here a semicolon is needed!!
         if file.getName.endsWith(".scala");
         line <- fileLines(file)
         if line.trim.matches(pattern)
          ) println(file +": "+ line.trim)
grep(".*gcd.*")
//similar to nested list comprehension in Python

//note that in the previous example `line.trim` is computed twice
//use bound value by: 
def grep(pattern: String) =
    for {
      file <- filesHere
      if file.getName.endsWith(".scala")
      line <- fileLines(file)
      //assgined line.trim to value trimmed
      trimmed = line.trim
      if trimmed.matches(pattern)
    } println(file +": "+ trimmed)
grep(".*gcd.*")

//producing a new collection with for loop
val scalaFiles =
  //using curly brace for long for expressions to avoid semocolon problems
  for {file <- filesHere if file.getName.endsWith(".scala")}
  yield {file}

-------- A basic try => catch => finally stucture ---------
import java.io.FileReader
import java.io.FileNotFoundException
import java.io.IOException

try {
	file = new FileReader("someFile.txt")
} catch {
	case e: FileNotFoundException => //do sth
	case e: IOException => //do sth 
} finally {
	file.close
}


-------- A basic match => case structure -------------------
val firstArg = if (!args.isEmpty) args(0) else " "
firstArg match {
	case "salt" => println("Pepper")
	case "chips" => println("Salsa")
	case "eggs" => println("Bacon")
	case _ => println("huh?")
}

//A more functional way, since match-case also return values
val res = 
firstArg match {
	case "salt" => "Pepper"
	case "chips" => "Salsa"
	case "eggs" => "Bacon"
	case _ => "Huh?"
}
println(res)


------- There's no break or continue in scala--------------
//To find the file endswith .scala but not start with -
//one way to do it
var i = 0
var foundIt = false

while (i < args.length && !foundIt){
  if (!args(i).startsWith("-")){
    if (args(i)).endsWith(".scala"))
      foundIt = true
  }
  i += 1
}

//A more functional way of doing it
def searchForm(i: Int): Int = {
  if (i >= args.length) -1
  else if (args(i).startsWith("-")) searchForm(i+1)
  else if (args(i).endsWith(".scala")) i
  else searchForm(i + 1)
}
val i = searchForm

------ Functions ------------------------------------------
//A simple function method to check the longlines in files
import scala.io.Source
object LongLines { 
  
  def processFile(filename: String, width:Int): Unit ={
    val source = Source.fromFile(filename)
    for (line <- source.getLines())
       processLine(filename, width, line)
  }
  
  private def processLine(filename: String, width: Int, line: String): Unit ={
    if (line.length > width)
      println(filename + ":" + line.trim)
  }
  
}


//In order to run it in command line
//first argument is width, all the others are filenames
object FindLongLines {
  def main(args: Array[String]): Unit ={
    val width = args(0).toInt
    for (arg <- args.drop(1))
      LongLines.processFile(arg, width)
  }
}

---- Local functions: functions that you don't want your users to see----
---- You can use private, while scala provides another choice: --
---- Functions within functions ---------------------------------
//This time using local functions
def processFile(filename: String, width: Int): Unit ={
  //here filename and width can be ignored, since it's in the scope
  def processLine(filename: String, width: Int, line: String): Unit ={
    if (line.length > width)
      println(filename + ":" + line)
  }  
  
  val source = Source.fromFile(filename)
  for ( line <- source.getLines()){
    processLine(filename, width, line)
  }
}

---- Function literal ---------------------------------------------
val increase = (x: Int) => x + 1
//increase is a function that increase the input by 1
//here you need to specify the input types, meanwhile
val someNumbers = List(-11, -10, -5, 0, 5, 10)
someNumers.filter( x => x > 0)
//here scala does target typing, so you can ignore type and parenthesis

//place holders
someNumers.filter( _ > 0)

val f = (x: Int, y: Int) => x + y
val f = (_: Int) + (_: Int) //same here
f(2, 3) //return 5


--- Partially invoked function -----------------------------------
def sum(a: Int, b: Int, c:Int) = a + b + c
val three_params = sum _
three_params(1, 2, 3) // 6

val one_params = sum(1, _:Int, 3)
one_params(2) //6

--- Closure ------------------------------------------------------
var more = 1
val increase = (x : Int) => x + more
increase(10) //return 11
//now that we change the value of more
more = 100
increase(10) //return 110

--- Repeated arguments -------------------------------------------
//just like kwags
def echo(args: String*){
  for (arg <- args) println(arg)
}
echo("A", "B", "C")

//unpacking values
val arr = Array("A", "B", "C")
echo(arr) //get error
echo(arr: _*) //expand the arguments by : _*


--- High order functions -----------------------------------------
--- Functions that take function as input ------------------------
object FileMatch {
  private def filesHere = (new java.io.File(".")).listFiles

  def filesEnding(query: String) = {
    for (file <- filesHere; if file.getName.endsWith(query))
      yield file
  }

  def filesContaining(query: String) = {
    for (file <- filesHere; if file.getName.contains(query))
      yield file
  }
  
  def filesRegex(query: String) = {
    for (file <- filesHere; if file.getName.matches(query))
      yield file
  }

}


//instead of passing in three different functions for searching
//you can pass in a function value instead
def filesMatching(query: String, matcher: (String, String) => Boolean) = {
  for (file <- filesHere, if matcher(file.getName, query))
    yield file
}

def filesEnding(query: String) = {
  filesMatching(query, _.endsWith(_))
  //This is exactly the same as
  filesMatching((query, (fileName: String, query: String) => fileName.endsWith(query)))
}

def filesContaining(query: String) = {
  filesMatching(query, _.contains(_))
}

def filesRegex(query: String) = {
  filesMatching(query, _.matches(_))
}

//This can be further simplified
object FileMatcher {
  private def fileHere = (new java.io.File(".")).listFiles
  
  private def filesMatching(matcher: String => Boolean) = {
    for (file <- fileHere; if matcher(file.getName))
      yield file
  }
  
  def filesEnding(query: String) = {
    filesMatching(_.endsWith(query))
  }
}

--- Currying -----------------------------------------------
