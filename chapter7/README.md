# Side-Scrolling Games
This chapter teaches about infinite scrolling backgrounds, parallax and difficulty ramps.

As suggested by the book the following features have been added:
* Give the player hit points
* Moving the player continuously rather than discretely
* Engine sounds for enemies
* Visual effect when the player moves upwards
* Enemies move in the y-direction
* Store highscore in a text file
* A menu, which displays all-time highscore
* A restart is displayed on game over

Bonus feature:
* More parallax

![menuscreen](https://user-images.githubusercontent.com/4059636/61268572-26405d80-a79c-11e9-81f0-407737df2f0c.png)


## Infinite Scrolling
What the game means by a side-scrolling game is simply that the game appears to be moving constantly in a direction (from right to left). This is done by having a copy of each background assets used and moving them side-by-side across the screen.

![sideScrolling](https://user-images.githubusercontent.com/4059636/61347394-150b5580-a85d-11e9-818d-63706d9814cc.png)

As shown in the figure above when the blue background moves completely clear of the green game screen, it is repositioned behind the red, and vice versa. It is also important that the beginning and the end of the background are _seamless_, which means the background can be placed side by side itself without creating a noticeable boundary.

The algorithm in a background's act method:
```
// if moved completely past left edge of screen: shift right, past other instance
if ( getX() + getWidth() < 0) {
    moveBy(2 * getWidth(), 0);
}
```

To save memory it is important that the texture asset is not _loaded_ twice, but rather copied.

## Parallax
> "Parallax scrolling is a technique in computer graphics where background images move past the camera more slowly than foreground images, creating an illusion of depth in a 2D scene and adding to the sense of immersion in the virtual experience."
>-- <cite>[wikipedia.org](https://en.m.wikipedia.org/wiki/Parallax_scrolling)</cite>

![The Whispered World](https://upload.wikimedia.org/wikipedia/commons/thumb/f/fb/The_Whispered_World_parallax_scrolling_sample_1.jpg/750px-The_Whispered_World_parallax_scrolling_sample_1.jpg)
>"A side view of the layers used for parallax scrolling in the Whispered World" >-- <cite>[wikipedia.org](https://en.m.wikipedia.org/wiki/Parallax_scrolling)</cite>

As a bonus feature, a Parallax class was created and implemented, the game now has 4 layers of background, which quickly adds depth to the world. Some consideration should be used for the color schemes such that the background will remain a background and not become too noisy

## Difficulty Ramps
Ramping the difficulty simply means changing the difficulty over time or by other events. Plane Dodger becomes increasingly difficult over time for every time a new enemy is spawned up to a cap.

The algorithm in `LevelScreen`, which is run each time an enemy spawns:
```
enemySpawnInterval -= 0.10f;
enemySpeed += 10;

if (enemySpawnInterval < .50f)
    enemySpawnInterval = .50f;

if (enemySpeed > 400)
    enemySpeed = 400;
```

## Bugs
Unfortunately, the book does not explain how to better manage assets. This becomes a problem with the sparkle animation which is triggered when the player collects a star. What happens is that game 'lags' for a second or so upon pickup. This occurs because the game has to read the texture from the file and run the setup and animation algorithm for each animation. To ameliorate this a [TexturePacker](https://github.com/libgdx/libgdx/wiki/Texture-packer) and [AssetManager](https://github.com/libgdx/libgdx/wiki/Managing-your-assets) may be implemented (not shown in any of the chapters of the book).

## New Imports

No new import