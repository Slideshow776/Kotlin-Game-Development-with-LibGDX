package chapter5.theMissingHomework

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.scenes.scene2d.Stage

class Kelsoe(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    var normal: Animation<*>
    var sad: Animation<*>
    var lookLeft: Animation<*>
    var lookRight: Animation<*>

    init {
        normal = loadTexture("assets/kelsoe-normal.png")
        sad = loadTexture("assets/kelsoe-sad.png")
        lookLeft = loadTexture("assets/kelsoe-look-left.png")
        lookRight = loadTexture("assets/kelsoe-look-right.png")
    }
}
