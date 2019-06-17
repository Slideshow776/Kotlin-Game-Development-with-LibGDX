package chapter6.rhythmTapper

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Message(x: Float, y: Float, s: Stage): BaseActor(x, y, s)  {
    var perfect: Animation<TextureRegion>
    var great: Animation<TextureRegion>
    var good: Animation<TextureRegion>
    var almost: Animation<TextureRegion>
    var miss: Animation<TextureRegion>

    init {
        perfect = loadTexture("assets/perfect.png")
        great = loadTexture("assets/great.png")
        good = loadTexture("assets/good.png")
        almost = loadTexture("assets/almost.png")
        miss = loadTexture("assets/miss.png")
    }

    fun pulseFade() {
        setOpacity(1f)
        clearActions()
        val pulseFade = Actions.sequence(
            Actions.scaleTo(1.1f, 1.1f, .05f),
            Actions.scaleTo(1.0f, 1.0f, .05f),
            Actions.delay(1f),
            Actions.fadeOut(.5f)
        )
        addAction(pulseFade)
    }
}
