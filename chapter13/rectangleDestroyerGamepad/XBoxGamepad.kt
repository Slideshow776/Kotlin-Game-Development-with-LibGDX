package chapter13.rectangleDestroyerGamepad

import com.badlogic.gdx.controllers.PovDirection

class XBoxGamepad {
    companion object {

        /* button codes */
        const val BUTTON_A: Int = 0
        const val BUTTON_B: Int = 1
        const val BUTTON_X: Int = 2
        const val BUTTON_Y: Int = 3
        const val BUTTON_LEFT_SHOULDER: Int = 4
        const val BUTTON_RIGHT_SHOULDER: Int = 5
        const val BUTTON_BACK: Int = 6
        const val BUTTON_START: Int = 7
        const val BUTTON_LEFT_STICK: Int = 8
        const val BUTTON_RIGHT_STICK: Int = 9

        /* directional pad codes */
        val DPAD_UP: PovDirection = PovDirection.north
        val DPAD_DOWN: PovDirection = PovDirection.south
        val DPAD_RIGHT: PovDirection = PovDirection.east
        val DPAD_LEFT: PovDirection = PovDirection.west

        /* joystick axis codes */
        // X-axis: -1 = left, +1 = right
        // Y-axis: -1 = up, +1 = down
        const val AXIS_LEFT_X: Int = 1
        const val AXIS_LEFT_Y: Int = 0
        const val AXIS_RIGHT_X: Int = 3
        const val AXIS_RIGHT_Y: Int = 2

        /* trigger codes */
        // Left & Right Trigger buttons treated as a single axis; same ID value
        // Values - Left trigger: 0 to +1. Right trigger: 0 to -1.
        // Note: values are additive; they can cancel each other if both are pressed.
        const val AXIS_LEFT_TRIGGER: Int = 4
        const val AXIS_RIGHT_TRIGGER: Int = 4
    }
}
