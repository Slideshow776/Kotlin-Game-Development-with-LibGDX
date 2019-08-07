# Platform Games
This chapter teaches how to create a basic platformer game. The main character has multiple animations according to each of its actions. Extended physics and a 'sensory' helper actor to determine the ability to jump.

## Jumping Jack
Jumping Jack is the game created. It features several interactable objects and UI mechanics.

As suggested by the book the following features have been added:
* Added Spikes and health
* Audio
* Instructions screen
* Menu screen

Bonus features:
* Camera lerp

As shown in the figure below the level design was created using [Tiled](https://www.mapeditor.org), which the previous chapter explained more in detail.
![Level design in Tiled](https://user-images.githubusercontent.com/4059636/62594254-2b478700-b8da-11e9-8be3-18c583a1ce5a.png)

The figure below shows the gameplay of the game 'Jumping Jack'.
![Jumping Jack Gameplay](https://user-images.githubusercontent.com/4059636/62594274-43b7a180-b8da-11e9-861c-60f4d84264fd.png)

## Actors with Multiple Animations
Setting up different animation for different actions is simply initialize and use.
The sample algorithm below shows Jack the Koala's three different game states with each of it's own animation.
```
if (this.isOnSolid()) {
    belowSensor.color = Color.GREEN
    if (velocityVec.x == 0f)
    setAnimation(stand)
else
    setAnimation(walk)
} else {
    belowSensor.color = Color.RED
    setAnimation(jump)
}
```
Changing direction of the animation, such that Jack the Koala faces left and right is as easy as setting the scale of x in negative or positive: 
``` 
if (velocityVec.x > 0) // face right
    scaleX = 1f
if (velocityVec.x < 0) // face left
    scaleX = -1f
```

## Using actors as 'sensors'
Determining when Jack the Koala can jump is challenging, this is, however, possible to establish using a helper sensory actor positioned directly below. If this sensor overlaps a solid object then Jack the Koala should be able to jump. Detailed below are the algorithms that check if the Jack the Koala is on a solid object. From there jumping from the ground is made possible.

```
fun isOnSolid(): Boolean {
    for (actor in BaseActor.getList(stage, Solid::class.java.canonicalName)) {
        val solid = actor as Solid
        if (belowOverlaps(solid) && solid.enabled)
            return true
    }
    return false
}
```
```
fun belowOverlaps(actor: BaseActor): Boolean {
    return belowSensor.overlaps(actor) 
}
```

## Camera and Linear Interpolation
When the camera follows the main character smoothing out the motion is more visually pleasant.
This can be accomplished by following [Conner Anderson's](https://www.youtube.com/watch?v=M6KAYk9Xup4) algorithm tutorial summed up below.
```
a + (b - a) * lerp
a = current camera position
b = target
```
Where `lerp` is a value usually between .1 and .2, the lower the value the more 'lazy' the camera will follow. Below is his algorithm implemented into the game Jumping Jack.

```
// center camera on actor
val position = camera.position
position.x = camera.position.x + (x + originX - camera.position.x) * lerp
position.y = camera.position.y + (y + originY - camera.position.y) * lerp
camera.position.set(position)
```

## Bugs and Features
A few unwanted observations that never got corrected.
* **Collision Bug:** Sometimes when colliding with an object Jack the Koala slips right through the world.
* **Player Controls:** The player controls are 'heavy' and not too enjoyable, might just need some simple tuning.
* **Map Bug:** All actors are misaligned in the y direction, making it so that they float above when they should touch the ground.

## New Imports

No new imports
