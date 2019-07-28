package chapter05.theMissingHomework

import com.badlogic.gdx.scenes.scene2d.Action

open class SetTextAction(protected val textToDisplay: String) : Action() {

    override fun act(delta: Float): Boolean {
        val db = target as DialogBox
        db.setText(textToDisplay)
        return true
    }
}
