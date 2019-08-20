# Bouncing and Collision Games
This chapter teaches about simulating bouncing off of things and enumerating types.

As suggested by the book the following features have been added:
* Added a menu
* Added additional sounds:
    * Ball lost
    * Game won
    * Game over
* A solid moving block
* Powerups:
    * Bonus points
    * Extra life
    * Destroy random brick
    * paddle freeze

Bonus feature:
* Brick explodes

![image](https://user-images.githubusercontent.com/4059636/60953053-b4c86100-a2fc-11e9-9768-fdc417dcd72b.png)

## Bouncing
In order to have the ball bounce off of things, the BaseActor's overlap method is used. This method returns the vector in which the object was moved away to prevent overlap. By comparing if the vector's x or y direction's values are the highest it is possible to determine if it is a horizontal or vertical bounce. Multiplying the respective direction with -1 gives the wanted bouncing behaviour.

The algorithm:

```
public void bounceOff(BaseActor other) {
    Vector2 v = this.preventOverlap(other);
    if (Math.abs(v.x) >= Math.abs(v.y)) // horizontal bounce
        this.velocityVec.x *= -1;
    else // vertical bounce
        this.velocityVec.y *= -1;
}
```

## Linearly Interpolating
In order to have the ball bounce off the paddle with the correct angular direction linear interpolation is used. Calculating how far along on the paddle the ball's x position is needed. As a percentage 0.0 represents the leftmost edge, .5 is the middle, and 1.0 is the rightmost edge. The percentage is then used in LibGDX's MathUtils.lerp method to return the appropriate angle.

The algorithm:
```
if (ball.overlaps(paddle)) {
    float ballCenterX = ball.getX() + ball.getWidth()/2;
    float paddlePercentHit = (ballCenterX - paddle.getX()) / paddle.getWidth();
    float bounceAngle = MathUtils.lerp(150, 30, paddlePercentHit);
    ball.setMotionAngle(bounceAngle);
}
```

## Enumerated Types
>An enum type is a special data type that enables a variable to be a set of predefined constants. The variable must be equal to one of the values that have been predefined for it. Common examples include compass directions (values of NORTH, SOUTH, EAST, and WEST) and the days of the week.
>
>Because they are constants, the names of an enum type's fields are in uppercase letters.
>
>In the Java programming language, you define an enum type by using the enum keyword. For example, you would specify a days-of-the-week enum type as:
>```
>public enum Day {
>    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
>    THURSDAY, FRIDAY, SATURDAY 
>}
>```
>-- <cite>[docs.oracle.com](https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html)</cite>

Enumeration types are quite useful when defining an actor's behaviour or item sets.


## Bugs
There are two possible bugs:
* The ball may move through solid objects in a single game iteration if:
    * the ball moves too fast
    * the object is too thin
* The ball may pass through bricks if it collides with them at the exact same time. The `bounceOff()` method is then called twice, reversing the direction twice.

## New Imports

No new imports