# Adventure Games
This chapter teaches how to create a combat-based adventure game, with new features such as multiple types of weapons, NPC's with unique and dynamic dialogs, sword placement when swinging, more collision detection and hero knockback.

As suggested by the book the following features have been added:
* Audio
* A menu
* Bombs
* A restart button
* A new enemy

Bonus features:
* camera movement

![Gameplay](https://user-images.githubusercontent.com/4059636/63206774-ffd35200-c0ba-11e9-8c3a-3aca48ae2e23.png)

## Game Controls
* Arrow keys - for movement
* A - For shooting an arrow
* S - For swinging the sword
* E - For placing bombs
* B - To buy items from the shopkeeper NPC

## Level Setup
Once again the [Tiled](https://www.mapeditor.org) map-editor is used as a helper tool to create a map with objects and tiles. The biggest take away from this chapter is the custom properties attached to the object tiles, which essentially is extra custom data for each object. See chapter 10 notes for more on [tile maps](https://github.com/libgdx/libgdx/wiki/Tile-maps).

![Tiled](https://user-images.githubusercontent.com/4059636/63206765-ddd9cf80-c0ba-11e9-8391-242741b2d6ce.png)

## Spritesheet
Often spritesheets include multiple animation frames for each character action, as seen in the figure below.

![hero spritesheet](https://user-images.githubusercontent.com/4059636/63206965-01067e00-c0bf-11e9-988d-0e615d149af9.png)

To extract each animation per row LibGDX's TextureRegion class [split](https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/graphics/g2d/TextureRegion.html#split-int-int-) method will be used.

South, West, East and North represents the direction the character is moving, the algorithm used is shown below:
```
val fileName = "assets/hero.png"
val rows = 4
val cols = 4
val texture = Texture(Gdx.files.internal(fileName), true)
val framewidth = texture.width / cols
val frameheight = texture.height / rows
val frameDuration = .2f

val temp = TextureRegion.split(texture, framewidth, frameheight)
val textureArray = Array<TextureRegion>()
for (c in 0 until cols) {
    textureArray.add(temp[0][c])
}
south = Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG)

textureArray.clear()
for (c in 0 until cols) {
    textureArray.add(temp[1][c])
}
west = Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG)

textureArray.clear()
for (c in 0 until cols) {
    textureArray.add(temp[2][c])
}
east = Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG)

textureArray.clear()
for (c in 0 until cols) {
    textureArray.add(temp[3][c])
}
north = Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG)
```

## The Sword
The character may swing a sword. Placement of the sword upon the different directions the character may be facing is as simple as a minor offset and placing the sword on top of or underneath the hero. 
```
val offset = Vector2()
when (facingAngle) {
    0f -> offset.set(.5f, .2f)
    90f -> offset.set(.65f, .5f)
    180f -> offset.set(.4f, .2f)
    else -> offset.set(.25f, .2f)
}

sword.setPosition(hero.x, hero.y)
sword.moveBy(offset.x * hero.width, offset.y * hero.height)

// hero should appear in front of sword when facing north or west
if (facingAngle == 90f || facingAngle == 180f)
    hero.toFront()
else
    sword.toFront()
```

The actual swinging of the sword is done by `Actions.rotateBy()` after a small offset.
```
val swordArc = 90f
sword.rotation = facingAngle - swordArc / 2
sword.originX = 0f

sword.isVisible = true
sword.addAction(Actions.rotateBy(swordArc, .25f))
sword.addAction(Actions.after(Actions.visible(false)))
```

## Knockback
If the character collides with an enemy they should be knocked back as to give the player a chance to avoid further hits and also deny the instant death from rapid successive collisions. Calculating the angle of the knockback is done by subtracting the vector from the character to the enemy.
```
if (hero.overlaps(flyer)) {
    hero.preventOverlap(flyer)
    flyer.setMotionAngle(flyer.getMotionAngle() + 180f)
    val heroPosition = Vector2(hero.x, hero.y)
    val flyerPosition = Vector2(flyer.x, flyer.y)
    val hitVector = heroPosition.sub(flyerPosition)
    hero.setMotionAngle(hitVector.angle())
    hero.setSpeed(100f)
    health--
    hitHurtAudio.play()
}
```

Moving the hero can also be done as by using Actions. 
```
hero.addAction(Actions.moveBy(hitVector.x, hitVector.y, .1f, Interpolation.exp10))
```

## Camera Movement
As detailed by [Conner Anderson](https://www.youtube.com/playlist?list=PLD_bW3UTVsEnRf9k3uZI4V0y5Jcfp-0ER)'s camera effects tutorial, some are implemented by this project.
* `fun alignCamera()` has been modified with a lerping effect to smooth out the movement of the camera following the character.
* `fun averageBetweenTargetsCamera()` Creates an enemy lock-on, following both the hero and the enemy on an average.
* `fun searchFocalPoints()` When defined focal points (defined in Tiled) are within distance the camera no longer follows the character but locks on that focal point instead (used in both the NPC pens).
* `fun bindCameraToWorld()` The binding of the camera was refactored to its own method.

## Known Bugs
* When the game is over, or it's paused, the Flyers and Skulls keep on flying and can move through anything.
* Explosions are a bit too small.
* The character cannot change direction before speed is zero.
* Arrows that hit bushes may play the thud sound too many times.
* If the character is hit while the sword is swinging, the sword does not follow the hero when knockback.

## New Imports
No new imports