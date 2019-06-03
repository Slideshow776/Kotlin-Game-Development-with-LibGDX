package chapter5.starfishCollector

import com.badlogic.gdx.scenes.scene2d.Action
import chapter5.starfishCollector.DialogBox

class SetTextAction(protected val textToDisplay: String) : Action() {

    override fun act(delta: Float): Boolean {
        val db = target as DialogBox
        db.setText(textToDisplay)
        return true
    }
}
