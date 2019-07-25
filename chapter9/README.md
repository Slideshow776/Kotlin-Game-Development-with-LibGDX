# Drag-and-Drop Games (wip)
This chapter teaches how to add drag-and-drop functionality to your games. Clicking on an object, holding it, and dragging it across the screen until released.

## Drag-and-Drop Functionality
To add drag-and-drop functionality a new helper class named `DragAndDropActor` which extends `BaseActor` is created. The actor contains a listener on initialization which overrides [`InputListener`](https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/scenes/scene2d/InputListener.html), and three touch methods are of interest: 
* `public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)`
* `public void touchDragged(InputEvent event, float x, float y, int pointer)`
* `public void touchUp(InputEvent event, float x, float y, int pointer, int button)`

The float parametres of these three methods store the relative distance of where the mouse clicked on the object. E.g if an object that has a 200-pixel width and 300-pixel height is clicked in the center the x becomes 100, and the y becomes 150.

To calculate how much to move the actor in `touchDragged` the initial x and y values must be stored.

A `DropTargetActor` class is created to be the drop target.

## Movement and Interpolation
The [`Interpolation`](https://github.com/libgdx/libgdx/wiki/Interpolation) class is used to create a more smooth movement when a `DragAndDropActor` is forcibly moved. In this instance, the beginning and end of a movement are slowed down as shown in the graph below. 

![pow3](https://cloud.githubusercontent.com/assets/1666014/15840452/9b2c6b34-2c52-11e6-8e66-fe467a605220.png)

Code in `DragAndDropActor`:
```
addAction(Actions.moveTo(x, y, .5f, Interpolation.pow3));
```

Read more about interpolation on LibGDX's [wiki page](https://github.com/libgdx/libgdx/wiki/Interpolation).

## Jigsaw Puzzle Game
As suggested by the book the following features have been added:
* Added sounds and background music
* Added a label which displays current time spent
* Added a label which displays best overall highscore
* Rotation of puzzle pieces by double tap

Bonus features:
* A restart button
* Random number of pieces
* A screen transition which slides to the left
* Puzzle pieces bounce in animation

![Jigsaw Puzzle Game](https://user-images.githubusercontent.com/4059636/61556273-ad722780-aa61-11e9-81b5-5d2c5ecb1e5e.png)

### Double tap
The double tap algorithm in `DragAndDropActor` class's `touchUp` method:
```
// double tap
val time = TimeUtils.nanoTime()
if (time - lastTapTime > tapCountInterval)
    tapCount = 0
tapCount++
lastTapTime = time
if( tapCount == 2) // double click detected
    doubleTap()
```

### Rotation
Apparently, when rotating an actor it's whole coordinate system is rotated with it, meaning that the x-axis becomes the y-axis and vice versa on 90 and 270-degree rotations. The algorithm below is a solution that works.
```
override fun touchDragged(event: InputEvent?, eventOffsetX: Float, eventOffsetY: Float, pointer: Int) {
    val cos = cos(self.rotation * MathUtils.degreesToRadians)
    val sin = sin(self.rotation * MathUtils.degreesToRadians)

    val tox = (eventOffsetX - self.grabOffsetX)
    val toy = (eventOffsetY - self.grabOffsetY)

    var deltaX = 0f
    var deltaY = 0f

    if (abs(cos) == 1f) {
        deltaX = tox * cos
        deltaY = toy * cos
    } else {
        deltaX = toy * -sin
        deltaY = tox * sin
    }

    self.moveBy(deltaX, deltaY)
}
```

### Interpolation examples
#### Screen transition
```
addAction(
    Actions.sequence(
    Actions.moveBy(-800f, 0f, .75f, Interpolation.sine),
    Actions.removeActor()
))
```

#### Puzzle piece bounce in animation
```
val randomDelay = MathUtils.random(0f, 1f)
addAction(Actions.sequence(
    Actions.moveBy(0f, 620f, 0f),
    Actions.delay(randomDelay),
    Actions.moveTo(x, y, 2f, Interpolation.bounceOut)
))
```

## 52 Card Pickup
As suggested by the book the following features have been added:
* Added sounds and background music
* Added a label which displays current time spent
* Added a label which displays best overall highscore

Bonus features:
* A restart button
* A screen transition which slides to the left
* Cards bounce-in animation

![52 Card Pickup](https://user-images.githubusercontent.com/4059636/61574574-dd511780-aac1-11e9-8d19-87b44# Drag-and-Drop Games
This chapter teaches how to add drag-and-drop functionality to your games. Clicking on an object, holding it, and dragging it across the screen until released.

## Drag-and-Drop Functionality
To add drag-and-drop functionality a new helper class named `DragAndDropActor` which extends `BaseActor` is created. The actor contains a listener on initialization which overrides [`InputListener`](https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/scenes/scene2d/InputListener.html), and three touch methods are of interest: 
* `public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)`
* `public void touchDragged(InputEvent event, float x, float y, int pointer)`
* `public void touchUp(InputEvent event, float x, float y, int pointer, int button)`

The float parametres of these three methods store the relative distance of where the mouse clicked on the object. E.g if an object that has a 200-pixel width and 300-pixel height is clicked in the center the x becomes 100, and the y becomes 150.

To calculate how much to move the actor in `touchDragged` the initial x and y values must be stored.

A `DropTargetActor` class is created to be the drop target.

## Movement and Interpolation
The [`Interpolation`](https://github.com/libgdx/libgdx/wiki/Interpolation) class is used to create a more smooth movement when a `DragAndDropActor` is forcibly moved. In this instance, the beginning and end of a movement are slowed down as shown in the graph below. 

![pow3](https://cloud.githubusercontent.com/assets/1666014/15840452/9b2c6b34-2c52-11e6-8e66-fe467a605220.png)

Code in `DragAndDropActor`:
```
addAction(Actions.moveTo(x, y, .5f, Interpolation.pow3));
```

Read more about interpolation on LibGDX's [wiki page](https://github.com/libgdx/libgdx/wiki/Interpolation).

## Jigsaw Puzzle Game
As suggested by the book the following features have been added:
* Added sounds and background music
* Added a label which displays current time spent
* Added a label which displays best overall highscore
* Rotation of puzzle pieces by double tap

Bonus features:
* A restart button
* Random number of pieces
* A screen transition which slides to the left
* Puzzle pieces bounce in animation

![Jigsaw Puzzle Game](https://user-images.githubusercontent.com/4059636/61556273-ad722780-aa61-11e9-81b5-5d2c5ecb1e5e.png)

### Double tap
The double tap algorithm in `DragAndDropActor` class's `touchUp` method:
```
// double tap
val time = TimeUtils.nanoTime()
if (time - lastTapTime > tapCountInterval)
    tapCount = 0
tapCount++
lastTapTime = time
if( tapCount == 2) // double click detected
    doubleTap()
```

### Rotation
Apparently, when rotating an actor it's whole coordinate system is rotated with it, meaning that the x-axis becomes the y-axis and vice versa on 90 and 270-degree rotations. The algorithm below is a solution that works.
```
override fun touchDragged(event: InputEvent?, eventOffsetX: Float, eventOffsetY: Float, pointer: Int) {
    val cos = cos(self.rotation * MathUtils.degreesToRadians)
    val sin = sin(self.rotation * MathUtils.degreesToRadians)

    val tox = (eventOffsetX - self.grabOffsetX)
    val toy = (eventOffsetY - self.grabOffsetY)

    var deltaX = 0f
    var deltaY = 0f

    if (abs(cos) == 1f) {
        deltaX = tox * cos
        deltaY = toy * cos
    } else {
        deltaX = toy * -sin
        deltaY = tox * sin
    }

    self.moveBy(deltaX, deltaY)
}
```

### Interpolation examples
#### Screen transition
```
addAction(
    Actions.sequence(
    Actions.moveBy(-800f, 0f, .75f, Interpolation.sine),
    Actions.removeActor()
))
```

#### Puzzle piece bounce in animation
```
val randomDelay = MathUtils.random(0f, 1f)
addAction(Actions.sequence(
    Actions.moveBy(0f, 620f, 0f),
    Actions.delay(randomDelay),
    Actions.moveTo(x, y, 2f, Interpolation.bounceOut)
))
```

## 52 Card Pickup
As suggested by the book the following features have been added:
* Added sounds and background music
* Added a label which displays current time spent
* Added a label which displays best overall highscore

Bonus features:
* A restart button
* A screen transition which slides to the left

![52 Card Pickup](https://user-images.githubusercontent.com/4059636/61574574-dd511780-aac1-11e9-8d19-87b44ddfe8f5.png)

## Crazy Eights
As suggested by the book the game "Crazy Eights" was added.

![Crazy Eights](https://user-images.githubusercontent.com/4059636/61846390-37c5eb80-aea7-11e9-867f-b2172ee553ba.png)

To win all cards from the hand must be placed upon the pile in the middle. A card placed must match the color or value, eights can be laid down anytime.
ddfe8f5.png)

## Crazy Eights
As suggested by the book the game "Crazy Eights" was added.

![Crazy Eights](https://user-images.githubusercontent.com/4059636/61846390-37c5eb80-aea7-11e9-867f-b2172ee553ba.png)

To win all cards from the hand must be placed  upon the pile in the middle. Card placed must match the color or value, eights can be layed down anytime.
