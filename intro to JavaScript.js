//Javascript

//where to put scripts
<html>
	<head>
	<script defer src = 'example.js'></script>
	</head>
<body>
	
</body>
</html>


-----undefined variable
var message;
alert(message == undefined); //true


-------test whether a variable has value
if (car != null){
	//operations
}

------NaN(not a number)
isNaN(10) //false
isNaN('blue') //true


------string to number
Number()
pasrseInt('22.34', 10) //22 , based on 10
parseFloat('22.34') //22.34

-------string methods
var text = 'This is a test string';
alert(test.length); 

--------convert to string
String(object)


--------operator
++num;
--num;

var age = 29;
var anotherage = --age + 2;
alert(age);  //28
alert(anotherage); //30

var num1 = 2;
var num2 = 20;
var num3 = --num1  + num2; //21
var num4 = num1 + num2 //21

num++;
num--;

var num1 = 2;
var num2 = 20;
var num3 = num1--  + num2; //22
var num4 = num1 + num2; //21

----logic operation
! negative
&& and 
|| or


-----conditional operator
var max = (num1 > num2) ? num1: num2;
if (condition) statement1 else statement2

do {
//
} while ();

while (){
//
}

for (var i = 0; i < count; i++){
	//
}

for (; i < count; ){
	//same as while expression
}

with(){
	//operations upon a single object
}

switch (i) {
	case value: 
		//do sth
		break;
	case value2;
		//do sth
		break;
	default:
		//do sth
}

------User input
text = prompt('Enter some text:')

------DOM
window.document.write
window.alert

document.URL
document.title
document.referrer //last visited url
document.lastModified //last modify date
document.cookie

document.links // list of links in document
document.links[2].href

window.history.go(-2) // go back to the second last website
window.history.back() //last
window.histroy.forward() //next

window.location.href = "http://www.google.com" //load google into the current window
window.location.reload //refresh the website

------Array-------
//create an array
scores = new Array(1,3,5,7) //one way
scores = [1,3,5,7] //the other way
scores.length
scores.sort()

var test = "this is a very long string!"
parts = test.split(" ")
total = parts.join(" ")

----Object-------
//initiation functon
function Card(name, address, work, home){
	this.name = name;
	this.address= address;
	this.work = work;
	this.home = home;
	this.printCard = PrintCard; //method
}

function PrintCard(){
	document.write(this.name, this.address, this.work, this.home);
}

Tom = new Card("tom", '123 West', '222222', '3434343');

//Adding method to exiting object
String.prototype.heading = addhead;

function addhead(level) {
	html = 'h' + level;
	text = this.toString();
	return html + text;
}

------Built-in functions
Math.random()


-------defining event reaction in javaScript file
function mousealert(){
	alert('warning!');
}
document.onmousedown = mousealert;


//in html define and id
<a href = "" id = 'link1'>
//in javascript
obj = document.getElementById("link1");
obj.onclick = MyFunction;

----event project is what users type on keyboard
<body onKeyPress = "getkey(event);">
//in JavaScript
function getkey(e){
	if (!e) e = window.event;
}

-----event object for mouse action
onMouseOver
onMouseOut
onclick
//<a href = "http://www.google.com" onClick = "return(window.confirm('Sure?'));"></a>

onDblClick
onMouseDown
onMouseUp

---Javascript to detect mouse status
function mousestatus(e){
	if (!e) e = window.event;
	button  = e.button;
	whichone = (button < 2)? "left": "right";
	message = e.type + ":" + whichone + "\n";
	document.form1.info.value += message;
}
obj = document.getElementById('testlink');
obj.onmousedown = mousestatus;
obj.onmouseup = mousestatus;
obj.onclick = mousestatus;
obj.ondblclick = mousestatus;

---open new page
winObj = window.open("URL", "windowname", "feature list");
winObj.close();

---adjust window
window.moveTo()
window.moveBy();
window.resizeTo();
window.resizeBy();

------overTime
window.setTimeout





