package chapter10.starfishCollector

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Align

class SceneActions : Actions() {
    companion object {
        fun setText(s: String) = SetTextAction(s)
        fun pause() = Actions.forever(Actions.delay(1f))
        fun moveToScreenLeft(duration: Float) = Actions.moveToAligned(0f, 0f, Align.bottomLeft, duration)
        fun moveToScreenRight(duration: Float) = Actions.moveToAligned(BaseActor.getWorldBounds().width, 0f, Align.bottomRight, duration)
        fun moveToScreenCenter(duration: Float) = Actions.moveToAligned(BaseActor.getWorldBounds().width / 2, 0f,  Align.bottom, duration)
        fun moveToOutsideLeft(duration: Float) = Actions.moveToAligned(0f, 0f, Align.bottomRight, duration)
        fun moveToOutsideRight(duration: Float) = Actions.moveToAligned(BaseActor.getWorldBounds().width, 0f, Align.bottomLeft, duration)
    }
}
