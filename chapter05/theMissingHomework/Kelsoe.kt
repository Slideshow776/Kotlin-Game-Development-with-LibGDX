package chapter05.theMissingHomework

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage

class Kelsoe(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    val normal: Animation<TextureRegion>
    val sad: Animation<TextureRegion>
    val lookLeft: Animation<TextureRegion>
    val lookRight: Animation<TextureRegion>
    val embarrased: Animation<TextureRegion>

    init {
        normal = loadTexture("assets/kelsoe-normal.png")
        sad = loadTexture("assets/kelsoe-sad.png")
        lookLeft = loadTexture("assets/kelsoe-look-left.png")
        lookRight = loadTexture("assets/kelsoe-look-right.png")
        embarrased = loadTexture("assets/kelsoe-embarrased.png")
    }
}
