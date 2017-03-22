! detailed documentation on Scala testing suits
http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
A basic testing will be like:

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {
  test("adding integers"){
    assert(1 + 2 === 4)
  }
}
//test() defines the name of the test
//assert using === shows detailed information when assert fails


scala default uses call by value -> evaluates expression before passing to function
you can force to use call by name -> pass expression to function before evaluation by using:
def first(x: Int, y: => Int) = 1 //here y is call by name

1. the def is "by name", its right-hand side is evaluated on each use
2. val is by value, the right-hand side of a val definition is evaluated at the 
point of definition itself.

ex.
def loop: Boolean = loop
def x = loop //no problem
val x = loop //infinite loop

for recursive function, the return type is required, while for other function it's optional

---- Blocks and lexical scopes ------------------------
A block is delimited by braces {...}
1. It contains a sequence of definitions or expressions
2. The last element of a block is an expression that defines its value
3. Blocks are themselves expressions, a block may appear everywhere an expression can.
4. The definitions inside a block shadow definitions of the same name outside of the block

ex. newton's method

import scala.math
def sqrt(x: Double) = {
  def sqrtIter(guess: Double, x: Double): Double =
    if (isGoodEnough(guess, x)) guess
    else sqrtIter(improve(guess, x), x)

  def isGoodEnough(guess: Double, x: Double): Boolean =
    math.abs(guess * guess - x) < 0.001

  def improve(guess: Double, x: Double): Double =
    (guess + x / guess) / 2
  //last element of block defines the value
  sqrtIter(1, x)
}
sqrt(2)

lexical Scoping:
1. definitions of outer blocks are visible inside a block unless they're shadowed.
2. Previous example can be simplified by eliminating redundant occurrences of the x parameter

import scala.math
def sqrt(x: Double) = {
  def sqrtIter(guess: Double): Double =
    if (isGoodEnough(guess)) guess
    else sqrtIter(improve(guess))

  def isGoodEnough(guess: Double): Boolean =
    math.abs(guess * guess - x) < 0.001

  def improve(guess: Double): Double =
    (guess + x / guess) / 2

  sqrtIter(1)
}
sqrt(2)


--- Tail Recursion ----------------------
//it's a tail recursion call
def gcd(a: Int,  b: Int): Int = 
  if (b == 0 ) a else gcd(b, a%b)

//not a tail recursion call, because the last it also times n
def factorial(n: Int): Int = 
  if (n == 0) 1 else n * factorial(n-1)

If a function cas itsef as its last action, the functions stack frame can be reused.
This is called tail recursion. 
In general, if the last action of a function consist of calling a function (which may be the same), 
one stack frame would be sufficient for both functions. Such calls are called tail-calls.

In Scala, only directly recursive calls to the current function are optimized
One can require that a function is tail-recursing using:
@tailrec
def gcd(a: Int, b: Int): Int = ....


--- High-order functions -------------------
functions that take other functions as parameters or that return 
functions as results are called higher order functions

def sumInts(a: Int, b: Int): Int = {
  if (a > b) 0 else a + sumInts(a+1, b)
}

def cube(x: Int): Int = x * x * x

def sumCubes(a: Int, b: Int): Int =
  if (a > b) 0 else cube(a) + sumCubes(a+1, b)

//abstract the pattern
def sum(f: Int => Int, a: Int, b: Int): Int = {
  if (a > b) 0 else f(a) + sum(f, a+1, b)
}

def sumInts(a: Int, b: Int) = sum(identity, a, b)
def sumCubes(a: Int, b: Int) = sum(cube, a, b)

Here we defines extra small functions which can be pretty annoying
compare to strings:
def str = "abc"; println(str)
println("abc")
we can do that because string exists as literals, Analogously we 
would like function literals => anonymous functions

Note that a and b get passed unchanged from sumInts and sumCubes into sum
can we be even shorter by getting rid of those parameters?

def sum(f: Int => Int): (Int, Int) => Int = {
  def sumF(a: Int, b: Int): Int =
    if (a > b) 0
    else f(a) + sumF(a + 1, b)
  sumF
}

sum is now a function that returns another function
now:
def sumInts = sum(x => x)
def sumCubes = sum(x => x* x* x)

we can also do sum(cube)(1, 10)
note here sum(cube) applies sum to cube and returns a FUNCTION
this function is then applied to parameters (1, 10)

Scala provides a special syntax
def sum(f: Int => Int)(a: Int, b: Int): Int =
  if (a > b) 0 else f(a) + sum(f)(a+1, b)


Because operators are also valid identifiers so - (minus sign) can also be an identifiers
To implement method of a - b, it should be def - (a, b)
To implement method of -a, it should be def unary_- : type = 


Any subclasses can be used when a superclass is required.
//binary tree implementation of integer set
abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
}

object Empty extends IntSet {
  def contains(x: Int) = false
  def incl(x: Int): IntSet = new NonEmpty(x, Empty, Empty)
  override def toString = "."
}

class NonEmpty(
     val elem: Int,
     val left: IntSet,
     val right: IntSet
              ) extends IntSet {
  def contains(x: Int): Boolean = {
    if (x < elem) left contains x
    else if (x > elem) right contains x
    else true
  }

  def incl(x: Int): IntSet = {
    if (x < elem) new NonEmpty(elem, left incl x, right)
    else if (x > elem) new NonEmpty(elem, left, right incl x)
    //already in the tree
    else this
  }

  override def toString = "{" + left + elem + right + "}"
}

val t1 = new NonEmpty(3, Empty, Empty)
val t2 = t1 incl 4

Since there is really only one Empty set, so it's better to create EmptySet as an object. 
object is created when it's first referenced by calling its name.


--- managing modules with packages ----------------------
//top of source file
package progfun.examples
object Hello { ... }
//To run the Hello program
scala progfun.examples.Hello

import progfun._  //import everything in progfun
import progfun.{Hello, Rational} //import selected packages

!you can import either a package or an object


--- Traits -----------------------------------------------
in Java, as well as in Scala, a class can only have on superclass
But when a class has several natural supertyeps to which it conforms
Now we use traits
trait can not have parameters.

class, object and trait can inheritant from at most one class but as many traits as you want
class Square extends Shape with Area with Movable with param {
  ...
}


The type of null is Null, Null is a subtype of every class that inheritants from object;
while it is incompatible with subtypes of AnyVal

val x = null
val y: String = null
val z: Int = null //error

--- Type parameters --------------------------------------
trait List[T]{
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
}


class Cons[T](val head: T, val tail: List[T]) extends List[T]{
  def isEmpty = false
}

class Nil[T] extends List[T] {
  def isEmpty = true
  def head = throw new NoSuchElementException("Nil.head")
  def tail = throw new NoSuchElementException("Nil.tail")
}

//function can also take tyep parameters
def singleton[T](elem: T) = new Cons[T](elem, new Nil[T])
!! Need to define [T] right after the function name 
!! So the parameters know what [T] is  

singleton[Int](1)
singleton[Boolean](true)
//most times Scala compile can infer the type
singleton(1)
singleton(true)

Type parameters do not affect the evaluation in Scala
We can assume all type parameters and arguments are removed before evaluating the program

polymorphism:
1. subtyping: instances of a subclass can be passed to a base class
2. generics: instances of a function or class can be created by type parameterization


--- Objects everywhere ------------------------
A pure OO language is one in which every value is an object

function values are treated as objects in Scala
function A => B is just an abbreviation for the class
scala.Function1[A, B], which is roughly defined as follows.Array

package scala
trait Function1[A, B] {
  def apply(x: A): B
}

Expansion of function values
  (x: Int) => x * x
is expanded to:
  {class AnonFun extends Function1[Int, Int]{
    def apply(x: Int) = x * x
  }
    new AnonFun
}
  
//implementation of List
trait List[T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmpty: Boolean =  false
}

class Nil[T] extends List[T] {
  def isEmpty: Boolean = true
  def head: Nothing = throw new NoSuchElementException()
  def tail: Nothing = throw new NoSuchElementException()
}

object List {
  //List(1, 2) is List.apply(1, 2)
  def apply[T](x1: T, x2: T): List[T] = {
    new Cons(x1, new Cons(x2, new Nil))
  }
  def apply[T](x1: T): List[T] = {
    new Cons(x1, new Nil)
  }

  def apply[T]() = new Nil
}


Two principal forms of polymorphism
1. subtyping: we can pass instance of a subtype when a base type is required
2. generics: we can parameterize types 
Two main areas where these two interact:
1. bounds: subject type parameter to some constraints
2. variance: define how parameter types behave under subtyping

//upper bounds
def someFunction[S <: IntSet](r: S): S = {
    ...
}

//lower bounds
def someFunction[S >: IntSet](r: S): S = {
  ...
}

If A <: B, then everything one can do with a value of type B, 
one should also be able to do with a value of type A

same if A <: B                    
if c[A] <: c[B] c is covariant     -------   class c[+T]
if c[B] <: c[A] c is contravariant -------   class c[-T]

--- Pattern Matching ----------------------------------------
trait Expr{
  def eval: Int = this match {
    case Number(n) => n
    case Sum(e1, e2) => e1.eval + e2.eval  
  }
}

case class Number(n: Int) extends Expr

case class Sum(e1: Expr, e2: Expr) extends Expr

def eval(e: Expr): Int = e match {
  case Number(n) => n
  case Sum(e1, e2) => eval(e1) + eval(e2)
}


val test = Number(2)
eval(test) //2

Patterns are constructed from
1. constructor: Number, Sum
2. variables: n, e1, e2
3. wildcard patterns: _
4. constants, eg. 1, true

rules:
Variables always begin with a lowercase letter
The same variable name can only appear once in a pattern
Names of constants begins with a capital letter, with the exception of 
reserved words null, true, false


Pattern matching for List:
def sample[T](xs: List[T]) = xs match {
  //first match empty list
  case List() => List()
  case x::xs => .....
}

//insertion sort with pattern matching
def isort(xs: List[Int]): List[Int] = xs match {
  case List() => List()
  case x::xs => insert(x, isort(xs))
}


def insert(x: Int, xs: List[Int]): List[Int] = xs match {
  case List() => List(x)
  case y::ys => {
    if (x <= y ) x::y::ys else y::insert(x, ys)
  }
}

val test = List(2, 5, 3, 1, 4)
isort(test)


Pattern matching for Pair:
//merge sort
def msort(xs: List[Int]): List[Int] = {
  val n = xs.length / 2
  if (n == 0) xs
  else {
    val (fst, snd) = xs splitAt n
    merge(msort(fst), msort(snd))
  }
}

def merge(xs: List[Int], ys: List[Int]): List[Int] = {
  (xs, ys) match {
    case (Nil, z::zs) => ys
    case (t::ts, Nil) => xs
    case (t::ts, z::zs) => {
      if (t <= z) t::merge(ts, ys)
      else z::merge(xs, zs)
    }
  }
}



--- More on Lists ---------------------------------------
def last[T](xs: List[T]): T = xs match {
  case List() => throw new Error("Last of empty")
  case List(x) => x
  case y::ys => last(ys)
}


def init[T](xs: List[T]): List[T] = xs match {
  case List() => throw new Error("Last of empty")
  case List(x) => List()
  case y::ys =>   y::init(ys)
}

def concat[T](xs: List[T], ys: List[T]): List[T] = xs match {
  case List() => ys
  case z::zs => z::concat(zs, ys)
}

def reverse[T](xs: List[T]): List[T] = xs match {
  case List() => xs
  case y::ys => reverse(ys) ++ List(y)
}

def removeAt[T](xs: List[T], n: Int): List[T] = {
  if (n > xs.length)
    xs
  else if (n == 0)
    xs.tail
  else
    xs.head::removeAt(xs.tail, n-1)
}

def flatten(xs: List[Any]): List[Any] = xs match {
  case Nil => Nil
  case (head: List[Any]) :: tail => flatten(head) ++ flatten(tail)
  case head :: tail => head :: flatten(tail)
}

flatten(List(List(1, 1), 2, List(3, List(5, 8))))



High-order functions:






































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
def curriedSum(x: Int)(y: Int) = x + y
curriedSum(1)(2) //3


--- writing control structures -----------------------------
def runTwice(op: Double => Double, x: Double) = op(op(x))
runTwice(_ + 1, 5)

--- by-name parameters -------------------------------------
//A new type of asserts
var assertionEnabled = true

def myAssert(predicate: () => Boolean) = 
  if (assertionEnabled && !predicate())
    throw new AssertionError

//Now if you wanna use it
myAssert(() => 5 > 3)

def byNameAssert(predicate: => Boolean) = 
  if (assertionEnabled && !predicate)
    throw new AssertionError
//So this time you can use
myAssert(5 > 3)

--- Composition & Inheritance -----------------------------
composition: one class holds a reference to another, using the referenced
class to help fulfill its mission
inheritance: superclass/subclass relationship

//create an abstract class
abstract class Element {
  def contents: Array[String]
  def height: Int = contents.length
  def width: Int = if (height == 0) 0 else contents(0).length 
}
//you can not instantiate an abstract class

Note: Technically a parenthesisless function and a function with no parameters
are the same.
Note: But traditionally a perenthesisless function has no side effect: like ##.length
Note: Meanwhile a function with no parameter would usually have some sort of side effect: like println()

//create an abstract class
abstract class Element {
  def contents: Array[String]
  def height: Int = contents.length
  def width: Int = if (height == 0) 0 else contents(0).length
}
//you can not instantiate an abstract class

ArrayElement is a subclass of Element class
it inherits all the non-private members from class Element
if leaves out an extends clause, scala implicitely assumes it's inherits
from the class AnyRef

class ArrayElement(conts: Array[String]) extends Element {
  def contents: Array[String] = conts
}

//you can also override the method in the abstract class into a field
class ArrayEleemnt(const: Array[String]) extends Element {
	val contents: Array[String] = const
}

//in scala it's forbidden to define method and field with the same name

//A better way to do it, combining field and parameter
class ArrayElement(val contents: Array[String]) extends Element

//you can also define var and private in this way
class Cat {
  val dangerous = false
}
class Tiger(
  override val dangerous: Boolean,
  private var age: Int
           ) extends Cat

//note here to invoke the constructor of superclass
//you need to pass an Array to ArrayElement class
class LineElement(s: String) extends ArrayElement(Array(s)) {
  override def width = s.length
  override def height = 1
}

//because of the inheritance hierachy, the following statements will be all valid
val a: Element = new ArrayElement(Array("hello", "world"))
val b: ArrayElement = new LineElement("hello world")

to declare a method can not be overrided, put final in front of the method:
final override def demo()
to declare a class that shouldn't have subclasses, put final in front of the class name:
final class ArrayElement


//re-implement LineElement so it's a subclass of Element
//which makes more sense in inheritance
class LineElements(s: String) extends Element {
  def contents = Array(s)
  override def height = 1
  override def width = s.length
}

//add functions in Element class
def above(that: Element): Element = {
    new ArrayElement(this.contents ++ that.contents)
  }

  /*
  def beside(that: Element): Element = {
    val contents = new Array[String](this.contents.length)
    for (i <- 0 until this.contents.length)
      contents(i) = this.contents(i) + that.contents(i)
      new ArrayElement(contents)
  }
  */
  
  //better way to implement it 
  def beside(that: Element): Element = {
    val contents = for ((line1, line2) <- this.contents zip that.contents) yield line1+line2
    new ArrayElement(contents)  
  }
  
  override def toString = contents mkString "\n"
}


--- Defining a factory object ----------------------------
Instead of allowing users to create different types of Element with new
we should create a companion object that creates different types of Elements in one place


object Element{
  def elem(contents: Array[String]): Element =
    new ArrayElement(contents)
  
  def elem(chr: Char, width: Int, height: Int): Element =
    new UniformElement(chr, width, height)
  
  def elem(line: String): Element =
    new LineElement(line)
}

then import Element.ele at the top of source file
so we call the factory method by simply using elem

--- refactoring Element class --------------------------
all the constructions goes into the companion object 
so the actual class uses the factory method

object Element {
  private class ArrayElement(
    val contents: Array[String]
  ) extends Element
  private class LineElement(s: String) extends Element {
    val contents = Array(s)
    override def width = s.length
    override def height = 1
}
  private class UniformElement(
    ch: Char,
    override val width: Int,
    override val height: Int
  ) extends Element {
    private val line = ch.toString * width
    def contents = Array.fill(height)(line)
  }
  def elem(contents:  Array[String]): Element =
    new ArrayElement(contents)
  def elem(chr: Char, width: Int, height: Int): Element =
    new UniformElement(chr, width, height)
  def elem(line: String): Element =
    new LineElement(line)
}

import Element.elem
      abstract class Element {
        def contents:  Array[String]
        def width: Int = contents(0).length
        def height: Int = contents.length
        def above(that: Element): Element = {
          val this1 = this widen that.width
          val that1 = that widen this.width
          elem(this1.contents ++ that1.contents)
}
        def beside(that: Element): Element = {
          val this1 = this heighten that.height
          val that1 = that heighten this.height
          elem(
            for ((line1, line2) <- this1.contents zip that1.contents)
            yield line1 + line2)
        }
        def widen(w: Int): Element =
          if (w <= width) this
          else {
            val left = elem(' ', (w - width) / 2, height)
            var right = elem(' ', w - width - left.width, height)
            left beside this beside right
}
        def heighten(h: Int): Element =
          if (h <= height) this
          else {
            val top = elem(' ', width, (h - height) / 2)
            var bot = elem(' ', width, h - height - top.height)
            top above this above bot
}
        override def toString = contents mkString "\n"
      }


--- Hierachy in Scala ----------------------------------
In scala, any class is a subclass of Any
The methods of Any includeds

final def ==(that: Any): Boolean
// == actually calls equals
// != is the negation of equals
final def !=(that: Any): Boolean
def equals(that: Any): Boolean
def ##: Int
def hashCode: Int
def toString: String

Any has two subclasses: AnyVal and AnyRef

Null and Nothing are the bottom class (subclass of all classes)
useful in error handling:

def error(message: String): Nothing =
  throw new RuntimeException(message)


--- Traits ----------------------------------------------
A trait encapsulates method and field definittions,
which can be reused by mixing them into classes.
Unlike class inheritance, in which each class must inherit from just one superclass,
a class can mix in any number of traits.

trait Philosophical {
  def philosophize(): Unit = {
    println("I consume memory, therefor I am!")
  }
}

class Frog extends Philosophical {
  override def toString: String = "Green"
}

val frog = new Frog

//trait also defines a type
val phil: Philosophical = frog

//mix trait into a class with a superclass
class Animal

class Frog extends Animal with Philosophical {
  override def toString = "green"
}

//you can still call Philosophical type
val phrog: Philosophical = new Frog

The only two differences between trait and class is that:
1. trait can not take constructor parametres 
trait NoPoint(x: Int, y: Int) // Does not compile
2. trait is dynamicly bounded to superclasses
when calling super.function in class, you already know the superclass


class Point(val x: Int, val y: Int)

trait Rectangular {
  //couldn't provide constructor variable
  def topLeft: Point
  def bottomRight: Point
  def left = topLeft.x
  def right = bottomRight.x
  def width = right - left
}

abstract class Component extends Rectangular {
  //Add other methods
}

--- Ordered traints ---------------------------------
class Rational(n: Int, d: Int) extends Ordered[Rational]{
  //some method
  def compare(that: Rational) = {
    this.number - that.number
  }
}
1. Ordered trait need to pass a type variable [] 
2. Ordered trait need to define a method compare

--- Traits as stackable modifications --------------
abstract class IntQueue {
  def get(): Int
  def put(x: Int)
}

import scala.collection.mutable.ArrayBuffer

class BasicIntQueue extends IntQueue {
  private val buf = new ArrayBuffer[Int]
  def get = buf.remove(0)
  def put(x: Int) {buf += x}
}

val test = new BasicIntQueue

test.put(10)
test.put(20)
test.get()
test.get()

1. Here doubling extends IntQueue, So it can only be mixed into a class that also extends IntQueue
2. trait can has a super.method that's actually from an abstract class, which is illegal 
when using a normal class

trait Doubling extends IntQueue {
  abstract override def put(x: Int) {super.put(2*x)}
}

//so to used a trait 
class MyQueue extends BasicIntQueue with Doubling
//now the put is doubled
val test = new MyQueue
test.put(10)
test.get() //20

//or you can just call a new class like this
val queue = new BasicIntQueue with Doubling

--- stacked -----------------------------------------
trait Incrementing extends IntQueue {
  abstract override def put(x: Int): Unit ={
    super.put(x+1)
  }
}

trait Filtering extends IntQueue {
  abstract override def put(x: Int): Unit ={
    if (x >= 0) super.put(x)
  }
}

//now stacking traits
val queue = new BasicIntQueue with Incrementing with Filtering
queue.put(-1)
queue.put(2)
queue.get()

!! The method in the traits further to the right runs first !!


1. If the behavior will not be reused, then make it a concrete class
2. If it might be reused in multiple, unrelated classes, make it trait
3. If you want to inherit from it in Java code
4. If you plan to distribute it in compiled form, and you expect outside groups
to write classes inheriting from it, you might lean towards using an abstract class.
because when a trait gains or loses a member, any classes that inherit from it must be recompiled.
5. If efficiency is very important, lean towards using a class  

--- Packages and Importing -------------------------
put the content of an entire file into pakage by putting package pacakgename on top
ex. package company.navigator

put the content of a certain package in curly braces if a document contains
several different Packages

package bobsrockets {
          package navigation {
            // In package bobsrockets.navigation
            class Navigator
            package tests {
              // In package bobsrockets.navigation.tests
              class NavigatorSuite
            }
} }


  package bobsrockets {
          package navigation {
            class Navigator {
              // No need to say bobsrockets.navigation.StarMap
              // Here because it's in the same package
              val map = new StarMap
}
            class StarMap
          }
          class Ship {
            // No need to say bobsrockets.navigation.Navigator
            // Only need to speciy navigation. ,because they are both in bobsrockets package
            val nav = new navigation.Navigator
          }
          package fleets {
            class Fleet {
              // No need to say bobsrockets.Ship
              def addShip() { new Ship }
} }
}

package bobsrockets {
   class Ship
}
 package bobsrockets.fleets {
   class Fleet {
     // Doesn’t compile! Ship is not in scope.
     def addShip() { new Ship }
   }

}

// Base packages are in package.scala
// In file launch.scala
 package launch {
   class Booster3
}
 // In file bobsrockets.scala
 package bobsrockets {
   package navigation {
     package launch {
       class Booster1
     }
     class MissionControl {
       val booster1 = new launch.Booster1
       val booster2 = new bobsrockets.launch.Booster2
       val booster3 = new _root_.launch.Booster3
} }
   package launch {
     class Booster2
} }

when using the curly-braces packaging syntax, 
all names accessible in scopes outside the packaging are also available inside it

We can also create nested packages without the curly braces by:
package bobsrockets
package fleets
class Fleet {

}

scala provides a package _root_ that is outside any package you can write

--- Imports ------------------------------------
package bobsdelights
abstract class Fruit(
    val name: String,
    val color: String
                    )

object Fruits {
  object Apple extends Fruit("apple", "red")
  object Orange extends Fruit("orange", "orange")
  object Pear extends Fruit("Pear", "yellowish")
  val menu = List(Apple, Orange, Pear)
}

// easy access to Fruit
import bobsdelights.Fruit

// easy access to all members of bobsdelights
import bobsdelights._

// easy access to all members of Fruits
import bobsdelights.Fruits._

The import in scala works more than just import packages
def showFruit(fruit: Fruit) {
  import fruit._
  println(name + "s are" + color)
}

--- Selectors and Renaming during import-----------
import Fruits.{Apple, Orange}
import Fruits.{Apple => Macintosh, Orange => Lemon}
import Fruits.{Apple => A, _}
//This imports all methods in Fruits except changing apple to A

//implicit imports in scala
import java.lang._
import scala._
import Predef._


private members:
A member labeled private is visible only inside the class or object that contains the member definition
class Outer {
  class Inner {
    private def f() { println("f") }
    class InnerMost {
      f() // OK }
    }
    (new Inner).f() // error: f is not accessible
  }

protected members:
A protected member is only accessible from subclasses of the class in which the member is defined
  package p {
    class Super {
      protected def f() { println("f") }
    }
    class Sub extends Super {
      f()
    }
    class Other {
      (new Super).f()  // error: f is not accessible
    }
  }

  A class can access all private methods of its companion object
  An object can access all private methods of its companion class

class Rocket {
  import Rocket.fuel
  private def canGoHomeAgain = fuel > 20
}
  
object Rocket {
  private def fuel = 10
}


--- List ----------------------------------------------
List is immutable
The element of list all have the same type

//These all the same
var nums = List(1,2, 3)
var nums = 1 :: (2 :: (3 :: Nil))

//list patterns
//just like expanding tuples in Python
val fruit = List("apple", "orange", "pears")
val List(a, b, c) = fruit
///if not sure about the length of list
val a::b::rest = fruit
//b will be a list
val a::b  = fruit

fruit.head //first one
fruit.tail //all but the first one
fruit.init //all but the last one
fruit.last //last one

val abcd = List("a","b","c","d","e")
abcd take 2 //take first 2
abcd drop 2 //take all except first 2
abcd splitAt 2 //return two lists
val (first, second) = abcd.splitAt(2) //assign value after spliit

val zipped = abcd.indices zip abcd // like enumerate in Python
val zipped = abcd.zipWithIndex 

val (abcd, indexes)  = zipped.unzip

val arr = abcd.toArray
val abcd = arr.toList

--- High-order functions for List
val nums = List(1,2,3,4)
nums map (_ + 1)
val words = List("the", "quick", "pass")
words map (_.toList.reverse.mkString) //reverse all the strings

var sum = 0 
nums foreach( sum += _)
//The difference between foreach and map is foreach returns unit

//take the elements untill
nums takeWhile(_  > 0)
//drop the elements unitll
words dropWhile (_ startsWith "t")

//span combines the takeWhile and dropWhile
val (a, b) = nums span(_ > 0)

//folding lists
//this creates a left leaning operation tree
def sum(xs: List[Int]): Int = {
  (0 /: xs)(_ + _)
}
//creates a right leaning operation tree
def product(xs: List[Int]): Int = {
  (xs :\ 1)(_ * _)
}


--- Collections -----------------------------------
//appending both ends of List
//use ListBuffer
import scala.collection.mutable.ListBuffer
val buf = new ListBuffer[Int]
buf += 1
buf += 2 //append at the end
0 +=: buf //append at the begining
buf.toList

//Simiarly with ArrayBuffer
import scala.collection.mutable.ArrayBuffer
val Abuf = new ArrayBuffer[Int]()
Abuf += 1
Abuf += 2
0 +=: Abuf

//Using mutable sets for word count
val wordsArray = Array("A", "A", "B", "C", "D", "C")
import scala.collection.mutable
val words = mutable.Set.empty[String]
for (word <- wordsArray)
  words += word


//common operation for sets
//this is immutable, so can not use += 
val nums = Set(1,2,3)
nums + 5
nums - 3
nums ++ List(5, 6)
nums -- List(1,2)
nums & Set(1,3,5,7)
nums.size
nums.contains(3)


//Method for Maps
val mm = Map("i" -> 1, "ii" -> 2)
mm + ("vi" -> 6)
mm - "ii" //remove entry
mm ++ List("iii" -> 3, "v" -> 5) //adding multiple entries
mm.size
mm.contains("ii")
mm("ii")

mm.keys //return a iterator
mm.keySet //return a set
mm.values //return a iteratorgit 


--- Type parametization -----------------------------
//sorted sets and maps
import scala.collection.immutable.TreeSet
val ts = TreeSet(9, 3, 1, 8, 0)

import scala.collection.immutable.TreeMap
val tm = TreeMap(1 -> "x", 2 -> "y", 3 -> "z")

//this is slow, because the enqueue takes time proportional to the size of the queue
class SlowAppendQueue(elems: List[T]){
  def head = elems.head
  def tail = new SlowAppendQueue(elems.tail)
  def enqueue(x: T) = new SlowAppendQueue(elems:::List(x))
}

//One way of improvement is to reverse the list
//now head and tail takes time proportional to the size
class SlowHeadQueue(elem: List[T]){
  def head = elem.last
  def tail = new SlowHeadQueue(elem.init)
  def enqueue(x:T) = new SlowHeadQueue(x::elem)
}


//A better implementation
class Queue[T](
  private val leading: List[T],
  private val trailing: List[T]
              ){
  private def mirror = {
    if (leading.isEmpty)
      new Queue(trailing.reverse, Nil)
    else
      this
  }

  def head = mirror.leading.head
  def tail = {
    val q = mirror
    new Queue(q.leading.tail, q.trailing)
  }

  def enqueue(x: T) =
    new Queue(leading, x::trailing)

}

//private constructors
class Queue[T] private (
                         private val leading: List[T],
                         private val trailing: List[T]
                       )

new Queue(List(1,2), List(3)) //return error

//since the primary constructor of class Queue is no longer available
//there's other way to create new queues with auxiliary constructor
def this(elems: T*) = this(elems.toList, Nil)
//T* is notation for repeated parameters

//or add a factory methods by define an object Queue
object Queue {
  //construct a queue with initial elements 'xs'
  def apply[T](xs: T*) = new Queue[T](xs.toList, Nil)
}
//Now users can create new Queue with Queueu(1,2,3)




--- Abstract ---------------------------------------
Four types of abstracts in scala: type, method, val and var

trait Abstract {
  type T
  def transform(x: T): T
  val initial: T
  var current: T
}

//concrete implementation
class Concrete extends Abstract {
  type T = String
  def transform(x: String) = x + x
  val initial = "hi"
  var current = initial
}


//Abstract vals
val initial: String

def initial: String
//This is also valid, but the difference is val is guaranteed
//to yield the same value every time, while def could be implemented
//by a concrete method that return a different value

abstract class Fruit {
  val v: String // ‘v’ for value
  def m: String // ‘m’ for method
}
abstract class Apple extends Fruit {
  val v: String
  val m: String // OK to override a ‘def’ with a ‘val’
}
abstract class BadApple extends Fruit {
  def v: String // ERROR: cannot override a ‘val’ with a ‘def’
  def m: String
}


//abstract vars
trait AbstractTime {
  var hour: Int
  var minute: Int
}
//exactly the same
trait AbstractTime {
  def hour: Int
  def hour_=(x: Int)
  def minute: Int
  def minute_=(x: Int)
  // getter for ‘hour’
  // setter for ‘hour’
  // getter for ‘minute’
  // setter for ‘minute’
}


//abstract vals is often used in traits
//since traits can not take constructor values
trait RationalTrait {
  val numerArg: Int
  val denomArg: Int
}
//instantiate a concrete of that trait
//return an anonymous class
val t = new RationalTrait {
  val numerArg = 1
  val denomArg = 2
}

/*the expression of numerArg and denomArg are evaluated as part of 
the initialization of the anonymous class, but the anonymous class is 
initialized after the RationalTrait. So the values are not available during
the initialization of RationalTrait(the values will be Int:0)
 */

Two solutions to the problem:
1. Preinitialized fields
2. Lazy vals

import java.sql.Types

pre-initialized fields lets you initialize a field of subclass before the superclass is called

new {
  val numerArg = 1 * x
  val denomArg = 2 * x
} with RationalTrait

object twoThirds extends {
  val numerArg = 2
  val denomArg = 3
} with RationalTrait

//Because the pre-initialized fields are initialized before the
//superclass constructor is called, their initializers can't refer to
//the object that's being constructed

Lazy vals: Only evaluate when used, and save the value for later use

trait LazyRationalTrait {
  val numerArg: Int
  val denomArg: Int
  lazy val numer = numerArg / g
  lazy val denom = denomArg / g
  override def toString = numer +"/"+ denom
  private lazy val g = {
    require(denomArg != 0)
    gcd(numerArg, denomArg)
  }
  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
}

Abstract Types
class Food
abstract class Animal {
  def eat(food: Food)
}

class Grass extends Food
class Cow extends Animal {
  override def eat(food: Grass){}
}
//This won't compile, because the eat method in class Cow does not override the eat method in class Animal,
//because its parameter type is different

//A proper way of doing it is apply more precise type modeling
class Food
abstract class Animal {
  type SuitableFood <: Food //accept subclasses of Food
  def eat(food: SuitableFood)
}

class Grass extends Food
class Cow extends Animal {
  type SuitableFood = Grass
  override def eat(food: Grass){}
}

--- Enumeration -------------------------------------


