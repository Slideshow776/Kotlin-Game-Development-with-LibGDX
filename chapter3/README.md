# Extended Starfish Collector

## Animation
**Value-Based Animations** uses a single image and continuously changes associated values (such as position or rotation).
These effects can easily be added to the game by using LibGDX's Action class.

**Image-Based Animations** is created from images that are rapidly displayed in sequence to create the illusion of movement.
In LibGDX this is accomplished by using the Animation class.

## Texture Filtering
In computer graphics, texture filtering or texture smoothing is the method used to determine the texture color for a texture mapped pixel, using the colors of nearby texels (pixels of the texture). There are two main categories of texture filtering, magnification filtering and minification filtering.

![Texture filtering: nearest vs Linear](https://www.gamedevelopment.blog/wp-content/uploads/2017/11/nearest-vs-linear-texture-filter.png)
![Texture filtering: nearest vs Linear](https://love2d.org/w/images/8/8f/ComparisonFilters.png)

## New Imports
**import com.badlogic.gdx.utils.array** - A resizable, ordered or unordered array of objects. If unordered, this class avoids a memory copy when removing elements (the last element is moved to the removed element's position).

**import com.badlogic.gdx.graphics.Color** - A color class, holding the r, g, b and alpha component as floats in the range [0,1]. All methods perform clamping on the internal values after execution.

**import com.badlogic.gdx.texture.TextureFilter** - In computer graphics, texture filtering or texture smoothing is the method used to determine the texture color for a texture mapped pixel, using the colors of nearby texels (pixels of the texture). There are two main categories of texture filtering, magnification filtering and minification filtering.

**import com.badlogic.gdx.g2d.Animation** - An Animation stores a list of objects representing an animated sequence, e.g. for running or jumping. Each object in the Animation is called a key frame, and multiple key frames make up the animation.

**import com.badlogic.gdx.g2d.Animation.PlayMode** - Defines possible playback modes for an Animation.

