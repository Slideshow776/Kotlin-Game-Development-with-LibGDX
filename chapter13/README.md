# Alternative Sources of User Input
This chapter explores two alternative approaches of user input: gamepad controllers and touchscreen controls.


## Gamepad Controllers
> A game controller, or simply controller, is an input device used with video games or entertainment systems to provide input to a video game, typically to control an object or character in the game. -- <cite>[wikipedia.org](https://en.wikipedia.org/wiki/Game_controller)</cite>

This subchapter teaches how to set up a predefined button [controllers](https://github.com/libgdx/libgdx/wiki/Controllers) for an Xbox 360 gamepad, shown in the figure below.

![Xbox 360 Controller](https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/360_controller.svg/450px-360_controller.svg.png)

This requires the inclusion of three new `.jar` files; gdx-controllers.jar, gdx-controllers-desktop.jar, and gdx-controllers-desktop-natives.jar. 

Controller manufacturers are undecided on a standard button code format. This is why the most secure way of determining gamepad mapping should be left up to the player to configure at runtime by looping through the different actions required by the game. This chapter omits this functionality and instead offers a complete mapping of the Xbox gamepad setup found in the file `XBoxGamepad.kt`.

The following `Controller` class methods are available to poll the states of these gamepad components:
* Joystick: `getAxis(code)`, code determines left or right joystick, x-axis: -1 = left, +1 = right, y-axis: -1 = up, +1 = down
* Triggers: `getAxis(code)`, left trigger: [0, +1], right trigger: [0, -1]
* Gamepad buttons: `getButton(code)`
* Directional pad: `getPov(num)`, num is the index of the directional pad

Use of continuous input:

```
if (Controllers.getControllers().size > 0) {
    val gamepad = Controllers.getControllers()[0]
    val xAxis = gamepad.getAxis(XBoxGamepad.AXIS_LEFT_X)
    val yAxis = -gamepad.getAxis(XBoxGamepad.AXIS_LEFT_Y) // the orientation of most controllers is the opposite of LibGDX libraries
    val direction = Vector2(xAxis, yAxis)

    val length = direction.len()
    val deadZone = .1f
    if (length > deadZone) {
        setSpeed(length * 100)
        setMotionAngle(direction.angle())
    }
}
```

Use of discrete input:

```
override fun buttonDown(controller: Controller, buttonCode: Int): Boolean {
    if (buttonCode == XBoxGamepad.BUTTON_BACK) {
        dispose()
        BaseGame.setActiveScreen(LevelScreen())
    }
    return false
}
```



## Touchscreen Controls
Touch events and mouse events are handled in the same way as LibGDX. This chapter merely teaches how to use LibGDX's touchpad.

### Touchpad
> [Touchpad](http://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/scenes/scene2d/ui/Touchpad.html) ([code](https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/scenes/scene2d/ui/Touchpad.java)) is an onscreen joystick that moves in a
> circular area. It has a background drawable and a drawable for the
> knob that the user drags around. --
> <cite>[LibGDX's wiki](https://github.com/libgdx/libgdx/wiki/Scene2d.ui#touchpad)</cite>

![joystick](https://images-na.ssl-images-amazon.com/images/I/51pm4GggnCL._SY355_.jpg) ![image](https://user-images.githubusercontent.com/4059636/63485816-1c99cc00-c4a5-11e9-9d93-cc6930d3c7de.png)


Touchpads are comprised of two images; the base and the knob. The following algorithms initializes and uses a touchpad.
```
val touchStyle = TouchpadStyle()
touchStyle.knob = TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("assets/joystick-knob.png"))))
touchStyle.background = TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("assets/joystick-background.png"))))
touchpad = Touchpad(5f, touchStyle) // first input is deadzone in pixels.
```

```
val direction = Vector2(touchpad.knobPercentX, touchpad.knobPercentY)
val length = direction.len()
if (length > 0) {
    turtle.setSpeed(100 * length)
    turtle.setMotionAngle(direction.angle())
}
```

## New Imports

**import com.badlogic.gdx.controllers.PovDirection** - An enum class containing the possible directional pad's orientations.

**import com.badlogic.gdx.controllers.ControllerListener** - A way to register listeners to receive controller events, either globally for all controllers, or for a specific controller. 

**import com.badlogic.gdx.controllers.Controller** - Provides access to connected controller instances. Query the available controllers via `getControllers()`, add and remove global `ControllerListener` instances via `addListener(ControllerListener)` and `removeListener(ControllerListener)`. The listeners will be invoked on the rendering thread. The global listeners will be invoked for all events generated by all controllers. Polling a Controller can be done by invoking one of its getter methods.

**import com.badlogic.gdx.controllers.Controllers** - 

**import com.badlogic.gdx.controllers.Vector3** - 

**import com.badlogic.gdx.utils.viewport.FitViewport** - 

**import com.badlogic.gdx.scenes.scene2d.ui.Touchpad** - 

**import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle** - 