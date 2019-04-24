# Extended Starfish Collector

## Animation
**Value-Based Animations** uses a single image and continuously changes associated values (such as position or rotation).
These effects can easily be added to the game by using LibGDX's Action class.

![animation of square rotating and changing color](https://camo.githubusercontent.com/c30621e6c4fe034f630f02ae177126cd84d26bb3/687474703a2f2f7777772e636f736d69636d696e642e636f6d2f6d6f74696f6e2f616e696d6174696f6e732f726f746174652e676966) ![animation of circle fading](https://camo.githubusercontent.com/41deb847c8c34f9a257b76264040d6dcc6424446/687474703a2f2f7777772e636f736d69636d696e642e636f6d2f6d6f74696f6e2f616e696d6174696f6e732f666164652e676966) ![animation of square growing and shrinking](https://camo.githubusercontent.com/dad6d0d48e5d2d856cc2fb278753c3cc07e753cf/687474703a2f2f7777772e636f736d69636d696e642e636f6d2f6d6f74696f6e2f616e696d6174696f6e732f73697a652e676966)

**Image-Based Animations** is created from images that are rapidly displayed in sequence to create the illusion of movement.
In LibGDX this is accomplished by using the Animation class.

![spritesheet of mario walking](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQknIrEmlPTg_WckDUat3EUnky-RXjrgPvqzqdW_bMz2Uhn9fMM2g)

## Texture Filtering
In computer graphics, texture filtering or texture smoothing is the method used to determine the texture color for a texture mapped pixel, using the colors of nearby texels (pixels of the texture). There are two main categories of texture filtering, magnification filtering and minification filtering.

![Texture filtering: nearest vs Linear](https://www.gamedevelopment.blog/wp-content/uploads/2017/11/nearest-vs-linear-texture-filter.png)
![Texture filtering: nearest vs Linear](https://love2d.org/w/images/8/8f/ComparisonFilters.png)

## Physics and Movement
### Velocity
How an object's position  (speed and direction) changes over time.
* Position (x, y) [m]
* velocity <x, y> [m/s]

![figure of a vector](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVziijmlIcnAnqHetkzw7e3lhLhooLLbsZ2SgfioxXAuP80mIU)

The image above is a visual representation of the vector <Vx, Va> with length V and direction angle ø.
LibGDX uses its own Vector2 and Mathutils libraries to handle vector calculations.

### Acceleration
Acceleration represents how fast velocity is changing
* Position (x, y) [m]
* Velocity <x, y> [m/s]
* Acceleration <x, y> [m/(s^2)]

If an object's initial velocity is <a, b> and there is a constant acceleration of <c, d>, then after t seconds, the formula at that time is <a + tc, b + td>

### Movement
Movement is implemented by the BaseActor.applyPhysics and keys are set in Turtle.kt

## Collision detection
All BaseActors have their default collision box set to a rectangle.

![default_collision_box-png](https://user-images.githubusercontent.com/4059636/54528810-527ad780-497e-11e9-9f18-cc6325064b8b.png)

Custom collision box is supported, based on the BaseActor's width and height and dependent on number of points chosen will approximate roundness. E.g. an actor with a width that equals it's height will be an octagon if the number of sides chosen is eight.

![octagon](https://user-images.githubusercontent.com/4059636/54529294-d08bae00-497f-11e9-8212-59e74d8095a9.png)


Note that LibGDX's Polygon collision algorithm only supports convex polygons.

![convex vs. concave polygon](https://user-images.githubusercontent.com/4059636/54909018-80fe3280-4ee9-11e9-9ca1-a75d25f7360a.png)


Relationships between the classes in the final version of the Starfish Collector game.
There are four main groups of classes involved in creating a game with this framework: the Launcher, the BaseGame class and its extensions, the BaseScreen class and its extensions, and the BaseActor class and its extensions.

![Relationships between the classes in the final version of the Starfish Collector game](https://user-images.githubusercontent.com/4059636/56556879-6f1bb800-6599-11e9-9a5a-198c5be68b0e.png)


## New Imports
**import com.badlogic.gdx.utils.array** - A resizable, ordered or unordered array of objects. If unordered, this class avoids a memory copy when removing elements (the last element is moved to the removed element's position).

**import com.badlogic.gdx.graphics.Color** - A color class, holding the r, g, b and alpha component as floats in the range [0,1]. All methods perform clamping on the internal values after execution.

**import com.badlogic.gdx.texture.TextureFilter** - In computer graphics, texture filtering or texture smoothing is the method used to determine the texture color for a texture mapped pixel, using the colors of nearby texels (pixels of the texture). There are two main categories of texture filtering, magnification filtering and minification filtering.

**import com.badlogic.gdx.g2d.Animation** - An Animation stores a list of objects representing an animated sequence, e.g. for running or jumping. Each object in the Animation is called a key frame, and multiple key frames make up the animation.

**import com.badlogic.gdx.g2d.Animation.PlayMode** - Defines possible playback modes for an Animation.

**import com.badlogic.gdx.math.Vector2** - Encapsulates a 2D vector. Allows chaining methods by returning a reference to itself

**import com.badlogic.gdx.math.MathUtils** - Utility and fast math functions.

**import com.badlogic.gdx.graphics.Camera** - Base class for OrthographicCamera and PerspectiveCamera.

**import com.badlogic.gdx.utils.viewport.Viewport** - Manages a Camera and determines how world coordinates are mapped to and from the screen.

**import com.badlogic.gdx.Screen** - Represents one of many application screens, such as a main menu, a settings menu, the game screen and so on.

**import com.badlogic.gdx.scenes.scene2d.Action** - Actions attach to an Actor and perform some task, often over time.

**import com.badlogic.gdx.scenes.scene2d.actions.Actions** - Static convenience methods for using pooled actions, intended for static import.
