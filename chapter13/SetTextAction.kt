package chapter13

import com.badlogic.gdx.scenes.scene2d.Action

class SetTextAction(protected val textToDisplay: String) : Action() {

    override fun act(delta: Float): Boolean {
        val db = target as DialogBox
        db.setText(textToDisplay)
        return true
    }
}
