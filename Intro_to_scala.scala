/** mannually declare type */
var msg: String = "Hello world!"

def max(x: Int, y: Int): Int = {
	if (x > y) x
	else y
}

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

P86