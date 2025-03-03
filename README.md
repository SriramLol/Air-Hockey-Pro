# Air-Hockey-Pro

Air Hockey game created using java
Project 4 Game Implementation
P4 Accountability Document due Monday March 3rd
P4 final submissions due Wednesday March 12th
Academic Integrity
Please make sure you read the academic integrity section of the syllabus so you understand what is permissible in our programming projects. Any case of academic dishonesty will be referred to the University's Office of Student Conduct. Please don't post your code online where others can see your code.
Project 4 Objectives and Background Information
This project will allow you to practice two-dimensional arrays, abstract classes, and test development, In this project, you will use what you know about inheritance and inner classes to implement a game of your choosing.
A few quick notes:
I'm providing you with some distribution code -- courtesy of Samir Paul -- that displays a black screen. Your job in this project will be to take the blank screen and make a game of your choosing. Start early!
For this project, you will be assigned to work with another person. You must write your names at the top of every class in the required javadoc comments (see code requirements).
Please read the end of the assignment to see what the final submission(s) should look like.
 
Part 1: Getting Started
Start by getting comfortable with the distribution code. (Linked in this assignment) 
Below you will read through the four classes included in the package.  You can see that they exist in the following class hierarchy (except the class for your game which will extend Game):

Note that they are imported via the package java.awt.  Also note that Game (and Component, for that matter) is an abstract class.  Confused about why I haven't included Component and Canvas?  Game itself is their direct subclass, and you won't need to make any other child classes of Component and Canvas other than Game.  So we won't pay much attention to them. 
What about the other two classes in the project?
The Point Class
There is an existing Point class in Java.awt.* but we will use our own. Please make sure your project does not import point from AWT! Otherwise, it should look straightforward.  Note that here, the instance variables are declared as public.  This is bad style and not very safe, but is done for your convenience so that you can access its instance variables (two int values) directly as <objectName>.<inst var 1> or, as an example with some point p1, p1.x and p1.y.  If you'd like, you can also use the getters/setters.  Either way works here.
The Polygon Class
What are the instance variables? What methods are defined?  In a client class, how would you create a new Polygon object?  Do we have a main method here?
When you make a new Polygon object, you pass it three parameters:
An array of points -- the shape of your desired polygon if drawn in the top left corner (remember -- that's 0,0 in screen coordinates!)
A single point -- an offset for your polygon. Polygon has code that will shift your points (from the first parameter) so that your shape is now centered on the second parameter point.  Changing this is how you "move" a shape.
A double -- how much you want to rotate your shape initially. 0 degrees is due east.
 
Look at YourGameName, which is your "control center" for this program:
Please rename this class (right click->refactor->rename) with the name of your game.
What is this class's parent class?  
Does YourGameName have a main method?  What happens when you run it?  
Look at the YourGameName What does it do?
Look at these two lines in the constructor:
setFocusable(true); 
	this.requestFocus();
These lines just mean, "Make it so that this Component can be focused on" and then "Focus on this Component."
Look at the paint() method. On this canvas, we use a brush.  We use that brush (which gets passed to paint as a parameter, an instance of the Graphics class) in order to render different elements.  
YourGameName.java will call its paint(Graphics brush) method every tenth of a second to draw the next frame of the game's animation.  Painting is simple: You call methods in the Graphics class using the brush, as in:
fillPolygon(...)or
drawString(...)
drawLine(...)
See the Graphics API and Graphics tutorial
As we develop our code, you'll write paint(Graphics brush) methods for your own classes -- for instance, for a ship, for an asteroid, etc.  The smart way to call those methods is to do so from inside YourGameName.paint(Graphics brush), passing the very same brush used in that method.
Part 2: Make things appear on screen!
Everything that will appear on the screen will be fueled by the Polygon class in the code distribution. You can have some fun creating intricate shapes for your items, but I would start with making sure you are able to draw (and later move) easy shapes like triangles and squares. A Polygon object holds information about a polygon's shape, its position on the game canvas, and how much it has been rotated from its default configuration.  Be sure to look at the instance variables and identify each.  Make sure you understand that a Polygon represents a shape as an array of Points.
When it comes time for you to begin writing the code for an element of your game (like a ship or character for example) follow the following steps:
Create a new class for the element, make sure that it is a subclass of Polygon
Write a constructor for the new class, don’t neglect the superclass constructor!
To make the element now show up on your screen, it will require you to write some more:
First, notice that Game is a subclass of Canvas, which is part of the user interface toolkit AWT.  YourGameName therefore inherits (from Game, which inherits from Canvas) a method paint(Graphics brush) which is called every tenth of a second to update the animation.
We're eventually going to make an instance of the element class we defined earlier.  But in order to make it appear, we need to give the element class a paint method that defines how it will be drawn.
In your element class, write an instance method void paint (Graphics brush) that draws the element. You should use either the fillPolygon or drawPolygon method in the Graphics class.
Note the three parameters of these methods. In your element’s class's paint method, you'll need to process your Point[] array called shape into two arrays that match the parameters of fillPolygon or drawPolygon. That means using this.getPoints() to get the array of Points and then processing it into an array of x coordinate integers and an array of y coordinate integers.
You might see that there are overloaded versions of fillPolygon and drawPolygon that take Polygons as a parameter. However, these take a different type of Polygon object as a parameter -- one defined in the awt package, NOT the one we are using.  So we won't be able to use those versions here. 
Let's actually make our element now.  We want to create our element when the game starts.  Think: Which method runs ONCE when we run this program?  That's where you should instantiate your element.  Note that we WON'T edit main at all. Here are a few key points about drawing your new element:
In that spot, you will instantiate a new element You'll declare your element variable as a class-level variable in YourGameName.
Before you actually construct the element object, first create an array of Point objects called elementPoints (something like shipPoints for example) that contains a series of Point objects that define the shape of the element. You'll pass this array into the constructor as the first parameter.  For example, creating an array holding these points would make the element a square with sides of ten pixels each:
new Point(0,0)
new Point(10,0)
new Point(10,10)
new Point(0, 10)
Note that the order in which you pass the points matters! The points should be in a logically drawn shape.
Make it start at the center of the screen.
Rotate it as necessary to get it to start the way you want it to.
OK, you've made your square and you've defined how it will be drawn. Now make it draw.
In YourGameName.paint, add a chunk of code that draws the element you just made. (Now does it make sense why you declared your element variable outside of any methods?  That makes it accessible to any element)
Now when you run YourGameName, you should see your element!
Is your element not showing up?  A few things to consider:
Casting has very high precedence. Are you properly casting the x coordinates and y coordinates of your element’s points when you get them in the element’s paint method?
Did you pass the Points to your element constructor in a logical order? e. is your element polygon going to draw sensibly?


Part 3: Adding Functionality
Making Elements move and turn:
In the Element class you created in part 2, create a method public void move() that will change the position of your element if the forward key is being held and change the rotation of the element if either of the turn keys are being held.  Don't worry yet about the keyboard responsiveness; first just focus on getting it to move on the screen without anything being pressed from the keyboard.  We'll add keyboard responsiveness in a moment.  We'll call this move method from YourGameName.paint()  before we call the element’s paint method. 
As you are implementing move(), think: What variable holds your element’s current position?  How do you access the x coordinate and y coordinate of the element’s current position?  Use a variable -- called something like amountToMove or stepSize -- to hold the basic step size of your element. 
Now add boolean instance variables for the forward, left, and right turn keys to your element class that can be set to true if the appropriate key is being held down and to false otherwise.  For now, set the forward key variable to true and the other two to false.  We'll set up the keyboard responsiveness in a moment.
Add code to element’s move() method so that the element only moves forward when the forward boolean variable is true.
Add keyboard responsiveness:
Let's fix that now by adding the KeyListener interface.  If you click the KeyListener API linked, you'll see that implementing KeyListener means your class MUST include three methods:
public void keyPressed(KeyEvent e)
public void keyReleased(KeyEvent e)
public void keyTyped(KeyEvent e)
These methods are called when keys are pressed (i.e. goes down) or released (i.e. key goes up).  We'll actually leave the keyTyped method empty in our implementation, but we still have to have it there in order to satisfy the interface that we are implementing.
All of those KeyListener methods take a KeyEvent object as their parameter.  This object contains information about which key was pressed or released.  You can get the key code, which is a number representing the key pressed or released by calling the non-static (instance) method getKeyCode() on the KeyEvent instance that was passed in as a parameter.  You'll want to check whether the KeyEvent's keycode is equal to whatever value you care about; see the predefined constants here.  For instance, if the key code is equal to the constant VK_ENTER, it means the user hit the Enter key.  Remember: These are static constants in the KeyEvent class.  You have a KeyEvent object that was passed as a parameter into each of the KeyListener methods.  That should tell you how to access them. 
So: Fill in the keyPressed() and keyReleased() methods so that when your chosen keys are pressed or released, their corresponding boolean values (for forward, left, and right) are changed appropriately.
The Canvas object -- which is the superclass of Game and therefore of YourGameName, too -- generates KeyEvent objects when a key is pressed, released, or typed.  Therefore, we need to register our new KeyListener with YourGameName, so that it will know where to send these event objects.  We do this by adding the code:
this.addKeyListener(whateverYourElementInstanceIsCalled);
in the constructor of YourGameName. 
Set the default for your boolean variable for the forward key being pressed to false.  Test your code to make sure that your element moves when the forward key is pressed.  Again, it might be moving in the wrong direction, but right now we just care about keyboard responsiveness.
Make the elements rotate:
Add code in move() to make the element rotate if the appropriate key is being held (i.e. if the correct boolean values are true).  Test that the element is rotating (though, again, it may still move in the wrong direction). 
Finally, let's get the element moving in the direction it's facing.  If the element doesn't move in the correct direction even before pressing the turn key, then you need to change the direction that your element is facing when it starts.  Take a look at the third parameter you pass when you instantiate your element object.
If the element moves in the correct direction when you start but then keeps moving in that same direction even after you press a turn key, then you need to use some trigonometry to make sure you are incrementing the x and y coordinates the correct amount to go the desired direction.  Specifically, if you are currently incrementing the x and y coordinates by the same amount, you need to instead multiply the increase in the x coordinate by Math.cos(Math.toRadians(rotation)) and multiply the increase in the y coordinate by Math.sin(Math.toRadians(rotation)).


Check for Collisions between elements:
OK, now if we want to check for whether or not our element(s) has collided with any/all of our elements.  To test for this collision, we need to be able to test if two polygons are intersecting each other.
Look at the method contains(Point point) in Polygon.  This method checks if the given Point is contained in whatever instance of Polygon is calling the method.  That is, are the coordinates of the given Point in the region defined by the Polygon calling it?  Notice that contains() calls getPoints(), so it is using the current location of the Polygon (with the offset and rotation applied) to get to the boundary.
Write a method called collides(Polygon other) in Polygon that uses the contains() method to test if two Polygons – this and other --  intersect.  What class should this go in?  Should this method be static or non-static?  What should the return type of this method be?  There are a few different ways; your way need not be perfect, but it should at least detect the most obvious collisions.
Call this collides method from YourGameName.paint.  You should have a loop in YourGameName.paint that iterates through your array of elements in your game, moves each one, checks if it has reached the edge and needs to be wrapped around, checks if it has collided with the ship, and then calls the Element’s paint method.
P4 Requirements
Now that you have a brief understanding of how to add simple functionality on this game engine, let’s talk about what you need to submit:
Code Requirements:
At least 3 different elements to your game in 3 classes
Things like ships, aliens, soccer balls etc.
2 inner classes
They must be in logical locations that take advantage of the inner class structure.
1 interface
1 anonymous class
Must be in logical locations that take advantage of the anonymous class structure.
1 lambda expression
Must have at least one element move using keyboard input
Must handle collisions between elements
Must rotate elements
Must have proper Javadoc comments for each class and method you write. We will be strict!
Most importantly: Must be well done and something you are proud of!
P4 Deadlines
By Monday March 3rd, you must submit a breakdown of how you and your teammate will work on this project together. Use this template to detail your plans.
By Wednesday March 12th you must submit:
a zip file of your project with all of your code on the submit server. 
Everyone must submit this for their code
Don't forget to rename files based on the project you implemented i.e. the project and YourGameName to be something specific to your game.
A write-up of your project on ELMS with:
UML Structure of your project
What went well?
What didn't go well?
If you had more time, what would you implement next?
What skills did you learn/demonstrate from this project? How?
Should be 1-2 pages with at least 3-4 sentences per question.
Only one person needs to submit on ELMS.
A video walkthrough of your game, aim for a 5-6 minute maximum video.
You must submit a publicly accessible link to your video. If we are not able to access it, you will lose points!
Only one person needs to submit on ELMS.

