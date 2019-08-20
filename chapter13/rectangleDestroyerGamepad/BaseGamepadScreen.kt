package chapter13.rectangleDestroyerGamepad

import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.ControllerListener
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.controllers.PovDirection
import com.badlogic.gdx.math.Vector3

abstract class BaseGamepadScreen : BaseScreen(), ControllerListener {
    init {
        Controllers.clearListeners()
        Controllers.addListener(this)
    }

    override fun connected(var1: Controller) {}

    override fun disconnected(var1: Controller) {}

    override fun buttonDown(var1: Controller, var2: Int): Boolean {
        return false
    }

    override fun buttonUp(var1: Controller, var2: Int): Boolean {
        return false
    }

    override fun axisMoved(var1: Controller, var2: Int, var3: Float): Boolean {
        return false
    }

    override fun povMoved(var1: Controller, var2: Int, var3: PovDirection): Boolean {
        return false
    }

    override fun xSliderMoved(var1: Controller, var2: Int, var3: Boolean): Boolean {
        return false
    }

    override fun ySliderMoved(var1: Controller, var2: Int, var3: Boolean): Boolean {
        return false
    }

    override fun accelerometerMoved(var1: Controller, var2: Int, var3: Vector3): Boolean {
        return false
    }
}
