package chapter13.spaceRocksGamepad

import com.badlogic.gdx.controllers.ControllerListener
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.controllers.PovDirection
import com.badlogic.gdx.math.Vector3

abstract class BaseGamepadScreen : BaseScreen(), ControllerListener {
    init {
        Controllers.clearListeners()
        Controllers.addListener(this)
    }

    // methods required by ControllerListener interface
    //  enable discrete input processing

    override fun connected(controller: Controller) {}

    override fun disconnected(controller: Controller) {}

    override fun xSliderMoved(controller: Controller, sliderCode: Int, value: Boolean): Boolean {
        return false
    }

    override fun ySliderMoved(controller: Controller, sliderCode: Int, value: Boolean): Boolean {
        return false
    }

    override fun accelerometerMoved(controller: Controller, accelerometerCode: Int, value: Vector3): Boolean {
        return false
    }

    override fun povMoved(controller: Controller, povCode: Int, value: PovDirection): Boolean {
        return false
    }

    override fun axisMoved(controller: Controller, axisCode: Int, value: Float): Boolean {
        return false
    }

    override fun buttonDown(controller: Controller, buttonCode: Int): Boolean {
        return false
    }

    override fun buttonUp(controller: Controller, buttonCode: Int): Boolean {
        return false
    }
}
