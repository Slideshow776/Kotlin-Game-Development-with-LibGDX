package chapter5.starfishCollector

import com.badlogic.gdx.scenes.scene2d.actions.Actions

class SceneActions : Actions() {
    companion object {
        fun setText(s: String) = SetTextAction(s)
        fun pause() = Actions.forever(Actions.delay(1f))
    }
}
