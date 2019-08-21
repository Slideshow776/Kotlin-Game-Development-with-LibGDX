# Alternative Sources of User Input (wip)
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
> Touchpad Touchpad (code) is an onscreen joystick that moves in a
> circular area. It has a background drawable and a drawable for the
> knob that the user drags around. --
> <cite>[LibGDX's wiki](https://github.com/libgdx/libgdx/wiki/Scene2d.ui#touchpad)</cite>