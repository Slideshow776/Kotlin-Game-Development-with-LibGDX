package chapter5.theMissingHomework

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Action

class SetAnimationAction(protected val animationToDisplay: Animation<TextureRegion>) : Action() {

    override fun act(delta: Float): Boolean {
        val ba = target as BaseActor
        ba.setAnimation(animationToDisplay)
        return true
    }
}
